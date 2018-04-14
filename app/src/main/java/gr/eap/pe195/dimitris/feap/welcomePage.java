package gr.eap.pe195.dimitris.feap;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by admin on 6/3/2015.
 */
public class welcomePage extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomepage);

        Button goBtn = (Button) findViewById(R.id.goButton);

        ImageView iv = (ImageView) findViewById(R.id.welcomePicIV);
        iv.setImageResource(R.drawable.welcomepic);


        goBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity((new Intent(welcomePage.this, mainPage.class)));
            }
        });


       Button goBtnTest = (Button) findViewById(R.id.testButton);

        goBtnTest.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity((new Intent(welcomePage.this, testingUI.class)));
            }
        });





    }
}
