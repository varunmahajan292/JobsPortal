package com.example.infinityjobportal.ui.map;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infinityjobportal.GlobalStorage;
import com.example.infinityjobportal.MapFilterData;
import com.example.infinityjobportal.R;
import com.example.infinityjobportal.SearchViewMap;
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
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static android.content.Context.LOCATION_SERVICE;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    GoogleMap map;
    LatLng latLng;
    ImageView  back;
    SupportMapFragment home;

    SupportMapFragment mapFragment;
    SearchView searchView;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    Button btn_myLocation;
    double latitude;
    double longitude;
    Marker marker=null;
    double lattu;
    double longu;
    double lat;
    double lng;
    String result;
    String city;
    String street;
    SeekBar seekRadius;
    TextView textSeek;
    int radius;

    int flag = 0;
    String TAG="data is fetched";
    Button FloatingActionButton;
    private List streetList=new ArrayList<>();
    ArrayList<String> D=new ArrayList<>();
    ArrayList<String> E=new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        //  startActivity(new Intent(getContext(),SearchViewMap.class));

        searchView = root.findViewById(R.id.searchViewMap);
        seekRadius = (SeekBar) root.findViewById(R.id.seekRadius);
        textSeek = root.findViewById(R.id.textSeek);
        FloatingActionButton = (Button) root.findViewById(R.id.floatingActionButton);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        home=(SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.home);
        btn_myLocation = root.findViewById(R.id.btn_myLocation);

        // mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);


        seekRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                textSeek.setText(progress + "KM");
                radius = progress * 1000;
                map.clear();
                GlobalStorage.S.clear();
                GlobalStorage.T.clear();


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        btn_myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery("",false);
                searchView.clearFocus();
                GlobalStorage.S.clear();
                GlobalStorage.T.clear();


                flag = 0;
                locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]
                                    {Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_LOCATION_PERMISSION);


                } else {


                    LocationListener locationListener = new LocationListener() {
                        @Override
                        public void onLocationChanged(final Location location) {

                            if (flag == 0) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();

                                //drawMarker(latitude, longitude);


                                //get the location name from latitude and longitude
                                Geocoder geocoder = new Geocoder(getContext());
                                // Marker marker=null;
                                try {
                                    List<Address> addresses =
                                            geocoder.getFromLocation(latitude, longitude, 1);
                                    result = addresses.get(0).getAddressLine(0) + "," + addresses.get(0).getLocality() + "," + addresses.get(0).getCountryName();
                                    latLng = new LatLng(latitude, longitude);


                                    db.collection("Jobs").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            if (!queryDocumentSnapshots.isEmpty()) {
                                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                for (DocumentSnapshot d : list) {
                                                    CircleOptions circleOptions1 = new CircleOptions().center(latLng).radius(200).fillColor(Color.RED);
                                                    map.addCircle(circleOptions1);

                                                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                                                    //  CircleOptions circleOptions1 = new CircleOptions().center(latLng).radius(200).fillColor(Color.RED);
                                                    // map.addCircle(circleOptions1);

                                                    PostJobPojo p = d.toObject(PostJobPojo.class);
                                                    street = p.getStreetAddress();
                                                    streetList.add(street);


                                                    city = p.getCityAddress();


                                                    lat = p.getLatitude();


                                                    lng = p.getLongitude();


                                                    float[] results = new float[streetList.size()];


                                                    Location.distanceBetween(latitude, longitude, lat, lng, results);
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
                                                                                  //  GlobalStorage.S = D;


                                                                                }
                                                                            } else {
                                                                                Log.d(TAG, "Error getting documents: ", task.getException());
                                                                            }

                                                                        }
                                                                    });
                                                        }

                                                        FloatingActionButton.setOnClickListener(new View.OnClickListener() {
                                                            public void onClick(View v) {
                                                                Set<String>DWithoutDuplicates = new LinkedHashSet<String>(D);
                                                                D.clear();

                                                                D.addAll(DWithoutDuplicates);


                                                                   GlobalStorage.S=D;
                                                                Intent intent = new Intent(getContext(), MapFilterData.class);
                                                                startActivity(intent);


                                                            }
                                                        });


                                                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(newposition, 10));

                                                    }
                                                    map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                                        @Override
                                                        public boolean onMarkerClick(Marker marker) {

                                                            Toast.makeText(getContext(), "" + marker.getTag(), Toast.LENGTH_SHORT).show();
                                                            String start = result;
                                                            //location.toString();
                                                            //getAddressLine(0);
                                                            String dest = (String) marker.getTag();
                                                            displayTrack(start, dest);


                                                            return false;

                                                        }


                                                    });

                                                }
                                            }
                                        }

                                    });
                                                            /* if (marker != null) {
                                                                 marker.remove();
                                                                 marker = map.addMarker(new MarkerOptions().position(latLng).title(result));
                                                                 map.setMaxZoomPreference(20);
                                                                 map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.0f));
                                                             } else {
                                                                 marker = map.addMarker(new MarkerOptions().position(latLng).title(result));
                                                                 map.setMaxZoomPreference(20);
                                                                 map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.0f));
                                                             }*/


                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                map.clear();
                                GlobalStorage.S.clear();


                            }
                            flag = 1;

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
                    // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 12000, 0, locationListener);
                }


                //return false;
            }


        });





        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                GlobalStorage.T.clear();
                GlobalStorage.S.clear();
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;
                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(getContext());
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    final Address address = addressList.get(0);
                    // drawMarker(address.getLatitude(), address.getLongitude());

                    final LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());


                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));



                    db.collection("Jobs").
                            get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d : list) {
                                    CircleOptions circleOptions1 = new CircleOptions().center(latLng).radius(200).fillColor(Color.RED);
                                    map.addCircle(circleOptions1);
                                    GlobalStorage.S.clear();


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


                                                                    E.add(document.getId());











                                                                }
                                                            } else {
                                                                Log.d(TAG, "Error getting documents: ", task.getException());
                                                            }



                                                        }
                                                    });
                                        }


                                        FloatingActionButton.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                                Set<String> EWithoutDuplicates = new LinkedHashSet<String>(E);
                                                E.clear();

                                                E.addAll(EWithoutDuplicates);


                                                GlobalStorage.T= E;
                                                Intent intent = new Intent(getContext(), MapFilterData.class);
                                                startActivity(intent);


                                            }
                                        });


                                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(newposition, 10));

                                    }
                                    map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                        @Override
                                        public boolean onMarkerClick(Marker marker) {

                                            Toast.makeText(getContext(), "" + marker.getTag(), Toast.LENGTH_SHORT).show();
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
                GlobalStorage.T.clear();
                map.clear();
                return false;



            }


            @Override
            public boolean onQueryTextChange(String s) {
                map.clear();
                GlobalStorage.S.clear();
                GlobalStorage.T.clear();

                return false;
            }
        });





        // search view end


        mapFragment.getMapAsync(this);
        return root;

    }

    private void drawMarker(double latitude, double longitude) {

/*
       //get the location name from latitude and longitude
       Geocoder geocoder = new Geocoder(getContext());
       // Marker marker=null;
       try {
           List<Address> addresses =
                   geocoder.getFromLocation(latitude, longitude, 1);
           result=addresses.get(0).getAddressLine(0)+","+addresses.get(0).getLocality() + ","+ addresses.get(0).getCountryName();
           latLng = new LatLng(latitude, longitude);


           db.collection("Jobs").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
               @Override
               public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                   if (!queryDocumentSnapshots.isEmpty()) {
                       List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                       for (DocumentSnapshot d : list) {
                           CircleOptions circleOptions1 = new CircleOptions().center(latLng).radius(200).fillColor(Color.RED);
                           map.addCircle(circleOptions1);

                           map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                           //  CircleOptions circleOptions1 = new CircleOptions().center(latLng).radius(200).fillColor(Color.RED);
                           // map.addCircle(circleOptions1);

                           PostJobPojo p = d.toObject(PostJobPojo.class);
                           street = p.getStreetAddress();
                           streetList.add(street);


                           city = p.getCityAddress();


                           lat = p.getLatitude();


                           lng = p.getLongitude();


                           float[] results = new float[streetList.size()];


                           Location.distanceBetween(latitude, longitude,  lat, lng, results);
                           final LatLng newposition = new LatLng(lat,lng);
                           float distance = results[0];
                           // int kilometer = (int) (distance / 1000);
                           if (distance < radius) {

                               MarkerOptions markerOptions1 = new MarkerOptions().position(newposition).title("Destination Location");
                               final Marker  marker= map.addMarker(markerOptions1);
                               marker.setTag(street + "," + city);
                               lattu=marker.getPosition().latitude;
                               longu=marker.getPosition().longitude;



                               if (lattu==lat& longu==lng) {
                                   db.collection("Jobs").whereEqualTo("latitude", lattu).whereEqualTo("longitude", longu).get()
                                           .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                               @Override
                                               public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                   if (task.isSuccessful()) {
                                                       for (final QueryDocumentSnapshot document : task.getResult()) {
                                                           Log.d(TAG, document.getId() + " => " + document.getData());


                                                           D.add(document.getId());
                                                           GlobalStorage.S=D;



                                                       }
                                                   } else {
                                                       Log.d(TAG, "Error getting documents: ", task.getException());
                                                   }

                                               }
                                           });
                               }

                               FloatingActionButton.setOnClickListener(new View.OnClickListener() {
                                   public void onClick(View v) {

                                       //   GlobalStorage.S=D;
                                       Intent intent = new Intent(getContext(), MapFilterData.class);
                                       startActivity(intent);



                                   }
                               });









                               map.animateCamera(CameraUpdateFactory.newLatLngZoom(newposition, 10));

                           }
                           map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                               @Override
                               public boolean onMarkerClick(Marker marker) {

                                   Toast.makeText(getContext(), ""+marker.getTag(), Toast.LENGTH_SHORT).show();
                                   String start =  result;
                                   //location.toString();
                                   //getAddressLine(0);
                                   String dest = (String) marker.getTag();
                                   displayTrack(start, dest);



                                   return false;

                               }


                           });

                       }
                   }
               }

           });
                                                            /* if (marker != null) {
                                                                 marker.remove();
                                                                 marker = map.addMarker(new MarkerOptions().position(latLng).title(result));
                                                                 map.setMaxZoomPreference(20);
                                                                 map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.0f));
                                                             } else {
                                                                 marker = map.addMarker(new MarkerOptions().position(latLng).title(result));
                                                                 map.setMaxZoomPreference(20);
                                                                 map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.0f));
                                                             }




       } catch (IOException e) {
           e.printStackTrace();
       }
       map.clear();
       GlobalStorage.S.clear();
       */
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        map=googleMap;



    }

    private void displayTrack(String startpoint, String endpoint) {
        try {
            Uri uri= Uri.parse("https://www.google.co.in/maps/dir/"+startpoint+"/"+endpoint);
            Intent intent=new Intent(Intent.ACTION_VIEW,uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        catch(ActivityNotFoundException e) {
            Uri uri=Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent=new Intent(Intent.ACTION_VIEW,uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


    }



}

