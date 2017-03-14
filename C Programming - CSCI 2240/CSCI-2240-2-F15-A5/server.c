/*
Name        : Caleb Miller 
Class       : 2240-002
Program #   : Assignment 5 - Database Server (server.c)
Due Date    : December 2, 2015

Honor Pledge: On my honor as a student of the University
              of Nebraska at Omaha, I have neither given nor received
              unauthorized help on this homework assignment.

NAME        : Caleb Miller
NUID        : 84947616
EMAIL       : calebmiller@unomaha.edu

Partners    : none

Description : This file is the server for the assignment. It reads 
              commands sent over from the client, and performs various
              actions. It controls the linked list structures that hold
              the student records, and also controls the database file
              to make the student records accessible after the server
              and client are terminated and run later.
*/


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h> 
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#define PORT_NUM 20720

void error(char *msg)
{
    perror(msg);
    exit(1);
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

void linked_insert(Node **nodePtr, Student value, int type);
int linked_delete(Node **nodePtr, int match);
void write_database(Node *nodePtr);
int load_database(Node **sidList,Node **lnameList, Node **fnameList, Node **gpaList);

int main(int argc, char *argv[])
{
    /* DECLARE VARIABLES */
    Node *fnameList = NULL, *lnameList = NULL, *gpaList = NULL, *sidList = NULL, *sendnode = NULL;
    Student SREC;
    int portno = PORT_NUM, sockfd = 0, newsockfd = 0, end = 0, x = 0, count = 0, cmd = 0, delSID = 0, delsuc = 0;
    socklen_t clilen;
    char buffer[256];
    struct sockaddr_in serv_addr, cli_addr;

    /* LOAD DATABASE FROM FILE "database" INTO LINKED LISTS */
    count = load_database(&sidList,&lnameList,&fnameList,&gpaList);

    /* CODE GIVEN TO US FOR SERVER */
    /*STEP 1********************************************/
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd < 0)
        error("ERROR opening socket");
   /*STEP 2*********************************************/
    memset((char *) &serv_addr, 0, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = INADDR_ANY;
    serv_addr.sin_port = htons(portno);
   /*STEP 3*********************************************/
    if (bind(sockfd, (struct sockaddr *) &serv_addr,
                sizeof(serv_addr)) < 0) 
        error("ERROR on binding");  
   /*STEP 4*********************************************/
    listen(sockfd,5);
   /*STEP 5*********************************************/
    clilen = sizeof(cli_addr);
    newsockfd = accept(sockfd, 
            (struct sockaddr *) &cli_addr, 
            &clilen);
    if (newsockfd < 0) 
        error("ERROR on accept");
    memset(buffer, 0, 256);
   
    /* BEGIN COMMUNICATION */
    do{
        read(newsockfd,&cmd,sizeof(int*)); /* read command from client */
        
        switch(cmd) {
            case 1: /* command "get lname" */
                write(newsockfd,&count,sizeof(int*)); /* tell client number of records to send over */
                sendnode = lnameList;
                for(x=0;x<count;x++) {
                    write(newsockfd,&(sendnode -> data),sizeof(Student)); /* send student record to client */
                    sendnode = sendnode -> next; /* advance pointer to next student record */
                }
                break;
            case 2: /* command "get fname" */
                write(newsockfd,&count,sizeof(int*)); /* tell client number of records to send over */
                sendnode = fnameList;
                for(x=0;x<count;x++) {
                    write(newsockfd,&(sendnode -> data),sizeof(Student)); /* send student record to client */
                    sendnode = sendnode -> next; /* advance pointer to next student record */
                }
                break;
            case 3: /* command "get SID" */
                write(newsockfd,&count,sizeof(int*)); /* tell client number of records to send over */
                sendnode = sidList;
                for(x=0;x<count;x++) {
                    write(newsockfd,&(sendnode -> data),sizeof(Student)); /* send student record to client */
                    sendnode = sendnode -> next; /* advance pointer to next student record */
                }
                break;
            case 4: /* command "get GPA" */
                write(newsockfd,&count,sizeof(int*)); /* tell client number of records to send over */
                sendnode = gpaList;
                for(x=0;x<count;x++) {
                    write(newsockfd,&(sendnode -> data),sizeof(Student)); /* send student record to client */
                    sendnode = sendnode -> next; /* advance pointer to next student record */
                }
                break;
            case 5: /* command "put [lname,fname,initial,SID,GPA]" */
                count++;
                write(newsockfd,&cmd,sizeof(int*));
                read(newsockfd,&SREC,sizeof(SREC));

                /* insert student record to linked lists */
                linked_insert(&sidList,SREC,1);
                linked_insert(&lnameList,SREC,2);
                linked_insert(&fnameList,SREC,3);
                linked_insert(&gpaList,SREC,4);
                break;
            case 6: /* command "delete [SID]" */
                write(newsockfd,&cmd,sizeof(int*));
                read(newsockfd,&delSID,sizeof(int*));
                delsuc=0;
                if(count) { /* make sure list is not empty */
                    /* delete student from the lists */
                    if(linked_delete(&sidList,delSID)&&linked_delete(&lnameList,delSID)&&linked_delete(&fnameList,delSID)&& linked_delete(&gpaList,delSID)) {
                    delsuc=1;
                    count--;
                    }
                    else
                        delsuc=0;
                }
                write(newsockfd,&delsuc,sizeof(int*));
                read(newsockfd,&delsuc,sizeof(int*));
                break;
            case 7: /* command "stop" */
                end=1;
                write_database(sidList); /* save linked list to "database" file */
                write(newsockfd,&end,sizeof(int*));
                break;
            default:
                break;
        }

        read(newsockfd,&cmd,sizeof(int*));

    } while(end==0);

    close(newsockfd); /* close socket */
    
    return 0;
} 

/*  
    Method Name      : load_database
    Parameters       : every linked list to be updated
    Return value(s)  : int c -- size of the linked lists
    Partners         : none
    Description      : this method reads all the student records from 
                       the file called "database" and stores them in
                       all the linked lists.
*/
int load_database(Node **sidList,Node **lnameList, Node **fnameList, Node **gpaList) {
    int c=0;
    Student SREC;
    FILE *dbase = NULL;
    dbase = fopen ("database","rb");
    if(dbase == NULL){
        return 0;
    }
    fread(&SREC,sizeof(Student),1,dbase);
    while (!feof(dbase))
    {
        linked_insert(sidList,SREC,1);
        linked_insert(lnameList,SREC,2);
        linked_insert(fnameList,SREC,3);
        linked_insert(gpaList,SREC,4);
        c++;
        fread(&SREC,sizeof(Student),1,dbase);
    }
    fclose(dbase);
    return c;
    
}

/*  
    Method Name      : write_database
    Parameters       : Node *list -- a pointer to the first node in the list
                       whose student records will be written to the database
    Return value(s)  : void
    Partners         : none
    Description      : this method writes all the student records from the 
                       linked list passed in to the file called "database" 
*/
void write_database (Node *list) {
    FILE *dbase = NULL;
    Node *nodePtr = list; 
    dbase = fopen("database","wb");
    if(dbase==NULL)
        error("Error opening file");

    if(nodePtr != NULL) {
        while(nodePtr!= NULL)
        {
            fwrite(&nodePtr -> data,sizeof(nodePtr -> data),1,dbase);
            nodePtr = nodePtr -> next;
        }
    }
}

/*  
    Method Name      : linked_insert 
    Parameters       : Node **nodePtr -- the list for the student record to be
                                         inserted into
                       Student value -- the student record to be inserted
                       int type -- changes what the comparison criteria is 
                                   based on which list the student record will
                                   be inserted into
    Return value(s)  : void
    Partners         : none
    Description      : this method inserts the given student record into the 
                       given linked list 
*/
void linked_insert (Node **nodePtr, Student value, int type) {
    Node *newNode;
    Node *previous;
    Node *current;
    newNode = (Node*)malloc(sizeof(Node));
    switch(type){
        case 1:
            if(newNode!=NULL)
            {   
                newNode -> data = value;
                newNode -> next = NULL;

                previous = NULL;
                current = *nodePtr;
        
                while(current!=NULL && value.SID > current->data.SID)
                {
                    previous = current;
                    current = current -> next;
                }
                if(previous == NULL)
                {
                    newNode -> next = *nodePtr;
                    *nodePtr = newNode;
                }
                else
                {
                    previous -> next = newNode;
                    newNode -> next = current;
                }
            }
            break;
        case 2:
            if(newNode!=NULL)
            {
                newNode -> data = value;
                newNode -> next = NULL;

                previous = NULL;
                current = *nodePtr;

                while(current!=NULL && strcmp(value.lname, current->data.lname)>0)
                {
                    previous = current;
                    current = current -> next;
                    puts("in while loop\n");
                }
                if(previous == NULL)
                {
                    puts("prev == null, in if statement\n");
                    newNode -> next = *nodePtr;
                    *nodePtr = newNode;
                }
                else
                {
                    previous -> next = newNode;
                    newNode -> next = current;
                }
            }
            break;
        case 3:
            if(newNode!=NULL)
            {
                newNode -> data = value;
                newNode -> next = NULL;

                previous = NULL;
                current = *nodePtr;

                while(current!=NULL && strcmp(value.fname, current->data.fname)>0)
                {
                    previous = current;
                    current = current -> next;
                }
                if(previous == NULL)
                {
                    newNode -> next = *nodePtr;
                    *nodePtr = newNode;
                }
                else
                {
                    previous -> next = newNode;
                    newNode -> next = current;
                }
            }
            break;
        case 4:
            if(newNode!=NULL)
            {
                newNode -> data = value;
                newNode -> next = NULL;

                previous = NULL;
                current = *nodePtr;

                while(current!=NULL && value.GPA < current->data.GPA)
                {
                    previous = current;
                    current = current -> next;
                }
                if(previous == NULL)
                {
                    newNode -> next = *nodePtr;
                    *nodePtr = newNode;
                }
                else
                {
                    previous -> next = newNode;
                    newNode -> next = current;
                }
            }
            break;
    }
}

/*  
    Method Name      : linked_delete
    Parameters       : Node **nodePtr -- the linked list to have a record removed from
                       int match -- the SID to be removed from the list
    Return value(s)  : int -- 0 or 1 whether or not the deletion was successful
    Partners         : none
    Description      : this method removes a record from the linked list based on the
                       Student ID passed to it 
*/
int linked_delete(Node **nodePtr, int match)
{
    Node *previous;
    Node *current;
    Node *temp;
    if(match == (*nodePtr) -> data.SID) {
        temp = *nodePtr;
        *nodePtr = (*nodePtr) -> next;
        free(temp);
        return 1;
    }
    else
    {
        previous = *nodePtr;
        current = (*nodePtr) -> next;
        while (current != NULL && current -> data.SID != match)
        {
            previous = current;
            current = current -> next;
        }
        if(current != NULL)
        {
            previous -> next = current -> next;
            free(current);
            return 1;
        }
    }

    return 0;
}

