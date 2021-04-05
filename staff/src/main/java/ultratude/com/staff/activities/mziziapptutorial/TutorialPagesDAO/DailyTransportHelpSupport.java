package ultratude.com.staff.activities.mziziapptutorial.TutorialPagesDAO;

import ultratude.com.staff.R;

/**
 * Created by James on 27/05/2019.
 */

public class DailyTransportHelpSupport {

    private int[] helpText;

    public int[] getHelpText() {
        int[] arr = {
                R.string.daily_transport_q_1
        };
        return arr;
    }

    private int[] slideDescription;
    private String slideImages;


    public int[] getSlideDescription() {
        int[] arr  = {
                R.string.tran_daily_step_1,
                R.string.tran_daily_step_2,
                R.string.tran_daily_step_3,
                R.string.tran_daily_step_4,
                R.string.tran_daily_step_5,
                R.string.tran_daily_step_6,
                R.string.tran_daily_step_7,
                R.string.tran_daily_step_8,
                R.string.tran_daily_step_9




        };



        return arr;
    }

    public String[] getSlideImages() {
        String[] arr = {
                "tran_daily_step_1_img_1.PNG",
                "tran_daily_step_2_img_2.PNG",
                "tran_daily_step_3_img_3.PNG",
                "tran_daily_step_4_img_4.PNG",
                "tran_daily_step_5_img_5.PNG",
                "tran_daily_step_6_img_6.PNG",
                "tran_daily_step_7_img_7.PNG",
                "tran_daily_step_8_img_8.PNG",
                "tran_daily_step_9_img_9.PNG"


        };
        return arr;
    }

}
