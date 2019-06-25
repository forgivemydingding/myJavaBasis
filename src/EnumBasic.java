/**
 * @author by liu.hongda
 * @Description TODO
 * @Date 2019/6/24 16:35
 */
public enum EnumBasic {

    SUN(0,""), MON(1,""), TUS(2,""), WED(3,""), THU(4,""), FRI(5,""), SAT(6,"");
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
        System.out.println(EnumBasic.valueOf("SUN"));
        System.out.println("<-------------->");
        for (EnumBasic e : EnumBasic.values()) {
            System.out.println(e);
        }
        System.out.println(EnumBasic.MON.ordinal());
    }
}