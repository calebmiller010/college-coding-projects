public class Time
{
    private int hours;
    private int minutes;
    private int seconds;
    private int hundredths;

    public Time()
    {
        hours = 0;
        minutes = 0;
        seconds = 0;
        hundredths = 0;
    }

    // ticks one centisecond
    public void tick()
    {
        hundredths++;

        if ( hundredths >= 100 )
        {
            seconds++;
            hundredths = 0;
        }
        
        if ( seconds >= 60 )
        {
            minutes++;
            seconds = 0;
        }

        if ( minutes >= 60 )
        {
            hours++;
            minutes = 0;
        }
    }

    // prints the current time
    public void printTime()
    {
        System.out.printf( "Time is now %02d:%02d:%02d:%02d\n",
                hours, minutes, seconds, hundredths );
    }

    // resets the time
    public void reset()
    {
        hours = 0;
        minutes = 0;
        seconds = 0;
        hundredths = 0;
        System.out.print( "Time reset to 00:00:00:00\n" );
    }

    // return hours
    public int getHours()
    {
        return hours;
    }

    // return minutes
    public int getMinutes()
    {
        return minutes;
    }

    // return seconds
    public int getSeconds()
    {
        return seconds;
    }
    
    // return centiseconds
    public int getHundredths()
    {
        return hundredths;
    }

    // sets hours
    public void setHours( int newHours )
    {
        if ( newHours < 0 )
        {
            System.out.print( "Error: Invalid. Hours not set.\n" );
        }
        else
        {
            hours = newHours;
        }
    }

    // sets minutes
    public void setMinutes( int newMinutes )
    {
        if ( newMinutes < 0 )
        {
            System.out.print( "Error: Invalid. Minutes not set.\n" );
        }
        else
        {
            setHours( newMinutes / 60 );
            minutes = newMinutes % 60;
        }
    }

    // sets seconds
    public void setSeconds( int newSeconds )
    {
        if ( newSeconds < 0 )
        {
            System.out.print( "Error: Invalid. Seconds not set.\n" );
        }
        else
        {
            setMinutes( newSeconds / 60 );
            seconds = newSeconds % 60;
        }
    }
    
    // sets centiseconds
    public void setHundredths( int newHundredths )
    {
        if ( newHundredths < 0 )
        {
            System.out.print( "Error: Invalid. Hundredths not set.\n" );
        }
        else
        {
            setSeconds( newHundredths / 100 );
            hundredths = newHundredths % 100;
        }
    }
}
