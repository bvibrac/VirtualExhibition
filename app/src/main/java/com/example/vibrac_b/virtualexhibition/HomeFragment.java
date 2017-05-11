package com.example.vibrac_b.virtualexhibition;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, RoutingListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private ProgressDialog progressDialog;
    private LatLng myPos;
    private LatLng sydney;
    private List<Polyline> polylines;
    private String apiResponse = "[\n" +
            "  {\n" +
            "    \"_id\": \"5912c69561c81abc5346d09a\",\n" +
            "    \"updatedAt\": \"2017-05-10T09:18:56.727Z\",\n" +
            "    \"createdAt\": \"2017-05-10T07:51:49.000Z\",\n" +
            "    \"name\": \"Exposition de test\",\n" +
            "    \"description\": \"Ceci est une exposition de test\",\n" +
            "    \"author\": \"590a9bfbbeec60be05553465\",\n" +
            "    \"longitude\": 39.9506,\n" +
            "    \"latitude\": 116.3386,\n" +
            "    \"__v\": 1,\n" +
            "    \"arts\": [\n" +
            "      {\n" +
            "        \"id\": \"5912b78eea35972a22aa09e8\",\n" +
            "        \"qr\": \"no qr\",\n" +
            "        \"_id\": \"5912db0061c81abc5346d0a1\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"5912b78eea35972a22aa09e8\",\n" +
            "        \"qr\": \"no qr\",\n" +
            "        \"_id\": \"5912db0061c81abc5346d0a1\"\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"_id\": \"5912d84861c81abc5346d09f\",\n" +
            "    \"updatedAt\": \"2017-05-10T09:07:20.000Z\",\n" +
            "    \"createdAt\": \"2017-05-10T09:07:20.000Z\",\n" +
            "    \"name\": \"Exposition de test\",\n" +
            "    \"description\": \"Ceci est une exposition de test\",\n" +
            "    \"author\": \"590a9bfbbeec60be05553465\",\n" +
            "    \"longitude\": 39.9506,\n" +
            "    \"latitude\": 116.3386,\n" +
            "    \"__v\": 0,\n" +
            "    \"arts\": [\n" +
            "      {\n" +
            "        \"id\": \"5912b78eea35972a22aa09e8\",\n" +
            "        \"qr\": \"no qr\",\n" +
            "        \"_id\": \"5912d84861c81abc5346d0a0\"\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "]";
    private static final int[] COLORS = new int[]{R.color.colorPrimaryDark,R.color.colorPrimary,R.color.wallet_primary_text_holo_light,R.color.colorAccent,R.color.primary_dark_material_light};
    private List<Marker> allpos = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null, false);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                myPos = new LatLng(location.getLatitude(),location.getLongitude());
                CameraUpdate center = CameraUpdateFactory.newLatLng(myPos);
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);

                mMap.moveCamera(center);
                mMap.animateCamera(zoom);
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
        });
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                3000, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        myPos = new LatLng(location.getLatitude(),location.getLongitude());
                        CameraUpdate center = CameraUpdateFactory.newLatLng(myPos);
                        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);

                        mMap.moveCamera(center);
                        mMap.animateCamera(zoom);

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
                });
        // Inflate the layout for this fragment
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        // Add a marker in Sydney and move the camera
        try {
            JSONArray ApiResponse = new JSONArray(apiResponse);
            for (int i = 0; i != ApiResponse.length(); i++){
                JSONObject jsonObject = ApiResponse.getJSONObject(i);
                Log.d("lat", Double.toString(jsonObject.getDouble("latitude")));
                Log.d("lng", Double.toString(jsonObject.getDouble("longitude")));
                allpos.add(mMap.addMarker(new MarkerOptions().position(new LatLng(jsonObject.getDouble("longitude"), jsonObject.getDouble("latitude"))).title(jsonObject.getString("name"))));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMap.setOnMarkerClickListener(this);
//        sydney = new LatLng(39.94, 116.3432);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Test"));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getTitle().equals("Test")){
            progressDialog = ProgressDialog.show(getContext(), "Please wait.",
                    "Fetching route information.", true);
            Routing routing = new Routing.Builder()
                    .travelMode(Routing.TravelMode.WALKING)
                    .withListener(this)
                    .waypoints(myPos, marker.getPosition())
                    .build();
            routing.execute();
            return true;
        }
        return true;
    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
        progressDialog.dismiss();
        CameraUpdate center = CameraUpdateFactory.newLatLng(myPos);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);

        mMap.moveCamera(center);

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i <route.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = mMap.addPolyline(polyOptions);
            polylines.add(polyline);

            Toast.makeText(getContext(),"Route "+ (i+1) +": distance - "+ route.get(i).getDistanceValue()+": duration - "+ route.get(i).getDurationValue(),Toast.LENGTH_SHORT).show();
        }

        // Start marker
        MarkerOptions options = new MarkerOptions();
        options.position(myPos);
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mMap.addMarker(options);

        // End marker
        options = new MarkerOptions();
        options.position(sydney);
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mMap.addMarker(options);

    }

    @Override
    public void onRoutingCancelled() {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingFailure(RouteException e) {
        progressDialog.dismiss();
        if(e != null) {
            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getContext(), "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
        }
    }

}
