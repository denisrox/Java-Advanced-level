package Lesson3array;

public class MyArraySizeException extends Exception{
    public MyArraySizeException(int arrayLenght, int arrayWidht)
    {
        super("Длинна массива должна быть "+arrayLenght+" количество строк должно быть " + arrayWidht);
    }
}