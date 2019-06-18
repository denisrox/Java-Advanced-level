package lesson_5;

import java.lang.Math;
import java.util.Arrays;


public class mainfunction
{

    public static void main(String[] args)
    {
        System.out.println("тестирование первого метода");
        TestEfficiency test=new TestEfficiency(10000000);
        test.startRealization(1);
        test.startRealization(2);
        test.startRealization(5);
        test.startRealization(10);
        System.out.println("тестирование второго метода");
        TestEfficiencyTwo test2=new TestEfficiencyTwo(10000000);
        test2.startRealization(1);
        test2.startRealization(2);
        test2.startRealization(5);
        test2.startRealization(10);

    }
}




