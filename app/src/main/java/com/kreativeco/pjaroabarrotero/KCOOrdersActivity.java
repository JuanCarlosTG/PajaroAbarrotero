package com.kreativeco.pjaroabarrotero;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;


public class KCOOrdersActivity extends Activity {

    ImageView redBar, blueBar, yellowBar, greenBar;
    ListView orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcoorders);

        redBar = (ImageView) findViewById(R.id.red_bar2);
        blueBar = (ImageView) findViewById(R.id.blue_bar2);
        yellowBar = (ImageView) findViewById(R.id.yellow_bar2);
        greenBar = (ImageView) findViewById(R.id.green_bar2);
        this.orderList = (ListView) findViewById(R.id.list_orders);

        ArrayList<KCOListItems> listItems = getItems();

        KCOListAdapterToOrder customaAdapter = new KCOListAdapterToOrder(this, listItems);

        orderList.setAdapter(customaAdapter);

        orderList.setOnItemClickListener(new DrawerView());
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
                launchOrderDetails();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 1:
                launchOrderDetails();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 2:
                launchOrderDetails();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 3:
                launchOrderDetails();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 4:
                launchOrderDetails();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 5:
                launchOrderDetails();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 6:
                launchOrderDetails();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 7:
                launchOrderDetails();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 8:
                launchOrderDetails();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 9:
                launchOrderDetails();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 10:
                launchOrderDetails();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 11:
                launchOrderDetails();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;

            default:
                break;

        }
    }

    private ArrayList<KCOListItems>getItems(){

        ArrayList<KCOListItems> items = new ArrayList<KCOListItems>();

        items.add(new KCOListItems(1, "Pedido1", "drawable/_07icono_canasta"));
        items.add(new KCOListItems(2, "Pedido2", "drawable/_07icono_canasta"));
        items.add(new KCOListItems(3, "Pedido3", "drawable/_07icono_canasta"));
        items.add(new KCOListItems(4, "Pedido4", "drawable/_07icono_canasta"));
        items.add(new KCOListItems(5, "Pedido5", "drawable/_07icono_canasta"));
        items.add(new KCOListItems(6, "Pedido6", "drawable/_07icono_canasta"));
        items.add(new KCOListItems(7, "Pedido7", "drawable/_07icono_canasta"));
        items.add(new KCOListItems(8, "Pedido8", "drawable/_07icono_canasta"));
        items.add(new KCOListItems(9, "Pedido9", "drawable/_07icono_canasta"));
        items.add(new KCOListItems(10, "Pedido10", "drawable/_07icono_canasta"));
        items.add(new KCOListItems(11, "Pedido11", "drawable/_07icono_canasta"));
        items.add(new KCOListItems(12, "Pedido12", "drawable/_07icono_canasta"));
        items.add(new KCOListItems(13, "Pedido13", "drawable/_07icono_canasta"));
        items.add(new KCOListItems(14, "Pedido14", "drawable/_07icono_canasta"));
        items.add(new KCOListItems(15, "Pedido15", "drawable/_07icono_canasta"));
        items.add(new KCOListItems(16, "Pedido16", "drawable/_07icono_canasta"));
        items.add(new KCOListItems(17, "Pedido17", "drawable/_07icono_canasta"));
        items.add(new KCOListItems(18, "Pedido18", "drawable/_07icono_canasta"));
        items.add(new KCOListItems(19, "Pedido19", "drawable/_07icono_canasta"));
        items.add(new KCOListItems(20, "Pedido20", "drawable/_07icono_canasta"));
        items.add(new KCOListItems(21, "Pedido21", "drawable/_07icono_canasta"));
        items.add(new KCOListItems(22, "Pedido22", "drawable/_07icono_canasta"));
        items.add(new KCOListItems(23, "Pedido23", "drawable/_07icono_canasta"));
        items.add(new KCOListItems(24, "Pedido24", "drawable/_07icono_canasta"));



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

    public void hideButtons(){
        redBar.setVisibility(View.INVISIBLE);
        blueBar.setVisibility(View.INVISIBLE);
        yellowBar.setVisibility(View.INVISIBLE);
        greenBar.setVisibility(View.INVISIBLE);
    }

    public void launchOutStanding(View v)
    {
        hideButtons();
        redBar.setVisibility(View.VISIBLE);
    }

    public void launchRegistered(View v)
    {
        hideButtons();
        blueBar.setVisibility(View.VISIBLE);
    }

    public void launchSend(View v)
    {
        hideButtons();
        yellowBar.setVisibility(View.VISIBLE);
    }

    public void launchDelivered(View v)
    {
        hideButtons();
        greenBar.setVisibility(View.VISIBLE);
    }

    public void launchOrderDetails()
    {
        Intent launchActivity = new Intent(KCOOrdersActivity.this, KCOOrderDetailsActivity.class);
        startActivity(launchActivity);
    }
}
