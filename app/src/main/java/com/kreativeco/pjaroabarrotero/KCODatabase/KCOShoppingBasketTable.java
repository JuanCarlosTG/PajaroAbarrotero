package com.kreativeco.pjaroabarrotero.KCODatabase;

import android.provider.BaseColumns;

public final class KCOShoppingBasketTable
{
    /*Empty Constructor*/
    public KCOShoppingBasketTable()
    {
    }

    /* Abstract class with the databases' names */
    public static abstract class BasketDataBase implements BaseColumns
    {

        public static final String BASKET_DATABASE_NAME = "shopping_basket_database";

        //public static final String COLUMN_NAME_ID = "id";
        public static final String TABLE_NAME = "PRODUCT";
        public static final String COLUMN_NAME_PRODUCT_NAME = "product_name";
        public static final String COLUMN_NAME_PRODUCT_IMAGE = "product_image";
        public static final String COLUMN_NAME_PRODUCT_CODE = "product_code";
        public static final String COLUMN_NAME_NUMBER_PRODUCTS = "number_products";
        public static final String COLUMN_NAME_PRODUCT_PRICE = "price_product";
        public static final String COLUMN_NAME_TOTAL = "total";
    }

}
