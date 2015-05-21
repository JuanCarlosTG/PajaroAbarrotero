package com.kreativeco.pjaroabarrotero.libraries;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class KCOASGetShopActives extends AsyncTask<String, Integer, ArrayList<HashMap<String, String>>> {
        public KCOAsyncResponse delegate = null;

        public KCOASGetShopActives(KCOAsyncResponse delegate){
            this.delegate = delegate;
        }

        JSONArray shops = null;
        private static final String SHOP = "shop";
        private static final String CODE = "code";
        private static final String CODE_CUSTOMER = "code_customer";
        private static final String CONTACT = "contact";
        private static final String ADDRESS = "address";
        private static final String REFERENCE_ADDRESS = "reference_address";
        private static final String COLONY = "colony";
        private static final String CITY = "city";
        private static final String ZIP = "zip";
        private static final String FILE_IMAGE = "file_image";

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(String...params) {

            KCOUserFunctions userFun = new KCOUserFunctions();
            JSONObject json = userFun.getShopActives(params[0]);

            ArrayList<HashMap<String, String>> listShopsActives = new ArrayList<>();

            try{
                shops = json.getJSONArray("active_shops");

                for(int i = 0; i < shops.length(); ++i){
                    JSONObject aux = shops.getJSONObject(i);

                    String shop = aux.getString(SHOP);
                    String code = aux.getString(CODE);
                    String code_customer = aux.getString(CODE_CUSTOMER);
                    String contact = aux.getString(CONTACT);
                    String address = aux.getString(ADDRESS);
                    String reference_address = aux.getString(REFERENCE_ADDRESS);
                    String colony = aux.getString(COLONY);
                    String city = aux.getString(CITY);
                    String zip = aux.getString(ZIP);
                    String file_image = aux.getString(FILE_IMAGE);


                    HashMap<String, String> map = new HashMap<>();

                    map.put(SHOP, shop);
                    map.put(CODE, code);
                    map.put(CODE_CUSTOMER, code_customer);
                    map.put(CONTACT, contact);
                    map.put(ADDRESS, address);
                    map.put(REFERENCE_ADDRESS, reference_address);
                    map.put(COLONY, colony);
                    map.put(CITY, city);
                    map.put(ZIP, zip);
                    map.put(FILE_IMAGE, file_image);

                    listShopsActives.add(map);
                }
                return listShopsActives;
            }
            catch(JSONException e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> listShopsActives) {
            delegate.processFinish(listShopsActives);

        }
    }
