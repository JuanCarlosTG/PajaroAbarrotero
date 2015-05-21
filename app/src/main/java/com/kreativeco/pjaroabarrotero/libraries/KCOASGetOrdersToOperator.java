package com.kreativeco.pjaroabarrotero.libraries;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class KCOASGetOrdersToOperator extends AsyncTask<String, Integer, ArrayList<HashMap<String, String>>> {
    public KCOAsyncResponse delegate = null;

    public KCOASGetOrdersToOperator(KCOAsyncResponse delegate){
        this.delegate = delegate;
    }

        JSONArray ordersToOp = null;
        private static final String SHOP = "shop";
        private static final String CODE_CUSTOMER = "code_customer";
        private static final String FOLIO_NUMBER = "folio_number";
        private static final String STATUS = "status";

    @Override
    protected ArrayList<HashMap<String, String>> doInBackground(String...params) {

        KCOUserFunctions userFun = new KCOUserFunctions();
        JSONObject json = userFun.getOrdersOp(params[0], params[1], params[2]);

        ArrayList<HashMap<String, String>> listOrdersToOp = new ArrayList<>();

        try{
            ordersToOp = json.getJSONArray("list_orders");

            for(int i = 0; i < ordersToOp.length(); ++i){
                JSONObject aux = ordersToOp.getJSONObject(i);

                String shop = aux.getString(SHOP);
                String code_customer = aux.getString(CODE_CUSTOMER);
                String folio_number = aux.getString(FOLIO_NUMBER);
                String status = aux.getString(STATUS);

                HashMap<String, String> map = new HashMap<>();

                map.put(SHOP, shop);
                map.put(CODE_CUSTOMER, code_customer);
                map.put(FOLIO_NUMBER, folio_number);
                map.put(STATUS, status);

                listOrdersToOp.add(map);
            }
            return listOrdersToOp;
        }
        catch(JSONException e){
             e.printStackTrace();
             return null;
          }
    }

    @Override
    protected void onPostExecute(ArrayList<HashMap<String, String>> listOrdersToOp) {
        delegate.processFinish(listOrdersToOp);
    }
}
