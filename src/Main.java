import java.io.*;
import java.util.*;

import static java.lang.Integer.parseInt;
import static javafx.application.Application.launch;

public class Main {

    /**
     * Main beginds the scheduling program
     * */
    public static void main(String[] args) {
        ArrayList<String> courseList = new ArrayList<>();
        File userDirectory = new File(System.getProperty("user.dir"));
        File courseFile = new File(String.format("%s\\%s", userDirectory.getPath(), "classes.txt"));
        Hashtable<Integer, Course> courseTable = addClasses(readFileInput(courseFile), courseList);
        File constraintsFile = new File(String.format("%s\\%s", userDirectory.getPath(), "constraints.txt"));
        addConstraints(readFileInput(constraintsFile), courseTable);
        Schedule schedule = generateRandomSchedule(courseTable, courseList);
        constraintSatisfactions(schedule, courseTable, courseList.size(), courseList);
        System.out.println(schedule.toString());
    }

    /**
     * Method receives initial schedule
     * Course table stored as a hashtable for searching
     * number of courses sets the bound while leaving the method open for various course sizes
     * Course list used to supplament the courseTable hash which can be diffacult to iterate through
     * */
    private static boolean constraintSatisfactions(Schedule schedule, Hashtable<Integer, Course> courseTable, int numberOfCourses, ArrayList<String> courseList) {
        String[][] constraintValues = generateConstraintValues(schedule, courseTable, numberOfCourses);
        List<ArrayList<String>> failedConstraints = new ArrayList<>();
        do {
            updateFailedConstraints(constraintValues, courseTable);
            failedConstraints = maxFailedConstraint(constraintValues, courseTable);
            String adjustForCourseName = failedConstraints.get(0).get(0);
            String adjustForCourseFails = failedConstraints.get(0).get(1);
            System.out.println("Next AdjustedCourse: " + adjustForCourseName + " Failed Constraints: " + adjustForCourseFails);
            schedule = adjustSchedule(schedule, courseTable, adjustForCourseName, courseList);
            constraintValues = generateConstraintValues(schedule, courseTable, numberOfCourses);
            clearFailedConstraints(constraintValues, courseTable);

            System.out.println(schedule.toString());
        } while (parseInt(failedConstraints.get(0).get(1)) > 0);

        return true;
    }

    /**
     * Method contains the primary constraint satisfaction functions
     * Provided the a valid schedule and table of courses the methods takes the course to be adjusted
     * and attempts to satisfy one of it's constraints. If the constraint cannot be satisfied it moves to the next constraint.
     * Once all of the constaraints have been tested withough a sucessful adjustment the alg. resets the schedule.
     * */
    private static Schedule adjustSchedule(Schedule schedule, Hashtable<Integer, Course> courseTable, String adjustForCourseName, ArrayList<String> courseList) {

        int constraintNumber = 0;
        boolean schedulesSwapped = false;
        while (!schedulesSwapped) {
            Course courseA = courseTable.get(adjustForCourseName.hashCode());
            Constraint constraint = courseA.getFailedConstraintList().get(constraintNumber);
            Course courseB = constraint.getPairedCourse(courseA);
            Semester courseASemester = null;
            Semester courseBSemester = null;
            for (Semester semester : schedule.getSemesters()) {
                int matchA = 0;
                int matchB = 0;
                for (String courseName : semester.getSemesterCourseList()) {
                    courseName = courseName.substring(0, courseName.length() - 2);
                    if (courseName.equals(courseA.getCourseName())) {
                        matchA = 1;
                    }
                    if (courseName.equals(courseB.getCourseName())) {
                        matchB = 1;
                    }
                }
                if (matchA == 1) {
                    courseASemester = semester;
                    courseASemester.removeCourse(courseA);
                }
                if (matchB == 1) {
                    courseBSemester = semester;
                    courseBSemester.removeCourse(courseB);
                }
            }
            if (courseASemester.equals(courseBSemester)) {
                addSemesterCourse(courseASemester, courseA);
                addSemesterCourse(courseBSemester, courseB);
                schedulesSwapped = false;
            } else if (canAddSemesterCourse(courseBSemester, courseA)
                    && canAddSemesterCourse(courseASemester, courseB)) {
                addSemesterCourse(courseBSemester, courseA);
                addSemesterCourse(courseASemester, courseB);
                schedulesSwapped = true;
            } else {
                {
                    addSemesterCourse(courseASemester, courseA);
                    addSemesterCourse(courseBSemester, courseB);
                    schedulesSwapped = false;
                }
            }
            constraintNumber++;
            System.out.println("Constraint Number: " + constraintNumber + " compared to " + courseA.getFailedConstraintList().size());
            if (constraintNumber >= courseA.getFailedConstraintList().size()) {
                schedule = generateRandomSchedule(courseTable, courseList);
                schedulesSwapped = true;
            }
        }
        System.out.println("Exit Adjust Schedule");
        return schedule;

    }


    private static void clearFailedConstraints(String[][] constraintValues, Hashtable<Integer, Course> courseTable) {
        for (int i = 0; i < constraintValues.length; i++) {
            String courseName = constraintValues[i][0].substring(0, constraintValues[i][0].length() - 2);
            Course course = courseTable.get(courseName.hashCode());
            course.setConstraintsFailed(0);
            course.clearFailedConstraintList();
        }
    }

    private static List<ArrayList<String>> maxFailedConstraint(String[][] constraintValues, Hashtable<Integer, Course> courseTable) {
        List<ArrayList<String>> returnCourse = new ArrayList<>();
        for (int i = 0; i < constraintValues.length; i++) {
            String courseName = constraintValues[i][0].substring(0, constraintValues[i][0].length() - 2);
            ArrayList<String> newList = new ArrayList<>();
            newList.add(courseName);
            newList.add(courseTable.get(courseName.hashCode()).getConstraintsFailed() + "");
            returnCourse.add(newList);
        }
        Collections.sort(returnCourse, new CustomComparator());
        Collections.reverse(returnCourse);

        return returnCourse;
    }

    private static void updateFailedConstraints(String[][] constraintValues, Hashtable<Integer, Course> courseTable) {
        for (int i = 0; i < constraintValues.length; i++) {
            String courseAName = constraintValues[i][0].substring(0, constraintValues[i][0].length() - 2);
            Course courseA = courseTable.get(courseAName.hashCode());
            for (int j = i + 1; j < constraintValues.length; j++) {
                String courseBName = constraintValues[j][0].substring(0, constraintValues[j][0].length() - 2);
                if (courseA.constraintExists(courseBName)) {
                    if (!courseA.checkConstraint(courseBName, parseInt(constraintValues[i][1]), parseInt(constraintValues[j][1]))) {
                        Constraint failedConstrant = courseA.getConstraint(courseBName);
                        courseA.updateConstraintFailed(1);
                        courseA.addFailedConstraint(failedConstrant);
                        courseTable.get(courseBName.hashCode()).updateConstraintFailed(1);
                        courseTable.get(courseBName.hashCode()).addFailedConstraint(failedConstrant);
                    }
                }
            }
        }
        for (int i = 0; i < constraintValues.length; i++) {
            String courseName = constraintValues[i][0].substring(0, constraintValues[i][0].length() - 2);
            Course course = courseTable.get(courseName.hashCode());
//            System.out.println("Course: " + course.getCourseName() + " Failed Constraints: " +course.getConstraintsFailed());
        }
    }

    private static String[][] generateConstraintValues(Schedule schedule, Hashtable<Integer, Course> courseTable, int size) {
        String[][] constraintValues = new String[size][2];
        int i = 0;
        Integer j = 0;
        for (Semester semester : schedule.getSemesters()) {
            for (String course : semester.getSemesterCourseList()) {
//                Course newCourse = courseTable.get(course.substring(0, course.length()-2).hashCode());
                constraintValues[i][0] = course;
                constraintValues[i][1] = j.toString();
                i++;
            }
            j++;
        }
        return constraintValues;
    }

    private static Schedule generateFixedSchedule(Hashtable<Integer, Course> courseTable, ArrayList<String> courseList) {
        Random r = new Random();
        Schedule schedule = new Schedule();

        // uncomment the setSeed(…) line to get a nonrandom starting assignment.
        // different seeds will give different assignments.
        // !!!--- comment the next line out before turning in the program ---!!!
        r.setSeed(10);
        for (int course = 0; course < courseList.size(); course++) {
            int sem = r.nextInt(11);
            Semester semester = schedule.getSemesterByIndex(sem);
            Course currentCourse = courseTable.get(courseList.get(course).hashCode());
            boolean scheudleUpdated = addSemesterCourse(semester, currentCourse);
            while (!scheudleUpdated) {
                sem = r.nextInt(11);
                semester = schedule.getSemesterByIndex(sem);
                scheudleUpdated = addSemesterCourse(semester, currentCourse);

            }
        }

        return schedule;
    }

    private static Schedule generateRandomSchedule(Hashtable<Integer, Course> courseTable, ArrayList<String> courseList) {
        Random r = new Random();
        Schedule schedule = new Schedule();

        // uncomment the setSeed(…) line to get a nonrandom starting assignment.
        // different seeds will give different assignments.
        // !!!--- comment the next line out before turning in the program ---!!!
//        r.setSeed(10);
        for (int course = 0; course < courseList.size(); course++) {
            int sem = r.nextInt(11);
            Semester semester = schedule.getSemesterByIndex(sem);
            Course currentCourse = courseTable.get(courseList.get(course).hashCode());
            boolean scheduleUpdated = addSemesterCourse(semester, currentCourse);
            int xxx = 0;
            while (!scheduleUpdated) {
                if (xxx > 10) {
                    generateRandomSchedule(courseTable, courseList);
                }
                sem = r.nextInt(11);
                semester = schedule.getSemesterByIndex(sem);
                scheduleUpdated = addSemesterCourse(semester, currentCourse);
                xxx++;


            }
        }

        return schedule;
    }

    private static boolean canAddSemesterCourse(Semester semester, Course currentCourse) {

        Iterator<String> offeredDays = currentCourse.getDaysIteratorBySeason(semester.getSemesterSeason());
        while (offeredDays.hasNext()) {
            if (semester.checkAddCourse(currentCourse, offeredDays.next())) {
                return true;
            }
        }
        return false;
    }

    private static boolean addSemesterCourse(Semester semester, Course currentCourse) {

        Iterator<String> offeredDays = currentCourse.getDaysIteratorBySeason(semester.getSemesterSeason());
        while (offeredDays.hasNext()) {
            if (semester.addClass(currentCourse, offeredDays.next())) {
                return true;
            }
        }
        return false;
    }

    private static Hashtable<Integer, Course> addClasses(ArrayList<String> courseInputFile, ArrayList<String> courseList) {

        Hashtable<Integer, Course> courseTable = new Hashtable();
        List<String> courseInputList = courseInputFile.subList(1, courseInputFile.size());
        for (String course : courseInputList) {
            String[] indexString = course.split("\\s+");
            Course newCourse = new Course(indexString[0], indexString[1], indexString[2], indexString[3]);
            courseTable.put(newCourse.getCourseName().hashCode(), newCourse);

            courseList.add(indexString[0]);
        }

        return courseTable;
    }

    private static void addConstraints(ArrayList<String> constraintsInputFile, Hashtable<Integer, Course> courseTable) {

        for (String readConstraint : constraintsInputFile) {
            String[] indexString = readConstraint.split("\\s+");
            Constraint newConstraint = new Constraint();
            int courseAHash = indexString[0].hashCode();
            int courseBHash = indexString[2].hashCode();
            newConstraint.setCourseA(courseTable.get(courseAHash));
            newConstraint.setCourseB(courseTable.get(courseBHash));
            newConstraint.setOperator(indexString[1]);
            courseTable.get(courseAHash).addConstraint(newConstraint);
            courseTable.get(courseBHash).addConstraint(newConstraint);
        }
    }

    /**
     * Receives an existing input file and reads through and assigns each line to an array
     *
     * @param inputFile
     * @return fileList
     */
    private static ArrayList<String> readFileInput(File inputFile) {
        ArrayList<String> fileList = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile.getPath()));
            String readLine;
            while ((readLine = bufferedReader.readLine()) != null) {
                fileList.add(readLine);
            }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound: No file at specified file path" + inputFile.getPath());
        } catch (IOException e) {
            System.out.println("IOException: No data in selected file");
        }
        return fileList;
    }

    /**
     * Creates a new text file in a specified accessible location
     *
     * @return newFile is the file object
     * @parm fileName the name of the text file to be created (_out.txt will be appended to name)
     * @parm the location the file is to be created in
     */
    private File createFile(String fileName, File fileDirectory) throws IOException {
        File newFile = new File(String.format("%s\\%s_out.txt", fileDirectory.getPath(), fileName));
        newFile.createNewFile();
        return newFile;
    }

    /**
     * Writes a single string to an exsisting file
     *
     * @param fileName the name of the exsisting file
     * @param fileText the String/text to be written
     */
    private void writeToFile(File fileName, ArrayList<String> fileText) {

        try {
            PrintStream writer = new PrintStream(new FileOutputStream(fileName));

            for (String line : fileText) {
                writer.println(line);
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
