package enums.attributes;

import enums.ChanceEnum;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: MaxLEAF
 * @Date: 2021/08/28/10:55
 * @Description: 理之冠主词条分布
 */
public enum PrimAttrCircletEnum implements ChanceEnum, PrimAttrEnum {
    DEF_P(0.22),
    ATK_P(0.22),
    HP_P(0.22),
    CRIT(0.1),
    CRIT_DMG(0.1),
    EM(0.04),
    HEAL(0.1),
    ;
    private final double chance;

    PrimAttrCircletEnum(double chance) {
        this.chance = chance;
    }

    @Override
    public double chance() {
        return this.chance;
    }
}
