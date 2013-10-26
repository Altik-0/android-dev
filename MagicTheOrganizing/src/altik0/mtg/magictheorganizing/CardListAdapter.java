package altik0.mtg.magictheorganizing;

import altik0.mtg.magictheorganizing.MtgDataTypes.CardData;
import altik0.mtg.magictheorganizing.views.CardView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class CardListAdapter extends ArrayAdapter<CardData>
{
    private CardData[] data;

    public CardListAdapter(Context context, CardData[] _data)
    {
        super(context, 0);
        data = _data;
    }
    
    @Override
    public int getCount()
    {
        return data.length;
    }
    
    @Override
    public View getView(int index, View toReplace, ViewGroup parent)
    {
        if (toReplace == null)
            toReplace = new CardView(getContext(), data[index]);
        if (toReplace instanceof CardView)
            ((CardView)toReplace).setCardData(data[index]);
        
        return toReplace;
    }
}
