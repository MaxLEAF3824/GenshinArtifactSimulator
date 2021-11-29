package enums;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: MaxLEAF
 * @Date: 2021/08/28/12:40
 * @Description:
 */
public enum UpMultiEnum implements ChanceEnum {
    DOUBLE(0.09,2), //双倍强化概率
    PENTA(0.01,5), //五倍强化概率
    SINGLE(0.90,1),
    ;
    private final double chance;
    private final double value;

    UpMultiEnum(double chance, int value) {
        this.chance = chance;
        this.value=value;
    }

    @Override
    public double chance() {
        return this.chance;
    }

    public double value(){
        return this.value;
    }
}
