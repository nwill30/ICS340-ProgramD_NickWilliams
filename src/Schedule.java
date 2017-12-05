import java.util.ArrayList;

public class Schedule {

    private ArrayList<Semester> semesters;

    public Schedule(){
        this.semesters = new ArrayList<>();
        this.semesters.add(new Semester("20193"));
        this.semesters.add(new Semester("20195"));
        this.semesters.add(new Semester("20201"));
        this.semesters.add(new Semester("20203"));
        this.semesters.add(new Semester("20205"));
        this.semesters.add(new Semester("20211"));
        this.semesters.add(new Semester("20213"));
        this.semesters.add(new Semester("20215"));
        this.semesters.add(new Semester("20221"));
        this.semesters.add(new Semester("20223"));
        this.semesters.add(new Semester("20225"));

    }

    public Semester getSemesterByIndex(int i ){

        return semesters.get(i);
    }
}
