package Lesson3array;


public  class StringToIntArroy {
    static int arrayLenght=4;
    static int arrayWidht=4;
    public static void StringToInt(String[][] mass) throws MyArraySizeException
    {
        if(mass.length!=arrayWidht && mass[0].length!=arrayLenght)
            throw new MyArraySizeException(arrayLenght,arrayWidht);
        Summ(mass);

    }
    private static void Summ(String[][] array)
    {
        int SummLines=0;
        int SummAll=0;
        for(int i = 0;i<arrayLenght;i++) {
            for (int j = 0;j<arrayLenght;j++)
                try {
                    SummLines+=Integer.parseInt(array[i][j]);
                }catch (NumberFormatException e)
                {
                    try {
                        throw new MyArrayDataException(i,j);
                    } catch (MyArrayDataException ex) {
                        ex.printStackTrace();
                    }
                }
            System.out.println(SummLines);
            SummAll+=SummLines;
            SummLines=0;
        }
        System.out.println("========\n"+"Общая сумма: "+SummAll);
    }
}
