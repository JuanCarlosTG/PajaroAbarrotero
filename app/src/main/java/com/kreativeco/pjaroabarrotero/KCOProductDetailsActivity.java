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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.kreativeco.pjaroabarrotero.KCODatabase.KCOConnectionDataBase;
import com.kreativeco.pjaroabarrotero.libraries.Config;
import com.kreativeco.pjaroabarrotero.libraries.KCOASWS;
import com.kreativeco.pjaroabarrotero.libraries.KCOAsyncResponseG;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class KCOProductDetailsActivity extends Activity {

    RelativeLayout layoutPiker;
    TextView productName, productCap, productBox, productMin, productCost;
    EditText numberProducts, totalCost;
    ImageView productImage;
    ImageButton addProductToBasket;
    Context thisClass = this;
    String code;
    Double total;
    int numberTotalProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcoproduct_details);

        layoutPiker = (RelativeLayout) findViewById(R.id.layout_piker);
        numberProducts = (EditText) findViewById(R.id.number_products);
        /*numberProducts.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String numProds = numberProducts.getText().toString();
                String totalStr = productCost.getText().toString();
                totalStr = totalStr.replace("Costo unitario: $","");
                numberTotalProducts = Integer.parseInt(numProds);
                total = Double.parseDouble(totalStr);
                total = total*numberTotalProducts;
                totalCost.setText("Costo Tolal: $" + total );
            }
        });*/

        totalCost = (EditText) findViewById(R.id.total_cost);
        addProductToBasket = (ImageButton) findViewById(R.id.add_product);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(numberProducts.getWindowToken(), 0);

        code = getIntent().getStringExtra("code");
        init(code);
        Toast.makeText(this,"TagCode en mi nueva vista: " + code , Toast.LENGTH_SHORT).show();
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
        //String totalStr = productCost.getText().toString();

        KCOConnectionDataBase connectionDataBase = new KCOConnectionDataBase(thisClass);
        connectionDataBase.insertInformation(connectionDataBase, "producto X", "img.jpg", "00001", "1.5", numProds, "6");
        Toast.makeText(getBaseContext(), "Producto Agregado Al Carrito", Toast.LENGTH_LONG).show();
        startActivity(new Intent(KCOProductDetailsActivity.this, KCOOrdersActivity.class ));
        finish();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(KCOProductDetailsActivity.this, KCOMainDrawerActivity.class ));
    }

    private void init(String code){

        SharedPreferences userProfile = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);
        new KCOASWS(new KCOAsyncResponseG() {
            @Override
            public void processFinishG(JSONObject json) {
                if (json != null && json.length() > 0) {
                    try {
                        String status = json.getString("status");
                        if (Integer.parseInt(status) == 1) {

                            productImage = (ImageView) findViewById(R.id.product_image);
                            productName = (TextView) findViewById(R.id.product_name);
                            productCap = (TextView) findViewById(R.id.product_cap);
                            productBox = (TextView) findViewById(R.id.product_box);
                            productMin = (TextView) findViewById(R.id.product_min);
                            productCost = (TextView) findViewById(R.id.product_cost);

                            //Obtenemos del JSON los datos
                            String message = json.getString("message");
                            JSONObject jsonProduct = json.getJSONObject("product");
                            String id = jsonProduct.getString("id");
                            String cod = jsonProduct.getString("cod");
                            String name = jsonProduct.getString("name");
                            String cap = jsonProduct.getString("cap");
                            String ccaj = jsonProduct.getString("ccaj");
                            String min = jsonProduct.getString("min");
                            String cunit_total = jsonProduct.getString("cunit_total");
                            String tagUrl = jsonProduct.getString("file_image");

                            Picasso.with(thisClass).load(tagUrl).placeholder(R.drawable.ic_launcher).resize(200,200).into(productImage);




                            productName.setText(name);
                            productCap.setText("Contenido del producto:" + getString(R.string.tab) + cap);
                            productBox.setText("Contenido por caja:" + getString(R.string.tab) + ccaj);
                            productMin.setText("Productos mínimos:" + getString(R.string.tab) + min);
                            productCost.setText(cunit_total);
                            numberProducts.setText(min);

                            numberTotalProducts =Integer.parseInt(min);
                            total = Double.parseDouble(cunit_total);
                            total = total*numberTotalProducts;

                            totalCost.setText("Costo Tolal: $" + total );
                            //productImage


                            //Debug
                            Log.v("GetProduct", "Message : " + message);
                            Log.v("GetProduct", "ID : " + id);
                            Log.v("GetProduct", "COD : " + cod);
                            Log.v("GetProduct", "NAME : " + name);
                            Log.v("GetProduct", "CAP : " + cap);

                            Log.d("GetProduct", "Complete Process");
                        } else if (Integer.parseInt(status) == 0) {
                            Log.d("GetProduct", "Status 0");
                        } else if (Integer.parseInt(status) == -1) {
                            Log.d("GetProduct", "Status -1");
                            //createMessageServiceNoOk();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //createMessageServiceNoOk();
                    }
                } else {
                    Log.d("LOGIN", "Estatus -1 Conexión Interrumpida");

                    //createMessageServiceNoOk();
                }
            }
        }).execute(Config.WS_GET_PRODUCT, userProfile.getString("Token", ""), code);
    }
}
