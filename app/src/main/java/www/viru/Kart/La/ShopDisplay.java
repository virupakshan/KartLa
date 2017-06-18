package www.viru.Kart.La;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ShopDisplay extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String html;
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> contact = new ArrayList<String>();
    ArrayList<String> address = new ArrayList<String>();
    ArrayList<Bitmap> images = new ArrayList<Bitmap>();
   public ArrayList<Double> latitude = new ArrayList<Double>();
   public ArrayList<Double> longitude = new ArrayList<Double>();
    ArrayList<String>  image_urls = new ArrayList<String>();
    ArrayList<String>  distance_km = new ArrayList<String>();

    TextView titlebar;

    Double user_latitude,user_longitude;

    String url = null;

    String user_area ="";

    String distance = "";

    Bundle bundle;

    int result =0 ;

    String category_number = "";

    String shop_category = "";

    String shop_name= "";
    String number = "";

    String get_specific = null;

    ImageView back_button,map_expand;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_display);

        titlebar = (TextView) findViewById(R.id.titlebar);

        back_button = (ImageView) findViewById(R.id.back_button);

        map_expand = (ImageView) findViewById(R.id.map_expand);


        bundle  = getIntent().getExtras();

        user_latitude = bundle.getDouble("latitude");
        user_longitude = bundle.getDouble("longitude");

        category_number = bundle.getString("category_number");



        user_area = bundle.getString("user_area");

        distance = bundle.getString("distance");

        shop_category = bundle.getString("shop_category");

        get_specific = bundle.getString("get_specific");


        if(get_specific.length() > 0 && user_area.length() == 0)
        {



            url = "https://kart.la/search-results/?gmw_keywords="+get_specific+"&gmw_address%5B0%5D=chennai&gmw_post=post&gmw_distance=100&gmw_units=metric&gmw_form=2&gmw_per_page=10&gmw_lat=13.0826802&gmw_lng=80.27071840000008&gmw_px=pt&action=gmw_post";

        }
        else if(get_specific.length() > 0 && user_area.length() > 0 && distance == "1")
        {
            url = "https://kart.la/search-results/?gmw_keywords="+get_specific+"&gmw_address%5B0%5D=chennai&gmw_post=post&gmw_distance=100&gmw_units=metric&" +
                    "gmw_form=2&gmw_per_page=10&gmw_lat="+ user_latitude +"&gmw_lng="+ user_longitude +"&gmw_px=pt&action=gmw_post";

        }
        else if(get_specific.length() > 0 && user_area.length() > 0 && distance != "1" )
        {
            url = "https://kart.la/search-results/?gmw_keywords="+get_specific+"&gmw_address%5B0%5D=chennai&gmw_post=post&gmw_distance="+distance+"&gmw_units=metric&" +
                    "gmw_form=2&gmw_per_page=10&gmw_lat="+ user_latitude +"&gmw_lng="+ user_longitude +"&gmw_px=pt&action=gmw_post";

        }
        else
        {
            url = "https://kart.la/search-results/?gmw_keywords&gmw_address%5B0%5D=" + "Nanganallur" +
                    "%2C%20Chennai%2C%20Tamil%20Nadu&gmw_post=post&tax_category%5B0%5D=" + category_number +
                    "&gmw_distance=" + distance + "&gmw_units=metric&gmw_form=2&gmw_per_page=10&gmw_lat=" + user_latitude + "&gmw_lng=" + user_longitude + "&gmw_px=pt&action=gmw_post";

        }


        try {
            Ion.with(ShopDisplay.this)
                    .load(url)
                    .asString().setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                    html = result;
                    Log.d("Html code",html);

                }
            }).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Sorry something went wrong", Toast.LENGTH_SHORT).show();
        }


       //BackendProcess bp =new BackendProcess();
        //bp.methodfor();
        BackendProcess bp =new BackendProcess();
        bp.methodfor();


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ShopDisplay.this,MainActivity.class);

                startActivity(intent);

                           }
        });

        map_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle b = new Bundle();

                Intent mapIntent = new Intent(ShopDisplay.this,Map_Expand_allShops.class);



                mapIntent.putExtra("latitude",latitude);
                mapIntent.putExtra("longitude",longitude);
                mapIntent.putStringArrayListExtra("name",name);
                mapIntent.putExtra("category_number",category_number);

                mapIntent.putExtra("result",result);



                b.putDouble("user_latitude", user_latitude);
                b.putDouble("user_longitude", user_longitude);
                mapIntent.putExtras(b);




              //  mapIntent.putStringArrayListExtra(latitude);



                startActivity(mapIntent);

            }
        });

    }

    public class BackendProcess {
        void methodfor() {
            try {
                int index1 = 0, index2 = 0, i = 0;
                String imgurl = "";
                if (html.indexOf("<!-- Title -->") != -1) {

                    result = 1;
                    int k = 0;

                    while (html.indexOf("<!-- Title -->", index1) != -1 && k < 15)

                    {
                        k++;
                        i++;
                        index1 += 10;

                        Log.d("Starting index value", "" + index1);
                        index1 = html.indexOf("<!-- Title -->", index1);
                        Log.d("index value1", "" + index1);
                        index1 = html.indexOf(")", index1);
                        Log.d("index value2", "" + index1);
                        index2 = html.indexOf("(", index1);

                        //getting shop name
                        shop_name = html.substring(index1 + 1, index2).trim();




                        Log.d("shop name", html.substring(index1 + 1, index2));


                        index1 = index2 + 1;
                        Log.d("index value3", "" + index1);
                        index2 = html.indexOf(")", index1);

                        //getting contact number
                        number = html.substring(index1, index2).trim();



                        if (shop_name.contains("</a>")) {
                            shop_name = shop_name.trim().substring(0, 10);
                            number = shop_name;
                            shop_name = "Unknown";
                            index1 = index1 - 7;
                            index2 = index2 - 7;
                        }

                        if(shop_name.contains("<br />"))
                        {
                            int i1 = shop_name.indexOf("<br />");

                            shop_name = shop_name.substring(0,i1);
                        }

                        // for shops whose contact number is not correct
                        try
                        {
                            long to_check_number = Long.parseLong(number);
                        }
                        catch (Exception e)
                        {

                            if(shop_name.contains("&amp;"))
                            {

                                int i1 = shop_name.indexOf("&amp;");
                                String t = shop_name.substring(0,i1);
                                i1+= 5;
                                t = t+" & "+shop_name.substring(i1);

                                shop_name = t;


                            }
                            if(number.contains("&amp;"))
                            {

                                int i1 = number.indexOf("&amp;");
                                String t = number.substring(0,i1);
                                i1+= 5;
                                t = t+" & "+number.substring(i1);

                                number = t;


                            }

                            index1 = html.indexOf("(",index2);
                            index2 = html.indexOf(")",index1);

                            shop_name = shop_name+"("+number+")";

                            number = html.substring(index1+1,index2);

                        }
                        if(shop_name.contains("&amp;"))
                        {

                            int i1 = shop_name.indexOf("&amp;");
                            String t = shop_name.substring(0,i1);
                            i1+= 5;
                            t = t+" & "+shop_name.substring(i1);

                            shop_name = t;


                        }

                        contact.add(number);
                        name.add(shop_name);

                        index1 = html.indexOf(">(", index2);
                        index1 = index1 + 2;


                        index2 = html.indexOf("km)", index1);
                        String temp = html.substring(index1, index2);


                        if (temp.contains("<!DOCTYPE html>")) {
                            temp = "16.6";
                            k = 20;
                        }



                        distance_km.add(temp + "km");





                        index1 = html.indexOf("src=\"", index2);
                        index1 += 5;

                        index2 = html.indexOf("\"", index1);



                        imgurl = html.substring(index1, index2);

                        if(imgurl.contains("-150x150"))
                        {
                            String tp = "";
                            int t1=0;
                            t1 = imgurl.indexOf("-150x150");
                            tp = imgurl.substring(0,t1);
                            t1+=8;

                            imgurl = tp+imgurl.substring(t1);



                        }


                        image_urls.add(imgurl);



                        Log.d("image url", html.substring(index1, index2));

                        index1 = html.indexOf("address\">", index2);
                        index1 = index1 + 9;
                        index2 = html.indexOf("</div>", index1);
                        final String addr = html.substring(index1, index2).trim();

                        Log.d("Address", html.substring(index1, index2).trim());

                        address.add(addr);



                        index1 = index2 + 1;
                        index2 = index2 + 2;

                        index1 = html.indexOf("var end = new google.maps.LatLng", index2);
                        index1 = html.indexOf("LatLng('", index1);

                        index1 = index1 + 8;

                        index2 = html.indexOf("',", index1);

                        String lat = html.substring(index1, index2);

                        double d = Double.parseDouble(lat);
                        latitude.add(d);

                        //getting longitude

                        index2 += 2;

                        index1 = html.indexOf("'", index2);
                        index1 = index1 + 1;

                        index2 = html.indexOf("'", index1);

                        String lon = html.substring(index1, index2);

                        d = Double.parseDouble(lon);

                        longitude.add(d);

                        Log.d("Coordinates", "Latitude :" + lat + "Longitude : " + lon);



                    }

                    int n = name.size();


                    String r = "";
                    if (n == 1) {
                        r = "Result";
                    } else {
                        r = "Results";
                    }




                    String titlebar_temp = "";

                    if(get_specific.length() == 0) {
                       titlebar_temp = n + " " + r + " Found for " + shop_category + " In \n" + user_area + " Within " + distance + " km";
                    }
                    else if(get_specific.length() > 0 && user_area.length() == 0 )
                    {
                        titlebar_temp = n + " " + r + " Found for " + get_specific ;
                    }
                    else if(get_specific.length() > 0 && user_area.length() > 0 && distance == "1")
                    {
                        titlebar_temp = n + " " + r + " Found for " + get_specific +" In \n" + user_area + " Within " + distance + " km";
                    }
                    else if(get_specific.length() > 0 && user_area.length() > 0 && distance != "1")
                    {
                        titlebar_temp = n + " " + r + " Found for " + get_specific +" In \n" + user_area + " Within " + distance + " km";
                    }

                    titlebar.setText(titlebar_temp);
                    if(titlebar.length() > 70)
                    {
                       titlebar.setTextSize(13);
                    }


                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(ShopDisplay.this);


                    ListView myListView = (ListView) findViewById(R.id.list);

                    CustomList adaptor = new CustomList(ShopDisplay.this, name, image_urls, address, contact, distance_km);

                    myListView.setAdapter(adaptor);
                    myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {

                            Intent intent = new Intent(ShopDisplay.this, Shop_Full_Details.class);
                            intent.putExtra("shop_name", name.get(position));
                            intent.putExtra("address", address.get(position));
                            intent.putExtra("contact", contact.get(position));
                            intent.putExtra("image_url", image_urls.get(position));
                            intent.putExtra("category_number", category_number);
                            // intent.putExtra("latitude",latitude.get(position));
                            // intent.putExtra("longitude",longitude.get(position));
                            Bundle b = new Bundle();
                            b.putDouble("latitude", latitude.get(position));
                            b.putDouble("longitude", longitude.get(position));
                            b.putDouble("user_latitude", user_latitude);
                            b.putDouble("user_longitude", user_longitude);
                            intent.putExtras(b);
                            startActivity(intent);


                        }
                    });



                } else {
                    Toast.makeText(ShopDisplay.this, "Sorry no results found!!, We are working on it", Toast.LENGTH_SHORT).show();
                    if(shop_category.length() == 0)
                    {
                        titlebar.setText("No results for "+get_specific);
                    }
                    if(shop_category != null)
                    {
                        titlebar.setText("No results for " + shop_category);
                    }



                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(ShopDisplay.this);
                }

            }
            catch (Exception e)
            {
                Toast.makeText(ShopDisplay.this, "Sorry some error, try again "+e, Toast.LENGTH_SHORT).show();
            }
        }

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

       if(result==1)
        {

            int n = latitude.size();



            LatLng temp = new LatLng(13.4868, 80.35345);

            for (int i = 0; i < n; i++) {
                temp = new LatLng(latitude.get(i), longitude.get(i));
                Marker m = mMap.addMarker(new MarkerOptions().position(temp).title(name.get(i)).icon(BitmapDescriptorFactory.fromBitmap(smallMarker_shop)));
                markers.add(m);

            }


        }
if(result ==1)
{
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

}

    }
}
