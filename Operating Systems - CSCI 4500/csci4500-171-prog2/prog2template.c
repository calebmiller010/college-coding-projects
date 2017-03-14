/*-----------------------------------------------------------------*/
/* This is a template for the solution to programming assignment 2 */
/* for CSCI 4500, Spring 2017. IT IS NOT A COMPLETE SOLUTION.      */
/* HOWEVER, THE CODE GIVEN COULD BE USED AS THE BASIS FOR A        */
/* SOLUTION.                               */
/*  --Stan Wileman, Feburary 7, 2017               */
/*-----------------------------------------------------------------*/

/*-------------------------------------------------------------*/
/* Input data (from standard input):                   */
/* =================================                   */
/*                                 */
/*  num_cpu   num_proc   quantum                   */
/*  pid  prio  t_submit  tot_cpu_req  cpu_cycle  io_cycle  */
/*  (the previous line occurs num_proc times)              */
/*                                 */
/*  There may be multiple simulations. The last case is    */
/*  followed by the end of file.                   */
/*                                 */
/*  num_cpu     number of CPUs (1..4)              */
/*  num_proc    number of processes (1..25)        */
/*  quantum     quantum size (1..?)            */
/*  pid     process ID (1..999)            */
/*  prio        process priority (1..10)           */
/*  t_submit    time process submitted (0..?)          */
/*  tot_cpu_req total CPU time process requires        */
/*  cpu_cycle   time process uses CPU before doing I/O */
/*  io_cycle    time for each I/O operation        */
/*                                 */
/* Each process will compute for 'cpu_cycle' time units, then  */
/* do I/O for 'io_cycle' time units, and repeat until the      */
/* total CPU time accumulated equals 'tot_cpu_req'. Thus a     */
/* process with tot_cpu_req = 25, cpu_cycle = 10, and io_cycle */
/* = 25 will proceed as follows:                   */
/*  10  compute                        */
/*  25  do I/O                         */
/*  10  compute                        */
/*  25  do I/O                         */
/*  5   compute                        */
/* If it was the only process, it would require 75 time units  */
/* to complete.                            */
/*                                 */
/* Scheduling                              */
/* ==========                              */
/* Scheduling rules are as follows:                */
/*  Processes are run in priority order, with higher       */
/*      priority processes preempting a running process    */
/*      with a lower priority.                 */
/*  Processes with the same priority are run round-robin,  */
/*      with the specified quantum size.               */
/*  If it is necessary to preempt a running process, then  */
/*      the lowest priority process is picked first. If    */
/*      there are multiple running processes with priority */
/*      priority lower than that of the process that is to */
/*      be run, then the process that has consumed the     */
/*      most CPU time in this quantum is selected. If that */
/*      rule still doesn't pick a unique process, then the */
/*      process on the lowest-numbered CPU is selected.    */
/*  Each time a process is moved (from the ready state or  */
/*      the not-yet-submitted state) to the running state, */
/*      it receives a full quantum.                */
/*                                 */
/* Process Preemption                          */
/* ==================                          */
/* Deciding which processes are to be preempted, if any, must  */
/* be done carefully.  Here is the preemption procedure:       */
/*                                 */
/* 1. If there are no ready processes, we are done.            */
/* 2. If there are no running processes, we are done.          */
/* 3. Pick the running process that might be preempted. This   */
/*    is the process with the lowest priority, with the least  */
/*    running time this quantum, on the lowest-numbered CPU.   */
/* 4. If the selected process has a priority less than that of */
/*    the highest priority ready process, then do the          */
/*    preemption. If not, we are done.                 */
/*    4.1 Move the process being preempted back to the ready   */
/*        state.                           */
/*    4.2 Move the highest-priority ready process to the       */
/*        vacated CPU.                         */
/* 5. Repeat from step 1.                      */
/*                                 */
/* Output Requirements                         */
/* ===================                         */
/* For each process, compute and display the following:        */
/*  process ID                         */
/*  completion time                        */
/* For each simulation, compute and display the following:     */
/*  average CPU idle time (total idle time of all CPUs     */
/*      divided by the number of CPUs)             */
/*  average turnaround time for all processes          */
/*                                 */
/* The output displayed by the instructor's solution should be */
/* used as an example.                         */
/*-------------------------------------------------------------*/

#include <stdio.h>

#define MAXCPU 4        /* limit from problem statement */
#define MAXPROC 25      /* limit from problem statement */

/* Process states */
#define NOTYETSUBMITTED 1
#define READY 2
#define RUNNING 3
#define BLOCKED 4
#define COMPLETED 5

int casenum;            /* simulation case # */
int last_t;         /* time of previous event */
int t;              /* simulation time (current event time) */
int nfinished;          /* # of procs in COMPLETED state */

int num_cpu;            /* # of CPUs in current simulation */
int num_proc;           /* # of processes (from input data) */
int quantum;            /* quantum size (from input data) */

struct{
    int idle;           /* total CPU idle time */
    int run_time;       /* time spent running current proc */
    int proc_ndx;       /* -1 if CPU is idle, else proc index */
} cpu[MAXCPU];

struct {
    /* ---- the input data: immutable ---- */
    int pid;            /* ID (1..999) */
    int prio;           /* priority (1..10) */
    int t_submit;       /* time of submission */
    int tot_cpu_req;        /* total CPU time required (1..1000) */
    int cpu_cycle;      /* CPU time in each cycle (1..100) */
    int io_cycle;       /* I/O time in each cycle (1..1000) */

    /* ---- dynamic process state information ---- */
    int state;          /* state */
    int t_event;        /* time of next "event" (-1 if unknown) */
    int tot_cpu;        /* CPU time used so far */
    int tot_io;         /* I/O time used so far */
    int t_finish;       /* time of completion */

    /* ---- linked list of all ready processes ---- */
    int next_ready;     /* index of next ready process */
} proc[MAXPROC];

int ready_head, ready_tail; /* index of head/tail of ready list */

/*-----------------------------------------------------------------*/
/* Get all of the input data for the next simulation and return 1. */
/* If the end of file is encountered, return 0.                    */
/*-----------------------------------------------------------------*/
int getdata(void)
{
    int i;

    if (scanf("%d",&num_cpu) != 1 || num_cpu == 0)
    return 0;

    if (scanf("%d%d",&num_proc,&quantum) != 2)
    return 0;

    for (i=0;i<num_proc;i++)
    scanf("%d%d%d%d%d%d",&proc[i].pid,&proc[i].prio,&proc[i].t_submit,
        &proc[i].tot_cpu_req,&proc[i].cpu_cycle,&proc[i].io_cycle);
    return 1;
}

/*---------------------------------------------------------------------*/
/* Add the process with index 'k' to the ready list at the appropriate */
/* place (after all processes with the same or higher priority, and    */
/* before all processes with lower priority).                          */
/*---------------------------------------------------------------------*/
void add_to_ready(int k)
{
    int prev, curr;


    proc[k].state = READY;
    prev = -1;
    curr = ready_head;
    while (curr != -1) {
    if (proc[k].prio > proc[curr].prio)
        break;
    prev = curr;
    curr = proc[curr].next_ready;
    }

    /*---------------------------------------*/
    /* Insert after 'prev' and before 'curr' */
    /*---------------------------------------*/
    if (prev == -1)
    ready_head = k;
    else
    proc[prev].next_ready = k;
    if (curr == -1) {
    ready_tail = k;
    proc[k].next_ready = -1;
    } else
    proc[k].next_ready = curr;
}

/*---------------------------------------------------------*/
/* See if any of the running processes finishes at time t. */
/* Move each such process to the finished state and mark   */
/* the CPU it was using as available.                      */
/*---------------------------------------------------------*/
void run_proc_fin(void)
{
}

/*------------------------------------------------------*/
/* See if any of the processes blocked for I/O finishes */
/* the I/O at time t. If so, move the process to the    */
/* ready state.                                         */
/*------------------------------------------------------*/
void io_completed(void)
{
}

/*---------------------------------------------------*/
/* If any process is submitted at time t, move it to */
/* the ready state.                                  */
/*---------------------------------------------------*/
void do_submit(void)
{
    int i;

    for (i=0;i<num_proc;i++) {
    if (proc[i].state != NOTYETSUBMITTED)
        continue;
    if (proc[i].t_event == t) {
        proc[i].t_event = -1;
        add_to_ready(i);
    }
    }
}

/*---------------------------------------------------------*/
/* Move processes that have finished a CPU cycle at time t */
/* from the running state to the blocked state, and flag   */
/* the CPU that was being used as idle.                    */
/*---------------------------------------------------------*/
void cpu_cycle_done(void)
{
}

/*-------------------------------------------------------*/
/* See if the quantum has expired for any of the running */
/* processes. If so, move the process to the ready state */
/* and flag the CPU that was being used as idle.         */
/*-------------------------------------------------------*/
void quantum_runout(void)
{
}

/*-------------------------------------------------------*/
/* For each idle CPU, pick the appropriate ready process */
/* and move it to the running state on that CPU. Also    */
/* determine and save the time of the next event for the */
/* processes moved to the running state.                 */
/*-------------------------------------------------------*/
void schedule_cpus(void)
{
}

/*---------------------------*/
/* Find the next event time. */
/*---------------------------*/
void set_next_event_time(void)
{
    int i, next_t;

    next_t = -1;
    for (i=0;i<num_proc;i++) {
    if (proc[i].state == COMPLETED)
        continue;
    if (proc[i].t_event == -1)
        continue;
    if (next_t == -1)
        next_t = proc[i].t_event;
    else if (proc[i].t_event < next_t)
        next_t = proc[i].t_event;
    }
    last_t = t;
    if (next_t != -1)
    t = next_t;

    /*------------------------------------------------*/
    /* Update the run time or idle time for each CPU. */
    /*------------------------------------------------*/
    for (i=0;i<num_cpu;i++) {
    if (cpu[i].proc_ndx == -1)
        cpu[i].idle += (t - last_t);
    else
        cpu[i].run_time += (t - last_t);
    }
}

/*--------------------------------------------------------------------------*/
/* Preempt the appropriate processes, if any. This function is to be called */
/* only as the last step in scheduling. In particular, if one or more CPUs  */
/* is idle, then there will be no preemption.                               */
/*--------------------------------------------------------------------------*/
void do_preempt(void)
{
}

/*-----------------------*/
/* Perform a simulation. */
/*-----------------------*/
void simulate(void)
{
    int i;
    int nevents = 0;

    last_t = 0;
    t = proc[0].t_submit;
    for (i=0;i<num_proc;i++) {          /* all not yet submitted */
    proc[i].state = NOTYETSUBMITTED;
    if (proc[i].t_submit < t)       /* find earliest submit time */
        t = proc[i].t_submit;
    proc[i].t_event = proc[i].t_submit;
    proc[i].next_ready = -1;
    proc[i].tot_cpu = 0;
    proc[i].tot_io = 0;
    proc[i].t_finish = -1;
    }

    for (i=0;i<num_cpu;i++) {           /* all CPUs are idle */
    cpu[i].idle = t;
    cpu[i].proc_ndx = -1;
    cpu[i].run_time = 0;
    }

    /*----------------------------------------------------------------*/
    /* XXX TEMPLATE CODE RETURNS HERE SINCE IT WILL NEVER FINISH. XXX */
    /* XXX ONCE NEEDED FUNCTIONS ARE WRITTEN, REMOVE THIS COMMENT XXX */
    /* XXX AND THE FOLLOWING RETURN STATEMENT.                    XXX */
    /*----------------------------------------------------------------*/
    return;             /* XXX REMOVE ME LATER XXX */

    nfinished = 0;
    ready_head = ready_tail = -1;       /* ready list is empty */

    while (nfinished < num_proc) {      /* while some still not done */
    nevents++;

    /* check for a running process finishing (step 3) */
    run_proc_fin();

    /* check for a blocked process completing I/O (step 4) */
    io_completed();

    /* check for a NotYetSubmitted process being submitted (step 5) */
    do_submit();

    /* check for a running process completing its CPU cycle (step 6) */
    cpu_cycle_done();

    /* check for a running process' quantum expiring (step 7) */
    quantum_runout();

    /* fill up the idle CPUs (stp 8) */
    schedule_cpus();

    /* check for any preemption (step 9) */
    do_preempt();

    /* advance the simulation time to the next event time (step 10) */
    set_next_event_time();
    }
}

/*-------------------------------------------------*/
/* Display the heading for the current input case. */
/*-------------------------------------------------*/
void display_heading(void)
{
    int i, k;

    if (casenum > 1)            /* blank line to separate cases */
    putchar('\n');
    printf("Simulation # %d\n", casenum);
    printf("--------------");
    k = 10;             /* extra hyphen(s), if needed */
    while (k <= casenum) {
    putchar('-');
    k *= 10;
    }
    putchar('\n');
    printf("Input:\n");         /* display the input data */
    printf("      %d CPU%s, ", num_cpu, num_cpu > 1 ? "s" : "");
    printf("%d process%s, quantum size = %d\n", 
    num_proc, num_proc > 1 ? "es" : "", quantum);
    for (i=0;i<num_proc;i++) {
    printf("      PID %d, prio = %d, submit = %d, totCPU = %d,"
           " CPU = %d, I/O = %d\n",
        proc[i].pid, 
            proc[i].prio,
            proc[i].t_submit,
            proc[i].tot_cpu_req,
            proc[i].cpu_cycle,
            proc[i].io_cycle);
    }
}

/*--------------------------------------*/
/* Display the results of a simulation. */
/*--------------------------------------*/
void display_results(void)
{
    int i;
    int totturn;            /* total turnaround time */
    int totidle, avgidle;

    /*-------------------------------------------------------------------*/
    /* XXX REMOVE THE t = 1; ASSIGNMENT FROM THE TEMPLATE WHEN DONE. XXX */
    /*-------------------------------------------------------------------*/
    t = 1;          /* XXX REMOVE ME LATER XXX */

    printf("Output:\n");
    totturn = 0;
    for (i=0;i<num_proc;i++) {
    printf("      PID %d completed execution at %d, "
        "turnaround time = %d\n",
        proc[i].pid,
        proc[i].t_finish,
        proc[i].t_finish - proc[i].t_submit);
    totturn += (proc[i].t_finish - proc[i].t_submit);
    }
    totidle = 0;
    for (i=0;i<num_cpu;i++)
    totidle += cpu[i].idle;
    avgidle = totidle / num_cpu;
    printf("      Average CPU idle time = %d (%d%%)\n",
    avgidle,
    avgidle * 100 / t);
    printf("      Average process turnaround time = %d\n",
    totturn / num_proc);
}

/*------------------------------------------------------------*/
/* Main: get data, display heading, simulate, display results */
/*------------------------------------------------------------*/
int main(int argc, char *argv[])
{
    for (casenum=1;;casenum++) {
    if (!getdata())
        break;
    display_heading();
    simulate();
    display_results();
    }
}

