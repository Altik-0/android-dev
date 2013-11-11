package utah.edu.cs4962.collage;

import utah.edu.cs4962.collage.model.CollageModel;
import utah.edu.cs4962.collage.model.CollageUpdateListener;
import utah.edu.cs4962.collage.view.CollageView;
import utah.edu.cs4962.collage.view.CollageViewDataSource;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class CollageFragment extends Fragment implements CollageViewDataSource, CollageUpdateListener
{
    private CollageView collageView;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        CollageModel.getInstance().registerForCollageUpdates(this);
        collageView = new CollageView(getActivity(), this);
        return collageView;
    }

    @Override
    public Bitmap getCollage()
    {
        // For now, just open a sample bitmap and return that
        return CollageModel.getInstance().getRenderedCollage();
    }

    @Override
    public int getWidth()
    {
        return CollageModel.getInstance().getWidth();
    }

    @Override
    public int getHeight()
    {
        return CollageModel.getInstance().getHeight();
    }

    @Override
    public void collageEntryRemoved()
    {
        collageView.invalidate();
    }

    @Override
    public void collageEntryAdded()
    {
        collageView.invalidate();
    }
    
    @Override
    public void collageImageUpdated()
    {
        collageView.invalidate();
    }

    @Override
    public Rect getSelectedRegion()
    {
        CollageModel.CollageEntry entry = CollageModel.getInstance().getSelectedEntry();
        if (entry == null)
            return null;
        return entry.getBounds();
    }

    private Integer entryToMod = null;
    @Override
    public Boolean tryTouchPoint(Point p)
    {
        entryToMod = CollageModel.getInstance().findAndSelectEntryAtPoint(p);
        
        // If we didn't hit something, deselect
        if (entryToMod == null)
            CollageModel.getInstance().setSelectedEntry(null);
        
        // Invalidate views, since things may have changed
        collageView.invalidate();
        
        return entryToMod != null;
    }

    @Override
    public Boolean tryMove(Point change)
    {
        if (entryToMod == null)
            return false;
        
        // If we do have one, track the change, and submit that
        CollageModel.getInstance().moveEntry(entryToMod, change.x, change.y);
        return true;
    }
    
    @Override
    public Boolean tryScaleBy(float scaleFactor)
    {
        if (entryToMod == null)
            return false;
        
        CollageModel.getInstance().scaleEntry(entryToMod, scaleFactor);
        return true;
    }
}
