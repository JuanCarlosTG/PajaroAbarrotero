package com.kreativeco.pjaroabarrotero;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.kreativeco.pjaroabarrotero.libraries.KCOASWS;
import com.kreativeco.pjaroabarrotero.libraries.KCOAsyncResponseG;

import org.json.JSONException;
import org.json.JSONObject;


public class KCOLoginActivity extends Activity {
    EditText user,pass;
    String opt="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcologin);

        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.password);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kcologin, menu);
        return true;
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

    public void launchMainMenuActivity(View v)
    {
        opt="0";
        String result="";
        new KCOASWS(new KCOAsyncResponseG() {
            @Override
            public void processFinishG(JSONObject json) {
              if (json!=null && json.length() > 0){
                try {
                    //Se crea la estructura que contendrá toda la información del token
                    SharedPreferences tokenUser = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = tokenUser.edit();

                    //Obtenemos del JSON los datos
                    String message = json.getString("message");
                    String token = json.getString("token");

                    //Almacenamos los datos del JSON en la estructura
                    editor.putString("Message", message);
                    editor.putString("Token", token);
                    editor.commit();

                    //Debug
                    Log.d("Login", "Message : " + message);
                    Log.d("Token", "Token : " + token);

                    Intent launchActivity = new Intent(KCOLoginActivity.this, KCOMainDrawerActivity.class);
                    startActivity(launchActivity);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Log.d("LOGIN","Estatus 0 no logeado");
                  //METHOD ALERT
            }
           }
        }).execute(opt, user.getText().toString(), pass.getText().toString());

    }

    public void launchRegisterActivity(View v)
    {
        Intent launchActivity = new Intent(KCOLoginActivity.this, KCORegisterActivity.class);
        startActivity(launchActivity);
    }
}