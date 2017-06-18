package www.viru.Kart.La;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class Map_Expand_allShops extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //hello

    ArrayList<Double> latitude,longitude;
    ArrayList<String > name;

    String category_number="",result="";

    Double user_latitude,user_longitude;

    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map__expand_all_shops);

        b  = getIntent().getExtras();

        Intent intent = getIntent();

        latitude =  (ArrayList<Double>) intent.getExtras().getSerializable("latitude");
        longitude =  (ArrayList<Double>) intent.getExtras().getSerializable("longitude");
        name = intent.getStringArrayListExtra("name");
        category_number = intent.getStringExtra("category_number");

        user_latitude = b.getDouble("user_latitude");
        user_longitude = b.getDouble("user_longitude");

        result = b.getString("result");







        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        CameraUpdate point = CameraUpdateFactory.newLatLng(new LatLng(13.067439,80.23761));


// moves camera to coordinates
        mMap.moveCamera(point);
// animates camera to coordinates
        // mMap.animateCamera(point);

        int height = 100;
        int width = 100;
        BitmapDrawable bitmapdraw_user=(BitmapDrawable)getResources().getDrawable(R.drawable.humanicon);
        Bitmap b_user=bitmapdraw_user.getBitmap();
        Bitmap smallMarker_user = Bitmap.createScaledBitmap(b_user, width, height, false);

        Bitmap smallMarker_shop;

        if(category_number.equals("162"))
        {
            Toast.makeText(this, "yess auto", Toast.LENGTH_SHORT).show();
            BitmapDrawable bitmapdraw_shop = (BitmapDrawable) getResources().getDrawable(R.drawable.rickshawicon);
            Bitmap b_shop = bitmapdraw_shop.getBitmap();
            smallMarker_shop = Bitmap.createScaledBitmap(b_shop, width, height, false);
        }
        else
        {
            BitmapDrawable bitmapdraw_shop = (BitmapDrawable) getResources().getDrawable(R.drawable.shop_icon);
            Bitmap b_shop = bitmapdraw_shop.getBitmap();
            smallMarker_shop = Bitmap.createScaledBitmap(b_shop, width, height, false);
        }
        List<Marker> markers = new ArrayList<Marker>();


        if(user_latitude != 0.0) {

            LatLng user = new LatLng(user_latitude, user_longitude);
            Marker u = mMap.addMarker(new MarkerOptions().position(user).title("Your location").icon(BitmapDescriptorFactory.fromBitmap(smallMarker_user)));
            u.showInfoWindow();


            markers.add(u);
        }




            int n = latitude.size();



            LatLng temp = new LatLng(13.4868, 80.35345);

            for (int i = 0; i < n; i++) {
                temp = new LatLng(latitude.get(i), longitude.get(i));
                Marker m = mMap.addMarker(new MarkerOptions().position(temp).title(name.get(i)).icon(BitmapDescriptorFactory.fromBitmap(smallMarker_shop)));
                markers.add(m);

            }




        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker m : markers) {
            builder.include(m.getPosition());
        }
        /**initialize the padding for map boundary*/
        final int padding = 250;
        /**create the bounds from latlngBuilder to set into map camera*/
        final LatLngBounds bounds = builder.build();
        /**create the camera with bounds and padding to set into map*/

        /**call the map call back to know map is loaded or not*/
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                /**set animated zoom camera into map*/
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));

            }
        });
    }
}
