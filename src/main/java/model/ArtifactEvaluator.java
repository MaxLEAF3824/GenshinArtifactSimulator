package model;

import enums.StarEnum;
import enums.SuitEnum;
import enums.attributes.*;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: MaxLEAF
 * @Date: 2021/08/27/0:20
 * @Description:
 */

public class ArtifactEvaluator {
    public static final double W_PRIM_FALSE = -99;
    public double w_CRIT;
    public double w_CRIT_DMG;
    public double w_ATK;
    public double w_ATK_P;
    public double w_DEF;
    public double w_DEF_P;
    public double w_HP;
    public double w_HP_P;
    public double w_ER;
    public double w_EM;
    public PrimAttrEnum primSand;
    public PrimAttrEnum primGoblet;
    public PrimAttrEnum primCirclet;
    public double niceLowerLimit;   //作为一个好圣遗物的最低要求


    public ArtifactEvaluator(double w_CRIT, double w_CRIT_DMG, double w_ATK, double w_ATK_P, double w_DEF,
                             double w_DEF_P, double w_HP, double w_HP_P, double w_ER, double w_EM, PrimAttrEnum primSand,
                             PrimAttrEnum primGoblet, PrimAttrEnum primCirclet, double niceLowerLimit) {
        this.w_CRIT = w_CRIT;
        this.w_CRIT_DMG = w_CRIT_DMG;
        this.w_ATK = w_ATK;
        this.w_ATK_P = w_ATK_P;
        this.w_DEF = w_DEF;
        this.w_DEF_P = w_DEF_P;
        this.w_HP = w_HP;
        this.w_HP_P = w_HP_P;
        this.w_ER = w_ER;
        this.w_EM = w_EM;
        this.primSand = primSand;
        this.primGoblet = primGoblet;
        this.primCirclet = primCirclet;
        this.niceLowerLimit = niceLowerLimit;
    }

    public ArtifactEvaluator() {
        this(
                2,
                1,
                0,
                1.3,
                0,
                0,
                0,
                0,
                0,
                0,
                PrimAttrSandEnum.ATK_P,
                PrimAttrGobletEnum.CRYO,
                PrimAttrCircletEnum.CRIT,
                10
        );
    }

    /**
     * 计算圣遗物的当前评分
     *
     * @param artifact
     * @return
     */
    public double evaluate(Artifact artifact) {
        double score = 0;
        for (int i = 0; i < artifact.subAttrs.length; i++) {
            SubAttrEnum subAttrs = artifact.subAttrs[i];
            double subAttrValues = artifact.subAttrValues[i];
            switch (subAttrs) {
                case EM:
                    score += w_EM * subAttrValues;
                    break;
                case ER:
                    score += w_ER * subAttrValues;
                    break;
                case HP:
                    score += w_HP * subAttrValues;
                    break;
                case ATK:
                    score += w_ATK * subAttrValues;
                    break;
                case DEF:
                    score += w_DEF * subAttrValues;
                    break;
                case CRIT:
                    score += w_CRIT * subAttrValues;
                    break;
                case CRIT_DMG:
                    score += w_CRIT_DMG * subAttrValues;
                    break;
                case HP_P:
                    score += w_HP_P * subAttrValues;
                    break;
                case ATK_P:
                    score += w_ATK_P * subAttrValues;
                    break;
                case DEF_P:
                    score += w_DEF_P * subAttrValues;
                    break;
            }
        }
        if (!isPrimTrue(artifact)) {
            score -= W_PRIM_FALSE;
        }
        return score;
    }

    /**
     * 根据圣遗物的当前评分和未来潜力来评价其综合期望
     *
     * @param artifact
     * @return
     */
    public double predict(Artifact artifact) {
        return evaluate(artifact) + potential(artifact);
    }

    /**
     * 计算圣遗物的未来潜力
     *
     * @param artifact
     * @return
     */
    public double potential(Artifact artifact) {

        double remainSubUpCount = Math.ceil((20 - artifact.level) / 4d);
        double avgUpSingle = 0;
        double avgUp = 0;
        /* 如果第四个副属性还没有出现,我们应该先单独计算这次强化的期望值 */
        if (artifact.subAttrValues[3] == 0) {
            remainSubUpCount--;
            Optional<Double> reduce = Arrays.stream(SubAttrUpEnum.values())
                    .filter(a -> a.parent() != artifact.subAttrs[0])
                    .filter(a -> a.parent() != artifact.subAttrs[1])
                    .filter(a -> a.parent() != artifact.subAttrs[2])
                    .filter(a -> a.name().endsWith("_2"))
                    .map(subAttrUpEnum -> {
                        switch (subAttrUpEnum) {
                            case EM_2:
                                return subAttrUpEnum.value() * w_EM;
                            case ER_2:
                                return subAttrUpEnum.value() * w_ER;
                            case HP_2:
                                return subAttrUpEnum.value() * w_HP;
                            case ATK_2:
                                return subAttrUpEnum.value() * w_ATK;
                            case DEF_2:
                                return subAttrUpEnum.value() * w_DEF;
                            case CRIT_2:
                                return subAttrUpEnum.value() * w_CRIT;
                            case CRIT_DMG_2:
                                return subAttrUpEnum.value() * w_CRIT_DMG;
                            case HP_P_2:
                                return subAttrUpEnum.value() * w_HP_P;
                            case ATK_P_2:
                                return subAttrUpEnum.value() * w_ATK_P;
                            case DEF_P_2:
                                return subAttrUpEnum.value() * w_DEF_P;
                        }
                        return 0d;
                    })
                    .reduce(Double::sum);
            if (reduce.isPresent())
                avgUp += reduce.get() / 7;
        }
        int count = 0;
        for (int i = 0; i < artifact.subAttrs.length; i++) {
            SubAttrEnum subAttr = artifact.subAttrs[i];
            // 如果这个词条还没出现，就不预测
            if (artifact.subAttrValues[i] == 0)
                continue;
            count++;
            switch (subAttr) {
                case EM:
                    avgUpSingle += w_EM * SubAttrUpEnum.EM_2.value();
                    break;
                case ER:
                    avgUpSingle += w_ER * SubAttrUpEnum.ER_2.value();
                    break;
                case HP:
                    avgUpSingle += w_HP * SubAttrUpEnum.HP_2.value();
                    break;
                case ATK:
                    avgUpSingle += w_ATK * SubAttrUpEnum.ATK_2.value();
                    break;
                case DEF:
                    avgUpSingle += w_DEF * SubAttrUpEnum.DEF_2.value();
                    break;
                case CRIT:
                    avgUpSingle += w_CRIT * SubAttrUpEnum.CRIT_2.value();
                    break;
                case CRIT_DMG:
                    avgUpSingle += w_CRIT_DMG * SubAttrUpEnum.CRIT_DMG_2.value();
                    break;
                case HP_P:
                    avgUpSingle += w_HP_P * SubAttrUpEnum.HP_P_2.value();
                    break;
                case ATK_P:
                    avgUpSingle += w_ATK_P * SubAttrUpEnum.ATK_P_2.value();
                    break;
                case DEF_P:
                    avgUpSingle += w_DEF_P * SubAttrUpEnum.DEF_P_2.value();
                    break;
            }
        }
        avgUpSingle /= count;
        avgUp = avgUpSingle * remainSubUpCount;
        return avgUp;
    }

    /**
     * 计算圣遗物的强化上限
     *
     * @param artifact
     * @return
     */
    public double potentialTopLimit(Artifact artifact) {

        double remainSubUpCount = Math.ceil((20 - artifact.level) / 4d);
        double maxUpSingle = 0;
        /* 如果第四个副属性还没有出现,我们应该先单独计算这次强化的期望值 */
        if (artifact.subAttrValues[3] == 0) {
            remainSubUpCount--;
            Optional<Double> reduce = Arrays.stream(SubAttrUpEnum.values())
                    .filter(a -> a.parent() != artifact.subAttrs[0])
                    .filter(a -> a.parent() != artifact.subAttrs[1])
                    .filter(a -> a.parent() != artifact.subAttrs[2])
                    .filter(a -> a.name().endsWith("_3"))
                    .map(subAttrUpEnum -> {
                        switch (subAttrUpEnum) {
                            case EM_3:
                                return subAttrUpEnum.value() * w_EM;
                            case ER_3:
                                return subAttrUpEnum.value() * w_ER;
                            case HP_3:
                                return subAttrUpEnum.value() * w_HP;
                            case ATK_3:
                                return subAttrUpEnum.value() * w_ATK;
                            case DEF_3:
                                return subAttrUpEnum.value() * w_DEF;
                            case CRIT_3:
                                return subAttrUpEnum.value() * w_CRIT;
                            case CRIT_DMG_3:
                                return subAttrUpEnum.value() * w_CRIT_DMG;
                            case HP_P_3:
                                return subAttrUpEnum.value() * w_HP_P;
                            case ATK_P_3:
                                return subAttrUpEnum.value() * w_ATK_P;
                            case DEF_P_3:
                                return subAttrUpEnum.value() * w_DEF_P;
                        }
                        return 0d;
                    })
                    .reduce(Double::max);
            if (reduce.isPresent())
                maxUpSingle = reduce.get();
        }
        for (int i = 0; i < artifact.subAttrs.length; i++) {
            SubAttrEnum subAttr = artifact.subAttrs[i];
            // 如果这个词条还没出现，就不预测
            if (artifact.subAttrValues[i] == 0)
                continue;
            switch (subAttr) {
                case EM:
                    if (maxUpSingle < w_EM * SubAttrUpEnum.EM_3.value())
                        maxUpSingle = w_EM * SubAttrUpEnum.EM_3.value();
                    break;
                case ER:
                    if (maxUpSingle < w_ER * SubAttrUpEnum.ER_3.value())
                        maxUpSingle = w_ER * SubAttrUpEnum.ER_3.value();
                    break;
                case HP:
                    if (maxUpSingle < w_HP * SubAttrUpEnum.HP_3.value())
                        maxUpSingle = w_HP * SubAttrUpEnum.HP_3.value();
                    break;
                case ATK:
                    if (maxUpSingle < w_ATK * SubAttrUpEnum.ATK_3.value())
                        maxUpSingle = w_ATK * SubAttrUpEnum.ATK_3.value();
                    break;
                case DEF:
                    if (maxUpSingle < w_DEF * SubAttrUpEnum.DEF_3.value())
                        maxUpSingle = w_DEF * SubAttrUpEnum.DEF_3.value();
                    break;
                case CRIT:
                    if (maxUpSingle < w_CRIT * SubAttrUpEnum.CRIT_3.value())
                        maxUpSingle = w_CRIT * SubAttrUpEnum.CRIT_3.value();
                    break;
                case CRIT_DMG:
                    if (maxUpSingle < w_CRIT_DMG * SubAttrUpEnum.CRIT_DMG_3.value())
                        maxUpSingle = w_CRIT_DMG * SubAttrUpEnum.CRIT_DMG_3.value();
                    break;
                case HP_P:
                    if (maxUpSingle < w_HP_P * SubAttrUpEnum.HP_P_3.value())
                        maxUpSingle = w_HP_P * SubAttrUpEnum.HP_P_3.value();
                    break;
                case ATK_P:
                    if (maxUpSingle < w_ATK_P * SubAttrUpEnum.ATK_P_3.value())
                        maxUpSingle = w_ATK_P * SubAttrUpEnum.ATK_P_3.value();
                    break;
                case DEF_P:
                    if (maxUpSingle < w_DEF_P * SubAttrUpEnum.DEF_P_3.value())
                        maxUpSingle = w_DEF_P * SubAttrUpEnum.DEF_P_3.value();
                    break;
            }
        }
        return maxUpSingle * remainSubUpCount + evaluate(artifact);
    }

    public boolean isTrash(Artifact artifact) {
        if (artifact == null)
            return false;
        return !isPrimTrue(artifact) || potentialTopLimit(artifact) < niceLowerLimit;
    }

    public boolean isPrimTrue(Artifact artifact) {
        if (artifact == null)
            return false;
        switch (artifact.category) {
            case SAND:
                return artifact.primAttr == primSand;
            case GOBLET:
                return artifact.primAttr == primGoblet;
            case CIRCLET:
                return artifact.primAttr == primCirclet;
            default:
                return true;
        }
    }

}
