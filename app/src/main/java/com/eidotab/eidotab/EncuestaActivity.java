package com.eidotab.eidotab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

public class EncuestaActivity extends AppCompatActivity {


    RatingBar ratingpersonal;
    TextView rbvalpersonal;

    RatingBar ratingtiempos;
    TextView rbvaltiempos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);


        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        ratingpersonal = (RatingBar) findViewById(R.id.ratingpersonal);
        rbvalpersonal = (TextView) findViewById(R.id.rbvalpersonal);

        ratingtiempos = (RatingBar) findViewById(R.id.ratingtiempos);
        rbvaltiempos = (TextView) findViewById(R.id.rbvaltiempos);


        ratingpersonal.setRating((float)3 );
        rbvalpersonal.setText("buena!");

        ratingtiempos.setRating((float)3 );
        rbvaltiempos.setText("moderado!");

        ratingpersonal.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {


                if(v < 2.0f)
                {
                    ratingBar.setRating(1.0f);
                    rbvalpersonal.setText("mala!");
                }


            }

        });

        ratingpersonal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch((int) ratingpersonal.getRating())
                {

                    case 2:

                        rbvalpersonal.setText("regular");
                        break;

                    case 3:

                        rbvalpersonal.setText("buena!");
                        break;

                    case 4:

                        rbvalpersonal.setText("muy buena!");
                        break;

                    case 5:

                        rbvalpersonal.setText("excelente!");
                        break;

                    default:

                        ratingpersonal.setRating(1.0f);
                        rbvalpersonal.setText("mala!");

                        break;

                }


                return false;
            }
        });



        ratingtiempos.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {


                if(v < 2.0f)
                {
                    ratingBar.setRating(1.0f);
                    rbvaltiempos.setText("muy lento!");
                }


                }

        });

        ratingtiempos.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch((int) ratingtiempos.getRating())
                {

                    case 2:

                        rbvaltiempos.setText("lento!");
                        break;

                    case 3:

                        rbvaltiempos.setText("moderado!");
                        break;

                    case 4:

                        rbvaltiempos.setText("rápido!");
                        break;

                    case 5:

                        rbvaltiempos.setText("muy rápido!");
                        break;

                    default:

                        ratingtiempos.setRating(1.0f);
                        rbvaltiempos.setText("muy lento!");

                        break;

                }


                return false;
            }
        });








    }
}
