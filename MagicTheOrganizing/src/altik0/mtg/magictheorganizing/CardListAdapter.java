package altik0.mtg.magictheorganizing;

import java.util.ArrayList;

import altik0.mtg.magictheorganizing.Database.CollectionModel.Collection;
import altik0.mtg.magictheorganizing.Database.MtgDatabaseManager;
import altik0.mtg.magictheorganizing.Database.SearchParams;
import altik0.mtg.magictheorganizing.MtgDataTypes.CardData;
import altik0.mtg.magictheorganizing.views.CardView;
import android.widget.BaseAdapter;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

public class CardListAdapter extends BaseAdapter
{
    private ArrayList<CardData> data;
    private Context context;
    private SearchParams params;

    public CardListAdapter(Context _context, SearchParams _params)
    {
        context = _context;
        params = _params;
        MtgDatabaseManager db = MtgDatabaseManager.getInstance(_context);
        data = db.SearchForCards(params);
        notifyDataSetChanged();
    }
    
    public CardListAdapter(Context _context, SearchParams _params, Collection _c)
    {
        context = _context;
        params = _params;
        MtgDatabaseManager db = MtgDatabaseManager.getInstance(_context);
        if (!params.searchOverCollection)
            data = db.SearchForCards(params);
        else
            data = db.SearchCollectionForCards(params);
        notifyDataSetChanged();
    }
    
    public void setSearchParams(SearchParams _params)
    {
        params = _params;
        MtgDatabaseManager db = MtgDatabaseManager.getInstance(context);
        if (!params.searchOverCollection)
            data = db.SearchForCards(params);
        else
            data = db.SearchCollectionForCards(params);
        notifyDataSetChanged();
    }
    
    public void addCard(CardData card)
    {
        // Does nothing if we don't have a collectionId to insert to:
        if (params.CollectionId == null)
            return;
        
        // Otherwise, insert
        MtgDatabaseManager db = MtgDatabaseManager.getInstance(context);
        db.AddCardToCollection(params.CollectionId, card);
        data = db.SearchCollectionForCards(params);
        notifyDataSetChanged();
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
    public boolean areAllItemsEnabled()
    {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled(int arg0)
    {
        // TODO Auto-generated method stub
        return true;
    }
}
