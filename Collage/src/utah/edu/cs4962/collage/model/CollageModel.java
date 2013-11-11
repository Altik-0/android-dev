package utah.edu.cs4962.collage.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.content.LocalBroadcastManager;

public class CollageModel
{
    // TODO: something smarter?
    public static final int THUMB_WIDTH  = 100;
    public static final int THUMB_HEIGHT = 100;
    // To ensure that we do not run out of space, images will never be stored
    // any larger than the below width and height.
    public static final int MAX_IMG_WIDTH = 1000;
    public static final int MAX_IMG_HEIGHT = 1000;
    
    // Listeners for broadcasts
    LinkedList<CollageUpdateListener> collageListeners;
    LinkedList<LibraryUpdateListener> libraryListeners;
    
    // Struct holding data loaded from path and interesting for user
    public class LibraryElementData
    {
        public Bitmap thumbnail;
        public int width;
        public int height;
        public Date lastModified;
        public String path;
        public boolean inCollage;
        
        public LibraryElementData(String _path)
        {
            path = _path;
            File f = new File(path);
            
            lastModified = new Date(f.lastModified());
            // TODO: something smart in case file doesn't exist
            // TODO: reconsider opening image to get thumbnail
            Bitmap img = BitmapFactory.decodeFile(path);
            width = img.getWidth();
            height = img.getHeight();
            thumbnail = Bitmap.createScaledBitmap(img, THUMB_WIDTH, THUMB_HEIGHT, true);
            inCollage = false;
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
            // First, get default width and height of image
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(libraryPaths.get(imageID).path, opts);
            
            int baseWidth = opts.outWidth;
            int baseHeight = opts.outHeight;
            int desiredWidth = width / 4;
            int desiredHeight = height / 4;
            
            // Compare aspect ratios:
            float baseAspectRatio = (float)baseWidth / (float)baseHeight;
            float collageAspectRatio = (float)width / (float)height;
            float scalar = 1.0f;
            // If too wide:
            if (baseAspectRatio > collageAspectRatio)
                scalar = (float)desiredWidth / (float)baseWidth;
            // If too tall:
            else
                scalar = (float)desiredHeight / (float)baseHeight;
            
            // Build bounds
            bounds = new Rect(0, 0, (int)(baseWidth * scalar), (int)(baseHeight * scalar));
            
            // Move bounds to center
            setPosition((width / 2) - (bounds.width() / 2),
                        (height / 2) - (bounds.height() / 2));
        }
        
        public void setPosition(int x, int y)
        {
            int dx = x - bounds.left;
            int dy = y - bounds.top;
            movePosition(dx, dy);
        }
        
        public void movePosition(int dx, int dy)
        {
            bounds.left += dx;
            bounds.right += dx;
            bounds.top += dy;
            bounds.bottom += dy;
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
    // Index of the presently selected image. null if none selected
    Integer selectedEntry = null;
    
    // Cache to handle loading images
    private SimpleCache<Bitmap, Integer> imageCache;
    private static final int MAX_CACHE_SIZE = 10;    // TODO: tweak to work nicely
    
    // Cached bitmap. We'll update this regularly as necessary
    private Bitmap currentImage;
    
    // Default size for the collage
    private int width;
    private int height;
    
    private CollageModel()
    {
        collageListeners = new LinkedList<CollageUpdateListener>();
        libraryListeners = new LinkedList<LibraryUpdateListener>();
        
        libraryPaths = new LinkedList<LibraryElementData>();
        collageEntries = new LinkedList<CollageEntry>();
        imageCache = new SimpleCache<Bitmap, Integer>(MAX_CACHE_SIZE);
        
        // TODO: for now, we'll just have the collage ALWAYS be 2000 by 2000 pixels
        setWidthAndHeight(2000, 2000);
        
        currentImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        updateImage();
    }
    
    // TODO: remove? Not req.
    
    public synchronized void addImageToLibrary(String path)
    {
        // do not insert if path already included
        for (LibraryElementData libData : libraryPaths)
            if (libData.path == path)
                return;

        
        libraryPaths.add(new LibraryElementData(path));
        
        // Tell library listeners that we've added something
        for (LibraryUpdateListener listener : libraryListeners)
        {
            listener.libraryElementAdded();
            listener.libraryHasChanged();
        }
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
        libraryPaths.get(index).inCollage = true;
        
        for (CollageUpdateListener listener : collageListeners)
        {
            listener.collageEntryAdded();
        }
        for (LibraryUpdateListener listener : libraryListeners)
        {
            listener.libraryHasChanged();
        }
        
        // update our image to accommodate for the new entry
        updateImage();
    }
    
    public synchronized void removeLibraryElementFromCollage(int index)
    {
        // Search for the index to confirm it was in there
        for (int i = 0; i < collageEntries.size(); i++)
        {
            CollageEntry entry = collageEntries.get(i);
            if (entry.imageID == index)
            {
                collageEntries.remove(entry);
                libraryPaths.get(index).inCollage = false;
                
                // Adjust selected image appropriately
                if (selectedEntry != null)
                {
                    if (selectedEntry == i)
                    {
                        selectedEntry = null;
                        for (CollageUpdateListener listener : collageListeners)
                        {
                            listener.collageSelectionChanged();
                        }
                    }
                    else if (selectedEntry > i)
                    {
                        selectedEntry--;
                        for (CollageUpdateListener listener : collageListeners)
                        {
                            listener.collageSelectionChanged();
                        }
                    }
                }
                
                this.updateImage();
                
                for (CollageUpdateListener listener : collageListeners)
                {
                    listener.collageEntryRemoved();
                }
                for (LibraryUpdateListener listener : libraryListeners)
                {
                    listener.libraryHasChanged();
                }
                return;
            }
        }
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
    
    public synchronized CollageEntry[] getCollageEntries()
    {
        return (CollageEntry[])collageEntries.toArray();
    }

    public synchronized int getWidth()
    {
        return width;
    }

    public synchronized int getHeight()
    {
        return height;
    }
    
    public synchronized int getLibraryCount()
    {
        return libraryPaths.size();
    }

    public synchronized void setWidth(int width)
    {
        this.width = width;
        currentImage = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        updateImage();
    }

    public synchronized void setHeight(int height)
    {
        this.height = height;
        currentImage = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        updateImage();
    }
    
    public synchronized void setWidthAndHeight(int _width, int _height)
    {
        width = _width;
        height = _height;
        currentImage = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        updateImage();
    }
    
    private synchronized Bitmap loadCollageEntry(CollageEntry entry)
    {
        Bitmap bmp = imageCache.getDataFromID(entry.imageID);
        // Image currently isn't cached, so let's load it
        if (bmp == null)
        {
            // First, let's get image's base width and height:
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(libraryPaths.get(entry.imageID).path, opts);
            
            int baseWidth = opts.outWidth;
            int baseHeight = opts.outHeight;
            
            // Compute which power of two is best to scale down by:
            int inSampleSize = 1;
            while ((baseWidth / inSampleSize) > MAX_IMG_WIDTH ||
                   (baseHeight / inSampleSize) > MAX_IMG_HEIGHT)
            {
                inSampleSize *= 2;
            }
            
            // Load the actual image
            opts.inSampleSize = inSampleSize;
            opts.inJustDecodeBounds = false;
            bmp = BitmapFactory.decodeFile(libraryPaths.get(entry.imageID).path, opts);
            
            // Inform the cache we loaded the image
            imageCache.loadDataWithID(bmp, entry.imageID);
        }
        
        return bmp;
    }
    
    private synchronized void updateImage()
    {
        Canvas canvas = new Canvas(currentImage);
        canvas.drawColor(Color.WHITE);
        
        for (CollageEntry entry : collageEntries)
        {
            Bitmap bmp = Bitmap.createScaledBitmap(loadCollageEntry(entry),
                                                   entry.bounds.width(),
                                                   entry.bounds.height(),
                                                   true);
            
            canvas.drawBitmap(bmp, entry.bounds.left, entry.bounds.top, null);
        }
        
        for (CollageUpdateListener listener : collageListeners)
        {
            listener.collageImageUpdated();
        }
    }
    
    public synchronized Bitmap getRenderedCollage()
    {
        return currentImage;
    }
    
    public synchronized void registerForCollageUpdates(CollageUpdateListener listener)
    {
        collageListeners.add(listener);
    }
    
    public synchronized void registerForLibraryUpdates(LibraryUpdateListener listener)
    {
        libraryListeners.add(listener);
    }
    
    public synchronized void setSelectedEntry(Integer libraryId)
    {
        // If the libraryId is null, we just set nothing as selected
        if (libraryId == null)
        {
            selectedEntry = null;
            for (CollageUpdateListener listener : collageListeners)
            {
                listener.collageSelectionChanged();
            }
            return;
        }
        
        // If the Id isn't contained in the library, do nothing
        if (libraryPaths.size() <= libraryId)
            return;
        
        // Search for it. If we find it, set value. Otherwise, set to null
        for (int i = 0; i < collageEntries.size(); i++)
        {
            if (collageEntries.get(i).imageID == libraryId)
            {
                selectedEntry = i;
                for (CollageUpdateListener listener : collageListeners)
                {
                    listener.collageSelectionChanged();
                }
                return;
            }
        }
        selectedEntry = null;
        
        for (CollageUpdateListener listener : collageListeners)
        {
            listener.collageSelectionChanged();
        }
    }
    
    public synchronized CollageEntry getSelectedEntry()
    {
        if (selectedEntry == null)
            return null;
        return collageEntries.get(selectedEntry);
    }
    
    public synchronized Integer findAndSelectEntryAtPoint(Point p)
    {
        // Check selected entry first
        if (selectedEntry != null &&
            collageEntries.get(selectedEntry).bounds.contains(p.x, p.y))
        {
            return selectedEntry;
        }
        
        // Check, starting backwards (to check those drawn on top first)
        for (int i = collageEntries.size() - 1; i >= 0; i--)
        {
            if (collageEntries.get(i).bounds.contains(p.x, p.y))
            {
                selectedEntry = i;
                for (CollageUpdateListener listener : collageListeners)
                {
                    listener.collageSelectionChanged();
                }
                return i;
            }
        }
        
        // Didn't find one, so return null
        return null;
    }
    
    public synchronized void moveEntry(Integer entryIndex, int dx, int dy)
    {
        if (entryIndex == null)
            return;
        
        collageEntries.get(entryIndex).movePosition(dx, dy);
        
        updateImage();
    }
    
    public synchronized void scaleEntry(Integer entryIndex, float scaleFactor)
    {
        if (entryIndex == null)
            return;
        
        collageEntries.get(entryIndex).scale(scaleFactor);
        
        updateImage();
    }
    
    //-------------------------------
    // Content for saving the model:
    //-------------------------------
    private class CollageSerialization
    {
        public String librarySerial;
        public String collageSerial;
        public int width;
        public int height;
    }
    
    private class LibraryEntrySerialization
    {
        public String path;
        public boolean inCollage;
    }
    
    public synchronized void saveModel(String savePath, Context context)
    {
        Gson gson = new Gson();
        CollageSerialization serial = new CollageSerialization();
        
        // Get paths out of libraryPaths:
        LinkedList<LibraryEntrySerialization> libSerials = new LinkedList<LibraryEntrySerialization>();
        for (LibraryElementData data : libraryPaths)
        {
            LibraryEntrySerialization libSerial = new LibraryEntrySerialization();
            libSerial.path = data.path;
            libSerial.inCollage = data.inCollage;
            libSerials.add(libSerial);
        }
        
        Type libraryType = new TypeToken<LinkedList<LibraryEntrySerialization>>(){}.getType();
        Type collageType = new TypeToken<LinkedList<CollageEntry>>(){}.getType();
        serial.librarySerial = gson.toJson(libSerials, libraryType);
        serial.collageSerial = gson.toJson(collageEntries, collageType);
        serial.width = width;
        serial.height = height;
        
        try
        {
            FileOutputStream outStream = context.openFileOutput(savePath, Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(outStream);
            writer.write(gson.toJson(serial, CollageSerialization.class));
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public static synchronized void loadModel (String openPath, Context context)
    {
        try
        {
            FileInputStream inStream = context.openFileInput(openPath);
            
            InputStreamReader reader = new InputStreamReader(inStream);
            BufferedReader buff = new BufferedReader(reader);
            String json = "";
            
            String str;
            while ((str = buff.readLine()) != null)
                json += str;
            
            reader.close();
            
            // Rewrite our instance
            if (instance == null)
                instance = new CollageModel();
            
            Gson gson = new Gson();
            CollageSerialization serial = gson.fromJson(json, CollageSerialization.class);

            // Need type tokens because generics. :\
            Type libraryType = new TypeToken<LinkedList<LibraryEntrySerialization>>(){}.getType();
            Type collageType = new TypeToken<LinkedList<CollageEntry>>(){}.getType();
            LinkedList<LibraryEntrySerialization> libSerials = gson.fromJson(serial.librarySerial, libraryType);
            
            // Load each of the library elements based on the path
            instance.libraryPaths = new LinkedList<LibraryElementData>();
            for (LibraryUpdateListener listener : instance.libraryListeners)
            {
                listener.libraryElementRemoved();
                listener.libraryHasChanged();
            }
            for (LibraryEntrySerialization libSerial : libSerials)
            {
                LibraryElementData libData = instance.new LibraryElementData(libSerial.path);
                libData.inCollage = libSerial.inCollage;
                instance.libraryPaths.add(libData);
            }
            for (LibraryUpdateListener listener : instance.libraryListeners)
            {
                listener.libraryElementAdded();
                listener.libraryHasChanged();
            }
            
            instance.collageEntries = gson.fromJson(serial.collageSerial, collageType);
            instance.width = serial.width;
            instance.height = serial.height;
            
            // Reset any other variables that should be
            instance.selectedEntry = null;
            for (CollageUpdateListener listener : instance.collageListeners)
            {
                listener.collageSelectionChanged();
            }
            
            // Now that we've done all that, let's update our image
            instance.updateImage();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
