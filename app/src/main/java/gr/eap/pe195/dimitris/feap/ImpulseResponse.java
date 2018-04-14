package gr.eap.pe195.dimitris.feap;

import java.util.ArrayList;


public class ImpulseResponse
{

    final int THREE = 3;        // 1 IMAGES
    final int FIVE = 5;         // 2 IMAGES
    final int SEVEN = 7;        // 3 IMAGES
    final int NINE = 9;         // 4 IMAGES
    final int ELEVEN = 11;      // 5 IMAGES
    final int THIRTEEN = 13;    // 6 IMAGES     (2*number Of images)+1  (6*2)+1=13
    final int FIFTEEN = 15;     // 7 IMAGES
    final int SEVENTEEN = 17;   // 8 IMAGES
    final int NINETEEN = 19;    // 9 IMAGES
    final int TWENTYONE = 21;   // 10 IMAGES   (2*number Of images)+1  (10*2) + 1 = 21

//    final int MAX_SIZE_OF_TIME = 9261; //  21 * 21 *21 = 9261
    /////////Declarations for IR Calculations

    private double[] n = new double[TWENTYONE];   //% Index for the sequence
    private double[] rms = new double[TWENTYONE];  // required for Calculations in equations 2,3,& 4
    private double[] srcs = new double[TWENTYONE]; // required for Calculations in equations 2,3,& 4


    private double[] src = new double[THREE];// position of source
    private double[] mic  = new double[THREE];//position of mic
    private double[] rm = new double[THREE]; // dimension of room

    private double[] xi = new double[TWENTYONE]; // required for equation 2
    private double[] yj = new double[TWENTYONE]; // required for equation 2
    private double[] zk = new double[TWENTYONE]; // required for equation 2


    private double[][][] pini = new double[TWENTYONE][TWENTYONE][TWENTYONE];  //[i,j,k]=meshgrid(xi,yj,zk);           % convert vectors to 3D matrices
    private double[][][] pinj = new double[TWENTYONE][TWENTYONE][TWENTYONE];  //[i,j,k]=meshgrid(xi,yj,zk);           % convert vectors to 3D matrices
    private double[][][] pink = new double[TWENTYONE][TWENTYONE][TWENTYONE];  //[i,j,k]=meshgrid(xi,yj,zk);           % convert vectors to 3D matrices


    //// ===d=sqrt(i.^2+j.^2+k.^2);       % Equation 5=====
    private double[][][] pini_squared = new double[TWENTYONE][TWENTYONE][TWENTYONE];
    private double[][][] pinj_squared = new double[TWENTYONE][TWENTYONE][TWENTYONE];
    private double[][][] pink_squared = new double[TWENTYONE][TWENTYONE][TWENTYONE];




    private double max,min;  //

    private double[][][] d = new double[TWENTYONE][TWENTYONE][TWENTYONE]; //d=sqrt(i.^2+j.^2+k.^2); % Equation 5


    private int[][][] time = new int[TWENTYONE][TWENTYONE][TWENTYONE]; //time=round(fs*d/343)+1;  % Similar to Equation 6


    //////////////////[e,f,g]=meshgrid(nn, nn, nn);    % convert vectors to 3D matrices
    private double[][][] e = new double[TWENTYONE][TWENTYONE][TWENTYONE];
    private double[][][] f = new double[TWENTYONE][TWENTYONE][TWENTYONE];
    private double[][][] g = new double[TWENTYONE][TWENTYONE][TWENTYONE];



    /////////c=r.^(abs(e)+abs(f)+abs(g));   % Equation 9
    private  double[][][] c = new double[TWENTYONE][TWENTYONE][TWENTYONE];


    //////////////////////e=c./d;    Equivalent to Equation 10
    private double[][][] e2 = new double[TWENTYONE][TWENTYONE][TWENTYONE];





    private int fs;
    private int numOfImages;
    private double  reflCoeffs;


    private ArrayList<Double> hFinal = new ArrayList<Double>();  ////// Impulse response List of samples

   // private hMax

    private boolean isIRCalculated ;




    public double getMax()
    {
        return max;
    }

    public void setMax(double max)
    {
        this.max = max;
    }

    public double getMin()
    {
        return min;
    }

    public void setMin(double min)
    {
        this.min = min;
    }

    public int getFs()
    {
        return fs;
    }

    public void setFs(int fs)
    {
        this.fs = fs;
    }

    public int getNumOfImages()
    {
        return numOfImages;
    }

    public void setNumOfImages(int numOfImages)
    {
        this.numOfImages = numOfImages;
    }

    public double getReflCoeffs()
    {
        return reflCoeffs;
    }

    public void setReflCoeffs(double reflCoeffs)
    {
        this.reflCoeffs = reflCoeffs;
    }

    public double getRm(int index)
    {
        return rm[index];
    }

    public void setRm(double rmX, double rmY, double rmZ )
    {
        this.rm[0] = rmX;
        this.rm[1] = rmY;
        this.rm[2] = rmZ;
    }

    public double getMic(int index)
    {
        return mic[index];
    }

    public void setMic(double micX, double micY, double micZ)
    {
        this.mic[0] = micX;
        this.mic[1] = micY;
        this.mic[2] = micZ;
    }

    public double getSrc(int index)
    {
        return src[index];
    }

    public void setSrc(double srcX, double srcY, double srcZ )
    {
        this.src[0] = srcX;
        this.src[1] = srcY;
        this.src[2] = srcZ;
    }

    public double getN(int index)
    {
        return n[index];
    }

    public void setN(double n, int index)
    {
        this.n[index] = n;
    }

    public double getRms(int index)
    {
        return rms[index];
    }

    public void setRms(double rms, int index)
    {
        this.rms[index] = rms;
    }


    public double getSrcs(int index)
    {
        return srcs[index];
    }

    public void setSrcs(double srcs, int index)
    {
        this.srcs[index] = srcs;
    }

    public double getXi(int index )
    {
        return xi[index];
    }

    public void setXi(double xi, int index)
    {
        this.xi[index] = xi;
    }

    public double getYj(int index)
    {
        return yj[index];
    }

    public void setYj(double yj, int index)
    {
        this.yj[index] = yj;
    }

    public double getZk(int index)
    {
        return zk[index];
    }

    public void setZk(double zk, int index)
    {
        this.zk[index] = zk;
    }



    public double getPini(int i, int j, int k)
    {
        return pini[i][j][k];
    }

    public void setPini(double pini, int i, int j, int k)
    {
        this.pini[i][j][k] = pini;
    }



    public double getPinj(int i, int j, int k)
    {
        return pinj[i][j][k];
    }

    public void setPinj(double pinj, int i, int j, int k )
    {
        this.pinj[i][j][k] = pinj;
    }



    public double getPink(int i, int j, int k)
    {
        return pink[i][j][k];
    }

    public void setPink(double pink, int i, int j, int k)
    {
        this.pink[i][j][k] = pink;
    }


    public double getPini_squared(int i, int j, int k)
    {
        return pini_squared[i][j][k];
    }

    public void setPini_squared(double pini_squared, int i, int j, int k)
    {
        this.pini_squared[i][j][k] = pini_squared;
    }

    public double getPinj_squared(int i, int j, int k)
    {
        return pinj_squared[i][j][k];
    }

    public void setPinj_squared(double pinj_squared, int i, int j, int k)
    {
        this.pinj_squared[i][j][k] = pinj_squared;
    }

    public double getPink_squared(int i, int j, int k)
    {
        return pink_squared[i][j][k];
    }

    public void setPink_squared(double pink_squared, int i, int j, int k)
    {
        this.pink_squared[i][j][k] = pink_squared;
    }

    public double getD(int i, int j, int k)
    {
        return d[i][j][k];
    }

    public void setD(double d, int i, int j, int k)
    {
        this.d[i][j][k] = d;
    }

    public int getTime(int i, int j, int k)
    {
        return time[i][j][k];
    }

    public void setTime(int time, int i, int j, int k )
    {
        this.time[i][j][k] = time;
    }




    public double  getE(int i, int j, int k)
    {
        return e[i][j][k];
    }

    public void setE(double  e, int i, int j, int k)
    {
        this.e[i][j][k] = e;
    }

    public double  getF(int i, int j, int k)
    {
        return f[i][j][k];
    }

    public void setF(double  f, int i, int j, int k)
    {
        this.f[i][j][k] = f;
    }

    public double  getG(int i, int j, int k)
    {
        return g[i][j][k];
    }

    public void setG(double  g, int i, int j, int k)
    {
        this.g[i][j][k] = g;
    }






    public double getC(int i, int j, int k)
    {
        return c[i][j][k];
    }

    public void setC(double c, int i, int j, int k)
    {
        this.c[i][j][k] = c;
    }

    public double getE2(int i, int j, int k)
    {
        return e2[i][j][k];
    }

    public void setE2(double e2, int i, int j, int k)
    {
        this.e2[i][j][k] = e2;
    }


    public ArrayList<Double> gethFinal()
    {
        return hFinal;
    }

    public double gethFinal(int index)
    {
        return hFinal.get(index);
    }


    public void sethFinal(ArrayList<Double> hFinal)
    {
        this.hFinal = hFinal;
    }


    public boolean getIsIRCalculated()
    {
        return isIRCalculated;
    }

    public void setIRCalculated(boolean isIRCalculated)
    {
        this.isIRCalculated = isIRCalculated;
    }
}
