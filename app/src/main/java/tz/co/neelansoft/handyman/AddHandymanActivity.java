package tz.co.neelansoft.handyman;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static tz.co.neelansoft.handyman.SignUpActivity.EXTRA_USER_ID;

/**
 * Created by landre on 06/07/2018.
 */

public class AddHandymanActivity extends AppCompatActivity implements OnMapReadyCallback{

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private static final String MAPVIEW_BUNDLE_KEY = "mapview_key";
    private EditText mEditServiceName;
    private EditText mEditServiceDescription;
    private Spinner  mSpinnerService;
    private MapView  mMapView;
    private Button   mSaveButton;
    private Button   mCancelButton;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mFirebaseDatabase;

    private LocationManager locationManager;

    private GoogleMap mGoogleMap;
    private Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_handyman_layout);
        mEditServiceName = findViewById(R.id.etServiceName);
        mEditServiceDescription = findViewById(R.id.etServiceDescription);
        mSpinnerService  = findViewById(R.id.spService);
        mMapView      = findViewById(R.id.mapView);

        mSaveButton = findViewById(R.id.btnServiceSave);
        mCancelButton = findViewById(R.id.btnServiceCancel);

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        Bundle mapViewBundle = null;
        if(savedInstanceState != null){
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        checkPermission();
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_REFERENCE);
        if(getIntent().hasExtra(EXTRA_USER_ID) && getIntent().getStringExtra(EXTRA_USER_ID) != null){
            final FirebaseUser user = mFirebaseAuth.getCurrentUser();
            mEditServiceName.setText(user.getDisplayName());

            mCancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),MainActivity.class).putExtra(EXTRA_USER_ID,user.getUid()));
                    finish();
                }
            });

            mSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //saveService(user);
                }
            });
        }

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.services,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerService.setAdapter(adapter);
    }


    @Override
    protected void onStart(){
        super.onStart();

        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if(user == null){
            exit();
        }
    }

    private void saveService(FirebaseUser user){
        DatabaseReference dbRef = mFirebaseDatabase.child("service_providers");
        String serviceName = mEditServiceName.getText().toString();
        int serviceCategory = mSpinnerService.getSelectedItemPosition();
        String serviceDescription = mEditServiceDescription.getText().toString();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(AddHandymanActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            }
            else{
                mLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }

    }

    private void checkPermission(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(AddHandymanActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            }
            else{
                mLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] permissionResults){
        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:
                if(permissionResults.length > 0 && permissionResults[0] == PackageManager.PERMISSION_GRANTED){

                    mLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                }
                else{
                    checkPermission();
                }
        }
    }
    private void exit(){
       startActivity(new Intent(getApplicationContext(), SignInActivity.class));
       finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMinZoomPreference(12);

        UiSettings uiSettings = mGoogleMap.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setAllGesturesEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        LatLng myPlace = new LatLng(mLocation.getLatitude(),mLocation.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(myPlace);
        mGoogleMap.addMarker(markerOptions);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(myPlace));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
