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
    //参考NGA论坛 [数据讨论] [提瓦特大学](附代码)圣遗物副词条与其中的多重概率问题 中给出的数据
    CRIT(0.0682),
    CRIT_DMG(0.0682),
    ATK(0.1364),
    ATK_P(0.0909),
    DEF(0.1364),
    DEF_P(0.0909),
    HP(0.1364),
    HP_P(0.0909),
    ER(0.0909),
    EM(0.0909),
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
