package altik0.mtg.magictheorganizing.MtgDataTypes;

import java.util.ArrayList;

public class CardData
{
    // Database indicator things
    private int cardId;
    private int setId;
    
    // Cached values
    private int cmc;

    // We'll put something in by default, so there isn't a crash
    // if we haven't initialized data.
	private String name = "NULL";
	private String manaCost = "NULL";
	private Integer power = null;
	private Integer toughness = null;
	private Integer loyalty = null;
	private String rarity;
	private String set;
	
	private ArrayList<CardColor> colors;
	private ArrayList<CardSupertype> supertypes;
	private ArrayList<CardType> types;
	private ArrayList<String> subtypes;
	
	// TODO: artist, art
	
	public CardData()
	{
		colors = new ArrayList<CardColor>();
		types  = new ArrayList<CardType>();
		supertypes = new ArrayList<CardSupertype>();
		subtypes = new ArrayList<String>();
	}
	
	public CardData(CardData _old)
	{
		name = _old.name;
		manaCost = _old.manaCost;
		power = _old.power;
		toughness = _old.toughness;
		cardId = _old.cardId;
	    setId = _old.setId;
	    cmc = _old.cmc;
	    loyalty = _old.loyalty;
	    rarity = _old.rarity;
	    set = _old.set;
		
		colors = new ArrayList<CardColor>();
		types = new ArrayList<CardType>();
		supertypes = new ArrayList<CardSupertype>();
		subtypes = new ArrayList<String>();
		
		for (CardColor color : _old.colors)
			colors.add(color);
		for (CardType type : _old.types)
			types.add(type);
		for (CardSupertype supertype : _old.supertypes)
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
	
	public void addType(CardType newType)
	{
		// Don't add duplicate types
		if (types.contains(newType))
			return;
		types.add(newType);
	}
	
	public void addSupertype(CardSupertype supertype)
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
		for(CardSupertype supertype : supertypes)
		{
		    toRet += supertype.toString() + " ";
		}
		// Scan for normal types:
		for(CardType type : types)
		{
			toRet += type.toString() + " ";
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
		return Integer.toString(power) + "/" + Integer.toString(toughness);
	}

	public Integer getToughness() {
		return toughness;
	}

	public void setToughness(Integer toughness) {
		this.toughness = toughness;
	}

	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
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
	
	public ArrayList<CardType> getTypes()
	{
		return types;
	}
	
	public ArrayList<CardSupertype> getSupertypes()
	{
	    return supertypes;
	}

	public ArrayList<String> getSubtypes() {
		return subtypes;
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

    public int getSetId()
    {
        return setId;
    }

    public void setSetId(int setId)
    {
        this.setId = setId;
    }

    public int getCmc()
    {
        return cmc;
    }

    public void setCmc(int cmc)
    {
        this.cmc = cmc;
    }

    public String getSet()
    {
        return set;
    }

    public void setSet(String set)
    {
        this.set = set;
    }
    
    public Integer getLoyalty()
    {
        return this.loyalty;
    }
    
    public void setLoyalty(Integer loyalty)
    {
        this.loyalty = loyalty;
    }
}
