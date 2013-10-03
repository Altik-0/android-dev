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
    
    // "Setters"
    public void onCreateCurve(int color, float width);
    public void onDrawPoint(Point newPoint, long time);
    public void onHandMovement(Point move, long time);
}