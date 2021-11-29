package enums.attributes;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: MaxLEAF
 * @Date: 2021/08/27/23:44
 * @Description:
 */
public enum PrimAttrUpEnum {
    BASE_HP(717),
    BASE_ATK(47),
    BASE_HP_P_ATK_P_ELE(7),
    BASE_DEF_P_PHY(8.7),
    BASE_ER(7.8),
    BASE_EM(28),
    BASE_CRIT(4.7),
    BASE_CRIT_DMG(9.3),
    BASE_HEAL(5.4),
    GROWTH_HP(203.15),
    GROWTH_ATK(13.2),
    GROWTH_HP_P_ATK_P_ELE(1.98),
    GROWTH_DEF_P_PHY(2.48),
    GROWTH_ER(2.2),
    GROWTH_EM(7.95),
    GROWTH_CRIT(1.32),
    GROWTH_CRIT_DMG(2.645),
    GROWTH_HEAL(1.525),
    ;

    private final double value;

    PrimAttrUpEnum(double value) {
        this.value = value;
    }

    public double value() {
        return this.value;
    }

    public static double getPrimAttrValue(PrimAttrEnum primAttr, int level) {
        // 生命
        if (primAttr == PrimAttrFlowerEnum.HP) {
            return (double) Math.round(BASE_HP.value() + GROWTH_HP.value() * level);
        }
        // 攻击
        else if (primAttr == PrimAttrFeatherEnum.ATK) {
            return (double) Math.round(BASE_ATK.value() + GROWTH_ATK.value() * level);
        }
        // 百分比攻击_百分比生命_元素加成
        else if (primAttr == PrimAttrGobletEnum.ATK_P || primAttr == PrimAttrSandEnum.ATK_P
                || primAttr == PrimAttrCircletEnum.ATK_P || primAttr == PrimAttrGobletEnum.HP_P
                || primAttr == PrimAttrSandEnum.HP_P || primAttr == PrimAttrCircletEnum.HP_P
                || primAttr == PrimAttrGobletEnum.ANEMO || primAttr == PrimAttrGobletEnum.GEO
                || primAttr == PrimAttrGobletEnum.PRYO || primAttr == PrimAttrGobletEnum.HYDRO
                || primAttr == PrimAttrGobletEnum.CRYO || primAttr == PrimAttrGobletEnum.ELECTRO
        ) {
            return (double) Math.round(BASE_HP_P_ATK_P_ELE.value() * 10 + GROWTH_HP_P_ATK_P_ELE.value() * 10 * level) * 0.1d;
        }
        // 百分比防御_物理
        else if (primAttr == PrimAttrGobletEnum.DEF_P || primAttr == PrimAttrSandEnum.DEF_P
                || primAttr == PrimAttrCircletEnum.DEF_P || primAttr == PrimAttrGobletEnum.PHY) {
            return (double) Math.round(BASE_DEF_P_PHY.value() * 10 + GROWTH_DEF_P_PHY.value() * 10 * level) * 0.1d;
        }
        // 元素充能
        else if (primAttr == PrimAttrSandEnum.ER) {
            return (double) Math.round(BASE_ER.value() * 10 + GROWTH_ER.value() * 10 * level) * 0.1d;
        }
        // 元素精通
        else if (primAttr == PrimAttrGobletEnum.EM || primAttr == PrimAttrSandEnum.EM
                || primAttr == PrimAttrCircletEnum.EM) {
            return (double) Math.round(BASE_EM.value() + GROWTH_EM.value() * level);
        }
        // 暴击率
        else if (primAttr == PrimAttrCircletEnum.CRIT) {
            return (double) Math.round(BASE_CRIT.value() * 10 + GROWTH_CRIT.value() * 10 * level) * 0.1d;
        }
        // 暴击伤害
        else if (primAttr == PrimAttrCircletEnum.CRIT_DMG) {
            return (double) Math.round(BASE_CRIT_DMG.value() * 10 + GROWTH_CRIT_DMG.value() * 10 * level) * 0.1d;
        }
        // 治疗加成
        else if (primAttr == PrimAttrCircletEnum.HEAL) {
            return (double) Math.round(BASE_HEAL.value() * 10 + GROWTH_HEAL.value() * 10 * level) * 0.1d;
        }
        return 0;
    }
}
