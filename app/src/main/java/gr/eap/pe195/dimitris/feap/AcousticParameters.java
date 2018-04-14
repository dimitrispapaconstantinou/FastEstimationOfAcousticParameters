package gr.eap.pe195.dimitris.feap;

import android.util.Log;

/**
 * Created by admin on 29/3/2015.
 */
public class AcousticParameters
{
    private ImpulseResponse params;
    private int intMax;

    public AcousticParameters()
    {
//        intMax=1;
    }

    public AcousticParameters(ImpulseResponse p)
    {
        params = p;
//        int intMax = (int) p.getMax()+1;
    }

    public ImpulseResponse getParams()
    {
        return params;
    }

    public void setParams(ImpulseResponse params)
    {
        this.params = params;
    }




    public double c80()
    {


        Log.i("FAEP", "Samples of h # " + intMax); // μεγιστο μεγεθος κρουστικής σε samples

        int fundPos=0;  // the position of the fundamental

        //the first position that is !=0 is the fundamental
        for (int i=0; i<params.gethFinal().size() ; i++)
            if(params.gethFinal(i) != 0)
            {
                fundPos=i;
                break;
            }

        Log.i("FAEP", " Position of fundamental of the impulse response(h)" + fundPos); ///θεση της fundamental
        Log.i("FAEP", " Fundamental periexei " + params.gethFinal(fundPos));
//        Log.i("FAEP", " τι περιεχει η h στην θεση της fundamental " + p.gethFinal(2736));



        double hTempNumerator = 0; // temporary variable. Helds the numerator (αριθμητης).see C80 equation
        double hTempDenominator = 0; // temporary variable. Helds the denominator (παρoνομαστης).see C80 equation


        // ποσα samples ειναι τα 80 ms.
        // see equations in thesis
        int  numOfSamplesFor80ms= (int)  ( params.getFs() * 0.08 );
        Log.i("FAEP", " time80ms " +numOfSamplesFor80ms);



        // Ξεκινάει να υπολογίζει απο την θέση που είναι η fundamental
        // μέχρι τη θέση στην οποια τελειώνουν τα 80 ms.
        // πχ αν η fundamental της κρουστικής ειναι στην θέση 3000
        // το for θα τρέξει μεχρι τη θεση 3000 + ( αριθμός samples που αντιστοιχει σε 80ms)
        for( int i= fundPos ; i < fundPos + numOfSamplesFor80ms; i++)
            hTempNumerator+= Math.pow( params.gethFinal(i), 2);

        Log.i("FAEP", " hTempNumerator " + hTempNumerator);

        //////////////////////////////////////
        Log.i("FAEP", "\n");
        Log.i("FAEP", " απο εδω ξεκναει ο υπολογισμος των 80ms και μετα " +fundPos +numOfSamplesFor80ms+1);
        Log.i("FAEP", " μέχρι την θεση  " + params.gethFinal().size() );
        //////////////////////////////////////


        // οτι και παραπανω
        for (int i = fundPos +numOfSamplesFor80ms+1; i < params.gethFinal().size(); i++)
//            if (  p.gethFinal(i)!=0)
            hTempDenominator += Math.pow ( params.gethFinal(i), 2);


        Log.i("FAEP", " hTempDenominator " + hTempDenominator);

        Log.i("FAEP", " c802  " + (10 * Math.log10(hTempNumerator / hTempDenominator) ) );

        return (10 * Math.log10(hTempNumerator / hTempDenominator) );
    }


    public double c80_2()
    {

//        Από τον παραπάνω κώδικα βρίσκεις το i (αριθμό δείγματος της κρουστικής)
//        το οποίο αντιστοιχεί στα 80ms
//        και στην συνέχεια υπολογίζεις τις ενέργειες του αριθμητή και του παρανομαστή...
//        t=0;
//        i=0;
//        while (t<=0.08)     80*10^-3)
//        {
//        i++
//            i += i*(1/fs)
//        t=i
//            t=++i*(1/fs);
//        }
//
        Log.i("FAEP", "===========================================");
        int intMax = (int) params.getMax()+1;
        Log.i("FAEP", " Samples of h # :" + intMax); // μεγιστο μεγεθος κρουστικής σε samples

        int fundPos=0;  // the position of the fundamental

        //the first position that is !=0 is the fundamental
//        for (int i=0; i<params.gethFinal().size() ; i++)
//            if(params.gethFinal(i) != 0)
//            {
//                fundPos=i;
//                break;
//            }

       // Log.i("FAEP", " Position of fundamental of the impulse response(h :)" + fundPos); ///θεση της fundamental
       // Log.i("FAEP", " Fundamental (contains) : " + params.gethFinal(fundPos));
//        Log.i("FAEP", " τι περιεχει η h στην θεση της fundamental " + p.gethFinal(2736));
       // Log.i("FAEP", "------------");


        double hTempNumerator = 0; // temporary variable. Helds the numerator (αριθμητης).see C80 equation
        double hTempDenominator = 0; // temporary variable. Helds the denominator (παρoνομαστης).see C80 equation


        // ποσα samples ειναι τα 80 ms.
        // see equations in thesis
        int  numOfSamplesFor80ms= (int)  ( params.getFs() * 0.08 );
        Log.i("FAEP", " Number of samples for 80 ms : " +numOfSamplesFor80ms);

        // Ξεκιναει να υπολογιζει απο την αρχή θεση 0
        // μέχρι τη θέση στην οποια τελειώνουν τα 80 ms.
        for( int i=0 ; i < numOfSamplesFor80ms; i++)
            hTempNumerator+= Math.pow( params.gethFinal(i), 2);

        Log.i("FAEP", " hTempNumerator  " + hTempNumerator);

        //////////////////////////////////////
        Log.i("FAEP", "\n");
        Log.i("FAEP", " απο θεση ("+ (numOfSamplesFor80ms+1) +") ξεκναει ο υπολογισμος των 80ms και μετα ");
        Log.i("FAEP", " μέχρι την θεση  " + params.gethFinal().size() );
        //////////////////////////////////////


        // οτι και παραπανω
        for (int i = numOfSamplesFor80ms+1; i < params.gethFinal().size(); i++)
//            if (  p.gethFinal(i)!=0)
            hTempDenominator += Math.pow ( params.gethFinal(i), 2);


        Log.i("FAEP", " hTempDenominator " + hTempDenominator);

        Log.i("FAEP", " c80_2  " + (10 * Math.log10(hTempNumerator / hTempDenominator) ) );

        return (10 * Math.log10(hTempNumerator / hTempDenominator) );
    }







    public double c50()
    {
        Log.i("FAEP", " intMax " +intMax); // μεγιστο μεγεθος κρουστικής σε samples

        int fundTime=0;  // the position of the fundamental

        //the first position that is !=0 is the fundamental
        for (int i=0; i<params.gethFinal().size() ; i++)
            if(params.gethFinal(i) != 0)
            {
                fundTime=i;
                break;
            }

        Log.i("FAEP", " θεση της   " + fundTime); ///θεση της fundamental
        Log.i("FAEP", " τι περιεχει η h στην θεση της fundamental " + params.gethFinal(2736));



        double hTempNumerator = 0; // temporary variable. Helds the numerator (αριθμητης).see C50 equation
        double hTempDenominator = 0; // temporary variable. Helds the denominator (παρoνομαστης).see C50 equation


        // ποσα samples ειναι τα 50 ms.
        // see equations in thesis
        int  time50ms= (int)  ( params.getFs() * 0.05 );
        Log.i("FAEP", " time50ms " +time50ms);



        // Ξεκινάει να υπολογίζει απο την θέση που είναι η fundamental
        // μέχρι τη θέση στην οποια τελειώνουν τα 50 ms.
        // πχ αν η fundamental της κρουστικής ειναι στην θέση 3000
        // το for θα τρέξει μεχρι τη θεση 3000 + ( αριθμός samples που αντιστοιχει σε 50ms)
        for( int i= fundTime ; i < fundTime + time50ms; i++)
            hTempNumerator+= Math.pow( params.gethFinal(i), 2);

        Log.i("FAEP", " hTempNumerator " + hTempNumerator);

        //////////////////////////////////////
        Log.i("FAEP", "\n");
        Log.i("FAEP", " απο εδω ξεκναει ο υπολογισμος των 80ms και μετα " +fundTime +time50ms+1);
        Log.i("FAEP", " μέχρι την θεση  " + params.gethFinal().size() );
        //////////////////////////////////////


        // οτι και παραπανω
        for (int i = fundTime +time50ms+1; i < params.gethFinal().size(); i++)
            if (  params.gethFinal(i)!=0)
                hTempDenominator += Math.pow ( params.gethFinal(i), 2);


        Log.i("FAEP", " hTempDenominator " + hTempDenominator);

        return (10 * Math.log10(hTempNumerator / hTempDenominator) );
    }

    public double c50_2()
    {
        Log.i("FAEP", "==============================================");
        int intMax = (int) params.getMax()+1;
        Log.i("FAEP", " Samples of h # :" + intMax); // μεγιστο μεγεθος κρουστικής σε samples

        int fundPos=0;  // the position of the fundamental

        //the first position that is !=0 is the fundamental
//        for (int i=0; i<params.gethFinal().size() ; i++)
//            if(params.gethFinal(i) != 0)
//            {
//                fundPos=i;
//                break;
//            }

       // Log.i("FAEP", " Position of fundamental of the impulse response(h :)" + fundPos); ///θεση της fundamental
        //Log.i("FAEP", " Fundamental (contains) : " + params.gethFinal(fundPos));
//        Log.i("FAEP", " τι περιεχει η h στην θεση της fundamental " + p.gethFinal(2736));
       // Log.i("FAEP", "-----------------");


        double hTempNumerator = 0; // temporary variable. Helds the numerator (αριθμητης).see C80 equation
        double hTempDenominator = 0; // temporary variable. Helds the denominator (παρoνομαστης).see C80 equation


        // ποσα samples ειναι τα 50 ms.
        // see equations in thesis
        int  numOfSamplesFor50ms= (int)  ( params.getFs() * 0.05 );
        Log.i("FAEP", " Number of samples for 50 ms : " +numOfSamplesFor50ms);

        // Ξεκιναει να υπολογιζει απο την αρχή θεση 0
        // μέχρι τη θέση στην οποια τελειώνουν τα 50 ms.
        for( int i=0 ; i < numOfSamplesFor50ms; i++)
            hTempNumerator+= Math.pow( params.gethFinal(i), 2);

        Log.i("FAEP", " hTempNumerator  " + hTempNumerator);

        //////////////////////////////////////
        Log.i("FAEP", "\n");
        Log.i("FAEP", " απο θεση ("+ (numOfSamplesFor50ms+1) +") ξεκναει ο υπολογισμος των 50ms και μετα ");
        Log.i("FAEP", " μέχρι την θεση  " + params.gethFinal().size() );
        //////////////////////////////////////


        // οτι και παραπανω
        for (int i = (numOfSamplesFor50ms+1); i < params.gethFinal().size(); i++)
//            if (  p.gethFinal(i)!=0)
            hTempDenominator += Math.pow ( params.gethFinal(i), 2);


        Log.i("FAEP", " hTempDenominator " + hTempDenominator);

        Log.i("FAEP", " c50_2  " + (10 * Math.log10(hTempNumerator / hTempDenominator) ) );

        return (10 * Math.log10(hTempNumerator / hTempDenominator) );
    }




    public double edt(double t)   ////ok
    {

        Log.i("FAEP", "υπολιγισμος EDT");

        double hTempNumerator = 0; // temporary variable. Helds the numerator (αριθμητης).see C50 equation
        double hTempDenominator = 0; // temporary variable. Helds the denominator (παρoνομαστης).see C50 equation


        int  time= (int)  ( params.getFs() * t );

        for( int i= time ; i < params.gethFinal().size(); i++)
            hTempNumerator+=  Math.pow( params.gethFinal(i), 2) ;



        for (int i =0; i < params.gethFinal().size(); i++)
            hTempDenominator += Math.pow ( params.gethFinal(i), 2);


        Log.i("FAEP", " hTempDenominator " + hTempDenominator);


        Log.i("FAEP", " EDT " + (10 * Math.log10(hTempNumerator / hTempDenominator) ) );
        return (10 * Math.log10(hTempNumerator / hTempDenominator) );

    }

    public double ct( double t)   //// ok
    {

        Log.i("FAEP", "υπολιγισμος ct");

        double hTempNumerator = 0; // temporary variable. Helds the numerator (αριθμητης).see C50 equation
        double hTempDenominator = 0; // temporary variable. Helds the denominator (παρoνομαστης).see C50 equation


        for( int i= 0 ; i < params.gethFinal().size(); i++)
            hTempNumerator+= (t * Math.pow( params.gethFinal(i), 2) );



        for (int i =0; i < params.gethFinal().size(); i++)
            hTempDenominator += Math.pow ( params.gethFinal(i), 2);


        Log.i("FAEP", " hTempDenominator " + hTempDenominator);
        Log.i("FAEP", " hTempDenominator " + hTempDenominator);

        return (10 * Math.log10(hTempNumerator / hTempDenominator) );
    }

    public double g(double t)
    {
        //TODO calculate g
        Log.i("FAEP", "υπολιγισμος g");
        return 0;
    }

    public double rt()   //ok
    {
        ////A = aporofifi corou
        // 2 * a * x * z
        try
        {
            double A = (2 * params.getReflCoeffs() * params.getRm(0) * params.getRm(2)) +
                    (2 * params.getReflCoeffs() * params.getRm(1) * params.getRm(2)) +
                    ( 2 * params.getReflCoeffs() * params.getRm(0) * params.getRm(1)   );
            Log.i("FAEP", "A  = " + A);

            double V = params.getRm(0) * params.getRm(1) * params.getRm(2);
            Log.i("FAEP", "Oγκος v = " + V);

            double rt = (0.161 * V) / A;
            Log.i("FAEP", "rt  = " + rt);
            return rt;
        }
        catch (Exception e)
        {
            Log.i("FAEP", "e  = " + e);
        }

        return 0;
    }

}
