package lesson_5;

import java.lang.Math;
import java.util.Arrays;


public class mainfunction
{

    public static void main(String[] args)
    {
        System.out.println("тестирование первого метода");
        TestEfficiency test=new TestEfficiency(10000000);
        try {
            test.startRealization(1); //при counter = 1 новый поток не создаетс
            test.startRealization(2);
            test.startRealization(5);
            test.startRealization(10);
            test.startRealization(100);
            test.startRealization(200);
        }catch (amountThreadException e)
        {
            System.out.println(e.toString());
        }

        System.out.println("тестирование второго метода");
        TestEfficiencyTwo test2=new TestEfficiencyTwo(10000000);
        try {
            test2.startRealization(1);//при counter = 1 новый поток не создаетс
            test2.startRealization(2);
            test2.startRealization(5);
            test2.startRealization(10);
            test2.startRealization(100);
            test2.startRealization(200);
        }catch (amountThreadException e)
        {
            System.out.printf("Необходимо поменять кол-во потоков");
        }

    }
}




