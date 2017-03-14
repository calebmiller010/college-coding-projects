/*
    Name        : Caleb Miller
    Class       : 2240-002
    Program #   : 3 - Virtual Computer
    Due Date    : 21 October 2015

    Honor Pledge:  On my honor as a student of the University
                   of Nebraska at Omaha, I have neither given nor received
                   unauthorized help on this homework assignment.

    NAME: Caleb Miller
    NUID: 84947616
    EMAIL: calebmiller@unomaha.edu

    Partners:   NONE

    Description: This program emulates a very basic computer with limited
                 commands and memory. First, we compile the program by
                 storing all of the commands into the blocks of memory of
                 our computer. Then we run the program by pulling out what
                 is stored in memory, and perform operation based on what
                 was pulled out of memory.


***** THIS IS INFORMATION ABOUT THE VIRTUAL COMPUTER'S BASIC LANGUAGE *****

- Each command starts with the block of memory the command is to be stored in,
followed by the command itself (must be all upper-case letters), followed by
a number that relates to the command, in most cases, a command that points
to another block of memory.

COMMAND   | MEMORY VALUE  | DESCRIPTION
------------------------------------------------
READ        10              Retrieves a value from the user and places it in the given address
WRIT        11              Outputs a word from the given address to the terminal
PRNT        12              Outputs a string starting at the given address, will continue outputting
                            consecutive words as strings until NULL is reached
LOAD        20              Load a word from the given memory address into the accumulator
STOR        21              Store the word in the accumulator into the given address
SET         22              Stores the given word into the preceding address (Note: The operation code
                            value of 22 will never appear in a compile program, is only included for completeness)
ADD         30              Adds the word at the given memory address to the accumulator
SUB         31              Subtracts the word at the given memory address to the accumulator
DIV         32              Divides the word at the given memory address to the accumulator
MULT        33              Multiplies the word at the given memory address to the accumulator
MOD         34              Mods the word at the given memory address to the accumulator
BRAN        40              Execution jumps to the given memory location
BRNG        41              Execution jumps to the given memory location if the accumulator is negative
BRZR        42              Execution jumps to the given memory location if the accumulator is zero
HALT        99              Terminates execution, no address given, value of 99 is standard, also prints
                            out the state of memory in a tabular format

EXAMPLE:
Command '00 READ 10' would store input given from the user into memory block 10. The command itself is
                     stored in memory block 00.

*/

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
bool compile(int* memory, int* accumulator, int* instructionCounter, int* instructionRegister, int* operationCode, int* operand, char* argv[10]);
void execute(int* memory, int* accumulator, int* instructionCounter, int* instructionRegister, int* operationCode, int* operand);
void printtable(int* memory, int* accumulator, int* instructionCounter, int* instructionRegister, int* operationCode, int* operand);

int main(int argc, char* argv[10]) {
    /* Declare variables for Virtual Computer */
    signed int accumulator=0;
    signed int instructionCounter=0;
    signed int instructionRegister=0;
    signed int operationCode=0;
    signed int operand=0;
    int* memory;

    memory=(int*)calloc(100,sizeof(int)); /* allocate new memory */

    if(memory==NULL) /* Make sure we allocated our memory successfully */
        return 1;

    if(argc==2) {
        if(compile(memory,&accumulator,&instructionCounter,&instructionRegister,&operationCode,&operand,argv)) {
/* If compilation is successful, then execute the function, and print out the table after execute is performed */
            execute(memory,&accumulator,&instructionCounter,&instructionRegister,&operationCode,&operand);
            printtable(memory,&accumulator,&instructionCounter,&instructionRegister,&operationCode,&operand);
        }
    }
    else 
        printf("*** ERROR: Must enter file to run with the program ***\n");

    free(memory); /* Free the memory */
    return 0;

}

/*
Method Name      : compile
Parameters       : int* memory
                   int* accumulator
                   int* instructionCounter
                   int* instructionRegister
                   int* operationCode
                   int* operand
                   int* argv[10]
Return value(s)  : boolean - whether the compilation was successful or not
Partners         : none
Description      : This method reads the file from the user, getting command after command, and storing
                   it into the correct place in Virtual Computer's memory. It will return true if the
                   compilation is successful, but if not, will print out the compilation error, and return
                   false, thus ending the program.
*/
bool compile(int* memory, int* accumulator, int* instructionCounter, int* instructionRegister, int* operationCode, int* operand, char* argv[10]) {

    FILE *fPtr = NULL;
    char buf[30]={0};
    fPtr = fopen(argv[1],"r");

    while(fgets(buf,sizeof buf,fPtr)!=NULL) { /* get the line, and store it in buf, 1 line at a time */

        *accumulator=0;
        
        /* Check for extra or less spaces (allowed on the end of instruction, so why it only counts up to 8) */
        for((*instructionRegister)=0;(*instructionRegister)<8;(*instructionRegister)++) {
            if(buf[*instructionRegister]==' ')
                (*accumulator)++;
        }

        sscanf(buf,"%d %s %d", operationCode, buf, operand); /* get operationCode, operation command (by re-using buf),
                                                                and operand from the line */
        if((*accumulator)!=2) {
            printf("***Compilation error in file '%s': invalid spacing at address %d***\n",argv[1],*operationCode);
            return false;
        }

        if(*operationCode<0||*operationCode>99) {
            printf("***Compilation error in file '%s': invalid address %d***\n",argv[1],*operationCode);
            return false;
        }

        if(strcmp(buf,"READ")==0) {
            if(*operand/-1>0) /* account for negative operands; although the only command in which
                                 negatives are useable is SET, we still will store it as a negative
                                 number in memory, and will be dealt with in the execute function,
                                 most likely as a segmentation fault error */
                memory[*operationCode]=-1000+*operand;
            else
                memory[*operationCode]=1000+*operand;
        }
        else if(strcmp(buf,"WRIT")==0) {
            if(*operand/-1>0)
                memory[*operationCode]=-1100+*operand;
            else
                memory[*operationCode]=1100+*operand;
        }
        else if(strcmp(buf,"PRNT")==0) {
            if(*operand/-1>0)
                memory[*operationCode]=-1200+*operand;
            else
                memory[*operationCode]=1200+*operand;
        }
        else if(strcmp(buf,"LOAD")==0) {
            if(*operand/-1>0)
                memory[*operationCode]=-2000+*operand;
            else
                memory[*operationCode]=2000+*operand;
        }
        else if(strcmp(buf,"STOR")==0) {
            if(*operand/-1>0)
                memory[*operationCode]=-2100+*operand;
            else
                memory[*operationCode]=2100+*operand;
        }
        else if(strcmp(buf,"SET")==0)
            memory[*operationCode]=(*operand);
        else if(strcmp(buf,"ADD")==0) {
            if(*operand/-1>0)
                memory[*operationCode]=-3000+*operand;
            else
                memory[*operationCode]=3000+*operand;
        }
        else if(strcmp(buf,"SUB")==0) {   if(*operand/-1>0)
                memory[*operationCode]=-3100+*operand;
            else
                memory[*operationCode]=3100+*operand;
        }
        else if(strcmp(buf,"DIV")==0) {
            if(*operand/-1>0)
                memory[*operationCode]=-3200+*operand;
            else
                memory[*operationCode]=3200+*operand;
        }
        else if(strcmp(buf,"MULT")==0) {
            if(*operand/-1>0)
                memory[*operationCode]=-3300+*operand;
            else
                memory[*operationCode]=3300+*operand;
        }
        else if(strcmp(buf,"MOD")==0) {
            if(*operand/-1>0)
                memory[*operationCode]=-3400+*operand;
            else
                memory[*operationCode]=3400+*operand;
        }
        else if(strcmp(buf,"BRAN")==0) {
            if(*operand/-1>0)
                memory[*operationCode]=-4000+*operand;
            else
                memory[*operationCode]=4000+*operand;
        }
        else if(strcmp(buf,"BRNG")==0) {
            if(*operand/-1>0)
                memory[*operationCode]=-4100+*operand;
            else
                memory[*operationCode]=4100+*operand;
        }
        else if(strcmp(buf,"BRZR")==0) {
            if(*operand/-1>0)
                memory[*operationCode]=-4200+*operand;
            else
                memory[*operationCode]=4200+*operand;
        }
        else if(strcmp(buf,"HALT")==0) {
            memory[*operationCode]=9999;
            *instructionCounter=1;/* mark a HALT command was found */
        }
        else {
            printf("***Compilation error in file '%s': invalid instruction or format '%s' at address %d***\n",argv[1],buf,*operationCode);
            return false;
        }

        if(memory[*operationCode]>9999) {
            printf("***Compilation error in file '%s': cannot store %d at address %d***\n",argv[1],memory[*operationCode],*operationCode);
            return false;
        }

    } /* end of while loop */

    if(*instructionCounter!=1) { /* make sure there's a HALT command */
        printf("***Compilation error in file '%s': no HALT command***\n",argv[1]);
        return false;
    }

    stdin = fopen("/dev/tty", "r");
    fclose(fPtr); /* close pointer to file */
    return true;

}

/*
Method Name      : execute
Parameters       : int* memory
                   int* accumulator
                   int* instructionCounter
                   int* instructionRegister
                   int* operationCode
                   int* operand
Return value(s)  : void
Partners         : none
Description      : This method performs the actions based on what was stored in memory. It goes through and reads
                   each command stored in the given memory block, and performs an action based on that. This is the
                   section of the computer that will read from the user, print out on the console, and actually
                   perform the user's commands.
*/
void execute(int* memory, int* accumulator, int* instructionCounter, int* instructionRegister, int* operationCode, int* operand) {

    /* reset the registers after their use in compiling the code*/
    *accumulator=0;
    *operationCode=0;
    *instructionCounter=0;
    *instructionRegister=0;
    *operand=0;

    /* go through every piece of memory, performing each operation given*/
    for(*instructionCounter=0;*instructionCounter<100;(*instructionCounter)++) {

        *instructionRegister=memory[*instructionCounter]; /* Pull out what is stored in that memory block */
        *operationCode=abs(*instructionRegister/100); /* First 2 numbers = operationCode */
        *operand=*instructionRegister%100; /* Last 2 numbers = operand */

        if(*operand<0||*operand>99) { /* Accessing memory outside of our allocated memory */
            printf("\n***Segmentation fault: no access to address  %d ***\n",*operand);
            return;
        }

        switch(*operationCode) { /* perform operation based on operationCode */
            case 10:if(scanf("%d",&memory[*operand]) == 1) {
                        if(memory[*operand]<-9999||memory[*operand]>9999) {
                            printf("\n***Runtime Error: input %d out of range***\n",*&memory[*operand]);
                            return;
                        }
                    }
                    else {
                        printf("\n***Runtime Error: mismatch of input***\n");
                        return;
                    }
                    break;
            case 11:printf("%d\n",memory[*operand]);
                    break;
            case 12:while((memory[*operand]/100)!=0||(memory[*operand]%100)!=0) {
                        if(memory[*operand]/100==00||memory[*operand]/100==10||(memory[*operand]/100<=90&&memory[*operand]/100>=65))
                            printf("%c",memory[*operand]/100);
                        else {
                            printf("\n***Runtime Error: ASCII value '%d' not recognized at address %02d ***\n",memory[*operand]/100,*operand);
                            return;
                        }
                        if(memory[*operand]%100==00||memory[*operand]%100==10||(memory[*operand]%100<=90&&memory[*operand]%100>=65))
                            printf("%c",memory[*operand]%100);
                        else {
                            printf("\n***Runtime Error: ASCII value '%d' not recognized at address %02d ***\n",memory[*operand]%100,*operand);
                            return;
                        }
                        (*operand)++;
                    }
                    break;
            case 20:*accumulator=memory[*operand];
                    break;
            case 21:memory[*operand]=*accumulator;
                    break;
            case 30:*accumulator=*accumulator+memory[*operand];
                    break;
            case 31:*accumulator=*accumulator-memory[*operand];
                    break;
            case 32:if(memory[*operand]==0) {
                        printf("\n***Runtime Error: Cannot divide by 0. Address: %02d ***\n",*instructionCounter);
                        return;
                    }
                    else
                        *accumulator=*accumulator/memory[*operand];
                    break;
            case 33:*accumulator=*accumulator*memory[*operand];
                    break;
            case 34:*accumulator=*accumulator%memory[*operand];
                    break;
            case 40:*instructionCounter=*operand-1;
                    break;
            case 41:if(*accumulator<0)
                    *instructionCounter=*operand-1;
                    break;
            case 42:if(*accumulator==0)
                    *instructionCounter=*operand-1;
                    break;
            case 99:return;
                    break;
            default:printf("\n***Runtime Error: Command %d not recognized at address %d ***\n", *operationCode ,*instructionCounter);
                    return;
                    break;
        }/*End of Switch Statement*/

        if(*accumulator>9999||*accumulator<-9999) {
            printf("\n***Runtime Error: accumulator out of range. Address: %d ***\n",*instructionCounter);
            return;
        }
    }/*End of 'for' loop*/

}

/*
Method Name      : printtable
Parameters       : int* memory
                   int* accumulator
                   int* instructionCounter
                   int* instructionRegister
                   int* operationCode
                   int* operand
Return value(s)  : void
Partners         : none
Description      : This method simply prints out the table of memory for the user. The method
                   isn't really necessary since it's only called once, but it just makes the code
                   in the main function look a little cleaner.
*/
void printtable(int* memory, int* accumulator, int* instructionCounter, int* instructionRegister, int* operationCode, int* operand) 
{
    /*print out memory information*/
    printf("\nREGISTERS:\naccumulator\t\t %+05d\ninstructionCounter\t    %02d\ninstructionRegister\t %+05d\noperationCode\t\t    %02d\noperand\t\t\t    %02d\nMEMORY:\n", *accumulator,*instructionCounter,*instructionRegister,*operationCode,*operand);
    printf("       0     1     2     3     4     5     6     7     8     9\n");
    for((*operand)=0;(*operand)<10;(*operand)++) {
        printf("%d0 %+05d %+05d %+05d %+05d %+05d %+05d %+05d %+05d %+05d %+05d\n",*operand,memory[*operand*10],memory[*operand*10+1],memory[*operand*10+2],memory[*operand*10+3],memory[*operand*10+4],memory[*operand*10+5],memory[*operand*10+6],memory[*operand*10+7],memory[*operand*10+8],memory[*operand*10+9]);
    }
    printf("\n");
    /* memory printed */
}

