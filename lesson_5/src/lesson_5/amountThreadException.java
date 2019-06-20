package lesson_5;

public class amountThreadException extends Exception {
    public amountThreadException(){
        super("Для чистоты эксперимента количество потоков должно быть четно размерности массива!");
    }
}
