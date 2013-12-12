package altik0.mtg.magictheorganizing.dialogFragments;

import java.util.ArrayList;

import altik0.mtg.magictheorganizing.Database.CollectionModel.Location;
import altik0.mtg.magictheorganizing.Database.MtgDatabaseManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SelectLocationDialogFragment extends DialogFragment
{
    // Used to track callback functions
    public interface LocationAccepter
    {
        public void returnLocation(Location l);
    }
    
    private LocationAccepter accepter;
    public void setLocationAccepter (LocationAccepter _accepter)
    {
        accepter = _accepter;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder editLocDialog = new AlertDialog.Builder(getActivity());
        // TODO: not static string
        editLocDialog.setTitle("Select Location");
        final Spinner locSpinner = new Spinner(getActivity());
        ArrayList<Location> locations = MtgDatabaseManager.getInstance(getActivity()).GetLocations();
        ArrayAdapter<Location> ad = new ArrayAdapter<Location>(
                getActivity(), 
                android.R.layout.simple_spinner_item,
                locations);
        locSpinner.setAdapter(ad);
        editLocDialog.setView(locSpinner);
        
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
                        
                        accepter.returnLocation((Location)locSpinner.getSelectedItem());
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
