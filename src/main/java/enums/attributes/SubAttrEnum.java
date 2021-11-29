package enums.attributes;

import enums.ChanceEnum;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: MaxLEAF
 * @Date: 2021/08/27/0:54
 * @Description:
 */
public enum SubAttrEnum implements ChanceEnum {
    //副词条出现概率(参考真实采样数据并修正)
    CRIT(0.07),
    CRIT_DMG(0.07),
    ATK(0.125),
    ATK_P(0.086),
    DEF(0.127),
    DEF_P(0.096),
    HP(0.125),
    HP_P(0.096),
    ER(0.1),
    EM(0.105),
    ;

    private final double chance;

    SubAttrEnum(double chance) {
        this.chance = chance;
    }

    @Override
    public double chance() {
        return this.chance;
    }

}
