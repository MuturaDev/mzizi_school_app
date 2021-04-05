package ultratude.com.mzizi.modelclasses;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 18/05/2019.
 */

public class Activities {

    private String activityName;
    private String courseName;
    private String levelName;
    private List<LearningAreas> learningAreaList  = new ArrayList<>();

    public Activities(String activityName, String courseName, String levelName) {
        this.activityName = activityName;
        this.courseName = courseName;
        this.levelName = levelName;

    }

    @Override
    public String toString() {
        return "Activities{" +
                "activityName='" + activityName + '\'' +
                ", courseName='" + courseName + '\'' +
                ", levelName='" + levelName + '\'' +
                ", learningAreaList=" + learningAreaList +
                '}';
    }

    public String getActivityName() {
        return activityName;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getLevelName() {
        return levelName;
    }


    public void setLearningAreaList(List<LearningAreas> learningAreaList) {
        this.learningAreaList = learningAreaList;
    }

    public List<LearningAreas> getLearningAreaList() {
        return learningAreaList;
    }
}
