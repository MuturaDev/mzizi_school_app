package ultratude.com.mzizi.notificationpg.NotificationSQDB;

import android.provider.BaseColumns;

/**
 * Created by James on 01/09/2018.
 */

public class LocalDBContract {

    public class NotificationTB implements BaseColumns {

        //COLUMN NAMES
        public static final String MSGID = "msgID";
        public static final String MSG = "msg";
        public static final String DATESENT = "dateSent";
        public static final String STUDID = "StudID";

        //TABLE NAME
        public static final String NOTIFICAITON_TB_NAME = "Notification_TB";

    }


    public class NotificationReadTracking implements  BaseColumns{
        public static final String STUDENTID = "studentID";
        public static final String DATEREAD = "dateRead";
        public static final String STUDID = "StudID";

        public static final String NOTIFICATIONREADTRACKING_TABLE = "notificationReadTracking";
    }

    public class ReadNotReadNotifications implements BaseColumns
    {
        public static final String MSGID = "msgID";
        public static final String STUDID = "StudID";

        public static final String READNOTREADNOTIFICATIONS_TABLE = "readNotReadNotifications";

    }

    public class SyncMyAccountTB implements BaseColumns{
        //COLUMN NAMES
        public static final String BALANCE = "balanace";
        public static final String BILLING_BALANCE = "billing_balance";
        public static final String PORTAL_ACCOUNT = "portal_account";
        public static final String PORTAL_PAYBILL = "portal_paybill";
        public static final String ORGANIZATION_ID = "organization";
        public static final String STUDID = "StudID";

        //TABLE NAME
        public static final String SYNC_MYACCOUNTTB = "Sync_myAccountTB";
    }


    public class NewCurriculumExamFormat implements BaseColumns{
        public static final String STUDENTFULLNAME = "studentFullName";
        public static final String YEAID = "yearID";
        public static final String TERM_NAME = "termName";
        public static final String EXAMTYPEDESC = "examTypeDesc";
        public static final String COURSE_NAME = "courseName";
        public static final String LEVEL_NAME = "levelName";
        public static final String ACTIVITY = "activity";
        public static final String LEARNINGAREA  = "learningArea";
        public static final String REMARKS = "remarks";
        public static final String SCORE_DESCRIPTION = "scoreDescription";
        public static final String CLASS_TEACHER_COMMENT = "classTeacherComment";
        public static final String HEAD_TEACHER_COMMENT = "headTeacherComment";
        public static final String STUDID = "StudID";


        public static final String NEWCURRICULUMEXAMFORMAT_TABLE  = "newCurriculumExamFormat";
    }

}
