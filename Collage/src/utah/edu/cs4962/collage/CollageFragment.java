package utah.edu.cs4962.collage;

import utah.edu.cs4962.collage.view.CollageView;
import utah.edu.cs4962.collage.view.CollageViewDataSource;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CollageFragment extends Fragment implements CollageViewDataSource
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        CollageView collageView = new CollageView(getActivity(), this);
        return collageView;
    }

    @Override
    public Bitmap getCollage()
    {
        // For now, just open a sample bitmap and return that
        return BitmapFactory.decodeResource(getResources(), R.drawable.saturn);
    }

    @Override
    public int getWidth()
    {
        Bitmap tmp = BitmapFactory.decodeResource(getResources(), R.drawable.saturn);
        return tmp.getWidth();
    }

    @Override
    public int getHeight()
    {
        Bitmap tmp = BitmapFactory.decodeResource(getResources(), R.drawable.saturn);
        return tmp.getHeight();
    }
}
