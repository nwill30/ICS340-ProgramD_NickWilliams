import java.util.ArrayList;

public class Semester {

    private String semesterID;
    private String semesterYear;
    private String semesterSeason;
    private Course sundayClass;
    private Course mondayClass;
    private Course tuesdayClass;
    private Course wednsdayClass;
    private Course thursdayClass;
    private Course fridayClass;
    private Course saturdayClass;
    private ArrayList<Course> semesterCourseList;
    private Course[] onlineClass = new Course[3];

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
            case "3":
                this.semesterSeason = "Fall";
            case "5":
                this.semesterSeason = "Spring";
        }
    }

    public boolean addClass(Course course, String courseDay){

        if (semesterCourseList.size() < 3) {
            switch (courseDay) {
                case "U":
                    if (course.courseOffered(Integer.parseInt(this.semesterID.substring(4)), "U") && this.sundayClass == null) {
                        this.sundayClass = course;
                        return true;
                    } else return false;
                case "M":
                    if (course.courseOffered(Integer.parseInt(this.semesterID.substring(4)), "M") && this.mondayClass == null) {
                        this.mondayClass = course;
                        return true;
                    } else return false;
                case "T":
                    if (course.courseOffered(Integer.parseInt(this.semesterID.substring(4)), "T") && this.tuesdayClass == null) {
                        this.tuesdayClass = course;
                        return true;
                    } else return false;
                case "W":
                    if (course.courseOffered(Integer.parseInt(this.semesterID.substring(4)), "W") && this.wednsdayClass == null) {
                        this.wednsdayClass = course;
                        return true;
                    } else return false;
                case "H":
                    if (course.courseOffered(Integer.parseInt(this.semesterID.substring(4)), "H") && this.thursdayClass == null) {
                        this.thursdayClass = course;
                        return true;
                    } else return false;
                case "F":
                    if (course.courseOffered(Integer.parseInt(this.semesterID.substring(4)), "F") && this.fridayClass == null) {
                        this.fridayClass = course;
                        return true;
                    } else return false;
                case "S":
                    if (course.courseOffered(Integer.parseInt(this.semesterID.substring(4)), "S") && this.saturdayClass == null) {
                        this.saturdayClass = course;
                        return true;
                    } else return false;
                case "O":
                    if (course.courseOffered(Integer.parseInt(this.semesterID.substring(4)), "O")) {
                        for (int i = 0; i <= this.onlineClass.length; i++) {
                            if (this.onlineClass[i] == null) {
                                this.onlineClass[i] = course;
                                return true;
                            }
                        }

                    } else return false;
            }
        }
        return false;
    }


        public String getSemesterID () {
            return semesterID;
        }

        public void setSemesterID (String semesterID){
            this.semesterID = semesterID;
        }

        public String getSemesterYear () {
            return semesterYear;
        }

        public void setSemesterYear (String semesterYear){
            this.semesterYear = semesterYear;
        }

        public String getSemesterSeason () {
            return semesterSeason;
        }

        public void setSemesterSeason (String semesterSeason){
            this.semesterSeason = semesterSeason;
        }

        public Course getSundayClass () {
            return sundayClass;
        }

        public void setSundayClass (Course sundayClass){
            this.sundayClass = sundayClass;
        }

        public Course getMondayClass () {
            return mondayClass;
        }

        public void setMondayClass (Course mondayClass){
            this.mondayClass = mondayClass;
        }

        public Course getTuesdayClass () {
            return tuesdayClass;
        }

        public void setTuesdayClass (Course tuesdayClass){
            this.tuesdayClass = tuesdayClass;
        }

        public Course getWednsdayClass () {
            return wednsdayClass;
        }

        public void setWednsdayClass (Course wednsdayClass){
            this.wednsdayClass = wednsdayClass;
        }

        public Course getThursdayClass () {
            return thursdayClass;
        }

        public void setThursdayClass (Course thursdayClass){
            this.thursdayClass = thursdayClass;
        }

        public Course getFridayClass () {
            return fridayClass;
        }

        public void setFridayClass (Course fridayClass){
            this.fridayClass = fridayClass;
        }

        public Course getSaturdayClass () {
            return saturdayClass;
        }

        public void setSaturdayClass (Course saturdayClass){
            this.saturdayClass = saturdayClass;
        }

        public ArrayList<Course> getSemesterCourseList () {
            return semesterCourseList;
        }

        public void setSemesterCourseList (ArrayList < Course > semesterCourseList) {
            this.semesterCourseList = semesterCourseList;
        }
    }
