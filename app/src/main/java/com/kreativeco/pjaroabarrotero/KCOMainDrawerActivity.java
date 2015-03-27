package com.kreativeco.pjaroabarrotero;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.kreativeco.pjaroabarrotero.libraries.Config;
import com.kreativeco.pjaroabarrotero.libraries.KCOASProductsToCategory;
import com.kreativeco.pjaroabarrotero.libraries.KCOAsyncResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class KCOMainDrawerActivity extends Activity {

    ImageView redBar, blueBar, yellowBar, greenBar;
    ImageButton mainButton;
    public DrawerLayout leftDrawer;
    private ListView leftListDrawer;
    TableLayout careTable, homeTable, foodTable, othersTable;
    ScrollView careScroll, homeScroll, foodScroll, othersScroll ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcomain_drawer);

        /* Tab Buttons*/
        redBar = (ImageView) findViewById(R.id.red_bar);
        blueBar = (ImageView) findViewById(R.id.blue_bar);
        yellowBar = (ImageView) findViewById(R.id.yellow_bar);
        greenBar = (ImageView) findViewById(R.id.green_bar);

        /*Scrolls for tables*/
        homeScroll = (ScrollView) findViewById(R.id.home_scroll);
        careScroll = (ScrollView) findViewById(R.id.care_scroll);
        foodScroll = (ScrollView) findViewById(R.id.food_scroll);
        othersScroll = (ScrollView) findViewById(R.id.others_scroll);

        /*Tables*/
        careTable = (TableLayout) findViewById(R.id.care_table);
        homeTable = (TableLayout) findViewById(R.id.home_table);
        foodTable = (TableLayout) findViewById(R.id.food_table);
        othersTable = (TableLayout) findViewById(R.id.others_table);

        leftDrawer = (DrawerLayout) findViewById(R.id.mainDraweLayout);
        mainButton = (ImageButton) findViewById(R.id.menu_button_catalogue);

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
                leftDrawer.closeDrawers();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 1:
                launchProfile();
                leftDrawer.closeDrawers();
                finish();
                break;
            case 2:
                leftDrawer.closeDrawers();
                break;
            case 3:
                launchPersonalCare();
                Log.i("posicion sleccionada", String.valueOf(position));
                leftDrawer.closeDrawers();
                break;
            case 4:
                launchHome();
                leftDrawer.closeDrawers();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 5:
                launchFoods();
                leftDrawer.closeDrawers();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 6:
                launchOthers();
                leftDrawer.closeDrawers();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 7:
                launchOrders();
                finish();
                break;
            case 8:
                launchOrders();
                finish();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 9:
                launchOrders();
                finish();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 10:
                launchOrders();
                finish();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;
            case 11:
                launchOrders();
                finish();
                Log.i("posicion sleccionada", String.valueOf(position));
                break;

            default:
                break;

        }
    }

    private ArrayList<KCOListItems>getItems(){

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kcomain_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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
        careScroll.setVisibility(View.INVISIBLE);
        homeScroll.setVisibility(View.INVISIBLE);
        foodScroll.setVisibility(View.INVISIBLE);
        othersScroll.setVisibility(View.INVISIBLE);
    }

    public void launchPersonalCare(View v)
    {
        hideButtons();
        redBar.setVisibility(View.VISIBLE);
        careTable.removeAllViews();
        SharedPreferences userProfile = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);
        new KCOASProductsToCategory(new KCOAsyncResponse() {
            @Override
            public void processFinish(ArrayList<HashMap<String, String>> output)
            {

                Log.v("Tamano",output.size()+"");
                deployButtons(output.size(), careTable);
                setCatalogue(output, careTable);

            }
        }).execute(userProfile.getString("Token", ""), Config.CAT_CUIDADO_PERSONAL);
        careScroll.setVisibility(View.VISIBLE);
    }

    public void launchPersonalCare()
    {
        hideButtons();
        redBar.setVisibility(View.VISIBLE);
        careTable.removeAllViews();

        SharedPreferences userProfile = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);

        new KCOASProductsToCategory(new KCOAsyncResponse() {
            @Override
            public void processFinish(ArrayList<HashMap<String, String>> output)
            {

                Log.v("Tamano",output.size()+"");
                deployButtons(output.size(), careTable);
                setCatalogue(output, careTable);

            }
        }).execute(userProfile.getString("Token", ""), Config.CAT_CUIDADO_PERSONAL);
        careScroll.setVisibility(View.VISIBLE);
    }

    public void launchHome(View v)
    {
        hideButtons();
        blueBar.setVisibility(View.VISIBLE);
        homeTable.removeAllViews();

        SharedPreferences userProfile = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);
        new KCOASProductsToCategory(new KCOAsyncResponse() {
            @Override
            public void processFinish(ArrayList<HashMap<String, String>> output)
            {
                Log.v("Tamano",output.size()+"");
                deployButtons(output.size(), homeTable);
                setCatalogue(output, homeTable);
            }
        }).execute(userProfile.getString("Token", ""), Config.CAT_HOGAR);

        homeScroll.setVisibility(View.VISIBLE);
    }

    public void launchHome()
    {
        hideButtons();
        blueBar.setVisibility(View.VISIBLE);
        homeTable.removeAllViews();

        SharedPreferences userProfile = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);
        new KCOASProductsToCategory(new KCOAsyncResponse() {
            @Override
            public void processFinish(ArrayList<HashMap<String, String>> output)
            {
                Log.v("Tamano",output.size()+"");
                deployButtons(output.size(), homeTable);
                setCatalogue(output, homeTable);
            }
        }).execute(userProfile.getString("Token", ""), Config.CAT_HOGAR);

        homeScroll.setVisibility(View.VISIBLE);
    }

    public void launchFoods(View v)
    {
        hideButtons();
        yellowBar.setVisibility(View.VISIBLE);
        foodTable.removeAllViews();

        SharedPreferences userProfile = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);
        new KCOASProductsToCategory(new KCOAsyncResponse() {
            @Override
            public void processFinish(ArrayList<HashMap<String, String>> output)
            {
                Log.v("Tamano",output.size()+"");
                deployButtons(output.size(), foodTable);
                setCatalogue(output, foodTable);
            }
        }).execute(userProfile.getString("Token", ""), Config.CAT_ALIMENTOS);

        foodScroll.setVisibility(View.VISIBLE);
    }

    public void launchFoods()
    {
        hideButtons();
        yellowBar.setVisibility(View.VISIBLE);
        foodTable.removeAllViews();

        SharedPreferences userProfile = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);
        new KCOASProductsToCategory(new KCOAsyncResponse() {
            @Override
            public void processFinish(ArrayList<HashMap<String, String>> output)
            {
                Log.v("Tamano",output.size()+"");
                deployButtons(output.size(), foodTable);
                setCatalogue(output, foodTable);
            }
        }).execute(userProfile.getString("Token", ""), Config.CAT_ALIMENTOS);

        foodScroll.setVisibility(View.VISIBLE);
    }

    public void launchOthers(View v)
    {
        hideButtons();
        greenBar.setVisibility(View.VISIBLE);
        othersTable.removeAllViews();

        SharedPreferences userProfile = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);
        new KCOASProductsToCategory(new KCOAsyncResponse() {
            @Override
            public void processFinish(ArrayList<HashMap<String, String>> output)
            {
                Log.v("Tamano",output.size()+"");
                deployButtons(output.size(), othersTable);
                setCatalogue(output, othersTable);
            }
        }).execute(userProfile.getString("Token", ""), Config.CAT_OTROS);

        othersScroll.setVisibility(View.VISIBLE);
        /*hideButtons();
        othersScroll.setVisibility(View.VISIBLE);
        greenBar.setVisibility(View.VISIBLE);

        SharedPreferences userProfile = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);
        new KCOASProductsToCategory(new KCOAsyncResponse() {
            @Override
            public void processFinish(ArrayList<HashMap<String, String>> output) {
                Log.d("Tamaño ooutput", output.size()+"");
                if(output!=null) {
                    //ArrayList<KCOListItems> items = new ArrayList<>();
                    for (Map<String, String> map : output) {
                        String tagID = map.get("id");
                        Log.d("Values Received ID", tagID);
                        String tagCod = map.get("cod");
                        Log.d("Values Received COD", tagCod);
                        String tagName = map.get("name");
                        Log.d("Values Received NAME", tagName);
                        //items.add(new KCOListItems(Long.parseLong(tagName), tagCod, "drawable/_07icono_canasta"));
                    }
                    //KCOListAdapterToOrder customAdapter = new KCOListAdapterToOrder(KCOMainDrawerActivity.this, items);
                }else{
                    Log.d("Products To Category","Status 0");
                }
            }
        }).execute(userProfile.getString("Token", ""), Config.CAT_OTROS);*/
    }

    public void launchOthers()
    {
        hideButtons();
        othersScroll.setVisibility(View.VISIBLE);
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

    public void openDrawer(View v){
        leftDrawer.openDrawer(leftListDrawer);
    }

    private void deployButtons(int elementos, TableLayout catalogueTable) {
        int NUM_ROW;
        int indexRows=0;
        if(elementos % 3 == 0){
            NUM_ROW = elementos/3;
        }
        else NUM_ROW = (elementos/3) +1;

        int category_count = 12;
        int NUM_COL = category_count/4;

        LayoutInflater inflater = LayoutInflater.from(this);

        for(int row = 0; row<NUM_ROW; row++){
            TableRow myRow = new TableRow(this);
            myRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT
            ));
            for(int col = 0; col<NUM_COL; col++){
                RelativeLayout rl = (RelativeLayout) inflater.inflate(R.layout.row_item, null);
                // Modify inflated layout
                if((elementos % 3 == 1) && (col == 1 || col ==2) && (row == NUM_ROW-1) )
                {
                    ImageButton img = (ImageButton) rl.findViewById(R.id.img);
                    img.setBackgroundColor(Color.TRANSPARENT);
                    // Add the modified layout to the row
                    myRow.addView(rl);
                }
                else if((elementos % 3 == 2) && (col == 2) && (row == NUM_ROW - 1) )
                {
                    ImageButton img = (ImageButton) rl.findViewById(R.id.img);
                    img.setBackgroundColor(Color.TRANSPARENT);
                    // Add the modified layout to the row
                    myRow.addView(rl);
                 }

                else{

                    ImageButton img = (ImageButton) rl.findViewById(R.id.img);
                    TextView tv = (TextView) rl.findViewById(R.id.text);
                    tv.setBackgroundColor(Color.rgb(229, 229, 229));
                    // Add the modified layout to the row
                    myRow.addView(rl);
                    indexRows = indexRows +1;
                }

            }
            catalogueTable.addView(myRow);
        }
    }

    private void setCatalogue(ArrayList<HashMap<String, String>> output, TableLayout currentTable)
    {
        if(output!=null) {
            int elementos, indexCol, indexRow;
            indexCol = 0;
            indexRow = 0;
            elementos = 1;
            TableRow row;
            RelativeLayout rl;
            ImageButton imageButton;
            TextView textView;

            for(Map<String, String> map : output)
            {
                if(elementos % 3 == 1)
                {
                    Log.v("entraa if","1");
                    row = (TableRow) currentTable.getChildAt(indexRow);
                    Log.v("INDEX COL", indexCol+"");
                    rl = (RelativeLayout) row.getChildAt(indexCol);
                    imageButton = (ImageButton) rl.getChildAt(0);
                    textView = (TextView) rl.getChildAt(1);

                    final String tagCod = map.get("cod");
                    Log.d("Values Received COD", tagCod);

                    imageButton.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View view){
                            catalogueClicked(tagCod);
                        }
                    });

                    String tagUrl = map.get("file_image");
                    Picasso.with(this).load(tagUrl).placeholder(R.drawable.ic_launcher).resize(200,200).into(imageButton);
                    String tagName = map.get("name");

                    textView.setText(tagName);

                    Log.d("URL image", tagUrl+"");
                    Log.d("Values Received NAME 1", tagName);

                    indexCol = 1;
                    Log.i("IndexCol",indexCol+"");
                }

                if(elementos % 3 == 2)
                {
                    Log.v("entraa if","2");
                    row = (TableRow) currentTable.getChildAt(indexRow);
                    rl = (RelativeLayout) row.getChildAt(indexCol);

                    final String tagCod = map.get("cod");
                    Log.d("Values Received COD", tagCod);

                    imageButton = (ImageButton) rl.getChildAt(0);
                    textView = (TextView) rl.getChildAt(1);

                    imageButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            catalogueClicked(tagCod);
                        }
                    });

                    String tagUrl = map.get("file_image");
                    Picasso.with(this).load(tagUrl).placeholder(R.drawable.ic_launcher).resize(200,200).into(imageButton);

                    String tagName = map.get("name");
                    textView.setText(tagName);

                    Log.d("URL image", tagUrl+"");
                    Log.d("Values Received NAME 2", tagName);

                    indexCol = 2;
                    //Log.i("IndexCol",indexCol+"");
                }

                if(elementos % 3 == 0)
                {
                    Log.v("entraa if","3");
                    row = (TableRow) currentTable.getChildAt(indexRow);
                    rl = (RelativeLayout) row.getChildAt(indexCol);
                    imageButton = (ImageButton) rl.getChildAt(0);
                    textView = (TextView) rl.getChildAt(1);

                    final String tagCod = map.get("cod");
                    Log.d("Values Received COD", tagCod);

                    String tagName = map.get("name");
                    textView.setText(tagName);

                    String tagUrl = map.get("file_image");
                    Picasso.with(this).load(tagUrl).placeholder(R.drawable.ic_launcher).resize(200,200).into(imageButton);

                    imageButton.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View view){
                            catalogueClicked(tagCod);
                        }
                    });
                    Log.d("URL image", tagUrl+"");
                    Log.d("Values Received NAME 3", tagName);

                    indexCol = 0;
                    indexRow = indexRow + 1;
                    //Log.i("IndexCol",indexCol+"");
                    //Log.i("IndexCol",indexRow+"");
                }

                elementos = elementos +1;
            }

        }else{
            Log.d("Products To Category","Status 0");
        }
    }

    private void catalogueClicked(String tagCod){
        Intent launchActivity = new Intent(KCOMainDrawerActivity.this, KCOProductDetailsActivity.class);
        launchActivity.putExtra("code", tagCod);
        startActivity(launchActivity);
        Toast.makeText(this,"TagCode: " + tagCod , Toast.LENGTH_SHORT).show();
    }
}
