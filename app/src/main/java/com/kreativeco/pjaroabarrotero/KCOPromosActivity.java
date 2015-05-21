package com.kreativeco.pjaroabarrotero;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by kreativeco on 21/05/15.
 */
public class KCOPromosActivity extends Activity {

    LayoutInflater inflater;
    ImageButton gone1;
    TextView tvHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promos);

        inflater    = getLayoutInflater();
        tvHeader    = (TextView) findViewById(R.id.tv_header);
        gone1       = (ImageButton) findViewById(R.id.menu_button_catalogue);

        tvHeader.setText("Promociones");
        gone1.setVisibility(View.INVISIBLE);

    }

    public void showInfo(View v) {
        PopupWindow popup = new PopupWindow(KCOPromosActivity.this);
        View layout = getLayoutInflater().inflate(R.layout.ui_popup_info, null);
        popup.setContentView(layout);

        // Set content width and height
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);

        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);

        // Show anchored to button
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAsDropDown(v);
    }

    public void launchShoppingCar(View view){
        Intent i = new Intent(KCOPromosActivity.this, KCOOrdersActivity.class);
        startActivity(i);
    }


}
