package regex;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {

    public static void main(String[] args) {
        int numberOfSuccessful=0;
        StringBuilder pass = new StringBuilder();
        ArrayList<String[]> listLiteral=new ArrayList<String[]>();
        SetLeteral(listLiteral);
        Scanner sc = new Scanner(System.in);
        //String listLiteral[]={".*\\d.*",".*[a-z].*",".*[A-Z].*",".{8}.*","[^абв]+",".*\\W.*"};
        do {
            System.out.println("Введите пароль");
            pass=new StringBuilder(sc.nextLine());
            numberOfSuccessful=0;
            for(int i = 0;i<listLiteral.size();i++)
            {
                Pattern pattern = Pattern.compile(listLiteral.get(i)[0]);
                Matcher matcher = pattern.matcher(pass);
                if(matcher.matches())numberOfSuccessful++;
                else System.out.println(listLiteral.get(i)[1]);
            }
        }while (numberOfSuccessful<listLiteral.size());
        System.out.println("Пароль "+pass+ " подходит!");
        sc.close();


    }
    static void SetLeteral(ArrayList<String[]> listLiteral)
    {
        listLiteral.add(new String[]{".*\\d.*","В пароле должна быть хотя бы 1 цифра!!!"});
        listLiteral.add(new String[]{".*[a-z].*","В пароле должна быть хотя бы 1 английская буква нижнего регистра!!!"});
        listLiteral.add(new String[]{".*[A-Z].*","В пароле должна быть хотя бы 1 большая английская буква!!!"});
        listLiteral.add(new String[]{".{8}.*","В пароле должно быть 8 и более символов!!!"});
        listLiteral.add(new String[]{"[^а-яА-Я]+","В пароле не должно быть русский букв!!!"});
        listLiteral.add(new String[]{".*\\W.*","В пароле должен быть минимум 1 спецсимвол!!!"});
    }


}
