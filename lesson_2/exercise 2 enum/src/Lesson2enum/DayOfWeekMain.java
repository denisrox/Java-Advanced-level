package Lesson2enum;

public class DayOfWeekMain {
    public static void main(final String[] args)
    {
        System.out.println(getWorkingHours(DayOfWeek.MONDAY));
    }
    public static String getWorkingHours(DayOfWeek Day)
    {
        String msg;
        if (Day.ordinal()<5) msg="Сегодня "+Day.getRus()+ ". Значит нам осталось батражить " + Integer.toString((5-Day.ordinal())*8) +" часов";
        else
        {
            msg=(Day.ordinal()==5)?"Ура, суббота, отдыхаем":"Воскресенье, приходим в себя после отдыха";
        }
        return msg;
    }
    public enum DayOfWeek {
        MONDAY("Понедельник"),
        TUESDAY("Вторник"),
        WEDNESDAY("Среда"),
        THURSDAY("Четверг"),
        FRIDAY("Пятница"),
        SATURDAY("Суббота"),
        SUNDAY("Воскресенье");
        private String rus;
        DayOfWeek(String rus)
        {
            this.rus=rus;
        }
        private String getRus()
        {
            return rus;
        }
    }
}
