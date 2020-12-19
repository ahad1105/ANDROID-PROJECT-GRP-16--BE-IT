package com.example.myapplication1;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class fragment_map extends Fragment implements OnMapReadyCallback {
    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;

    FirebaseDatabase database;
    DatabaseReference myref;
    ArrayList<Firebase> s=new ArrayList<>();
    ArrayAdapter<Firebase> ad;
    ArrayList<String> k =new ArrayList<>();

    public fragment_map(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    Double lat, lon;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_map, container, false);

        database=FirebaseDatabase.getInstance();
        myref=database.getReference("Firebase");

        Toast.makeText(getActivity(), lat + "\n" + lon, Toast.LENGTH_SHORT).show();

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) mView.findViewById(R.id.mapView);


        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }
    public void onMapReady(final GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.clear();

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                s.clear();
                for(DataSnapshot d:dataSnapshot.getChildren())
                {
                    Firebase data=d.getValue(Firebase.class);
                    s.add(data);
                    k.add(d.getKey());
                    for (int i=0;i<s.size();i++){
                        String[] t = s.get(i).toString().split(":");
                        googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(t[0]),Double.parseDouble(t[1]))).title(t[2]));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        CameraPosition Liberty = CameraPosition.builder().target(new LatLng(lat, lon)).zoom(16).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty  ));
    }
}

