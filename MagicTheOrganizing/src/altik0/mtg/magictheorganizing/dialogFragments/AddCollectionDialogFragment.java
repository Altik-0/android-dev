package altik0.mtg.magictheorganizing.dialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

public class AddCollectionDialogFragment extends DialogFragment
{
public static final String LOCATION_NAME_KEY = "locationName";
    
    // Used to track callback functions
    public interface AddCollectionHolder
    {
        public void addCollectionWithName(String locationName, String newName);
    }
    
    private EditText dialogTextPrompt;
    private AddCollectionHolder locHolder;
    public void setAddCollectionHolder (AddCollectionHolder _locHolder)
    {
        locHolder = _locHolder;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        if (!getArguments().containsKey(LOCATION_NAME_KEY))
            throw new IllegalArgumentException("This dialog needs a location name!");
        
        final String locationName = getArguments().getString(LOCATION_NAME_KEY);
        
        dialogTextPrompt = new EditText(getActivity());
        
        AlertDialog.Builder addColDialog = new AlertDialog.Builder(getActivity());
        // TODO: use strings in resources
        addColDialog.setTitle("Add Collection");
        addColDialog.setMessage("Name of collection");
        addColDialog.setView(dialogTextPrompt);
        addColDialog.setPositiveButton("Add",
                // Listener inside a listener! :o
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        // If our locHolder is null, we can't do anything anyway
                        if (locHolder == null)
                            return;
                        
                        String newName = dialogTextPrompt.getText().toString();
                        locHolder.addCollectionWithName(locationName, newName);
                    }
                });
        addColDialog.setNegativeButton("Cancel",
                // Listener inside a listener! :o
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        // Do nothing - they're cancelling
                    }
                });
        
        return addColDialog.create();
    }
}
