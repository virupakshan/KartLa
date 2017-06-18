package www.viru.Kart.La;

import android.app.Activity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by gowtham g on 07-05-2017.
 */

public class CustomList extends ArrayAdapter<String> {

   public final Activity context;
   public final ArrayList<String> name;
   public  final ArrayList<String> contact;
   public final ArrayList<String> address;
    public  final  ArrayList<String> distance_km;
  // public ArrayList<Bitmap> images = new ArrayList<Bitmap>();
  //  public final ArrayList<Double> latitude;
  //  public final ArrayList<Double> longitude;

    ArrayList<String> image_urls ;

    //ArrayList<String> contact, ArrayList<String> address
          //  , ArrayList<Bitmap> images, ArrayList<Double> latitude, ArrayList<Double> longitude

    public CustomList(Activity context, ArrayList<String> name, ArrayList<String> image_urls,ArrayList<String> address,ArrayList<String> contact,
                      ArrayList<String> distance_km) {
        super(context, R.layout.customlayout, name);
        this.context = context;
        this.name = name;
        this.address = address;
       this.contact = contact;
      /*  this.address = address;
        this.images = images;
        this.latitude = latitude;
        this.longitude = longitude;  */
        this.image_urls = image_urls;
        this.distance_km = distance_km;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.customlayout, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        TextView txtTitle2 = (TextView) rowView.findViewById(R.id.txt2);


        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        if(name.get(position).length() > 22 )
        {
            txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        }


        txtTitle.setText((position+1)+". "+name.get(position)+"("+contact.get(position)+")"+"\n"+distance_km.get(position));
       /* Glide.with(context)
                .load(image_urls.get(position))
                .into(imageView); */

        Picasso.with(context).load(image_urls.get(position)).into(imageView);

          txtTitle2.setText(address.get(position));

        // imageView.setImageResource(imageId[position]);
       // imageView.setImageBitmap(images.get(position));
        return rowView;
    }


}
