package com.kreativeco.pjaroabarrotero;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import com.kreativeco.pjaroabarrotero.KCODatabase.KCOConnectionDataBase;

public class KCOProductDetailsActivity extends Activity {

    RelativeLayout layoutPiker;
    EditText numberProducts;
    ImageButton addProductToBasket;
    Context thisClass = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcoproduct_details);

        layoutPiker = (RelativeLayout) findViewById(R.id.layout_piker);
        numberProducts = (EditText) findViewById(R.id.number_products);
        addProductToBasket = (ImageButton) findViewById(R.id.add_product);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(numberProducts.getWindowToken(), 0);

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

    public void addProducts(View v)
    {
        String numProds = numberProducts.getText().toString();
        KCOConnectionDataBase connectionDataBase = new KCOConnectionDataBase(thisClass);
        connectionDataBase.insertInformation(connectionDataBase, "producto X", "img.jpg", "00001", "1.5", numProds, "6");
        Toast.makeText(getBaseContext(), "Producto Agregado Al Carrito", Toast.LENGTH_LONG).show();
        onBackPressed();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(KCOProductDetailsActivity.this, KCOOrdersActivity.class ));
    }
}
