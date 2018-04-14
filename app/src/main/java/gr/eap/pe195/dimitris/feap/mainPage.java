package gr.eap.pe195.dimitris.feap;

import android.app.Activity;
import android.app.AlertDialog;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;


public class mainPage extends Activity
{

    static private final String TAG = "FAEP";


    /////////// UI ELEMENTS

    //Space Dimentions  (x,y,z,)
    private EditText dimXet;
    private EditText dimYet;
    private EditText dimZet;

//    Microphone position (x,y,z)
    private EditText micXet;
    private EditText micYet;
    private EditText micZet;

//    Source position (x,y,z)
    private EditText sourceXet;
    private EditText sourceYet;
    private EditText sourceZet;


//    private TextView fstv; //FS textview (sampling rate)
    public Spinner fssp;

//    private TextView numOfImagestv;  //Number of Images textview
    public Spinner numOfImagessp;

//    private TextView reflCoeffstv; //Absorbtion coefficients textview
    private EditText reflCoeffset;



    /////temporary vars
    String output1 ="";
    private TextView tv1;


    //////////////////

    ///////////////////////////////
    // Buttons - TextViews - EditTexts in Results area
    private Button calcBtn;

    private Button c80Btn;
    private TextView c80TV;

    private Button c50Btn;
    private TextView c50TV;

    private Button edtBtn;
    private EditText edtET;
    private TextView edTV;

    private Button centreTimeBtn;
    private EditText centreTimeET;
    private TextView centreTimeTV;

    private Button gBtn;
    private EditText strengthET;
    private TextView strengthTV;

    private Button rtBtn;
    private TextView rtTV;
    /////////////////////////////////





    /////////////////////////////////
    private String errorSummary="";        //string that keeps the errors  empty textboc etc.
    private AlertDialog.Builder alertDlg;  // Dialog box for error report

    /////////////////////////////////



//            Εδώ φτιαχνουμε αντικείμενο με τις παραμέτρους που χρειαζονται
//            για να δημιουργήσου την κρουστική
    private ImpulseResponse ir = new ImpulseResponse();

    private Calculations calculate;  // antikeimeno που υπολογιζει την κρουστικη

    private boolean isIrCalculated;   // if the impulse response has has being calculated

    private AcousticParameters ap;

    //initialisation Of UI
    // connect java variables with Android GUI elements
    private void initialiseUI()
    {

        alertDlg = new AlertDialog.Builder(this); // init of error dialog


        ///////////      initialisation of UI in the activity

        /////// input pane
        dimXet = (EditText) findViewById(R.id.dimXET);
        dimYet = (EditText) findViewById(R.id.dimYET);
        dimZet = (EditText) findViewById(R.id.dimZET);

        micXet = (EditText) findViewById(R.id.micPosXET);
        micYet = (EditText) findViewById(R.id.micPosYET);
        micZet = (EditText) findViewById(R.id.micPosZET);

        sourceXet = (EditText) findViewById(R.id.sourcePosXET);
        sourceYet = (EditText) findViewById(R.id.sourcePosYET);
        sourceZet = (EditText) findViewById(R.id.sourcePosZET);

//        fstv = (TextView) findViewById(R.id.samplingRateET);
        fssp = (Spinner) findViewById(R.id.samplingRateSP);

//        numOfImagestv  = (TextView) findViewById(R.id.imagesET);
        numOfImagessp = (Spinner) findViewById(R.id.imagesSP);

//        reflCoeffstv = (TextView) findViewById(R.id.coefET);
        reflCoeffset = (EditText) findViewById(R.id.coefET);




        calcBtn = (Button) findViewById(R.id.calculateButton);

        ////////// results section GUI

        c80Btn = (Button) findViewById(R.id.calcC80Button);
        c80TV = (TextView) findViewById(R.id.c80ResultTV);

        c50Btn = (Button) findViewById(R.id.calc50Button);
        c50TV = (TextView) findViewById(R.id.c50ResultTV);

        edtBtn = (Button) findViewById(R.id.calcEDTButton);
        edtET = (EditText) findViewById(R.id.edtParamET);
        edTV = (TextView) findViewById(R.id.edtResultTV);

        centreTimeBtn = (Button) findViewById(R.id.calcCTButton);
        centreTimeET = (EditText) findViewById(R.id.ctParamET);
        centreTimeTV = (TextView) findViewById(R.id.ctResultTV);

        gBtn = (Button) findViewById(R.id.calcGButton);
        strengthET = (EditText) findViewById(R.id.gParamET);
        strengthTV = (TextView) findViewById(R.id.gResultTV);

        rtBtn = (Button) findViewById(R.id.calcRTButton);
        rtTV = (TextView) findViewById(R.id.rtResultTV);
        ////////////////////////////////////////



        ////////////////////////////////////////////////////

        tv1 = (TextView)findViewById(R.id.tv1GUI);
    }



    ////////////////////////////////////////////
    ///// validation of user input - (completed)
    private int validateUserInput()
    {
        int f1 =0;
        int f2 =0;
        int f3 =0;
        int f4 =0;
        int f5 =0;
        int f6 =0;
        int f7 =0;
        int f8 =0;
        int f9 =0;
        int f10=0;

        errorSummary="";
        int isInputAcceptable=0;

//        double temp1;
//        temp1  = (dimXet.getText().length() == 0 ) ? 1 : Double.parseDouble(dimXet.getText().toString());



        if(dimXet.getText().length()>0 )
        {
            double temp1 = Double.parseDouble(dimXet.getText().toString());
            if (temp1 > 0 && temp1 < 51)
                f1 = 1;
            else
                errorSummary += "\n Dimension X: Please enter number between 1 - 25 ";
        }
        else
            errorSummary += "\n  Dimension X: Cannot be empty";


//        double temp2 = Double.parseDouble(dimYet.getText().toString());
//        if ( temp2 > 0  && temp2 < 51  )
//            f2=1;
        if(dimYet.getText().length()>0 )
        {
            double temp2 = Double.parseDouble(dimYet.getText().toString());
            if ( temp2 > 0  && temp2 < 51  )
                f2=1;
            else
                errorSummary += "\n Dimension Y: Please enter number between 1 - 25 ";
        }
        else
            errorSummary += "\n  Dimension Y: Cannot be empty";



//        double temp3 = Double.parseDouble(dimZet.getText().toString());
//        if ( temp3 > 0  && temp3 < 51  )
//            f3=1;
        if(dimZet.getText().length()>0 )
        {
            double temp3 = Double.parseDouble(dimZet.getText().toString());
            if ( temp3 > 0  && temp3 < 51  )
                f3=1;
            else
                errorSummary += "\n Dimension Z: Please enter number between 1 - 25 ";
        }
        else
            errorSummary += "\n  Dimension Z: Cannot be empty";
        //////////////////////////////////////////////////////////////////////////////



//        double temp4 = Double.parseDouble(micXet.getText().toString());
//        if ( temp4 > 0  && temp4 < 51  )
//            f4=1;
        if(micXet.getText().length()>0 )
        {
            double temp4 = Double.parseDouble(micXet.getText().toString());
            if ( temp4 > 0  && temp4 < 51  )
                f4=1;
            else
                errorSummary += "\n Microphone position X: Please enter number between 1 - 25 ";
        }
        else
            errorSummary += "\n  Microphone position X: Cannot be empty";


//        double temp5 = Double.parseDouble(micYet.getText().toString());
//        if ( temp5 > 0  && temp5 < 51  )
//            f5=1;
        if(micYet.getText().length()>0 )
        {
            double temp5 = Double.parseDouble(micYet.getText().toString());
            if ( temp5 > 0  && temp5 < 51  )
                f5=1;
            else
                errorSummary += "\n Microphone position Y: Please enter number between 1 - 25 ";
        }
        else
            errorSummary += "\n  Microphone position Y: Cannot be empty";


//        double temp6 = Double.parseDouble(micZet.getText().toString());
//        if ( temp6 > 0  && temp6 < 51  )
//            f6=1;
        if(micZet.getText().length()>0 )
        {
            double temp6 = Double.parseDouble(micZet.getText().toString());
            if ( temp6 > 0  && temp6 < 51  )
                f6=1;
            else
                errorSummary += "\n Microphone position Z: Please enter number between 1 - 25 ";
        }
        else
            errorSummary += "\n  Microphone position Z: Cannot be empty";
        ///////////////////////////////////////////////////////////////



//        double temp7 = Double.parseDouble(sourceXet.getText().toString());
//        if ( temp7 > 0  && temp7 < 51  )
//            f7=1;
        if(sourceXet.getText().length()>0 )
        {
            double temp7 = Double.parseDouble(sourceXet.getText().toString());
            if ( temp7 > 0  && temp7 < 51  )
                f7=1;
            else
                errorSummary += "\n Source position X: Please enter number between 1 - 25 ";
        }
        else
            errorSummary += "\n  Source position X: Cannot be empty";



//        double temp8 = Double.parseDouble(sourceYet.getText().toString());
//        if ( temp8 > 0  && temp8 < 51  )
//            f8=1;
        if(sourceYet.getText().length()>0 )
        {
            double temp8 = Double.parseDouble(sourceYet.getText().toString());
            if ( temp8 > 0  && temp8 < 51  )
                f8=1;
            else
                errorSummary += "\n Source position Y: Please enter number between 1 - 25 ";
        }
        else
            errorSummary += "\n  Source position Y: Cannot be empty";


//        double temp9 = Double.parseDouble(sourceZet.getText().toString());
//        if ( temp9 > 0  && temp9 < 51  )
//            f9=1;
        if(sourceZet.getText().length()>0 )
        {
            double temp9 = Double.parseDouble(sourceZet.getText().toString());
            if ( temp9 > 0  && temp9 < 51  )
                f9=1;
            else
                errorSummary += "\n Source position Z: Please enter number between 1 - 25 ";
        }
        else
            errorSummary += "\n  Source position Z: Cannot be empty";
        ////////////////////////////////////////////////////////////////////



        try
        {
            double temp10 = Double.parseDouble(reflCoeffset.getText().toString());
            if (reflCoeffset.getText().length() > 0)
            {
                if (temp10 > -1.000001 && temp10 < 1.000001)
                    f10 = 1;
                else
                    errorSummary += "\n Reflection coefficients: Please enter number between -1 and 1 ";
            }
            else
                errorSummary += "\n  Reflection coefficients: Cannot be empty";

        }
        catch (NumberFormatException nfe)
        {
            errorSummary += "\n  Reflection coefficients: Only Numeric characters";
        }

        /////////////////////////////////////////////////////////////////






        /////////final check
        if ( f1==1 && f2==1 && f3==1 && f4==1 && f5==1 && f6==1 && f7==1 && f8==1 && f9==1 && f10==1)
            isInputAcceptable=1;


        return isInputAcceptable;
    }

    private void disableButtons()
    {
        c80Btn.setEnabled(false);
        c50Btn.setEnabled(false);

        edtBtn.setEnabled(false);
        edtET.setEnabled(false);

        centreTimeBtn.setEnabled(false);
        centreTimeET.setEnabled(false);

        gBtn.setEnabled(false);
        strengthET.setEnabled(false);

        rtBtn.setEnabled(false);
    }

    private void enableButtons()
    {
        c80Btn.setEnabled(true);
        c50Btn.setEnabled(true);

        edtBtn.setEnabled(true);
        edtET.setEnabled(true);

        centreTimeBtn.setEnabled(true);
        centreTimeET.setEnabled(true);

        gBtn.setEnabled(true);
        strengthET.setEnabled(true);

        rtBtn.setEnabled(true);

    }

    public void clearTextBoxResult()
    {
        c80TV.setText("");
        c50TV.setText("");

        edtET.setText("Give t");
        edTV.setText("");

        centreTimeET.setText("Give t");
        centreTimeTV.setText("");

        strengthET.setText("Give t");
        strengthTV.setText("");

        tv1.setText("");
        output1 ="";
    }


    private int validateForEDT(ImpulseResponse i)
    {
        int f1=0;
        errorSummary="";
        int isInputAcceptable=0;

        try
        {
            double temp1 = Double.parseDouble(edtET.getText().toString());
            if (temp1 < (i.getMax() / i.getFs()) && temp1 > 0)
            {
                f1 = 1;/// einai ste ok
            } else
            {
                errorSummary += "o χρονος που δινεις δεν μπορει να ειναι μεγαλυτερος το τον χρονο της κρουστικής";
            }
        }
        catch (NumberFormatException nfe)
        {
            errorSummary += "\n  (EDT) t parameter : Only Numeric characters";
        }


//        if(temp1< χρονο της h ( πχ 0.653.....) )
//        {
//            f1=1 ;/// einai ste ok
//        }
//        else
//        {
//            o χρονος που δινεις δεν μπορει να ειναι μεγαλυτερος το τον χρονο της κρουστικής
//        }

        if ( f1==1 )
            isInputAcceptable=1;
        return isInputAcceptable;
    }


    private int validateForCT(ImpulseResponse i)
    {
        int f1=0;
        errorSummary="";
        int isInputAcceptable=0;

        try
        {
            double temp1 = Double.parseDouble(centreTimeET.getText().toString());
            if (temp1 < (i.getMax() / i.getFs())  && temp1 >0  )
            {
                f1 = 1;/// einai ste ok
            } else
            {
                errorSummary += "o χρονος που δινεις δεν μπορει να ειναι μεγαλυτερος το τον χρονο της κρουστικής";
            }
        }
        catch ( NumberFormatException nfe)
        {
            errorSummary += "\n  (CT) t parameter : Only Numeric characters";
        }


        if ( f1==1 )
            isInputAcceptable=1;

        return isInputAcceptable;
    }


    /////////////////////////////////////////////////

    private boolean isAlphaNumeric(String str)
    {
        String pattern= "^[a-zA-Z]*$"; /////String pattern= "^[a-zA-Z0-9]*$";
        if(str.matches(pattern))
        {
            return true;
        }
        return false;
    }

    private boolean isNumeric(String str)
    {
        boolean ret = false;

        String pattern= "^[0-9]*$"; /////String pattern= "^[a-zA-Z0-9]*$";
        if(str.matches(pattern))
        {
            return true;
        }
        return false;
    }

    private void errorDialog( String str)
    {
        alertDlg.setMessage(str);
        alertDlg.setTitle("Error Report");
        alertDlg.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss the dialog
                    }
                });
        alertDlg.setCancelable(true);
        alertDlg.create().show();
    }

    private void playErrorSound()
    {
        try
        {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager. TYPE_NOTIFICATION );
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    ///////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);




        initialiseUI(); //1.

        //////// Lists for the spinners
        List<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<=10; i++)
            list.add(i);

        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>
                (this, android.R.layout.simple_spinner_item, list);

        numOfImagessp.setAdapter(dataAdapter);
        numOfImagessp.setSelection(5); // 6th position of the spinner ( number 6. useful for debugging )
        /////////////////////////////////



        disableButtons(); //2.





        calcBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                int validation = validateUserInput();

                clearTextBoxResult();

               if (validation==1 )  // validation passes
               {
                   //inserting the values from UI elements into java variables
                   ir.setRm(
                           Double.parseDouble(dimXet.getText().toString()),
                           Double.parseDouble(dimYet.getText().toString()),
                           Double.parseDouble(dimZet.getText().toString()));

                   ir.setMic(
                           Double.parseDouble(micXet.getText().toString()),
                           Double.parseDouble(micYet.getText().toString()),
                           Double.parseDouble(micZet.getText().toString()));

                   ir.setSrc(
                           Double.parseDouble(sourceXet.getText().toString()),
                           Double.parseDouble(sourceYet.getText().toString()),
                           Double.parseDouble(sourceZet.getText().toString()));

//                   ir.setFs(Integer.parseInt(fstv.getText().toString()));
                   ir.setFs(Integer.parseInt(fssp.getSelectedItem().toString()));

//                ir.setNumOfImages(  Integer.parseInt(numOfImagestv.getText().toString()) );
                   ir.setNumOfImages(Integer.parseInt(numOfImagessp.getSelectedItem().toString()));


//                   ir.setReflCoeffs(Double.parseDouble(reflCoeffstv.getText().toString()));
                   ir.setReflCoeffs(Double.parseDouble(reflCoeffset.getText().toString()));



                   // we pass to the 'calculate' object  (type Calculation)
                   // the ir object. The ir contains all the input parameters
                   // that the user enters in the main page.
                   // eg. dimensions of the area (x,y,z), positions (x,y,z)
                   // of the microphone and of the source. and others check code above
                   calculate = new Calculations(ir);


                   // after we pass the ir, the impulse response is calculated
                   calculate.calculateIR();


                   // after the calculations the ir object is UPDATED with all the
                   // parameters that the h function has.
                   // specifically the most useful is the array of the actual h function.
                   ir = calculate.getP();  //// IMPORTANT

                   // a new object  'ap' is created   (type AcousticParameters)
                   // and the ir is passed. (The important is that the h function is passed)
                   // the ap object then calculates all the acoustic parameters
                   // such as C80 , c50, EDT etc.
                   ap = new AcousticParameters(ir);



                   /////////////////////////
//                   output1 += "Number of samples in function h = " + ir.getMax() +
//                           "\n h=1 is in sample = " + ir.getMin();
                   output1 += " \n Size of h :" + ir.gethFinal().size();
                   output1 += "\n Duration of h = " + ir.getMax() / ir.getFs() + " sec";
                   ////////////////////////////
                   tv1.setText(output1);


                   isIrCalculated = ir.getIsIRCalculated();
                   if (isIrCalculated)
                       enableButtons();

               }
                else //  validation fails
               {
                   Log.i(TAG, "Error in validation");
                   errorDialog(errorSummary);
                   playErrorSound();
               }

            }
        });




        c80Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                double clarity80 = +calculate.c80();
//                Log.i(TAG, "μεσα στο c80 einai  " + clarity80);
//                c80TV.setText( Double.toString(clarity80  ) );
//                double clarity80 = +ap.c80();
//                Log.i(TAG, "μεσα στο c80 einai  " + clarity80);
                c80TV.setText( Double.toString(ap.c80_2()  ) );

            }
        });


        c50Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                c50TV.setText( Double.toString(calculate.c50() ));
                c50TV.setText( Double.toString(ap.c50_2() ));
            }
        });




        edtBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int valEDT = validateForEDT(ir);
                if (valEDT == 1)
                {
                    double edt;
                    edt =  ap.edt( Double.parseDouble(edtET.getText().toString()) );
                    edTV.setText(Double.toString( edt))  ;
                }
                else
                {
                    Log.i(TAG, "Error in EDT validation!!");
                    errorDialog(errorSummary);
                    playErrorSound();
                }


            }
        });


        centreTimeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int valCT = validateForCT(ir);
                if (valCT == 1)
                {
                    double ct;
                    ct =  ap.ct(Double.parseDouble(centreTimeET.getText().toString()));
                    centreTimeTV.setText(Double.toString( ct))  ;

                }
                else
                {
                    Log.i(TAG, "Error in EDT validation! !");
                    errorDialog(errorSummary);
                    playErrorSound();
                }

            }
        });

        gBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ap.g(1.0);
            }
        });



        rtBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    double rt = ap.rt();
                    rtTV.setText(Double.toString(rt));
                    Log.i("FAEP", "Reverbererion Time" + rt );

                }
                catch (Exception e)
                {
                    Log.i("FAEP", "e  = " + e);
                }


            }
        });



    }
}
