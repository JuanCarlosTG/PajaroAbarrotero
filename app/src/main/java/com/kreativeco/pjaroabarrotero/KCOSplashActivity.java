package com.kreativeco.pjaroabarrotero;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class KCOSplashActivity extends Activity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcosplash);

        Thread background = new Thread() {
            public void run() {

                try {
                    sleep(3*1000);

                    SharedPreferences userProfile = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);

                    if(userProfile.contains("Token")){
                        int userType = Integer.parseInt(userProfile.getString("User_type", ""));
                        Log.i("USER FUCK", userType + "");

                        if(userType == 2){
                            Intent launchActivity = new Intent(KCOSplashActivity.this, KCOSearchShopsActivity.class);
                            startActivity(launchActivity);
                            finish();

                        }else if(userType == 3){
                            Intent launchActivity = new Intent(KCOSplashActivity.this, KCOMainDrawerActivity.class);
                            startActivity(launchActivity);
                            finish();

                        }else if(userType == 4){
                            Intent launchActivity = new Intent(KCOSplashActivity.this, KCORoutesActivity.class);
                            startActivity(launchActivity);
                            finish();
                        }

                    } else {
                        Intent i=new Intent(getBaseContext(), KCOLoginActivity.class);
                        startActivity(i);
                        finish();
                    }

                } catch (Exception e) {
                    Log.e("EXception thread", e.toString());
                }
            }
        };
        background.start();
    }



    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kcosplash, menu);
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

    protected void onDestroy()
    {
        super.onDestroy();
    }
}
