package altik0.mtg.magictheorganizing;

import java.util.ArrayList;

import altik0.mtg.magictheorganizing.Database.MtgDatabaseManager;
import altik0.mtg.magictheorganizing.Database.SearchParams;
import altik0.mtg.magictheorganizing.MtgDataTypes.CardData;
import altik0.mtg.magictheorganizing.views.CardView;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

// Needs to be a BaseAdapter over a ListAdapter so notifyDataSetChanged gets put in
public class CardListAdapter extends BaseAdapter
{
    private ArrayList<CardData> data;
    private Context context;
    private Integer collectionId;

    public CardListAdapter(Context _context, SearchParams _params)
    {
        this(_context, _params, null);
    }
    
    public CardListAdapter(Context _context, SearchParams _params, Integer _collectionId)
    {
        context = _context;
        collectionId = _collectionId;
        MtgDatabaseManager db = MtgDatabaseManager.getInstance(_context);
        
        // If we're given a collectionId, we're searching over collections.
        // Otherwise, we're searching over all cards.
        if (collectionId == null)
            data = db.SearchForCards(_params);
        else
            data = db.SearchForCardsInCollection(_params, collectionId);
    }
    
    public void addCardToCollection(Integer collectionId, CardData card)
    {
        // For now, we assume that collectionId == null just means skip adding
        // to the database, and we'll just refresh
        if (collectionId != null)
            MtgDatabaseManager.getInstance(context).AddCardToCollection(collectionId, card);
        notifyDataSetChanged();
    }
    
    public void setSearchParams(SearchParams params)
    {
        MtgDatabaseManager db = MtgDatabaseManager.getInstance(context);
        data = db.SearchForCards(params);
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
        return true;
    }

    @Override
    public boolean isEnabled(int arg0)
    {
        // TODO Auto-generated method stub
        return true;
    }
}
