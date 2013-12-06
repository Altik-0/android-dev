package altik0.mtg.magictheorganizing;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
    public static final String RETURNED_CARD_KEY = "returnedCard";
    // Tracks if we're supposed to return a card upon a button press
    private boolean doWantReturnFoSho = false;

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    
    /**
     * The dummy content this fragment is presenting.
     */
    private CardData card;
    
    private OnClickListener selectListener = new OnClickListener()
    {
        @Override
        public void onClick(View arg0)
        {
            // TODO - return shit!
            Intent cardReturn = new Intent();
            cardReturn.putExtra(RETURNED_CARD_KEY, card);
            getActivity().setResult(Activity.RESULT_OK, cardReturn);
            getActivity().finish();
        }
    };
    
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
        
        doWantReturnFoSho = getArguments().getBoolean(RETURNED_CARD_KEY, false);
        
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
        LinearLayout rootView = (LinearLayout)inflater.inflate(R.layout.fragment_card_detail,
                                        container, false);
        
        // Handle the differences between 
        Button selectButton = (Button)getActivity().findViewById(R.id.selectCardButton);
        if (doWantReturnFoSho)
        {
            selectButton.setOnClickListener(selectListener);
        }
        else
        {
            rootView.removeView(selectButton); 
        }
        
        // Show the dummy content as text in a TextView.
        if (card != null)
        {
            CardView cardDetail = (CardView)rootView.findViewById(R.id.cardDetail);
            TextView nameText = (TextView)rootView.findViewById(R.id.nameDetailText);
            TextView typeText = (TextView)rootView.findViewById(R.id.typeDetailText);
            TextView costText = (TextView)rootView.findViewById(R.id.costDetailText);
            TextView textText = (TextView)rootView.findViewById(R.id.cardTextText);
            TextView flavorTextText = (TextView)rootView.findViewById(R.id.cardFlavorTextText);
            TextView expansionText = (TextView)rootView.findViewById(R.id.expansionText);
            TextView artistText = (TextView)rootView.findViewById(R.id.artistText);
            
            // TODO:
            //  1) replace static strings with R values (for localization and whatnot)
            //  2) make mana costs not text
            //  3) All the fields durp
            cardDetail.setCardData(card);
            nameText.setText("Name: " + card.getName());
            typeText.setText("Type: " + card.getTypeString());
            costText.setText("Cost: " + card.getManaCost());
            textText.setText("Card Text: " + card.getText());
            expansionText.setText("Expansions: " + card.getSets().get(0));
            flavorTextText.setText("Flavor Text: " + card.getFlavorText());
        }
        
        return rootView;
    }
}
