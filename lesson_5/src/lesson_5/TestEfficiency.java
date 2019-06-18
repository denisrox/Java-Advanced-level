package lesson_5;

import java.util.Arrays;

public class TestEfficiency {
    private float[]arr;;
    int size;
    public TestEfficiency(int size)
    {
        arr=new float[size];
        this.size=size;
        for(int i=0;i<size;i++)
            arr[i]=1;
    }
    public void startRealization(int counter)
    {
        long timeWork;
        if(counter==1)
        {
            timeWork=System.currentTimeMillis();
            for(int i=0;i<arr.length;i++)
                arr[i]=(float)(arr[i]*Math.sin(0.2f+i/5)*Math.cos(0.2f+i/5)*Math.cos(0.4f+i/2));
            System.out.println(System.currentTimeMillis()-timeWork);
        }
        else
        {
            timeWork=System.currentTimeMillis();
            ThreadForTest[] threadTest=new ThreadForTest[counter];
            for(int i = 0;i<counter;i++) {
                threadTest[i] = new ThreadForTest(Arrays.copyOfRange(arr, i * size / counter, (i + 1) * size / counter));
                threadTest[i].start();
            }
            for (ThreadForTest thread:threadTest) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for(int i = 0;i<counter;i++) {
                System.arraycopy(threadTest[i].getArray(), 0, arr, i * size / counter, size/counter);
            }
            System.out.println(System.currentTimeMillis()-timeWork);
        }
    }
}
class ThreadForTest extends Thread    {
    float [] arr;
    public ThreadForTest(float[]arr)
    {
        this.arr=arr;
    }
    @Override
    public void run() {
        for(int i=0;i<arr.length;i++)
            arr[i]=(float)(arr[i]*Math.sin(0.2f+i/5)*Math.cos(0.2f+i/5)*Math.cos(0.4f+i/2));
    }
    public float[] getArray()
    {
        return arr;
    }
}

