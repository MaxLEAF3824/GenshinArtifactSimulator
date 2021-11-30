package enums;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: MaxLEAF
 * @Date: 2021/08/28/0:01
 * @Description: 圣遗物套装名称枚举
 */
public enum SuitEnum {
    A("追忆之注连"),
    B("绝缘之旗印"),
    PURPLE("紫色圣遗物"),
    BLUE("蓝色圣遗物"),
    ;
    private final String name;

    SuitEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
