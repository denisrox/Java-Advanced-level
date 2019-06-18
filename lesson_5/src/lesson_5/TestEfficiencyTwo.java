package lesson_5;

import java.util.Arrays;

public class TestEfficiencyTwo {
    private float[]arr;;
    int size;
    public TestEfficiencyTwo(int size)
    {
        arr=new float[size];
        this.size=size;
        for(int i=0;i<size;i++)
            arr[i]=1;
        myThready.arr=arr;
    }
    public void startRealization(int amountThread) throws amountThreadException {
        if(size%amountThread!=0) throw new amountThreadException();
        myThready.counter=amountThread;
        long timeWork=System.currentTimeMillis();
        if(amountThread==1)
        {
            for(int i=0;i<arr.length;i++)
                arr[i]=(float)(arr[i]*Math.sin(0.2f+i/5)*Math.cos(0.2f+i/5)*Math.cos(0.4f+i/2));
        }
        else
        {
            int sizeOfThread=size/amountThread;
            myThready[] threadTest=new myThready[amountThread];
            for(int i = 0;i<amountThread;i++) {
                threadTest[i]=new myThready(i*sizeOfThread);
                threadTest[i].start();
            }

            for (myThready thread:threadTest) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(System.currentTimeMillis()-timeWork);
    }
}

class myThready extends Thread    {
    static float [] arr;
    static int counter;
    private int startPosition;
    public myThready(int startPosition)
    {
        this.startPosition=startPosition;
    }


    @Override
    public void run() {
        for(int i=startPosition;i<startPosition+arr.length/counter;i++)
            arr[i]=(float)(arr[i]*Math.sin(0.2f+i/5)*Math.cos(0.2f+i/5)*Math.cos(0.4f+i/2));
    }
}
