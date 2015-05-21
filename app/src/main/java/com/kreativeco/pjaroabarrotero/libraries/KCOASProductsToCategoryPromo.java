package com.kreativeco.pjaroabarrotero.libraries;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class KCOASProductsToCategoryPromo extends AsyncTask<String, Integer, ArrayList<HashMap<String, String>>> {
    public KCOAsyncResponseP delegate = null;

    public KCOASProductsToCategoryPromo(KCOAsyncResponseP delegate){
        this.delegate = delegate;
    }

    JSONArray productsAmount = null;
    JSONArray productsPackage = null;
    JSONArray productsDiscount = null;
    //private static final String ID = "id";
    private static final String COD = "cod";
    private static final String NAME = "name";
    private static final String IVA = "iva";
    private static final String IMAGEURL = "file_image";
    private static final String COST = "cunit";
    private static final String MINIMUMUNITS = "min";
    private static final String PROMOTION = "promotion_id";

    private static final String ID_PROMOTIONS = "id_promotions";
    private static final String DESCRIPTION = "description";
    private static final String AMOUNT = "amount";
    private static final String AMOUNT_TOTAL = "amount_total";
    private static final String PERCENTAJE = "percentaje";
    private static final String PRODUCTS = "products";

    private static final String CODE_P ="cod";
    private static final String NAME_P ="name";
    private static final String IVA_P ="iva";
    private static final String CUNIT_P ="cunit";
    private static final String MIN_P ="min";
    private static final String CUNIT_T_P ="cunit_total";
    private static final String WS_P ="ws";
    private static final String WS_M_P ="ws_middle";
    private static final String FI_P ="file_image";
    private static final String PROMO_ID_P ="promotion_id";

    private static final String FILE_IMAGE_PROMO ="file_image_promotions";

    @Override
    protected ArrayList<HashMap<String, String>> doInBackground(String...params) {

        KCOUserFunctions userFun = new KCOUserFunctions();
        JSONObject json = userFun.getProductsToCat(params[0],params[1]);

        ArrayList<HashMap<String, String>> listProductsToCatA = new ArrayList<>();
        ArrayList<HashMap<String, String>> listProductsToCatP = new ArrayList<>();
        ArrayList<HashMap<String, String>> listProductsToCatD = new ArrayList<>();

        try{
            productsAmount = json.getJSONArray("promo_amount");

            for(int i = 0; i < productsAmount.length(); ++i){
                JSONObject aux = productsAmount.getJSONObject(i);

                String idPromotions = aux.getString(ID_PROMOTIONS);
                String description = aux.getString(DESCRIPTION);
                String amount = aux.getString(AMOUNT);


                ArrayList<HashMap<String, String>> listProductsAmount = new ArrayList<>();
                JSONArray productsA = null;
                productsA = aux.getJSONArray("products");

                for(int j = 0; j < productsA.length(); ++j){
                    JSONObject aux2 = productsA.getJSONObject(j);

                    String code = aux2.getString(CODE_P);
                    String name = aux2.getString(NAME_P);
                    String iva = aux2.getString(IVA_P);
                    String cunit = aux2.getString(CUNIT_P);
                    String min = aux2.getString(MIN_P);
                    String cunit_total = aux2.getString(CUNIT_T_P);
                    String ws = aux2.getString(WS_P);
                    String ws_middle = aux2.getString(WS_M_P);
                    String file_image = aux2.getString(FI_P);
                    String promotion_id = aux2.getString(PROMO_ID_P);

                    HashMap<String, String> map2 = new HashMap<>();

                    map2.put(CODE_P, code);
                    map2.put(NAME_P, name);
                    map2.put(IVA_P, iva);
                    map2.put(CUNIT_P, cunit);
                    map2.put(MIN_P, min);
                    map2.put(CUNIT_T_P, cunit_total);
                    map2.put(WS_P, ws);
                    map2.put(WS_M_P, ws_middle);
                    map2.put(FI_P, file_image);
                    map2.put(PROMO_ID_P, promotion_id);

                    listProductsAmount.add(map2);
                }

                //String products = aux.getString(PRODUCTS);

                HashMap<String, String> map = new HashMap<>();

                map.put(ID_PROMOTIONS, idPromotions);
                map.put(DESCRIPTION, description);
                map.put(AMOUNT, amount);
                map.put(PRODUCTS, String.valueOf(listProductsAmount));

                listProductsToCatA.add(map);
            }
            //return listProductsToCat;
        }
        catch(JSONException e){
            e.printStackTrace();
            return null;
        }


        /*PROMO_PACKAGE*/
        try{
            productsPackage = json.getJSONArray("promo_package");

            for(int i = 0; i < productsPackage.length(); ++i){
                JSONObject aux = productsPackage.getJSONObject(i);

                String idPromotions = aux.getString(ID_PROMOTIONS);
                String description = aux.getString(DESCRIPTION);
                String amount_total = aux.getString(AMOUNT_TOTAL);
                String file_ip = aux.getString(FILE_IMAGE_PROMO);


                ArrayList<HashMap<String, String>> listProductsPackage = new ArrayList<>();
                JSONArray productsP = null;
                productsP = aux.getJSONArray("products");

                for(int j = 0; j < productsP.length(); ++j){
                    JSONObject aux2 = productsP.getJSONObject(j);

                    String code = aux2.getString(CODE_P);
                    String name = aux2.getString(NAME_P);
                    String iva = aux2.getString(IVA_P);
                    String cunit = aux2.getString(CUNIT_P);
                    String min = aux2.getString(MIN_P);
                    String cunit_total = aux2.getString(CUNIT_T_P);
                    String ws = aux2.getString(WS_P);
                    String ws_middle = aux2.getString(WS_M_P);
                    String file_image = aux2.getString(FI_P);
                    String promotion_id = aux2.getString(PROMO_ID_P);

                    HashMap<String, String> map2 = new HashMap<>();

                    map2.put(CODE_P, code);
                    map2.put(NAME_P, name);
                    map2.put(IVA_P, iva);
                    map2.put(CUNIT_P, cunit);
                    map2.put(MIN_P, min);
                    map2.put(CUNIT_T_P, cunit_total);
                    map2.put(WS_P, ws);
                    map2.put(WS_M_P, ws_middle);
                    map2.put(FI_P, file_image);
                    map2.put(PROMO_ID_P, promotion_id);

                    listProductsPackage.add(map2);
                }

                //String products = aux.getString(PRODUCTS);

                HashMap<String, String> map = new HashMap<>();

                map.put(ID_PROMOTIONS, idPromotions);
                map.put(DESCRIPTION, description);
                map.put(AMOUNT_TOTAL, amount_total);
                map.put(FILE_IMAGE_PROMO, file_ip);
                map.put(PRODUCTS, String.valueOf(listProductsPackage));

                listProductsToCatP.add(map);
            }
            //return listProductsToCat;
        }
        catch(JSONException e){
            e.printStackTrace();
            return null;
        }


        /*PROMO_DISCOUNT*/

        try{
            productsDiscount = json.getJSONArray("promo_discount");

            for(int i = 0; i < productsDiscount.length(); ++i){
                JSONObject aux = productsDiscount.getJSONObject(i);

                String idPromotions = aux.getString(ID_PROMOTIONS);
                String description = aux.getString(DESCRIPTION);
                String percentaje = aux.getString(PERCENTAJE);


                ArrayList<HashMap<String, String>> listProductsDiscount = new ArrayList<>();
                JSONArray productsD = null;
                productsD = aux.getJSONArray("products");

                for(int j = 0; j < productsD.length(); ++j){
                    JSONObject aux2 = productsD.getJSONObject(j);

                    String code = aux2.getString(CODE_P);
                    String name = aux2.getString(NAME_P);
                    String iva = aux2.getString(IVA_P);
                    String cunit = aux2.getString(CUNIT_P);
                    String min = aux2.getString(MIN_P);
                    String cunit_total = aux2.getString(CUNIT_T_P);
                    String ws = aux2.getString(WS_P);
                    String ws_middle = aux2.getString(WS_M_P);
                    String file_image = aux2.getString(FI_P);
                    String promotion_id = aux2.getString(PROMO_ID_P);

                    HashMap<String, String> map2 = new HashMap<>();

                    map2.put(CODE_P, code);
                    map2.put(NAME_P, name);
                    map2.put(IVA_P, iva);
                    map2.put(CUNIT_P, cunit);
                    map2.put(MIN_P, min);
                    map2.put(CUNIT_T_P, cunit_total);
                    map2.put(WS_P, ws);
                    map2.put(WS_M_P, ws_middle);
                    map2.put(FI_P, file_image);
                    map2.put(PROMO_ID_P, promotion_id);

                    listProductsDiscount.add(map2);
                }

                //String products = aux.getString(PRODUCTS);

                HashMap<String, String> map = new HashMap<>();

                map.put(ID_PROMOTIONS, idPromotions);
                map.put(DESCRIPTION, description);
                map.put(PERCENTAJE, percentaje);
                map.put(PRODUCTS, String.valueOf(listProductsDiscount));

                listProductsToCatD.add(map);
            }
            //return listProductsToCat;
        }
        catch(JSONException e){
            e.printStackTrace();
            return null;
        }

        ArrayList listProductsPromo = new ArrayList<>();
        listProductsPromo.add(listProductsToCatA);
        listProductsPromo.add(listProductsToCatP);
        listProductsPromo.add(listProductsToCatD);
        return listProductsPromo;
    }

    @Override
    protected void onPostExecute(ArrayList listProductsPromo) {
        delegate.processFinishP(listProductsPromo);

    }
}