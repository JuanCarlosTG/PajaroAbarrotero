package com.kreativeco.pjaroabarrotero.libraries;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class KCOASProductsToCategory extends AsyncTask<String, Integer, ArrayList<HashMap<String, String>>> {
    public KCOAsyncResponse delegate = null;

    public KCOASProductsToCategory(KCOAsyncResponse delegate){
        this.delegate = delegate;
    }

    JSONArray products = null;
    private static final String ID = "id";
    private static final String COD = "cod";
    private static final String NAME = "name";
    private static final String IVA = "iva";
    private static final String IMAGEURL = "file_image";

    @Override
    protected ArrayList<HashMap<String, String>> doInBackground(String...params) {

        KCOUserFunctions userFun = new KCOUserFunctions();
        JSONObject json = userFun.getProductsToCat(params[0],params[1]);

        ArrayList<HashMap<String, String>> listProductsToCat = new ArrayList<>();

        try{
            products = json.getJSONArray("products");

            for(int i = 0; i < products.length(); ++i){
                JSONObject aux = products.getJSONObject(i);

                String nameProduct = aux.getString(NAME);
                String idProduct = aux.getString(ID);
                String codProduct = aux.getString(COD);
                String ivaProduct = aux.getString(IVA);
                String imageProduct = aux.getString(IMAGEURL);

                HashMap<String, String> map = new HashMap<>();

                map.put(NAME, nameProduct);
                map.put(ID, idProduct);
                map.put(COD, codProduct);
                map.put(IVA, ivaProduct);
                map.put(IMAGEURL, imageProduct);

                listProductsToCat.add(map);
            }
            return listProductsToCat;
        }
        catch(JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<HashMap<String, String>> listProductsToCat) {
        delegate.processFinish(listProductsToCat);

    }
}

/*
new KCOASProductsToCategory(new KCOAsyncResponse() {
    @Override
    public void processFinish(ArrayList<HashMap<String, String>> output) {
        // do whatever you want with the result
    }
}).execute(TOKEN,CATEGORYS_ID);
* */