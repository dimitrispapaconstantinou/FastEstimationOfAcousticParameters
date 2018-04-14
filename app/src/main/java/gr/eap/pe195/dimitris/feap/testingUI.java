package gr.eap.pe195.dimitris.feap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 21/3/2015.
 */
public class testingUI extends Activity
{

    private  TextView restv; // step 1a
    private EditText et1;
    private EditText et2;
    private EditText et3;



    public void initUI()
    {
        restv = (TextView) findViewById(R.id.resTV);
        et1 = (EditText) findViewById(R.id.editText);
        et2 = (EditText) findViewById(R.id.editText2);
        et3 = (EditText) findViewById(R.id.editText3);
    }



    public int validation()
    {
        int f1=0;
        int f2=0;
        int f3=0;

        int val=0;

        double temp1 = Double.parseDouble( et1.getText().toString() );
        if ( temp1 > 0  && temp1 < 25  )
            f1=1;

        double temp2 = Double.parseDouble( et2.getText().toString() );
        if ( temp2  > 0  && temp2   <25  )
            f2=1;

        double temp3 = Double.parseDouble( et3.getText().toString() );
        if ( temp3  > 0  && temp3   <25  )
            f3=1;




        if ( f1 == 1 && f2==1  && f3==1)
        {
            val=1;
        }

        return val;


    }


    public int validation2()
    {

        int f1=0;
        int f2=0;
        int f3=0;

        int val=0;

        double temp1 = Double.parseDouble( et1.getText().toString() );
        double temp2 = Double.parseDouble( et2.getText().toString() );


        if ( temp1 < 0  && temp1 > 25  )
        {
         val=0;
        }
        else if( temp2 <  0 && temp2 >25)
        {
            val=0;
        }
        else
        {
            val=1;
        }


        return val;
    }





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testing_ui);
        
        initUI();


//        EditText et1 = (EditText) findViewById(R.id.editText);
//        et1 = (EditText) findViewById(R.id.editText);///step2 ok




//        EditText et2 = (EditText) findViewById(R.id.editText2);  step 3




//        final TextView restv = (TextView) findViewById(R.id.resTV);  // step 1a ok
//        restv = (TextView) findViewById(R.id.resTV);   //step 1b

        Button btn = (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int va = validation();

                if (va==1)
                {
                    restv.setText("ollla ok");
                }
                else
                {
                    restv.setText("DEN ειμαστε οκ - validation failed");
                }
            }
        });




///////////////////////SPINERS////////////////////
        Spinner sp = (Spinner) findViewById(R.id.spinner);

       List<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<=10; i++)
            list.add(i);

        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>
                (this, android.R.layout.simple_spinner_item, list);


//        List<String> list = new ArrayList<String>();
//        for (int i=1; i<=10; i++)
//            list.add(Integer.toString(i) + " rerererer");
//
//
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
//                (this, android.R.layout.simple_spinner_item, list);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);


       sp.setAdapter(dataAdapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Log.i("TAG","mesa sto public void onItemSelected");


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                Log.i("TAG","mesa sto onNothingSelected");



            }
        });


///////////////////////////////////////////////////////////////









    }
}
