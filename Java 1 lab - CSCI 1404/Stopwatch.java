public class Stopwatch
{
    private long startNano;
    private long stopNano;
    private boolean running;
    
    public Stopwatch()
    {
        startNano = 0;
        stopNano = 0;
        running = false;
    }
    
    // starts the stopwatch
    public void start()
    {
        // get start time
        startNano = System.nanoTime();
        stopNano = 0;
        running = true;
    }
    
    // Stops the stopwatch
    public void stop()
    {
        // get stop time
        stopNano = System.nanoTime();
        running = false;
    }
    
    // resets the stopwatch
    public void reset()
    {
        startNano = 0;
        stopNano = 0;
        running = false;
    }
    
    // prints the time, even while running
    public void printTime()
    {
        if ( running ) stopNano = System.nanoTime();
        
        // convert it to Time format
        // 1 centisecond = 10000000 nanoseconds
        Time t = new Time();
        t.setHundredths( (int)( ( stopNano - startNano ) / 10000000 ) );
        t.printTime();
    }
}
