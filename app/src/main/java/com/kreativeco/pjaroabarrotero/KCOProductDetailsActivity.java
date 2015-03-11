package com.kreativeco.pjaroabarrotero;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.view.ViewGroup.LayoutParams;

public class KCOProductDetailsActivity extends Activity {

    RelativeLayout layoutPiker;
    NumberPicker customPiker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcoproduct_details);

        layoutPiker = (RelativeLayout) findViewById(R.id.layout_piker);

        customPiker = new NumberPicker(KCOProductDetailsActivity.this);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        customPiker.setLayoutParams(params);
        customPiker.setMinValue(0);
        customPiker.setMaxValue(30);
        customPiker.setWrapSelectorWheel(true);

        layoutPiker.addView(customPiker);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kcoproduct_details, menu);
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

}
