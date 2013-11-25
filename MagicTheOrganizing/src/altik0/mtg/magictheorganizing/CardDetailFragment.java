package altik0.mtg.magictheorganizing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import altik0.mtg.magictheorganizing.MtgDataTypes.CardData;

/**
 * A fragment representing a single Card detail screen. This fragment is either
 * contained in a {@link CardListActivity} in two-pane mode (on tablets) or a
 * {@link CardDetailActivity} on handsets.
 */
public class CardDetailFragment extends Fragment
{
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    
    /**
     * The dummy content this fragment is presenting.
     */
    private CardData mItem;
    
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CardDetailFragment()
    {
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        if (getArguments().containsKey(ARG_ITEM_ID))
        {
            // TODO: load CardData to display
            // mItem = ... get card somehow?
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_card_detail,
                container, false);
        
        // Show the dummy content as text in a TextView.
        if (mItem != null)
        {
            // TODO: replace below appropriately
            //((TextView) rootView.findViewById(R.id.card_detail)).setText(mItem.content);
        }
        
        return rootView;
    }
}
