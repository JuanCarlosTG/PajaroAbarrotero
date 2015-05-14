package com.kreativeco.pjaroabarrotero.KCODatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kreativeco.pjaroabarrotero.KCODatabase.KCOShoppingBasketTable.BasketDataBase;

public class KCOConnectionDataBase extends SQLiteOpenHelper {

    public static final  int databaseVersion = 1;
    public String createTable = "CREATE TABLE " + BasketDataBase.TABLE_NAME + "("
            + BasketDataBase.COLUMN_NAME_PRODUCT_NAME + " VARCHAR(99), "
            + BasketDataBase.COLUMN_NAME_PRODUCT_IMAGE + " VARCHAR(50), "
            + BasketDataBase.COLUMN_NAME_PRODUCT_CODE + " VARCHAR(99), "
            + BasketDataBase.COLUMN_NAME_PRODUCT_PRICE + " REAL, "
            + BasketDataBase.COLUMN_NAME_NUMBER_PRODUCTS + " INT, "
            + BasketDataBase.COLUMN_NAME_TOTAL + " REAL);";


    public KCOConnectionDataBase(Context context) {
        super(context, BasketDataBase.BASKET_DATABASE_NAME, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("String createTable", createTable);
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertInformation(KCOConnectionDataBase connectDB, String WSName, String WSImage, String WSCode, String WSPrice, String numProd, String total)
    {
        SQLiteDatabase SqlDB = connectDB.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(BasketDataBase.COLUMN_NAME_PRODUCT_NAME, WSName);
        contentValues.put(BasketDataBase.COLUMN_NAME_PRODUCT_IMAGE, WSImage);
        contentValues.put(BasketDataBase.COLUMN_NAME_PRODUCT_CODE, WSCode);
        contentValues.put(BasketDataBase.COLUMN_NAME_PRODUCT_PRICE, WSPrice);
        contentValues.put(BasketDataBase.COLUMN_NAME_NUMBER_PRODUCTS, numProd);
        contentValues.put(BasketDataBase.COLUMN_NAME_TOTAL, total);

        SqlDB.insert(BasketDataBase.TABLE_NAME, null, contentValues);
        //Log.d("Database insertInformation", "Product inserted in Data Base");

    }

    public Cursor getInformationFromBasket(KCOConnectionDataBase connectDB)
    {
        SQLiteDatabase SqlDB = connectDB.getReadableDatabase();
        String[] columns =
                {
                        BasketDataBase.COLUMN_NAME_PRODUCT_NAME,
                        BasketDataBase.COLUMN_NAME_PRODUCT_IMAGE,
                        BasketDataBase.COLUMN_NAME_PRODUCT_CODE,
                        BasketDataBase.COLUMN_NAME_PRODUCT_PRICE,
                        BasketDataBase.COLUMN_NAME_NUMBER_PRODUCTS,
                        BasketDataBase.COLUMN_NAME_TOTAL,
                };
        return SqlDB.query(BasketDataBase.TABLE_NAME, columns, null, null, null, null, null);
    }

    public void deleteInformationrFromBasket(KCOConnectionDataBase connectDB){
        SQLiteDatabase SqlDB = connectDB.getWritableDatabase();
        SqlDB.execSQL("DELETE FROM '" + BasketDataBase.TABLE_NAME + "'");

    }

    public void deleteItem(KCOConnectionDataBase connectDB, String name){
        SQLiteDatabase SqlDB = connectDB.getWritableDatabase();
        SqlDB.delete(BasketDataBase.TABLE_NAME, BasketDataBase.COLUMN_NAME_PRODUCT_NAME + " = " + name, null);
    }

}
