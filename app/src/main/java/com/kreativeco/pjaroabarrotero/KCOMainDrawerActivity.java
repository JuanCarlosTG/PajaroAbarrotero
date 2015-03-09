package com.kreativeco.pjaroabarrotero;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;


public class KCOMainDrawerActivity extends Activity {

    ImageView redBar, blueBar, yellowBar, greenBar;
    private DrawerLayout leftDrawer;

    private ListView leftListDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcomain_drawer);

        redBar = (ImageView) findViewById(R.id.red_bar);
        blueBar = (ImageView) findViewById(R.id.blue_bar);
        yellowBar = (ImageView) findViewById(R.id.yellow_bar);
        greenBar = (ImageView) findViewById(R.id.green_bar);
        leftDrawer = (DrawerLayout) findViewById(R.id.mainDraweLayout);

        this.leftListDrawer = (ListView)findViewById(R.id.left_drawer);

        ArrayList<KCOListItems> listItems = getItems();

        KCOListItemsAdapter customaAdapter = new KCOListItemsAdapter(this, listItems);

        leftListDrawer.setAdapter(customaAdapter);

        leftListDrawer.setOnItemClickListener(new DrawerView());

        /*final String[] options = getResources().getStringArray(R.array.StringsDrawerList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options);
        leftListDrawer.setAdapter(adapter);*/

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
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 1:
                launchProfile();
                leftDrawer.closeDrawers();
                break;
            case 2:
                leftDrawer.closeDrawers();
                break;
            case 3:
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 4:
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 5:
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 6:
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 7:
                launchOrders();
                leftDrawer.closeDrawers();
                break;
            case 8:
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 9:
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 10:
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 11:
                Log.i("posicion sleccionada", String.valueOf(position));
                break;

            default:
                break;

        }
    }

    private ArrayList<KCOListItems>getItems(){

        ArrayList<KCOListItems> items = new ArrayList<KCOListItems>();

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kcomain_drawer, menu);
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

    public void launchPersonalCare(View v)
    {
        hideButtons();
        redBar.setVisibility(View.VISIBLE);
    }

    public void launchHome(View v)
    {
        hideButtons();
        blueBar.setVisibility(View.VISIBLE);
    }

    public void launchFoods(View v)
    {
        hideButtons();
        yellowBar.setVisibility(View.VISIBLE);
    }

    public void launchOthers(View v)
    {
        hideButtons();
        greenBar.setVisibility(View.VISIBLE);
    }

    public void launchProfile()
    {
        Intent launchActivity = new Intent(KCOMainDrawerActivity.this, KCOProfileActivity.class);
        startActivity(launchActivity);
    }

    public void launchOrders()
    {
        Intent launchActivity = new Intent(KCOMainDrawerActivity.this, KCOOrdersActivity.class);
        startActivity(launchActivity);
    }
}
