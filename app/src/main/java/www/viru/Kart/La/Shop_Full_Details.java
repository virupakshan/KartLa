package www.viru.Kart.La;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Shop_Full_Details extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    TextView shop_title;
    TextView shop_address;
    ImageView shop_image;
    Button call_button;
    Bundle bundle;
    String shop_paer,img_url;
    String category_number;
    Double shop_lat,shop_lng,user_lat,user_lng;

    Button get_direction,map_full_view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop__full__details);
        shop_title = (TextView) findViewById(R.id.shopname);
        shop_address = (TextView) findViewById(R.id.Individual_address);
        shop_image = (ImageView) findViewById(R.id.Individual_image);
         call_button = (Button) findViewById(R.id.Call_button);
        get_direction = (Button) findViewById(R.id.get_direction);
        map_full_view = (Button) findViewById(R.id.full_view);


        bundle  = getIntent().getExtras();

                                                //for get direction
   //     Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW,
//                Uri.parse("http://maps.google.com/maps?daddr=13.1143167,80.14805509999997"));
//        if (mapIntent.resolveActivity(getPackageManager()) != null) {
//            startActivity(mapIntent);
//        }


                // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        shop_paer = bundle.getString("shop_name");
        img_url = bundle.getString("image_url");
       shop_lat = bundle.getDouble("latitude");
        shop_lng = bundle.getDouble("longitude");

        user_lat = bundle.getDouble("user_latitude");
        user_lng = bundle.getDouble("user_longitude");

        category_number = bundle.getString("category_number");

        get_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


                //get direction with just end location, starts map with route from your current location to the destiantion

                       /* Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="+shop_lat+","+shop_lng+"\""));

                                if (mapIntent.resolveActivity(getPackageManager()) != null)
                                        {
                                               startActivity(mapIntent);
                                         }

                         */

                       //this one is with two latlng, start and end- route from start to end

                String uri = "http://maps.google.com/maps?f=d&hl=en&saddr="+user_lat+","+user_lng+"&daddr="+shop_lat+","+shop_lng;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);


            }
        });



        shop_address.setText("Address : "+ bundle.getString("address"));

        Picasso.with(Shop_Full_Details.this).load(img_url).into(shop_image);

        String number = bundle.getString("contact");

         final String c = number.substring(0,3)+"-"+number.substring(3,6)+"-"+number.substring(6,9)+"-"+number.substring(9);

        call_button.setText("CALL - "+ number);

       call_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",c, null));
                startActivity(intent);
              //  Toast.makeText(Shop_Full_Details.this, "call pressed", Toast.LENGTH_SHORT).show();
            }
        });




        //lat = Double.parseDouble (bundle.getString("latitude"));
       // lng = Double.parseDouble (bundle.getString("longitude"));

     //  Toast.makeText(this, ""+lat+","+lng, Toast.LENGTH_SHORT).show();

            map_full_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent mapIntent = new Intent(Shop_Full_Details.this,Full_Map_View.class);

                    Bundle b = new Bundle();
                    b.putDouble("shop_lat",shop_lat);
                    b.putDouble("shop_lng",shop_lng);
                    b.putDouble("user_lat",user_lat);
                    b.putDouble("user_lng",user_lng);
                    mapIntent.putExtra("shop_name",shop_paer);
                    mapIntent.putExtra("category_number",category_number);

                    mapIntent.putExtras(b);





                    startActivity(mapIntent);

                }
            });



            if(shop_paer.length() > 35)
            {
                shop_title.setText(shop_paer);
                shop_title.setTextSize(16);

            }
            else
            {
                shop_title.setText(shop_paer);


            }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
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
       Marker shop_marker = mMap.addMarker(new MarkerOptions().position(shop).title(shop_paer).icon(BitmapDescriptorFactory.fromBitmap(smallMarker_shop)));





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
