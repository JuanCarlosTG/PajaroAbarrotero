package com.kreativeco.pjaroabarrotero;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class KCOMainMenuActivity extends Activity {

    ImageView redBar, blueBar, yellowBar, greenBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcomain_menu);
        redBar = (ImageView) findViewById(R.id.red_bar);
        blueBar = (ImageView) findViewById(R.id.blue_bar);
        yellowBar = (ImageView) findViewById(R.id.yellow_bar);
        greenBar = (ImageView) findViewById(R.id.green_bar);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kcomain_menu, menu);
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

    public void hideButtons(){
        redBar.setVisibility(View.INVISIBLE);
        blueBar.setVisibility(View.INVISIBLE);
        yellowBar.setVisibility(View.INVISIBLE);
        greenBar.setVisibility(View.INVISIBLE);
    }

    public void launchPersonalCare(View v)
    {
        hideButtons();
        redBar.setVisibility(View.VISIBLE);
    }

    public void launchHome(View v)
    {
        hideButtons();
        blueBar.setVisibility(View.VISIBLE);
    }

    public void launchFoods(View v)
    {
        hideButtons();
        yellowBar.setVisibility(View.VISIBLE);
    }

    public void launchOthers(View v)
    {
        hideButtons();
        greenBar.setVisibility(View.VISIBLE);
    }
}
