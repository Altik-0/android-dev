package utah.edu.cs4962.collage.model;

public interface LibraryUpdateListener
{
    public void libraryElementRemoved();
    public void libraryElementAdded();
    public void libraryHasChanged();
}
