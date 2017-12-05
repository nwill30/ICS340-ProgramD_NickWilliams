import java.util.ArrayList;

public class Semester {

    private String semesterID;
    private String semesterYear;
    private String semesterSeason;
    private ArrayList<Course> sundayClass = new ArrayList<>();
    private ArrayList<Course> mondayClass = new ArrayList<>();
    private ArrayList<Course> tuesdayClass = new ArrayList<>();
    private ArrayList<Course> wednsdayClass = new ArrayList<>();
    private ArrayList<Course> thursdayClass = new ArrayList<>();
    private ArrayList<Course> fridayClass = new ArrayList<>();
    private ArrayList<Course> saturdayClass = new ArrayList<>();
    private ArrayList<Course> semesterCourseList = new ArrayList<>();
    private ArrayList<Course> onlineClass = new ArrayList<>();

    public Semester(String semesterID) {

        this.semesterID = semesterID;
        this.semesterCourseList = new ArrayList<>();

        if (this.semesterID.substring(4).equals("3")) {
            int year = Integer.parseInt(semesterID.substring(0, 4)) + 1;
            this.semesterYear = Integer.toBinaryString(year);
        } else {
            this.semesterYear = semesterID.substring(0, 4);
        }

        switch (semesterID.substring(4)) {
            case "1":
                this.semesterSeason = "Summer";
                break;
            case "3":
                this.semesterSeason = "Fall";
                break;
            case "5":
                this.semesterSeason = "Spring";
                break;
        }
    }

    public boolean addClass(Course course, String courseDay) {

        if (semesterCourseList.size() < 3) {
            switch (courseDay) {
                case "U":
                    if (course.courseOffered(Integer.parseInt(this.semesterID.substring(4)), "U")) {
                        this.sundayClass.add(course);
                        return true;
                    } else return false;
                case "M":
                    if (course.courseOffered(Integer.parseInt(this.semesterID.substring(4)), "M")) {
                        this.mondayClass.add(course);
                        return true;
                    } else return false;
                case "T":
                    if (course.courseOffered(Integer.parseInt(this.semesterID.substring(4)), "T")) {
                        this.tuesdayClass.add(course);
                        return true;
                    } else return false;
                case "W":
                    if (course.courseOffered(Integer.parseInt(this.semesterID.substring(4)), "W")) {
                        this.wednsdayClass.add(course);
                        return true;
                    } else return false;
                case "H":
                    if (course.courseOffered(Integer.parseInt(this.semesterID.substring(4)), "H")) {
                        this.thursdayClass.add(course);
                        return true;
                    } else return false;
                case "F":
                    if (course.courseOffered(Integer.parseInt(this.semesterID.substring(4)), "F")) {
                        this.fridayClass.add(course);
                        return true;
                    } else return false;
                case "S":
                    if (course.courseOffered(Integer.parseInt(this.semesterID.substring(4)), "S")) {
                        this.saturdayClass.add(course);
                        return true;
                    } else return false;
                case "O":
                    if (course.courseOffered(Integer.parseInt(this.semesterID.substring(4)), "O")) {
                                this.onlineClass.add(course);
                                return true;
                    } else return false;
            }
        }
        return false;
    }


    public String getSemesterID() {
        return semesterID;
    }

    public void setSemesterID(String semesterID) {
        this.semesterID = semesterID;
    }

    public String getSemesterYear() {
        return semesterYear;
    }

    public void setSemesterYear(String semesterYear) {
        this.semesterYear = semesterYear;
    }

    public String getSemesterSeason() {
        return semesterSeason;
    }

    public void setSemesterSeason(String semesterSeason) {
        this.semesterSeason = semesterSeason;
    }

    public ArrayList<Course> getSundayClass() {
        return sundayClass;
    }

    public void setSundayClass(ArrayList<Course> sundayClass) {
        this.sundayClass = sundayClass;
    }

    public ArrayList<Course> getMondayClass() {
        return mondayClass;
    }

    public void setMondayClass(ArrayList<Course> mondayClass) {
        this.mondayClass = mondayClass;
    }

    public ArrayList<Course> getTuesdayClass() {
        return tuesdayClass;
    }

    public void setTuesdayClass(ArrayList<Course> tuesdayClass) {
        this.tuesdayClass = tuesdayClass;
    }

    public ArrayList<Course> getWednsdayClass() {
        return wednsdayClass;
    }

    public void setWednsdayClass(ArrayList<Course> wednsdayClass) {
        this.wednsdayClass = wednsdayClass;
    }

    public ArrayList<Course> getThursdayClass() {
        return thursdayClass;
    }

    public void setThursdayClass(ArrayList<Course> thursdayClass) {
        this.thursdayClass = thursdayClass;
    }

    public ArrayList<Course> getFridayClass() {
        return fridayClass;
    }

    public void setFridayClass(ArrayList<Course> fridayClass) {
        this.fridayClass = fridayClass;
    }

    public ArrayList<Course> getSaturdayClass() {
        return saturdayClass;
    }

    public void setSaturdayClass(ArrayList<Course> saturdayClass) {
        this.saturdayClass = saturdayClass;
    }

    public ArrayList<Course> getOnlineClass() {
        return onlineClass;
    }

    public void setOnlineClass(ArrayList<Course> onlineClass) {
        this.onlineClass = onlineClass;
    }

    public ArrayList<Course> getSemesterCourseList() {
        return semesterCourseList;
    }

    public void setSemesterCourseList(ArrayList<Course> semesterCourseList) {
        this.semesterCourseList = semesterCourseList;
    }
}
