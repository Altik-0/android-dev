package utah.edu.cs4962.moviepaints.model;

import java.util.LinkedList;
import android.graphics.Point;

public class PaintingModel
{
    // Contains all information for each curve that has been drawn on screen
    public class Curve
    {
        private LinkedList<Point> points;
        private LinkedList<Long> times;
        private int color;
        private float width;
        
        public Curve(int _color, float _width)
        {
            setColor(_color);
            setWidth(_width);
            points = new LinkedList<Point>();
            times = new LinkedList<Long>();
        }

        public LinkedList<Point> getPoints()
        {
            return points;
        }

        public void setPoints(LinkedList<Point> points)
        {
            this.points = points;
        }

        public LinkedList<Long> getTimes()
        {
            return times;
        }

        public void setTimes(LinkedList<Long> times)
        {
            this.times = times;
        }

        public int getColor()
        {
            return color;
        }

        public void setColor(int color)
        {
            this.color = color;
        }

        public float getWidth()
        {
            return width;
        }

        public void setWidth(float width)
        {
            this.width = width;
        }

        public void drawPoint(Point newPoint, long time)
        {
            points.addLast(newPoint);
            times.addLast(time);
        }
    }

    // Contains all information for each scroll using the hand tool
    public class HandMovement
    {
        private Point movement;
        private long time;
        
        public HandMovement(Point _movement, long _time)
        {
            movement = _movement;
            time = _time;
        }

        public Point getMovement()
        {
            return movement;
        }

        public void setMovement(Point movement)
        {
            this.movement = movement;
        }

        public long getTime()
        {
            return time;
        }

        public void setTime(long time)
        {
            this.time = time;
        }
    }
    
    // This class is a singleton!
    private static PaintingModel instance;
    public static PaintingModel getInstance()
    {
        if(instance == null)
            instance = new PaintingModel();
        return instance;
    }
    
    private LinkedList<Curve> curves;
    private LinkedList<HandMovement> handMoves;

    private Stopwatch watch;
    
    private PaintingModel()
    {
        curves = new LinkedList<Curve>();
        handMoves = new LinkedList<HandMovement>();
        watch = new Stopwatch();
    }
    
    public void startTimer()
    {
        watch.Start();
    }
    
    public void pauseTimer()
    {
        watch.Pause();
    }
    
    public void resumeTimer()
    {
        watch.Unpause();
    }
    
    public boolean isStarted()
    {
        return watch.isStarted();
    }
    
    public boolean isPaused()
    {
        return watch.isPaused();
    }
    
    public void restart()
    {
        watch.Restart();
        curves.clear();
        handMoves.clear();
    }
    
    public void stopTimer()
    {
        watch.Restart();
    }
    
    public void createNewCurve(int color, float width)
    {
        curves.addLast(new Curve(color, width));
    }
    
    public void drawPoint(Point newPoint)
    {
        long time = watch.GetTime();
        curves.getLast().drawPoint(newPoint, time);
    }
    
    public void createHandMovement(Point move)
    {
        long time = watch.GetTime();
        handMoves.add(new HandMovement(move, time));
    }
    
    public int getCurveCount()
    {
        return curves.size();
    }

    public Curve getCurve(int index)
    {
        return curves.get(index);
    }

    public Point getHandOffset()
    {
        Point toRet = new Point(0, 0);
        for (HandMovement move : handMoves)
        {
            toRet.x += move.movement.x;
            toRet.y += move.movement.y;
        }
        
        return toRet;
    }
    
    public Point getHandOffsetAtTime(long time)
    {
        Point toRet = new Point(0, 0);
        for (HandMovement move: handMoves)
        {
            if (move.time < time)
            {
                toRet.x += move.movement.x;
                toRet.y += move.movement.y;
            }
        }
        
        return toRet;
    }
    
    // TODO: figure out how to properly make these members read-only
    public LinkedList<Curve> getCurves()
    {
        return curves;
    }
    public LinkedList<HandMovement> getHandMoves()
    {
        return handMoves;
    }
    
    public LinkedList<Curve> getCurvesUpToTime(long time)
    {
        LinkedList<Curve> toRet = new LinkedList<Curve>();
        
        for (Curve curve : curves)
        {
            // If the curve is entirely drawn by the end of this time,
            // then we'll just stash it
            if(curve.times.getLast() < time)
                toRet.add(curve);
            // If it wasn't entirely drawn, we build a new curve that has
            // only the points that will be drawn so far.
            else
            {
                Curve lastCurve = new Curve(curve.color, curve.width);
                for(int i = 0; i < curve.points.size(); i++)
                    if (curve.times.get(i) < time)
                        lastCurve.drawPoint(curve.points.get(i), curve.times.get(i));
                toRet.add(lastCurve);
            }
        }
        
        return toRet;
    }

    public long getEndTime()
    {
        if(!watch.isStarted())
            return 0;

        return watch.GetTime();
    }
}
