package utah.edu.cs4962.collage;

import utah.edu.cs4962.collage.model.CollageModel;
import utah.edu.cs4962.collage.model.LibraryUpdateListener;
import utah.edu.cs4962.collage.view.LibraryListCellView;
import android.app.Fragment;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CollageLibraryFragment extends Fragment implements ListAdapter,
                                                                LibraryUpdateListener,
                                                                OnItemClickListener
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
        libraryList.setOnItemClickListener(this);
        CollageModel.getInstance().registerForLibraryUpdates(this);
        
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
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        LibraryListCellView toRet = null;
        CollageModel.LibraryElementData data = (CollageModel.LibraryElementData)this.getItem(position);
        if (convertView == null || !(convertView instanceof LibraryListCellView))
        {
            // TODO: properly handle \pm case
            toRet = new LibraryListCellView(getActivity(),
                    data.thumbnail, data.width, data.height, data.lastModified, data.inCollage);
        }
        else
        {
            toRet = (LibraryListCellView)convertView;
            toRet.setThumbnail(data.thumbnail);
            toRet.setImgWidth(data.width);
            toRet.setImgHeight(data.height);
            toRet.setTimestamp(data.lastModified);
            toRet.setIsInCollage(data.inCollage);
        }
        
        toRet.setPlusMinusClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // If the element is presently in the collage, remove it
                if (CollageModel.getInstance().getDataForIndex(position).inCollage)
                {
                    CollageModel.getInstance().removeLibraryElementFromCollage(position);
                }
                // If it is not, then add it
                else
                {
                    CollageModel.getInstance().addLibraryElementToCollage(position);
                }
            }
        });
        
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
        return CollageModel.getInstance().getDataForIndex(position).inCollage;
    }

    @Override
    public void libraryElementRemoved()
    {
        libraryList.invalidateViews();
    }

    @Override
    public void libraryElementAdded()
    {
        libraryList.invalidateViews();
    }

    @Override
    public void libraryHasChanged()
    {
        libraryList.invalidateViews();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        CollageModel.getInstance().setSelectedEntry(position);
    }
}
