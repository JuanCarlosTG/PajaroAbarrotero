package com.kreativeco.pjaroabarrotero;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.kreativeco.pjaroabarrotero.KCODatabase.KCOCustomerDBAdapter;
import com.kreativeco.pjaroabarrotero.libraries.KCOASGetShopActives;
import com.kreativeco.pjaroabarrotero.libraries.KCOAsyncResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kreativeco on 20/05/15.
 */
public class KCOSearchShopsActivity extends Activity implements SearchView.OnQueryTextListener,
        SearchView.OnCloseListener{

    private SearchView searchView;
    private KCOCustomerDBAdapter mDbHelper;
    ImageButton gone1;
    Button gone2, gone3;
    TextView tvHeader;
    LinearLayout llShops;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_shops);

        searchView = (SearchView) findViewById(R.id.search);
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);

        mDbHelper = new KCOCustomerDBAdapter(this);
        mDbHelper.open();

        llShops     = (LinearLayout) findViewById(R.id.ll_shops);
        gone1       = (ImageButton) findViewById(R.id.menu_button_catalogue);
        gone2       = (Button) findViewById(R.id.bt_info);
        gone3       = (Button) findViewById(R.id.bt_car);

        tvHeader    = (TextView) findViewById(R.id.tv_header);
        tvHeader.setText("Tiendas Activas");

        gone1.setVisibility(View.INVISIBLE);
        gone2.setVisibility(View.INVISIBLE);
        gone3.setVisibility(View.INVISIBLE);
        //Clean all Customers
        mDbHelper.deleteAllCustomers();
        createShopDataBase();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDbHelper  != null) {
            mDbHelper.close();
        }
    }

    public boolean onQueryTextChange(String newText) {
        showResults(newText + "*");
        return false;
    }

    public boolean onQueryTextSubmit(String query) {
        showResults(query + "*");
        return false;
    }

    public boolean onClose() {
        showResults("CLI *");
        return false;
    }


    private void showResults(String query) {

        llShops.removeAllViews();

        Cursor cursor = mDbHelper.searchCustomer((query != null ? query.toString() : "@@@@"));
        if(cursor.moveToFirst()) {

            LayoutInflater inflater = LayoutInflater.from(this);

            do {

                LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.customerresult, null);

                ImageView ivShop             = (ImageView)   ll.findViewById(R.id.iv_shop);
                final TextView tvShopName    = (TextView)    ll.findViewById(R.id.tv_shop_name);
                final TextView tvShopAddress = (TextView)    ll.findViewById(R.id.tv_shop_address);

                String name     = cursor.getString(1);
                tvShopName.setText(name);

                String address  = cursor.getString(4);
                String colony   = cursor.getString(6);
                String country  = cursor.getString(7);
                String zip      = cursor.getString(9);
                tvShopAddress.setText(address + ", " + colony + ", " + country + ", CP " + zip );

                String img      = cursor.getString(8);
                Picasso.with(this).load(img).placeholder(R.drawable.ic_launcher).resize(100,100).into(ivShop);

                ll.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view){
                        goToMainMenu();
                    }
                });
                llShops.addView(ll);

            } while (cursor.moveToNext());
        }

    }

    public void createShopDataBase(){

        SharedPreferences userProfile = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);
        new KCOASGetShopActives(new KCOAsyncResponse() {
            @Override
            public void processFinish(ArrayList<HashMap<String, String>> output)
            {
                Log.v("Tamano", output + "");
                setShopDataBase(output);

            }
        }).execute(userProfile.getString("Token", ""));
    }

    public void setShopDataBase(ArrayList<HashMap<String, String>> output){

        if(output!=null) {

            for(Map<String, String> map : output)
            {
                String tagReferenceAddress = map.get("reference_address");
                Log.i("referencia", tagReferenceAddress);

                String tagZip = map.get("zip");
                Log.i("zip", tagZip);

                String tagShop = map.get("shop");
                Log.i("nombre", tagShop);

                String tagAddress = map.get("address");
                String tagColony = map.get("colony");
                String tagFileImage = map.get("file_image");
                Log.i("imagen", tagFileImage);

                String tagCode = map.get("code");
                String tagContact = map.get("contact");
                String tagCodeCustomer = map.get("code_customer");
                String tagCity = map.get("city");

                mDbHelper.createCustomer(tagShop, tagCode, tagCodeCustomer, tagContact, tagAddress, tagReferenceAddress, tagColony, tagCity, tagZip, tagFileImage);
            }
            showResults("CLI *");
        }else{
            Log.d("Products To Category","Status 0");
        }
    }

    public void goToMainMenu(){
        Intent i = new Intent(KCOSearchShopsActivity.this, KCOMainDrawerActivity.class);
        startActivity(i);
    }
}
