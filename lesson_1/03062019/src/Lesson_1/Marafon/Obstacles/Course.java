package Lesson_1.Marafon.Obstacles;

import Lesson_1.Marafon.Animals.Competitor;

public class Course {
    private Obstacle[] course;
    public Course(Obstacle...course)
    {
        this.course=new Obstacle[course.length];
        for(int i = 0;i<course.length;i++)
            this.course[i]=course[i];
    }
    public void begin(Competitor c)
    {
        for (Obstacle o : course) {
            o.doIt(c);
            if (!c.isOnDistance()) break;
        }
    }
}
