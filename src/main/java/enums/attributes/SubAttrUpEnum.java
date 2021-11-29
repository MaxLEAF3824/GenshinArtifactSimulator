package enums.attributes;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: MaxLEAF
 * @Date: 2021/08/28/13:24
 * @Description:
 */
public enum SubAttrUpEnum {
    CRIT_1(2.7, SubAttrEnum.CRIT),
    CRIT_2(3.1, SubAttrEnum.CRIT),
    CRIT_3(3.5, SubAttrEnum.CRIT),
    CRIT_4(3.9, SubAttrEnum.CRIT),
    CRIT_DMG_1(5.4, SubAttrEnum.CRIT_DMG),
    CRIT_DMG_2(6.2, SubAttrEnum.CRIT_DMG),
    CRIT_DMG_3(7.0, SubAttrEnum.CRIT_DMG),
    CRIT_DMG_4(7.8, SubAttrEnum.CRIT_DMG),
    ATK_1(14, SubAttrEnum.ATK),
    ATK_2(16, SubAttrEnum.ATK),
    ATK_3(18, SubAttrEnum.ATK),
    ATK_4(19, SubAttrEnum.ATK),
    ATK_P_1(4.1, SubAttrEnum.ATK_P),
    ATK_P_2(4.7, SubAttrEnum.ATK_P),
    ATK_P_3(5.3, SubAttrEnum.ATK_P),
    ATK_P_4(5.8, SubAttrEnum.ATK_P),
    DEF_1(16, SubAttrEnum.DEF),
    DEF_2(19, SubAttrEnum.DEF),
    DEF_3(21, SubAttrEnum.DEF),
    DEF_4(23, SubAttrEnum.DEF),
    DEF_P_1(5.1, SubAttrEnum.DEF_P),
    DEF_P_2(5.8, SubAttrEnum.DEF_P),
    DEF_P_3(6.6, SubAttrEnum.DEF_P),
    DEF_P_4(7.3, SubAttrEnum.DEF_P),
    HP_1(209, SubAttrEnum.HP),
    HP_2(239, SubAttrEnum.HP),
    HP_3(269, SubAttrEnum.HP),
    HP_4(299, SubAttrEnum.HP),
    HP_P_1(4.1, SubAttrEnum.HP_P),
    HP_P_2(4.7, SubAttrEnum.HP_P),
    HP_P_3(5.3, SubAttrEnum.HP_P),
    HP_P_4(5.8, SubAttrEnum.HP_P),
    ER_1(4.5, SubAttrEnum.ER),
    ER_2(5.2, SubAttrEnum.ER),
    ER_3(5.8, SubAttrEnum.ER),
    ER_4(6.5, SubAttrEnum.ER),
    EM_1(16, SubAttrEnum.EM),
    EM_2(19, SubAttrEnum.EM),
    EM_3(21, SubAttrEnum.EM),
    EM_4(23, SubAttrEnum.EM),
    FOUR_ATTR(0.2, null), //初始4词条概率
    ;
    private final double value;
    private final Enum parent;

    SubAttrUpEnum(double value, Enum parent) {
        this.value = value;
        this.parent = parent;
    }

    public double value() {
        return this.value;
    }

    public Enum parent() {
        return this.parent;
    }
}
