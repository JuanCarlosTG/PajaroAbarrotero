package com.kreativeco.pjaroabarrotero.libraries;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class KCOASOrderToCDetails extends AsyncTask<String, Integer, ArrayList<HashMap<String, String>>> {
    public KCOAsyncResponse delegate = null;

    public KCOASOrderToCDetails(KCOAsyncResponse delegate){
        this.delegate = delegate;
    }

    JSONArray orders = null;
    private static final String ID = "id";
    private static final String FOLIO = "folio_number";
    private static final String STATUS = "status";
    private static final String DATE = "date";
    private static final String CODE_CUST = "code_customer";
    private static final String CODE_PROD = "cod_product";
    private static final String PRICE = "price";
    private static final String COD = "cod";
    private static final String NAME = "name";

    @Override
    protected ArrayList<HashMap<String, String>> doInBackground(String...params) {

        KCOUserFunctions userFun = new KCOUserFunctions();
        JSONObject json = userFun.getOrdersToCustDetail(params[0],params[1]);

        ArrayList<HashMap<String, String>> listOrdersToCDetails = new ArrayList<>();

        try{
            orders = json.getJSONArray("order");

            for(int i = 0; i < orders.length(); ++i){
                JSONObject aux = orders.getJSONObject(i);

                String idOrder = aux.getString(ID);
                String folioOrder = aux.getString(FOLIO);
                String statusOrder = aux.getString(STATUS);
                String dateOrder = aux.getString(DATE);
                String codeOrder = aux.getString(CODE_CUST);
                String codeProdOrder = aux.getString(CODE_PROD);
                String codePriceOrder = aux.getString(PRICE);
                String codeCodOrder = aux.getString(COD);
                String codeNameOrder = aux.getString(NAME);

                HashMap<String, String> map = new HashMap<>();

                map.put(ID, idOrder);
                map.put(FOLIO, folioOrder);
                map.put(STATUS, statusOrder);
                map.put(DATE, dateOrder);
                map.put(CODE_CUST, codeOrder);
                map.put(CODE_PROD, codeProdOrder);
                map.put(PRICE, codePriceOrder);
                map.put(COD, codeCodOrder);
                map.put(NAME, codeNameOrder);

                listOrdersToCDetails.add(map);
            }
            return listOrdersToCDetails;
        }
        catch(JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<HashMap<String, String>> listOrdersToCDetails) {
        delegate.processFinish(listOrdersToCDetails);

    }
}

/*
new KCOASOrderToCDetails(new KCOAsyncResponse() {
    @Override
    public void processFinish(ArrayList<HashMap<String, String>> output) {
        // do whatever you want with the result
    }
}).execute(TOKEN,FOLIO_NUMBER);
* */

