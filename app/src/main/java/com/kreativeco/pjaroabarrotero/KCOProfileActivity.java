package com.kreativeco.pjaroabarrotero;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kreativeco.pjaroabarrotero.libraries.Config;
import com.kreativeco.pjaroabarrotero.libraries.KCOASWS;
import com.kreativeco.pjaroabarrotero.libraries.KCOAsyncResponseG;
import com.pkmmte.view.CircularImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class KCOProfileActivity extends Activity implements OnMapReadyCallback {
    EditText shop,contact,address;
    RelativeLayout launchOrders;
    public DrawerLayout leftDrawer;
    private ListView leftListDrawer;
    ImageButton mainButton;
    ProgressDialog pDialog;
    String latitud,longitud;
    LocationManager locationManager = null;

    CircularImageView circularImageView;
    ImageButton btnCapture;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcoprofile);
        context = this;
        shop = (EditText) findViewById(R.id.shop);
        contact = (EditText) findViewById(R.id.contact);
        address = (EditText) findViewById(R.id.address);
        circularImageView = (CircularImageView)findViewById(R.id.imgPreviewProfile);
        btnCapture = (ImageButton)findViewById(R.id.camera_button_profile);
        btnCapture.setAlpha(0f);
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(shop.getWindowToken(), 0);
        launchOrders = (RelativeLayout) findViewById(R.id.proof);

        leftDrawer = (DrawerLayout) findViewById(R.id.drawerLayoutProfile);
        mainButton = (ImageButton) findViewById(R.id.menu_button_catalogue);

        this.leftListDrawer = (ListView)findViewById(R.id.left_drawer_profile);

        ArrayList<KCOListItems> listItems = getItems();

        KCOListItemsAdapter customaAdapter = new KCOListItemsAdapter(this, listItems);

        leftListDrawer.setAdapter(customaAdapter);

        leftListDrawer.setOnItemClickListener(new DrawerView());



        pDialog = new ProgressDialog(KCOProfileActivity.this);
        pDialog.setMessage("Por favor espere...");
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                UpdatePosition(location);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.bird_map_profile);
        mapFragment.getMapAsync(this);

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
        UpdatePosition();

        getProfileWS();

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
                leftDrawer.closeDrawers();
                break;
            case 2:
                leftDrawer.closeDrawers();
                launchCatalogue();
                break;
            case 3:
                leftDrawer.closeDrawers();
                launchCatalogue();
                break;
            case 4:
                leftDrawer.closeDrawers();
                launchCatalogue();
                break;
            case 5:
                leftDrawer.closeDrawers();
                launchCatalogue();
                break;
            case 6:
                leftDrawer.closeDrawers();
                launchCatalogue();
                break;
            case 7:
                leftDrawer.closeDrawers();
                launchOrders();
                break;
            case 8:
                leftDrawer.closeDrawers();
                launchOrders();
                break;
            case 9:
                leftDrawer.closeDrawers();
                launchOrders();
                break;
            case 10:
                leftDrawer.closeDrawers();
                launchOrders();
                break;
            case 11:
                leftDrawer.closeDrawers();
                launchOrders();
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

    public void openDrawer(View v){
        leftDrawer.openDrawer(leftListDrawer);
    }

    public void getProfileWS(){
        SharedPreferences userProfile = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);
        pDialog.show();
        new KCOASWS(new KCOAsyncResponseG() {
            @Override
            public void processFinishG(JSONObject json) {
                if (json!=null && json.length() > 0){
                    try {

                        //Obtenemos del JSON los datos
                        String message = json.getString("message");
                        String status = json.getString("status");

                        JSONObject profile = json.getJSONObject("profile");
                        String shopJ = profile.getString("shop");
                        String contactJ = profile.getString("contact");
                        String addressJ = profile.getString("address");
                        String fileImage = profile.getString("file_image");
                        String Client = profile.getString("code_customer");
                        Log.v("Cliente", Client);

                        Glide.with(context)
                                .load(fileImage)
                                .into(circularImageView);
                        circularImageView.setRotation(90f);
                        shop.setText(shopJ);

                        contact.setText(contactJ);
                        address.setText(addressJ);

                        //Debug
                        Log.d("Login", "Message : " + message);
                        Log.d("Status", "Status : " + status);
                        pDialog.dismiss();
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Log.d("GETPROFILE","Estatus 0 Token No Valido");
                    pDialog.dismiss();
                    //METHOD ALERT
                }
            }
        }).execute(Config.WS_GET_PROFILE, userProfile.getString("Token", ""));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kcoprofile, menu);
        return true;
    }

    public void LaunchOrdersMethod(View v)
    {
        Intent launchActivity = new Intent(KCOProfileActivity.this, KCOOrdersActivity.class);
        startActivity(launchActivity);
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

    @Override
    public void onMapReady(GoogleMap map) {

        Location lastCurrent = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        LatLng myShop = new LatLng(lastCurrent.getLatitude(), lastCurrent.getLongitude());

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myShop, 17));

        map.addMarker(new MarkerOptions()
                .title("MI Tienda")
                .snippet("Pajaro abarrotero")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable._04pin))
                .draggable(true)
                .position(myShop));
    }

    private void UpdatePosition(){
        Location loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        UpdatePosition(loc);
    }

    private void UpdatePosition(Location location){
        if(location!=null){
            latitud = Double.toString(location.getLatitude());
            longitud = Double.toString(location.getLongitude());
            Log.d("Coordenadas",latitud);
            Log.d("Coordenadas",longitud);
        }
    }

    public void launchCatalogue()
    {
        Intent launchActivity = new Intent(KCOProfileActivity.this, KCOMainDrawerActivity.class);
        startActivity(launchActivity);
        finish();
    }

    public void launchOrders()
    {
        Intent launchActivity = new Intent(KCOProfileActivity.this, KCOOrdersActivity.class);
        startActivity(launchActivity);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();
        //startActivity(new Intent(KCOProfileActivity.this, KCOMainDrawerActivity.class ));

        finish();
    }
}


