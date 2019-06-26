/**
 * @author by liu.hongda
 * @Description TODO
 * @Date 2019/6/24 16:35
 */

public enum EnumBasic {

    SUN(0,"周天"), MON(1,"周一"), TUS(2,"周二"), WED(3,"周三"), THU(4,"周四"), FRI(5,"周五"), SAT(6,"周六");
    private int value;
    private String des;

    EnumBasic(int value,String des) {
        this.value = value;
        this.des = des;
    }


}

class Test {
    public static void main(String[] args) {
        EnumBasic sun = EnumBasic.SUN;
        //System.out.println(EnumBasic.valueOf("SUN"));
        //System.out.println("<-------------->");
        for (EnumBasic e : EnumBasic.values()) {
            //System.out.println(e);
            System.out.println(e.name().charAt(0));
            //System.out.println(e.toString());
        }
        System.out.println(EnumBasic.MON.ordinal());
    }
}