package com.kreativeco.pjaroabarrotero;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
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

    /*Parameters*/

    public DrawerLayout leftDrawer;
    private ListView leftListDrawer;

    ImageView   redBar, blueBar, yellowBar, greenBar;
    ImageButton mainButton;
    Button      btInfo;
    TableLayout careTable, homeTable, foodTable, othersTable;
    ScrollView  careScroll, homeScroll, foodScroll, othersScroll ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLUE);
        }
        setContentView(R.layout.activity_kcomain_drawer);

        btInfo          = (Button) findViewById(R.id.bt_info);

        /* Tab Buttons*/
        redBar          = (ImageView) findViewById(R.id.iv_red);
        blueBar         = (ImageView) findViewById(R.id.iv_blue);
        yellowBar       = (ImageView) findViewById(R.id.iv_yellow);
        greenBar        = (ImageView) findViewById(R.id.iv_green);

        /*Scrolls for tables*/
        homeScroll      = (ScrollView) findViewById(R.id.home_scroll);
        careScroll      = (ScrollView) findViewById(R.id.care_scroll);
        foodScroll      = (ScrollView) findViewById(R.id.food_scroll);
        othersScroll    = (ScrollView) findViewById(R.id.others_scroll);

        /*Tables*/
        careTable       = (TableLayout) findViewById(R.id.care_table);
        homeTable       = (TableLayout) findViewById(R.id.home_table);
        foodTable       = (TableLayout) findViewById(R.id.food_table);
        othersTable     = (TableLayout) findViewById(R.id.others_table);

        leftDrawer      = (DrawerLayout) findViewById(R.id.mainDraweLayout);
        mainButton      = (ImageButton) findViewById(R.id.menu_button_catalogue);

        this.leftListDrawer = (ListView)findViewById(R.id.left_drawer);
        ArrayList<KCOListItems> listItems = getItems();
        KCOListItemsAdapter customaAdapter = new KCOListItemsAdapter(this, listItems);
        leftListDrawer.setAdapter(customaAdapter);
        leftListDrawer.setOnItemClickListener(new DrawerView());

        listProducts(careTable, careScroll, "");

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
                leftDrawer.closeDrawers();
                break;
            case 4:
                launchHome();
                leftDrawer.closeDrawers();
                break;
            case 5:
                launchFoods();
                leftDrawer.closeDrawers();
                break;
            case 6:
                launchOthers();
                leftDrawer.closeDrawers();
                break;
            case 7:
                launchOrders();
                finish();
                break;
            case 8:
                launchOrders();
                finish();
                break;
            case 9:
                launchOrders();
                finish();
                break;
            case 10:
                launchOrders();
                finish();
                break;
            case 11:
                launchOrders();
                finish();
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

    public void clickCategry(View v){

        int i = Integer.parseInt(v.getTag().toString());
        if(i==1){
            listProducts(careTable, redBar, careScroll, v.getTag().toString());
        }else if(i==2){
            listProducts(homeTable, blueBar, homeScroll, v.getTag().toString());
        }else if(i==3){
            listProducts(foodTable, yellowBar, foodScroll, v.getTag().toString());
        }else if(i==4){
            listProducts(othersTable, greenBar, othersScroll, v.getTag().toString());
        }
    }

    public void listProducts(final TableLayout tableCategory, ImageView imageBar, ScrollView scrollCategory, String strCategory){

        hideButtons();
        imageBar.setVisibility(View.VISIBLE);
        tableCategory.removeAllViews();

        SharedPreferences userProfile = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);

        new KCOASProductsToCategory(new KCOAsyncResponse() {
            @Override
            public void processFinish(ArrayList<HashMap<String, String>> output)
            {

                Log.v("Tamano",output.size()+"");
                deployButtons(output.size(), tableCategory);
                setCatalogue(output, tableCategory);

            }
        }).execute(userProfile.getString("Token", ""), strCategory);

        scrollCategory.setVisibility(View.VISIBLE);

    }

    public void listProducts(final TableLayout tableCategory, ScrollView scrollCategory, String strCategory){

        tableCategory.removeAllViews();

        SharedPreferences userProfile = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);

        new KCOASProductsToCategory(new KCOAsyncResponse() {
            @Override
            public void processFinish(ArrayList<HashMap<String, String>> output)
            {

                Log.v("Tamano",output.size()+"");
                deployButtons(output.size(), tableCategory);
                setCatalogue(output, tableCategory);

            }
        }).execute(userProfile.getString("Token", ""), strCategory);

        scrollCategory.setVisibility(View.VISIBLE);

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

    public void launchOthers()
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
                    // Add the modified layout to the row
                    rl.setVisibility(View.INVISIBLE);
                    myRow.addView(rl);
                }
                else if((elementos % 3 == 2) && (col == 2) && (row == NUM_ROW - 1) )
                {
                    // Add the modified layout to the row
                    rl.setVisibility(View.INVISIBLE);
                    myRow.addView(rl);
                 }

                else{

                    ImageButton img = (ImageButton) rl.findViewById(R.id.bt_product);
                    TextView tvProductName = (TextView) rl.findViewById(R.id.tv_product_name);
                    TextView tvCantity = (TextView) rl.findViewById(R.id.tv_cantity);
                    Button btPlus = (Button) rl.findViewById(R.id.bt_plus);
                    Button btMinus = (Button) rl.findViewById(R.id.bt_minus);
                    //Button btAddProduct = (Button) rl.findViewById(R.id.bt_add_product);
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

            int elementos = 1, indexCol = 0, indexRow=0;

            for(Map<String, String> map : output)
            {
                if(elementos % 3 == 1)
                {
                    setProduct(currentTable, map, indexCol, indexRow);
                    indexCol = 1;
                }

                if(elementos % 3 == 2)
                {
                    setProduct(currentTable, map, indexCol, indexRow);
                    indexCol = 2;
                }

                if(elementos % 3 == 0)
                {
                    setProduct(currentTable, map, indexCol, indexRow);
                    indexCol = 0;
                    indexRow = indexRow +1;
                }

                elementos = elementos +1;
            }

        }else{
            Log.d("Products To Category","Status 0");
        }
    }


    public  void setProduct(TableLayout currentTable, Map<String, String> map, int indexCol, int indexRow){

        TableRow row;
        RelativeLayout rl;
        ImageButton imageButton;
        TextView tvProductName, tvPrice;
        Button btPlus, btMinus, btAddProduct;

        row = (TableRow) currentTable.getChildAt(indexRow);
        rl = (RelativeLayout) row.getChildAt(indexCol);
        imageButton = (ImageButton) rl.findViewById(R.id.bt_product);

        btPlus = (Button) rl.findViewById(R.id.bt_plus);
        btMinus = (Button) rl.findViewById(R.id.bt_minus);
        tvProductName = (TextView) rl.findViewById(R.id.tv_product_name);
        tvPrice = (TextView) rl.findViewById(R.id.tv_price);
        final TextView tvCantity = (TextView) rl.findViewById(R.id.tv_cantity);

        String tagPromotion = map.get("min");
        final String tagCod = map.get("cod");
        String tagUrl = map.get("file_image");
        String tagName = map.get("name");
        String tagPrice = map.get("cunit");

        tvCantity.setText(tagPromotion);

        Picasso.with(this).load(tagUrl).placeholder(R.drawable.ic_launcher).resize(200,200).into(imageButton);
        tvProductName.setText(tagName);
        tvPrice.setText(tagPrice);

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

        imageButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                catalogueClicked(tagCod);
            }
        });
    }

    private void catalogueClicked(String tagCod){
        Intent launchActivity = new Intent(KCOMainDrawerActivity.this, KCOProductDetailsActivity.class);
        launchActivity.putExtra("code", tagCod);
        startActivity(launchActivity);
        finish();
        Toast.makeText(this,"TagCode: " + tagCod , Toast.LENGTH_SHORT).show();
    }

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

    public void showInfo(View v) {
        PopupWindow popup = new PopupWindow(KCOMainDrawerActivity.this);
        View layout = getLayoutInflater().inflate(R.layout.ui_popup_info, null);
        popup.setContentView(layout);

        // Set content width and height
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);

        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);

        // Show anchored to button
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAsDropDown(v);
    }

    public void launchShoppingCar(View view){
        Intent i = new Intent(KCOMainDrawerActivity.this, KCOOrdersActivity.class);
        startActivity(i);
    }

}
