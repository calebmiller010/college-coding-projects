/*
Name         : Caleb Miller
Class        : 2400-002 
Program #    : 1
Due Date     : Wednesday, September 9, 11:59 PM

Honor Pledge : On my honor as a student of the University
               of Nebraska at Omaha, I have neither given nor received
               unauthorized help on this homework assignment.

NAME         : Caleb Miller 
NUID         : 616
EMAIL        : calebmiller@unomaha.edu  

Partners     : NONE
Description  : This program is a basic math quiz that will prompt the user
               for how many questions they want, and difficulty, then give
               them basic arithmetic questions, where the numbers, and the
               arithmetic functions are random. When they're finished, the
               program will tell them how many questions they got correct.

*/

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>

int genQuestion ( int d );
bool answerQuestion ( int coranswer );
void response ( bool rw );

int main ()
{
    int answer, ques, diff, x, numright;
    srand ( time ( NULL ) );
    
    /* Prompt user for number of questions */
    ques = 0;
    while ( ques < 1 || ques > 20 ) {
        printf ( "\nHow many questions for this test (1 - 20)? " );
        scanf ( "%d", &ques );
    }

    /* Prompt user for difficulty */
    diff = 0;
    while ( diff < 1 || diff > 4 ) {
        printf ( "Select difficulty (1 - 4): " );
        scanf ( "%d", &diff );
    }

    numright = 0;
    
    /* Outside 'for' loop based on number of questions */
    for ( x = 1; x <= ques; x++ ) {
        printf ( "Question %d: ", x );
        /* This generates the question, and returns the correct answer */
        answer = genQuestion ( diff );
        
        /* This checks the user's answer with the real answer to determine
           if it's right or wrong */
        if ( answerQuestion ( answer ) ) {
            response ( true );
            numright += 1;
        }
        else {
            response ( false );
            printf ( "The correct answer was %d\n", answer );
        } 
    }

    printf ( "Your score was %d/%d\n\n", numright, ques );
    return 0;

}

/*
Method Name      : genQuestion
Parameters       : int d (the difficulty)
Return value(s)  : int answerlocal (the answer to the question generated)
Partners         : none
Description      : this method generates the question. Based on the difficulty
                   selected, the lower bound and range are set, and then those
                   are used to generate the randomized numbers for the question.
                   Then it randomizes which arithmetic function for the question,
                   and finally, it passes back the answer to the main method.
*/
int genQuestion ( int d ) {
    int range, lbound, num1, num2, answerlocal, oper;
    
    /* Set the lower bound and range based on the difficulty selected */
    switch ( d ) {
        case 1: lbound = 1;
                range = 10;
                break;
        case 2: lbound = 1;
                range = 50;
                break;
        case 3: lbound = 1;
                range = 100;
                break;
        case 4: lbound = -100;
                range = 200;
    }

    /* Set random numbers */
    num1 = ( rand() % range ) + lbound;
    num2 = ( rand() % range ) + lbound;
    oper = ( rand() % 4 ) + 1;
    
    /* Create the question based on a random operator */
    switch ( oper ) {
        case 1: printf ( "%d + %d =\n", num1, num2 );
                answerlocal = num1 + num2;
                break;
        case 2: printf ( "%d - %d =\n", num1, num2 );
                answerlocal = num1 - num2;
                break;
        case 3: printf ( "%d * %d =\n", num1, num2 );
                answerlocal = num1 * num2;
                break;
        case 4: printf ( "%d / %d =\n", num1, num2 );
                while ( num2 == 0 ) 
                    num2 = ( rand() % range ) + lbound;
                answerlocal = num1 / num2;
                break;
    }

    return answerlocal;

} 

/*
Method Name      : answerQuestion
Parameters       : int coranswer (the correct answer to the problem)
Return value(s)  : boolean: true if they got it right, false if not
Partners         : none
Description      : this method promps the user for an answer to the
                   question, compares their answer to the correct one,
                   and returns true or false if they got it right or not
*/
bool answerQuestion ( int coranswer ) {
    int useranswer;
    printf ( "Enter Answer: " );
    scanf ( "%d", &useranswer );
    if ( useranswer == coranswer )
        return true;
    else
        return false;
}

/*
Method Name      : response
Parameters       : bool rw (whether they go the question right or not)
Return value(s)  : void: simply prints out randomized response
Partners         : none
Description      : this method chooses a randomized response to the user,
                   based off if they got the question right or not (from
                   the boolean value passed in)
*/
void response ( bool rw ) {
    int res = ( rand() % 3 );
    if ( rw ) {
        switch ( res ) {
            case 0: printf ( "Good job!" );
                    break;
            case 1: printf ( "Nice!" );
                    break;
            case 2: printf ( "You're right!" );
                    break;
        }
    }
    else {
        switch ( res ) {
            case 0: printf ( "Sorry!" );
                    break;
            case 1: printf ( "You're wrong!" );
                    break;
            case 2: printf ( "That's not it!" );
                    break;
        }
    }
    printf ( "\n" );
}


