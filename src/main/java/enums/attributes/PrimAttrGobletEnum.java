package enums.attributes;

import enums.ChanceEnum;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: MaxLEAF
 * @Date: 2021/08/28/10:57
 * @Description:
 */
public enum PrimAttrGobletEnum implements ChanceEnum, PrimAttrEnum {
    DEF_P(0.2),
    ATK_P(0.2),
    HP_P(0.2),
    PHY(0.05),
    PRYO(0.05),
    HYDRO(0.05),
    CRYO(0.05),
    ELECTRO(0.05),
    ANEMO(0.05),
    GEO(0.05),
    EM(0.05),
    ;

    private final double chance;

    PrimAttrGobletEnum(double chance) {
        this.chance = chance;
    }

    @Override
    public double chance() {
        return this.chance;
    }
}
