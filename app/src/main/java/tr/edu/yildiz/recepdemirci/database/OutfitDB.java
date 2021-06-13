package tr.edu.yildiz.recepdemirci.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import tr.edu.yildiz.recepdemirci.Item;
import tr.edu.yildiz.recepdemirci.Outfit;

public class OutfitDB extends SQLiteOpenHelper {
    private Context context;
    private static final String TABLE_NAME = "Outfit";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "overhead_id";
    private static final String COL_3 = "face_id";
    private static final String COL_4 = "top_id";
    private static final String COL_5 = "bottom_id";
    private static final String COL_6 = "shoe_id";


    public OutfitDB (Context context) {
        super(context, TABLE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY, " +
                COL_2 + " INTEGER, " + COL_3 + " INTEGER, " + COL_4 + " INTEGER, "+ COL_5 + " INTEGER, " + COL_6  + " INTEGER, " +
                " FOREIGN KEY(" + COL_2 + ") REFERENCES Item(ID) ON DELETE SET NULL," + " FOREIGN KEY(" + COL_3 + ") REFERENCES Item(ID) ON DELETE SET NULL," +
                " FOREIGN KEY(" + COL_4 + ") REFERENCES Item(ID) ON DELETE SET NULL," + " FOREIGN KEY(" + COL_5 + ") REFERENCES Item(ID) ON DELETE SET NULL," +
                " FOREIGN KEY(" + COL_6 + ") REFERENCES Item(ID) ON DELETE SET NULL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long add(long _id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, _id);
        return db.insert(TABLE_NAME, null, contentValues);
    }

    public Outfit findById(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String findQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                COL_1 + "=" + id;
        Cursor cursor = db.rawQuery(findQuery, null);

        if (cursor.moveToNext()) {
            Outfit outfit = createOutfit(cursor);
            cursor.close();
            return outfit;
        }
        else {
            return null;
        }
    }

    public ArrayList<Outfit> getOutfits() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Outfit> outfits = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            outfits.add(createOutfit(cursor));
        }
        return outfits;
    }

    public Outfit createOutfit(Cursor cursor) {
        ItemDB item_db = new ItemDB(context);
        long id = cursor.getLong(0);
        Item overhead = item_db.findById(cursor.getLong(1));
        Item face = item_db.findById(cursor.getLong(2));
        Item top = item_db.findById(cursor.getLong(3));
        Item bottom = item_db.findById(cursor.getLong(4));
        Item shoe = item_db.findById(cursor.getLong(5));
        Outfit outfit = new Outfit(overhead, face, top, bottom, shoe);
        outfit.setId(id);
        return outfit;
    }

    public void setOutfit(long id, String col, long item_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + col + "_id" + "=" + item_id + " WHERE ID=" + id;
        db.execSQL(query);
    }

    public int remove(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID=?", new String[] {String.valueOf(id)});
    }

}
