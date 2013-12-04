package altik0.mtg.magictheorganizing.dialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

public class AddLocationDialogFragment extends DialogFragment
{
    // Used to track callback functions
    public interface AddLocationHolder
    {
        public void addLocation(String newName);
    }
    
    private AddLocationHolder locHolder;
    private EditText dialogTextPrompt;
    public void setAddLocationHolder (AddLocationHolder _locHolder)
    {
        locHolder = _locHolder;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        dialogTextPrompt = new EditText(getActivity());
        AlertDialog.Builder addLocDialog = new AlertDialog.Builder(getActivity());
        // TODO: use strings in resources
        addLocDialog.setTitle("Add Location");
        addLocDialog.setView(dialogTextPrompt);
        addLocDialog.setMessage("Name of the location");
        addLocDialog.setPositiveButton("Add",
                // Listener inside a listener! :o
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        String newName = dialogTextPrompt.getText().toString();
                        locHolder.addLocation(newName);
                    }
                });
        addLocDialog.setNegativeButton("Cancel",
                // Listener inside a listener! :o
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        // Do nothing - they're cancelling
                    }
                });
        
        return addLocDialog.create();
    }
}
