package com.kreativeco.pjaroabarrotero.libraries;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class KCOASWS extends AsyncTask<String, Integer, JSONObject> {

    public KCOAsyncResponseG delegate = null;

    public KCOASWS(KCOAsyncResponseG delegate){
        this.delegate = delegate;
    }

    @Override
    protected JSONObject doInBackground(String...params) {
        JSONObject result=null;
        String status="-1";
        KCOUserFunctions userFun = new KCOUserFunctions();
        JSONObject json=null;
        switch (Integer.parseInt(params[0])){
            case 0:
                json = userFun.loginApp(params[1],params[2]);
                break;
            case 1:
                json = userFun.addProfile(params[1],params[2],params[3],params[4],params[5]);
                break;
            case 2:
                json = userFun.recoverPass(params[1]);
                break;
            case 3:
                json = userFun.getProfile(params[1]);
                break;
            case 4:
                json = userFun.getProduct(params[1],params[2]);
                break;
            case 5:
                //String[] param2=new String[]{params[2]};
                json = userFun.addOrder(params[1],params[2]);
                break;
            case 6:
                json = userFun.courtOrder(params[1],params[2]);
                break;
            default:
                //??
                result=null;
                break;
        }

        if (json!=null && json.length() > 0){
            try {
                status = json.getString("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (Integer.parseInt(status)==0){
                Log.d("status KCOASWS", "invalido");
                return result;
            }
            else if(Integer.parseInt(status)==1){
                Log.d("status ", "valido");
                return json;
            }
        }else{
            Log.e("JSON  ", "ERROR");
            return result;
        }

        return  result;

    }

    @Override
    protected void onPostExecute(JSONObject output) {
        delegate.processFinishG(output);
    }
}