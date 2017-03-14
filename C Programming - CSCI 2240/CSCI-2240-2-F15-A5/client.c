/*
Name        : Caleb Miller
Class       : 2240-002
Program #   : Assignment 5 - Database Server (client.c)
Due Date    : December 2, 2015

Honor Pledge: On my honor as a student of the University
              of Nebraska at Omaha, I have neither given nor received
              unauthorized help on this homework assignment.

NAME        : Caleb Miller
NUID        : 84947616
EMAIL       : calebmiller@unomaha.edu

Partners    : none

Description : This file is the client for the assignment. It receives
              commands from the user, and sends the commands to the 
              server. This is where the student database is printed
              out to the user when requested. 
*/


#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <sys/types.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h> 
#include <unistd.h>
#define PORT_NUM 20720

void error(char *msg)
{
    perror(msg);
    exit(0);
}

typedef struct Student {
    char lname[10],initial,fname[10];
    unsigned long SID;
    float GPA;
} Student;

typedef struct node {
    Student data;
    struct node *next;
} Node;

int main(int argc, char *argv[])
{
    /* DECLARE VARIABLES */
    int portno = PORT_NUM, sockfd = 0, cmd = 0, end = 0, count = 0, x = 0, delSID = 0, delsuc = 0;
    struct sockaddr_in serv_addr;
    struct hostent *server;
    Student SREC;
    char buffer[256], t1[20], t2[20], t3[20], t4[20], t5[20], t[20];
    char* bufptr = NULL;

    /* CODE GIVEN TO US TO SET UP OUR CLIENT */
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd < 0) 
        error("ERROR opening socket");
    server = gethostbyname("loki");
    if (server == NULL) {
        fprintf(stderr,"ERROR, no such host\n");
        exit(0);
    }
    memset((char *) &serv_addr, 0, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    memcpy((char *)server->h_addr_list, 
            (char *)&serv_addr.sin_addr.s_addr,
            server->h_length);
    serv_addr.sin_port = htons(portno);
    if (connect(sockfd,(struct sockaddr*) &serv_addr,sizeof(serv_addr)) < 0) 
        error("ERROR connecting");

    /* START COMMUNICATION */
    do{
        /* reset necessary variables */
        cmd=0;
        memset(&SREC,0,sizeof(SREC));
        memset(buffer, 0, sizeof(buffer));
        memset(t1, 0, sizeof(t1));
        
        /* store command into buffer */
        printf("Please enter the command: ");
        fgets(buffer,255,stdin);
        bufptr=&buffer[0];
        
        /* perform action based off command */
        if(strncmp(buffer,"get lname",9)==0) { /* get lname */
            cmd=1;
            write(sockfd,&cmd,sizeof(int*)); /* send command number over */
            read(sockfd,&count,sizeof(int*)); /* receive number of students to be received */
            printf("\n");
            printf("| SID   | Lname     | Fname     | M | GPA  |\n");
            printf("+-------+-----------+-----------+---+------+\n");
            for(x=0;x<count;x++) {
                read(sockfd,&SREC,sizeof(Student)); /* receive student info for printing */
                printf("| %5.5lu | %-9s | %-9s | %c | %.2f |\n",SREC.SID%100000, SREC.lname,SREC.fname,SREC.initial,SREC.GPA);
            }        
            puts("+-------+-----------+-----------+---+------+\n\n");
        }
        else if(strncmp(buffer,"get fname",9)==0) { /* get fname */
            cmd=2;
            write(sockfd,&cmd,sizeof(int*)); /* send command number over */
            read(sockfd,&count,sizeof(int*)); /* receive number of students to be received */
            printf("\n");
            printf("| SID   | Lname     | Fname     | M | GPA  |\n");
            printf("+-------+-----------+-----------+---+------+\n");
            for(x=0;x<count;x++) {
                read(sockfd,&SREC,sizeof(Student)); /* receive student info for printing */
                printf("| %5.5lu | %-9s | %-9s | %c | %.2f |\n",SREC.SID%100000, SREC.lname,SREC.fname,SREC.initial,SREC.GPA);
            }        
            puts("+-------+-----------+-----------+---+------+\n\n");
        }
        else if(strncmp(buffer,"get SID",7)==0) { /* get SID */
            cmd=3;
            write(sockfd,&cmd,sizeof(int*)); /* send command number over */
            read(sockfd,&count,sizeof(int*)); /* receive number of students to be received */
            printf("\n");
            printf("| SID   | Lname     | Fname     | M | GPA  |\n");
            printf("+-------+-----------+-----------+---+------+\n");
            for(x=0;x<count;x++) {
                read(sockfd,&SREC,sizeof(Student)); /* receive student info for printing */
                printf("| %5.5lu | %-9s | %-9s | %c | %.2f |\n",SREC.SID%100000, SREC.lname,SREC.fname,SREC.initial,SREC.GPA);
            }        
            puts("+-------+-----------+-----------+---+------+\n\n");
        }
        else if(strncmp(buffer,"get GPA",7)==0) { /* get GPA */
            cmd=4;
            write(sockfd,&cmd,sizeof(int*)); /* send command number over */
            read(sockfd,&count,sizeof(int*)); /* receive number of students to be received */
            printf("\n");
            printf("| SID   | Lname     | Fname     | M | GPA  |\n");
            printf("+-------+-----------+-----------+---+------+\n");
            for(x=0;x<count;x++) {
                read(sockfd,&SREC,sizeof(Student)); /* receive student info for printing */
                printf("| %5.5lu | %-9s | %-9s | %c | %.2f |\n",SREC.SID%100000, SREC.lname,SREC.fname,SREC.initial,SREC.GPA);
            }        
            puts("+-------+-----------+-----------+---+------+\n\n");
        }
        else if(strncmp(buffer,"put",3)==0) {
            cmd=5;
            write(sockfd,&cmd,sizeof(int*));

            /* tokenize buffer into SREC structure */
            sscanf(buffer,"%s %[^','],%[^','],%[^','],%[^','],%s",t,t1,t2,t3,t4,t5);
            strncpy(SREC.lname,t1,9);
            strncpy(SREC.fname,t2,9);
            SREC.initial=t3[0];
            SREC.SID=strtoul(t4,&bufptr,10);
            SREC.GPA=atof(t5);

            read(sockfd,&cmd,sizeof(int*));
            write(sockfd,&SREC,sizeof(Student)); /* send structure over */
        }
        else if(strncmp(buffer,"delete",6)==0) {
            cmd=6;
            write(sockfd,&cmd,sizeof(int*)); /* send command number over */
            sscanf(buffer,"%s %d",t1,&delSID); /* scan SID number to delete */
            read(sockfd,&cmd,sizeof(int*));
            write(sockfd,&delSID,sizeof(int*)); /* send over SID number to delete */
            read(sockfd,&delsuc,sizeof(int*)); /* receive whether deletion was successful or not */
            if(delsuc) {
                count--;
                printf("\nSID %d SUCCESSFULLY DELETED\n\n\n", delSID);
            }
            else
                printf("\nSID %d DOES NOT EXIST, NOTHING DELETED\n\n\n", delSID);
            write(sockfd,&delsuc,sizeof(int*));
        }
        else if(strncmp(buffer,"stop",4)==0) {
            cmd=7;
            write(sockfd,&cmd,sizeof(int*)); /* send stop command */
        }
        else
        {
            cmd=8;
            puts("UNRECOGNIZED COMMAND");
            write(sockfd,&cmd,sizeof(int*));
        }


        if(cmd==7) {
            read(sockfd,&end,sizeof(int*)); /* read back from server to stop loop */
            if(end==1)
                break;
        }
        write(sockfd,&cmd,sizeof(int*));
    
    } while(end==0);
    
    close(sockfd); /* close socket */

    return 0;

}

