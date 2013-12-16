package altik0.mtg.magictheorganizing;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import altik0.mtg.magictheorganizing.Database.CollectionModel.Collection;
import altik0.mtg.magictheorganizing.Database.MtgDatabaseManager;
import altik0.mtg.magictheorganizing.Database.CollectionModel.Location;
import altik0.mtg.magictheorganizing.Database.MtgDatabaseManager.DatabaseListener;
import altik0.mtg.magictheorganizing.MtgDataTypes.CardData;
import altik0.mtg.magictheorganizing.dialogFragments.AddCardToCollectionDialogFragment;
import altik0.mtg.magictheorganizing.dialogFragments.AddCardToCollectionDialogFragment.DataAccepter;
import altik0.mtg.magictheorganizing.dialogFragments.EditLocationDialogFragment;
import altik0.mtg.magictheorganizing.dialogFragments.PromptCountAndTagsDialogFragment;
import altik0.mtg.magictheorganizing.dialogFragments.PromptCountAndTagsDialogFragment.CountAndTagAccepter;
import altik0.mtg.magictheorganizing.views.CardView;

/**
 * A fragment representing a single Card detail screen. This fragment is either
 * contained in a {@link CardListActivity} in two-pane mode (on tablets) or a
 * {@link CardDetailActivity} on handsets.
 */
public class CardDetailFragment extends Fragment implements DatabaseListener
{
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String COLLECTION_MODE = "colMode";
    public static final String CARD_RETURN_KEY = "thisIsACard!";
    public static final String DIALOG_TAG = "dialog";
    public static final String STATE_KEY = "state saving yayayay!";
    
    public interface ContainerCallbacks
    {
        public void detailContentDeleted();
    }
    private ContainerCallbacks container;
    
    public void setContainerCallbacks(ContainerCallbacks _container)
    {
        container = _container;
    }
    
    // States the fragment can be in
    //     Normal: no buttons, no collection data
    //     Collection: buttons for edit/delete, collection data
    //     Return: select button, no collection data
    public enum DetailFragmentState
    {
        Normal, Collection, Return,
    }
    private DetailFragmentState state = DetailFragmentState.Normal;
    private boolean collectionMode;
    private int id;
    private Button selectButton;
    private Button editButton;
    private Button removeButton;
    private Button addButton;
    private ViewGroup buttonSet;
    private ViewGroup collectionButtons;
    private ViewGroup collectionData;
    private ViewGroup detailSet;
    
    private OnClickListener selectCardListener = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            displayPromptCountAndTagsPopupForReturn(card.getCount(), card.getTags());
        }
    };
    
    private OnClickListener editListener = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            displayPromptCountAndTagsPopupForEdit(card.getCount(), card.getTags());
        }
    };
    
    private OnClickListener removeListener = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            // Unregister ourselves from the database manager, since our data is going away:
            MtgDatabaseManager.UnregisterListener(CardDetailFragment.this);
            // Perform delete:
            MtgDatabaseManager.getInstance(getActivity()).DeleteCardFromCollection(card.getCollectedId());
            // If we delete the card, we need to tell the activity - this fragment
            // is now invalid, and we may need to end, or possibly refresh with new content
            informDelete();
        }
    };
    
    private OnClickListener addListener = new OnClickListener()
    {
        @Override
        public void onClick(View arg0)
        {
            displayAddCardToCollectionDialog(card.getCount(), card.getTags());
        }  
    };
    
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
            id = getArguments().getInt(ARG_ITEM_ID);
            // If we're in collection mode, the id is the collectedId.
            // Otherwise, it is the cardId.
            collectionMode = getArguments().getBoolean(COLLECTION_MODE, false);
            if (collectionMode)
                card = MtgDatabaseManager.getInstance(getActivity()).GetCardWithCollectionId(id);
            else
                card = MtgDatabaseManager.getInstance(getActivity()).GetCardWithId(id);
        }
        
        // Register to the database's listeners:
        MtgDatabaseManager.RegisterListener(this);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        if (savedInstanceState != null)
        {
            if (savedInstanceState.containsKey(STATE_KEY))
            {
                switch(savedInstanceState.getInt(STATE_KEY))
                {
                    case 0:
                        state = DetailFragmentState.Normal;
                        break;
                    case 1:
                        state = DetailFragmentState.Collection;
                        break;
                    case 2:
                    default:
                        state = DetailFragmentState.Return;
                        break;
                }
            }
        }
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_card_detail,
                container, false);
        
        selectButton = (Button)rootView.findViewById(R.id.selectCardButton);
        editButton = (Button)rootView.findViewById(R.id.editButton);
        removeButton = (Button)rootView.findViewById(R.id.removeButton);
        addButton = (Button)rootView.findViewById(R.id.addCardButton);
        buttonSet = (ViewGroup)rootView.findViewById(R.id.detailButtonSet);
        collectionData = (ViewGroup)rootView.findViewById(R.id.detailsCollection);
        collectionButtons = (ViewGroup)rootView.findViewById(R.id.collectionButtonList);
        detailSet = (ViewGroup)rootView.findViewById(R.id.details_layout);
        
        // Hookup button click listeners:
        selectButton.setOnClickListener(selectCardListener);
        editButton.setOnClickListener(editListener);
        removeButton.setOnClickListener(removeListener);
        addButton.setOnClickListener(addListener);
        
        // Show the dummy content as text in a TextView.
        if (card != null)
        {
            CardView cardDetail = (CardView)rootView.findViewById(R.id.cardDetail);
            TextView nameText = (TextView)rootView.findViewById(R.id.nameDetailText);
            TextView typeText = (TextView)rootView.findViewById(R.id.typeDetailText);
            TextView costText = (TextView)rootView.findViewById(R.id.costDetailText);
            TextView ptText = (TextView)rootView.findViewById(R.id.ptDetailText);
            TextView textText = (TextView)rootView.findViewById(R.id.cardTextText);
            TextView flavorTextText = (TextView)rootView.findViewById(R.id.cardFlavorTextText);
            TextView expansionText = (TextView)rootView.findViewById(R.id.expansionText);
            //TextView artistText = (TextView)rootView.findViewById(R.id.artistText);
            TextView countText = (TextView)rootView.findViewById(R.id.countText);
            //TextView tagText = (TextView)rootView.findViewById(R.id.tagText);
            
            // TODO:
            //  1) replace static strings with R values (for localization and whatnot)
            //  2) make mana costs not text
            //  3) All the fields durp
            cardDetail.setCardData(card);
            nameText.setText(card.getName());
            typeText.setText(card.getTypeString());
            costText.setText(card.getManaCost());
            ptText.setText(card.getPowerToughnessString());
            textText.setText(card.getText());
            expansionText.setText(card.getSets().get(0));
            flavorTextText.setText(card.getFlavorText());
            
            // We'll set these fields, but delete them later if necessary
            countText.setText(Integer.toString(card.getCount()));
            //tagText.setText("Tags: " + card.getTags());
        }
        
        // Delete appropriate content based on state
        if (state == DetailFragmentState.Normal)
        {
            buttonSet.removeView(collectionButtons);
            buttonSet.removeView(selectButton);
            detailSet.removeView(collectionData);
        }
        else if (state == DetailFragmentState.Return)
        {
            buttonSet.removeView(collectionButtons);
            buttonSet.removeView(addButton);
            detailSet.removeView(collectionData);
        }
        else // if(state == DetailFragmentState.Collection)
        {
            buttonSet.removeView(addButton);
            buttonSet.removeView(selectButton);
        }
        
        return rootView;
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        int stateVal = 0;
        switch(state)
        {
            case Normal:
                stateVal = 0;
                break;
            case Collection:
                stateVal = 1;
                break;
            case Return:
            default:
                stateVal = 2;
                break;
        }
        outState.putInt(STATE_KEY, stateVal);
        
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy()
    {
        // Be sure to unregister before we're supposed to go away
        MtgDatabaseManager.UnregisterListener(this);
        
        super.onDestroy();
    }
    
    public void setState(DetailFragmentState newState)
    {
        if (state != newState)
        {
            // Step 1: return stuff we deleted
            ViewGroup rootView = (ViewGroup)getView();
            if (rootView != null)
            {
                if (state == DetailFragmentState.Normal)
                {
                    buttonSet.addView(collectionButtons);
                    buttonSet.addView(selectButton);
                    detailSet.addView(collectionData);
                }
                else if (state == DetailFragmentState.Return)
                {
                    buttonSet.addView(collectionButtons);
                    buttonSet.addView(addButton);
                    detailSet.addView(collectionData);
                }
                else // if(state == DetailFragmentState.Collection)
                {
                    buttonSet.addView(addButton);
                    buttonSet.addView(selectButton);
                }
            }
            
            // Step 2: delete stuff we now want gone
            state = newState;
            if (rootView != null)
            {
                if (state == DetailFragmentState.Normal)
                {
                    buttonSet.removeView(collectionButtons);
                    buttonSet.removeView(selectButton);
                    detailSet.removeView(collectionData);
                }
                else if (state == DetailFragmentState.Return)
                {
                    buttonSet.removeView(collectionButtons);
                    buttonSet.removeView(addButton);
                    detailSet.removeView(collectionData);
                }
                else // if(state == DetailFragmentState.Collection)
                {
                    buttonSet.removeView(addButton);
                    buttonSet.removeView(selectButton);
                }
            }
        }
    }
    
    private void displayAddCardToCollectionDialog(int count, ArrayList<String> tags)
    {
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        Fragment oldDialog = getFragmentManager().findFragmentByTag(DIALOG_TAG);
        if (oldDialog != null)
            trans.remove(oldDialog);
        
        //trans.addToBackStack(null);
        
        AddCardToCollectionDialogFragment newDialog = new AddCardToCollectionDialogFragment();
        Bundle args = new Bundle();
        args.putInt(AddCardToCollectionDialogFragment.COUNT_KEY, count);
        args.putSerializable(AddCardToCollectionDialogFragment.TAGS_KEY, tags);
        // TODO: don't use static titles
        args.putString(AddCardToCollectionDialogFragment.TITLE_KEY, "Insert into Collection");
        newDialog.setArguments(args);
        newDialog.setDataAccepter(new DataAccepter()
        {
            @Override
            public void returnValues(int cnt, ArrayList<String> tags, Collection c)
            {
                // TODO: new prompt in this case
                // If count is 0, do nothing:
                if (cnt == 0)
                    return;
                else
                {
                    CardData newCard = new CardData(card);
                    newCard.setCount(cnt);
                    newCard.setTags(tags);
                    MtgDatabaseManager.getInstance(getActivity()).AddCardToCollection(
                            c.CollectionId, newCard);
                }
            }
        });
        
        newDialog.show(trans, DIALOG_TAG);
    }
    
    private void displayPromptCountAndTagsPopupForEdit(int count, ArrayList<String> tags)
    {
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        Fragment oldDialog = getFragmentManager().findFragmentByTag(DIALOG_TAG);
        if (oldDialog != null)
            trans.remove(oldDialog);
        
        //trans.addToBackStack(null);
        
        PromptCountAndTagsDialogFragment newDialog = new PromptCountAndTagsDialogFragment();
        Bundle args = new Bundle();
        args.putInt(PromptCountAndTagsDialogFragment.COUNT_KEY, count);
        args.putSerializable(PromptCountAndTagsDialogFragment.TAGS_KEY, tags);
        // TODO: don't use static titles
        args.putString(PromptCountAndTagsDialogFragment.TITLE_KEY, "Edit Data");
        newDialog.setArguments(args);
        newDialog.setCountAndTagAccepter(new CountAndTagAccepter()
        {
            @Override
            public void returnValues(int cnt, ArrayList<String> tags)
            {
                // If count is 0, instead delete the card:
                if (cnt == 0)
                {
                 // Unregister ourselves from the database manager, since our data is going away:
                    MtgDatabaseManager.UnregisterListener(CardDetailFragment.this);
                    // Perform delete:
                    MtgDatabaseManager.getInstance(getActivity()).DeleteCardFromCollection(card.getCollectedId());
                    // If we delete the card, we need to tell the activity - this fragment
                    // is now invalid, and we may need to end, or possibly refresh with new content
                    informDelete();
                }
                else
                {
                    card.setCount(cnt);
                    card.setTags(tags);
                    MtgDatabaseManager.getInstance(getActivity()).UpdateCollectedCard(
                            card.getCollectedId(), card);
                }
            }
        });
        
        newDialog.show(trans, DIALOG_TAG);
    }
    
    private void displayPromptCountAndTagsPopupForReturn(int count, ArrayList<String> tags)
    {
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        Fragment oldDialog = getFragmentManager().findFragmentByTag(DIALOG_TAG);
        if (oldDialog != null)
            trans.remove(oldDialog);
        
        //trans.addToBackStack(null);
        
        PromptCountAndTagsDialogFragment newDialog = new PromptCountAndTagsDialogFragment();
        Bundle args = new Bundle();
        args.putInt(PromptCountAndTagsDialogFragment.COUNT_KEY, count);
        args.putSerializable(PromptCountAndTagsDialogFragment.TAGS_KEY, tags);
        // TODO: don't use static titles
        args.putString(PromptCountAndTagsDialogFragment.TITLE_KEY, "Enter Data");
        newDialog.setArguments(args);
        newDialog.setCountAndTagAccepter(new CountAndTagAccepter()
        {
            @Override
            public void returnValues(int cnt, ArrayList<String> tags)
            {
                // TODO: if count is 0, present popup alerting error, and continue.
                // For now, just don't return anything
                if (cnt == 0)
                    return;
                
                card.setCount(cnt);
                card.setTags(tags);
                Intent returnIntent = new Intent();
                returnIntent.putExtra(CARD_RETURN_KEY, card);
                getActivity().setResult(Activity.RESULT_OK, returnIntent);
                getActivity().finish();
            }
        });
        
        newDialog.show(trans, DIALOG_TAG);
    }

    @Override
    public void refresh()
    {
        // Obtain card
        if (collectionMode)
            card = MtgDatabaseManager.getInstance(getActivity()).GetCardWithCollectionId(id);
        else
            card = MtgDatabaseManager.getInstance(getActivity()).GetCardWithId(id);
        
        // Update views
        ViewGroup rootView = (ViewGroup)getView();
        if (card != null)
        {
            CardView cardDetail = (CardView)rootView.findViewById(R.id.cardDetail);
            TextView nameText = (TextView)rootView.findViewById(R.id.nameDetailText);
            TextView typeText = (TextView)rootView.findViewById(R.id.typeDetailText);
            TextView costText = (TextView)rootView.findViewById(R.id.costDetailText);
            TextView ptText = (TextView)rootView.findViewById(R.id.ptDetailText);
            TextView textText = (TextView)rootView.findViewById(R.id.cardTextText);
            TextView flavorTextText = (TextView)rootView.findViewById(R.id.cardFlavorTextText);
            TextView expansionText = (TextView)rootView.findViewById(R.id.expansionText);
            //TextView artistText = (TextView)rootView.findViewById(R.id.artistText);
            TextView countText = (TextView)collectionData.findViewById(R.id.countText);
            //TextView tagText = (TextView)collectionData.findViewById(R.id.tagText);
            
            // TODO:
            //  2) make mana costs not text
            //  3) All the fields durp
            cardDetail.setCardData(card);
            nameText.setText(card.getName());
            typeText.setText(card.getTypeString());
            costText.setText(card.getManaCost());
            ptText.setText(card.getPowerToughnessString());
            textText.setText(card.getText());
            expansionText.setText(card.getSets().get(0));
            flavorTextText.setText(card.getFlavorText());
            
            // We'll set these fields, but delete them later if necessary
            countText.setText(Integer.toString(card.getCount()));
            //tagText.setText(card.getTags());
        }
    }
    
    private void informDelete()
    {
        if (container != null)
            container.detailContentDeleted();
    }
}
