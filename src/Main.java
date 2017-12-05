import javafx.application.Application;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

import static javafx.application.Application.launch;

public class Main {

    public static void main(String[] args) {
        ArrayList<String> courseList = new ArrayList<>();

        File userDirectory = new File(System.getProperty("user.dir"));

        File courseFile = new File(String.format("%s\\%s", userDirectory.getPath(), "classes.txt"));
        Hashtable<Integer, Course> courseTable = addClasses(readFileInput(courseFile), courseList);

        File constraintsFile = new File(String.format("%s\\%s", userDirectory.getPath(), "constraints.txt"));
        addConstraints(readFileInput(constraintsFile), courseTable);

        Random r = new Random();
        Schedule schedule = new Schedule();

        // uncomment the setSeed(…) line to get a nonrandom starting assignment.
        // different seeds will give different assignments.
        // !!!--- comment the next line out before turning in the program ---!!!
        r.setSeed(10);
        for (int course = 0; course < 30; course++) {
            int sem = r.nextInt(11);
            Course currentCourse = courseTable.get(courseList.get(course).hashCode());
            boolean scheudleUpdated = generateSchedule(schedule, sem, currentCourse);
            while (!scheudleUpdated) {
                sem = r.nextInt(11);
                scheudleUpdated = generateSchedule(schedule, sem, currentCourse);
            }
            System.out.println("Course " + currentCourse.getCourseName() + "  is taken semester " + sem);
        }
    }


    private static boolean generateSchedule(Schedule schedule, int sem, Course currentCourse) {
        Semester semester = schedule.getSemesterByIndex(sem);
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
