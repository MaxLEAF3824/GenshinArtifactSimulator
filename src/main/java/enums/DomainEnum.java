package enums;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: MaxLEAF
 * @Date: 2021/08/27/0:25
 * @Description: 副本相关数据
 */


public enum DomainEnum {
    //最高等级单次出货数量(20树脂)
    BASE_GOLD(1),       //基础出货数量
    BOUNS_GOLD(0.067),   //在此基础上+1的概率
    BASE_PURPLE(2),
    BOUNS_PURPLE(0.484),
    BASE_BLUE(3),
    BOUNS_BLUE(0.547);

    private final double value;

    DomainEnum(double value) {
        this.value = value;
    }

    public double value() {
        return this.value;
    }
}
