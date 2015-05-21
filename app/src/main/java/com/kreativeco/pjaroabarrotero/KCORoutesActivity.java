package com.kreativeco.pjaroabarrotero;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kreativeco.pjaroabarrotero.libraries.KCOASGetOrdersToOperator;
import com.kreativeco.pjaroabarrotero.libraries.KCOAsyncResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kreativeco on 21/05/15.
 */
public class KCORoutesActivity extends Activity {

    private static final float alpha = 0.7f;
    LayoutInflater inflater;
    ImageButton gone1;
    Button gone2, gone3, btSended, btReceived;
    TextView tvHeader;
    LinearLayout llRoutes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        inflater    = getLayoutInflater();
        tvHeader    = (TextView) findViewById(R.id.tv_header);
        gone1       = (ImageButton) findViewById(R.id.menu_button_catalogue);
        gone2       = (Button) findViewById(R.id.bt_info);
        gone3       = (Button) findViewById(R.id.bt_car);
        llRoutes    = (LinearLayout) findViewById(R.id.ll_routes);
        btSended    = (Button) findViewById(R.id.bt_on_route);
        btReceived  = (Button) findViewById(R.id.bt_received);

        tvHeader.setText("Hitorial de Pedidos");
        gone1.setVisibility(View.INVISIBLE);
        gone2.setVisibility(View.INVISIBLE);
        gone3.setVisibility(View.INVISIBLE);

    }

    public void clickCategry(View v){

        int i = Integer.parseInt(v.getTag().toString());
        if(i==1){
            listProducts("sended");
            btReceived.setAlpha(alpha);
            btSended.setAlpha(1);
        }else if(i==2){
            listProducts("received");
            btReceived.setAlpha(1);
            btSended.setAlpha(alpha);
        }
    }

    public void listProducts(String strStatus){


        SharedPreferences userProfile = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);

        new KCOASGetOrdersToOperator(new KCOAsyncResponse() {
            @Override
            public void processFinish(ArrayList<HashMap<String, String>> output)
            {

                setRoutes(output);
                //Log.i("Tamano", output + "");

            }
        }).execute("5f29b26e4d4044778c0d2806a994b3cbb127a36f:GmAVbZe6", "tuesday", strStatus);
           //execute(userProfile.getString("Token", ""), "tuesday", "received");

    }

    private void setRoutes(ArrayList<HashMap<String, String>> output)
    {
        llRoutes.removeAllViews();

        if(output!=null) {

            inflater = LayoutInflater.from(this);

            for(Map<String, String> map : output)
            {
                LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.customerresult, null);

                ImageView ivShop             = (ImageView)   ll.findViewById(R.id.iv_shop);
                final TextView tvShopName    = (TextView)    ll.findViewById(R.id.tv_shop_name);
                final TextView tvShopAddress = (TextView)    ll.findViewById(R.id.tv_shop_address);

                String strFolio = map.get("folio_number");
                String strShop  = map.get("shop");
                String strCode  = map.get("code_customer");

                tvShopName.setText(strShop);
                tvShopAddress.setText("CLIENTE: " + strCode + " Folio del pedido: " +strFolio);

                String img      = "imagen";
                Picasso.with(this).load(img).placeholder(R.drawable.ic_launcher).resize(100,100).into(ivShop);

                ll.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view){
                        goToProducts();
                    }
                });
                llRoutes.addView(ll);

                Log.i("Datos del WS", strShop + " " + strCode +" " + strFolio);
            }

        }else{
            Log.d("Products To Category","Status 0");
        }
    }

    public void goToProducts(){
        Intent i = new Intent(KCORoutesActivity.this, KCOOrdersOnRouteActivity.class);
        startActivity(i);
    }
}
