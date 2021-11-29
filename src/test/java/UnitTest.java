import com.csvreader.CsvWriter;
import enums.StarEnum;
import enums.SuitEnum;
import model.Artifact;
import model.ArtifactEvaluator;
import model.Domain;
import model.Player;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: MaxLEAF
 * @Date: 2021/08/28/22:18
 * @Description:
 */
public class UnitTest {

    @Test
    public void coverageTest() {
        Domain domain = new Domain();
        Player player = new Player();
        player.grind(domain, SuitEnum.A,10);
        player.reduce();
    }

    /**
     * 完整的平均成长结果获取
     */
    @Test
    public void basicResultTest() {
        Domain domain = new Domain();
        int day = 365;
        int playerNum = 1000;
        String filePath = "basicResult.csv";
        CsvWriter csvWriter = new CsvWriter(filePath, ',', Charset.forName("GBK"));
        String[] scores = new String[day];
        for (int i = 0; i < playerNum; i++) {
            Player player = new Player();
            System.out.println(i);
            for (int j = 0; j < day; j++) {
                player.grind(domain, SuitEnum.A, 9);
                player.reduce();
                double score = player.calculateOverallScore();
                scores[j] = String.valueOf(score);
            }
            try {
                csvWriter.writeRecord(scores);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        csvWriter.close();
    }

    /**
     * 对比不同初始狗粮数量对结果的影响
     */
    @Test
    public void initTrashNumTest() {
        int day = 100;
        int playerNum = 100;
        String filePath = "initTrashNumTest.csv";
        Domain domain = new Domain();
        CsvWriter csvWriter = new CsvWriter(filePath, ',', Charset.forName("GBK"));

        // 比较不同起始蓝色狗粮数量下的成长速度
        for (int blueTrashNum = 0; blueTrashNum <= 600; blueTrashNum += 200) {
            double[] scores = new double[day];
            for (int i = 0; i < playerNum; i++) {
                Player player = new Player();
                System.out.println("第" + i + "个人");
                Artifact artifact = Artifact.buildRandomArtifact(SuitEnum.BLUE, StarEnum.BLUE);
                for (int j = 0; j < blueTrashNum; j++) {
                    player.getTrash().add(artifact);
                }
                for (int j = 0; j < day; j++) {
                    player.grind(domain, SuitEnum.A, 9);
                    player.reduce();
                    double score = player.calculateOverallScore();
                    scores[j] += score;
                }
            }

            // 记录结果
            String[] scoresString = new String[day];
            for (int i = 0; i < day; i++)
                scoresString[i] = String.valueOf(scores[i] / playerNum);
            try {
                csvWriter.writeRecord(scoresString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        csvWriter.close();
    }

    /**
     * 对比不同强化节奏对结果的影响
     */
    @Test
    public void dayReduceTest() {
        int day = 100;
        int playerNum = 100;
        String filePath = "dayReduceTest.csv";
        Domain domain = new Domain();
        CsvWriter csvWriter = new CsvWriter(filePath, ',', Charset.forName("GBK"));

        // 比较不同强化节奏的成长速度
        for (int reduceDay = 1; reduceDay <= 50; reduceDay += 5) {
            double[] scores = new double[day];
            for (int i = 0; i < playerNum; i++) {
                Player player = new Player();
                System.out.println("第" + i + "个人");
                for (int j = 0; j < day; j++) {
                    player.grind(domain, SuitEnum.A, 9);
                    if (j % reduceDay == 0)
                        player.reduce();
                    double score = player.calculateOverallScore();
                    scores[j] += score;
                }
            }

            // 记录结果
            String[] scoresString = new String[day];
            for (int i = 0; i < day; i++)
                scoresString[i] = String.valueOf(scores[i] / playerNum);
            try {
                csvWriter.writeRecord(scoresString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        csvWriter.close();
    }

    /**
     * 对比不同金色圣遗物狗粮认定标准对结果的影响
     */
    @Test
    public void niceLowerLimitTest() {
        int day = 100;
        int playerNum = 200;
        String filePath = "niceLowerLimitTest5—15.csv";
        Domain domain = new Domain();
        CsvWriter csvWriter = new CsvWriter(filePath, ',', Charset.forName("GBK"));

        // 比较不同不同金色圣遗物狗粮认定标准对结果的影响
        for (int niceLowerLimit = 5; niceLowerLimit <= 15; niceLowerLimit += 1) {
            double[] scores = new double[day];
            for (int i = 0; i < playerNum; i++) {
                Player player = new Player();
                player.getEvaluator().niceLowerLimit = niceLowerLimit;
                System.out.println("第" + i + "个人");
                for (int j = 0; j < day; j++) {
                    player.grind(domain, SuitEnum.A, 9);
                    player.reduce();
                    double score = player.calculateOverallScore();
                    scores[j] += score;
                }
            }

            // 记录结果
            String[] scoresString = new String[day];
            for (int i = 0; i < day; i++)
                scoresString[i] = String.valueOf(scores[i] / playerNum);
            try {
                csvWriter.writeRecord(scoresString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        csvWriter.close();
    }
}