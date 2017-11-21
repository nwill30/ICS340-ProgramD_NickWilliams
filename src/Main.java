import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args){

        File userDirectory = new File(System.getProperty("user.dir"));

        File courseFile = new File(String.format("%s\\%s", userDirectory.getPath(), "classes.txt"));
        addClasses(readFileInput(courseFile));

        File constraintsFile = new File(String.format("%s\\%s", userDirectory.getPath(), "constraints.txt"));
        addConstraints(readFileInput(constraintsFile));

    }

    private static void addClasses(ArrayList<String> courseInputFile){

        ArrayList<Course> courseList = new ArrayList<>();
        List<String> courselist = courseInputFile.subList(1,courseInputFile.size());
        for(String course: courselist){
            String[] indexString = course.split("\\t");
            Course newCourse = new Course(indexString[0],indexString[1],indexString[2],indexString[3]);

        }

    }

    private static void addConstraints(ArrayList<String>  ConstraintsInputFile){


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
     * @parm fileName the name of the text file to be created (_out.txt will be appended to name)
     * @parm    the location the file is to be created in
     * @return newFile is the file object
     *
     * */
    private File createFile(String fileName, File fileDirectory) throws IOException {
        File newFile = new File(String.format("%s\\%s_out.txt",fileDirectory.getPath(),fileName));
        newFile.createNewFile();
        return newFile;
    }
    /**
     * Writes a single string to an exsisting file
     *
     * @param fileName the name of the exsisting file
     *                 @param fileText the String/text to be written
     * */
    private void writeToFile(File fileName, ArrayList<String> fileText){

        try {
            PrintStream writer = new PrintStream(new FileOutputStream(fileName));

            for(String line : fileText){
                writer.println(line);
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
