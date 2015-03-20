package com.kreativeco.pjaroabarrotero;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kreativeco.pjaroabarrotero.libraries.KCOASWS;
import com.kreativeco.pjaroabarrotero.libraries.KCOAsyncResponseG;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class KCORegisterActivity extends Activity implements OnMapReadyCallback {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    String mCurrentPhotoPath;
    ImageButton cameraBtn,registerBtn;
    EditText shop,name,address;
    LocationManager locationManager = null;
    String latitud,longitud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcoregister);

        shop = (EditText)findViewById(R.id.shop_register);
        name = (EditText)findViewById(R.id.nameS_register);
        address = (EditText)findViewById(R.id.addr_register);

        cameraBtn = (ImageButton)findViewById(R.id.camera_button);
        cameraBtn.setOnClickListener(cameraListener);

        registerBtn = (ImageButton)findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(registerListener);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.bird_map);
        mapFragment.getMapAsync(this);

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

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
        UpdatePosition();
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

    private OnClickListener cameraListener = new OnClickListener()
    {
        public void onClick(View view){
            dispatchTakePictureIntent(view);
        }
    };

    private void createMessageRegisterOK(){
        AlertDialog.Builder builder = new AlertDialog.Builder(KCORegisterActivity.this);
        builder.setMessage(R.string.message_registerOK).setTitle(R.string.title_register);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent launchActivity = new Intent(KCORegisterActivity.this, KCOLoginActivity.class);
                startActivity(launchActivity);
                finish();
            }
        });

        AlertDialog dialogResetOk = builder.create();
        dialogResetOk.show();
    }

    private OnClickListener registerListener = new OnClickListener()
    {
        public void onClick(View view){
            String opt="1";
            new KCOASWS(new KCOAsyncResponseG() {
                @Override
                public void processFinishG(JSONObject json) {
                    if (json!=null && json.length() > 0){
                        try {
                            //Obtenemos del JSON los datos
                            String message = json.getString("message");
                            JSONObject profile = json.getJSONObject("profile");
                            String username = profile.getString("username");

                            //Debug
                            Log.d("Message", "Message : " + message);
                            Log.d("UserName", "Token : " + username);

                            createMessageRegisterOK();

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Log.d("REGISTER","Token No Válido");
                        //METHOD ALERT
                    }
                }
            }).execute(opt, shop.getText().toString(), name.getText().toString(),address.getText().toString(),latitud,longitud);
        }
    };

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        Log.i("ruta", mCurrentPhotoPath);
        return image;
        //ImageButton registerPhotoBtn = (ImageButton)findViewById(R.id.camera_button);
        //registerPhotoBtn.setImageURI();

    }

    public void dispatchTakePictureIntent(View view)
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("Error al cargar foto", ex.toString());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
        /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }*/
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kcoregister, menu);
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

    @Override
    public void onMapReady(GoogleMap map) {

        Location loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        LatLng myShop = new LatLng(loc.getLatitude(), loc.getLongitude());
            map.setMyLocationEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myShop, 17));

            map.addMarker(new MarkerOptions()
                    .title("MI Tienda")
                    .snippet("Pajaro abarrotero")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable._04pin))
                    .draggable(true)
                    .position(myShop));
    }
}
