package altik0.mtg.magictheorganizing.Database;

import java.io.Serializable;

// Useful struct for passing around arguments towards searches in the database
// General rule of thumb: fields you don't want to search on are NULL
public class SearchParams implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    // FIELDS SPECIFYING SEARCH OVER COLLECTION OR ALL CARDS:
    public boolean searchOverCollection = false;
    public Integer CollectionId = null;
    
    // STRING FIELDS:
    public String TextSearch = null;
    public String NameSearch = null;
    public String TypeSearch = null;
    public String ExpansionSearch = null;
    
    // BITMASK FIELDS, AND CONSTANTS:
    public Integer RarityFilter = null;
    public static final int COMMON_FLAG             = 1;
    public static final int UNCOMMON_FLAG           = 2;
    public static final int RARE_FLAG               = 4;
    public static final int MYTHIC_FLAG             = 8;
    
    public Integer ColorFilter = null;
    public static final int WHITE_FLAG              = 1;
    public static final int BLUE_FLAG               = 2;
    public static final int BLACK_FLAG              = 4;
    public static final int RED_FLAG                = 8;
    public static final int GREEN_FLAG              = 16;
    public static final int COLORLESS_FLAG          = 32;
    
    public static final int EXCLUDE_UNSELECTED_FLAG = 64;
    public static final int REQUIRE_MULTICOLOR_FLAG = 128;
}
