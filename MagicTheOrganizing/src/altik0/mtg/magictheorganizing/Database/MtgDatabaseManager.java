package altik0.mtg.magictheorganizing.Database;

import altik0.mtg.magictheorganizing.R;
import altik0.mtg.magictheorganizing.MtgDataTypes.CardColor;
import altik0.mtg.magictheorganizing.MtgDataTypes.CardData;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

public class MtgDatabaseManager extends SQLiteOpenHelper
{
    private static final String DB_NAME = "mtg_sqlite.db";
    private static final int VER_NUM = 1;
    private Context context;
    
    public MtgDatabaseManager(Context _context)
    {
        super (_context, DB_NAME, null, VER_NUM);
        context = _context;
    }
    
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // Open sql file
        InputStream is = context.getResources().openRawResource(R.raw.mtg_sql);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader buf = new BufferedReader(isr);
        
        // Read commands one at a time.
        // TODO: something smarter. For now, we'll assume that lines ending in ';'
        // are the only place where commands end
        String line = null;
        String command = "";
        try
        {
            int i = 0;
            while ((line = buf.readLine()) != null)
            {
                i++;
                command += line;
                if (command.endsWith(";"))
                {
                    try
                    {
                        db.execSQL(command);
                        command = "";
                    }
                    catch (SQLException e)
                    {
                        // Nothing: this is a hack.
                        // Short version: sometimes lines end in ';' when
                        // still reading flavor text or something. Point is,
                        // if the line we read wasn't enough to issue the
                        // command, we'll just keep reading and pretend it
                        // didn't happen.
                    }
                }
            }
            Log.i("Command Cnt", ""+i);
            
            /*
            while ((line = buf.readLine()) != null)
                command += line;
            
            db.execSQL(command);
            */
            
            // TODO: figure out how to do this better. Not sure where I should put this
            // logic to make the code solid, because the fuck are these close() methods
            // throwing exceptions for?
            // Close any open stuff
            buf.close();
            isr.close();
            is.close();
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer)
    {
        // TODO something smarter. For now, just delete and restart
        // Open sql file
        InputStream is = context.getResources().openRawResource(R.raw.mtg_drop);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader buf = new BufferedReader(isr);
        
        // Read commands one at a time.
        // TODO: something smarter. For now, we'll assume that lines ending in ';'
        // are the only place where commands end
        String line = null;
        String command = "";
        try
        {
            while ((line = buf.readLine()) != null)
            {
                command += line;
                if (command.endsWith(";"))
                    db.execSQL(command);
                command = "";
            }
            
            // TODO: figure out how to do this better. Not sure where I should put this
            // logic to make the code solid, because the fuck are these close() methods
            // throwing exceptions for?
            // Close any open stuff
            buf.close();
            isr.close();
            is.close();
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        onCreate(db);
    }
    
    public CardData GetCardWithId(int id)
    {
        ArrayList<CardData> toRet = new ArrayList<CardData>();
        
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Cards card_table " +
                                    "WHERE card_table.CardID = ?",
                                    new String[]{Integer.toString(id)});
        
        // Read cards
        if (cursor.moveToFirst())
        {
            do
            {
                CardData card = new CardData();
                card.setCardId(cursor.getInt(0));
                card.setRarity(cursor.getString(4));
                card.setName(cursor.getString(6));
                card.setCmc(cursor.getInt(7));
                card.setManaCost(cursor.getString(9));
                card.setPower(cursor.getString(11));
                card.setToughness(cursor.getString(12));
                card.setLoyalty(cursor.getInt(13));
                card.setText(cursor.getString(14));
                card.setFlavorText(cursor.getString(15));
                
                int colorCode = cursor.getInt(10);
                String typeCode = cursor.getString(5);
                String setCode = cursor.getString(2);
                
                card.setColorsFromCode(colorCode);
                card.setTypesFromCode(typeCode);
                card.setSetsFromCode(setCode);
    
                // Add card to list
                toRet.add(card);
            } while(cursor.moveToNext());
        }
        
        // We only used an array list because all the functions take that contract
        // In reality, it's just going to be one item. If there weren't any, I suppose
        // that would be a problem, but we can address that later.
        // I guess TODO and whatnot.
        return toRet.get(0);
    }
    
    public ArrayList<CardData> SearchForCards(SearchParams params)
    {
        // Build dat query
        String query = "SELECT * FROM Cards card_table WHERE ";
        
        // For all the nulls, we'll use a dummy condition:
        String textCheck = "1 = 1";
        String expansionCheck = "1 = 1";
        String rarityCheck = "1 = 1";
        String nameCheck = "1 = 1";
        String typeCheck = "1 = 1";
        String colorCheck = "1 = 1";
        LinkedList<String> queryParams = new LinkedList<String>();
        
        // For the things which aren't null, build the appropriate replacement:
        if (params.NameSearch != null)
        {
            // TODO: smarter than just equivalent. String contains?
            nameCheck = "LOWER(card_table.Name) LIKE LOWER(?)";
            queryParams.addLast("%" + params.NameSearch + "%");
        }
        if (params.TextSearch != null)
        {
            textCheck = "LOWER(card_table.Text) LIKE LOWER(?)";
            queryParams.addLast("%" + params.TextSearch + "%");
        }
        if (params.ExpansionSearch != null)
        {
            expansionCheck = "LOWER(card_table.Sets) LIKE LOWER(?)";
            queryParams.addLast("%" + params.ExpansionSearch + "%");
        }
        if (params.TypeSearch != null)
        {
            typeCheck = "LOWER(card_table.Type) LIKE LOWER(?)";
            queryParams.addLast("%" + params.TypeSearch + "%");
        }
        if (params.RarityFilter != null)
        {
            // Treat multiple boxes as ors:
            String commonCheck = "0 = 1";
            String uncommonCheck = "0 = 1";
            String rareCheck = "0 = 1";
            String mythicCheck = "0 = 1";
            if ((params.RarityFilter & SearchParams.COMMON_FLAG) != 0)
            {
                commonCheck = "LOWER(card_table.Rarity) = 'common'";
            }
            if ((params.RarityFilter & SearchParams.UNCOMMON_FLAG) != 0)
            {
                uncommonCheck = "LOWER(card_table.Rarity) = 'uncommon'";
            }
            if ((params.RarityFilter & SearchParams.RARE_FLAG) != 0)
            {
                rareCheck = "LOWER(card_table.Rarity) = 'rare'";
            }
            if ((params.RarityFilter & SearchParams.MYTHIC_FLAG) != 0)
            {
                mythicCheck = "LOWER(card_table.Rarity) = 'mythic'";
            }
            rarityCheck = "(" +
                          commonCheck + " OR " +
                          uncommonCheck + " OR " +
                          rareCheck + " OR " +
                          mythicCheck + ")";
        }
        if (params.ColorFilter != null)
        {
            // TODO: req. multicolor and exc. unselected
            String whiteCheck = "0 = 1";
            String blueCheck = "0 = 1";
            String blackCheck = "0 = 1";
            String redCheck = "0 = 1";
            String greenCheck = "0 = 1";
            
            int filter = params.ColorFilter;
            if ((filter & SearchParams.WHITE_FLAG) != 0)
            {
                whiteCheck = "(card_table.Colors & 1) != 0";
            }
            if ((filter & SearchParams.BLUE_FLAG) != 0)
            {
                blueCheck = "(card_table.Colors & 2) != 0";
            }
            if ((filter & SearchParams.BLACK_FLAG) != 0)
            {
                blackCheck = "(card_table.Colors & 4) != 0";
            }
            if ((filter & SearchParams.RED_FLAG) != 0)
            {
                redCheck = "(card_table.Colors & 8) != 0";
            }
            if ((filter & SearchParams.GREEN_FLAG) != 0)
            {
                greenCheck = "(card_table.Colors & 16) != 0";
            }
            
            colorCheck = "(" +
                    whiteCheck + " OR " +
                    blueCheck + " OR " +
                    blackCheck + " OR " +
                    redCheck + " OR " +
                    greenCheck + ")";
        }
        
        String[] queryArray = queryParams.toArray(new String[0]);
        
        // Build final query
        query += nameCheck + " AND " +
                 textCheck + " AND " +
                 expansionCheck + " AND " +
                 typeCheck + " AND " +
                 rarityCheck + " AND " +
                 colorCheck;
        
        // Get the datas
        ArrayList<CardData> toRet = new ArrayList<CardData>();
        
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, queryArray);
        
        // Read cards
        if (cursor.moveToFirst())
        {
            do
            {
                CardData card = new CardData();
                card.setCardId(cursor.getInt(0));
                card.setRarity(cursor.getString(4));
                card.setName(cursor.getString(6));
                card.setCmc(cursor.getInt(7));
                card.setManaCost(cursor.getString(9));
                card.setPower(cursor.getString(11));
                card.setToughness(cursor.getString(12));
                card.setLoyalty(cursor.getInt(13));
                card.setText(cursor.getString(14));
                card.setFlavorText(cursor.getString(15));
                
                int colorCode = cursor.getInt(10);
                String typeCode = cursor.getString(5);
                String setCode = cursor.getString(2);
                
                card.setColorsFromCode(colorCode);
                card.setTypesFromCode(typeCode);
                card.setSetsFromCode(setCode);
    
                // Add card to list
                toRet.add(card);
            } while(cursor.moveToNext());
        }
        
        return toRet;
    }
    
    public ArrayList<CardData> GetAllCards()
    {
        ArrayList<CardData> toRet = new ArrayList<CardData>();
        
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Cards card_table", null);
        
        // Read cards
        if (cursor.moveToFirst())
        {
            do
            {
                CardData card = new CardData();
                card.setCardId(cursor.getInt(0));
                card.setRarity(cursor.getString(4));
                card.setName(cursor.getString(6));
                card.setCmc(cursor.getInt(7));
                card.setManaCost(cursor.getString(9));
                card.setPower(cursor.getString(11));
                card.setToughness(cursor.getString(12));
                card.setLoyalty(cursor.getInt(13));
                card.setText(cursor.getString(14));
                card.setFlavorText(cursor.getString(15));
                
                int colorCode = cursor.getInt(10);
                String typeCode = cursor.getString(5);
                String setCode = cursor.getString(2);
                
                card.setColorsFromCode(colorCode);
                card.setTypesFromCode(typeCode);
                card.setSetsFromCode(setCode);
    
                // Add card to list
                toRet.add(card);
            } while(cursor.moveToNext());
        }
        
        return toRet;
    }
    
    
    // Held onto for documentation purposes, but ultimately not used anymore
    /*
    // Takes cards, which are presumed to just have data from the Cards database,
    // and appends data for various list-type fields spread across different tables
    private static void FixCardListData(ArrayList<CardData> cards, SQLiteDatabase db, Cursor cursor)
    {
        for (CardData card : cards)
        {
            // Get colors
            cursor = db.rawQuery(
                    "SELECT color_table.Name FROM Cards card_table " +
                    "JOIN CardColors card_color_table ON card_color_table.CardID = card_table.CardID " +
                    "JOIN Colors color_table ON card_color_table.ColorID = color_table.ColorID " +
                    "WHERE card_table.CardID = ?",
                    new String[] { Integer.toString(card.getCardId()) });
            
            if (cursor.moveToFirst())
            {
                card.setSet(cursor.getString(0));
                do
                {
                    String color = cursor.getString(0);
                    if (color.toUpperCase().equals("WHITE"))
                        card.addColor(CardColor.White);
                    else if (color.toUpperCase().equals("BLUE"))
                        card.addColor(CardColor.Blue);
                    else if (color.toUpperCase().equals("BLACK"))
                        card.addColor(CardColor.Black);
                    else if (color.toUpperCase().equals("RED"))
                        card.addColor(CardColor.Red);
                    else if (color.toUpperCase().equals("GREEN"))
                        card.addColor(CardColor.Green);
                } while(cursor.moveToNext());
            }
                    
            // Get types
            cursor = db.rawQuery(
                    "SELECT type_table.Name FROM Cards card_table " +
                    "JOIN CardTypes card_type_table ON card_type_table.CardID = card_table.CardID " +
                    "JOIN Types type_table ON card_type_table.TypeID = type_table.TypeID " +
                    "WHERE card_table.CardID = ?",
                    new String[] { Integer.toString(card.getCardId()) });
            
            if (cursor.moveToFirst())
            {
                do
                {
                    String type = cursor.getString(0);
                    if (type.toUpperCase().equals("ARTIFACT"))
                        card.addType(CardType.Artifact);
                    else if (type.toUpperCase().equals("CREATURE"))
                        card.addType(CardType.Creature);
                    else if (type.toUpperCase().equals("ENCHANTMENT"))
                        card.addType(CardType.Enchantment);
                    else if (type.toUpperCase().equals("INSTANT"))
                        card.addType(CardType.Instant);
                    else if (type.toUpperCase().equals("SORCERY"))
                        card.addType(CardType.Sorcery);
                    else if (type.toUpperCase().equals("PLANESWALKER"))
                        card.addType(CardType.Planeswalker);
                    else if (type.toUpperCase().equals("LAND"))
                        card.addType(CardType.Land);
                    else if (type.toUpperCase().equals("TRIBAL"))
                        card.addType(CardType.Tribal);
                } while(cursor.moveToNext());
            }
            
            // Get supertypes
            cursor = db.rawQuery(
                    "SELECT type_table.Name FROM Cards card_table " +
                    "JOIN CardSupertypes card_type_table ON card_type_table.CardID = card_table.CardID " +
                    "JOIN Supertypes type_table ON card_type_table.SupertypeID = type_table.SupertypeID " +
                    "WHERE card_table.CardID = ?",
                    new String[] { Integer.toString(card.getCardId()) });
            
            if (cursor.moveToFirst())
            {
                do
                {
                    String type = cursor.getString(0);
                    if (type.toUpperCase().equals("LEGENDARY"))
                        card.addSupertype(CardSupertype.Legendary);
                    else if (type.toUpperCase().equals("SNOW"))
                        card.addSupertype(CardSupertype.Snow);
                    else if (type.toUpperCase().equals("BASIC"))
                        card.addSupertype(CardSupertype.Basic);
                    else if (type.toUpperCase().equals("WORLD"))
                        card.addSupertype(CardSupertype.World);
                } while(cursor.moveToNext());
            }
            

            
            // Get subtypes
           cursor = db.rawQuery(
                   "SELECT type_table.Name FROM Cards card_table " +
                   "JOIN CardSubtypes card_type_table ON card_type_table.CardID = card_table.CardID " +
                   "JOIN Subtypes type_table ON card_type_table.SubtypeID = type_table.SubtypeID " +
                   "WHERE card_table.CardID = ?",
                   new String[] { Integer.toString(card.getCardId()) });
           
           if (cursor.moveToFirst())
           {
               do
               {
                   card.addSubtype(cursor.getString(0));
               } while(cursor.moveToNext());
           }
        }
    }
    */

    private static MtgDatabaseManager instance = null;
    public static synchronized MtgDatabaseManager getInstance(Context _context)
    {
        if (instance == null)
            instance = new MtgDatabaseManager(_context.getApplicationContext());
        return instance;
    }
}
