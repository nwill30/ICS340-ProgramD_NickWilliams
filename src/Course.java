import java.util.ArrayList;
import java.util.Iterator;

public class Course {

    private String courseName;
    private ArrayList<String> fallSemesterDays;
    private ArrayList<String> springSemesterDays;
    private ArrayList<String> summerSemesterDays;
    private ArrayList<Constraint> classConstraints;
    private int constraintsFailed = 0;
    private ArrayList<Constraint> failedConstraintList;

    public Course(String courseName, String fallSemesterDays, String springSemesterDays, String summerSemesterDays) {

        this.courseName = courseName;
        this.fallSemesterDays = new ArrayList<>();
        this.springSemesterDays = new ArrayList<>();
        this.summerSemesterDays = new ArrayList<>();
        this.classConstraints = new ArrayList<>();
        this.failedConstraintList = new ArrayList<>();
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

    public ArrayList<String> getDaysBySeason(String season){
        if(season.toUpperCase() == "FALL"){
            return getFallSemesterDays();
        }else if (season.toUpperCase() == "SPRING"){
            return getSpringSemesterDays();
        }else if (season.toUpperCase() == "SUMMER"){
            return getSummerSemesterDays();
        }else return null;
    }

    public Iterator<String> getDaysIteratorBySeason(String season){
        if(season.toUpperCase().equals("FALL")){
            return getFallSemesterDays().iterator();
        }else if (season.toUpperCase().equals("SPRING")){
            return getSpringSemesterDays().iterator();
        }else if (season.toUpperCase().equals("SUMMER")){
            return getSummerSemesterDays().iterator();
        }else return null;
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

    public boolean checkConstraint(String courseB, int courseAValue, int courseBValue){

        if(constraintExists(courseB)){
            Constraint constraint = classConstraints.get(getConstraintIndex(courseB));
            if(constraint.getCourseA().equals(this)){
                return constraint.constraintValid(courseAValue,courseBValue);
            }else{
                return constraint.constraintValid(courseBValue, courseAValue);
            }

        }

        return false;
    }

    public boolean courseOffered(int semester, String day){

        switch (semester){

            case 5 : return this.springSemesterDays.contains(day);
            case 3 : return this.fallSemesterDays.contains(day);
            case 1 : return this.summerSemesterDays.contains(day);
        }

        return false;
    }

    public boolean constraintExists(String courseB) {

        for(Constraint constraint: classConstraints){
            if(constraint.checkOperands(this.getCourseName(), courseB)){
                return true;
            }
        }
        return false;
    }

    private int getConstraintIndex(String courseB) {

        int constraintIndex = 0;
        for(Constraint constraint: classConstraints){
            if(constraint.checkOperands(this.getCourseName(), courseB)){
                return constraintIndex;
            }
            constraintIndex++;
        }
        return -1;
    }



    public void addConstraint(Constraint newConstraint) {

        this.classConstraints.add(newConstraint);
    }

    public int getConstraintsFailed() {
        return constraintsFailed;
    }

    public void setConstraintsFailed(int constraintsFailed) {
        this.constraintsFailed = constraintsFailed;
    }

    public void updateConstraintFailed(int i) {

        this.constraintsFailed = this.constraintsFailed + i;
    }

    public Constraint getConstraint(String courseB) {

        if(constraintExists(courseB)){
            return classConstraints.get(getConstraintIndex(courseB));
        }

        return null;
    }

    public void addFailedConstraint(Constraint failedConstrant) {
        this.failedConstraintList.add(failedConstrant);
    }

    public ArrayList<Constraint> getClassConstraints() {
        return classConstraints;
    }

    public void setClassConstraints(ArrayList<Constraint> classConstraints) {
        this.classConstraints = classConstraints;
    }

    public ArrayList<Constraint> getFailedConstraintList() {
        return failedConstraintList;
    }

    public void setFailedConstraintList(ArrayList<Constraint> failedConstraintList) {
        this.failedConstraintList = failedConstraintList;
    }

    public void clearFailedConstraintList(){
        this.failedConstraintList.clear();
    }
}
