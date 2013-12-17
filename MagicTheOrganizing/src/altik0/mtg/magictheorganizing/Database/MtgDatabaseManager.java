package altik0.mtg.magictheorganizing.Database;

import altik0.mtg.magictheorganizing.R;
import altik0.mtg.magictheorganizing.Database.CollectionModel.Collection;
import altik0.mtg.magictheorganizing.Database.CollectionModel.Location;
import altik0.mtg.magictheorganizing.MtgDataTypes.CardColor;
import altik0.mtg.magictheorganizing.MtgDataTypes.CardData;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Debug;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.LinkedList;

public class MtgDatabaseManager extends SQLiteOpenHelper
{
    private static final String DB_NAME = "mtg_sqlite.db";
    private static final int VER_NUM = 1;
    private Context context;
    
    // Listener functionality
    public interface DatabaseListener
    {
        public void refresh();
    }
    private static LinkedList<DatabaseListener> listeners = new LinkedList<DatabaseListener>();
    
    public static void RegisterListener(DatabaseListener listener)
    {
        listeners.add(listener);
    }
    
    public static void UnregisterListener(DatabaseListener listener)
    {
        listeners.remove(listener);
    }
    
    public static void RefreshListeners()
    {
        for (DatabaseListener listener : listeners)
            listener.refresh();
    }
    
    public MtgDatabaseManager(Context _context)
    {
        super (_context, DB_NAME, null, VER_NUM);
        context = _context;
    }
    
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // Open sql file
        InputStream is = context.getResources().openRawResource(R.raw.mtg_sql_small);
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
    
    public ArrayList<String> GetAllCardNames()
    {
        ArrayList<String> toRet = new ArrayList<String>();
        
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Name FROM Cards", null);
        
        if (cursor.moveToFirst())
        {
            do
            {
                toRet.add(cursor.getString(0));
            } while(cursor.moveToNext());
        }
        
        return toRet;
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
                if (!cursor.isNull(13))
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
    
    public CardData GetCardWithCollectionId(int id)
    {
        ArrayList<CardData> toRet = new ArrayList<CardData>();
        
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CollectedCards col_cards JOIN Cards card_table " +
                                    "ON card_table.CardID = col_cards.CardID " +
                                    "WHERE col_cards.CollectedID = ?",
                                    new String[]{Integer.toString(id)});
        
        // Read cards
        if (cursor.moveToFirst())
        {
            do
            {
                CardData card = new CardData();
                card.setCollectedId(cursor.getInt(0));
                card.setCardId(cursor.getInt(1));
                card.setRarity(cursor.getString(9));
                card.setName(cursor.getString(11));
                card.setCmc(cursor.getInt(12));
                card.setManaCost(cursor.getString(14));
                card.setPower(cursor.getString(16));
                card.setToughness(cursor.getString(17));
                if (!cursor.isNull(18))
                    card.setLoyalty(cursor.getInt(18));
                card.setText(cursor.getString(19));
                card.setFlavorText(cursor.getString(20));
                card.setCount(cursor.getInt(3));
                
                int colorCode = cursor.getInt(15);
                String typeCode = cursor.getString(10);
                String setCode = cursor.getString(7);
                String tagsCode = cursor.getString(2);
                
                card.setColorsFromCode(colorCode);
                card.setTypesFromCode(typeCode);
                card.setSetsFromCode(setCode);
                card.setTagsFromCode(tagsCode);
    
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
        //Debug.startMethodTracing("SearchTrace");
        
        // Build dat query
        String query = "SELECT * FROM Cards card_table WHERE ";
        
        // For all the nulls, we'll use a dummy condition:
        String textCheck = "1 = 1";
        String expansionCheck = "1 = 1";
        String rarityCheck = "1 = 1";
        String nameCheck = "1 = 1";
        String typeCheck = "1 = 1";
        String colorCheck = "1 = 1";
        String reqMultiCheck = "1 = 1";
        LinkedList<String> queryParams = new LinkedList<String>();
        
        // For the things which aren't null, build the appropriate replacement:
        if (params.NameSearch != null)
        {
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
            boolean excUnselected = (params.ColorFilter & SearchParams.EXCLUDE_UNSELECTED_FLAG) != 0;
            
            String whiteCheck = "0 = 1";
            String excWhiteCheck = "1 = 1";
            String blueCheck = "0 = 1";
            String excBlueCheck = "1 = 1";
            String blackCheck = "0 = 1";
            String excBlackCheck = "1 = 1";
            String redCheck = "0 = 1";
            String excRedCheck = "1 = 1";
            String greenCheck = "0 = 1";
            String excGreenCheck = "1 = 1";
            String colorlessCheck = "0 = 1";
            
            int filter = params.ColorFilter;
            if ((filter & SearchParams.WHITE_FLAG) != 0)
                whiteCheck = "(card_table.Colors & 1) != 0";
            else if (excUnselected)
                excWhiteCheck = "(card_table.Colors & 1) = 0";
            if ((filter & SearchParams.BLUE_FLAG) != 0)
                blueCheck = "(card_table.Colors & 2) != 0";
            else if (excUnselected)
                excBlueCheck = "(card_table.Colors & 2) = 0";
            if ((filter & SearchParams.BLACK_FLAG) != 0)
                blackCheck = "(card_table.Colors & 4) != 0";
            else if (excUnselected)
                excBlackCheck = "(card_table.Colors & 4) = 0";
            if ((filter & SearchParams.RED_FLAG) != 0)
                redCheck = "(card_table.Colors & 8) != 0";
            else if (excUnselected)
                excRedCheck = "(card_table.Colors & 8) = 0";
            if ((filter & SearchParams.GREEN_FLAG) != 0)
                greenCheck = "(card_table.Colors & 16) != 0";
            else if (excUnselected)
                excGreenCheck = "(card_table.Colors & 16) = 0";
            if ((filter & SearchParams.COLORLESS_FLAG) != 0)
                colorlessCheck = "card_table.Colors = 0";
            if ((filter & SearchParams.REQUIRE_MULTICOLOR_FLAG) != 0)
            {
                // Bit twiddling hack explanation:
                //  requiring multicolor is equivalent to >= 2 bits on in color mask.
                //  This is equivalent to color mask is a non-zero non-power of 2.
                //  (x != 0) && !(x & (x - 1)) returns true iff x is a power of 2.
                //  In query form, that is (x != 0) AND ((x & (x-1)) = 0)
                //  Negating the whole expression returns true on 0 or a non-power of 2.
                //  Un-negating the first term excludes 0, but keeps everything else.
                reqMultiCheck = "((card_table.Colors != 0) AND " +
                                 "((card_table.Colors & (card_table.Colors - 1)) != 0))";
            }
            
            // If the only bit turned on is req. multicolor, we don't want to have a big
            // chain of falses killing our search.
            if ((filter ^ SearchParams.REQUIRE_MULTICOLOR_FLAG) != 0)
            {   
                colorCheck = "(" +
                        whiteCheck + " OR " +
                        blueCheck + " OR " +
                        blackCheck + " OR " +
                        redCheck + " OR " +
                        greenCheck + " OR " +
                        colorlessCheck + ")";
                
                // Only bother with excluding unselected if the box actually got checked:
                if (excUnselected)
                {
                    colorCheck += " AND " +
                                  "(" +
                                  excWhiteCheck + " AND " +
                                  excBlueCheck + " AND " +
                                  excBlackCheck + " AND " +
                                  excRedCheck + " AND " +
                                  excGreenCheck + ")";
                }
            }
        }
        
        String[] queryArray = queryParams.toArray(new String[0]);
        
        // Build final query
        query += nameCheck + " AND " +
                 textCheck + " AND " +
                 expansionCheck + " AND " +
                 typeCheck + " AND " +
                 rarityCheck + " AND " +
                 colorCheck + " AND " +
                 reqMultiCheck;
        
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
                if (!cursor.isNull(13))
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
        
        //Debug.stopMethodTracing();
        
        return toRet;
    }
    
    public ArrayList<CardData> SearchCollectionForCards(SearchParams params)
    {
        // Build dat query
        String query = "SELECT * FROM CollectedCards col_cards " +
                       "JOIN Cards card_table " +
                       "ON col_cards.CardID = card_table.CardID " +
                       "WHERE ";
        
        // For all the nulls, we'll use a dummy condition:
        String colCheck = "1 = 1";
        String textCheck = "1 = 1";
        String expansionCheck = "1 = 1";
        String rarityCheck = "1 = 1";
        String nameCheck = "1 = 1";
        String typeCheck = "1 = 1";
        String colorCheck = "1 = 1";
        String reqMultiCheck = "1 = 1";
        LinkedList<String> queryParams = new LinkedList<String>();
        
        // For the things which aren't null, build the appropriate replacement:
        if (params.CollectionId != null)
        {
            colCheck = "col_cards.CollectionID = ?";
            queryParams.addFirst(Integer.toString(params.CollectionId));
        }
        if (params.NameSearch != null)
        {
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
            boolean excUnselected = (params.ColorFilter & SearchParams.EXCLUDE_UNSELECTED_FLAG) != 0;
            
            String whiteCheck = "0 = 1";
            String excWhiteCheck = "1 = 1";
            String blueCheck = "0 = 1";
            String excBlueCheck = "1 = 1";
            String blackCheck = "0 = 1";
            String excBlackCheck = "1 = 1";
            String redCheck = "0 = 1";
            String excRedCheck = "1 = 1";
            String greenCheck = "0 = 1";
            String excGreenCheck = "1 = 1";
            String colorlessCheck = "0 = 1";
            
            int filter = params.ColorFilter;
            if ((filter & SearchParams.WHITE_FLAG) != 0)
                whiteCheck = "(card_table.Colors & 1) != 0";
            else if (excUnselected)
                excWhiteCheck = "(card_table.Colors & 1) = 0";
            if ((filter & SearchParams.BLUE_FLAG) != 0)
                blueCheck = "(card_table.Colors & 2) != 0";
            else if (excUnselected)
                excBlueCheck = "(card_table.Colors & 2) = 0";
            if ((filter & SearchParams.BLACK_FLAG) != 0)
                blackCheck = "(card_table.Colors & 4) != 0";
            else if (excUnselected)
                excBlackCheck = "(card_table.Colors & 4) = 0";
            if ((filter & SearchParams.RED_FLAG) != 0)
                redCheck = "(card_table.Colors & 8) != 0";
            else if (excUnselected)
                excRedCheck = "(card_table.Colors & 8) = 0";
            if ((filter & SearchParams.GREEN_FLAG) != 0)
                greenCheck = "(card_table.Colors & 16) != 0";
            else if (excUnselected)
                excGreenCheck = "(card_table.Colors & 16) = 0";
            if ((filter & SearchParams.COLORLESS_FLAG) != 0)
                colorlessCheck = "card_table.Colors = 0";
            if ((filter & SearchParams.REQUIRE_MULTICOLOR_FLAG) != 0)
            {
                // Bit twiddling hack explanation:
                //  requiring multicolor is equivalent to >= 2 bits on in color mask.
                //  This is equivalent to color mask is a non-zero non-power of 2.
                //  (x != 0) && !(x & (x - 1)) returns true iff x is a power of 2.
                //  In query form, that is (x != 0) AND ((x & (x-1)) = 0)
                //  Negating the whole expression returns true on 0 or a non-power of 2.
                //  Un-negating the first term excludes 0, but keeps everything else.
                reqMultiCheck = "((card_table.Colors != 0) AND " +
                                 "((card_table.Colors & (card_table.Colors - 1)) != 0))";
            }
            
            // If the only bit turned on is req. multicolor, we don't want to have a big
            // chain of falses killing our search.
            if ((filter ^ SearchParams.REQUIRE_MULTICOLOR_FLAG) != 0)
            {   
                colorCheck = "(" +
                        whiteCheck + " OR " +
                        blueCheck + " OR " +
                        blackCheck + " OR " +
                        redCheck + " OR " +
                        greenCheck + " OR " +
                        colorlessCheck + ")";
                
                // Only bother with excluding unselected if the box actually got checked:
                if (excUnselected)
                {
                    colorCheck += " AND " +
                                  "(" +
                                  excWhiteCheck + " AND " +
                                  excBlueCheck + " AND " +
                                  excBlackCheck + " AND " +
                                  excRedCheck + " AND " +
                                  excGreenCheck + ")";
                }
            }
        }
        
        String[] queryArray = queryParams.toArray(new String[0]);
        
        // Build final query
        query += colCheck + " AND " +
                 nameCheck + " AND " +
                 textCheck + " AND " +
                 expansionCheck + " AND " +
                 typeCheck + " AND " +
                 rarityCheck + " AND " +
                 colorCheck + " AND " +
                 reqMultiCheck;
        
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
                card.setCollectedId(cursor.getInt(0));
                card.setCardId(cursor.getInt(1));
                card.setRarity(cursor.getString(9));
                card.setName(cursor.getString(11));
                card.setCmc(cursor.getInt(12));
                card.setManaCost(cursor.getString(14));
                card.setPower(cursor.getString(16));
                card.setToughness(cursor.getString(17));
                if (!cursor.isNull(18))
                    card.setLoyalty(cursor.getInt(18));
                card.setText(cursor.getString(19));
                card.setFlavorText(cursor.getString(20));
                card.setCount(cursor.getInt(3));
                
                int colorCode = cursor.getInt(15);
                String typeCode = cursor.getString(10);
                String setCode = cursor.getString(7);
                String tagsCode = cursor.getString(2);
                
                card.setColorsFromCode(colorCode);
                card.setTypesFromCode(typeCode);
                card.setSetsFromCode(setCode);
                card.setTagsFromCode(tagsCode);
    
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
                if (!cursor.isNull(13))
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
    
    public void AddLocation(String locationName)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Locations(Name) VALUES(?)", new String[] {locationName});
        
        // Tell our listeners that we've changed the data
        RefreshListeners();
    }
    
    public void DeleteLocation(int locationId)
    {
        // Delete from Locations table:
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Locations WHERE LocationID = ?",
                new String[] {Integer.toString(locationId)});
        
        // Now, get list of collection IDs attached to this location
        ArrayList<Integer> collectionIds = new ArrayList<Integer>();
        Cursor cursor = db.rawQuery("SELECT CollectionID FROM Collections WHERE LocationID = ?",
                            new String[] {Integer.toString(locationId)});
        
        if (cursor.moveToFirst())
        {
            do
            {
                collectionIds.add(cursor.getInt(0));
            } while(cursor.moveToNext());
        }
        
        // Delete the collections previously grabbed
        db.execSQL("DELETE FROM Collections WHERE LocationID = ?",
                new String[] {Integer.toString(locationId)});
        
        // Finally, delete CollectedCards in these collections
        for (int id : collectionIds)
        {
            db.execSQL("DELETE FROM CollectedCards WHERE CollectionID = ?",
                    new String[] {Integer.toString(id)});
        }
        
        // Tell our listeners that we've changed the data
        RefreshListeners();
    }
    
    public void DeleteCollection(int collectionId)
    {
        // Delete the collections previously grabbed
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Collections WHERE CollectionID = ?",
                new String[] {Integer.toString(collectionId)});
        
        // Now, delete CollectedCards in these collections
        db.execSQL("DELETE FROM CollectedCards WHERE CollectionID = ?",
            new String[] {Integer.toString(collectionId)});
        
        // Tell our listeners that we've changed the data
        RefreshListeners();
    }
    
    public void DeleteCardFromCollection(int collectedId)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM CollectedCards WHERE CollectedID = ?",
                   new String[] {Integer.toString(collectedId)});
        
        // Tell our listeners that we've changed the data
        RefreshListeners();
    }
    
    public void RenameLocation(int locationId, String newName)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Locations SET Name=? WHERE LocationID=?",
                new String[] {newName, Integer.toString(locationId)});
        
        // Tell our listeners that we've changed the data
        RefreshListeners();
    }
    
    public void RenameCollection(int collectionId, String newName)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Collections SET Name=? WHERE CollectionID=?",
                new String[] {newName, Integer.toString(collectionId)});
        
        // Tell our listeners that we've changed the data
        RefreshListeners();
    }
    
    public void AddCollectionToLocation(int locationId, String collectionName)
    {
        // TODO: would probably be good form to confirm the locationID actually exists in db
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Collections (Name, LocationID) " +
                   "VALUES (?, ?)", new String[]{collectionName, Integer.toString(locationId)});
        
        // Tell our listeners that we've changed the data
        RefreshListeners();
    }
    
    public void AddCardToCollection(int collectionId, CardData card)
    {
        // TODO: handle tags properly
        
        // Check for preexisting entry:
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CollectedCards " +
                                    "WHERE CollectionID = ? AND CardID = ?", 
                                    new String[] {Integer.toString(collectionId),
                                                  Integer.toString(card.getCardId())});

        Integer oldCnt = null;
        if (cursor.moveToFirst())
        {
            // TODO: there may be multiple entries for differing tags. For now,
            // just assume that the tags match and go with it
            oldCnt = cursor.getInt(0);
        }
        
        // If we found a match, update the entry to increase count:
        if (oldCnt != null)
        {
            // TODO: update tags
            db.execSQL("UPDATE CollectedCards SET Count = ? " +
                       "WHERE CollectionID = ? AND CardID = ?",
                       new String[] {Integer.toString(oldCnt + card.getCount()),
                                     Integer.toString(collectionId),
                                     Integer.toString(card.getCardId())});
        }
        // If we didn't find a match, insert the card into the database:
        else
        {
            // TODO: insert tags
            db.execSQL("INSERT INTO CollectedCards (CollectionID, CardID, Count) " +
            		   "VALUES (?, ?, ?)",
            		   new String[] {Integer.toString(collectionId),
                                     Integer.toString(card.getCardId()),
                                     Integer.toString(card.getCount())});
        }
        
        // Tell our listeners that we've changed the data
        RefreshListeners();
    }
    
    public void UpdateCollectedCard(int oldCollectedId, CardData newData)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE CollectedCards SET " +
        		   "CardID = ?, Count = ? " +
        		   "WHERE CollectedID = ?",       // TODO: tags
        		   new String[] {Integer.toString(newData.getCardId()),
                                 Integer.toString(newData.getCount()),
                                 Integer.toString(oldCollectedId)});
        
        // Tell our listeners that we've changed the data
        RefreshListeners();
    }
    
    public void CopyCollection(int oldColId, String newName)
    {
        // First, get data we weren't given:
        int locId;
        int newColId;
        
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT LocationID FROM Collections " +
                                    "WHERE CollectionID = ?",
                                    new String[] {Integer.toString(oldColId)});
        
        if (cursor.moveToFirst())
            locId = cursor.getInt(0);
        // If we didn't find the collection, can't copy it!
        else
            return;
        
        // Now, insert new collection
        ContentValues values = new ContentValues();
        values.put("Name", newName);
        values.put("LocationID", locId);
        newColId = (int)db.insert("Collections", null, values);
        
        // Finally, copy all the cards! :o
        db.execSQL("INSERT INTO CollectedCards(CardID, CollectionID, Count, Tags) " +
                   "SELECT CardID, ?, Count, Tags FROM CollectedCards " +
                   "WHERE CollectionID = ?",
                   new String[] {Integer.toString(newColId), Integer.toString(oldColId)});
        
        // Tell our listeners that we've changed the data
        RefreshListeners();
    }
    
    public void MoveCollectionToLocation(int collectionId, int locationId)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Collections SET LocationID = ? WHERE CollectionID = ?",
                   new String[] {Integer.toString(locationId), Integer.toString(collectionId)});
        
        // Tell our listeners we've changed the data
        RefreshListeners();
    }
    
    public ArrayList<Location> GetLocations()
    {
        ArrayList<Location> toRet = new ArrayList<Location>();
        
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT LocationID, Name FROM Locations", null);
        
        if (cursor.moveToFirst())
        {
            do
            {
                Location l = new Location();
                l.LocationId = cursor.getInt(0);
                l.Name = cursor.getString(1);
                toRet.add(l);
            } while(cursor.moveToNext());
        }
        
        return toRet;
    }
    
    public ArrayList<Collection> GetCollections()
    {
        ArrayList<Collection> toRet = new ArrayList<Collection>();
        
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT CollectionID, Name FROM Collections", null);
        
        if (cursor.moveToFirst())
        {
            do
            {
                Collection c = new Collection();
                c.CollectionId = cursor.getInt(0);
                c.Name = cursor.getString(1);
                toRet.add(c);
            } while(cursor.moveToNext());
        }
        
        return toRet;
    }
    
    public CollectionModel GetLocationsWithCollections()
    {
        CollectionModel toRet = new CollectionModel();
        
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Locations", null);
        
        if (cursor.moveToFirst())
        {
            do
            {
                Location l = new Location();
                l.LocationId = cursor.getInt(0);
                l.Name = cursor.getString(1);
                toRet.AddLocation(l);
            } while(cursor.moveToNext());
        }
        
        // Now fill in with collections:
        cursor = db.rawQuery("SELECT * FROM Collections collection_table", null);
        
        if (cursor.moveToFirst())
        {
            do
            {
                Collection c = new Collection();
                c.CollectionId = cursor.getInt(0);
                c.Name = cursor.getString(1);
                int locationId = cursor.getInt(2);
                
                toRet.AddCollection(c, locationId);
            } while(cursor.moveToNext());
        }
        
        return toRet;
    }
    
    private static MtgDatabaseManager instance = null;
    public static synchronized MtgDatabaseManager getInstance(Context _context)
    {
        if (instance == null)
            instance = new MtgDatabaseManager(_context.getApplicationContext());
        return instance;
    }
}
