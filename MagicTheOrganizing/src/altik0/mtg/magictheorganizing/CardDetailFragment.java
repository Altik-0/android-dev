package altik0.mtg.magictheorganizing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import altik0.mtg.magictheorganizing.Database.MtgDatabaseManager;
import altik0.mtg.magictheorganizing.MtgDataTypes.CardData;
import altik0.mtg.magictheorganizing.views.CardView;

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
    private CardData card;
    
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
            int id = getArguments().getInt(ARG_ITEM_ID);
            card = MtgDatabaseManager.getInstance(getActivity()).GetCardWithId(id);
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_card_detail,
                container, false);
        
        // Show the dummy content as text in a TextView.
        if (card != null)
        {
            CardView cardDetail = (CardView)rootView.findViewById(R.id.cardDetail);
            TextView nameText = (TextView)rootView.findViewById(R.id.nameDetailText);
            TextView typeText = (TextView)rootView.findViewById(R.id.typeDetailText);
            TextView costText = (TextView)rootView.findViewById(R.id.costDetailText);
            //TextView textText = (TextView)rootView.findViewById(R.id.cardTextText);
            
            // TODO:
            //  1) replace static strings with R values (for localization and whatnot)
            //  2) make mana costs not text
            //  3) All the fields durp
            cardDetail.setCardData(card);
            nameText.setText("Name: " + card.getName());
            typeText.setText("Type: " + card.getTypeString());
            costText.setText("Cost: " + card.getManaCost());
        }
        
        return rootView;
    }
}
