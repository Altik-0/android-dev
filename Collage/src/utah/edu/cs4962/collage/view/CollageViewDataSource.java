package utah.edu.cs4962.collage.view;

import android.graphics.Bitmap;

public interface CollageViewDataSource
{
    public Bitmap getCollage();
    public int getWidth();
    public int getHeight();
}
