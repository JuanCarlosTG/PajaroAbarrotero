package com.kreativeco.pjaroabarrotero;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kreativeco.pjaroabarrotero.KCODatabase.KCOConnectionDataBase;
import com.kreativeco.pjaroabarrotero.libraries.Config;
import com.kreativeco.pjaroabarrotero.libraries.KCOASWS;
import com.kreativeco.pjaroabarrotero.libraries.KCOAsyncResponseG;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kreativeco on 21/05/15.
 */
public class KCOOrdersOnRouteActivity extends Activity {

    public String codeCustomer;
    LinearLayout llProductsIntoCar;
    LayoutInflater inflater;
    Context thisClass = this;
    TextView tvHeader, tvTotal, tvNumber;
    private int intNumber = 0;
    private double dblTotal = 0.0;
    Button gone1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_on_route);

        llProductsIntoCar   = (LinearLayout) findViewById(R.id.ll_products_into_car);
        inflater            = getLayoutInflater();
        tvHeader            = (TextView) findViewById(R.id.tv_header);
        gone1               = (Button) findViewById(R.id.bt_info);
        tvNumber            = (TextView) findViewById(R.id.tv_number);
        tvTotal             = (TextView) findViewById(R.id.tv_total);

        tvHeader.setText("Carrito De Compra");
        gone1.setVisibility(View.INVISIBLE);

        showShoppingCar();
    }

    private void showShoppingCar(){

        KCOConnectionDataBase connectionDB = new KCOConnectionDataBase(thisClass);
        Cursor cursorInfo = connectionDB.getInformationFromBasket(connectionDB);

        if(cursorInfo.moveToFirst() ) {

            LayoutInflater inflater = LayoutInflater.from(this);

            do {

                RelativeLayout rl = (RelativeLayout) inflater.inflate(R.layout.ui_product_into_car, null);

                ImageView ivProduct             = (ImageView)   rl.findViewById(R.id.iv_product);
                final TextView tvProductName    = (TextView)    rl.findViewById(R.id.tv_product_name);
                final TextView tvCantity        = (TextView)    rl.findViewById(R.id.tv_cantity);
                Button btPlus                   = (Button)      rl.findViewById(R.id.bt_plus);
                Button btMinus                  = (Button)      rl.findViewById(R.id.bt_minus);
                TextView tvPrice                = (TextView)    rl.findViewById(R.id.tv_price);
                Button btDelete                 = (Button)      rl.findViewById(R.id.bt_delete);

                String name = cursorInfo.getString(0);
                tvProductName.setText(name);

                String img = cursorInfo.getString(1);
                Picasso.with(this).load(img).placeholder(R.drawable.ic_launcher).resize(100,100).into(ivProduct);

                String number = cursorInfo.getString(4);
                tvCantity.setText(number);
                intNumber = intNumber + Integer.parseInt(number);
                tvNumber.setText(Integer.toString(intNumber));

                String price = cursorInfo.getString(5);
                tvPrice.setText(price);
                dblTotal = dblTotal + Double.parseDouble(price);
                tvTotal.setText(Double.toString(dblTotal));

                btPlus.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view){
                        plusClicked(tvCantity);
                    }
                });

                btMinus.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view){
                        minusClicked(tvCantity);
                    }
                });

                btDelete.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view){
                        deleteClicked(tvProductName);
                    }
                });

                llProductsIntoCar.addView(rl);

                String total = cursorInfo.getString(4);

                String values = "name: " + name + " img: " + img + " price: " + price + " number: " + number + " total: " + total;
                Log.i("Query ", values);

            } while (cursorInfo.moveToNext());

//            totalCost.setText(totalFinal + "");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kcoorders, menu);
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


    public void getInfoFromDataBase(View v){

        KCOConnectionDataBase connectionDB = new KCOConnectionDataBase(thisClass);
        Cursor cursorInfo = connectionDB.getInformationFromBasket(connectionDB);

        JSONObject finalJSON = new JSONObject();
        JSONArray jsonArrayQueries = new JSONArray();

        getCodeCustomer();
        SharedPreferences customer = getSharedPreferences("codeClie", Context.MODE_PRIVATE );
        codeCustomer = customer.getString("Code", "");
        Log.v("CODE-CLIE", codeCustomer);

        double totalFinal = 0.0;
        String auxtotal;
        if(cursorInfo.moveToFirst()){
            do{

                String name = cursorInfo.getString(0);
                String img = cursorInfo.getString(1);
                String codeProduct = cursorInfo.getString(2);
                String price = cursorInfo.getString(3);
                String number = cursorInfo.getString(4);
                String total = cursorInfo.getString(5);

                String values = "name: " + name + " img: " + img + "code" +codeProduct + " price: " + price + " number: " + number + " total: " +total;
                Log.v("Query ", values);

                totalFinal += Double.parseDouble(total);

                JSONObject jsonQuery = new JSONObject();
                try {
                    jsonQuery.put("code", codeProduct);
                    jsonQuery.put("count", number);
                    jsonQuery.put("price", price);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jsonArrayQueries.put(jsonQuery);

            }while(cursorInfo.moveToNext());

            try {
                auxtotal = Double.toString(totalFinal);
                Log.v("VALOOOOOOOOOOR", auxtotal);
                finalJSON.put("code_customer", codeCustomer);
                finalJSON.put("order", jsonArrayQueries);
                finalJSON.put("total", auxtotal );

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.v("JSON QUERYyyyyy", finalJSON.toString());

            String opt="5";

            SharedPreferences userProfile = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);
            new KCOASWS(new KCOAsyncResponseG() {
                @Override
                public void processFinishG(JSONObject json) {
                    if (json!=null && json.length() > 0){
                        try {
                            //Obtenemos del JSON los datos y hacemos las respectivas validaciones para avisarle al usuario los resultados del servidor

                            String message = json.getString("message");
                            String status = json.getString("status");
                            //Debug
                            Log.d("ORDER", "Message : " + message);
                            Log.d("ORDER", "Status : " + status);

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Log.d("ORDER SEND","Orden rechazada");
                        //METHOD ALERT
                    }
                }
            }).execute(opt, userProfile.getString("Token",""), finalJSON.toString());

            connectionDB.deleteInformationrFromBasket(connectionDB);
            llProductsIntoCar.removeAllViews();
            Toast.makeText(getBaseContext(), "Pedido enviado exitosamente", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getBaseContext(), "No hay pedidos que enviar", Toast.LENGTH_LONG).show();
        }

    }

    public void launchMainDrawer()
    {
        Intent launchActivity = new Intent(KCOOrdersOnRouteActivity.this, KCOMainDrawerActivity.class);
        startActivity(launchActivity);
    }

    public void getCodeCustomer(){
        SharedPreferences userProfile = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);
        new KCOASWS(new KCOAsyncResponseG() {
            @Override
            public void processFinishG(JSONObject json) {
                if (json!=null && json.length() > 0){
                    try {

                        JSONObject profile = json.getJSONObject("profile");

                        SharedPreferences codeClie = getSharedPreferences("codeClie", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = codeClie.edit();

                        //Obtenemos del JSON los datos
                        codeCustomer = profile.getString("code_customer");

                        //Almacenamos los datos del JSON en la estructura
                        editor.putString("Code", codeCustomer);
                        editor.commit();

                        Log.v("Cliente", codeCustomer);
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Log.d("GETPROFILE","Estatus 0 Token No Valido");
                }
            }
        }).execute(Config.WS_GET_PROFILE, userProfile.getString("Token", ""));
    }

    private void plusClicked(TextView textView){
        int products = Integer.parseInt(textView.getText().toString());
        products++;
        textView.setText(Integer.toString(products));
        //Toast.makeText(this,"TagCode: " + products+"" , Toast.LENGTH_LONG).show();
    }

    private void minusClicked(TextView textView){
        int products = Integer.parseInt(textView.getText().toString());
        if(products<= 0) return;
        products--;
        textView.setText(Integer.toString(products));
        //Toast.makeText(this,"TagCode: " + products+"" , Toast.LENGTH_LONG).show();
    }

    public void launchShoppingCar(View view){
        Intent i = new Intent(KCOOrdersOnRouteActivity.this, KCOMainDrawerActivity.class);
        startActivity(i);
        finish();
    }

    public void deleteClicked(TextView tvProductName) {

        String strName = tvProductName.getText().toString();
        KCOConnectionDataBase connectionDataBase = new KCOConnectionDataBase(thisClass);

        connectionDataBase.deleteItem(connectionDataBase, strName);
        Toast.makeText(getBaseContext(), "Producto Agregado Al Carrito", Toast.LENGTH_LONG).show();

        llProductsIntoCar.removeAllViews();
        showShoppingCar();
    }
}
