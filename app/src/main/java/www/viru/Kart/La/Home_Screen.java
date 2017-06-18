package www.viru.Kart.La;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import www.viru.Kart.La.R;

public class Home_Screen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    ImageView welcome,to,kartla_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__screen);

        welcome= (ImageView) findViewById(R.id.welcome);
        to = (ImageView) findViewById(R.id.to);
        kartla_image = (ImageView) findViewById(R.id.kart);

       welcome.setVisibility(View.GONE);
       to.setVisibility(View.GONE);
        kartla_image.setVisibility(View.GONE);

        welcome.setTranslationX(1000f);

        to.setTranslationY(-1000f);

        kartla_image.setTranslationX(-1000f);

      /*  welcome.setTranslationY(-1000f);

        to.setTranslationY(-1000f);

        kartla_image.setTranslationY(-1000f);  */


        kartla_image.setVisibility(View.VISIBLE);
        to.setVisibility(View.VISIBLE);
        welcome.setVisibility(View.VISIBLE);

       welcome.animate()
                .translationXBy(-1000f)

                .rotationBy(1440)
                .setDuration(1000);
        to.animate()
                .translationYBy(1000f)

                .rotationBy(1440)
                .setDuration(1000);
       kartla_image.animate()
                .translationXBy(1000f)

                .rotationBy(1440)
                .setDuration(1000);

      //from top
      /*  welcome.animate()
                .translationYBy(1000f)

                .rotationBy(1440)
                .setDuration(1000);
        to.animate()
                .translationYBy(1000f)

                .rotationBy(1440)
                .setDuration(1000);
        kartla_image.animate()
                .translationYBy(1000f)

                .rotationBy(1440)
                .setDuration(1000);  */



        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                Intent mainIntent = new Intent(Home_Screen.this,MainActivity.class);
                Home_Screen.this.startActivity(mainIntent);
                Home_Screen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
