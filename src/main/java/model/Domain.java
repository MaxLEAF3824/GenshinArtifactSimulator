package model;

import enums.DomainEnum;
import enums.StarEnum;
import enums.SuitEnum;
import utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: MaxLEAF
 * @Date: 2021/08/28/19:37
 * @Description:
 */
public class Domain {
    public String domainName;
    public SuitEnum suitA;
    public SuitEnum suitB;

    public Domain(String domainName, SuitEnum suitA, SuitEnum suitB) {
        this.domainName = domainName;
        this.suitA = suitA;
        this.suitB = suitB;
    }

    public Domain() {
        this("追忆本", SuitEnum.A, SuitEnum.B);
    }

    public List<Artifact> output() {
        List<Artifact> artifacts = new ArrayList<>();
        int goldNum = (int) DomainEnum.BASE_GOLD.value();
        int purpleNum = (int) DomainEnum.BASE_PURPLE.value();
        int blueNum = (int) DomainEnum.BASE_BLUE.value();
        if (Utils.isHit(DomainEnum.BOUNS_GOLD.value()))
            goldNum++;
        if (Utils.isHit(DomainEnum.BOUNS_PURPLE.value()))
            purpleNum++;
        if (Utils.isHit(DomainEnum.BOUNS_BLUE.value()))
            blueNum++;
        for (int i = 0; i < goldNum; i++) {
            SuitEnum suit = Utils.getSingleRandomEnum(new ArrayList<>(Arrays.asList(suitA, suitB)));
            artifacts.add(Artifact.buildRandomArtifact(suit, StarEnum.GOLD));
        }
        for (int i = 0; i < purpleNum; i++) {
            artifacts.add(Artifact.buildRandomArtifact(SuitEnum.PURPLE, StarEnum.PURPLE));
        }
        for (int i = 0; i < blueNum; i++) {
            artifacts.add(Artifact.buildRandomArtifact(SuitEnum.BLUE, StarEnum.BLUE));
        }
        return artifacts;
    }


}
