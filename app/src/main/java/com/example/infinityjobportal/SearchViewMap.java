package com.example.infinityjobportal;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.infinityjobportal.model.PostJobPojo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class SearchViewMap extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap map;
    ImageView back;
    SupportMapFragment mapFragment;
    SearchView searchView;

    double lattu;
    double longu;
    double lat;
    double lng;
    String city;
    String street;
    SeekBar seekRadius;
    TextView textSeek;
    int radius;
    String TAG = "data is fetched";
    FloatingActionButton FloatingActionButton;
    private List streetList = new ArrayList<>();
    ArrayList<String> D = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    //  LocationManager locationManager;
    // private static final int REQUEST_LOCATION_PERMISSION = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view_map);

        searchView = findViewById(R.id.searchViewMap);
        seekRadius = (SeekBar) findViewById(R.id.seekRadius);
        textSeek = findViewById(R.id.textSeek);
        back = findViewById(R.id.back);
        FloatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        seekRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                textSeek.setText(progress + "KM");
                radius = progress * 1000;
                map.clear();
                GlobalStorage.S.clear();


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;
                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(SearchViewMap.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    final Address address = addressList.get(0);
                    final LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());


                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));


                    db.collection("Jobs").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d : list) {
                                    CircleOptions circleOptions1 = new CircleOptions().center(latLng).radius(200).fillColor(Color.RED);
                                    map.addCircle(circleOptions1);

                                    PostJobPojo p = d.toObject(PostJobPojo.class);
                                    street = p.getStreetAddress();
                                    streetList.add(street);


                                    city = p.getCityAddress();


                                    lat = p.getLatitude();


                                    lng = p.getLongitude();


                                    float[] results = new float[streetList.size()];


                                    Location.distanceBetween(address.getLatitude(), address.getLongitude(), lat, lng, results);
                                    final LatLng newposition = new LatLng(lat, lng);
                                    float distance = results[0];
                                    // int kilometer = (int) (distance / 1000);
                                    if (distance < radius) {

                                        MarkerOptions markerOptions1 = new MarkerOptions().position(newposition).title("Destination Location");
                                        final Marker marker = map.addMarker(markerOptions1);
                                        marker.setTag(street + "," + city);
                                        lattu = marker.getPosition().latitude;
                                        longu = marker.getPosition().longitude;


                                        if (lattu == lat & longu == lng) {
                                            db.collection("Jobs").whereEqualTo("latitude", lattu).whereEqualTo("longitude", longu).get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                for (final QueryDocumentSnapshot document : task.getResult()) {
                                                                    Log.d(TAG, document.getId() + " => " + document.getData());


                                                                    D.add(document.getId());


                                                                }
                                                            } else {
                                                                Log.d(TAG, "Error getting documents: ", task.getException());
                                                            }

                                                        }
                                                    });
                                        }

                                        FloatingActionButton.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {

                                                GlobalStorage.S = D;
                                                Intent intent = new Intent(SearchViewMap.this, MapFilterData.class);
                                                startActivity(intent);


                                            }
                                        });


                                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(newposition, 10));

                                    }
                                    map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                        @Override
                                        public boolean onMarkerClick(Marker marker) {

                                            Toast.makeText(SearchViewMap.this, "" + marker.getTag(), Toast.LENGTH_SHORT).show();
                                            String start = address.getAddressLine(0);
                                            String dest = (String) marker.getTag();
                                            displayTrack(start, dest);


                                            return false;

                                        }


                                    });

                                }
                            }
                        }

                    });


                    /////////////////////////////
                }

                map.clear();
                return false;


            }


            @Override
            public boolean onQueryTextChange(String s) {
                map.clear();
                GlobalStorage.S.clear();
                return false;
            }
        });
        // search view end


        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;


    }

    private void displayTrack(String startpoint, String endpoint) {
        try {
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + startpoint + "/" + endpoint);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


    }


}