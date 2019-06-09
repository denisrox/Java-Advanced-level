package Lesson3array;

import java.util.Random;

public class main {
    public static void main(String[] args) {
        String array[][]=new String[4][4];
//        array[0][0]="35";
//        array[0][1]="1";
//        array[0][2]="7";
//        array[0][3]="17";
//        array[1][0]="4";
//        array[1][1]="3f5";
//        array[1][2]="35";
//        array[1][3]="38";
//        array[2][0]="35";
//        array[2][1]="35";
//        array[2][2]="Helloworld";
//        array[2][3]="35";
//        array[2][0]="7245";
//        array[3][0]="123";
//        array[3][1]="346";
//        array[3][2]="3345";
//        array[3][3]="3S";
        createArrayForTesting(array);
        ShowArray(array);
        try {
            StringToIntArroy.StringToInt(array);
        } catch (MyArraySizeException e) {
            e.printStackTrace();
        }

    }
    public static void createArrayForTesting(String array[][])
    {
        int ChanceOfError=10;
        Random random = new Random();
        for(int i = 0;i<array.length;i++)
            for (int j = 0;j<array[0].length;j++)
            {
                int randI = random.nextInt(100);
                array[i][j]=(random.nextInt(100)>ChanceOfError)?Integer.toString(randI):"helloworld";
            }

    }
    public static void ShowArray(String array[][])
    {
        for(String[] massWidht:array) {
            for (String mass : massWidht)
                System.out.print(mass + " ");
            System.out.println("");
        }
    }
}
