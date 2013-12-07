package altik0.mtg.magictheorganizing.Database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class CollectionModel
{
    public static class Location implements Serializable
    {
        public int LocationId;
        public String Name;
    }
    
    public static class Collection implements Serializable
    {
        public int CollectionId;
        public String Name;
    }
    
    public ArrayList<Location> locations;
    public HashMap<Integer, ArrayList<Collection>> collectionSet;
    
    // CACHED VALUES:
    private Integer colCnt = null;
    
    public CollectionModel()
    {
        collectionSet = new HashMap<Integer, ArrayList<Collection>>();
        locations = new ArrayList<Location>();
    }
    
    public void AddLocation(Location l)
    {
        if (locations.contains(l))
            return;
        
        locations.add(l);
        collectionSet.put(l.LocationId, new ArrayList<Collection>());
    }
    
    public void AddCollection(Collection c, int locationId)
    {
        collectionSet.get(locationId).add(c);
    }
    
    public int LocationCount()
    {
        return locations.size();
    }
    
    public int CollectionCount()
    {
        if (colCnt != null)
            return colCnt;
        
        colCnt = 0;
        for (int l : collectionSet.keySet())
            colCnt += collectionSet.get(l).size();
        
        return colCnt;
    }
    
    public Location LocationWithId(int id)
    {
        for (Location l : locations)
        {
            if (id == l.LocationId)
                return l;
        }
        return null;
    }
}
