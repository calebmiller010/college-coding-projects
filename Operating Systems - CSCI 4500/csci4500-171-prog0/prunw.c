/*-----------------------------------------------------------------*/
/* prunw                                                           */
/* Create a process to run a program, wait for it to terminate,    */
/* then report its completion status.                              */
/* Last modification: January 18, 2016.                            */
/*-----------------------------------------------------------------*/
/* The program to be executed by the new process and any arguments */
/* are provided as the arguments on the prunw command line.        */
/* For example, to run the program in the file /usr/bin/who with   */
/* the arguments "am" and "i" you would use this command line:     */
/*								   */
/*	./prunw   /usr/bin/who  am  i				   */
/*								   */
/* It is common to use "./prunw" to invoke this program since it   */
/* is not normally found in any of the directories identified by   */
/* the PATH environment variable. Although the execvp system call  */
/* used by this program will search for the file to be executed in */
/* the same manner as the shell, it is important to recognize that */
/* the file being executed is often not found in the current       */
/* working directory.						   */
/*-----------------------------------------------------------------*/
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main(int argc, char *argv[])
{
    pid_t cpid;			/* PID of the new process (the child) */
    pid_t status;		/* waitpid's return value */
    int child_status;		/* exit status of the new process */

    /*----------------------------------------------------------------*/
    /* Check for too few arguments. There must be at least two, the   */
    /* filename of the prunw program and the name of the command that */
    /* is to be executed. If too few, display a brief summary of how  */
    /* the program is to be used and then terminate.                  */
    /*----------------------------------------------------------------*/
    if (argc < 2) { 
	fprintf(stderr,"Usage: [./]prunw progname [arg] ...\n");
	fprintf(stderr,"Use \"./\" before prunw if it's not in"
	    " a directory named in the PATH environment variable.\n");
	exit(1);		/* terminate */
    }

    /*---------------------------------------------------------*/
    /* Create a child process and save its process ID in cpid. */
    /*---------------------------------------------------------*/
    if ((cpid = fork()) == 0) {
	/*---------------------------------------------------------*/
	/* If fork returns 0 we're executing in the child process. */
	/*---------------------------------------------------------*/
	execvp(argv[1], argv+1);	/* execute the command */
	perror("execvp");		/* error: print a message */
	exit(2);			/* terminate */
    }

    /*----------------------------------------------*/
    /* If fork returns -1, it encountered an error. */
    /*----------------------------------------------*/
    if (cpid == -1) {
	perror("fork");		/* Display a message identifying the error. */
	exit(3);		/* and terminate */
    }

    /*-----------------------------------------------------------*/
    /* Otherwise (since cpid > 0) fork was successful. Wait for  */
    /* child process to exit. Also save the child's termination  */
    /* status in child_status.                                   */
    /*-----------------------------------------------------------*/
    /* Note that we could also have used the wait system call to */
    /* delay further execution until the child process quit. The */
    /* wait system call delays until any one of the children     */
    /* terminates, while waitpid waits for a specific child. In  */
    /* this code there is only one child, so they're equivalent. */
    /*-----------------------------------------------------------*/
    status = waitpid(cpid, &child_status, 0);	/* wait... */
    if (status == -1) {			/* if waitpid returned an error */
	perror("waitpid");		/* display an appropriate message */
	exit(4);			/* terminate ourselves */
    }

    /*-------------------------------------------------------------*/
    /* If the child exited normally (with exit or return), display */
    /* a message stating that fact and the low-order 8 bits of its */
    /* exit status. Otherwise indicate that it did not terminate   */
    /* normally. WIFEXITED is a preprocessor macro defined in the  */
    /* wait.h header file. It makes it easier to check the bits in */
    /* child_status to see if the child exited normally (that is,  */
    /* by using the exit system call or by returning from its main */
    /* function. If so, the WEXITSTATUS macro is used to extract   */
    /* the low-order 8 bits of the value specified in the exit     */
    /* system call or returned by the main function. See complete  */
    /* descriptions of these in the manual page for waitpid.       */
    /*-------------------------------------------------------------*/
    if (WIFEXITED(child_status)) {
	printf("Child process %u exited with status %d\n",
	    cpid, WEXITSTATUS(child_status));
    } else {
	printf("Child process %u did not exit normally.\n", cpid);
    }
    
    exit(0);				/* we now terminate normally */
}

/*---------------------------------------------------------------*/
/* Notes about this program                                      */
/*                                                               */
/* 1. Instead of using execve to execute the desired file (as in */
/*    prun), this program uses execvp which searches for the     */
/*    file in the directories specified in the PATH environment  */
/*    variable. Thus "./prunw echo Hello" will work correctly.   */
/*                                                               */
/* 2. The parent process also waits (using waitpid) for the      */
/*    child process to terminate normally or abnormally. It then */
/*    displays an appropriate message, and if termination was    */
/*    normal, also displays its exit or return status.           */
/*---------------------------------------------------------------*/
