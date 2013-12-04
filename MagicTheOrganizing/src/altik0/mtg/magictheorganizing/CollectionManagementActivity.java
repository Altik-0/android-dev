package altik0.mtg.magictheorganizing;

import java.util.ArrayList;
import java.util.HashMap;

import altik0.mtg.magictheorganizing.Database.MtgDatabaseManager;
import altik0.mtg.magictheorganizing.dialogFragments.*;
import altik0.mtg.magictheorganizing.dialogFragments.AddCollectionDialogFragment.AddCollectionHolder;
import altik0.mtg.magictheorganizing.dialogFragments.AddLocationDialogFragment.AddLocationHolder;
import altik0.mtg.magictheorganizing.dialogFragments.EditCollectionDialogFragment.EditCollectionHolder;
import altik0.mtg.magictheorganizing.dialogFragments.RenameCollectionDialogFragment.RenameCollectionHolder;
import altik0.mtg.magictheorganizing.dialogFragments.RenameLocationDialogFragment.RenameLocationHolder;
import altik0.mtg.magictheorganizing.dialogFragments.EditLocationDialogFragment.EditLocationHolder;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
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


// MASSIVE TODO:
//  Need to refactor to use a more rigorous model than a basic HashMap,
//  because in practice I need to keep track of location and collection
//  ids for proper identification in the database

public class CollectionManagementActivity extends Activity implements ListAdapter,
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
    
    private HashMap<String, ArrayList<String>> collectionMap;
    
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
            String content = (String)CollectionManagementActivity.this.getItem(index);
            int type = CollectionManagementActivity.this.getItemViewType(index);
            
            switch (type)
            {
                // Location:
                case 0:
                    displayEditLocationPopup(content);
                    break;
                // Collection:
                case 1:
                    displayEditCollectionPopup(content);
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
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                long arg3)
        {
            // TODO Auto-generated method stub
            
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
    public int getCount()
    {
        // Count is equal to the number of locations (for headers)
        // plus the number of total collections
        int cnt = collectionMap.keySet().size();
        
        for (String key : collectionMap.keySet())
        {
            cnt += collectionMap.get(key).size();
        }
        
        return cnt;
    }

    @Override
    public Object getItem(int index)
    {
        // Iterate through. We may end on a header, or we may end on a
        // collection - just depends.
        int pos = index;
        for (String key : collectionMap.keySet())
        {
            if (pos == 0)
                return key;
            pos--;
            
            ArrayList<String> collections = collectionMap.get(key);
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
        for (String key : collectionMap.keySet())
        {
            if (pos == 0)
                return 0;
            pos--;
            
            ArrayList<String> collections = collectionMap.get(key);
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
        tv.setText((String)getItem(position));
        
        // Settings based on type:
        switch (getItemViewType(position))
        {
            case 0:
                tv.setBackgroundColor(0x88000000);
                tv.setTextColor(0xFFFFFFFF);
                tv.setHeight(75);
                tv.setTextSize(20.0f);
                //tv.setTextAlignment(TextView.TEXT_ALIGNMENT_TEXT_START);
                break;
            case 1:
            default:
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
        // TODO
        return state == ActivityState.editing;
    }

    @Override
    public boolean isEnabled(int arg0)
    {
        // TODO
        return state == ActivityState.editing;
    }
    
    private void refresh()
    {
        collectionMap = MtgDatabaseManager.getInstance(this).GetLocationsWithCollections();
        ListView lv = (ListView)findViewById(R.id.contentManagementList);
        lv.invalidateViews();
    }
    
    private void displayEditLocationPopup(final String locationName)
    {
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        Fragment oldDialog = getFragmentManager().findFragmentByTag(DIALOG_TAG);
        if (oldDialog != null)
            trans.remove(oldDialog);
        
        //trans.addToBackStack(null);
        
        EditLocationDialogFragment newDialog = new EditLocationDialogFragment();
        Bundle args = new Bundle();
        args.putString(EditLocationDialogFragment.LOCATION_NAME_KEY, locationName);
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
    
    private void displayEditCollectionPopup(final String collectionName)
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
        args.putString(EditCollectionDialogFragment.COLLECTION_NAME_KEY, collectionName);
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
    public void deleteLocation(String locationName)
    {
        // TODO: prompt again, probably. For now, just fuck
        // the user and delete their shit with no regard for
        // their feelings of accidents
        MtgDatabaseManager.getInstance(this).DeleteLocation(locationName);
        refresh();
    }

    @Override
    public void renameLocation(String locationName)
    {
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        Fragment oldDialog = getFragmentManager().findFragmentByTag(DIALOG_TAG);
        if (oldDialog != null)
            trans.remove(oldDialog);
        
        //trans.addToBackStack(null);
        
        RenameLocationDialogFragment newDialog = new RenameLocationDialogFragment();
        Bundle args = new Bundle();
        args.putString(RenameLocationDialogFragment.LOCATION_NAME_KEY, locationName);
        newDialog.setArguments(args);
        newDialog.setRenameLocationHolder(this);
        
        newDialog.show(trans, DIALOG_TAG);
    }

    @Override
    public void addCollection(String locationName)
    {
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        Fragment oldDialog = getFragmentManager().findFragmentByTag(DIALOG_TAG);
        if (oldDialog != null)
            trans.remove(oldDialog);
        
        //trans.addToBackStack(null);
        
        AddCollectionDialogFragment newDialog = new AddCollectionDialogFragment();
        Bundle args = new Bundle();
        args.putString(AddCollectionDialogFragment.LOCATION_NAME_KEY, locationName);
        newDialog.setArguments(args);
        newDialog.setAddCollectionHolder(this);
        
        newDialog.show(trans, DIALOG_TAG);
    }

    @Override
    public void addLocation(String newName)
    {
        MtgDatabaseManager.getInstance(this).AddLocation(newName);
        refresh();
    }

    @Override
    public void renameLocationWithName(String oldName, String newName)
    {
        MtgDatabaseManager.getInstance(this).RenameLocation(oldName, newName);
        refresh();
    }

    @Override
    public void addCollectionWithName(String locationName, String newName)
    {
        MtgDatabaseManager.getInstance(this).AddCollectionToLocation(locationName, newName);
        refresh();
    }

    @Override
    public void deleteCollection(String collectionName)
    {
        // TODO probably want to prompt user that they're sure
        // for now, just do it
        MtgDatabaseManager.getInstance(this).DeleteCollection(collectionName);
        refresh();
    }

    @Override
    public void renameCollection(String collectionName)
    {
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        Fragment oldDialog = getFragmentManager().findFragmentByTag(DIALOG_TAG);
        if (oldDialog != null)
            trans.remove(oldDialog);
        
        //trans.addToBackStack(null);
        
        RenameCollectionDialogFragment newDialog = new RenameCollectionDialogFragment();
        Bundle args = new Bundle();
        args.putString(RenameCollectionDialogFragment.COLLECTION_NAME_KEY, collectionName);
        newDialog.setArguments(args);
        newDialog.setRenameCollectionHolder(this);
        
        newDialog.show(trans, DIALOG_TAG);
    }

    @Override
    public void copyCollection(String collectionName)
    {
        MtgDatabaseManager.getInstance(this).CopyCollection(collectionName, "Copy of " + collectionName);
        refresh();
    }

    @Override
    public void moveCollection(String collectionName)
    {
        // TODO: this is a silly feature that will take too long to implement probably
    }

    @Override
    public void renameCollectionWithName(String oldName, String newName)
    {
        MtgDatabaseManager.getInstance(this).RenameCollection(oldName, newName);
        refresh();
    }
}
