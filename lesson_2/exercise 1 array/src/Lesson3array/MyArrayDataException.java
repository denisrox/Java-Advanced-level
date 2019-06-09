package Lesson3array;

public class MyArrayDataException extends NumberFormatException{
    public MyArrayDataException(int ArrayX, int ArrayY)
    {
        super("Ошибка. Array["+ArrayX+"][" + ArrayY+"] не число!");
    }
}
