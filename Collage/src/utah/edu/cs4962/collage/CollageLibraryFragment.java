package utah.edu.cs4962.collage;

import utah.edu.cs4962.collage.model.CollageModel;
import utah.edu.cs4962.collage.view.LibraryListCellView;
import android.app.Fragment;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CollageLibraryFragment extends Fragment implements ListAdapter
{
    private ListView libraryList;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        libraryList = new ListView(getActivity());
        libraryList.setAdapter(this);
        
        return libraryList;
    }
    
    public void addPathToLibrary(String path)
    {
        CollageModel.getInstance().addImageToLibrary(path);
        libraryList.invalidateViews();
    }
    
    @Override
    public int getCount()
    {
        Log.i("Count:", "" + CollageModel.getInstance().getLibraryCount());
        return CollageModel.getInstance().getLibraryCount();
    }

    @Override
    public Object getItem(int arg0)
    {
        return CollageModel.getInstance().getDataForIndex(arg0);
    }

    @Override
    public long getItemId(int arg0)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getItemViewType(int arg0)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LibraryListCellView toRet = null;
        CollageModel.LibraryElementData data = (CollageModel.LibraryElementData)this.getItem(position);
        if (convertView == null || !(convertView instanceof LibraryListCellView))
        {
            // TODO: properly handle \pm case
            toRet = new LibraryListCellView(getActivity(),
                    data.thumbnail, data.width, data.height, data.lastModified, false);
        }
        else
        {
            toRet = (LibraryListCellView)convertView;
            toRet.setThumbnail(data.thumbnail);
            toRet.setImgWidth(data.width);
            toRet.setImgHeight(data.height);
            toRet.setTimestamp(data.lastModified);
        }
        
        return toRet;
    }

    @Override
    public int getViewTypeCount()
    {
        return 1;
    }

    @Override
    public boolean hasStableIds()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEmpty()
    {
        return CollageModel.getInstance().getLibraryCount() > 0;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean areAllItemsEnabled()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEnabled(int position)
    {
        // TODO Auto-generated method stub
        return false;
    }
}
