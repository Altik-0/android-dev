package utah.edu.cs4962.collage.view;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

public interface CollageViewDataSource
{
    // DataSource --> View
    public Bitmap getCollage();
    public int getWidth();
    public int getHeight();
    public Rect getSelectedRegion();
    
    // View --> DataSource
    public Boolean tryTouchPoint(Point p);
    public Boolean tryMove(Point change);
    public Boolean tryScaleBy(float scaleFactor);
}
