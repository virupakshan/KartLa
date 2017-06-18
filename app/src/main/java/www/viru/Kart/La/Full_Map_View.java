package www.viru.Kart.La;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import www.viru.Kart.La.R;
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

public class Full_Map_View extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    Double shop_lat,shop_lng,user_lat,user_lng;

    String shop_title ="";
    String category_number ="";

    Button get_direction;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full__map__view);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        bundle = getIntent().getExtras();

        shop_lat = bundle.getDouble("shop_lat");
        shop_lng= bundle.getDouble("shop_lng");
        user_lat = bundle.getDouble("user_lat");
        user_lng = bundle.getDouble("user_lng");

        shop_title = bundle.getString("shop_name");

        category_number = bundle.getString("category_number");

        get_direction = (Button) findViewById(R.id.get_direction);


       get_direction.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               String uri = "http://maps.google.com/maps?f=d&hl=en&saddr="+user_lat+","+user_lng+"&daddr="+shop_lat+","+shop_lng;
               Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
               startActivity(intent);

           }
       });






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
        List<Marker> markers = new ArrayList<Marker>();

        // Add a marker in Sydney and move the camera





        LatLng shop = new LatLng(shop_lat,shop_lng);
        int height = 100;
        int width = 100;


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
        BitmapDrawable bitmapdraw_user=(BitmapDrawable)getResources().getDrawable(R.drawable.humanicon);
        Bitmap b_user=bitmapdraw_user.getBitmap();
        Bitmap smallMarker_user = Bitmap.createScaledBitmap(b_user, width, height, false);

        if(user_lat != 0.0)
        {

            LatLng user = new LatLng(user_lat, user_lng);
            Marker user_marker = mMap.addMarker(new MarkerOptions().position(user).title("Your location").icon(BitmapDescriptorFactory.fromBitmap(smallMarker_user)));
            user_marker.showInfoWindow();
            markers.add(user_marker);
        }
        Marker shop_marker = mMap.addMarker(new MarkerOptions().position(shop).title(shop_title).icon(BitmapDescriptorFactory.fromBitmap(smallMarker_shop)));





        markers.add(shop_marker);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker m : markers) {
            builder.include(m.getPosition());
        }
        /**initialize the padding for map boundary*/
        final int padding = 150;
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

        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(shop,12));


    }
}
