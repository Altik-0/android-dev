package utah.edu.cs4962.moviepaints.model;

import com.google.gson.Gson;

import android.util.Log;

public class Stopwatch
{
    private long startTime;
    private long pauseTime;
    private boolean isPaused;
    private boolean isStarted;
    
    public Stopwatch()
    {
        isPaused = false;
        isStarted = false;
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
            startTime = tmp - pauseTime;
            isPaused = false;
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
            toRet = pauseTime;
        else
            toRet = tmp - startTime;
        
        return toRet;
    }
    
    public void Reset()
    {
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
    
    // Serialization functionality
    private class StopwatchSerialization
    {
        //public long oldStartTime;       // May not need this to be serialized, but may be useful, *shrug*
        //public long oldPauseTotal;
        public long lastSampledTime;
    }
    
    public String toJson()
    {
        long tmp = System.currentTimeMillis();
        
        StopwatchSerialization serial = new StopwatchSerialization();
        serial.lastSampledTime = GetTime();
        
        Gson gson = new Gson();
        return gson.toJson(serial, StopwatchSerialization.class);
    }
    
    public static Stopwatch fromJson(String json)
    {
        Stopwatch toRet = new Stopwatch();
        Gson gson = new Gson();
        StopwatchSerialization serial = gson.fromJson(json, StopwatchSerialization.class);
        
        toRet.startTime = System.currentTimeMillis() - serial.lastSampledTime;
        toRet.pauseTime = serial.lastSampledTime;
        
        // We really want our watch to be paused and started, not reset.
        toRet.isPaused = true;
        toRet.isStarted = true;
        
        return toRet;
    }
}
