package ultratude.com.staff.activities.mziziapptutorial.TutorialPagesDAO;

import ultratude.com.staff.R;

/**
 * Created by James on 27/05/2019.
 */

public class EnqExamHistoryHelpSupport {

    private int[] helpText;

    public int[] getHelpText() {
        int[] arr = {
                R.string.academic_performance_q_1
        };
        return arr;
    }

    private int[] slideDescription;
    private String slideImages;


    public int[] getSlideDescription() {
        int[] arr  = {
                R.string.enq_exam_hist_step_1,
                R.string.enq_exam_hist_step_2,
                R.string.enq_exam_hist_step_3,
                R.string.enq_exam_hist_step_4,
                R.string.enq_exam_hist_step_5,
                R.string.enq_exam_hist_step_6


        };



        return arr;
    }

    public String[] getSlideImages() {
        String[] arr = {
                "enq_exam_hist_step_1_img_1.PNG",
                "enq_exam_hist_step_2_img_2.PNG",
                "enq_exam_hist_step_3_img_3.PNG",
                "enq_exam_hist_step_4_img_4.PNG",
                "enq_exam_hist_step_5_img_5.PNG",
                "enq_exam_hist_step_6_img_6.PNG"

        };
        return arr;
    }

}
