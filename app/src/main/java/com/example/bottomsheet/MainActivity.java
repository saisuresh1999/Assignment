package com.example.bottomsheet;

import android.Manifest;
import android.app.LauncherActivity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latitude,longitude;
    Marker marker;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private TextView lat;
    private   TextView lon;
    private ImageButton loc;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<CardView> list;


    private BottomSheetBehavior bottomSheetBehavior;
    private ImageButton up;
    private ImageButton down;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//********************************Google Map Fragment supported by GoogleMapAPI***********************//


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//**********************************Recycler View Code************************************************//
        recyclerView=(RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        list=new ArrayList<>();

        CardView listitem= new CardView("480","₹","A Song of Ice And Fire","1.");
        list.add(listitem);
        CardView listitem1=new CardView("303","₹","The Secret","2.");
        list.add(listitem1);
        CardView listitem2=new CardView("215","₹","A Brief History Of Time ","3.");
        list.add(listitem2);
        CardView listitem3=new CardView("125","₹","Relativity","4.");
        list.add(listitem3);

        adapter=new MyAdapter(list,this);
        recyclerView.setAdapter(adapter);

//*************************Bottom Sheet Code**********************************************************//
        up=(ImageButton) findViewById(R.id.scrollIndicatorUp) ;
        down=(ImageButton) findViewById(R.id.scrollIndicatorDown) ;

        View bottomsheet= findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomsheet);

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {

                if( i==BottomSheetBehavior.STATE_EXPANDED || i==BottomSheetBehavior.STATE_HALF_EXPANDED)
                {
                    up.setVisibility(ImageButton.INVISIBLE);
                    down.setVisibility(ImageButton.VISIBLE);
                }
                else{
                    up.setVisibility(ImageButton.VISIBLE);
                    down.setVisibility(ImageButton.INVISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });


//********************************************Location using FusedLocationAPI ***************************//

        requestPermission();
        lat=(TextView) findViewById(R.id.lat);
        lon=(TextView) findViewById(R.id.lon);
        loc=(ImageButton) findViewById(R.id.loc_button);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

                return;
                }
                fusedLocationProviderClient.getLastLocation()
                        .addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {

                                if(location!=null){

                                    latitude=location.getLatitude();
                                    longitude=location.getLongitude();

                                   //calling the map to get the current location which is provided by the FusedLocationAPI
                                    onMapReady(mMap);




                                    lat.setText(""+location.getLatitude());
                                    lon.setText(""+location.getLongitude());
                                }



                            }
                        });






            }
        });


    }


    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);

    }


//*******************************************Placing the coordinates into Map which was got from FusedLocationAPI*******//

int flag=0;

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if(marker!=null){
            marker.remove();
        }


        float zoomLevel;
        mMap = googleMap;



        // Latitude and Longitude are provided by the FusedLocationAPI
        LatLng cur = new LatLng(latitude, longitude);

            if(flag==0){
               zoomLevel = 0.0f;
                flag++;
                }

            else{
                MarkerOptions markerOptions=new MarkerOptions().position(cur).title("Current Location");
                marker=mMap.addMarker(markerOptions);
               zoomLevel = 16.0f;

                }

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cur,zoomLevel));
    }
}
