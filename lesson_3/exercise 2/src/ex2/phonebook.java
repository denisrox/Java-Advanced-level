package ex2;//в принципе, можно сделать ещё 1 класс исключения и кидать его когда пытаются добавить номер, который уже существует, мол невозможно использовать два номера
            //но для этого придётся сделать отдельный список HashSet существующих номеров, что в принципе не сложно и могу дорелизовать)
import java.util.HashMap;
import java.util.HashSet;

public class phonebook {
    private HashMap<String, HashSet<Long>> book=new HashMap<>();
    public void add(String name, Long phone)
    {
        if(book.get(name)==null) {
            HashSet<Long> temp=new HashSet<>();
            temp.add(phone);
            book.put(name,temp);
        }
        else{
            book.get(name).add(phone);
        }
    }
    public String get(String name)
    {
        return name+": "+book.get(name).toString();
    }
}
