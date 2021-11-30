package utils;

import enums.ChanceEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: MaxLEAF
 * @Date: 2021/08/28/0:10
 * @Description:
 */
public class Utils {
    /**
     * 从EnumList中随机抽取n个，实现/没实现ChanceEnum接口的会分开处理
     * 按 带权不放回模式实现
     * @param enumList
     * @param num
     * @param <T>
     * @return
     */
    public static <T extends Enum> List<T> getRandomEnum(List<T> enumList, int num) {
        if (enumList.isEmpty())
            return null;
        Random random = new Random();
        List<T> resultList = new ArrayList<>();
        T first = enumList.get(0);
        if (first instanceof ChanceEnum) {
            for (int loop = 0; loop < num; loop++) {
                double sum = enumList.stream().map(t -> ((ChanceEnum) t).chance()).reduce(Double::sum).get();
                double result = random.nextDouble() * sum;
                sum = 0;
                for (T each : enumList) {
                    sum += ((ChanceEnum) each).chance();
                    if (sum >= result) {
                        resultList.add(each);
                        enumList.remove(each);
                        break;
                    }
                }
            }
        } else {
            for (int loop = 0; loop < num; loop++) {
                int index = random.nextInt(enumList.size());
                resultList.add(enumList.get(index));
                enumList.remove(index);
            }
        }
        return resultList;
    }

    public static <T extends Enum> T getSingleRandomEnum(List<T> enumList) {
        return getRandomEnum(enumList, 1).get(0);
    }

    //简单抽取
    public static boolean isHit(double probability) {
        Random random = new Random();
        return random.nextDouble() < probability;
    }
}
