package com.kreativeco.pjaroabarrotero;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.kreativeco.pjaroabarrotero.libraries.Config;
import com.pkmmte.view.CircularImageView;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class KCORegisterActivity extends Activity {

    String mCurrentPhotoPath;
    ImageButton registerBtn;
    EditText shop,name,address;
    LocationManager locationManager = null;
    String latitud,longitud;
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    private String foto,photo;
    private File file;
    private static String nombreFoto;
    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 1;
    public static final int MEDIA_TYPE_IMAGE = 1;

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "PajaroAbarrotero";

    private Uri fileUri; // file url to store image/video

    //private ImageView imgPreview;
    CircularImageView circularImageView;
    private ImageButton btnCapturePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcoregister);

        shop = (EditText)findViewById(R.id.shop_register);
        name = (EditText)findViewById(R.id.nameS_register);
        address = (EditText)findViewById(R.id.addr_register);

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

        //imgPreview = (ImageView) findViewById(R.id.imgPreview);
        circularImageView = (CircularImageView)findViewById(R.id.imgPreview);
        btnCapturePicture = (ImageButton) findViewById(R.id.camera_button);

        btnCapturePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // capture picture
                captureImage();
            }
        });

        registerBtn = (ImageButton)findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                if(foto.equals(photo))
                    Toast.makeText(getApplicationContext(), "¡Toma una foto primero!", Toast.LENGTH_LONG).show();
                else
                    new KCOUploadImage().execute();
            }
        });

        // Checa la disponibilidad de la camara
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Tu dispositivo no tiene soporte para cámara",
                    Toast.LENGTH_LONG).show();
            // cierra esta actividad
            finish();
        }
        beginFile();
        super.onResume();
    }


    private void beginFile(){
        fileUri = Uri.parse("android.resource://com.kreativeco.pjaroabarrotero/"+R.drawable._02logo);
        photo = fileUri.getPath();
        foto=photo;
        //file = new File(photo);
    }


    private boolean isDeviceSupportCamera() {
        // this device has a camera
// no camera on this device
        return getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA);
    }

    //Captura una imagen con la cámara y la muestra en un imageView
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        //inicia el intent de la captura de imagen
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    //Guardamos la url del archivo
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //obtenemos la url del archivo
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    //Recibimos el metodo de la actividad resultado despues de cerrar la camara
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Si el resultado de la captura es una Imagen
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //Imagen capturada con exito
                //Se muestra en el ImageView
                previewCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                //Se cancela la captura de Imagen
                Toast.makeText(getApplicationContext(),
                        "Captura Cancelada", Toast.LENGTH_SHORT).show();
            } else {
                //Fallo en la captura de imagen
                Toast.makeText(getApplicationContext(),
                        "Sorry! Error al capturar la imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Mostramos la imagen desde el path
    private void previewCapturedImage() {
        try {

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),options);

            Matrix mat = new Matrix();
            mat.postRotate(90);
            Bitmap bMapRotate = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);

            //circularImageView.setImageBitmap(bMapRotate);
            circularImageView.setImageBitmap(bMapRotate);
            btnCapturePicture.setAlpha(0f);

            foto = fileUri.getPath();
            file = new File(foto);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    // ------------ Helper Methods ----------------------

    //Crea la uri del archivo a almacenar image
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }


    //Retornamos image
    private static File getOutputMediaFile(int type) {
        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);
        //Creamos el directorio de almacenamiento si no existe
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        //Creamos el nombre del archivo de medios
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
            nombreFoto="IMG_" + timeStamp + ".jpg";
        } else {
            return null;
        }

        return mediaFile;
    }



    private boolean uploadFoto(String imag){
        boolean status = false;
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpPost httppost = new HttpPost(Config.FILE_UPLOAD_URL);
        MultipartEntity mpEntity = new MultipartEntity();
        ContentBody fotoUp = new FileBody(file, "image/jpeg");
        try {
            mpEntity.addPart("task", new StringBody(Config.ADD_PROFILE));
            mpEntity.addPart("shop", new StringBody(shop.getText().toString()));
            mpEntity.addPart("name", new StringBody(name.getText().toString()));
            mpEntity.addPart("address", new StringBody(address.getText().toString()));
            mpEntity.addPart("latitude", new StringBody(latitud));
            mpEntity.addPart("longitude", new StringBody(longitud));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mpEntity.addPart(Config.FILE_IMAGE, fotoUp);

        httppost.setEntity(mpEntity);
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity httpEntity = response.getEntity();
            is = httpEntity.getContent();
            status=saveUser(is);
            //String st = EntityUtils.toString(response.getEntity());
            //Log.v("Response ImageUpload", st);
            httpclient.getConnectionManager().shutdown();

        } catch (UnsupportedEncodingException | ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }

    private boolean saveUser(InputStream is){

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            Log.d("JSON Parse", json);
        } catch (Exception e) {
            Log.e("Error en el buffer", "Error al convertir el resultado " + e.toString());
        }

        // tratamos de parsear el string a un objeto de tipo JSON
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("Parseo JSON", "Error al parsear los datos " + e.toString());
        }

        String status = "";
        try {
            status = jObj.getString("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(status.equals("1")){
            //Guardamos datos
            SharedPreferences tokenUser = getSharedPreferences("User", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = tokenUser.edit();
            //Obtenemos del JSON los datos
            String message = "";
            String username = "";
            String password = "";
            try {
                message = jObj.getString("message");
                JSONObject profile = jObj.getJSONObject("profile");
                username = profile.getString("username");
                password = profile.getString("password");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Almacenamos los datos del JSON en la estructura
            editor.putString("Message", message);
            editor.putString("STATUS", status);
            editor.putString("USERNAME", username);
            editor.putString("PASSWORD", password);
            editor.commit();
            return true;
        }else{
            SharedPreferences tokenUser = getSharedPreferences("User", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = tokenUser.edit();
            editor.putString("Message", "Hubo un problema al realizar el registro");
            editor.putString("STATUS", status);
            editor.commit();

            return false;
        }

    }



    class KCOUploadImage extends AsyncTask<String,String,String> {

        ProgressDialog pDialog;

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(KCORegisterActivity.this);
            pDialog.setMessage("Realizando registro, espere..." );
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... arg0) {
            if(uploadFoto(foto))
                return "ok";
            else
                return "bad";
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result.equals("ok")){
                Log.d("UpImage","OK");
                createMessageRegisterOK();
            }
            else{
                Log.d("UpImage","NOT OK");
                createMessageRegisterNoOK();
            }
            pDialog.dismiss();
        }
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



    private void createMessageRegisterOK(){
        AlertDialog.Builder builder = new AlertDialog.Builder(KCORegisterActivity.this);
        builder.setMessage(R.string.message_registerOK).setTitle(R.string.title_register);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createMessageRegisterNewUser();
            }
        });

        AlertDialog dialogResetOk = builder.create();
        dialogResetOk.show();
    }

    private void createMessageRegisterNoOK(){
        AlertDialog.Builder builder = new AlertDialog.Builder(KCORegisterActivity.this);
        builder.setMessage(R.string.message_registerNoOK).setTitle(R.string.title_register);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialogResetOk = builder.create();
        dialogResetOk.show();
    }

    private void createMessageRegisterNewUser(){
        SharedPreferences newUser = getSharedPreferences("User", Context.MODE_PRIVATE);
        AlertDialog.Builder builder = new AlertDialog.Builder(KCORegisterActivity.this);
        builder.setMessage("Tu nombre de usuario es: "+newUser.getString("USERNAME", "")+"" +
                " y tu contraseña es: "+newUser.getString("PASSWORD", "")+".").setTitle(R.string.welcomepa);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                shop.setText("");
                name.setText("");
                address.setText("");
                Intent launchActivity = new Intent(KCORegisterActivity.this, KCOLoginActivity.class);
                startActivity(launchActivity);
                finish();
            }
        });

        AlertDialog dialogResetOk = builder.create();
        dialogResetOk.show();
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
}
