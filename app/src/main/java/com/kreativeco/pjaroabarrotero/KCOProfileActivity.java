package com.kreativeco.pjaroabarrotero;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.kreativeco.pjaroabarrotero.libraries.Config;
import com.kreativeco.pjaroabarrotero.libraries.KCOASWS;
import com.kreativeco.pjaroabarrotero.libraries.KCOAsyncResponseG;

import org.json.JSONException;
import org.json.JSONObject;


public class KCOProfileActivity extends Activity {
    EditText shop,contact,address;
    RelativeLayout launchOrders;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcoprofile);
        shop = (EditText) findViewById(R.id.shop);
        contact = (EditText) findViewById(R.id.contact);
        address = (EditText) findViewById(R.id.address);
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(shop.getWindowToken(), 0);
        launchOrders = (RelativeLayout) findViewById(R.id.proof);

        pDialog = new ProgressDialog(KCOProfileActivity.this);
        pDialog.setMessage("Por favor espere..." );
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        getProfileWS();
    }

    public void getProfileWS(){
        SharedPreferences userProfile = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);
        pDialog.show();
        new KCOASWS(new KCOAsyncResponseG() {
            @Override
            public void processFinishG(JSONObject json) {
                if (json!=null && json.length() > 0){
                    try {

                        //Obtenemos del JSON los datos
                        String message = json.getString("message");
                        String status = json.getString("status");

                        JSONObject profile = json.getJSONObject("profile");
                        String shopJ = profile.getString("shop");
                        String contactJ = profile.getString("contact");
                        String addressJ = profile.getString("address");

                        shop.setText(shopJ);
                        contact.setText(contactJ);
                        address.setText(addressJ);

                        //Debug
                        Log.d("Login", "Message : " + message);
                        Log.d("Status", "Status : " + status);
                        pDialog.dismiss();
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Log.d("GETPROFILE","Estatus 0 Token No Valido");
                    pDialog.dismiss();
                    //METHOD ALERT
                }
            }
        }).execute(Config.WS_GET_PROFILE, userProfile.getString("Token", ""));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kcoprofile, menu);
        return true;
    }

    public void LaunchOrdersMethod(View v)
    {
        Intent launchActivity = new Intent(KCOProfileActivity.this, KCOOrdersActivity.class);
        startActivity(launchActivity);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
