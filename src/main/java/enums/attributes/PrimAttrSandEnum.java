package enums.attributes;

import enums.ChanceEnum;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: MaxLEAF
 * @Date: 2021/08/28/10:55
 * @Description:
 */
public enum PrimAttrSandEnum implements ChanceEnum, PrimAttrEnum {
    DEF_P(0.25),
    ATK_P(0.25),
    HP_P(0.25),
    ER(0.125),
    EM(0.125),
    ;
    private final double chance;

    PrimAttrSandEnum(double chance) {
        this.chance = chance;
    }

    @Override
    public double chance() {
        return this.chance;
    }
}