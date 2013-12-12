package altik0.mtg.magictheorganizing.dialogFragments;

import java.util.ArrayList;

import altik0.mtg.magictheorganizing.R;
import altik0.mtg.magictheorganizing.Database.MtgDatabaseManager;
import altik0.mtg.magictheorganizing.Database.CollectionModel.Collection;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class AddCardToCollectionDialogFragment extends DialogFragment
{
    public static final String COUNT_KEY = "this is a count key! :D";
    public static final String TAGS_KEY = "this is a tags key... ._.";
    public static final String TITLE_KEY = "Dialog Title :o";
    
    // Used to track callback functions
    public interface DataAccepter
    {
        public void returnValues(int cnt, ArrayList<String> tags, Collection c);
    }
    
    private DataAccepter accepter;
    public void setDataAccepter (DataAccepter _accepter)
    {
        accepter = _accepter;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Bundle args = getArguments();
        int count = 0;
        ArrayList<String> tags = new ArrayList<String>();
        if (args.containsKey(COUNT_KEY))
            count = args.getInt(COUNT_KEY);
        if (args.containsKey(TAGS_KEY))
            tags = (ArrayList<String>)args.getSerializable(TAGS_KEY);
        
        AlertDialog.Builder editLocDialog = new AlertDialog.Builder(getActivity());
        
        // Only set the title if we were given one:
        if (args.containsKey(TITLE_KEY))
            editLocDialog.setTitle(args.getString(TITLE_KEY));
        final ViewGroup v = (ViewGroup)View.inflate(getActivity(),
                                                    R.layout.add_to_collection_view,
                                                    null);
        final EditText cntBox = (EditText)v.findViewById(R.id.countBox);
        cntBox.setText(Integer.toString(count));
        // TODO: tags
        final Spinner colSpinner = (Spinner)v.findViewById(R.id.collectionDialogSpinner);
        ArrayList<Collection> collections = MtgDatabaseManager.getInstance(getActivity()).GetCollections();
        ArrayAdapter<Collection> ad = new ArrayAdapter<Collection>(
                getActivity(), 
                android.R.layout.simple_spinner_item,
                collections);
        colSpinner.setAdapter(ad);
        editLocDialog.setView(v);
        
        // TODO: use strings in resources
        editLocDialog.setPositiveButton("Done",
                // Listener inside a listener! :o
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        // If our accepter is null, we can't do anything anyway
                        if (accepter == null)
                            return;
                        
                        int cnt = 0;
                        try
                        {
                            cnt = Integer.parseInt(cntBox.getText().toString());
                        }
                        // If we couldn't parse the int, just assume that it's 0
                        catch (NumberFormatException e)
                        {
                            cnt = 0;
                        }

                        // TODO: tags
                        accepter.returnValues(
                                cnt,
                                new ArrayList<String>(),
                                (Collection)colSpinner.getSelectedItem());
                    }
                });
        editLocDialog.setNegativeButton("Cancel",
                // Listener inside a listener! :o
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        // Do nothing - they're cancelling
                    }
                });
        
        return editLocDialog.create();
    }
}
