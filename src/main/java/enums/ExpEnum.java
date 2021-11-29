package enums;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: MaxLEAF
 * @Date: 2021/08/27/23:44
 * @Description:
 */
public enum ExpEnum {
    LV_0(3000),
    LV_1(3725),
    LV_2(4425),
    LV_3(5150),
    LV_4(5900),
    LV_5(6675),
    LV_6(7500),
    LV_7(8350),
    LV_8(9225),
    LV_9(10125),
    LV_10(11050),
    LV_11(12025),
    LV_12(13025),
    LV_13(15150),
    LV_14(17600),
    LV_15(20375),
    LV_16(23500),
    LV_17(27050),
    LV_18(31050),
    LV_19(35575),
    LV_20(999999999),
    EXP_BLUE(1260),
    EXP_PURPLE(2520),
    EXP_GOLD(3780),
    EXP_TOP_LIMIT(270475),
    EXP_LOSS_P(80);

    private final int value;

    ExpEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    public static int getExpByLevel(int level) {
        return ExpEnum.values()[level].value();
    }
}
