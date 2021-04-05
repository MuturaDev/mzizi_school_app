package ultratude.com.mzizi.helperactivityclasses;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.media.MicrophoneInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.Person;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.adriangl.overlayhelper.OverlayHelper;
import com.andrognito.patternlockview.utils.ResourceUtils;
import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.codewaves.youtubethumbnailview.ThumbnailLoadingListener;
import com.codewaves.youtubethumbnailview.ThumbnailView;
import com.flipkart.youtubeview.activity.YouTubeActivity;
import com.flipkart.youtubeview.models.YouTubePlayerType;
import com.sun.mail.imap.Utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;
import ultratude.com.mzizi.R;
import ultratude.com.mzizi.Utils.UtilityFunctions;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.GlobalSettings;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalDetailedToDoListResponse;
import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;
import ultratude.com.mzizi.uiactivities.AssignmentFileUploadFragment;
import ultratude.com.mzizi.uiactivities.DiaryFragment;
import ultratude.com.mzizi.uiactivities.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;


public class PortalDetailedToDoListResponseAdapter extends RecyclerView.Adapter<PortalDetailedToDoListResponseAdapter.ViewHolder> {

    private List<PortalDetailedToDoListResponse> portalDetailedToDoListResponseList;
    private Context context;
    private DiaryFragment fragment;



    public PortalDetailedToDoListResponseAdapter(List<PortalDetailedToDoListResponse> portalDetailedToDoListResponseList, Context context, DiaryFragment fragment){
        this.portalDetailedToDoListResponseList = portalDetailedToDoListResponseList;
        this.context = context;
        this.fragment = fragment;
        Paper.init(context);
    }


    @Override
    public PortalDetailedToDoListResponseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.diary_item_layout, parent, false);
        return new PortalDetailedToDoListResponseAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final PortalDetailedToDoListResponseAdapter.ViewHolder holder, final int position) {
        holder.bind(portalDetailedToDoListResponseList.get(position));
    }


    @Override
    public int getItemCount() {
        return portalDetailedToDoListResponseList.size();
    }

    //this is a class
    //this will take a hold of the views on the item view.
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name_text,download_text_btn,assingment_dueDate, upload_text_btn;
        //private ReadMoreTextView body_text;
        private TextView body_text,list1, list2;
        private LinearLayout  tobehighlighted;
        private ThumbnailView thumbnailView;
        private TextView submit_status_text;
        private TextView tapDonwloadInfo;

        String url;



        public ViewHolder(View itemView){
            super(itemView);
            upload_text_btn = itemView.findViewById(R.id.upload_text_btn);
            upload_text_btn.setVisibility(View.VISIBLE);

            thumbnailView = itemView.findViewById(R.id.thumbnail);
            name_text = itemView.findViewById(R.id.name_text);
            body_text = itemView.findViewById(R.id.body_text);

            list1 = itemView.findViewById(R.id.list1);
            list2 = itemView.findViewById(R.id.list2);

            tapDonwloadInfo = itemView.findViewById(R.id.tapDonwloadInfo);


            submit_status_text = itemView.findViewById(R.id.submit_status_text);
           // body_text.setTrimLines(5);
            assingment_dueDate = itemView.findViewById(R.id.assingment_dueDate);
            tobehighlighted = itemView.findViewById(R.id.tobehighlighted);
            download_text_btn = itemView.findViewById(R.id.download_text_btn);
            download_text_btn.setVisibility(View.VISIBLE);
            download_text_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AsyncTask asyncTask = new AsyncTask() {

                        @Override
                        protected void onPostExecute(Object o) {
                            Map<String, GlobalSettings> map = (Map<String,GlobalSettings>)o;
                            final String BASEURL =  map.get(Constants.PORTAL_URL_ENABLED).getGlobalSettingsValue();

                            String[] singleChoiceItems = context.getResources().getStringArray(R.array.download_document_choice_array);
                            int itemSelected = -1;
                            new AlertDialog.Builder(context)
                                    .setTitle("Select action")
                                    .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            url = portalDetailedToDoListResponseList.get(getAdapterPosition()).getDocPath();

                                            if(i == 0){
                                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                                intent.setDataAndType(Uri.parse("http://docs.google.com/viewer?url=" + BASEURL + "/" + url), "text/html");
                                                context.startActivity(intent);
                                                dialogInterface.dismiss();
                                            }else if(i == 1){
                                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(BASEURL + "/" + url));
                                                 context.startActivity(browserIntent);
                                                 dialogInterface.dismiss();
                                            }
                                        }
                                    })
                                    .show();
                            super.onPostExecute(o);
                        }

                        @Override
                        protected Object doInBackground(Object[] objects) {
                            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
                            Map<String, Object> map = new HashMap<>();

                            String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                            if(studentid == null){
                                studentid  = "0";
                            }

                            if(db.getGlobalSettingsDAO().getGlobalSettings(Constants.PORTAL_URL_ENABLED, Integer.valueOf(studentid)).size() > 0){
                                map.put(Constants.PORTAL_URL_ENABLED,db.getGlobalSettingsDAO().getGlobalSettings(Constants.PORTAL_URL_ENABLED, Integer.valueOf(studentid)).get(0));
                            }

                            return map;
                        }
                    };
                    asyncTask.execute();


                }
            });
        }







        public void bind(final PortalDetailedToDoListResponse toDoListResponse){
           name_text.setText(toDoListResponse.getName().replace("\\n",""));


            if(toDoListResponse.getIsDocPathAvailable().equals("1")){
                download_text_btn.setVisibility(View.VISIBLE);
                tapDonwloadInfo.setVisibility(View.VISIBLE);
            }else{
                download_text_btn.setVisibility(View.GONE);
                tapDonwloadInfo.setVisibility(View.GONE);
            }

           if(toDoListResponse.getBody().contains("Meeting ID") &&
                   toDoListResponse.getBody().contains("Password") &&
                   toDoListResponse.getBody().contains("http")){
             final  List<String> list = UtilityFunctions.getBodyWithUrl(toDoListResponse.getBody(), UtilityFunctions.removeUrl(toDoListResponse.getBody()));

               for(int i=0; i< list.size(); i++){//012
                   if(i == 0){
//                       if(list.get(i).endsWith("|"))
//                           body_text.setText(list.get(i).replace(
//                                   "Join Zoom Meeting",
//                                   "Please Tap the button below to Join Zoom Meeting").substring(0, (list.get(i)).length() - 1).replace("|", "\n"));
//                       else
                       if(list.get(i).contains("Join Zoom Meeting"))
                           body_text.setText(list.get(i).replace(
                                   "Join Zoom Meeting",
                                   "Please Tap the button below to Join Zoom Meeting").replace("|", "\n"));
                       if(list.get(i).contains("Join Zoom Lesson"))
                           body_text.setText(list.get(i).replace(
                                   "Join Zoom Lesson",
                                   "Please Tap the button below to Join Zoom Lesson").replace("|", "\n"));

                   }else if(i == 1){
                       //list1.setText(list.get(i).replace("|", "\n"));
                       list1.setText("Join Meeting");
                       list1.setVisibility(View.VISIBLE);
                       list1.setTextColor(context.getResources().getColor(R.color.primary_blue));
                       list1.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {

                              // Map<String,String> map = UtilityFunctions.getPassMeetID(toDoListResponse.getBody());


                               //Paper.book().write("MeetingCredentials",UtilityFunctions.getPassMeetID(list.get(2)));

                               //showFloatingView();

                               Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(1)));
                               context.startActivity(browserIntent);
                           }
                       });

                   }else if(i == 2){

                       if (list.get(i).endsWith("|")) {
                           list2.setText(list.get(i).substring(0, list.get(i).length() - 1).replace("|", "\n"));
                           list2.setVisibility(View.VISIBLE);
                       }else if(list.get(i).startsWith("|")) {
                           list2.setText(list.get(i).replaceFirst("|", "\n"));
                           list2.setVisibility(View.VISIBLE);
                       }else {
                           list2.setText(list.get(i).replace("|", "\n"));
                           list2.setVisibility(View.VISIBLE);
                       }
                   }

               }

           }else {
               list1.setVisibility(View.GONE);
               list2.setVisibility(View.GONE);
               body_text.setText(toDoListResponse.getBody().replace("|", "\n"));
           }


           if(toDoListResponse.getBody().contains("Meeting ID") &&
                   toDoListResponse.getBody().contains("Passcode") &&
                   !toDoListResponse.getBody().contains("http") ){
               list1.setText("Join Meeting");
               list1.setVisibility(View.VISIBLE);
               list1.setTextColor(context.getResources().getColor(R.color.primary_blue));
               list1.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                      ViewHolder.this.onClick(toDoListResponse);
                   }
               });
           }



            //assingment_dueDate.setText("Due in " + toDoListResponse.getToDoAge() + " days(s)");
            if(toDoListResponse.getToDoAge() != null){
                if(toDoListResponse.getToDoAge().isEmpty())
                    assingment_dueDate.setVisibility(View.GONE);
                else
                    assingment_dueDate.setText(toDoListResponse.getToDoAge());

            }

            if(toDoListResponse.isTobeHighlited()){

                body_text.setTextColor(ResourceUtils.getColor(context, R.color.white));
                list1.setTextColor(ResourceUtils.getColor(context, R.color.white));
                list2.setTextColor(ResourceUtils.getColor(context, R.color.white));
                tobehighlighted.setBackgroundColor(context.getResources().getColor(R.color.vertical_stepper_form_background_color_circle));
            }



            upload_text_btn.setVisibility( toDoListResponse.getIsFeedbackRequired().equals("1") ? View.VISIBLE : View.GONE);
            upload_text_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    //FragTransaction.dislayFragment(AssignmentFileUploadFragment.class, name_text.getText().toString(), ((MainActivity) context).fragmentManager);
                    try {
                        Class fragmentClass = AssignmentFileUploadFragment.class;
                        Fragment fragment = (Fragment) fragmentClass.newInstance();
                        Bundle b = new Bundle();
                        b.putSerializable("message", toDoListResponse);
                        fragment.setArguments(b);
                        //insert the fragment by replacing any existing
                        FragmentTransaction fragmentTransaction = ((MainActivity) context).fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_transaction, fragment).commit();
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            });

            thumbnailView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, YouTubeActivity.class);
                    intent.putExtra("apiKey", Constants.API_KEY);
                    // intent.putExtra("videoId", "3AtDnEC4zak");
                    intent.putExtra("videoId",toDoListResponse.getVideoLink() );
                    intent.putExtra("playerType", YouTubePlayerType.STRICT_NATIVE);
                    context.startActivity(intent);
                }
            });

            if(toDoListResponse.getToDoAge() != null) {
                if (!toDoListResponse.getToDoAge().isEmpty()) {
                    assingment_dueDate.setText(toDoListResponse.getToDoAge());

                    assingment_dueDate.setVisibility(View.VISIBLE);
                    if(assingment_dueDate.getText().toString().contains("submitted"))
                        assingment_dueDate.setBackgroundColor(context.getResources().getColor(R.color.success_green));

                    if(toDoListResponse.getToDoAge().contains("Due in")){
                        assingment_dueDate.setVisibility(View.GONE);
                        assingment_dueDate.setBackgroundColor(context.getResources().getColor(R.color.info_teal));
                    }

                    if(assingment_dueDate.getText().toString().contains("today")){
                        assingment_dueDate.setBackgroundColor(context.getResources().getColor(R.color.warning_yellow));
                    }


                }
                else {
                    assingment_dueDate.setVisibility(View.GONE);
                }
            }else {
                assingment_dueDate.setVisibility(View.GONE);
            }



            if(toDoListResponse.getIsVideoLinkAvailable().equalsIgnoreCase("1")){
                String videourl = "https://www.youtube.com/watch?v=" + toDoListResponse.getVideoLink();

                final String TAG = context.getPackageName().toUpperCase();
                //Log.d(TAG, videourl);
                thumbnailView.loadThumbnail(videourl, new ThumbnailLoadingListener() {
                    @Override
                    public void onLoadingStarted(@NonNull String url, @NonNull View view) {
                       // Log.i(TAG, "Thumbnail load started.");
                    }

                    @Override
                    public void onLoadingComplete(@NonNull String url, @NonNull View view) {
                      //  Log.i(TAG, "Thumbnail load finished.");
                    }

                    @Override
                    public void onLoadingCanceled(@NonNull String url, @NonNull View view) {
                      //  Log.i(TAG, "Thumbnail load canceled.");
                    }

                    @Override
                    public void onLoadingFailed(@NonNull String url, @NonNull View view, Throwable error) {
                       // Log.e(TAG, "Thumbnail load failed. " + error.getMessage());
                    }
                });
            }else{
                thumbnailView.setVisibility(View.GONE);
            }

            if(toDoListResponse.getName().contains("ZOOM LINK")){

                String[] bodySplit = new String[1];
                if(toDoListResponse.getBody().contains("Join Zoom Meeting"))
                    bodySplit = toDoListResponse.getBody().split("Join Zoom Meeting");
                if(toDoListResponse.getBody().contains("Join Zoom Lesson"))
                    bodySplit = toDoListResponse.getBody().split("Join Zoom Lesson");

                if(bodySplit.length > 1){
                    body_text.setText(bodySplit[0].replace("|", "\n"));

                    if(bodySplit[1].contains("One tap mobile")){
                        String[] splitText = bodySplit[1].split("One tap mobile");
                        if(splitText.length > 0){
                            list2.setVisibility(View.VISIBLE);
                            list2.setText(splitText[0].replace("|", "\n"));
                        }
                    }else{
                        list2.setText(bodySplit[1].replace("|", "\n"));
                        list2.setVisibility(View.VISIBLE);
                    }

                    for(String i : bodySplit){
                        Log.d(context.getPackageName().toUpperCase(),"Splite Body: " + i);
                    }


                    list1.setText("Join Meeting");
                    list1.setVisibility(View.VISIBLE);
                    list1.setTextColor(context.getResources().getColor(R.color.primary_blue));
                    list1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ViewHolder.this.onClick(toDoListResponse);
                        }
                    });

                }


            }


        }


        private void onClick(final PortalDetailedToDoListResponse toDoListResponse){
            //Map<String,String> map = UtilityFunctions.getPassMeetID(toDoListResponse.getBody());



            Paper.book().write("MeetingCredentials",UtilityFunctions.getPassMeetID(toDoListResponse.getBody()));

            if (android.os.Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(context)) {
                ((MainActivity)fragment.getActivity()).overlayHelper.requestDrawOverlaysPermission(
                        ((MainActivity)context),
                        "Request draw overlays permission?",
                        "You have to enable the draw overlays permission for this Meeting's Meeting-ID and Passcode to be displayed to you for entry",
                        "Enable",
                        "Cancel");
            }else{
                ((MainActivity)fragment.getActivity()).showFloatingView();
                //moved to MainActivities onActivityResult
                //launch zoom client from your app
                PackageManager pm = context.getPackageManager();
                Intent intent = pm.getLaunchIntentForPackage("us.zoom.videomeetings");
                if(intent != null){
                    context.startActivity(intent);
                }else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("zoomus://"));
                    if(browserIntent.resolveActivity(context.getPackageManager()) != null)
                        context.startActivity(browserIntent);
                }
            }





        }
    }


}
