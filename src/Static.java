import java.util.Date;

/**
 * @author by liu.hongda
 * @Description static
 * @Date 2019/6/21 16:41
 */

public class Static {

    private static String strOne = "one";

    private String strTwo = "two";

    private static String startDate;

    //static variable
    static {
        startDate="2000";
    }

    public void printOne(){
        System.out.println(strOne);
        System.out.println(strTwo);
        printTwo();
    }

    public static void printTwo(){
        System.out.println(strOne);

        //can't use the unStatic variable in static function
        //System.out.println(strTwo);

        //can't use the unStatic function in static function
        //printOne();
    }
}
