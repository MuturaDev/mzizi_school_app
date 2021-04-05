//package ultratude.com.mzizi.firebasenotificationhandler;
//
//import android.content.Context;
//import android.os.AsyncTask;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import ultratude.com.mzizi.firebasejobdispatch.RequestFor;
//import ultratude.com.mzizi.modelclasses.Student;
//import ultratude.com.mzizi.notificationpg.NotificationModel.Notification;
//import ultratude.com.mzizi.notificationpg.NotificationModel.NotificationDAO;
//import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;
//import ultratude.com.mzizi.webservice.APIRequest;
//
//public class SendRequestFromFirebaseFCM extends AsyncTask<Void, Void, List<String>> {
//
//
//    Context context;
//
//    public SendRequestFromFirebaseFCM(Context context) {
//
//        this.context = context;
//    }
//
//
//    @Override
//    protected void onPreExecute() {
//
//
//        super.onPreExecute();
//    }
//
//    @Override
//    protected void onPostExecute(List<String> list) {
//
//        if (list.size() == 5) {
//            String studentID = list.get(0);
//            String appcode = list.get(1);
//            String organization = list.get(2);
//            String notificationLastID = list.get(3);
//
//
//            Student student = new Student(studentID, appcode);
//            student.setOrganizationID(organization);
//            RequestFor.sendRequest(student, context, notificationLastID, APIRequest.ALL);
//
//
//        }
//
//        super.onPostExecute(list);
//    }
//
//    @Override
//    protected List<String> doInBackground(Void... voids) {
//
//        ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
//
//        List<AuthenticateUserResponse> authenticateUserResponse = db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();
//
//
//        List<Notification> notificationList = new NotificationDAO(context).getNotificationsList();
//
//        //  List<AuthenticateUserResponse> userResponse = db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();
//
//        List<String> list = new ArrayList<>();
//        if (authenticateUserResponse.size() > 0) {
//            list.add(authenticateUserResponse.get(0).getUserID());//0 = studentid
//            list.add(authenticateUserResponse.get(0).getAppcode());//1 = appcode
//            list.add(authenticateUserResponse.get(0).getOrganizationID());//2=organizationID
//        }
//
//
//        //check this array out of bounce
//        if (notificationList.isEmpty()) {
//
//            String.valueOf(new Notification().getMsgID());
//            list.add(String.valueOf(new Notification().getMsgID()));//3notificationid
//            return list;
//        } else {
//
//            list.add(String.valueOf(notificationList.get(notificationList.size() - 1).getMsgID()));
//            return list;
//        }
//    }
//}