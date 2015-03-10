package com.kreativeco.pjaroabarrotero.libraries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.util.Log;

public class FuncionesDeUsuario {

    private ParseaJSON jsonParser;

    private static String webServicesURL = "http://kreativeco.com/TESTING/pabarrotero/actions/webservices.php";
    private static String etiqueta_login = "login";
    private static String etiqueta_addP = "addProfile";
    private static String etiqueta_recPass = "recoverPass";
    private static String etiqueta_getP = "getProfile";
    private static String etiqueta_getPTC = "getProductsToCategory";
    private static String etiqueta_getProduct = "getProduct";
    private static String etiqueta_addO = "addOrder";
    private static String etiqueta_getO = "getOrdersToCustomer";
    private static String etiqueta_getOTCD = "getOrderToCustomerDetail";

    // constructor
    public FuncionesDeUsuario(){
        jsonParser = new ParseaJSON();
    }

    /**
     * funcion que hace la petición de Login
     * @param username
     * @param password
     * */
    public JSONObject loginApp(String username,String password){
        // Construimos los parametros
        List<NameValuePair> parametros = new ArrayList<NameValuePair>();
        parametros.add(new BasicNameValuePair("task", etiqueta_login));
        parametros.add(new BasicNameValuePair("username", username));
        parametros.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(webServicesURL, parametros);
        // return json
        Log.d("JSON", json.toString());
        return json;
    }

    public JSONObject addProfile(String shop,String name, String address, String latitud,String longitud){
        // Construimos los parametros
        List<NameValuePair> parametros = new ArrayList<NameValuePair>();
        parametros.add(new BasicNameValuePair("task", etiqueta_addP));
        parametros.add(new BasicNameValuePair("shop", shop));
        parametros.add(new BasicNameValuePair("name", name));
        parametros.add(new BasicNameValuePair("address", address));
        parametros.add(new BasicNameValuePair("latitude", latitud));
        parametros.add(new BasicNameValuePair("longitude", longitud));
        JSONObject json = jsonParser.getJSONFromUrl(webServicesURL, parametros);
        // return json
        Log.d("JSON", json.toString());
        return json;
    }

    public JSONObject recoverPass(String username){
        // Construimos los parametros
        List<NameValuePair> parametros = new ArrayList<NameValuePair>();
        parametros.add(new BasicNameValuePair("task", etiqueta_recPass));
        parametros.add(new BasicNameValuePair("username", username));
        JSONObject json = jsonParser.getJSONFromUrl(webServicesURL, parametros);
        // return json
        Log.d("JSON", json.toString());
        return json;
    }

    public JSONObject getProfile(String token){
        // Construimos los parametros
        List<NameValuePair> parametros = new ArrayList<NameValuePair>();
        parametros.add(new BasicNameValuePair("task", etiqueta_getP));
        parametros.add(new BasicNameValuePair("token", token));
        JSONObject json = jsonParser.getJSONFromUrl(webServicesURL, parametros);
        // return json
        Log.d("JSON", json.toString());
        return json;
    }

    public JSONObject getProductsToCat(String token, String catID){
        // Construimos los parametros
        List<NameValuePair> parametros = new ArrayList<NameValuePair>();
        parametros.add(new BasicNameValuePair("task", etiqueta_getPTC));
        parametros.add(new BasicNameValuePair("token", token));
        parametros.add(new BasicNameValuePair("categorys_id", catID));
        JSONObject json = jsonParser.getJSONFromUrl(webServicesURL, parametros);
        // return json
        Log.d("JSON", json.toString());
        return json;
    }

    public JSONObject getProduct(String token, String codP){
        // Construimos los parametros
        List<NameValuePair> parametros = new ArrayList<NameValuePair>();
        parametros.add(new BasicNameValuePair("task", etiqueta_getProduct));
        parametros.add(new BasicNameValuePair("token", token));
        parametros.add(new BasicNameValuePair("cod", codP));
        JSONObject json = jsonParser.getJSONFromUrl(webServicesURL, parametros);
        // return json
        Log.d("JSON", json.toString());
        return json;
    }


    public JSONObject addOrder(String token, String[] jsonOrder){
        // Construimos los parametros
        List<NameValuePair> parametros = new ArrayList<NameValuePair>();
        parametros.add(new BasicNameValuePair("task", etiqueta_addO));
        parametros.add(new BasicNameValuePair("token", token));
        parametros.add(new BasicNameValuePair("json", Arrays.toString(jsonOrder)));
        JSONObject json = jsonParser.getJSONFromUrl(webServicesURL, parametros);
        // return json
        Log.d("JSON", json.toString());
        return json;
    }


    public JSONObject getOrdersToCust(String token, String status){
        // Construimos los parametros
        List<NameValuePair> parametros = new ArrayList<NameValuePair>();
        parametros.add(new BasicNameValuePair("task", etiqueta_getO));
        parametros.add(new BasicNameValuePair("token", token));
        parametros.add(new BasicNameValuePair("status", status));
        JSONObject json = jsonParser.getJSONFromUrl(webServicesURL, parametros);
        // return json
        Log.d("JSON", json.toString());
        return json;
    }

    public JSONObject getOrdersToCustDetail(String token, String folio){
        // Construimos los parametros
        List<NameValuePair> parametros = new ArrayList<NameValuePair>();
        parametros.add(new BasicNameValuePair("task", etiqueta_getOTCD));
        parametros.add(new BasicNameValuePair("token", token));
        parametros.add(new BasicNameValuePair("folio_number", folio));
        JSONObject json = jsonParser.getJSONFromUrl(webServicesURL, parametros);
        // return json
        Log.d("JSON", json.toString());
        return json;
    }

}