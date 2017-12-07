import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class CustomComparator implements Comparator<ArrayList<String>>
{
    @Override
    public int compare(ArrayList<String> o1,
                       ArrayList<String> o2)
    {
        String firstString_o1 = o1.get(1);
        String firstString_o2 = o2.get(1);
        return firstString_o1.compareTo(firstString_o2);
    }
}