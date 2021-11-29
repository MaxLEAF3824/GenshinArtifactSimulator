package model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: MaxLEAF
 * @Date: 2021/08/30/21:15
 * @Description:
 */
public class ArtifactGroupDisplayer {
    List<Artifact> artifactGroup;
    ArtifactEvaluator evaluator;
    double overallScore;

    public void calculateOverallScore() {
        overallScore = 0;
        for (Artifact artifact : artifactGroup) {
            overallScore += evaluator.evaluate(artifact);
        }
    }

}
