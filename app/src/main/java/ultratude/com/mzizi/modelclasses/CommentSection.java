package ultratude.com.mzizi.modelclasses;

/**
 * Created by James on 21/05/2019.
 */

public class CommentSection {
    private String headTeacherComments;
    private String gradeFacilitatorComments;

    public CommentSection(String headTeacherComments, String gradeFacilitatorComments) {
        this.headTeacherComments = headTeacherComments;
        this.gradeFacilitatorComments = gradeFacilitatorComments;
    }

    public String getHeadTeacherComments() {
        return headTeacherComments;
    }

    public String getGradeFacilitatorComments() {
        return gradeFacilitatorComments;
    }
}
