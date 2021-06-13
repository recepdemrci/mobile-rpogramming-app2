package tr.edu.yildiz.recepdemirci.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.time.LocalDate;
import java.util.ArrayList;

import tr.edu.yildiz.recepdemirci.Item;

public class ItemDB extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "Item";

    private static final String COL_1 = "ID";
    private static final String COL_2 = "type";
    private static final String COL_3 = "color";
    private static final String COL_4 = "pattern";
    private static final String COL_5 = "price";
    private static final String COL_6 = "date";
    private static final String COL_7 = "image_path";
    private static final String COL_8 = "closet_idx";

    public ItemDB(Context context) {
        super(context, TABLE_NAME, null, 1);
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT, " + COL_3 + " TEXT, " + COL_4 + " TEXT, " + COL_5 + " REAL, " + COL_6 + " TEXT, " + COL_7 + " TEXT, " + COL_8 + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public long add(Item item, int closet_idx) {
        int numberOfRecord;
        SQLiteDatabase db = this.getWritableDatabase();
        // Control if same record is already exist
        String controlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "
                + COL_2 + "=?" + " AND "
                + COL_3 + "=?" + " AND "
                + COL_4 + "=?" + " AND "
                + COL_5 + "=" + item.getPrice() + " AND "
                + COL_6 + "=?" + " AND "
                + COL_7 + "=?" + " AND "
                + COL_8 + "=" + closet_idx;
        Cursor cursor = db.rawQuery(controlQuery, new String[] {item.getType(), item.getColor(), item.getPattern(), item.getPurchaseDateString(), item.getImagePath()});
        numberOfRecord = cursor.getCount();
        cursor.close();
        if (numberOfRecord > 0) {
            return -1;
        }

        // Create new record and insert it into table
        // If any error occurred return -1, else return id of the record
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, item.getType());
        contentValues.put(COL_3, item.getColor());
        contentValues.put(COL_4, item.getPattern());
        contentValues.put(COL_5, item.getPrice());
        contentValues.put(COL_6, item.getPurchaseDateString());
        contentValues.put(COL_7, item.getImagePath());
        contentValues.put(COL_8, closet_idx);
        return db.insert(TABLE_NAME, null, contentValues);
    }

    public long update(Item item, int closet_idx) {
        int numberOfRecord;
        SQLiteDatabase db = this.getWritableDatabase();
        // Control if same record is already exist
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, item.getType());
        contentValues.put(COL_3, item.getColor());
        contentValues.put(COL_4, item.getPattern());
        contentValues.put(COL_5, item.getPrice());
        contentValues.put(COL_6, item.getPurchaseDateString());
        contentValues.put(COL_7, item.getImagePath());
        contentValues.put(COL_8, closet_idx);
        return db.update(TABLE_NAME, contentValues, "ID=?", new String[] {String.valueOf(item.getId())});
    }

    public int remove(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID=?", new String[] {String.valueOf(id)});
    }

    public Item findById(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String findQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "
                + COL_1 + "=" + id;
        Cursor cursor = db.rawQuery(findQuery, null);

        if (cursor.moveToNext()) {
            Item item = createItem(cursor);
            cursor.close();
            return item;
        }
        else {
            return null;
        }
    }

    public ArrayList<Item> getItems(int closet_idx, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Item> items = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_8 + "=" + closet_idx + " AND " + COL_2 + "=?";
        Cursor cursor = db.rawQuery(query, new String[] {type});


        while (cursor.moveToNext()) {
            items.add(createItem(cursor));
        }
        return items;
    }


    public int getClosetCount() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL_8 + " FROM " + TABLE_NAME + " GROUP BY " + COL_8;
        Cursor cursor = db.rawQuery(query, null);
        int closet_count = cursor.getCount();
        cursor.close();
        return closet_count;
    }

    public Item createItem(Cursor cursor) {
        long id = cursor.getLong(0);
        String type = cursor.getString(1);
        String color = cursor.getString(2);
        String pattern = cursor.getString(3);
        float price = cursor.getFloat(4);
        String date = cursor.getString(5);
        String path = cursor.getString(6);
        LocalDate date_ = LocalDate.of(Integer.parseInt(date.split("-")[2]), Integer.parseInt(date.split("-")[1]), Integer.parseInt(date.split("-")[0]));
        Item item = new Item(type, color, pattern, date_, price, path);
        item.setId(id);
        return item;
    }
}
