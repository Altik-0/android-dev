package utah.edu.cs4962.moviepaints.model;

import com.google.gson.Gson;

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
    
    public void Reset()
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
    
    // Serialization functionality
    private class StopwatchSerialization
    {
        public long oldStartTime;       // May not need this to be serialized, but may be useful, *shrug*
        public long oldPauseTotal;
        public long lastSampledTime;
    }
    
    public String toJson()
    {
        long tmp = System.currentTimeMillis();
        
        StopwatchSerialization serial = new StopwatchSerialization();
        serial.oldPauseTotal = totalPauseLength;
        serial.oldStartTime = startTime;
        serial.lastSampledTime = GetTime();
        
        Gson gson = new Gson();
        return gson.toJson(serial, StopwatchSerialization.class);
    }
    
    public static Stopwatch fromJson(String json)
    {
        Stopwatch toRet = new Stopwatch();
        Gson gson = new Gson();
        StopwatchSerialization serial = gson.fromJson(json, StopwatchSerialization.class);
        
        toRet.totalPauseLength = serial.oldPauseTotal;
        toRet.startTime = System.currentTimeMillis() - serial.lastSampledTime;
        toRet.pauseTime = System.currentTimeMillis() - toRet.startTime - toRet.totalPauseLength;
        
        // We really want our watch to be paused and started, not reset.
        toRet.isPaused = true;
        toRet.isStarted = true;
        
        return toRet;
    }
}
