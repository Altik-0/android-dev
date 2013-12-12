package altik0.mtg.magictheorganizing;

import java.util.ArrayList;
import java.util.HashMap;

import altik0.mtg.magictheorganizing.Database.CollectionModel;
import altik0.mtg.magictheorganizing.Database.CollectionModel.Collection;
import altik0.mtg.magictheorganizing.Database.CollectionModel.Location;
import altik0.mtg.magictheorganizing.Database.MtgDatabaseManager;
import altik0.mtg.magictheorganizing.Database.MtgDatabaseManager.DatabaseListener;
import altik0.mtg.magictheorganizing.Database.SearchParams;
import altik0.mtg.magictheorganizing.dialogFragments.*;
import altik0.mtg.magictheorganizing.dialogFragments.AddCollectionDialogFragment.AddCollectionHolder;
import altik0.mtg.magictheorganizing.dialogFragments.AddLocationDialogFragment.AddLocationHolder;
import altik0.mtg.magictheorganizing.dialogFragments.EditCollectionDialogFragment.EditCollectionHolder;
import altik0.mtg.magictheorganizing.dialogFragments.RenameCollectionDialogFragment.RenameCollectionHolder;
import altik0.mtg.magictheorganizing.dialogFragments.RenameLocationDialogFragment.RenameLocationHolder;
import altik0.mtg.magictheorganizing.dialogFragments.EditLocationDialogFragment.EditLocationHolder;
import altik0.mtg.magictheorganizing.dialogFragments.SelectLocationDialogFragment.LocationAccepter;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CollectionManagementActivity extends Activity implements ListAdapter,
                                                                      DatabaseListener,
                                                                      EditLocationHolder,
                                                                      AddLocationHolder,
                                                                      RenameLocationHolder,
                                                                      AddCollectionHolder,
                                                                      EditCollectionHolder,
                                                                      RenameCollectionHolder
{
    private static final String DIALOG_TAG = "dialog";
    
    // Used to track whether we're displaying edit data or not.
    private enum ActivityState
    {
        normal, editing,
    }
    private ActivityState state = ActivityState.normal;
    
    private CollectionModel collectionMap;
    
    private OnClickListener editListener = new OnClickListener()
    {
        @Override
        public void onClick(View arg0)
        {
            if (state == ActivityState.normal)
                CollectionManagementActivity.this.setState(ActivityState.editing);
            else
                CollectionManagementActivity.this.setState(ActivityState.normal);
            
            ListView lv = (ListView)findViewById(R.id.contentManagementList);
            lv.invalidateViews();
        }
    };

    // EditText used by various dialog boxes:
    private EditText dialogTextPrompt;
    private OnClickListener addLocationListener = new OnClickListener()
    {
        @Override
        public void onClick(View arg0)
        {
            CollectionManagementActivity.this.displayAddLocationPopup();
        }  
    };
    
    private OnItemClickListener editSelectedListener = new OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View clicked, int index, long id)
        {
            Object o = CollectionManagementActivity.this.getItem(index);
            int type = CollectionManagementActivity.this.getItemViewType(index);
            
            switch (type)
            {
                // Location:
                case 0:
                    displayEditLocationPopup((Location)o);
                    break;
                // Collection:
                case 1:
                    displayEditCollectionPopup((Collection)o);
                    break;
                // Add Location button:
                case 2:
                default:
                    // Add location
                    break;
            }
        }
    };
    
    private OnItemClickListener normalSelectedListener = new OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int index,
                long id)
        {
            // This only happens on a collection, so get the data about that
            // Collection, so what that we can pass it on to activities that care
            Collection c = (Collection)getItem(index);
            SearchParams params = new SearchParams();
            params.CollectionId = c.CollectionId;
            params.searchOverCollection = true;
            Intent collectionViewIntent = CardListActivity.buildSearchIntent(
                    CollectionManagementActivity.this, params, true, true);
            startActivity(collectionViewIntent);
        }
    };
    
    private FrameLayout addLocationButtonBox;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_management);
        
        // TODO: hook up to this button
        Button editButton = (Button)findViewById(R.id.contentManagementEditButton);
        editButton.setOnClickListener(editListener);
        
        collectionMap = MtgDatabaseManager.getInstance(this).GetLocationsWithCollections();
        ListView collectionList = (ListView)findViewById(R.id.contentManagementList);
        
        // Sortof a hack?
        // If the system is in edit mode, we want to have an add button.
        // HOWEVER: you can't add/remove footer views dynamically.
        // SO: we put in an empty frame view, which we insert the button into dynamically
        addLocationButtonBox = new FrameLayout(this);
        collectionList.addFooterView(addLocationButtonBox);
        collectionList.setAdapter(this);
        collectionList.setOnItemClickListener(normalSelectedListener);
        
        // Register ourselves as a database listener:
        MtgDatabaseManager.RegisterListener(this);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.collection_management, menu);
        return true;
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outBundle)
    {
        outBundle.putString("ActivityState", state.toString());
        super.onSaveInstanceState(outBundle);
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle inBundle)
    {
        super.onRestoreInstanceState(inBundle);
        if (inBundle.containsKey("ActivityState") &&
            inBundle.getString("ActivityState") == "editing")
            setState(ActivityState.editing);
        else
            setState(ActivityState.normal);
    }
    
    @Override
    public void onDestroy()
    {
        // Unregister ourselves from the database
        MtgDatabaseManager.UnregisterListener(this);
        
        super.onDestroy();
    }

    @Override
    public int getCount()
    {
        // Count is equal to the number of locations (for headers)
        // plus the number of total collections
        return collectionMap.LocationCount() + collectionMap.CollectionCount();
    }

    @Override
    public Object getItem(int index)
    {
        // Iterate through. We may end on a header, or we may end on a
        // collection - just depends.
        int pos = index;
        for (int l : collectionMap.collectionSet.keySet())
        {
            if (pos == 0)
                return collectionMap.LocationWithId(l);
            pos--;
            
            ArrayList<Collection> collections = collectionMap.collectionSet.get(l);
            if (pos < collections.size())
                return collections.get(pos);
            pos -= collections.size();
        }
        
        // We shan't ever get here, but because java doesn't believe me:
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public int getItemViewType(int index)
    {
        // Iterate through. We may end on a header, or we may end on a
        // collection - just depends.
        int pos = index;
        for (int l : collectionMap.collectionSet.keySet())
        {
            if (pos == 0)
                return 0;
            pos--;
            
            ArrayList<Collection> collections = collectionMap.collectionSet.get(l);
            if (pos < collections.size())
                return 1;
            pos -= collections.size();
        }
        
        // We should never get here, but since java doesn't believe me:
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // TODO: nicer views
        if (convertView == null || !(convertView instanceof TextView))
            convertView = new TextView(this);
        
        TextView tv = (TextView)convertView;
        
        // Settings based on type:
        switch (getItemViewType(position))
        {
            case 0:
                Location l = (Location)getItem(position);
                tv.setText(l.Name);
                tv.setBackgroundColor(0x88000000);
                tv.setTextColor(0xFFFFFFFF);
                tv.setHeight(75);
                tv.setTextSize(20.0f);
                //tv.setTextAlignment(TextView.TEXT_ALIGNMENT_TEXT_START);
                break;
            case 1:
            default:
                Collection c = (Collection)getItem(position);
                tv.setText(c.Name);
                tv.setBackgroundColor(0x00000000);
                tv.setTextColor(0xFF000000);
                tv.setHeight(150);
                tv.setTextSize(20.0f);
                //tv.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                break;
        }
        
        return tv;
    }

    @Override
    public int getViewTypeCount()
    {
        return 2;
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
        return false;
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
        return state == ActivityState.editing;
    }

    @Override
    public boolean isEnabled(int index)
    {
        if (state == ActivityState.editing)
            return true;
        
     // Iterate through. We may end on a header, or we may end on a
        // collection - just depends.
        int pos = index;
        for (int l : collectionMap.collectionSet.keySet())
        {
            if (pos == 0)
                return false;
            pos--;
            
            ArrayList<Collection> collections = collectionMap.collectionSet.get(l);
            if (pos < collections.size())
                return true;
            pos -= collections.size();
        }
        
        // We should never get here, but since java doesn't believe me:
        return false;
    }
    
    public void refresh()
    {
        collectionMap = MtgDatabaseManager.getInstance(this).GetLocationsWithCollections();
        ListView lv = (ListView)findViewById(R.id.contentManagementList);
        lv.invalidateViews();
    }
    
    private void displayEditLocationPopup(final Location l)
    {
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        Fragment oldDialog = getFragmentManager().findFragmentByTag(DIALOG_TAG);
        if (oldDialog != null)
            trans.remove(oldDialog);
        
        //trans.addToBackStack(null);
        
        EditLocationDialogFragment newDialog = new EditLocationDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(EditLocationDialogFragment.LOCATION_KEY, l);
        newDialog.setArguments(args);
        newDialog.setEditLocationHolder(this);
        
        newDialog.show(trans, DIALOG_TAG);
    }
    
    private void displayAddLocationPopup()
    {
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        Fragment oldDialog = getFragmentManager().findFragmentByTag(DIALOG_TAG);
        if (oldDialog != null)
            trans.remove(oldDialog);
        
        // TODO: do I want this? not sure. Taking it out seemed to fix errors... ?
        //trans.addToBackStack(null);
        
        AddLocationDialogFragment newDialog = new AddLocationDialogFragment();
        newDialog.setAddLocationHolder(this);
        
        newDialog.show(trans, DIALOG_TAG);
    }
    
    private void displayEditCollectionPopup(final Collection c)
    {
     // Options:
        //   - delete
        //   - rename
        //   - copy
        //   - move
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        Fragment oldDialog = getFragmentManager().findFragmentByTag(DIALOG_TAG);
        if (oldDialog != null)
            trans.remove(oldDialog);
        
        //trans.addToBackStack(null);
        
        EditCollectionDialogFragment newDialog = new EditCollectionDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(EditCollectionDialogFragment.COLLECTION_KEY, c);
        newDialog.setArguments(args);
        newDialog.setEditCollectionHolder(this);
        
        newDialog.show(trans, DIALOG_TAG);
    }
    
    private void setState(ActivityState newState)
    {
        // If the new state is the same, ignore our change
        if (newState == state)
            return;
        
        if (newState == ActivityState.editing)
        {
            Button addLocButton = new Button(CollectionManagementActivity.this);
            // TODO: give this button's text something from resources
            addLocButton.setText("Add Location...");
            addLocButton.setOnClickListener(addLocationListener);
            addLocationButtonBox.addView(addLocButton);
            addLocationButtonBox.setLayoutParams(
                    new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT,
                                              ListView.LayoutParams.WRAP_CONTENT));
            ListView collectionList = (ListView)findViewById(R.id.contentManagementList);
            collectionList.setOnItemClickListener(editSelectedListener);
            state = ActivityState.editing;
        }
        else
        {
            addLocationButtonBox.setLayoutParams(
                    new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT,
                                              0));
            addLocationButtonBox.removeAllViews();
            ListView collectionList = (ListView)findViewById(R.id.contentManagementList);
            collectionList.setOnItemClickListener(normalSelectedListener);
            state = ActivityState.normal;
        }
    }

    @Override
    public void deleteLocation(Location l)
    {
        // TODO: prompt again, probably. For now, just fuck
        // the user and delete their shit with no regard for
        // their feelings of accidents
        MtgDatabaseManager.getInstance(this).DeleteLocation(l.LocationId);
    }

    @Override
    public void renameLocation(Location l)
    {
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        Fragment oldDialog = getFragmentManager().findFragmentByTag(DIALOG_TAG);
        if (oldDialog != null)
            trans.remove(oldDialog);
        
        //trans.addToBackStack(null);
        
        RenameLocationDialogFragment newDialog = new RenameLocationDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(RenameLocationDialogFragment.LOCATION_KEY, l);
        newDialog.setArguments(args);
        newDialog.setRenameLocationHolder(this);
        
        newDialog.show(trans, DIALOG_TAG);
    }

    @Override
    public void addCollection(Location l)
    {
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        Fragment oldDialog = getFragmentManager().findFragmentByTag(DIALOG_TAG);
        if (oldDialog != null)
            trans.remove(oldDialog);
        
        //trans.addToBackStack(null);
        
        AddCollectionDialogFragment newDialog = new AddCollectionDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(AddCollectionDialogFragment.LOCATION_KEY, l);
        newDialog.setArguments(args);
        newDialog.setAddCollectionHolder(this);
        
        newDialog.show(trans, DIALOG_TAG);
    }

    @Override
    public void addLocation(String newName)
    {
        MtgDatabaseManager.getInstance(this).AddLocation(newName);
    }

    @Override
    public void renameLocationWithName(Location l, String newName)
    {
        MtgDatabaseManager.getInstance(this).RenameLocation(l.LocationId, newName);
    }

    @Override
    public void addCollectionWithName(Location l, String newName)
    {
        MtgDatabaseManager.getInstance(this).AddCollectionToLocation(l.LocationId, newName);
    }

    @Override
    public void deleteCollection(Collection c)
    {
        // TODO probably want to prompt user that they're sure
        // for now, just do it
        MtgDatabaseManager.getInstance(this).DeleteCollection(c.CollectionId);
    }

    @Override
    public void renameCollection(Collection c)
    {
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        Fragment oldDialog = getFragmentManager().findFragmentByTag(DIALOG_TAG);
        if (oldDialog != null)
            trans.remove(oldDialog);
        
        //trans.addToBackStack(null);
        
        RenameCollectionDialogFragment newDialog = new RenameCollectionDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(RenameCollectionDialogFragment.COLLECTION_KEY, c);
        newDialog.setArguments(args);
        newDialog.setRenameCollectionHolder(this);
        
        newDialog.show(trans, DIALOG_TAG);
    }

    @Override
    public void copyCollection(Collection c)
    {
        MtgDatabaseManager.getInstance(this).CopyCollection(c.CollectionId, "Copy of " + c.Name);
    }

    @Override
    public void moveCollection(final Collection c)
    {
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        Fragment oldDialog = getFragmentManager().findFragmentByTag(DIALOG_TAG);
        if (oldDialog != null)
            trans.remove(oldDialog);
        
        //trans.addToBackStack(null);
        
        SelectLocationDialogFragment newDialog = new SelectLocationDialogFragment();
        Bundle args = new Bundle();
        newDialog.setArguments(args);
        newDialog.setLocationAccepter(new LocationAccepter()
        {
            @Override
            public void returnLocation(Location l)
            {
                MtgDatabaseManager.
                    getInstance(CollectionManagementActivity.this).
                    MoveCollectionToLocation(c.CollectionId, l.LocationId);
            }
        });
        
        newDialog.show(trans, DIALOG_TAG);
    }

    @Override
    public void renameCollectionWithName(Collection c, String newName)
    {
        MtgDatabaseManager.getInstance(this).RenameCollection(c.CollectionId, newName);
    }
}
