import java.util.ArrayList;

public class Course {

    private String courseName;
    private ArrayList<String> fallSemesterDays;
    private ArrayList<String> springSemesterDays;
    private ArrayList<String> summerSemesterDays;

    public Course(String courseName, String fallSemesterDays, String springSemesterDays, String summerSemesterDays) {

        this.courseName = courseName;
        this.fallSemesterDays = new ArrayList<>();
        this.springSemesterDays = new ArrayList<>();
        this.summerSemesterDays = new ArrayList<>();
        addCourseDays(this.fallSemesterDays, fallSemesterDays);
        addCourseDays(this.springSemesterDays, springSemesterDays);
        addCourseDays(this.summerSemesterDays, summerSemesterDays);
    }

    private void addCourseDays(ArrayList<String> semester, String days) {

        if(!days.equals("-")){
            for(int i = 0;i<days.length();i++){
                semester.add(String.valueOf(days.charAt(i)));
            }
        }
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public ArrayList<String> getFallSemesterDays() {
        return fallSemesterDays;
    }

    public void setFallSemesterDays(ArrayList<String> fallSemesterDays) {
        this.fallSemesterDays = fallSemesterDays;
    }

    public ArrayList<String> getSpringSemesterDays() {
        return springSemesterDays;
    }

    public void setSpringSemesterDays(ArrayList<String> springSemesterDays) {
        this.springSemesterDays = springSemesterDays;
    }

    public ArrayList<String> getSummerSemesterDays() {
        return summerSemesterDays;
    }

    public void setSummerSemesterDays(ArrayList<String> summerSemesterDays) {
        this.summerSemesterDays = summerSemesterDays;
    }

    public boolean courseOffered(int semester, String day){

        switch (semester){
            case 0 : return this.fallSemesterDays.contains(day);
            case 1 : return this.springSemesterDays.contains(day);
            case 2 : return this.summerSemesterDays.contains(day);
        }

        return false;
    }
}
