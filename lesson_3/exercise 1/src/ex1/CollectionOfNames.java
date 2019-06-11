package ex1;

import java.util.HashMap;

public class CollectionOfNames {
    private HashMap<String,Integer> Names=new HashMap<>();
    private int quantity = 0;
    public CollectionOfNames(String...mass)
    {
        for(String name: mass)
        {
            Integer temp=Names.get(name);
            Names.put(name,temp==null?1:temp+1);
            quantity++;
        }
    }
    @Override
    public String toString(){
        return Names.toString()+"\nВсего в списке имен: "+Integer.toString(quantity);
    }
}
