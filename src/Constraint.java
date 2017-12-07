public class Constraint {

    private Course courseA;
    private Course courseB;
    private String operator;

    public Constraint(Course course1, Course course2, int operator){


        this.courseA = course1;
        this.courseB = course2;
        this.operator = getOperator(operator);
    }

    public Constraint() {

    }

    public Course getCourseA() {
        return courseA;
    }

    public void setCourseA(Course courseA) {

        if(this.courseA==null) {
            this.courseA = courseA;
        }
    }

    public Course getCourseB() {
        return courseB;
    }

    public void setCourseB(Course courseB) {
        if (this.courseB==null) {
            this.courseB = courseB;
        }
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    private String getOperator(int operator) {

        try{
            switch (operator){
                case 0: return "==";
                case 1: return "<";
                case 2: return ">";
                case 3: return "<=";
                case 4: return ">=";
                case 5: return "!=";
            }
        }catch(Exception  e){
            System.out.println("Operator int <> 0-5");
            throw e;
        }
        return null;

    }

    public boolean checkOperands(String firstOperand, String secondOperand) {
        boolean valid = false;
        if(courseA.getCourseName().equals(firstOperand)&&courseB.getCourseName().equals(secondOperand)){
            valid = true;
        }else if (courseA.getCourseName().equals(secondOperand)&&courseB.getCourseName().equals(firstOperand)){
            valid = true;
        }
        return valid;
    }

    public boolean constraintValid(int courseAValue, int courseBValue){
        try{
            switch (this.operator){
                case "==": return courseAValue == courseBValue;
                case "<": return courseAValue < courseBValue;
                case ">": return courseAValue > courseBValue;
                case "<=": return courseAValue <= courseBValue;
                case ">=": return courseAValue >= courseBValue;
                case "!=": return courseAValue != courseBValue;
            }
        }catch(Exception  e){
            System.out.println("Operator int <> 0-5");
            throw e;
        }
        return false;
    }

    public Course getPairedCourse(Course course){
        if(course.equals(this.courseA)){
            return this.courseB;
        }else if(course.equals(this.courseB)){
            return this.courseA;
        }

        return null;
    }
}
