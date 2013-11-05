package utah.edu.cs4962.collage.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class CollageModel
{
    // TODO: something smarter?
    public static final int THUMB_WIDTH  = 100;
    public static final int THUMB_HEIGHT = 100;
    
    // Struct holding data loaded from path and interesting for user
    public class LibraryElementData
    {
        public Bitmap thumbnail;
        public int width;
        public int height;
        public Date lastModified;
        
        public LibraryElementData(Bitmap _thumbnail, int _width, int _height, Date _lastModified)
        {
            thumbnail = _thumbnail;
            width = _width;
            height = _height;
            lastModified = _lastModified;
        }
    }
    
    // Data regarding image in the collage
    public class CollageEntry
    {
        public Bitmap image;
        public Rect bounds;
        public String path;
        
        public CollageEntry(String _path)
        {
            path = _path;
            
            // Load image
            File imageFile = new File(path);
            if (imageFile.exists())
            {
                image = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                bounds = new Rect(0, 0, image.getWidth(), image.getHeight());
            }
            else
            {
                // ??? Dunno what to do if the image doesn't exist
            }
        }
        
        public Bitmap getScaledImage()
        {
            int width = bounds.width();
            int height = bounds.height();
            return Bitmap.createScaledBitmap(image, width, height, true);
        }
        
        public void setPosition(int x, int y)
        {
            bounds.left += x;
            bounds.right += x;
            bounds.top += y;
            bounds.bottom += y;
        }
        
        // TODO: scale cenetered at position
        public void scale(float scalar)
        {
            int widthChange  = (int)(bounds.width()  * scalar) - bounds.width();
            int heightChange = (int)(bounds.height() * scalar) - bounds.height();
            
            bounds.left += (widthChange / 2);
            bounds.right = bounds.left + (int)(bounds.width() * scalar);
            bounds.top += (heightChange / 2);
            bounds.bottom = bounds.top + (int)(bounds.height() * scalar);
        }
        
        public int getX()
        {
            return bounds.left;
        }
        
        public int getY()
        {
            return bounds.top;
        }
    }
    
    // Singleton accessor
    private CollageModel instance = null;
    public synchronized CollageModel getInstance()
    {
        if (instance == null)
            instance = new CollageModel();
        return instance;
    }
    
    // List of images inserted into the library. Listed as filepaths
    ArrayList<String> libraryPaths;
    
    // List of images already in collage
    ArrayList<CollageEntry> collageEntries;
    
    private CollageModel()
    {
        libraryPaths = new ArrayList<String>();
    }
    
    // TODO: remove? Not req.
    
    public synchronized void addImage(String path)
    {
        // do not insert if path already included
        if (libraryPaths.contains(path))
            return;
        libraryPaths.add(path);
    }
    
    public synchronized String[] getPaths()
    {
        return (String[])libraryPaths.toArray();
    }
    
    public synchronized LibraryElementData getDataForIndex(int index)
    {
        String path = libraryPaths.get(index);
        File f = new File(path);
        
        Date lastModified = new Date(f.lastModified());
        // TODO: something smart in case file doesn't exist
        Bitmap img = BitmapFactory.decodeFile(path);
        int width = img.getWidth();
        int height = img.getHeight();
        Bitmap thumbnail = Bitmap.createScaledBitmap(img, THUMB_WIDTH, THUMB_HEIGHT, true);
        return new LibraryElementData(thumbnail, width, height, lastModified);
    }
    
    public synchronized void addLibraryElementToCollage(int index)
    {
        String path = libraryPaths.get(index);
        CollageEntry entry = new CollageEntry(path);
        collageEntries.add(entry);
    }
    
    public synchronized CollageEntry getEntryFromLibraryIndex(int index)
    {
        String path = libraryPaths.get(index);
        
        for (CollageEntry entry : collageEntries)
        {
            if (entry.path == path)
                return entry;
        }
        
        return null;
    }
    
    public synchronized void RemoveCollageEntry(int index)
    {
        collageEntries.remove(index);
    }
    
    public synchronized CollageEntry[] getCollageEntries()
    {
        return (CollageEntry[])collageEntries.toArray();
    }
}
