package utah.edu.cs4962.collage.model;

public interface CollageUpdateListener
{
    public void collageEntryRemoved();
    public void collageEntryAdded();
    public void collageImageUpdated();
    public void collageSelectionChanged();
}
