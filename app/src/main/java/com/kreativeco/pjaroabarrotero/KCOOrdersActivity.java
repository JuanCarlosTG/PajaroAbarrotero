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
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kreativeco.pjaroabarrotero.libraries.KCOASOrdersToCustomer;
import com.kreativeco.pjaroabarrotero.libraries.KCOAsyncResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class KCOOrdersActivity extends Activity {

    ImageView redBar, blueBar, yellowBar, greenBar;
    ListView committedOrderList, orderDetailsList, registeredOredersList, sendOrdersList;
    TextView totalCost, folio;
    ImageButton sendOrderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcoorders);

        redBar = (ImageView) findViewById(R.id.red_bar2);
        blueBar = (ImageView) findViewById(R.id.blue_bar2);
        yellowBar = (ImageView) findViewById(R.id.yellow_bar2);
        greenBar = (ImageView) findViewById(R.id.green_bar2);

        this.committedOrderList = (ListView) findViewById(R.id.list_committed_orders);
        this.orderDetailsList = (ListView) findViewById(R.id.list_orders_details);
        this.registeredOredersList = (ListView) findViewById(R.id.list_registered_orders);
        this.sendOrdersList = (ListView) findViewById(R.id.list_send_orders);

        this.totalCost = (TextView) findViewById(R.id.total_cost);
        this.folio = (TextView) findViewById(R.id.folio);

        this.sendOrderBtn = (ImageButton) findViewById(R.id.send_order_btn);

        ArrayList<KCOListItems> listItems = getItems();

        KCOListAdapterToOrder customAdapter = new KCOListAdapterToOrder(this, listItems);

        orderDetailsList.setAdapter(customAdapter);

        orderDetailsList.setOnItemClickListener(new DrawerView());

        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(orderDetailsList.getWindowToken(), 0);
    }

    private class DrawerView implements ListView.OnItemClickListener{
        @Override
        public void  onItemClick(AdapterView<?> parent, View view, int position, long id){
            selectItem(position);
        }
    }

    private void selectItem(int position){
        switch (position){
            case 0:
                launchProductDetails();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 1:
                launchProductDetails();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 2:
                launchProductDetails();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 3:
                launchProductDetails();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 4:
                launchProductDetails();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 5:
                launchProductDetails();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 6:
                launchProductDetails();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 7:
                launchProductDetails();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 8:
                launchProductDetails();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 9:
                launchProductDetails();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 10:
                launchProductDetails();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 11:
                launchProductDetails();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;

            default:
                break;

        }
    }

    private ArrayList<KCOListItems>getItems(){

        ArrayList<KCOListItems> items = new ArrayList<>();

        items.add(new KCOListItems(1, "Shampoo Z", "drawable/order01"));
        items.add(new KCOListItems(2, "Shampoo Y", "drawable/order02"));
        items.add(new KCOListItems(3, "Shampoo X", "drawable/order03"));
        items.add(new KCOListItems(4, "Shampoo W", "drawable/order04"));
        items.add(new KCOListItems(5, "Shampoo V", "drawable/order01"));
        items.add(new KCOListItems(6, "Shampoo T", "drawable/order02"));
        items.add(new KCOListItems(7, "Shampoo S", "drawable/order03"));
        items.add(new KCOListItems(8, "Shampoo R", "drawable/order04"));
        items.add(new KCOListItems(9, "Shampoo Q", "drawable/order01"));
        items.add(new KCOListItems(10, "Shampoo P", "drawable/order02"));
        items.add(new KCOListItems(11, "Shampoo N", "drawable/order03"));
        items.add(new KCOListItems(12, "Shampoo M", "drawable/order04"));
        items.add(new KCOListItems(13, "Shampoo L", "drawable/order01"));
        items.add(new KCOListItems(14, "Shampoo K", "drawable/order02"));
        items.add(new KCOListItems(15, "Shampoo J", "drawable/order03"));
        items.add(new KCOListItems(16, "Shampoo H", "drawable/order04"));
        items.add(new KCOListItems(17, "Shampoo G", "drawable/order01"));
        items.add(new KCOListItems(18, "Shampoo F", "drawable/order02"));
        items.add(new KCOListItems(19, "Shampoo D", "drawable/order03"));
        items.add(new KCOListItems(20, "Shampoo C", "drawable/order04"));


        return items;
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

    public void hideButtons()
    {

        redBar.setVisibility(View.INVISIBLE);
        blueBar.setVisibility(View.INVISIBLE);
        yellowBar.setVisibility(View.INVISIBLE);
        greenBar.setVisibility(View.INVISIBLE);
        committedOrderList.setVisibility(View.INVISIBLE);
        orderDetailsList.setVisibility(View.INVISIBLE);
        registeredOredersList.setVisibility(View.INVISIBLE);
        sendOrdersList.setVisibility(View.INVISIBLE);
        totalCost.setVisibility(View.INVISIBLE);
        folio.setVisibility(View.INVISIBLE);
        sendOrderBtn.setVisibility(View.INVISIBLE);

    }

    public void launchOutStanding(View v)
    {

        hideButtons();
        redBar.setVisibility(View.VISIBLE);
        orderDetailsList.setVisibility(View.VISIBLE);
        totalCost.setVisibility(View.VISIBLE);
        folio.setVisibility(View.VISIBLE);
        sendOrderBtn.setVisibility(View.VISIBLE);

    }

    public void launchRegistered(View v)
    {
        hideButtons();
        blueBar.setVisibility(View.VISIBLE);
        registeredOredersList.setVisibility(View.VISIBLE);

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
                    items.add(new KCOListItems(Long.parseLong(tagID),tagCode,"drawable/_07icono_canasta"));
                }

                KCOListAdapterToOrder customAdapter = new KCOListAdapterToOrder(KCOOrdersActivity.this, items);
                registeredOredersList.setAdapter(customAdapter);
                registeredOredersList.setOnItemClickListener(new DrawerView());

            }
        }).execute(userProfile.getString("Token", ""),"registered");

    }

    public void launchSend(View v)
    {
        hideButtons();
        yellowBar.setVisibility(View.VISIBLE);
        sendOrdersList.setVisibility(View.VISIBLE);

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
                    items.add(new KCOListItems(Long.parseLong(tagID),tagCode,"drawable/_07icono_canasta"));
                }

                KCOListAdapterToOrder customAdapter = new KCOListAdapterToOrder(KCOOrdersActivity.this, items);
                sendOrdersList.setAdapter(customAdapter);
                sendOrdersList.setOnItemClickListener(new DrawerView());

            }
        }).execute(userProfile.getString("Token", ""),"sended");
    }

    public void launchDelivered(View v)
    {
        hideButtons();
        greenBar.setVisibility(View.VISIBLE);
        committedOrderList.setVisibility(View.VISIBLE);

        SharedPreferences userProfile = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);
        new KCOASOrdersToCustomer(new KCOAsyncResponse() {
            @Override
            public void processFinish(ArrayList<HashMap<String, String>> output) {
                ArrayList<KCOListItems> items = new ArrayList<>();

                for(Map<String, String> map : output){
                    String tagName = map.get("id");
                    Log.d("Values Received ID",tagName);
                    String tagFolio = map.get("folio_number");
                    Log.d("Values Received FOLIO",tagFolio);
                    items.add(new KCOListItems(Long.parseLong(tagName),tagFolio,"drawable/_07icono_canasta"));
                }

                KCOListAdapterToOrder customAdapter = new KCOListAdapterToOrder(KCOOrdersActivity.this, items);
                committedOrderList.setAdapter(customAdapter);
                committedOrderList.setOnItemClickListener(new DrawerView());

            }
        }).execute(userProfile.getString("Token", ""),"received");


    }

    public void launchProductDetails()
    {
        Intent launchActivity = new Intent(KCOOrdersActivity.this, KCOProductDetailsActivity.class);
        startActivity(launchActivity);
    }

    /*public void launchOrderDetails()
    {
        Intent launchActivity = new Intent(KCOOrdersActivity.this, KCOOrderDetailsActivity.class);
        startActivity(launchActivity);
    }*/
}