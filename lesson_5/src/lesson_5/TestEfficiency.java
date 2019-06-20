package lesson_5;

import java.util.Arrays;

public class TestEfficiency {
    private float[]arr;;
    int size;
    public TestEfficiency(int size)
    {

        arr=new float[size];
        this.size=(size<1)?1:size;
        for(int i=0;i<size;i++)
            arr[i]=1;
    }
    public void startRealization(int amountThread) throws amountThreadException {
        if(size%amountThread!=0) throw new amountThreadException();
        long timeWork=System.currentTimeMillis();
        if(amountThread==1)
        {
            for(int i=0;i<arr.length;i++)
                arr[i]=(float)(arr[i]*Math.sin(0.2f+i/5)*Math.cos(0.2f+i/5)*Math.cos(0.4f+i/2));
        }
        else
        {
            int sizeOfThread=size/amountThread;
            ThreadForTest[] threadTest=new ThreadForTest[amountThread];
            for(int i = 0;i<amountThread;i++) {

                threadTest[i] = new ThreadForTest(Arrays.copyOfRange(arr, i * sizeOfThread, (i + 1) * sizeOfThread));
                threadTest[i].start();
            }
            for (ThreadForTest thread:threadTest) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for(int i = 0;i<amountThread;i++) {
                System.arraycopy(threadTest[i].getArray(), 0, arr, i * sizeOfThread, sizeOfThread);
            }
        }
        System.out.println(System.currentTimeMillis()-timeWork);
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

