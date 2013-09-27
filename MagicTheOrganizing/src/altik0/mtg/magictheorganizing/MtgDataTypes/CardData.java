package altik0.mtg.magictheorganizing.MtgDataTypes;

import java.util.ArrayList;

public class CardData
{
	private String name;
	private String manaCost;
	private int power;
	private int toughness;
	
	private ArrayList<CardColor> colors;
	private ArrayList<CardType> types;
	private ArrayList<String> subtypes;
	
	// TODO: artist, art
	
	public CardData()
	{
		colors = new ArrayList<CardColor>();
		types  = new ArrayList<CardType>();
		subtypes = new ArrayList<String>();
	}
	
	public CardData(CardData _old)
	{
		name = _old.name;
		manaCost = _old.manaCost;
		power = _old.power;
		toughness = _old.toughness;
		
		colors = new ArrayList<CardColor>();
		types = new ArrayList<CardType>();
		subtypes = new ArrayList<String>();
		
		for (CardColor color : _old.colors)
			colors.add(color);
		for (CardType type : _old.types)
			types.add(type);
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

	public void addSubtype(String subtype)
	{
		if (subtypes.contains(subtype))
			return;
		subtypes.add(subtype);
	}
	
	public String getTypeString()
	{
		String toRet = "";
		// TODO: separate these steps, sort nicely, etc.
		// Scan for super types:
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
		toRet = toRet.substring(0, toRet.length() - 1);
		
		return toRet;
	}
	
	public String getPowerToughnessString()
	{		
		return Integer.toString(power) + "/" + Integer.toString(toughness);
	}

	public int getToughness() {
		return toughness;
	}

	public void setToughness(int toughness) {
		this.toughness = toughness;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
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

	public ArrayList<String> getSubtypes() {
		return subtypes;
	}
}
