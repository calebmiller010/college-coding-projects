/*
Name         : Caleb Miller
Class        : 2400-002
Program #    : 2
Due Date     : Wednesday, September 30, 11:59 PM

Honor Pledge : On my honor as a student of the University
               of Nebraska at Omaha, I have neither given nor received
               unauthorized help on this homework assignment.

NAME         : Caleb Miller
NUID         : 616
EMAIL        : calebmiller@unomaha.edu

Partners     : Needed some help just on converting the file we read from into
               the double array that are useable by my functions. I still wasn't
               totally sure how fgets worked, but now it's making more sense. I
               got help on that part from the Computer Science Leaning Center. 
               Other than that, I did everything else on my own.
Description  : This program reads in a word puzzle from another file, and then 
               searches for all the words, and prints out the solved word puzzle

*/

/* compile this program using 'gcc -Wall -ansi -pedantic wordsearch.c' */

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#define size 50
#define BUFFER_SIZE size*2+2
#define DEFAULT_FILE "square" /*Note: as of the time of the assignment due date and time, "square" contains the wordsearch from file "data1" in the directory. If you would like to change the wordsearch to be solved, either change "square" to the wordsearch you want, or run the wordsearch.c program with the name of the data file you want to use after the command to run it (for example, to run this program using the 'data2' file, I would run this program with 'data2' after compiling using "./a.out data2." */

char puzzle[size+1][size+1]={{0}}; /* double array of all the letters of the word search */
char words[100/*max words = 100 */][size+1]={{0}}; /* double array to contain all the words to be found in the puzzle */
char printpuzzle[size+1][size+1]={{0}}; /* double array that will contain the solved word search for the final output */


void addtopuzzle(char* word, int row, int col, int wordsize, int direction);
bool checkRestofWord(char* word, int row, int col, int wordsize, int direction);
void findWord(char* word, int ws, int dim);

int main (int argc, char* argv[] ){
    int a=0;
    int b=0;
    int c=0;
    int d=0;
    int e=0;
    int f=0;
    int g=0;
    int h=0;
    int dim=0;
    int row=0;
    int col=0;
    int wordsize=0;
    int numwords=0;
    char word[size]={0};
    char buf[BUFFER_SIZE]={0};
    FILE *fPtr = NULL;

/**** Default file 'square'. If user runs program with another file
  (for example: './a.out data2'), then it reads that file they enter in****/
    if (argc == 1){
        fPtr = fopen(DEFAULT_FILE, "r");
    }
    else {
        fPtr = fopen(argv[1], "r");
    }

    if (fPtr == NULL){
        printf("File could not be opened.\n");
        return 0;
    }

    fgets(buf,sizeof buf, fPtr);/*gets first line, used to count dimension of matrix*/
    while(buf[a]!='\n'){
        if (buf[a]!= ' ')
            dim++;
        a++;
    }

/* sets first line of puzzle[][] to the first line read */
    for (b=0;b<dim*2;b+=2)
        puzzle[0][b/2]=buf[b];

/* gets rest of puzzle[][] from lines read */
    for (row=1;row<dim;row++){ /* traverse each row */
        fgets(buf,sizeof buf, fPtr);
        for (col=0;col<dim*2;col+=2) /* get the letters and skip the spaces*/
            puzzle[row][col/2]=buf[col];
    }

/* after getting puzzle, we must get all of the words below it*/
    while(fgets(buf,sizeof buf, fPtr) != NULL ){
        for (c=0;c<size;c++)
            if(buf[c]=='\n')
                c=size;
            else
                words[numwords][c]=buf[c];
        numwords++;
    }

/* Solve the maze; to start, we update the word to get the next word in the
   list, and find the length of the word. Then we call the findWord function.*/
    for (d=0;d<numwords;d++){/* runs findWord algorithm for each word in the 
                                 data file*/
        for(e=0;e<size;e++) /* reset from the previous word */
            word[e]='\0';
        for(f=0;f<size;f++){
            if(words[d][f]=='\0')
                break;
            else{
                word[f]=words[d][f]; /* pass from the array of words to the word 
                                        that'll be used*/
                wordsize++;
            }
        }
        findWord(word,wordsize,dim);
        wordsize=0;
    }
    
/* Print out the solved puzzle, stored in printpuzzle[][] */
/* Note: h goes to dim + 1 because in the assignment sheet, it says we are to
   have a space following each row of the column, then the new line. Because 
   dim + 1 is '\0', it will become a blank space, and therefore adhere to the
   proper formatting guidelines.*/
    for(g=0;g<dim;g++){
        for(h=0;h<dim+1;h++){ 
            if (printpuzzle[g][h]=='\0')
                printpuzzle[g][h]=' ';
            printf("%c ",printpuzzle[g][h]);
        }
        printf("\n");
    }

    fclose(fPtr);

    return 0;

}



/*
Function Name    : findWord
Parameters       : char* word - the word that is to be searched for
                   int ws - the size of the word 
                   int dim - the dimensions of the matrix 
Return value(s)  : void
Partners         : none
Description      : This is the algorithm used to find the word. In this function, it 
                   searches for a match of the first 2 letters of the word, in which case
                   it sets the direction, and calls the checkRestofWord function 
*/
void findWord(char* word, int ws, int dim)
{    
    int r=0;
    int c=0;
    int direction=0;
    
    for(r=0;r<size;r++){ /*goes through rows of matrix*/
        for(c=0;c<size;c++){ /*goes through columns of matrix*/
            if (*(word)==puzzle[r][c]) {/*found first letter*/
                /*
                  Finding the direction to search: This part of the program finds which
                  direction the first 2 letters are going in, so it knows the direction
                  to check to compare with the rest of the word.  
                   
                  Direction:
                  1 = UP;
                  2 = UP AND RIGHT;
                  3 = RIGHT;
                  4 = DOWN AND RIGHT;
                  5 = DOWN;
                  6 = DOWN AND LEFT;
                  7 = LEFT;
                  8 = UP AND LEFT;

                  I know a lot of this code is repetitive, but I wasn't sure 
                  how else to get around this problem. Because I wanted each 
                  letter around the first letter to have a chance to be found.
                  Other ways I thought of that are not as repetitive wouldn't
                  have given every letter around it a chance in the case of 
                  multiple 2nd letter matches. I had to use 8 if statements to
                  ensure that if one of them returned false, it would still
                  look at the rest of the letters around it, and check to see 
                  if that was the path for the word to be found.
                */ 
                if(*(word+1)==puzzle[r-1][c]){   
                    direction=1;
                    if(checkRestofWord(word,r,c,ws,direction)){
                    }
                }
                if(*(word+1)==puzzle[r-1][c+1]){
                    direction=2;
                    if(checkRestofWord(word,r,c,ws,direction)){
                    }
                }
                if(*(word+1)==puzzle[r][c+1]){
                    direction=3;
                    if(checkRestofWord(word,r,c,ws,direction)){
                    }
                }
                if(*(word+1)==puzzle[r+1][c+1]){
                    direction=4;
                    if(checkRestofWord(word,r,c,ws,direction)){
                    }
                }
                if(*(word+1)==puzzle[r+1][c]){
                    direction=5;
                    if(checkRestofWord(word,r,c,ws,direction)){
                    }
                }
                if(*(word+1)==puzzle[r+1][c-1]){
                    direction=6;
                    if(checkRestofWord(word,r,c,ws,direction)){
                    }
                }
                if(*(word+1)==puzzle[r][c-1]){
                    direction=7;
                    if(checkRestofWord(word,r,c,ws,direction)){
                    }
                }
                if(*(word+1)==puzzle[r-1][c-1]){
                    direction=8;
                    if(checkRestofWord(word,r,c,ws,direction)){
                    }
                }
            }/*end of if statement (found the first letter)*/
        }/*end of inner for loop*/
    }/*end of outer for loop*/
}/*end of function*/



/*
Function Name    : checkRestofWord
Parameters       : char* word - the word that is to be searched for
                   int row - the row which the first letter was matched
                   int col - the column which the first letter was matched
                   int wordsize - the size of the word
                   int direction - the direction the word is going
Return value(s)  : boolean foundword - if the word was found or not
Partners         : none
Description      : This is the algorithm used to check if the rest of the word was found. 
                   If the word is found, it calls the addtopuzzle function to add it to
                   the final puzzle to be printed out. It then returns true or false
                   (whether the word was found or not) back to the findWord function.  
*/
bool checkRestofWord(char* word, int row, int col, int wordsize, int direction){

    int a=0;
    bool foundword=true;

    switch (direction) {/* searches for the rest of the word based off the direction */
        case 1: /*  UP  */
            for(a=0;a<wordsize;a++){
                    if(*(word+a)!=puzzle[row-a][col])
                        foundword=false;
            }
            break;
        case 2: /*  UP AND RIGHT  */
            for(a=0;a<wordsize;a++){
                    if(*(word+a)!=puzzle[row-a][col+a])
                        foundword=false;
            }
            break;
        case 3: /*  RIGHT  */
            for(a=0;a<wordsize;a++){
                    if(*(word+a)!=puzzle[row][col+a])
                        foundword=false;
            }
            break;
        case 4: /*  DOWN AND RIGHT  */
            for(a=0;a<wordsize;a++){
                    if(*(word+a)!=puzzle[row+a][col+a])
                        foundword=false;
            }
            break;
        case 5: /*  DOWN  */
            for(a=0;a<wordsize;a++){
                    if(*(word+a)!=puzzle[row+a][col])
                        foundword=false;
            }
            break;
        case 6: /*  DOWN AND LEFT  */
            for(a=0;a<wordsize;a++){
                    if(*(word+a)!=puzzle[row+a][col-a])
                        foundword=false;
            }
            break;
        case 7: /*  LEFT  */
            for(a=0;a<wordsize;a++){
                    if(*(word+a)!=puzzle[row][col-a])
                        foundword=false;
            }
            break;
        case 8: /*  UP AND LEFT  */
            for(a=0;a<wordsize;a++){
                    if(*(word+a)!=puzzle[row-a][col-a])
                        foundword=false;
            }
            break;
    }

/********** WORD FOUND *******/
/* NOW ADD TO THE SOLVED PUZZLE THAT WILL BE OUR FINAL OUTPUT */
    if(foundword)
        addtopuzzle(word,row,col,wordsize,direction); 
    return foundword;

}



/*
Function Name    : addtopuzzle
Parameters       : char* word - the word that is to be searched for
                   int row - the row which the first letter was matched
                   int col - the column which the first letter was matched
                   int wordsize - the size of the word
                   int direction - the direction the word is going
Return value(s)  : void
Partners         : none
Description      : This function simply adds in the word found based on its position to
                   the puzzle that will be printed as the final output of the program. 
*/
void addtopuzzle(char* word, int row, int col, int wordsize, int direction) {

    int b=0;

    switch(direction){
        case 1:
            for(b=0;b<wordsize;b++)
                printpuzzle[row-b][col]=*(word+b);
            break;
        case 2:
            for(b=0;b<wordsize;b++)
                printpuzzle[row-b][col+b]=*(word+b);
            break;
        case 3:
            for(b=0;b<wordsize;b++)
                printpuzzle[row][col+b]=*(word+b);
            break;
        case 4:
            for(b=0;b<wordsize;b++)
                printpuzzle[row+b][col+b]=*(word+b);
            break;
        case 5:
            for(b=0;b<wordsize;b++)
                printpuzzle[row+b][col]=*(word+b);
            break;
        case 6:
            for(b=0;b<wordsize;b++)
                printpuzzle[row+b][col-b]=*(word+b);
            break;
        case 7:
            for(b=0;b<wordsize;b++)
                printpuzzle[row][col-b]=*(word+b);
            break;
        case 8:
            for(b=0;b<wordsize;b++)
                printpuzzle[row-b][col-b]=*(word+b);
            break;
    }
}


