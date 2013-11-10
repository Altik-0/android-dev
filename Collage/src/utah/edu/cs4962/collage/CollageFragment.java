package utah.edu.cs4962.collage;

import utah.edu.cs4962.collage.model.CollageModel;
import utah.edu.cs4962.collage.view.CollageView;
import utah.edu.cs4962.collage.view.CollageViewDataSource;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CollageFragment extends Fragment implements CollageViewDataSource
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
        // TODO: for now, just always assume collage is 2000 x 2000 pixels
        CollageModel.getInstance().setWidthAndHeight(2000, 2000);
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
}
