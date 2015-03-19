package com.kreativeco.pjaroabarrotero;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kreativeco.pjaroabarrotero.KCODatabase.KCOConnectionDataBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class KCOOrdersActivity extends Activity {

    public DrawerLayout leftDrawerOrders;
    ImageView redBar, blueBar, yellowBar, greenBar;
    private ListView leftDrawerList, committedOrderList, orderDetailsList, registeredOredersList, sendOrdersList;
    TextView totalCost, folio;
    Context thisClass = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcoorders);

        redBar = (ImageView) findViewById(R.id.red_bar2);
        blueBar = (ImageView) findViewById(R.id.blue_bar2);
        yellowBar = (ImageView) findViewById(R.id.yellow_bar2);
        greenBar = (ImageView) findViewById(R.id.green_bar2);

        leftDrawerOrders = (DrawerLayout) findViewById(R.id.draweLayoutOrders);

        this.leftDrawerList = (ListView) findViewById(R.id.left_drawer_orders);
        ArrayList<KCOListItems> listItemsDrawer = getItemsDrawer();
        KCOListItemsAdapter customAdapterDrawer = new KCOListItemsAdapter(this, listItemsDrawer);
        leftDrawerList.setAdapter(customAdapterDrawer);
        leftDrawerList.setOnItemClickListener(new LeftDrawerView());

        this.committedOrderList = (ListView) findViewById(R.id.list_committed_orders);

        this.orderDetailsList = (ListView) findViewById(R.id.list_orders_details);
        ArrayList<KCOListItems> listItems = getItems();
        KCOListAdapterToOrder customAdapter = new KCOListAdapterToOrder(this, listItems);
        orderDetailsList.setAdapter(customAdapter);
        orderDetailsList.setOnItemClickListener(new DrawerView());

        this.registeredOredersList = (ListView) findViewById(R.id.list_registered_orders);
        this.sendOrdersList = (ListView) findViewById(R.id.list_send_orders);

        this.totalCost = (TextView) findViewById(R.id.total_cost);
        this.folio = (TextView) findViewById(R.id.folio);

        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(orderDetailsList.getWindowToken(), 0);
    }

    private class DrawerView implements ListView.OnItemClickListener{
        @Override
        public void  onItemClick(AdapterView<?> parent, View view, int position, long id){
            selectItem(position);
        }
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
                launchOutStanding();
                leftDrawerOrders.closeDrawers();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 9:
                launchRegistered();
                leftDrawerOrders.closeDrawers();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 10:
                launchSend();
                leftDrawerOrders.closeDrawers();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 11:
                launchDelivered();
                leftDrawerOrders.closeDrawers();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;

            default:
                break;

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
        items.add(new KCOListItems(9, "drawable/_05_5pendientes"));
        items.add(new KCOListItems(10, "drawable/_05_6registrados"));
        items.add(new KCOListItems(11, "drawable/_05_7enviados"));
        items.add(new KCOListItems(12, "drawable/_05_8entregados"));

        return items;
    }

    private ArrayList<KCOListItems>getItems(){

        ArrayList<KCOListItems> items = new ArrayList<>();
        int i = 1;
        int totalFinal = 0;

        items.add(new KCOListItems(i, "Pedido Pendiente", "drawable/order02"));
        i++;

        KCOConnectionDataBase connectionDB = new KCOConnectionDataBase(thisClass);
        Cursor cursorInfo = connectionDB.getInformationFromBasket(connectionDB);
        cursorInfo.moveToFirst();

        do{

            String name = cursorInfo.getString(0);
            String img = cursorInfo.getString(1);
            String price = cursorInfo.getString(2);
            String number = cursorInfo.getString(3);
            String total = cursorInfo.getString(4);

            String values = "name: " + name + " img: " + img + " price: " + price + " number: " + number + " total: " +total;
            Log.i("Query ", values);

            items.add(new KCOListItems(i, number + "\t" + name + " Costo Total p/p: $" + total, "drawable/order01"));
            i++;
            totalFinal += Integer.parseInt(total);

        }while(cursorInfo.moveToNext());

        items.add(new KCOListItems(i, "Total: $" +totalFinal , "drawable/order02"));

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
        //sendOrderBtn.setVisibility(View.INVISIBLE);

    }

    public void launchOutStanding(View v)
    {

        hideButtons();
        redBar.setVisibility(View.VISIBLE);
        orderDetailsList.setVisibility(View.VISIBLE);
        totalCost.setVisibility(View.VISIBLE);
        folio.setVisibility(View.VISIBLE);
        //sendOrderBtn.setVisibility(View.VISIBLE);

    }

    public void launchOutStanding()
    {

        hideButtons();
        redBar.setVisibility(View.VISIBLE);
        orderDetailsList.setVisibility(View.VISIBLE);
        totalCost.setVisibility(View.VISIBLE);
        folio.setVisibility(View.VISIBLE);
        //sendOrderBtn.setVisibility(View.VISIBLE);

    }

    public void launchRegistered(View v)
    {
        hideButtons();
        blueBar.setVisibility(View.VISIBLE);
        registeredOredersList.setVisibility(View.VISIBLE);
    }

    public void launchRegistered()
    {
        hideButtons();
        blueBar.setVisibility(View.VISIBLE);
        registeredOredersList.setVisibility(View.VISIBLE);
    }

    public void launchSend(View v)
    {
        hideButtons();
        yellowBar.setVisibility(View.VISIBLE);
        sendOrdersList.setVisibility(View.VISIBLE);
    }

    public void launchSend()
    {
        hideButtons();
        yellowBar.setVisibility(View.VISIBLE);
        sendOrdersList.setVisibility(View.VISIBLE);
    }

    public void launchDelivered(View v)
    {
        hideButtons();
        greenBar.setVisibility(View.VISIBLE);
        committedOrderList.setVisibility(View.VISIBLE);
    }

    public void launchDelivered()
    {
        hideButtons();
        greenBar.setVisibility(View.VISIBLE);
        committedOrderList.setVisibility(View.VISIBLE);
    }

    public void getInfoFromDataBase(View v){
        KCOConnectionDataBase connectionDB = new KCOConnectionDataBase(thisClass);
        Cursor cursorInfo = connectionDB.getInformationFromBasket(connectionDB);
        cursorInfo.moveToFirst();

        JSONObject finalJSON = new JSONObject();
        JSONArray jsonArrayQueries = new JSONArray();

        do{
            String name = cursorInfo.getString(0);
            String img = cursorInfo.getString(1);
            String price = cursorInfo.getString(2);
            String number = cursorInfo.getString(3);
            String total = cursorInfo.getString(4);

            String values = "name: " + name + " img: " + img + " price: " + price + " number: " + number + " total: " +total;
            Log.i("Query ", values);

            JSONObject jsonQuery = new JSONObject();
            try {
                jsonQuery.put("code", name);
                jsonQuery.put("count", number);
                jsonQuery.put("price", price);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsonArrayQueries.put(jsonQuery);

        }while(cursorInfo.moveToNext());

        try {
            finalJSON.put("code_customer", "CLI_0000");
            finalJSON.put("order", jsonArrayQueries);
            finalJSON.put("total", "20.20" );

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.v("JSON QUERY", finalJSON.toString());

    }

    public void launchProductDetails()
    {
        Intent launchActivity = new Intent(KCOOrdersActivity.this, KCOProductDetailsActivity.class);
        startActivity(launchActivity);
        finish();
    }

    public void launchMainDrawer()
    {
        Intent launchActivity = new Intent(KCOOrdersActivity.this, KCOMainDrawerActivity.class);
        startActivity(launchActivity);
    }

    public void openDrawer(View v)
    {
        leftDrawerOrders.openDrawer(leftDrawerList);
    }

}