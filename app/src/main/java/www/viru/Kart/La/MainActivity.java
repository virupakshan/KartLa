package www.viru.Kart.La;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    int y =0;
    ProgressDialog gpsprogress;
    String latlng ="";
    Double lat = 0.0;
    Double lng = 0.0;
    Bundle bundle;

    String area = "";

    TextView address_display;

    ImageButton get_location,getspecific_clear,go_btn;
    Button about_us;
    Button submit;

    EditText get_specific;
    String get_specific_string  ="";

    int location_update = 0;

    Spinner km ;
    String distance ="";

    //GPSTracker gps;

    Location lastknownlocation = null;

    AutoCompleteTextView category_at;
    String shop_category = "";
    String category_number = "0";

    ImageButton ACTV_clear_button;

    Toast location_toast = null;


    String[] category_list={"Advocate","Agency & A-to-Z Services","Apparels, Readymades & Textiles","Architects","Auto Stand","Automobile Shop",
            "Bags","Bakery, Sweets and Snacks","Beauty Parlour and Spa","Bedding","Biriyani","Book Shop","Browsing Center","Builders",
            "Building Materials","Butcher Shop","Call Taxi & Travels","Car Wash","Carpenter","Catering","Coconut Shop","Coffee & Tea Shop","Coffee Powder"
            ,"Computer Sales & Service","Corporate Gifts","Country Drugs","Courier","Dental Clinic","Diagnostic Lab","Driving School",
            "Electricals & Electronics","Electrician & Plumber","Engineering Works","Event Items Rentals","Fancy Store","Finance","Flour Mill & Rice Mill",
            "Flower Shop","Footwear","Fruit Shop","Furniture Rental","Furnitures","General Repair Shop","Glass and Plywoods","Gold Covering","Grocery, Provisions & General Store"
            ,"Guest House","Gym","Helmets & Seat Covers","Home Appliances","Hospitals and Clinics","Hotel, Restaurant & Fast Food","Idly Dosa Batter Shop",
            "Insect Screens","Interior Decoration","Jewelry Shop","Juice, Cool Drinks & Ice cream","Laundry, Ironing & Dry Cleaners",
            "Library","LIC & Insurance","Lock Smith","Magazine & Newspaper Mart","Mandapam","Medical & Drugs Shop","Milk Center","Mobile Recharge & Accessories",
            "Mobile Showroom","Modular Kitchen","Musical Instruments","Nursery","Office","Oil Store","Optical Shop","Organic Shop","Others","Pet Shop","Photo Studio, Videography & Colour Lab",
            "Photos & Frames Shop","Plastics Store","Play School","Pooja Materials","Printing, Offsetting and Binding","Puncture Shop","Real Estate","Rice Shop",
            "Roofings","Saloon","Shoe, Bag and Tailoring Materials","Sound Service","Sports","Stationery Shop","Sticker Shop","Super Market","Tailor","Tax Consultant",
            "Temples","test1", "Tiles Shop","Timber Shop", "Tinkering","Two Wheeler Dealers", "Two Wheeler Service","Tyre", "UPS & Inverters","Vegetable Shop",
            "Vessels & Utensils","Waste Paper Mart", "Watch and Clocks","Wedding Cards","Welding Shop","Wood Works","Xerox","Yoga and Meditation"};

    HashMap<String,Integer> category;

    //for Internet connection
    ConnectivityManager conMgr;
    NetworkInfo netInfo;




//for location
    LocationManager locationManager ;
    LocationListener locationListener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {

            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
            {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        get_specific = (EditText) findViewById(R.id.get_specific);
        getspecific_clear = (ImageButton) findViewById(R.id.getspecificclear);
        go_btn = (ImageButton) findViewById(R.id.go_button);

        //category HashMap

        category  = new HashMap<String,Integer>();
        category.put("Advocate", 245);
        category.put("Agency & A-to-Z Services", 112);
        category.put("Apparels, Readymades, & Textiles",31);
        category.put("Architects",66);
        category.put("Auto Stand", 162);
        category.put("Automobile Shop", 38);
        category.put("Baby Shop", 36);
        category.put("Bags", 107);
        category.put("Bakery, Sweets and Snacks", 15);
        category.put("Beauty Parlour and Spa",232);
        category.put("Bedding", 189);
        category.put("Biriyani", 235);
        category.put("Book Shop", 27);
        category.put("Browsing Cente",99);
        category.put("Builders", 67);
        category.put("Building Materials",123);
        category.put("Butcher Shop",102);
        category.put("Call Taxi & Travels", 113);
        category.put("Car Wash", 246);
        category.put("Carpenter", 70);
        category.put("Catering", 100);
        category.put("Coconut Shop", 153);
        category.put("Coffee & Tea Shop", 17);
        category.put("Coffee Powder", 250);
        category.put("Computer Sales & Service", 224);
        category.put("Corporate Gifts", 257);
        category.put("Country Drugs", 228);
        category.put("Courier", 199);
        category.put("Cycle Sales & Repair", 209);
        category.put("Corporate Gifts", 257);
        category.put("Country Drugs", 228);
        category.put("Courier", 199);

        category.put("Dental Clinic", 229);
        category.put("Diagnostic Lab", 240);
        category.put("Driving School", 111);
        category.put("Electricals & Electronics", 8);
        category.put("Electrician & Plumber", 68);
        category.put("Engineering Works", 143);
        category.put("Event Items Rentals", 172);
        category.put("Fancy Store",16 );
        category.put("Finance",279 );
        category.put("Flour Mill & Rice Mill", 72);
        category.put("Flower Shop",58 );
        category.put("Footwear",33 );
        category.put("Fruit Shop",23 );
        category.put("Furniture Rental",171 );
        category.put("Furnitures", 45);
        category.put("General Repair Shop", 134);
        category.put("Glass and Plywoods",138 );
        category.put("Gold Covering",251 );
        category.put("Grocery, Provisions & General Store",124 );
        category.put("Guest House",248 );
        category.put("Gym",51 );
        category.put("Helmets & Seat Covers",7 );
        category.put("Home Appliances",212 );
        category.put("Hospitals and Clinics",230 );
        category.put("Hotel, Restaurant & Fast Food",101 );
        category.put("Idly Dosa Batter Shop",155 );
        category.put("Insect Screens",121 );
        category.put("Interior Decoration",115 );
        category.put("Jewelry Shop",35 );
        category.put("Juice, Cool Drinks & Ice cream",105 );
        category.put("Laundry, Ironing & Dry Cleaners", 262);
        category.put("Library", 268);
        category.put("LIC & Insurance",157 );
        category.put("Lock Smith",108 );
        category.put("Magazine & Newspaper Mart",28 );
        category.put("Mandapam",60 );
        category.put("Medical & Drugs Shop",24 );
        category.put("Milk Center",152 );
        category.put("Mobile Recharge & Accessories",163 );
        category.put("Mobile Showroom",130 );
        category.put("Modular Kitchen",201 );

        category.put("Musical Instruments",59 );
        category.put("Nursery", 135);
        category.put("Office",219 );
        category.put("Oil Store",103 );
        category.put("Optical Shop",34 );
        category.put("Organic Shop",46 );
        category.put("Others",65 );
        category.put("Pet Shop",43 );
        category.put("Photo Studio, Videography & Colour Lab",143 );
        category.put("Photos & Frames Shop",173 );
        category.put("Plastics Store",181 );
        category.put("Play School",63 );
        category.put("Pooja Materials",227 );
        category.put("Printing, Offsetting and Binding",98 );
        category.put("Puncture Shop",244 );
        category.put("Real Estate", 178);
        category.put("Rice Shop",203 );
        category.put("Roofings",185 );
        category.put("Saloon",19 );
        category.put("Shoe, Bag and Tailoring Materials",247 );
        category.put("Sound Service",170 );
        category.put("Sports",160 );
        category.put("Stationery Shop",26 );
        category.put("Sticker Shop",151 );
        category.put("Super Market",37 );
          /*"Bags","Bakery, Sweets and Snacks","Beauty Parlour and Sp","Bedding","Biriyani","Book Shop","Browsing Center","Builders",
        "Building Materials","Butcher Shop","Call Taxi &amp; Travels","Car Wash","Carpenter","Catering","Coconut Shop","Coffee &amp; Tea Shop","Coffee Powder"
        ,"Computer Sales &amp; Service","Corporate Gifts","Country Drugs","Courier","Cycle Sales &amp; Repair","Dental Clinic","Diagnostic Lab","Driving School",
        "Electricals &amp; Electronics","Electrician &amp; Plumber","Engineering Works","Event Items Rentals","Fancy Store","Finance","Flour Mill &amp; Rice Mill",
        "Flower Shop","Footwear","Fruit Shop","Furniture Rental","Furnitures","General Repair Shop","Glass and Plywoods","Gold Covering","","Grocery, Provisions &amp; General Store"
        ,"Guest House","Gym","Helmets &amp; Seat Covers","Home Appliances","Hospitals and Clinics","Hotel, Restaurant &amp; Fast Food","Idly Dosa Batter Shop",
        "Insect Screens","Interior Decoration","Jewelry Shop","Juice, Cool Drinks &amp; Ice cream","Laundry, Ironing &amp; Dry Cleaners",
        "Library","LIC &amp; Insurance","Lock Smith","Magazine &amp; Newspaper Mart","Mandapam","Medical &amp; Drugs Shop","Milk Center","Mobile Recharge &amp; Accessories",
        "Mobile Showroom","Modular Kitchen","Musical Instruments","Nursery","Office","Oil Store","Optical Shop","Organic Shop","Others","Pet Shop","Photo Studio, Videography &amp; Colour Lab",
        "Photos &amp; Frames Shop","Plastics Store","Play School","Pooja Materials","Printing, Offsetting and Binding","Puncture Shop","Real Estate","Rice Shop",
        "Roofings","Saloon","Shoe, Bag and Tailoring Materials","Sound Service","Sports","Stationery Shop","Sticker Shop","Super Market","Tailor","Tax Consultant",
        "Temples","test1", "Tiles Shop","Timber Shop", "Tinkering","Two Wheeler Dealers", "Two Wheeler Service","Tyre", "UPS &amp; Inverters","Vegetable Shop",
         "Vessels &amp; Utensils","Waste Paper Mart", "Watch and Clocks","Wedding Cards","Welding Shop","Wood Works","Xerox","Yoga and Meditation","",
*/
        category.put("Tailor",42 );
        category.put("Tax Consultant",249 );
        category.put("Temples",239 );
        category.put("test1",282 );
        category.put("Tiles Shop",49 );
        category.put("Timber Shop",57 );
        category.put("Tinkering",191 );
        category.put("Two Wheeler Dealers",140 );
        category.put("Two Wheeler Service",39 );
        category.put("Tyre",243 );
        category.put("UPS &amp; Inverters",258 );
        category.put("Vegetable Shop",20 );
        category.put("Vessels &amp; Utensils",176 );
        category.put("Waste Paper Mart",21 );
        category.put("Watch and Clocks",48 );
        category.put("Wedding Cards",197 );
        category.put("Welding Shop",74 );
        category.put("Wood Works",116 );
        category.put("Xerox",22 );
        category.put("Yoga and Meditation",52);

        if(true)
        {
            conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            netInfo = conMgr.getActiveNetworkInfo();

            if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {


                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("No Internet Connection");
                builder.setCancelable(false);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });

                final AlertDialog alert = builder.create();
                alert.show();

            }
        }




        about_us = (Button) findViewById(R.id.about_us);

        submit = (Button) findViewById(R.id.submit);





        km = (Spinner) findViewById(R.id.km_spinner);

        km.setPrompt("Kilometers");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.kms, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        km.setAdapter(adapter);
     km.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

             distance = (String) km.getItemAtPosition(i);
             distance = distance.substring(0,distance.indexOf("km")).trim();


         }

         @Override
         public void onNothingSelected(AdapterView<?> adapterView) {

         }
     });



        category_at = (AutoCompleteTextView) findViewById(R.id.category_At);
        ACTV_clear_button = (ImageButton) findViewById(R.id.autotextviewclear);

        ArrayAdapter<String> category_adapter;

        category_adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,category_list);

        category_at.setAdapter(category_adapter);



        category_at.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

               category_at.showDropDown();
                category_at.requestFocus();
                return false;
            }
        });

        category_at.setOnItemClickListener(new AdapterView.OnItemClickListener() {



            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                get_specific.setText("");

               shop_category = (String) adapterView.getItemAtPosition(i);

                InputMethodManager imm=(InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);




                category_number = ""+category.get(shop_category);




            }

        });

        ACTV_clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                category_at.setText("");

            }
        });











        get_location = (ImageButton) findViewById(R.id.get_location);

        address_display = (TextView) findViewById(R.id.Adrress_field);
        ImageView kartla_image = (ImageView) findViewById(R.id.kartla_image);

        kartla_image.setImageResource(R.drawable.kartlaimage);

        // getting user location in onclick()

      get_location.setOnClickListener(new View.OnClickListener()
      {
            @Override
            public void onClick(View view) {


                GetUserLocation getlocation = new GetUserLocation();

                getlocation.userLocation();
        


            }

        });


            //place autocomplete search code
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setBoundsBias(new LatLngBounds(
                new LatLng(13.067439, 80.237617),
                new LatLng(13.067439, 80.237617)));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.



                String s = String.valueOf(place.getAddress());

                area = (String) place.getName();

                latlng = String.valueOf(place.getLatLng());


                address_display.setText("Address : "+s);
                address_display.setTextColor(Color.parseColor("#000000"));

                int index1,index2;

                index1 = latlng.indexOf("(");
                index2 = latlng.indexOf(",");

                location_update = 1;



                lat = Double.parseDouble(latlng.substring(index1+1,index2));

                index1 = index2+1;
                index2 = latlng.indexOf(")");

                lng = Double.parseDouble(latlng.substring(index1,index2));

               // address_display.setText("LatnLng - "+place.getLatLng()+"Latitude:"+lat+" Longitude:"+lng);

                // Toast.makeText(MainActivity.this, ""+lat, Toast.LENGTH_SHORT).show();




            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("errrrrorrrrrr", "An error occurred: " + status);
            }
        });



        about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {



                Intent about_us_intent = new Intent(MainActivity.this,About_us_webdisplay.class);

                startActivity(about_us_intent);
            }
        });


        getspecific_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                get_specific.setText("");

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                get_specific_string = String.valueOf(get_specific.getText());





                if (get_specific_string.length() > 0) {
                    Intent intent = new Intent(MainActivity.this, ShopDisplay.class);

                    intent.putExtra("user_area", area);
                    intent.putExtra("distance", distance);

                    intent.putExtra("category_number", category_number);


                    intent.putExtra("shop_category", shop_category);

                    intent.putExtra("get_specific", get_specific_string);


                    Bundle b = new Bundle();
                    b.putDouble("latitude", lat);
                    b.putDouble("longitude", lng);
                    intent.putExtras(b);
                    startActivity(intent);
                } else {

                    conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    netInfo = conMgr.getActiveNetworkInfo();

                    if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
                        //Toast.makeText(MainActivity.this, "No Internet connection!", Toast.LENGTH_LONG).show();

                        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("No Internet Connection");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                dialog.cancel();
                            }
                        });


                        //    if(get_specific.getText() != null)
                        //  {


                        //}

                  /*  Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");



                   builder.setNeutralButton("Mobile data",startActivity(intent));

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
                        }
                    });

                    builder.setPositiveButton("WiFi settings", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        }
                    });  */


                        final AlertDialog alert = builder.create();
                        alert.show();

                    } else {

                        if (lat == 0.0 && lng == 0.0) {

                            Toast.makeText(MainActivity.this, "Please give the location!!", Toast.LENGTH_SHORT).show();

                        } else if (category_number == "0") {
                            Toast.makeText(MainActivity.this, "Select What you are looking for", Toast.LENGTH_SHORT).show();
                        } else if (lat != 0.0 && lng != 0.0 && category_number != "0") {

                            Intent intent = new Intent(MainActivity.this, ShopDisplay.class);

                            intent.putExtra("user_area", area);
                            intent.putExtra("distance", distance);

                            intent.putExtra("category_number", category_number);


                            intent.putExtra("shop_category", shop_category);

                            intent.putExtra("get_specific", get_specific_string);


                            Bundle b = new Bundle();
                            b.putDouble("latitude", lat);
                            b.putDouble("longitude", lng);
                            intent.putExtras(b);


                            startActivity(intent);
                        }

                    }


                }
            }
        });

        go_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                get_specific_string = String.valueOf(get_specific.getText());





                if (get_specific_string.length() > 0) {
                    Intent intent = new Intent(MainActivity.this, ShopDisplay.class);

                    intent.putExtra("user_area", area);
                    intent.putExtra("distance", distance);

                    intent.putExtra("category_number", category_number);


                    intent.putExtra("shop_category", shop_category);

                    intent.putExtra("get_specific", get_specific_string);


                    Bundle b = new Bundle();
                    b.putDouble("latitude", lat);
                    b.putDouble("longitude", lng);
                    intent.putExtras(b);
                    startActivity(intent);
                } else {

                    conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    netInfo = conMgr.getActiveNetworkInfo();

                    if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
                        //Toast.makeText(MainActivity.this, "No Internet connection!", Toast.LENGTH_LONG).show();

                        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("No Internet Connection");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                dialog.cancel();
                            }
                        });


                        //    if(get_specific.getText() != null)
                        //  {


                        //}

                  /*  Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");



                   builder.setNeutralButton("Mobile data",startActivity(intent));

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
                        }
                    });

                    builder.setPositiveButton("WiFi settings", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        }
                    });  */


                        final AlertDialog alert = builder.create();
                        alert.show();

                    } else {

                        if (lat == 0.0 && lng == 0.0) {

                            Toast.makeText(MainActivity.this, "Please give the location!!", Toast.LENGTH_SHORT).show();

                        } else if (category_number == "0") {
                            Toast.makeText(MainActivity.this, "Select What you are looking for", Toast.LENGTH_SHORT).show();
                        } else if (lat != 0.0 && lng != 0.0 && category_number != "0") {

                            Intent intent = new Intent(MainActivity.this, ShopDisplay.class);

                            intent.putExtra("user_area", area);
                            intent.putExtra("distance", distance);

                            intent.putExtra("category_number", category_number);


                            intent.putExtra("shop_category", shop_category);

                            intent.putExtra("get_specific", get_specific_string);


                            Bundle b = new Bundle();
                            b.putDouble("latitude", lat);
                            b.putDouble("longitude", lng);
                            intent.putExtras(b);
                            startActivity(intent);
                        }

                    }


                }

            }
        });

    }

    class GetUserLocation {

        void userLocation() {



            locationManager = (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);

            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) )
            {
                //ALERT FOR GPS NOT ENABLED
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                dialog.cancel();
                            }
                        });
                final AlertDialog alert = builder.create();
                alert.show();

            }

            else
                {

                locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {


                        if (location_update == 0) {
                            // Toast.makeText(MainActivity.this,""+ location_update, Toast.LENGTH_SHORT).show();
                            //from stack overflow
                            try {
                                lat = location.getLatitude();
                                lng = location.getLongitude();


                            } catch (Exception e) {

                                Toast.makeText(MainActivity.this, "Sorry! Unable to get your location. location update", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                };

                // if device is running in SDK less than 23

                if (Build.VERSION.SDK_INT < 23) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                } else

                {

                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


                    }
                    else
                        {


                            if(y == 0)
                            {


                                gpsprogress = new ProgressDialog(MainActivity.this);
                                gpsprogress.setMessage("Getting your location");
                                gpsprogress.setCanceledOnTouchOutside(false);
                                gpsprogress.show();

                            }
                        lastknownlocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        if (lastknownlocation == null) {
                            lastknownlocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }

                        for (int i = 0; i < 1; i++) {
                            if (lastknownlocation == null) {
                                lastknownlocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (lastknownlocation == null) {
                                    lastknownlocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                                }
                            } else {
                                break;
                            }
                        }


                        if (lastknownlocation == null && y < 3)
                        {



                            try {

                                Thread.sleep(1000);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            y++;

                            GetUserLocation o = new GetUserLocation();
                            o.userLocation();
                        }








                     /*   if(lastknownlocation == null)
                        {
                            lastknownlocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if(lastknownlocation == null)
                            {
                                lastknownlocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            }

                            for(int i = 0;i<1;i++)
                            {
                                if(lastknownlocation == null)
                                {
                                    lastknownlocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                                }
                                else
                                {
                                    break;
                                }
                            }

                        }   */
                     /*   Toast.makeText(MainActivity.this, "From     Grp provider"+lastknownlocation+" "+lastknownlocation, Toast.LENGTH_SHORT).show();

                        if(lastknownlocation == null)
                        {
                            lastknownlocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            Toast.makeText(MainActivity.this, "From if Network"+lastknownlocation+" "+lastknownlocation, Toast.LENGTH_SHORT).show();
                        }

                        for(int i = 0;i<10;i++)
                        {
                            if(lastknownlocation == null)
                            {
                                lastknownlocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                                Toast.makeText(MainActivity.this, i+" From for Network"+lastknownlocation+" "+lastknownlocation, Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                break;
                            }
                        }*/



                  /*  while(lastknownlocation == null)
                    {
                        lastknownlocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                    }  */

                   /* if(lastknownlocation == null)
                    {
                        lastknownlocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        Toast.makeText(gps, "once if "+lastknownlocation, Toast.LENGTH_SHORT).show();
                    }*/


                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10000, locationListener);


                        if (lastknownlocation != null)
                        {
                           gpsprogress.dismiss();

                            lat = lastknownlocation.getLatitude();
                            lng = lastknownlocation.getLongitude();


                            //getting user address

                            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                            try {

                                List<Address> listAddresses = geocoder.getFromLocation(lat, lng, 1);

                                if (listAddresses != null && listAddresses.size() > 0) {
                                    String user_Address = "";
                                    int n = listAddresses.get(0).getMaxAddressLineIndex();

                                    if (n >= 0) {

                                        user_Address += listAddresses.get(0).getAddressLine(0);

                                        for (int i = 1; i <= n; i++) {
                                            user_Address += " " + listAddresses.get(0).getAddressLine(i);
                                        }
                                        address_display.setText(user_Address);
                                        address_display.setTextColor(Color.parseColor("#000000"));

                                        area = listAddresses.get(0).getSubLocality();

                                    }

                                    //if the user continuously presses the "get user location " button
                                    else {


                                        if (location_toast != null) {
                                            location_toast.cancel();
                                        }
                                        location_toast = Toast.makeText(MainActivity.this, "Sorry! Unable to get your location.", Toast.LENGTH_SHORT);
                                        location_toast.show();


                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //Toast.makeText(MainActivity.this, "from last known location", Toast.LENGTH_SHORT).show();

                        } else {




                            if(lastknownlocation == null)
                            {

                                gpsprogress.dismiss();
                                Toast.makeText(MainActivity.this, "Sorry, unable to get your location. Please try again!", Toast.LENGTH_SHORT).show();


                                address_display.setText("Sorry, unable to get your location. Please try again! ");
                                address_display.setTextColor(Color.parseColor("#000000"));
                            }

                        }


                    }

                }
            }

        }
    }
    }






