package enums;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: MaxLEAF
 * @Date: 2021/08/27/0:25
 * @Description:
 */


public enum DomainEnum {
    //单次出货数量(20树脂)
    BASE_GOLD(1),
    BOUNS_GOLD(0.15),
    BASE_PURPLE(2),
    BOUNS_PURPLE(0.38),
    BASE_BLUE(3),
    BOUNS_BLUE(0.6);

    private final double value;

    DomainEnum(double value) {
        this.value = value;
    }

    public double value() {
        return this.value;
    }
}
