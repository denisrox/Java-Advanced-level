package ex2;

public class main {
    public static void main(String[] args) {
        phonebook book = new phonebook();
        book.add("Денис", 79264205206L);
        book.add("Вася", 79264204206L);
        book.add("Денис", 79264235206L);
        book.add("Рома", 79264205706L);
        book.add("Дима", 79254205206L);
        book.add("Катя", 79261204206L);
        book.add("Ира", 79264235206L);
        book.add("Рома", 79266205706L);
        System.out.println(book.get("Денис"));
        System.out.println(book.get("Рома"));
        System.out.println(book.get("Ира"));
    }
}
