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
    public void startRealization(int counter)
    {
        myThready.counter=counter;
        long a;
        if(counter==1)
        {
            a=System.currentTimeMillis();
            for(int i=0;i<arr.length;i++)
                arr[i]=(float)(arr[i]*Math.sin(0.2f+i/5)*Math.cos(0.2f+i/5)*Math.cos(0.4f+i/2));
            System.out.println(System.currentTimeMillis()-a);
        }
        else
        {
            a=System.currentTimeMillis();
            myThready[] threadTest=new myThready[counter];
            for(int i = 0;i<counter;i++) {
                threadTest[i]=new myThready(i*size/counter);
                threadTest[i].start();
            }

            for (myThready thread:threadTest) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(System.currentTimeMillis()-a);
        }
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
