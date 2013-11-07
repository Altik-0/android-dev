package utah.edu.cs4962.collage.model;

import java.util.LinkedList;

// Used to track data accessed over the runtime of the application. Will
// track the most recently accessed data, up to a provided maximum cache
// size. Attaches IDs to each piece of data, so you use the class as
// follows:
//
// SimpleCache<Data, int> myCache = new SimpleCache<Data, int>(maxSize);
// if((data = myCache.containsDataFromID(id)) == null) {
//  # Load the data yourself
//  myCache.loadData(data, id);
// }
//
// Most recent accesses to the cache will be prioritized, so performance
// should be optimized towards frequent accesses. However, there is no
// guarantee when something will be kicked out of the cache, so always
// perform those null checks, like a good programmer. :)
public class SimpleCache<T,I>
{
    // Just a simple helper
    private class Tuple<A,B>
    {
        public A X;
        public B Y;
        public Tuple(A _X, B _Y)
        {
            X = _X;
            Y = _Y;
        }
    }
    
    private LinkedList<Tuple<T,I>> dataCache;
    private int maxDepth;
    
    public SimpleCache(int _maxDepth)
    {
        dataCache = new LinkedList<Tuple<T,I>>();
        maxDepth = _maxDepth;
    }
    
    public T getDataFromID(I id)
    {
        Tuple<T,I> result = null;
        for (Tuple<T,I> toCheck : dataCache)
        {
            if (toCheck.Y == id)
            {
                result = toCheck;
                break;
            }
        }
        
        if (result != null)
        {
            // Move the recently found item to the top of the queue
            dataCache.remove(result);
            dataCache.addFirst(result);
            return result.X;
        }
        
        // Didn't find it, so return null
        return null;
    }
    
    public void loadDataWithID(T data, I id)
    {
        dataCache.addFirst(new Tuple<T,I>(data, id));
        if (dataCache.size() > maxDepth)
        {
            dataCache.removeLast();
        }
    }
}
