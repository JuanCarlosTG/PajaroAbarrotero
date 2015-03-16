package com.kreativeco.pjaroabarrotero.libraries;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class KCOASOrdersToCustomer extends AsyncTask<String, Integer, ArrayList<HashMap<String, String>>> {
    public KCOAsyncResponse delegate = null;

    public KCOASOrdersToCustomer(KCOAsyncResponse delegate){
        this.delegate = delegate;
    }

    JSONArray orders = null;
    private static final String ID = "id";
    private static final String FOLIO = "folio_number";
    private static final String STATUS = "status";
    private static final String DATE = "date";
    private static final String CODE_CUST = "code_customer";

    @Override
    protected ArrayList<HashMap<String, String>> doInBackground(String...params) {

        KCOUserFunctions userFun = new KCOUserFunctions();
        JSONObject json = userFun.getOrdersToCust(params[0],params[1]);

        ArrayList<HashMap<String, String>> listOrdersToCust = new ArrayList<>();

        try{
            orders = json.getJSONArray("orders");
            if(orders==null){
                Log.d("ORDERS","No hay registros en la BD");
            }

            for(int i = 0; i < orders.length(); ++i){
                JSONObject aux = orders.getJSONObject(i);

                String idOrder = aux.getString(ID);
                String folioOrder = aux.getString(FOLIO);
                String statusOrder = aux.getString(STATUS);
                String dateOrder = aux.getString(DATE);
                String codeOrder = aux.getString(CODE_CUST);

                HashMap<String, String> map = new HashMap<>();

                map.put(ID, idOrder);
                map.put(FOLIO, folioOrder);
                map.put(STATUS, statusOrder);
                map.put(DATE, dateOrder);
                map.put(CODE_CUST, codeOrder);

                listOrdersToCust.add(map);
            }
            return listOrdersToCust;
        }
        catch(JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<HashMap<String, String>> listOrdersToCust) {
        delegate.processFinish(listOrdersToCust);

    }
}

/*
new KCOASOrdersToCustomer(new KCOAsyncResponse() {
    @Override
    public void processFinish(ArrayList<HashMap<String, String>> output) {
        // do whatever you want with the result
    }
}).execute(TOKEN,STATUS);
* */