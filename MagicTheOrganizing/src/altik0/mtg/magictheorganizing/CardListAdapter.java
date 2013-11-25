package altik0.mtg.magictheorganizing;

import java.util.ArrayList;

import altik0.mtg.magictheorganizing.Database.MtgDatabaseManager;
import altik0.mtg.magictheorganizing.MtgDataTypes.CardData;
import altik0.mtg.magictheorganizing.views.CardView;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

public class CardListAdapter implements ListAdapter
{
    private ArrayList<CardData> data;
    private Context context;

    public CardListAdapter(Context _context)
    {
        context = _context;
        MtgDatabaseManager db = MtgDatabaseManager.getInstance(context);
        data = db.GetAllCards();
    }
    
    @Override
    public int getCount()
    {
        return data.size();
    }
    
    @Override
    public View getView(int index, View toReplace, ViewGroup parent)
    {
        if (toReplace == null)
            toReplace = new CardView(context, data.get(index));
        if (toReplace instanceof CardView)
            ((CardView)toReplace).setCardData(data.get(index));
        
        return toReplace;
    }

    @Override
    public Object getItem(int index)
    {
        // TODO Auto-generated method stub
        return data.get(index);
    }

    @Override
    public long getItemId(int index)
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
    public int getViewTypeCount()
    {
        // TODO Auto-generated method stub
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
        // TODO Auto-generated method stub
        return data.size() > 0;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver arg0)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver arg0)
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
    public boolean isEnabled(int arg0)
    {
        // TODO Auto-generated method stub
        return false;
    }
}
