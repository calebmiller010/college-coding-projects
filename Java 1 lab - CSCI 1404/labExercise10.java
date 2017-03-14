import java.util.Scanner;
import java.util.Random;

public class labExercise10
{
    public static void main ( String[] args )
    {
        String [] heroes = new String [5];
        String [] weapons = new String [5];
        String [] villains = new String [5];
        String [] madLibs = new String [9];
        int choice;

        // Here, use the necessary method calls to complete the program.

        // fill in the heroes, weapons and villain arrays
        // get the words from the user
        // get a random choice for a random set of hero/villain
        // print the mad lib

    }

    public static void getWords( ) //fill in the parameter list
    {
        Scanner in = new Scanner (System.in);
        System.out.print ("Please enter a verb ending with -ed: ");
        words[0] = in.nextLine();
        System.out.print ("Please enter noun that could be tall: ");
        words[1] = in.nextLine();
        System.out.print ("Please enter a moving verb: ");
        words[2] = in.nextLine();
        System.out.print ("Please enter something that you would yell: ");
        words[3] = in.nextLine();
        System.out.print ("Please enter a verb ending with -ed: ");
        words[4] = in.nextLine();
        System.out.print ("Please enter a witty remark: ");
        words[5] = in.nextLine();
        System.out.print ("Please enter a fighting verb: ");
        words[6] = in.nextLine();
        System.out.print ("Please enter a verb: ");
        words[7] = in.nextLine();
        System.out.print ("Please enter a noun: ");
        words[8] = in.nextLine();  
    }

    public static int selectWords ()
    {
        Random rNum = new Random ();
        int choice = rNum.nextInt(5);

        return choice;
    }

    public static void printMadLibs ( ) //fill in the parameter list
    {
        System.out.println("\t\n\nBoom! There was a loud crash!");
        System.out.printf("\t%s %s off of the %s and grabbed his %s.\n", heroes[choice], madLibs[0], madLibs[1], weapons[choice]);
        System.out.printf("\tHe %s towards the sound and saw %s.\n", madLibs[2], villains[choice]);
        System.out.printf("\t%s yelled %s.\n", heroes[choice], madLibs[3]);
        System.out.printf("\t%s %s at %s and said %s.\n", villains[choice], madLibs[4], heroes[choice], madLibs[5]);
        System.out.printf("\tThey began to %s.\n", madLibs[6]);
        System.out.printf("\tAs %s struck %s down with his %s, %s said:\n", heroes[choice], villains[choice], weapons[choice], villains[choice]);
        System.out.printf("\t\"I %s my %s at you, %s! This is not the end!\"\n\n", madLibs[7], madLibs[8], heroes[choice]);
    }

    public static void setArrays ( ) //fill in the parameter list
    {
        goodPpl[0] = "Iron Man";
        goodPpl[1] = "Thor";
        goodPpl[2] = "Captain America";
        goodPpl[3] = "Hulk";
        goodPpl[4] = "Hawkeye";

        pointyThings[0] = "Repulsor Blaster";
        pointyThings[1] = "Mjolnir";
        pointyThings[2] = "Shield";
        pointyThings[3] = "Giant Green Fists";
        pointyThings[4] = "Exploding Arrows";

        badPpl[0] = "The Mandarin";
        badPpl[1] = "Loki";
        badPpl[2] = "Red Skull";
        badPpl[3] = "General Ross";
        badPpl[4] = "Crossfire";

    } 
}



