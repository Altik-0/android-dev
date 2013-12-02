package altik0.mtg.magictheorganizing.MtgDataTypes;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardData
{
    // Consts used for various codes:
    public static final int WHITE_CODE = 0x1;
    public static final int BLUE_CODE  = 0x2;
    public static final int BLACK_CODE = 0x4;
    public static final int RED_CODE   = 0x8;
    public static final int GREEN_CODE = 0x10;
    
    // Database indicator things
    private int cardId;
    
    // Cached values
    private int cmc;

    // We'll put something in by default, so there isn't a crash
    // if we haven't initialized data.
	private String name = "NULL";
	private String manaCost = "NULL";
	private String text = "";
	private String flavorText = "";

    private String power = null;
	private String toughness = null;
	private Integer loyalty = null;     // Pretty sure loyalty will be an integer always,
	                                    // but not necessarily true long term. This needs
	                                    // to change, but that's another database fix which
	                                    // I don't want to do
	private String rarity;
	
	private ArrayList<CardColor> colors;
    private ArrayList<String> sets;
	private ArrayList<String> supertypes;
	private ArrayList<String> types;
	private ArrayList<String> subtypes;
	
	// TODO: artist, art (seeing as this card represents multiple versions)
	
	public CardData()
	{
	    colors = new ArrayList<CardColor>();
        types = new ArrayList<String>();
        supertypes = new ArrayList<String>();
        subtypes = new ArrayList<String>();
        sets = new ArrayList<String>();
	}
	
	public CardData(CardData _old)
	{
		name = _old.name;
		manaCost = _old.manaCost;
		power = _old.power;
		toughness = _old.toughness;
		cardId = _old.cardId;
	    cmc = _old.cmc;
	    loyalty = _old.loyalty;
	    rarity = _old.rarity;
	    flavorText = _old.flavorText;
	    text = _old.text;
		
		colors = new ArrayList<CardColor>();
		types = new ArrayList<String>();
		supertypes = new ArrayList<String>();
		subtypes = new ArrayList<String>();
        sets = new ArrayList<String>();
		
		for (CardColor color : _old.colors)
			colors.add(color);
		for (String type : _old.types)
			types.add(type);
		for (String supertype : _old.supertypes)
		    supertypes.add(supertype);
		for (String subtype : _old.subtypes)
			subtypes.add(subtype);
	}
	
	public void addColor(CardColor newColor)
	{
		// Don't add duplicate colors
		if (colors.contains(newColor))
			return;
		colors.add(newColor);
	}
	
	public void addType(String newType)
	{
		// Don't add duplicate types
		if (types.contains(newType))
			return;
		types.add(newType);
	}
	
	public void setColorsFromCode(int colorCode)
	{
	    colors = new ArrayList<CardColor>();
        if ((colorCode & WHITE_CODE) != 0)
            colors.add(CardColor.White);
        if ((colorCode & BLUE_CODE) != 0)
            colors.add(CardColor.Blue);
        if ((colorCode & BLACK_CODE) != 0)
            colors.add(CardColor.Black);
        if ((colorCode & RED_CODE) != 0)
            colors.add(CardColor.Red);
        if ((colorCode & GREEN_CODE) != 0)
            colors.add(CardColor.Green);
	}
	
	public void setTypesFromCode(String typeString)
	{
	    String[] typeSplit = typeString.split("--");
	    // Subtypes:
	    if (typeSplit.length == 2)
	    {
	        String subtypeString = typeSplit[1].trim();
	        String otherTypeString = typeSplit[0].trim();
	        
	        String[] subtypes = subtypeString.split(" ");
	        String[] otherTypes = otherTypeString.split(" ");
	        
	        this.subtypes = new ArrayList<String>();
	        for (String subtype : subtypes)
	            this.subtypes.add(subtype);
	        
	        this.supertypes = new ArrayList<String>();
	        this.types = new ArrayList<String>();
	        for (String otherType : otherTypes)
	        {
	            if (otherType.toUpperCase().equals("LEGENDARY") ||
	                otherType.toUpperCase().equals("BASIC") ||
	                otherType.toUpperCase().equals("WORLD") ||
	                otherType.toUpperCase().equals("SNOW"))
	            {
	                this.supertypes.add(otherType);
	            }
	            else
	                this.types.add(otherType);
	        }
	    }
	    // No subtypes:
	    else
	    {
            String otherTypeString = typeSplit[0].trim();
	    }
	}
	
	public void setSetsFromCode(String setCode)
	{
	    Pattern p = Pattern.compile("\\[[^\\[\\]]*\\]");
        Matcher m = p.matcher(setCode);
        // Whenever we find a set, trim its brackets and put it in our list
        while (m.find())
            sets.add(m.group().substring(1, m.group().length() - 1));
	}
	
	public void addSupertype(String supertype)
	{
	    // Don't add duplicate types
	    if (supertypes.contains(supertype))
	        return;
	    supertypes.add(supertype);
	}

	public void addSubtype(String subtype)
	{
	    // Don't add duplicate types
		if (subtypes.contains(subtype))
			return;
		subtypes.add(subtype);
	}
	
	public String getTypeString()
	{
		String toRet = "";
		// TODO: separate these steps, sort nicely, etc.
		// Scan for super types:
		for(String supertype : supertypes)
		{
		    toRet += supertype + " ";
		}
		// Scan for normal types:
		for(String type : types)
		{
			toRet += type + " ";
		}
		// -- Scan for subtypes
		if (subtypes.size() > 0)
		{
			toRet += "-- ";
			for (String subtype : subtypes)
				toRet += subtype + " ";
		}
		
		// Trim tail space
		if (toRet.length() > 0)
		    toRet = toRet.substring(0, toRet.length() - 1);
		
		return toRet;
	}
	
	public String getPowerToughnessString()
	{		
		return power + "/" + toughness;
	}

	public String getToughness() {
		return toughness;
	}

	public void setToughness(String toughness) {
		this.toughness = toughness;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getManaCost() {
		return manaCost;
	}

	public void setManaCost(String manaCost) {
		this.manaCost = manaCost;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<CardColor> getColors()
	{
		return colors;
	}
	
	public ArrayList<String> getTypes()
	{
		return types;
	}
	
	public ArrayList<String> getSupertypes()
	{
	    return supertypes;
	}

	public ArrayList<String> getSubtypes() {
		return subtypes;
	}
	
	public ArrayList<String> getSets()
	{
	    return sets;
	}

    public String getRarity()
    {
        return rarity;
    }

    public void setRarity(String rarity)
    {
        this.rarity = rarity;
    }
    
    public int getCardId()
    {
        return cardId;
    }

    public void setCardId(int cardId)
    {
        this.cardId = cardId;
    }

    public int getCmc()
    {
        return cmc;
    }

    public void setCmc(int cmc)
    {
        this.cmc = cmc;
    }
    
    public Integer getLoyalty()
    {
        return this.loyalty;
    }
    
    public void setLoyalty(Integer loyalty)
    {
        this.loyalty = loyalty;
    }
    
    public String getText()
    {
        return text;
    }
    
    public void setText(String _Text)
    {
        text = _Text;
    }
    
    public String getFlavorText()
    {
        return flavorText;
    }
    
    public void setFlavorText(String _FlavorText)
    {
        flavorText = _FlavorText;
    }
}
