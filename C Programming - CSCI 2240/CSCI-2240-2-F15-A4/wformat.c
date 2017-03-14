/*
Name        : Caleb Miller
Class       : 2240-002
Program #   : 4 - Word Format
Due Date    : Due November 11, 2015 at 11:59 PM
    
Honor Pledge:  On my honor as a student of the University
               of Nebraska at Omaha, I have neither given nor received
               unauthorized help on this homework assignment.

NAME: Caleb Miller
NUID: 84947616
EMAIL: calebmiller@unomaha.edu

Partners: none

Desc: This program is a simple word processor that formats code so the spacing
      is even between lines, placing it into a file that ends with the tag '.out'.
      It also tracks how many times each words appears in the file, printing it 
      out in a file ending with the tag '.data'.
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>

int main(int argc, char* argv[]) {
    /* Declare variables */
    FILE *fptr;
    FILE *fwrite;
    FILE *fdata;
    char word[101];
    char line[101];
    char newfile[25];
    char newdata[25];
    char *tmpword;
    int length=atoi(argv[1]);
    int y=0;
    int i=0;
    int j=0;
    int z=0;
    int x=0;
    int tmpnum=0;
    struct list{
        char *word;
        int num;
    } *wordArray;
    /*struct list *wordArray;*/
    int a=0;
    int numwords=0;
    char testwords[1000][101];
    
    /* prepare name of 2 files to write to */
    sprintf(newfile,"%s.out",argv[2]);
    sprintf(newdata,"%s.words",argv[2]);

    /* check for reasonable length */
    if(length>100||length<25) {
        puts("Length of each line must be between 25 and 100");
        return 1;
    }
    
    /* open files */
    if((fptr=fopen(argv[2],"r"))==NULL) {
        fprintf(stderr,"Error opening file\n");
        exit(1);
    }
    if((fwrite=fopen(newfile,"w"))==NULL) {
        fprintf(stderr,"Error opening file\n");
        exit(1);
    }
    if((fdata=fopen(newdata,"w"))==NULL) {
        fprintf(stderr,"Error opening file\n");
        exit(1);
    }

    /* scan file, transfer into new file (.out) */
    fscanf(fptr,"%s",word);
    memset(line,0,strlen(line));
    while(!feof(fptr)) {
        if((strlen(word)+strlen(line))<=length) 
            sprintf(line,"%s%s ",line,word);
        else {
            strncpy(line,line,strlen(line)-1);
            line[strlen(line)-1]=0;
            fprintf(fwrite,"%s\n",line);
            memset(line,'\0',strlen(line));
            sprintf(line,"%s ",word);
        }
        fscanf(fptr,"%s",word);
    }
    strncpy(line,line,strlen(line)-1);
    line[strlen(line)-1]=0;
    fprintf(fwrite,"%s\n",line);

    fclose(fptr);
    
    if((fptr=fopen(argv[2],"r"))==NULL) {
        fprintf(stderr,"Error opening file\n");
        exit(1);
    }

    fscanf(fptr,"%s",testwords[numwords]);
    while(!feof(fptr)) {
        numwords++;
        fscanf(fptr,"%s",testwords[numwords]);
    }

    /* allocate memory for our structure based on how many words */
    wordArray=malloc(sizeof(wordArray));
    if(wordArray==NULL) {
        puts("Memory not successfully allocated, exiting");
        return 0;
    }
    
    /* clear existing memory */
    for(y=0;y<numwords;y++) {
        wordArray[y].num=0;
        wordArray[y].word=" ";
    }

    /* traverse through array of words, transferring it into our structure */
    for(x=0;x<numwords;x++) {
        for (z=0;z<numwords;z++) {
            /* if match found, add one to the number associated with that word */
            if(strcmp(wordArray[z].word,testwords[x])==0) {
                (wordArray[z].num)++;
                z=numwords;
            }
            /* if we've come to the end and no match found, make a new slot
               for that word */
            else if (wordArray[z].num==0) {
                wordArray[z].word=testwords[x];
                (wordArray[z].num)=1;
                z=numwords;
            }
        }
    }
    
    /* sort to get structure in alphabetical order */
    for(i=0;(wordArray[i].num!=0);i++) {
        for(j=0;(wordArray[j].num!=0);j++) {
            if(strcasecmp(wordArray[i].word,wordArray[j].word)<0) {
                tmpword=wordArray[i].word;
                tmpnum=wordArray[i].num;
                wordArray[i].word=wordArray[j].word;
                wordArray[i].num=wordArray[j].num;
                wordArray[j].word=tmpword;
                wordArray[j].num=tmpnum;
            }
        }
    }
    
    /* print structure of wordcounts into new file (.words) */
    for(a=0;(wordArray[a].num!=0);a++) {
        if(strcmp(wordArray[a].word,"")!=0)
            fprintf(fdata,"%s - %d\n",wordArray[a].word,wordArray[a].num);
    }

    /* close file pointers */
    fclose(fptr);
    fclose(fdata);
    fclose(fwrite);

    return 0;

}
