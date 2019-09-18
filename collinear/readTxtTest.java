/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
import java.io.FileReader;
import java.io.File;

public class readTxtTest {
    public static void main(String[] args) throws Exception{
        File file = new File("./input48.txt");
            FileReader fr = new FileReader(file);
            char[] a = new char[fr.read()];
            fr.read(a);
        for (int i = 0; i < a.length; i++) {
            System.out.println((int)a[i]);
        }
            fr.close();

    }
}
