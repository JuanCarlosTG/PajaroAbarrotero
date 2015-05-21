package com.kreativeco.pjaroabarrotero;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kreativeco.pjaroabarrotero.KCODatabase.KCOConnectionDataBase;
import com.kreativeco.pjaroabarrotero.libraries.Config;
import com.kreativeco.pjaroabarrotero.libraries.KCOASWS;
import com.kreativeco.pjaroabarrotero.libraries.KCOAsyncResponseG;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class KCOProductDetailsActivity extends Activity {


    TextView tvHeader, tvCost, tvProduct, tvProductMin, tvProductContent, tvBox, tvPrice, tvCantity;
    ImageView ivProduct;
    Button btPlus, btMinus;
    Context thisClass = this;
    String code;
    int numberTotalProducts;
    String id, cod, name, cap, ccaj, min, cunit_total, tagUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcoproduct_details);


        ivProduct           = (ImageView)   findViewById(R.id.iv_product);
        tvHeader            = (TextView)    findViewById(R.id.tv_header);
        tvCost              = (TextView)    findViewById(R.id.tv_cost);
        tvProduct           = (TextView)    findViewById(R.id.tv_product);
        tvProductMin        = (TextView)    findViewById(R.id.tv_product_min);
        tvProductContent    = (TextView)    findViewById(R.id.tv_product_content);
        tvBox               = (TextView)    findViewById(R.id.tv_box);
        tvPrice             = (TextView)    findViewById(R.id.tv_price);
        tvCantity           = (TextView)    findViewById(R.id.tv_cantity);

        btPlus              = (Button)      findViewById(R.id.bt_plus);
        btMinus             = (Button)      findViewById(R.id.bt_minus);

        tvHeader.setText("Producto");

        btPlus.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                plusClicked(tvCantity, tvCost, tvPrice);
            }
        });

        btMinus.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                minusClicked(tvCantity, tvCost, tvPrice);
            }
        });

        code = getIntent().getStringExtra("code");
        init(code);

    }


    public void addProducts(View v)
    {
        double products = Double.parseDouble(tvCantity.getText().toString());

        String numProds = tvCantity.getText().toString();
        String totalStr = tvPrice.getText().toString();
        String productMinStr = tvProductMin.getText().toString();
        String productNamestr = tvProduct.getText().toString();

        int aux = Integer.parseInt(productMinStr);

        if(!numProds.matches("")){

            numberTotalProducts = (int) products;

            if(numberTotalProducts >= aux){
                KCOConnectionDataBase connectionDataBase = new KCOConnectionDataBase(thisClass);
                connectionDataBase.insertInformation(connectionDataBase, productNamestr , tagUrl, cod, cunit_total, numProds, totalStr);
                //Toast.makeText(getBaseContext(), "Producto Agregado Al Carrito", Toast.LENGTH_LONG).show();
                startActivity(new Intent(KCOProductDetailsActivity.this, KCOOrdersActivity.class ));
                finish();
            }
            else{
                Toast.makeText(getBaseContext(),"Lo sentimos tu orden no fue procesada" +
                        " la cantidad debe ser mayor o igual a: "+
                        productMinStr,Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getBaseContext(),"Lo sentimos tu orden no fue procesada" +
                    " la cantidad debe ser mayor o igual a: "+
            productMinStr,Toast.LENGTH_LONG).show();
        }

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

                            //Obtenemos del JSON los datos
                            String message = json.getString("message");
                            JSONObject jsonProduct = json.getJSONObject("product");

                            cod = jsonProduct.getString("cod");
                            name = jsonProduct.getString("name");
                            cap = jsonProduct.getString("cap");
                            ccaj = jsonProduct.getString("ccaj");
                            min = jsonProduct.getString("min");
                            cunit_total = jsonProduct.getString("cunit_total");
                            tagUrl = jsonProduct.getString("file_image");

                            Picasso.with(thisClass).load(tagUrl).placeholder(R.drawable.ic_launcher).resize(200,200).into(ivProduct);
                            tvCost.setText(cunit_total);
                            tvProduct.setText(name);
                            tvProductMin.setText(min);
                            tvCantity.setText(min);
                            tvProductContent.setText(cap);
                            tvBox.setText(ccaj);


                        } else if (Integer.parseInt(status) == 0) {
                            Log.i("GetProduct", "Status 0");
                        } else if (Integer.parseInt(status) == -1) {
                            Log.i("GetProduct", "Status -1");
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

    private void plusClicked(TextView tvCantity, TextView tvCost, TextView tvPrice){
        double products = Double.parseDouble(tvCantity.getText().toString());
        double price    = Double.parseDouble(tvCost.getText().toString());
        products++;
        price = price * products;
        tvCantity.setText(roundTwoDecimals(products));
        tvPrice.setText(roundTwoDecimals(price));
    }

    private void minusClicked(TextView tvCantity, TextView tvCost, TextView tvPrice){
        double products = Double.parseDouble(tvCantity.getText().toString());
        double price    = Double.parseDouble(tvCost.getText().toString());

        if(products<= 0) return;

        products--;

        price = price * products;
        tvCantity.setText(roundTwoDecimals(products));
        tvPrice.setText(roundTwoDecimals(price));
    }

    String roundTwoDecimals(double d)
    {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d)).toString();
    }

    public void launchShoppingCar(View view){
        Intent i = new Intent(KCOProductDetailsActivity.this, KCOMainDrawerActivity.class);
        startActivity(i);
        finish();
    }
}
