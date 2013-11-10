package utah.edu.cs4962.collage;

import java.io.File;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FileBrowserActivity extends Activity implements ListAdapter
{
    private String[] paths;
    private String curPath = null;
    private File curDirectory = Environment.getExternalStorageDirectory();
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        File[] files = curDirectory.listFiles();
        paths = new String[files.length];
        for (int i = 0; i < files.length; i++)
            paths[i] = files[i].getAbsolutePath();

        final ListView lv = new ListView(this);
        lv.setAdapter(this);
        lv.setOnItemClickListener(new ListView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long id)
            {
                File f = new File(paths[index]);
                
                if (f.isDirectory())
                {
                    // Go deeper!
                    curDirectory = f;
                    File[] files = curDirectory.listFiles();
                    paths = new String[files.length];
                    for (int i = 0; i < files.length; i++)
                        paths[i] = files[i].getAbsolutePath();
                    
                }
                else if (f.isFile())
                {
                    // Set selected file!
                    curPath = f.getAbsolutePath();
                }
                else
                {
                    // ??? dafuq is a file neither a directory or a file?
                }
            }
        });
        
        Button okButton = new Button(this);
        Button cancelButton = new Button(this);
        okButton.setText("OK");
        cancelButton.setText("Cancel");
        
        okButton.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                finish();
            }
        });
        cancelButton.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                curPath = null;
                finish();
            }
        });
        
        LinearLayout MainLayout = new LinearLayout(this);
        LinearLayout ButtonLayout = new LinearLayout(this);
        FrameLayout listFrame = new FrameLayout(this);
        
        listFrame.addView(lv);
        
        ButtonLayout.addView(okButton, new LinearLayout.LayoutParams(
                                    0,
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    1));
        ButtonLayout.addView(cancelButton, new LinearLayout.LayoutParams(
                                    0,
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    1));
        
        MainLayout.setOrientation(LinearLayout.VERTICAL);
        MainLayout.addView(listFrame, new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT, 
                                    0, 9));
        MainLayout.addView(ButtonLayout, new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT, 
                                    200, 1));
        
        setContentView(MainLayout);
    }
    
    @Override
    public void finish()
    {
        if (curPath != null)
        {
            Intent okIntent = new Intent();
            okIntent.putExtra("selectedPath", curPath);
            setResult(RESULT_OK, okIntent);
        }
        else
        {
            Intent cancelIntent = new Intent();
            setResult(RESULT_CANCELED, cancelIntent);
        }
        super.finish();
    }

    @Override
    public int getCount()
    {
        return paths.length;
    }

    @Override
    public Object getItem(int position)
    {
        return new File(paths[position]).getName();
    }

    @Override
    public long getItemId(int position)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getItemViewType(int position)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView != null && convertView instanceof TextView)
        {
            TextView tv = (TextView)convertView;
            tv.setText((String)getItem(position));
            return tv;
        }
        TextView tv = new TextView(this);
        tv.setText((String)getItem(position));
        return tv;
    }

    @Override
    public int getViewTypeCount()
    {
        return 1;
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
        return paths.length > 0;
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
        return true;
    }

    @Override
    public boolean isEnabled(int arg0)
    {
        return true;
    }
}
