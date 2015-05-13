package com.kreativeco.pjaroabarrotero;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kreativeco.pjaroabarrotero.KCODatabase.KCOConnectionDataBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kreativeco.pjaroabarrotero.libraries.Config;
import com.kreativeco.pjaroabarrotero.libraries.KCOASWS;
import com.kreativeco.pjaroabarrotero.libraries.KCOAsyncResponseG;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class KCOOrdersActivity extends Activity {

    public DrawerLayout leftDrawerOrders;
    private ListView leftDrawerList;
    TextView totalCost;
    Context thisClass = this;
    public String codeCustomer;
    LinearLayout llProductsIntoCar;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcoorders);

        //this.totalCost = (TextView) findViewById(R.id.total_cost);

        leftDrawerOrders = (DrawerLayout) findViewById(R.id.draweLayoutOrders);

        this.leftDrawerList = (ListView) findViewById(R.id.left_drawer_orders);
        ArrayList<KCOListItems> listItemsDrawer = getItemsDrawer();
        KCOListItemsAdapter customAdapterDrawer = new KCOListItemsAdapter(this, listItemsDrawer);
        leftDrawerList.setAdapter(customAdapterDrawer);
        leftDrawerList.setOnItemClickListener(new LeftDrawerView());


        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        llProductsIntoCar = (LinearLayout) findViewById(R.id.ll_products_into_car);
        inflater = getLayoutInflater();

        showShoppingCar();
    }


    private class LeftDrawerView implements  ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            selectItemDrawer(position);
        }
    }

    private void selectItemDrawer(int position){
        switch (position){
            case 0:
                launchMainDrawer();
                finish();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 1:
                leftDrawerOrders.closeDrawers();
                break;
            case 2:
                launchMainDrawer();
                finish();
                break;
            case 3:
                launchMainDrawer();
                finish();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 4:
                launchMainDrawer();
                finish();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 5:
                launchMainDrawer();
                finish();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 6:
                launchMainDrawer();
                finish();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 7:
                leftDrawerOrders.closeDrawers();
                break;
            case 8:
                leftDrawerOrders.closeDrawers();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            /*case 9:
                leftDrawerOrders.closeDrawers();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 10:
                leftDrawerOrders.closeDrawers();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 11:
                leftDrawerOrders.closeDrawers();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;*/

            default:
                break;

        }
    }

    private void selectItem(int position){
        switch (position){
            case 0:
                launchMainDrawer();
                finish();
            default:
                launchMainDrawer();
                finish();
                break;

        }
    }

    private ArrayList<KCOListItems>getItemsDrawer(){

        ArrayList<KCOListItems> items = new ArrayList<>();

        items.add(new KCOListItems(1, "MENÚ", "drawable/_05backtitle"));
        items.add(new KCOListItems(2, "Mi Perfil", "drawable/_05backtitle"));
        items.add(new KCOListItems(3, "Catálogo", "drawable/_05backtitle"));
        items.add(new KCOListItems(4, "drawable/_05_1cuidado"));
        items.add(new KCOListItems(5, "drawable/_05_2hogar"));
        items.add(new KCOListItems(6, "drawable/_05_3alimentos"));
        items.add(new KCOListItems(7, "drawable/_05_4otros"));
        items.add(new KCOListItems(8, "Pedidos", "drawable/_05backtitle"));
        //items.add(new KCOListItems(9, "drawable/_05_5pendientes"));
        //items.add(new KCOListItems(10, "drawable/_05_6registrados"));
        //items.add(new KCOListItems(11, "drawable/_05_7enviados"));
        //items.add(new KCOListItems(12, "drawable/_05_8entregados"));

        return items;
    }

    private void showShoppingCar(){
        KCOConnectionDataBase connectionDB = new KCOConnectionDataBase(thisClass);
        Cursor cursorInfo = connectionDB.getInformationFromBasket(connectionDB);

        if(cursorInfo.moveToFirst() ) {

            LayoutInflater inflater = LayoutInflater.from(this);

            do {

                RelativeLayout rl = (RelativeLayout) inflater.inflate(R.layout.ui_product_into_car, null);

                ImageView ivProduct         = (ImageView) rl.findViewById(R.id.iv_product);
                TextView tvProductName      = (TextView) rl.findViewById(R.id.tv_product_name);
                final TextView tvCantity    = (TextView) rl.findViewById(R.id.tv_cantity);
                Button btPlus               = (Button) rl.findViewById(R.id.bt_plus);
                Button btMinus              = (Button) rl.findViewById(R.id.bt_minus);
                TextView tvPrice            = (TextView) rl.findViewById(R.id.tv_price);

                String name = cursorInfo.getString(0);
                tvProductName.setText(name);

                String img = cursorInfo.getString(1);
                Picasso.with(this).load(img).placeholder(R.drawable.ic_launcher).resize(100,100).into(ivProduct);

                String number = cursorInfo.getString(4);
                tvCantity.setText(number);

                String price = cursorInfo.getString(5);
                tvPrice.setText(price);

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

                llProductsIntoCar.addView(rl);
                
                String total = cursorInfo.getString(4);

                String values = "name: " + name + " img: " + img + " price: " + price + " number: " + number + " total: " + total;
                Log.i("Query ", values);

            } while (cursorInfo.moveToNext());

//            totalCost.setText(totalFinal + "");
        }

    }

    /*private ArrayList<KCOListItems>getItems(){

        ArrayList<KCOListItems> items = new ArrayList<>();
        int i = 1;
        double totalFinal = 0.0;

        items.add(new KCOListItems(i, "Pedido Pendiente", "drawable/_07icono_canasta"));
        i++;

        KCOConnectionDataBase connectionDB = new KCOConnectionDataBase(thisClass);
        Cursor cursorInfo = connectionDB.getInformationFromBasket(connectionDB);

        if(cursorInfo.moveToFirst() ) {
                do {
                    String name = cursorInfo.getString(0);
                    String img = cursorInfo.getString(1);
                    String price = cursorInfo.getString(2);
                    String number = cursorInfo.getString(3);
                    String total = cursorInfo.getString(4);

                    String values = "name: " + name + " img: " + img + " price: " + price + " number: " + number + " total: " + total;
                    Log.i("Query ", values);

                    items.add(new KCOListItems(i, number + "\t" + name + " Costo Total p/p: $" + total, img));
                    i++;
                    totalFinal += Double.parseDouble(total);

                } while (cursorInfo.moveToNext());

                totalCost.setText(totalFinal + "");
        }
        //items.add(new KCOListItems(i, "Total: $" +totalFinal , "drawable/order02"));
        return items;
    }*/

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
        //cursorInfo.moveToFirst();

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
        }else{
            Toast.makeText(getBaseContext(), "No hay pedidos que enviar", Toast.LENGTH_LONG).show();
        }

    }

    /*public void launchProductDetails()
    {
        Intent launchActivity = new Intent(KCOOrdersActivity.this, KCOProductDetailsActivity.class);
        startActivity(launchActivity);
        finish();
    }*/

    public void launchMainDrawer()
    {
        Intent launchActivity = new Intent(KCOOrdersActivity.this, KCOMainDrawerActivity.class);
        startActivity(launchActivity);
    }

    public void openDrawer(View v)
    {
        leftDrawerOrders.openDrawer(leftDrawerList);
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

    /*public void setListOrders(final ListView list, String orderStatus){

        SharedPreferences userProfile = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);
        new KCOASOrdersToCustomer(new KCOAsyncResponse() {
            @Override
            public void processFinish(ArrayList<HashMap<String, String>> output) {
                ArrayList<KCOListItems> items = new ArrayList<>();
                for(Map<String, String> map : output){
                    String tagID = map.get("id");
                    Log.d("Values Received ID",tagID);
                    String tagCode = map.get("code_customer");
                    Log.d("Values Received FOLIO",tagCode);
                    String tagFolio = map.get("folio_number");
                    String tagDate = map.get("date");
                    items.add(new KCOListItems(1,"FOLIO:\t" + tagFolio + "\t\t FECHA:\t" + tagDate, "drawable/_07icono_canasta"));
                }
                KCOListAdapterToOrder customAdapter = new KCOListAdapterToOrder(KCOOrdersActivity.this, items);
                list.setAdapter(customAdapter);
                //list.setOnItemClickListener(new DrawerView());

            }
        }).execute(userProfile.getString("Token", ""), orderStatus);
    }*/

    private void plusClicked(TextView textView){
        int products = Integer.parseInt(textView.getText().toString());
        products++;
        textView.setText(Integer.toString(products));
        Toast.makeText(this,"TagCode: " + products+"" , Toast.LENGTH_LONG).show();
    }

    private void minusClicked(TextView textView){
        int products = Integer.parseInt(textView.getText().toString());
        if(products<= 0) return;
        products--;
        textView.setText(Integer.toString(products));
        Toast.makeText(this,"TagCode: " + products+"" , Toast.LENGTH_LONG).show();
    }
}