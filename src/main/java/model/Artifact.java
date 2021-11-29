package model;

import enums.*;
import enums.attributes.*;
import utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: MaxLEAF
 * @Date: 2021/08/27/0:08
 * @Description:
 */


public class Artifact {
    public static int idAccumulate = 0; //递增id统计
    public int id;                      //唯一标识符
    public int level;                   //等级
    public int currExp;                 //当前等级多余经验
    public SuitEnum suit;               //套装类型
    public StarEnum star;               //星级
    public CategoryEnum category;       //种类
    public PrimAttrEnum primAttr;       //主属性
    public double primAttrValue;        //主属性数值
    public SubAttrEnum[] subAttrs;      //副属性
    public double[] subAttrValues;      //副属性数值

    public Artifact(SuitEnum suit, StarEnum star, CategoryEnum categoryEnum, PrimAttrEnum primAttr,
                    double primAttrValue, SubAttrEnum[] subAttrs, double[] subAttrValues) {
        this.id = idAccumulate;
        idAccumulate++;
        this.level = 0;
        this.currExp = 0;
        this.suit = suit;
        this.star = star;
        this.category = categoryEnum;
        this.primAttr = primAttr;
        this.primAttrValue = primAttrValue;
        this.subAttrs = subAttrs;
        this.subAttrValues = subAttrValues;
    }

    /**
     * 内部接口:初始化圣遗物
     *
     * @param suit
     * @param star
     * @param category
     * @param primAttr
     * @param subAttrList
     * @return
     */
    private static Artifact initArtifact(SuitEnum suit, StarEnum star, CategoryEnum category, PrimAttrEnum primAttr, List<SubAttrEnum> subAttrList) {
        /* 随机生成4个圣遗物的初始副属性 */
        List<SubAttrEnum> randomSubAttrs = Utils.getRandomEnum(subAttrList, 4);
        SubAttrEnum[] subAttrs = randomSubAttrs.toArray(new SubAttrEnum[0]);

        /* 构造圣遗物*/
        double primAttrValue = 0;
        double[] subAttrValues = {0, 0, 0, 0};
        Artifact artifact = new Artifact(suit, star, category, primAttr, primAttrValue, subAttrs, subAttrValues);

        /* 初始化圣遗物的主属性和副属性*/
        artifact.updatePrimAttr();
        artifact.upSubAttr(0);
        artifact.upSubAttr(1);
        artifact.upSubAttr(2);

        /* 按一定概率生成初始4副属性 */
        if (Utils.isHit(SubAttrUpEnum.FOUR_ATTR.value()))
            artifact.upSubAttr(3);
        return artifact;
    }

    /**
     * 创建花
     *
     * @param suit
     * @param star
     * @return
     */
    public static Artifact buildFlower(SuitEnum suit, StarEnum star) {
        CategoryEnum category = CategoryEnum.FLOWER;
        PrimAttrEnum primAttr = PrimAttrFlowerEnum.HP;
        List<SubAttrEnum> subAttrList = new ArrayList<>(Arrays.asList(SubAttrEnum.values()));
        subAttrList.remove(SubAttrEnum.HP); // 副词条没有小生命
        return initArtifact(suit, star, category, primAttr, subAttrList);
    }

    /**
     * 创建羽毛
     *
     * @param suit
     * @param star
     * @return
     */
    public static Artifact buildFeather(SuitEnum suit, StarEnum star) {
        CategoryEnum category = CategoryEnum.FEATHER;
        PrimAttrEnum primAttr = PrimAttrFeatherEnum.ATK;
        List<SubAttrEnum> subAttrList = new ArrayList<>(Arrays.asList(SubAttrEnum.values()));
        subAttrList.remove(SubAttrEnum.ATK);    //副词条没有小攻击
        return initArtifact(suit, star, category, primAttr, subAttrList);
    }

    /**
     * 创建沙
     *
     * @param suit
     * @param star
     * @return
     */
    public static Artifact buildSand(SuitEnum suit, StarEnum star) {
        CategoryEnum category = CategoryEnum.SAND;
        List<PrimAttrSandEnum> primAttrList = new ArrayList<>(Arrays.asList(PrimAttrSandEnum.values()));
        PrimAttrSandEnum primAttr = Utils.getSingleRandomEnum(primAttrList);
        List<SubAttrEnum> subAttrList = new ArrayList<>(Arrays.asList(SubAttrEnum.values()));

        // 根据主词条情况移除副词条出现可能性
        switch (primAttr) {
            case EM:
                subAttrList.remove(SubAttrEnum.EM);
                break;
            case ER:
                subAttrList.remove(SubAttrEnum.ER);
                break;
            case DEF_P:
                subAttrList.remove(SubAttrEnum.DEF_P);
                break;
            case ATK_P:
                subAttrList.remove(SubAttrEnum.ATK_P);
                break;
            case HP_P:
                subAttrList.remove(SubAttrEnum.HP_P);
                break;
        }
        return initArtifact(suit, star, category, primAttr, subAttrList);
    }

    /**
     * 创建杯子
     *
     * @param suit
     * @param star
     * @return
     */
    public static Artifact buildGoblet(SuitEnum suit, StarEnum star) {
        CategoryEnum category = CategoryEnum.GOBLET;
        List<PrimAttrGobletEnum> primAttrList = new ArrayList<>(Arrays.asList(PrimAttrGobletEnum.values()));
        PrimAttrGobletEnum primAttr = Utils.getSingleRandomEnum(primAttrList);
        List<SubAttrEnum> subAttrList = new ArrayList<>(Arrays.asList(SubAttrEnum.values()));

        // 根据主词条情况移除副词条出现可能性
        switch (primAttr) {
            case EM:
                subAttrList.remove(SubAttrEnum.EM);
                break;
            case DEF_P:
                subAttrList.remove(SubAttrEnum.DEF_P);
                break;
            case ATK_P:
                subAttrList.remove(SubAttrEnum.ATK_P);
                break;
            case HP_P:
                subAttrList.remove(SubAttrEnum.HP_P);
                break;
        }
        return initArtifact(suit, star, category, primAttr, subAttrList);
    }

    /**
     * 创建头
     *
     * @param suit
     * @param star
     * @return
     */
    public static Artifact buildCirclet(SuitEnum suit, StarEnum star) {
        CategoryEnum category = CategoryEnum.CIRCLET;
        List<PrimAttrCircletEnum> primAttrList = new ArrayList<>(Arrays.asList(PrimAttrCircletEnum.values()));
        PrimAttrCircletEnum primAttr = Utils.getSingleRandomEnum(primAttrList);
        List<SubAttrEnum> subAttrList = new ArrayList<>(Arrays.asList(SubAttrEnum.values()));

        // 根据主词条情况移除副词条出现可能性
        switch (primAttr) {
            case CRIT:
                subAttrList.remove(SubAttrEnum.CRIT);
                break;
            case CRIT_DMG:
                subAttrList.remove(SubAttrEnum.CRIT_DMG);
                break;
            case EM:
                subAttrList.remove(SubAttrEnum.EM);
                break;
            case DEF_P:
                subAttrList.remove(SubAttrEnum.DEF_P);
                break;
            case ATK_P:
                subAttrList.remove(SubAttrEnum.ATK_P);
                break;
            case HP_P:
                subAttrList.remove(SubAttrEnum.HP_P);
                break;
        }
        return initArtifact(suit, star, category, primAttr, subAttrList);
    }

    /**
     * 随机生成一个圣遗物
     *
     * @param suit
     * @param star
     * @return
     */
    public static Artifact buildRandomArtifact(SuitEnum suit, StarEnum star) {
        CategoryEnum category = Utils.getSingleRandomEnum(new ArrayList<>(Arrays.asList(CategoryEnum.values())));
        // 随机生成5种圣遗物中的一种
        switch (category) {
            case FLOWER:
                return buildFlower(suit, star);
            case FEATHER:
                return buildFeather(suit, star);
            case SAND:
                return buildSand(suit, star);
            case GOBLET:
                return buildGoblet(suit, star);
            default:
                return buildCirclet(suit, star);
        }
    }

    /**
     * 获取一个圣遗物被用于强化时的经验量
     *
     * @return
     */
    public int getTotalExpAsTrash() {
        // 升级该圣遗物时累计用的经验
        int count = getTotalExp();

        // 这部分经验*0.8
        count = count * ExpEnum.EXP_LOSS_P.value() / 100;

        // 加上圣遗物基础自带的经验
        switch (star) {
            case GOLD:
                count += ExpEnum.EXP_GOLD.value();
                break;
            case PURPLE:
                count += ExpEnum.EXP_PURPLE.value();
                break;
            case BLUE:
                count += ExpEnum.EXP_BLUE.value();
                break;
        }
        return count;
    }

    /**
     * 获取升级该圣遗物时累计用的经验
     *
     * @return
     */
    public int getTotalExp() {
        int count = 0;
        for (int i = 0; i < level; i++) {
            count += ExpEnum.getExpByLevel(i);
        }
        count += currExp;
        return count;
    }

    /**
     * 圣遗物升级
     *
     * @param exp
     */
    public void levelUp(int exp) {
        // 等级20的圣遗物无法强化
        if (this.level >= 20) {
            return;
        }
        // 按几率翻倍经验
        UpMultiEnum[] probabilityEnums = {UpMultiEnum.DOUBLE, UpMultiEnum.PENTA, UpMultiEnum.SINGLE};
        UpMultiEnum levelUpMulti = Utils.getSingleRandomEnum(new ArrayList<>(Arrays.asList(probabilityEnums)));
        exp *= levelUpMulti.value();

        // 提升等级
        int startLevel = level + 1;
        int remainExp = currExp + exp;
        while (remainExp >= ExpEnum.getExpByLevel(level) && level <= 20) {
            remainExp -= ExpEnum.getExpByLevel(level);
            level++;
        }
        currExp = remainExp;

        // 升级副属性
        for (; startLevel <= level; startLevel++) {
            if (startLevel % 4 == 0) {
                upSubAttr();
            }
        }

        // 更新主属性
        updatePrimAttr();
    }

    /**
     * 随机升级一个位置的副属性
     */
    public void upSubAttr() {
        Random random = new Random();
        this.upSubAttr(random.nextInt(4));
    }

    /**
     * 升级指定位置的副属性
     *
     * @param index
     */
    public void upSubAttr(int index) {
        // 获取副属性成长枚举中正确匹配副属性的枚举
        List<SubAttrUpEnum> collect = Arrays.stream(SubAttrUpEnum.values()).filter(
                subAttrUpEnum -> subAttrUpEnum.parent() == subAttrs[index]).collect(Collectors.toList());
        // 获取强化具体数值
        SubAttrUpEnum subAttrUp = Utils.getSingleRandomEnum(collect);
        this.subAttrValues[index] += subAttrUp.value();
    }

    /**
     * 更新主属性数值
     *
     * @return
     */
    public void updatePrimAttr() {
        this.primAttrValue = PrimAttrUpEnum.getPrimAttrValue(this.primAttr, this.level);
    }

    @Override
    public String toString() {
        return "圣遗物{" +
                "\n    level=" + level +
                "\n    suit=" + suit.getName() +
                "\n    star=" + star +
                "\n    category=" + category +
                "\n    primAttr=" + primAttr +
                "\n    subAttrs=" + Arrays.toString(subAttrs) +
                "\n    subAttrValues=" + Arrays.toString(subAttrValues) + "\n" +
                '}';
    }
}
