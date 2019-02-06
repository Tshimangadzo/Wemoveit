package com.example.a800361.shifterapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.craftman.cardform.CardForm;
import com.example.a800361.shifterapp.Remote.IFCMService;
import com.example.a800361.shifterapp.common.Common;
import com.example.a800361.shifterapp.helper.CustomInfoWindow;
import com.example.a800361.shifterapp.model.FCMResponse;
import com.example.a800361.shifterapp.model.Notification;
import com.example.a800361.shifterapp.model.Rider;
import com.example.a800361.shifterapp.model.Sender;
import com.example.a800361.shifterapp.model.Token;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.IOException;
import java.util.UUID;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class home_ extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,LocationListener{


    SupportMapFragment mapFragment;



    private GoogleMap mMap;
    //Declaration of values
    private static  final int MY_PERMISSION_REQUEST_CODE = 7000;
    private static  final int PLAY_SERVICE_RES_REQUEST = 7001;

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private static int UPDATE_INTERVAL = 5000;
    private static int FATEST_INTERVAL = 3000;
    private static int DISPLACEMENT = 10;

    DatabaseReference ref;
    GeoFire geoFire;
    Marker mUserMarker;

    //Uploading image profile
    ImageView imageView_profie;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    //Uploading image firebase
    FirebaseStorage storage;
    StorageReference storageReference;

    private PlaceAutocompleteFragment place_destination,place_location;

    Button btnPickupRequest;
    RelativeLayout RelativeLayout_;

    int distance = 1;//1km
    private static final int LIMIT = 3; //3KM

    IFCMService mService;

    DatabaseReference driversAvailable;

    double startLatitute,startLongitude;
    double endLatitude,endLongitude;
    String place_name_location,place_name_destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       final SaveLatandLong saveLatandLong = new SaveLatandLong();
        mService = Common.getFCMService();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

      place_destination =(PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.place_destination);
        place_location =(PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.place_location);


        place_location.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                final  LatLng latLng = place.getLatLng();
                startLatitute = latLng.latitude;
                startLongitude = latLng.longitude;


                  saveLatandLong.setStartlatitude(startLatitute);
                  saveLatandLong.setStartlongitude(startLongitude);

                if(mUserMarker!=null){
                    mUserMarker.remove();
                }
                place_name_location = place.getName().toString();
                saveLatandLong.setPicking_location(place_name_location);
                mUserMarker=mMap.addMarker(new MarkerOptions().title(place_name_location)
                        .position(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(12),2000,null);
                final  LatLng start = new LatLng(saveLatandLong.getStartlatitude(),saveLatandLong.getStartlongitude());
                final LatLng end  = new LatLng(saveLatandLong.getEndlatitude(),saveLatandLong.getEndlongitude());

                saveLatandLong.setKm(calculate_km(start,end));
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(getApplicationContext(),status.toString(),Toast.LENGTH_LONG).show();
            }
        });

        place_destination.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                final  LatLng latLng = place.getLatLng();
                 endLatitude = latLng.latitude;
                 endLongitude = latLng.longitude;
                saveLatandLong.setEndlatitude(endLatitude);
                saveLatandLong.setEndlongitude(endLongitude);

                 place_name_destination =place.getName().toString();
                 saveLatandLong.setDestination_location(place_name_destination);
                mUserMarker=mMap.addMarker(new MarkerOptions().title(place_name_destination)
                        .position(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(12),2000,null);
                final  LatLng start = new LatLng(saveLatandLong.getStartlatitude(),saveLatandLong.getStartlongitude());
                final LatLng end  = new LatLng(saveLatandLong.getEndlatitude(),saveLatandLong.getEndlongitude());

                saveLatandLong.setKm(calculate_km(start,end));

            }

            @Override
            public void onError(Status status) {
                Toast.makeText(getApplicationContext(),status.toString(),Toast.LENGTH_LONG).show();
            }
        });



        mapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        RelativeLayout_ = (RelativeLayout)findViewById(R.id.RelativeLayout_);
         btnPickupRequest =(Button)findViewById(R.id.btnPickupRequest);

         btnPickupRequest.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {



                 if(TextUtils.isEmpty(saveLatandLong.getPicking_location())){

                     Snackbar.make(RelativeLayout_,"Please enter from location",Snackbar.LENGTH_LONG).show();
                     return;
                 }
                  if(TextUtils.isEmpty(saveLatandLong.getDestination_location())){
                      Snackbar.make(RelativeLayout_,"Please enter to location",Snackbar.LENGTH_LONG).show();
                      return;
                  }
                      Intent i =new Intent(getApplicationContext(),complete_form.class);
                      i.putExtra("distance",saveLatandLong.getKm());
                      i.putExtra("location",saveLatandLong.getPicking_location());
                      i.putExtra("destination",saveLatandLong.getDestination_location());
                      startActivity(i);



             }
         });



         //Uploading image firebase profile
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        View hView =  navigationView.getHeaderView(0);
        imageView_profie =(ImageView)hView.findViewById(R.id.imageView_profile_);
        imageView_profie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    UploadImage();

                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });


        setUpLocation();
        updateFirebaseToken();
    }

    private void UploadImage() {
        Toast.makeText(getApplicationContext(),"Profile picture changed",Toast.LENGTH_LONG).show();
        if(ChooseImage()){
            if(filePath != null){
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Changing profile picture......");
                progressDialog.show();

                StorageReference ref = storageReference.child("images/"+UUID.randomUUID().toString());
                ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Profile picture changed",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Failed to upload "+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress =   (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage("Uploaded "+(int)progress+"%");
                    }
                });
            }
        }else {
           ChooseImage();
        }


    }

    private boolean ChooseImage() {

        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"select picture"),PICK_IMAGE_REQUEST);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && requestCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imageView_profie.setImageBitmap(bitmap);
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private double calculate_km(LatLng start, LatLng end) {
        float results[] = new float[10];
      Location.distanceBetween(start.latitude,start.longitude,end.latitude,end.longitude,results);
      double distance = ((double)results[0]/1000);

      return distance;
    }

    private void updateFirebaseToken() {

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference tokens = db.getReference(Common.token_tbl);

        Token token = new Token(FirebaseInstanceId.getInstance().getToken());

        tokens.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){

            case MY_PERMISSION_REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    if(checkPlayServices()){
                        buildGoogleApiClient();
                        createLocationRequest();
                        displayLocation();


                    }

                }
        }
    }

    private void setUpLocation() {

        try {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    ) {

                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_CODE);

            } else {

                if (checkPlayServices()) {
                    buildGoogleApiClient();
                    createLocationRequest();

                        displayLocation();


                }
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    private void displayLocation() {

        try {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    ) {
                return;

            }
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {

                    final double latitude = mLastLocation.getLatitude();
                    final double longitude = mLastLocation.getLongitude();


                //Presence System
                driversAvailable=FirebaseDatabase.getInstance().getReference(Common.driver_tbl);

                driversAvailable.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        loadAllAvailabes();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                            if (mUserMarker != null)
                                mUserMarker.remove();
                            mUserMarker = mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(latitude, longitude))
                                    .title(String.format("Your position")));
                            //Now lets move camera to the position
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15.0f));
                            //Lets now draw the animation

                //loadAllAvailabes();


            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("Error => ",e.getMessage());
        }


    }


    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);


    }
    private void buildGoogleApiClient() {
        try {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                    .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"I am still struggling",Toast.LENGTH_LONG).show();
        }



    }
    private boolean checkPlayServices() {

        int resultCode  = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(resultCode != ConnectionResult.SUCCESS){
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                GooglePlayServicesUtil.getErrorDialog(resultCode,this,PLAY_SERVICE_RES_REQUEST).show();
            }else{
                Toast.makeText(getApplicationContext(),"This device is not supported",Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.payments) {
            Toast.makeText(getApplicationContext(),"I am payments",Toast.LENGTH_LONG).show();

            // Handle the camera action
        } else if (id == R.id.Your_trips) {

        } else if (id == R.id.help) {

        } else if (id == R.id.notification) {

        } else if (id == R.id.promotions) {

        } else if (id == R.id.about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

       mMap =  googleMap;

       mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
        startLocationUpdates();

    }

    private void startLocationUpdates() {
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ){
            return;

        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest, (com.google.android.gms.location.LocationListener) home_.this);

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        displayLocation();

    }


}
