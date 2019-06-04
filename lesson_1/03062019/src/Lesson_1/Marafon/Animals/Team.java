package Lesson_1.Marafon.Animals;

import Lesson_1.Marafon.Obstacles.Course;

public class Team {
    private Competitor[] competitors;

    public Team(Competitor... competitors) {
        this.competitors = new Competitor[competitors.length];
        for (int i = 0; i < competitors.length; i++)
            this.competitors[i] = competitors[i];
    }

    public void start(Course courses) {
        for (Competitor c : competitors) {
            courses.begin(c);
        }
    }

    public void showResults() {
        for (Competitor c : competitors)
            c.info();

    }
}
