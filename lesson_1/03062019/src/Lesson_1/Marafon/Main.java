package Lesson_1.Marafon;

import Lesson_1.Marafon.Animals.*;
import Lesson_1.Marafon.Obstacles.Course;
import Lesson_1.Marafon.Obstacles.Cross;
import Lesson_1.Marafon.Obstacles.Wall;

public class Main {
    public static void main(String[] args) {
        Team competitors=new Team(new Human("Боб"), new Cat("Барсик"), new Dog("Бобик"));
        Course courses = new Course(new Cross(80), new Wall(2), new Wall(1), new Cross(120));
        competitors.start(courses);
        competitors.showResults();
    }
}