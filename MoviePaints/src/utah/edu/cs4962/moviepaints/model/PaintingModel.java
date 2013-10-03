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
    
    private LinkedList<Curve> curves;
    private LinkedList<HandMovement> handMoves;
    
    public PaintingModel()
    {
        curves = new LinkedList<Curve>();
        handMoves = new LinkedList<HandMovement>();
    }
    
    public void createNewCurve(int color, float width)
    {
        curves.addLast(new Curve(color, width));
    }
    
    public void drawPoint(Point newPoint, long time)
    {
        curves.getLast().drawPoint(newPoint, time);
    }
    
    public void createHandMovement(Point move, long time)
    {
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
    
    // TODO: figure out how to properly make these members read-only
    public LinkedList<Curve> getCurves()
    {
        return curves;
    }
    public LinkedList<HandMovement> getHandMoves()
    {
        return handMoves;
    }
}
