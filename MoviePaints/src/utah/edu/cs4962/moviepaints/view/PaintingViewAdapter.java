package utah.edu.cs4962.moviepaints.view;

import java.util.LinkedList;

import android.graphics.Point;
import android.widget.Adapter;

public interface PaintingViewAdapter
{
    // "Getters"
    public int getCurveCount();
    public LinkedList<Point> getCurve(int index);
    public int getCurveColor(int index);
    public float getCurveWidth(int index);
    public Point getHandOffset();
    
    // "Setters"
    public void onCreateCurve(int color, float width);
    public void onDrawPoint(Point newPoint);
    public void onHandMovement(Point move);
}
