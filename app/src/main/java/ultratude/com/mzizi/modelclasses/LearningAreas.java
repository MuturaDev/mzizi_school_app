package ultratude.com.mzizi.modelclasses;

/**
 * Created by James on 18/05/2019.
 */

public class LearningAreas {


    private String learningAreaName;
    private String scoreDescription;
    private String remarks;



    public LearningAreas(String learningAreaName, String scoreDescription, String remarks) {
        this.learningAreaName = learningAreaName;
        this.scoreDescription = scoreDescription;
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "LearningAreas{" +
                "learningAreaName='" + learningAreaName + '\'' +
                ", scoreDescription='" + scoreDescription + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }

    public String getLearningAreaName() {
        return learningAreaName;
    }

    public String getScoreDescription() {
        return scoreDescription;
    }

    public String getRemarks() {
        return remarks;
    }
}
