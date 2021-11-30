package model;

import enums.CategoryEnum;
import enums.ExpEnum;
import enums.StarEnum;
import enums.SuitEnum;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: MaxLEAF
 * @Date: 2021/08/27/0:57
 * @Description:
 */

public class Player {
    int grindCount;                  //刷本次数
    Queue<Artifact> niceFlowers;     //花胚区
    Queue<Artifact> niceFeathers;    //羽毛胚区
    Queue<Artifact> niceSands;       //沙漏胚区
    Queue<Artifact> niceGoblets;     //杯子胚区
    Queue<Artifact> niceCirclets;     //头胚区
    Queue<Artifact> trash;           //狗粮区
    List<Artifact> protect;         //保护区
    Queue<Artifact> finish;           //+20区
    ArtifactEvaluator evaluator;     //圣遗物评价器

    public Queue<Artifact> getNiceFlowers() {
        return niceFlowers;
    }

    public Queue<Artifact> getNiceFeathers() {
        return niceFeathers;
    }

    public Queue<Artifact> getNiceSands() {
        return niceSands;
    }

    public Queue<Artifact> getNiceGoblets() {
        return niceGoblets;
    }

    public Queue<Artifact> getNiceCirclets() {
        return niceCirclets;
    }

    public Queue<Artifact> getTrash() {
        return trash;
    }

    public List<Artifact> getProtect() {
        return protect;
    }

    public Queue<Artifact> getFinish() {
        return finish;
    }

    public ArtifactEvaluator getEvaluator() {
        return evaluator;
    }

    public Player(ArtifactEvaluator eva) {
        this.evaluator = eva;
        // 根据predict值比较
        Comparator<Artifact> cmpPredict = new Comparator<Artifact>() {
            @Override
            public int compare(Artifact a1, Artifact a2) {
                double predict2 = eva.predict(a2);
                double predict1 = eva.predict(a1);
                if (predict2 - predict1 > 0)
                    return 1;
                else if (predict2 - predict1 < 0)
                    return -1;
                return 0;
            }
        };
        // 根据作为狗粮的经验量比较
        Comparator<Artifact> cmpExp = new Comparator<Artifact>() {
            @Override
            public int compare(Artifact a1, Artifact a2) {
                return a1.getTotalExpAsTrash() - a2.getTotalExpAsTrash();
            }
        };
        this.niceFlowers = new PriorityQueue<>(cmpPredict);
        this.niceFeathers = new PriorityQueue<>(cmpPredict);
        this.niceSands = new PriorityQueue<>(cmpPredict);
        this.niceGoblets = new PriorityQueue<>(cmpPredict);
        this.niceCirclets = new PriorityQueue<>(cmpPredict);
        this.trash = new PriorityQueue<>(cmpExp);
        this.protect = new ArrayList<>();
        this.finish = new PriorityQueue<>(cmpPredict);
    }

    public Player() {
        this(new ArtifactEvaluator());
    }

    public void grind(Domain domain, SuitEnum targetSuit) {
        grind(domain, targetSuit, 1);
    }

    /**
     * 刷n次某副本,目标套装为SuitA
     * 1.将SuitA分类放在不同的胚区
     * 2.将SuitB放进保护区
     * 3.清理狗粮
     *
     * @param domain
     */
    public void grind(Domain domain, SuitEnum targetSuit, int num) {
        // 副本出货
        List<Artifact> output = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            output.addAll(domain.output());
            grindCount++;
        }

        // 分类保存
        List<Artifact> golds = output.stream().filter(a -> a.star == StarEnum.GOLD).collect(Collectors.toList());
        List<Artifact> purpleAndBlue = output.stream().filter(a -> a.star != StarEnum.GOLD).collect(Collectors.toList());
        golds.forEach(a -> {
            if (evaluator.isTrash(a))
                trash.add(a);
            else if (a.suit != targetSuit)
                protect.add(a);
            else if (a.category == CategoryEnum.FLOWER)
                niceFlowers.add(a);
            else if (a.category == CategoryEnum.FEATHER)
                niceFeathers.add(a);
            else if (a.category == CategoryEnum.SAND)
                niceSands.add(a);
            else if (a.category == CategoryEnum.GOBLET)
                niceGoblets.add(a);
            else
                niceCirclets.add(a);
        });
        trash.addAll(purpleAndBlue);
    }

    /**
     * 聚合:
     * 1.从现有的所有圣遗物中挑出最适合强化的一个
     * 2.将这个圣遗物强化4级
     * 3.清理狗粮
     * 4.重复1 2 3 直到狗粮用尽
     */
    public void reduce() {
        while (!trash.isEmpty()) {
            Queue<Artifact> bestQueue = choseBestQueue();   //选出当前最适合强化的位置
            Artifact bestArtifact = bestQueue.peek();
            if (bestArtifact == null) {
                return;
            }
            levelUpArtifactQuad(bestArtifact);
        }
    }

    /**
     * 内部接口:强化圣遗物一次
     * 一次用一个狗粮圣遗物，狗粮选择低于强化所需经验上限下,经验量最高的
     *
     * @param artifact
     */
    public void levelUpArtifact(Artifact artifact) {
        if (trash.isEmpty())
            return;
        int remainExp = ExpEnum.EXP_TOP_LIMIT.value() - artifact.getTotalExp();
        // pre-process 判断圣遗物是否满级
        if (remainExp <= 0)
            return;
        //选择狗粮并强化
        Optional<Artifact> firstSmaller = trash.stream().filter(a -> a.getTotalExpAsTrash() < remainExp).findFirst();
        Artifact targetTrash;
        if (firstSmaller.isPresent()) {
            targetTrash = firstSmaller.get();
            trash.remove(targetTrash);
        } else {
            targetTrash = trash.poll();
        }
        artifact.levelUp(targetTrash.getTotalExpAsTrash());
        // post-process 判断圣遗物是否达到20级，达到则放进+20区
        if (artifact.level == 20) {
            niceFlowers.remove(artifact);
            niceFeathers.remove(artifact);
            niceSands.remove(artifact);
            niceGoblets.remove(artifact);
            niceCirclets.remove(artifact);
            finish.add(artifact);
        }
    }

    /**
     * 将圣遗物升到下一个四的倍数等级
     *
     * @param artifact
     */
    public void levelUpArtifactQuad(Artifact artifact) {
        int targetLevel = (artifact.level / 4 + 1) * 4;
        while (artifact.level < targetLevel && !trash.isEmpty()) {
            levelUpArtifact(artifact);
        }
    }

    /**
     * 这个函数决定升级哪个位置的哪个圣遗物:
     * <p>
     * 1.短板位
     * 即+20区中,该位置的圣遗物压根没有或者评分远低于其他位置
     * 圣遗物较少的时候，玩家倾向于升级词条最好的圣遗物
     * 但是当同一位置的圣遗物变多时，玩家倾向于升级自己最弱位置的圣遗物以补齐短板
     * 即:+20区中的高分圣遗物应当对同位置的圣遗物胚产生优先级上的限制作用
     * 否则，可能会因为某些位置圣遗物的天然的副词条劣势而无法得到足够的关注，大家都知道双爆花比有好词条得的攻击沙好出多了
     * 例如:你已经有了一个高分花，但是沙还一件能用的都没有，现在手上有一个好花胚一个稍次的沙胚，这时选沙胚就更好
     * 因此按以下标准给每个圣遗物位的强化优先级打分：
     * 该位置的强化优先级 = 该位置最好的圣遗物胚的综合期望 - 在+20区中该位置最好圣遗物的综合期望（若+20区中没有则为0）
     * <p>
     * 2.同位置下综合期望(predict值)高
     * 综合期望 = 当前实力 + 未来潜力
     * 当前实力高但未来潜力低的圣遗物,不如当前实力低但未来潜力高的圣遗物
     * 例如:+16全歪小攻击的圣遗物，不如+0双爆大攻击的圣遗物，尽管前者可能评分更高
     *
     * @return
     */
    public Queue<Artifact> choseBestQueue() {
        // 获取+20区中各部位圣遗物评分
        double maxFinishScoreFlower = 0;
        double maxFinishScoreFeather = 0;
        double maxFinishScoreSand = 0;
        double maxFinishScoreGoblet = 0;
        double maxFinishScoreCirclet = 0;
        for (Artifact each : finish) {
            double score = evaluator.evaluate(each);
            switch (each.category) {
                case FLOWER:
                    maxFinishScoreFlower = Double.max(maxFinishScoreFlower, score);
                    break;
                case FEATHER:
                    maxFinishScoreFeather = Double.max(maxFinishScoreFeather, score);
                    break;
                case SAND:
                    maxFinishScoreSand = Double.max(maxFinishScoreSand, score);
                    break;
                case GOBLET:
                    maxFinishScoreGoblet = Double.max(maxFinishScoreGoblet, score);
                    break;
                case CIRCLET:
                    maxFinishScoreCirclet = Double.max(maxFinishScoreCirclet, score);
                    break;
            }
        }

        Queue<Artifact> bestQueue = niceFlowers;
        double bestScore;
        double currScore;
        if (niceFlowers.isEmpty())
            bestScore = -999;
        else
            bestScore = evaluator.predict(niceFlowers.peek()) - maxFinishScoreFlower;
        if (niceFeathers.isEmpty())
            currScore = -999;
        else
            currScore = evaluator.predict(niceFeathers.peek()) - maxFinishScoreFeather;
        if (currScore > bestScore) {
            bestScore = currScore;
            bestQueue = niceFeathers;
        }
        if (niceSands.isEmpty())
            currScore = -999;
        else
            currScore = evaluator.predict(niceSands.peek()) - maxFinishScoreSand;
        if (currScore > bestScore) {
            bestScore = currScore;
            bestQueue = niceSands;
        }
        if (niceGoblets.isEmpty())
            currScore = -999;
        else
            currScore = evaluator.predict(niceGoblets.peek()) - maxFinishScoreGoblet;
        if (currScore > bestScore) {
            bestScore = currScore;
            bestQueue = niceGoblets;
        }
        if (niceCirclets.isEmpty())
            currScore = -999;
        else
            currScore = evaluator.predict(niceCirclets.peek()) - maxFinishScoreCirclet;
        if (currScore > bestScore) {
            bestQueue = niceCirclets;
        }
        return bestQueue;
    }

    /**
     * 获取刷了多少次本
     *
     * @return
     */
    public int getGrindCount() {
        return grindCount;
    }

    public Artifact getBestArtifactFromSinglePosition(Queue<Artifact> queue, CategoryEnum categoryQueue) {
        // 根据predict值比较
        Comparator<Artifact> cmpEvaluate = new Comparator<Artifact>() {
            @Override
            public int compare(Artifact a1, Artifact a2) {
                double predict2 = evaluator.evaluate(a2);
                double predict1 = evaluator.evaluate(a1);
                if (predict2 - predict1 > 0)
                    return 1;
                else if (predict2 - predict1 < 0)
                    return -1;
                return 0;
            }
        };
        Queue<Artifact> evaluation = new PriorityQueue<>(cmpEvaluate);
        evaluation.addAll(queue);
        evaluation.addAll(finish.stream().filter(a -> a.category == categoryQueue).collect(Collectors.toList()));
        return evaluation.peek();
    }

    public double calculateOverallScore() {
        double[] scores = new double[5];
        Artifact a1 = getBestArtifactFromSinglePosition(niceFlowers, CategoryEnum.FLOWER);
        if (a1 != null)
            scores[0] = evaluator.evaluate(a1);
        Artifact a2 = getBestArtifactFromSinglePosition(niceFeathers, CategoryEnum.FEATHER);
        if (a2 != null)
            scores[1] = evaluator.evaluate(a2);
        Artifact a3 = getBestArtifactFromSinglePosition(niceSands, CategoryEnum.SAND);
        if (a3 != null)
            scores[2] = evaluator.evaluate(a3);
        Artifact a4 = getBestArtifactFromSinglePosition(niceGoblets, CategoryEnum.GOBLET);
        if (a4 != null)
            scores[3] = evaluator.evaluate(a4);
        Artifact a5 = getBestArtifactFromSinglePosition(niceCirclets, CategoryEnum.CIRCLET);
        if (a5 != null)
            scores[4] = evaluator.evaluate(a5);
        double min = Arrays.stream(scores).reduce(Double::min).getAsDouble();
        double sum = Arrays.stream(scores).reduce(Double::sum).getAsDouble();
        return (sum - min) / 4d;
    }

}
