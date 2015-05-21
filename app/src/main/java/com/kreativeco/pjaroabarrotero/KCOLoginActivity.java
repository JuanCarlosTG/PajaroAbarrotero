package com.kreativeco.pjaroabarrotero;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kreativeco.pjaroabarrotero.libraries.Config;
import com.kreativeco.pjaroabarrotero.libraries.KCOASWS;
import com.kreativeco.pjaroabarrotero.libraries.KCOAsyncResponseG;

import org.json.JSONException;
import org.json.JSONObject;


public class KCOLoginActivity extends Activity {
    private AlertDialog dialogReset;
    private View alertViewReset;
    private String newPass="";
    EditText user,pass;

    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcologin);

        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.password);

        pDialog = new ProgressDialog(KCOLoginActivity.this);
        pDialog.setMessage("Por favor espere..." );
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        createDialogResetPass();
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
        pDialog.show();

        new KCOASWS(new KCOAsyncResponseG() {
            @Override
            public void processFinishG(JSONObject json) {
              if (json!=null && json.length() > 0){
                try {
                    String status = json.getString("status");
                    if(Integer.parseInt(status)==1){
                        //Se crea la estructura que contendrá toda la información del token
                        SharedPreferences tokenUser = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = tokenUser.edit();

                        //Obtenemos del JSON los datos
                        String message = json.getString("message");
                        String token = json.getString("token");

                        //Almacenamos los datos del JSON en la estructura
                        editor.putString("Message", message);
                        editor.putString("Token", token);
                        editor.putString("User_type", json.getString("user_type"));
                        editor.commit();

                        //Debug
                        Log.d("Login", "Message : " + message);
                        Log.d("Token", "Token : " + token);
                        pDialog.dismiss();

                        int userType = Integer.parseInt(json.getString("user_type"));
                        Log.i("FUUCCKK", userType+"");

                        if(userType == 2){
                            Intent launchActivity = new Intent(KCOLoginActivity.this, KCOSearchShopsActivity.class);
                            startActivity(launchActivity);
                            finish();

                        }else if(userType == 3){
                            Intent launchActivity = new Intent(KCOLoginActivity.this, KCOMainDrawerActivity.class);
                            startActivity(launchActivity);
                            finish();

                        }else if(userType == 4){
                            Intent launchActivity = new Intent(KCOLoginActivity.this, KCORoutesActivity.class);
                            startActivity(launchActivity);
                            finish();
                        }

                        Log.d("Login","Complete Process To Login");
                    }else if(Integer.parseInt(status)==0){
                        Log.d("Login","Status 0");
                        pDialog.dismiss();
                        createMessageLoginNoOk();
                    } else if(Integer.parseInt(status)==-1){
                        Log.d("Login","Status -1");
                        pDialog.dismiss();
                        createMessageServiceNoOk();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                    createMessageServiceNoOk();
                }
            }else{
                Log.d("LOGIN","Estatus -1 Conexión Interrumpida");
                pDialog.dismiss();
                createMessageServiceNoOk();
            }
           }
        }).execute(Config.WS_LOGIN, user.getText().toString(), pass.getText().toString());

    }

    public void launchRegisterActivity(View v)
    {
        Intent launchActivity = new Intent(KCOLoginActivity.this, KCORegisterActivity.class);
        startActivity(launchActivity);
    }

    public void launchResetPassword(View v){
        dialogReset.show();
    }

    private void createDialogResetPass(){
        AlertDialog.Builder builder = new AlertDialog.Builder(KCOLoginActivity.this);
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        alertViewReset =inflater.inflate(R.layout.dialog_kco_reset_password,null);
        builder.setView(alertViewReset);

        TextView title = new TextView(this);
        title.setText(R.string.title_reset_password);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(18);

        builder.setCustomTitle(title);

        builder.setPositiveButton(R.string.send, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText t_email = (EditText) alertViewReset.findViewById(R.id.emailReset);
                final String email = t_email.getText().toString();

                if(checkEmailReset(email)){

                    pDialog.show();
                    new KCOASWS(new KCOAsyncResponseG() {
                        @Override
                        public void processFinishG(JSONObject json) {
                            if (json!=null && json.length() > 0){
                                try {
                                    String status = json.getString("status");
                                    if(Integer.parseInt(status)==1){
                                        //Obtenemos del JSON los datos
                                        String message = json.getString("message");
                                        newPass = json.getString("password");

                                        //Debug
                                        Log.d("RESET PASS", "Message : " + message);
                                        Log.d("RESET PASS", "PASSWD : " + newPass);

                                        pDialog.dismiss();
                                        createMessageNewPass(email);
                                    }else{
                                        Log.d("RESET PASS","Status 0");
                                        pDialog.dismiss();
                                        createMessageServiceNoOk();
                                    }
                                }catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                Log.d("RESET PASS","Estatus: Falla de Conexión a Internet");
                                pDialog.dismiss();
                                createMessageServiceNoOk();
                            }
                        }
                    }).execute(Config.WS_RECOVER_PASS, email);
                }else{
                    err_resetEmail();
                }
            }
        });

        builder.setNegativeButton(R.string.cancel,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                dialogReset.dismiss();
            }
        });

        dialogReset = builder.create();
    }

    public void err_resetEmail(){
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    public boolean checkEmailReset(String emailUser ){
        if 	(emailUser.equals("") ){
            Log.e("Reset UI", "Campo de username vacío");
            return false;
        }else{
            return true;
        }
    }

    private void createMessageNewPass(final String username){
        AlertDialog.Builder builder = new AlertDialog.Builder(KCOLoginActivity.this);
        builder.setMessage("Tu nuevo password es: "+newPass+"  Guardalo en un lugar seguro.").setTitle(R.string.welcome);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                user.setText(username);
                pass.setText(newPass);
            }
        });

        AlertDialog dialogResetOk = builder.create();
        dialogResetOk.show();
    }

    private void createMessageServiceNoOk(){
        AlertDialog.Builder builder = new AlertDialog.Builder(KCOLoginActivity.this);
        builder.setMessage(R.string.message_serviceNoOk).setTitle(R.string.title_serviceNoOK);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialogResetOk = builder.create();
        dialogResetOk.show();
    }

    private void createMessageLoginNoOk(){
        AlertDialog.Builder builder = new AlertDialog.Builder(KCOLoginActivity.this);
        builder.setMessage(R.string.message_loginNoOk).setTitle(R.string.title_loginNoOK);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialogResetOk = builder.create();
        dialogResetOk.show();
    }

}