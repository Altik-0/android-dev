package utah.edu.cs4962.moviepaints.model;

import android.util.Log;

public class Stopwatch
{
    private long startTime;
    private long pauseTime;
    private long totalPauseLength;
    private boolean isPaused;
    private boolean isStarted;
    
    public Stopwatch()
    {
        isPaused = false;
        isStarted = false;
        totalPauseLength = 0;
    }
    
    public void Start()
    {
        if (isStarted)
            throw new IllegalStateException("Watch is already started!");
        
        startTime = System.currentTimeMillis();
        isStarted = true;
    }
    
    public void Pause()
    {
        if (!isStarted)
            throw new IllegalStateException("Watch cannot be paused when it isn't started!");
        
        if (!isPaused)
        {
            pauseTime = System.currentTimeMillis() - startTime;
            isPaused = true;
        }
    }
    
    public void Unpause()
    {
        if (isPaused)
        {
            long tmp = System.currentTimeMillis();
            totalPauseLength += tmp - startTime - pauseTime;
            isPaused = false;

            Log.i("Pause data; start time", Long.toString(startTime));
            Log.i("Pause data; pause time", Long.toString(pauseTime));
            Log.i("Pause data; tmp", Long.toString(tmp));
            Log.i("Pause data; total pause length", Long.toString(totalPauseLength));
        }
    }
    
    public long GetTime()
    {
        // We may not need this, but for precision's sake, let's get it now
        long tmp = System.currentTimeMillis();
        
        // If we're not started, this is not good
        if (!isStarted)
            throw new IllegalStateException("Watch cannot be timed when it isn't started!");
        
        long toRet = 0;
        
        if (isPaused)
            toRet = pauseTime - totalPauseLength;
        else
            toRet = tmp - startTime - totalPauseLength;
        
        return toRet;
    }
    
    public void Restart()
    {
        totalPauseLength = 0;
        isPaused = false;
        isStarted = false;
    }
    
    public boolean isStarted()
    {
        return isStarted;
    }
    
    public boolean isPaused()
    {
        return isPaused;
    }
}
