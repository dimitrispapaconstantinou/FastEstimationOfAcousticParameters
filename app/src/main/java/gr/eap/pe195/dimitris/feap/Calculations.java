package gr.eap.pe195.dimitris.feap;

import android.app.ProgressDialog;
import android.nfc.Tag;
import android.util.Log;
import java.util.ArrayList;


public class Calculations
{

    private int lengthOfArrays =0;

    private ImpulseResponse p;   // αντικειμενο poy periexei olew tiw apaitoymene parametroye gia toyw ipologismous

    private int intMax;


    private ProgressDialog pd;


    public Calculations()
    {
        p.setRm(1, 1, 1);
        p.setMic(1, 1, 1);
        p.setSrc(1, 1, 1);
        p.setFs(44100);
        p.setNumOfImages(3);
        p.setReflCoeffs(0.3);
    }

    public Calculations(ImpulseResponse p)
    {
        this.p = p;
    }

    public ImpulseResponse getP()
    {
        return p;
    }







    public void calculateIR()
    {
        long startTime;
        try
        {

            lengthOfArrays = (p.getNumOfImages() * 2) + 1;  /////see declaration

            startTime = System.nanoTime();
            for (int i = 0; i < lengthOfArrays; i++)
            {
//            n[i] = (i - numOfImages);
                p.setN((i - p.getNumOfImages()), i);

                double temp = Math.pow(-1, p.getN(i));

                //rms[i] = n[i] + 0.5 - (0.5 * temp);
                p.setRms(p.getN(i) + 0.5 - (0.5 * temp), i);

//            srcs[i] = Math.pow(-1, n[i]);
                p.setSrcs(Math.pow(-1, p.getN(i)), i);
            }
//            Log.i("FAEP", " 1 for " + (System.nanoTime() - startTime  ));

            /////////////////////////////////////////////////////////////////////


            for (int i = 0; i < lengthOfArrays; i++)
            {
//            xi[i] = ((srcs[i] * src[0]) + (rms[i] * rm[0]) - mic[0]); //eq2
                p.setXi((p.getSrcs(i) * p.getSrc(0)) +
                        (p.getRms(i) * p.getRm(0)) -
                        p.getMic(0), i);


//            yj[i] = ((srcs[i] * src[1]) + (rms[i] * rm[1]) - mic[1]); //eq3
                p.setYj((p.getSrcs(i) * p.getSrc(1)) +
                        (p.getRms(i) * p.getRm(1)) -
                        p.getMic(1), i);


//            zk[i] = ((srcs[i] * src[2]) + (rms[i] * rm[2]) - mic[2]); //eq4
                p.setZk((p.getSrcs(i) * p.getSrc(2)) +
                        (p.getRms(i) * p.getRm(2)) -
                        p.getMic(2), i);

            }
            ///////////////////////////////////////////////////////////////////////

//            Log.i("FAEP", " 2 for" + (System.nanoTime() - startTime  ));

            ///////////////////////////////////////////////////////////
            //////// ========= i ==============//[i,j,k]=meshgrid(xi,yj,zk);
            /////////================% convert vectors to 3D matrices
            for (int k = 0; k < lengthOfArrays; k++)
                for (int i = 0; i < lengthOfArrays; i++)
                    for (int j = 0; j < lengthOfArrays; j++)
                    {
                        //pini[i][j][k] = xi[j];
//                    pini[i][j][k] =  p.getXi(j)   ;
                        p.setPini(p.getXi(j), i, j, k);

//                    pinj[i][j][k] = yj[i];
                        p.setPinj(p.getYj(i), i, j, k);

//                    pink[i][j][k] = zk[k];
                        p.setPink(p.getZk(k), i, j, k);
                    }
            //////////////////////////////////////////////////////////////////////////////

//            Log.i("FAEP", " [i,j,k]=meshgrid(xi,yj,zk) " + (System.nanoTime() - startTime  ));



            //// =========================================================
            //// ===d=sqrt(i.^2+j.^2+k.^2);       % Equation 5=====

            for (int k = 0; k < lengthOfArrays; k++)
                for (int i = 0; i < lengthOfArrays; i++)
                    for (int j = 0; j < lengthOfArrays; j++)
                    {
                        //////////////////// % Equation 5=====

                        //pini_squared[i][j][k] = Math.pow(pini[i][j][k], 2);
                        p.setPini_squared(
                                Math.pow(p.getPini(i, j, k), 2),
                                i, j, k);

//                    pinj_squared[i][j][k] = Math.pow(pinj[i][j][k], 2);
                        p.setPinj_squared(
                                Math.pow(p.getPinj(i, j, k), 2),
                                i, j, k);

//                    pink_squared[i][j][k] = Math.pow(pink[i][j][k], 2);
                        p.setPink_squared(
                                Math.pow(p.getPink(i, j, k), 2),
                                i, j, k);
                    }
//            Log.i("FAEP", " sqrt(i.^2+j.^2+k.^2);% Equation 5 : " + (System.nanoTime() - startTime  ));
            //////////////////////////////////////////////////////////////////////////////


            ////////////time=round(fs*d/343)+1;            % Similar to Equation 6
            for (int i = 0; i < lengthOfArrays; i++)
            {
                for (int j = 0; j < lengthOfArrays; j++)
                {
                    for (int k = 0; k < lengthOfArrays; k++)
                    {
//                    d[i][j][k] = Math.sqrt(pini_squared[i][j][k] + pinj_squared[i][j][k] + pink_squared[i][j][k]);
                        p.setD(Math.sqrt(
                                        p.getPini_squared(i, j, k) +
                                                p.getPinj_squared(i, j, k) +
                                                p.getPink_squared(i, j, k)
                                ),
                                i, j, k);

//                    double temp = Math.round(d[i][j][k] * 10000) / 10000.0;
                        double temp = Math.round(p.getD(i, j, k) * 10000) / 10000.0;

//                    d[i][j][k] = temp;
                        p.setD(temp, i, j, k);

//                    time[i][j][k] = (int) Math.round( (fs * d[i][j][k] / 343) + 1); ///time=round(fs*d/343)+1;
                        int tmp = (int) Math.round((p.getFs() * p.getD(i, j, k) / 343) + 1);
                        p.setTime(tmp, i, j, k);
                    }
                }
            }


//            Log.i("FAEP", "time=round(fs*d/343)+1;   Similar to Equation 6: " + (System.nanoTime() - startTime  ));
            ///////////////max min//////////////////////////////////////
//        max = time[0][0][0];
            p.setMax(p.getTime(0, 0, 0));

//        min = time[0][0][0];
            p.setMin(p.getTime(0, 0, 0));

            for (int i = 0; i < lengthOfArrays; i++)
                for (int j = 0; j < lengthOfArrays; j++)
                    for (int k = 0; k < lengthOfArrays; k++)
                    {
//                    if (time[i][j][k] > max)
//                        max = time[i][j][k];
                        if (p.getTime(i, j, k) > p.getMax())
                            p.setMax(p.getTime(i, j, k));


//                    if (time[i][j][k] < min)
//                        min = time[i][j][k];

                        if (p.getTime(i, j, k) < p.getMin())
                            p.setMin(p.getTime(i, j, k));
                    }

//        output1+="--time[max]--.   Number of samples in function h = "  +max + "\n --time[min]--.  h=1 is in sample = "+min;

            ///////////////max min//////////////////////////////////////


            //////////////////[e,f,g]=meshgrid(nn, nn, nn);    % convert vectors to 3D matrices ok

            for (int k = 0; k < lengthOfArrays; k++)
            {
                for (int i = 0; i < lengthOfArrays; i++)
                {
                    for (int j = 0; j < lengthOfArrays; j++)
                    {
//                    e[i][j][k] = n[j];
                        p.setE(p.getN(j), i, j, k);

//                    f[i][j][k] = n[i];
                        p.setF(p.getN(i), i, j, k);

//                    g[i][j][k] = n[k];
                        p.setG(p.getN(k), i, j, k);

                    }
                }
            }
//            Log.i("FAEP", "[e,f,g]=meshgrid(nn, nn, nn);% convert vectors to 3D matrices :  " + (System.nanoTime() - startTime  ));
////////////////////////////////////////////////////////////////////////////////////


            /////////////[e,f,g]=meshgrid(nn, nn, nn);    % convert vectors to 3D matrices
            /////////c=r.^(abs(e)+abs(f)+abs(g));   % Equation 9

            for (int i = 0; i < lengthOfArrays; i++)
            {
                for (int j = 0; j < lengthOfArrays; j++)
                {
                    for (int k = 0; k < lengthOfArrays; k++)
                    {
//                    double add = Math.abs(e[i][j][k]) + Math.abs(f[i][j][k]) + Math.abs(g[i][j][k]);
                        double add = Math.abs(p.getE(i, j, k)) + Math.abs(p.getF(i, j, k)) + Math.abs(p.getG(i, j, k));


//                    c[i][j][k] = Math.pow(reflCoeffs, add);
                        p.setC(Math.pow(p.getReflCoeffs(), add), i, j, k);

                        /////////// e=c./d;                               % Equivalent to Equation 10
//                    e2[i][j][k] = c[i][j][k] / d[i][j][k];
                        p.setE2(p.getC(i, j, k) / p.getD(i, j, k), i, j, k);
                    }
                }
            }
//            Log.i("FAEP", "c=r.^(abs(e)+abs(f)+abs(g));% Equation 9 : " + (System.nanoTime() - startTime  ));


            //////////============================ΑΡΧΗ=====================
            ///////////         Όλο αυτό είναι για την εξισωση 11. κοιτα και σημειωσεις
            ////////////////////////////////////////////////////////
            /// βάζουμε το  sorted time 3δ πινακα σε εναν 1δ και μετα
            /// οτι και παραπανω με λιστα
            int indexCubed = lengthOfArrays * lengthOfArrays * lengthOfArrays;

            ArrayList<Double> sortedTime2 = new ArrayList<Double>(indexCubed);

            for (int i = 0; i < indexCubed; i++) ///initialisation of array
                sortedTime2.add(0.0);

            int timeSortedId = 0;
            for (int i = 0; i < lengthOfArrays; i++)
            {
                for (int j = 0; j < lengthOfArrays; j++)
                {
                    for (int k = 0; k < lengthOfArrays; k++)
                    {
//                    sortedTime2.set(timeSortedId, (double)time[i][j][k]);
                        sortedTime2.set(timeSortedId, (double) p.getTime(i, j, k));
                        timeSortedId++;
                    }
                }
            }

            /////-----------------------------
            ////////////sort here   σορταρουμε ascended //////////// 0, 1, 2, 3, 4, 5, 6,...
            double tempVar_b;
            for (int i = 0; i < sortedTime2.size() - 1; i++)
            {
                for (int j = 1; j < sortedTime2.size() - i; j++)
                {
                    if (sortedTime2.get(j - 1) > sortedTime2.get(j))
                    {
                        tempVar_b = sortedTime2.get(j - 1);
                        sortedTime2.set((j - 1), sortedTime2.get(j));
                        sortedTime2.set(j, tempVar_b);
                    }
                }
            }
//        for (int i=0; i<sortedTime2.size();i++)
//            output1 += "\n o sorting time array ascending " + sortedTime2.get(i);

//////////=========================================================


//////////=========================================================
            /// βάζουμε το  sorted time 3δ πινακα σε εναν 1δ και μετα
            /// οτι και παραπανω με λιστα

            ArrayList<Double> sorted_E2 = new ArrayList<Double>(indexCubed);

            for (int i = 0; i < indexCubed; i++)///initialisation of array
                sorted_E2.add(0.0);

            int e2SortedId = 0;

            for (int i = 0; i < lengthOfArrays; i++)
            {
                for (int j = 0; j < lengthOfArrays; j++)
                {
                    for (int k = 0; k < lengthOfArrays; k++)
                    {
//                    sorted_E2.set(e2SortedId, (double)e2[i][j][k]);
                        sorted_E2.set(e2SortedId, (double) p.getE2(i, j, k));
                        e2SortedId++;
                    }
                }
            }

            ////////////sort here   σορταρουμε  descending/////////100, 99,98...0
            /////tora prepei na sortaroume ti lis
            double tempVar_c;
            for (int i = 0; i < sorted_E2.size() - 1; i++)
            {
                for (int j = 1; j < sorted_E2.size() - i; j++)
                {
                    if (sorted_E2.get(j - 1) < sorted_E2.get(j))
                    {
                        tempVar_c = sorted_E2.get(j - 1);
                        sorted_E2.set((j - 1), sorted_E2.get(j));
                        sorted_E2.set(j, tempVar_c);
                    }
                }
            }

//        for (int i=0; i<sorted_E2.size();i++)
//            output1 += "\n o sorting time array descending " + sorted_E2.get(i);
//////////=========================================================


//        int intMax = (int) max + 1; // int max ειναι  o maximum χρονος
            intMax = (int) p.getMax() + 1;

            // δηλωση του h, της κρουστικης δηλαδή με μηκος οσο και το max
            // δηλαδή οσος είναι και ο χρονος.
            ArrayList<Double> h = new ArrayList<Double>(intMax);
//        output1+="\n Αρχικό size της Λιστας "+ h.size();


            for (int i = 0; i < intMax; i++) // αρχικοποιηση Λιστας
                h.add(0.0);

            int intTime;
            for (int i = 0; i < sortedTime2.size(); i++)
            {
                double dTime = sortedTime2.get(i);
                intTime = (int) dTime;
                h.set(intTime, sorted_E2.get(i));
            }

//////////===========================ΤΕΛΟΣ=====================
//            Log.i("FAEP", "ΤΕΛΟΣ : " + (System.nanoTime() - startTime  ));


////////// Εξισωση 12  % Scale output    h=h/max(abs(h));


//        abs(h)
            for (int i = 0; i < h.size(); i++)
            {
                double temp = h.get(i);
                temp = Math.abs(temp);
                h.set(i, temp);
            }
//      βρες το max  των απ. τιμών του πινακα h , (της κρουστικής)    max(abs(h))
            double maxOfh = 0;
            for (int i = 0; i < h.size(); i++)
            {
                if (h.get(i) > maxOfh)
                {
                    maxOfh = Math.abs(h.get(i));
                }
            }

            //διαιρεση για την γινει scale η κρουστικης  h=h/max(abs(h));
            for (int i = 0; i < h.size(); i++)
            {
                if (h.get(i) != 0)
                {
                    h.set(i, h.get(i) / maxOfh);
                }
            }

            ///////////////
            p.sethFinal(h);
//            Log.i("FAEP", "h ready : " + (System.nanoTime() - startTime  ));


            //for(int i =0; i<20; i++)
            // Log.i("FAEP", " fundamendal F " + p.gethFinal(2735));
            // Log.i("FAEP", " fundamendal F " + p.gethFinal(2736));
            // Log.i("FAEP", " fundamendal F " + p.gethFinal(2737));
            /////////


            p.setIRCalculated(true);
        }
        catch (Exception e)
        {
            Log.i("FAEP", " Unexpected error in calculation of the impulse response");
            Log.i("FAEP", e.toString());

            p.setIRCalculated(false);
        }

    }


    //////////////////////////////////////////////////////////////

    public double c80()
    {
        Log.i("FAEP", " intMax " +intMax); // μεγιστο μεγεθος κρουστικής σε samples

        int fundPos=0;  // the position of the fundamental

        //the first position that is !=0 is the fundamental
        for (int i=0; i<p.gethFinal().size() ; i++)
            if(p.gethFinal(i) != 0)
            {
                fundPos=i;
                break;
            }

        Log.i("FAEP", " θεση της   " + fundPos); ///θεση της fundamental
        Log.i("FAEP", " τι περιεχει η h στην θεση της fundamental " + p.gethFinal(fundPos));
//        Log.i("FAEP", " τι περιεχει η h στην θεση της fundamental " + p.gethFinal(2736));



        double hTempNumerator = 0; // temporary variable. Helds the numerator (αριθμητης).see C80 equation
        double hTempDenominator = 0; // temporary variable. Helds the denominator (παρoνομαστης).see C80 equation


        // ποσα samples ειναι τα 80 ms.
        // see equations in thesis
        int  numOfSamplesFor80ms= (int)  ( p.getFs() * 0.08 );
        Log.i("FAEP", " time80ms " +numOfSamplesFor80ms);



        // Ξεκινάει να υπολογίζει απο την θέση που είναι η fundamental
        // μέχρι τη θέση στην οποια τελειώνουν τα 80 ms.
        // πχ αν η fundamental της κρουστικής ειναι στην θέση 3000
        // το for θα τρέξει μεχρι τη θεση 3000 + ( αριθμός samples που αντιστοιχει σε 80ms)
        for( int i= fundPos ; i < fundPos + numOfSamplesFor80ms; i++)
            hTempNumerator+= Math.pow( p.gethFinal(i), 2);

        Log.i("FAEP", " hTempNumerator " + hTempNumerator);

        //////////////////////////////////////
        Log.i("FAEP", "\n");
        Log.i("FAEP", " απο εδω ξεκναει ο υπολογισμος των 80ms και μετα " +fundPos +numOfSamplesFor80ms+1);
        Log.i("FAEP", " μέχρι την θεση  " + p.gethFinal().size() );
        //////////////////////////////////////


        // οτι και παραπανω
        for (int i = fundPos +numOfSamplesFor80ms+1; i < p.gethFinal().size(); i++)
//            if (  p.gethFinal(i)!=0)
            hTempDenominator += Math.pow ( p.gethFinal(i), 2);


        Log.i("FAEP", " hTempDenominator " + hTempDenominator);

        Log.i("FAEP", " c802  " + (10 * Math.log10(hTempNumerator / hTempDenominator) ) );

        return (10 * Math.log10(hTempNumerator / hTempDenominator) );
    }



    public double c50()
    {
        Log.i("FAEP", " intMax " +intMax); // μεγιστο μεγεθος κρουστικής σε samples

        int fundTime=0;  // the position of the fundamental

        //the first position that is !=0 is the fundamental
        for (int i=0; i<p.gethFinal().size() ; i++)
            if(p.gethFinal(i) != 0)
            {
                fundTime=i;
                break;
            }

        Log.i("FAEP", " θεση της   " + fundTime); ///θεση της fundamental
        Log.i("FAEP", " τι περιεχει η h στην θεση της fundamental " + p.gethFinal(2736));



        double hTempNumerator = 0; // temporary variable. Helds the numerator (αριθμητης).see C50 equation
        double hTempDenominator = 0; // temporary variable. Helds the denominator (παρoνομαστης).see C50 equation


        // ποσα samples ειναι τα 50 ms.
        // see equations in thesis
        int  time50ms= (int)  ( p.getFs() * 0.05 );
        Log.i("FAEP", " time50ms " +time50ms);



        // Ξεκινάει να υπολογίζει απο την θέση που είναι η fundamental
        // μέχρι τη θέση στην οποια τελειώνουν τα 50 ms.
        // πχ αν η fundamental της κρουστικής ειναι στην θέση 3000
        // το for θα τρέξει μεχρι τη θεση 3000 + ( αριθμός samples που αντιστοιχει σε 50ms)
        for( int i= fundTime ; i < fundTime + time50ms; i++)
            hTempNumerator+= Math.pow( p.gethFinal(i), 2);

        Log.i("FAEP", " hTempNumerator " + hTempNumerator);

        //////////////////////////////////////
        Log.i("FAEP", "\n");
        Log.i("FAEP", " απο εδω ξεκναει ο υπολογισμος των 80ms και μετα " +fundTime +time50ms+1);
        Log.i("FAEP", " μέχρι την θεση  " + p.gethFinal().size() );
        //////////////////////////////////////


        // οτι και παραπανω
        for (int i = fundTime +time50ms+1; i < p.gethFinal().size(); i++)
            if (  p.gethFinal(i)!=0)
                hTempDenominator += Math.pow ( p.gethFinal(i), 2);


        Log.i("FAEP", " hTempDenominator " + hTempDenominator);

        return (10 * Math.log10(hTempNumerator / hTempDenominator) );
    }



    public double c802()
    {
        Log.i("FAEP", " intMax " +intMax); // μεγιστο μεγεθος κρουστικής σε samples

        int fundTime=0;  // the position of the fundamental

        //the first position that is !=0 is the fundamental
        for (int i=0; i<p.gethFinal().size() ; i++)
            if(p.gethFinal(i) != 0)
            {
                fundTime=i;
                break;
            }

        Log.i("FAEP", " θεση της   " + fundTime); ///θεση της fundamental
        Log.i("FAEP", " τι περιεχει η h στην θεση της fundamental " + p.gethFinal(2736));



        double hTempNumerator = 0; // temporary variable. Helds the numerator (αριθμητης).see C80 equation
        double hTempDenominator = 0; // temporary variable. Helds the denominator (παρoνομαστης).see C80 equation


        // ποσα samples ειναι τα 80 ms.
        // see equations in thesis
        int  time80ms= (int)  ( p.getFs() * 0.08 );
        Log.i("FAEP", " time80ms " +time80ms);



        // Ξεκινάει να υπολογίζει απο την θέση που είναι η fundamental
        // μέχρι τη θέση στην οποια τελειώνουν τα 80 ms.
        // πχ αν η fundamental της κρουστικής ειναι στην θέση 3000
        // το for θα τρέξει μεχρι τη θεση 3000 + ( αριθμός samples που αντιστοιχει σε 80ms)
        for( int i= fundTime ; i < fundTime + time80ms; i++)
            hTempNumerator+= Math.pow( p.gethFinal(i), 2);

        Log.i("FAEP", " hTempNumerator " + hTempNumerator);

        //////////////////////////////////////
        Log.i("FAEP", "\n");
        Log.i("FAEP", " απο εδω ξεκναει ο υπολογισμος των 80ms και μετα " +fundTime +time80ms+1);
        Log.i("FAEP", " μέχρι την θεση  " + p.gethFinal().size() );
        //////////////////////////////////////


        // οτι και παραπανω
        for (int i = fundTime +time80ms+1; i < p.gethFinal().size(); i++)
            if (  p.gethFinal(i)!=0)
                hTempDenominator += Math.pow ( p.gethFinal(i), 2);


        Log.i("FAEP", " hTempDenominator " + hTempDenominator);

        Log.i("FAEP", " c80  " + (10 * Math.log10(hTempNumerator / hTempDenominator) ) );

        return (10 * Math.log10(hTempNumerator / hTempDenominator) );


    }

}
