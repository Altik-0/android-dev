package utah.edu.cs4962.collage.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v4.content.LocalBroadcastManager;

public class CollageModel
{
    // TODO: something smarter?
    public static final int THUMB_WIDTH  = 100;
    public static final int THUMB_HEIGHT = 100;
    
    // Struct holding data loaded from path and interesting for user
    public class LibraryElementData
    {
        public int ID;
        public Bitmap thumbnail;
        public int width;
        public int height;
        public Date lastModified;
        public String path;
        
        public LibraryElementData(int _ID, Bitmap _thumbnail,
                                  int _width, int _height, Date _lastModified,
                                  String _path)
        {
            ID = _ID;
            thumbnail = _thumbnail;
            width = _width;
            height = _height;
            lastModified = _lastModified;
            path = _path;
        }
    }
    
    // Data regarding image in the collage
    public class CollageEntry
    {
        public int imageID;
        private Rect bounds;
        
        public CollageEntry(int _imageID)
        {
            imageID = _imageID;
            
            // Set default bounds
            // TODO: properly scale image. For now, just go with width/heigh of collage
            bounds = new Rect(0, 0, width, height);
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
        
        public int getWidth()
        {
            return bounds.width();
        }
        
        public int getHeight()
        {
            return bounds.height();
        }
        
        public Rect getBounds()
        {
            return new Rect(bounds);
        }
    }
    
    // Singleton accessor
    private static CollageModel instance = null;
    public synchronized static CollageModel getInstance()
    {
        if (instance == null)
            instance = new CollageModel();
        return instance;
    }
    
    // List of images inserted into the library. Listed as filepaths
    private LinkedList<LibraryElementData> libraryPaths;
    
    // List of images already in collage
    private LinkedList<CollageEntry> collageEntries;
    
    // Cache to handle loading images
    private SimpleCache<Bitmap, Integer> imageCache;
    private static final int MAX_CACHE_SIZE = 3;    // TODO: tweak to work nicely
    
    // Cached bitmap. We'll update this regularly as necessary
    private Bitmap currentImage;
    
    // Default size for the collage
    private int width = 100;
    private int height = 100;
    
    private CollageModel()
    {
        libraryPaths = new LinkedList<LibraryElementData>();
        collageEntries = new LinkedList<CollageEntry>();
        imageCache = new SimpleCache<Bitmap, Integer>(MAX_CACHE_SIZE);
        
        currentImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    }
    
    // TODO: remove? Not req.
    
    public synchronized void addImageToLibrary(String path)
    {
        // do not insert if path already included
        if (libraryPaths.contains(path))
            return;

        File f = new File(path);
        
        Date lastModified = new Date(f.lastModified());
        // TODO: something smart in case file doesn't exist
        // TODO: reconsider opening image to get thumbnail
        // TODO: MAYBE cache this (don't really want to, though tbh)
        Bitmap img = BitmapFactory.decodeFile(path);
        int width = img.getWidth();
        int height = img.getHeight();
        Bitmap thumbnail = Bitmap.createScaledBitmap(img, THUMB_WIDTH, THUMB_HEIGHT, true);
        libraryPaths.add(new LibraryElementData(
                libraryPaths.size(), thumbnail, width, height, lastModified, path));
    }
    
    public synchronized String[] getPaths()
    {
        return (String[])libraryPaths.toArray();
    }
    
    public synchronized LibraryElementData getDataForIndex(int index)
    {
        return libraryPaths.get(index);
    }
    
    public synchronized void addLibraryElementToCollage(int index)
    {
        // If index out of range, ignore it
        if (index > libraryPaths.size())
            return;
        CollageEntry entry = new CollageEntry(index);
        collageEntries.add(entry);
    }
    
    public synchronized CollageEntry getEntryFromLibraryIndex(int index)
    {
        // If index out of range, return null
        if (index > libraryPaths.size())
            return null;
        
        for (CollageEntry entry : collageEntries)
        {
            if (entry.imageID == index)
                return entry;
        }
        
        return null;
    }
    
    public synchronized void RemoveCollageEntry(int index)
    {
        collageEntries.remove(index);
        // TODO: cache this library element isn't contained anymore?
    }
    
    public synchronized CollageEntry[] getCollageEntries()
    {
        return (CollageEntry[])collageEntries.toArray();
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
    
    public int getLibraryCount()
    {
        return libraryPaths.size();
    }

    public void setWidth(int width)
    {
        this.width = width;
        currentImage = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        updateImage();
    }

    public void setHeight(int height)
    {
        this.height = height;
        currentImage = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        updateImage();
    }
    
    public void setWidthAndHeight(int _width, int _height)
    {
        width = _width;
        height = _height;
        currentImage = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        updateImage();
    }
    
    // TODO: make this do more. For now, just wipes the background
    private void updateImage()
    {
        Canvas canvas = new Canvas(currentImage);
        canvas.drawColor(Color.WHITE);
    }
    
    public Bitmap getRenderedCollage()
    {
        return currentImage;
    }
}
