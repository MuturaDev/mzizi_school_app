package ultratude.com.mzizi.uiactivities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.Utils.UtilityFunctions;
import ultratude.com.mzizi.filepicker.config.Configurations;
import ultratude.com.mzizi.filepicker.model.MediaFile;
import ultratude.com.mzizi.filepicker.test_activity.FilePickerActivity;
import ultratude.com.mzizi.filepicker.utils.FileUtils;
import ultratude.com.mzizi.modelclasses.Student;
import ultratude.com.mzizi.modelclasses.response.YoutubeVideoGalleryResponse;
import ultratude.com.mzizi.report_analytics.ReportAnalytics;
import ultratude.com.mzizi.retrofitokhttp.APIClient;
import ultratude.com.mzizi.retrofitokhttp.APIInterface;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.YoutubeVideoGalleryResponseDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.GlobalSettings;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalDetailedToDoListResponse;
import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;
import ultratude.com.mzizi.uploadfiles.FileUploadPayload;
import ultratude.com.mzizi.webservice.mziziapiconstantsurl.MziziURLConstants;

import static android.app.Activity.RESULT_OK;

public class AssignmentFileUploadFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public ParentMziziDatabase db;

    private String teacherMemoID = "";


    public AssignmentFileUploadFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ReportAnalytics.reportScreenChangeAnalytic(getContext(), "AssignmentFileUpload Fragment");
        return inflater.inflate(R.layout.assignment_file_upload_layout, container, false);
    }

    private TextView name_text,body_text,download_text_btn,assingment_dueDate, upload_text_btn;
    private String url;
    private LinearLayout next_btn_layout;

    private EditText add_comment;



    private final static int FILE_REQUEST_CODE = 1;

    private ArrayList<MediaFile> mediaFiles = new ArrayList<>();


   private TextView submit_status_text;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        getActivity().setTitle("Upload assignment");

        if(getActivity() != null)
            Paper.init(getActivity());

        name_text = getView().findViewById(R.id.name_text);
        body_text = getView().findViewById(R.id.body_text);

        add_comment = view.findViewById(R.id.add_comment);


        submit_status_text = view.findViewById(R.id.submit_status_text);

        assingment_dueDate = getView().findViewById(R.id.assingment_dueDate);
        download_text_btn = getView().findViewById(R.id.download_text_btn);
        download_text_btn.setVisibility(View.VISIBLE);
        download_text_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FIXME: THIS BASE URL SHOULD NOT BE USED.
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(MziziURLConstants.BASE_URL + "/" + url));
//                getActivity().startActivity(browserIntent);

                AsyncTask asyncTask = new AsyncTask() {

                    @Override
                    protected void onPostExecute(Object o) {
                        Map<String, GlobalSettings> map = (Map<String,GlobalSettings>)o;
                        String BASEURL =  map.get(Constants.PORTAL_URL_ENABLED).getGlobalSettingsValue();

//                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(BASEURL + "/" + url));
//                            context.startActivity(browserIntent);

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse("http://docs.google.com/viewer?url=" + BASEURL + "/" + url), "text/html");
                        getActivity().startActivity(intent);

                        super.onPostExecute(o);
                    }

                    @Override
                    protected Object doInBackground(Object[] objects) {
                        ParentMziziDatabase db = ParentMziziDatabase.getInstance(getActivity());
                    Map<String, Object> map = new HashMap<>();


                        String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                        if(studentid == null){
                            studentid  = "0";
                        }

                        if(db.getGlobalSettingsDAO().getGlobalSettings(Constants.PORTAL_URL_ENABLED, Integer.valueOf(studentid)).size() > 0){
                            map.put(Constants.PORTAL_URL_ENABLED,db.getGlobalSettingsDAO().getGlobalSettings(Constants.PORTAL_URL_ENABLED,Integer.valueOf(studentid)).get(0));
                        }

                        return map;
                    }
            };
                asyncTask.execute();

            }
        });


        if(getArguments() !=  null){
            PortalDetailedToDoListResponse toDoListResponse = (PortalDetailedToDoListResponse) getArguments().getSerializable("message");
            if(toDoListResponse != null){
                teacherMemoID = toDoListResponse.getTeacherMemoID();
                name_text.setText(toDoListResponse.getName());
                body_text.setText(toDoListResponse.getBody());
                url = toDoListResponse.getDocPath();
                assingment_dueDate.setText(toDoListResponse.getToDoAge());

                if(toDoListResponse.getSubmitStatus() != null) {
                    if (!toDoListResponse.getSubmitStatus().isEmpty()) {
                        submit_status_text.setText(toDoListResponse.getSubmitStatus());
                        submit_status_text.setVisibility(View.VISIBLE);

                        if(toDoListResponse.getSubmitStatus().contains("NOT SUBMITTED")){
                            submit_status_text.setVisibility(View.GONE);
                        }
                    }
                    else {
                        submit_status_text.setVisibility(View.GONE);
                    }
                }else {
                    submit_status_text.setVisibility(View.GONE);
                }
            }
        }

        RelativeLayout file_upload = getView().findViewById(R.id.file_upload);
        file_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] singleChoiceItems = getActivity().getResources().getStringArray(R.array.dialog_single_choice_array);
                int itemSelected = -1;
                new AlertDialog.Builder(getActivity())
                        .setTitle("Select file type for upload")
                        .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {



                                    if(selectedIndex == 0){
                                        mediaFiles.clear();
                                        Intent intent = new Intent(getActivity(), FilePickerActivity.class);
                                        intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                                                .setCheckPermission(true)
                                                .setSelectedMediaFiles(mediaFiles)
                                                .enableImageCapture(true)
                                                .setShowVideos(false)
                                                .setSkipZeroSizeFiles(true)
                                                .setMaxSelection(1)
                                                .build());
                                        startActivityForResult(intent, FILE_REQUEST_CODE);


                                        dialogInterface.dismiss();

                                    }else if(selectedIndex == 1){
                                        mediaFiles.clear();
                                        Intent intent = new Intent(getActivity(), FilePickerActivity.class);
                                        intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                                                .setCheckPermission(true)
                                                .setSelectedMediaFiles(mediaFiles)
                                                .enableVideoCapture(true)
                                                .setShowImages(false)
                                                .setMaxSelection(1)
                                                .setIgnorePaths(".*WhatsApp.*")
                                                .build());
                                        startActivityForResult(intent, FILE_REQUEST_CODE);


                                        dialogInterface.dismiss();

                                    }else if(selectedIndex == 2){
                                        mediaFiles.clear();
                                        Intent intent = new Intent(getActivity(), FilePickerActivity.class);
                            //                MediaFile file = null;
                            //                for (int i = 0; i < mediaFiles.size(); i++) {
                            //                    if (mediaFiles.get(i).getMediaType() == MediaFile.TYPE_AUDIO) {
                            //                        file = mediaFiles.get(i);
                            //                    }
                            //                }

                                        //Log.d(FilesActivity.this.getPackageName().toUpperCase(), "Files is null? " + String.valueOf(file ==  null));
                                        intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                                                .setCheckPermission(true)
                                                .setSelectedMediaFiles(mediaFiles)
                                                .setShowImages(false)
                                                .setShowVideos(false)
                                                .setShowAudios(true)
                                                .setShowFiles(true)
                                                //.setSingleChoiceMode(true)
                                                //.setSelectedMediaFile(file)
                                                .setMaxSelection(1)
                                                .setRootPath(Environment.getExternalStorageDirectory().getPath() + "/")
                                                .build());
                                        startActivityForResult(intent, FILE_REQUEST_CODE);


                                        dialogInterface.dismiss();

                                    }else if(selectedIndex == 3){
                                        dialogInterface.cancel();
                                        requestMultiPermission();



                                    }

                            }
                        })
                        .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }
        });


        next_btn_layout = view.findViewById(R.id.next_btn_layout);
        next_btn_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                if(mediaFiles.size() <= 0){
                   // Toast.makeText(getActivity(), "Ooops, no file to upload", Toast.LENGTH_SHORT).show();
                    FancyToast.makeText(getActivity(), "Ooops, no file to upload", FancyToast.LENGTH_SHORT, FancyToast.INFO, R.mipmap.mzizi_app_icon,false ).show();
                    return;
                }

                if(UtilityFunctions.checkFileSizeNotToExceed10MB(new File(mediaFiles.get(0).getPath()))){
                    FancyToast.makeText(getActivity(), "Maximum allowed file size is 10MB", FancyToast.LENGTH_SHORT, FancyToast.INFO, R.mipmap.mzizi_app_icon,false ).show();
                    return;
                }

                if(!new UtilityFunctions(getActivity()).internetConnection()){
                    //FancyToast.makeText(getActivity(), "Oops Check your internet connection!", FancyToast.LENGTH_LONG, FancyToast.ERROR, R.mipmap.mzizi_app_icon,false).show();
                    return;
                }



                final ProgressDialog dialog = new ProgressDialog(getActivity());
                dialog.setTitle("Uploading file");
                dialog.setMessage("Please wait...");
                dialog.setCancelable(false);


                Student student = ((MainActivity)getActivity()).student;

                if(mediaFiles.size() > 0) {

                    MediaFile mediaFile = mediaFiles.get(0);

                    if(mediaFile.getName().split("\\.")[1].equals("bat") ||
                        mediaFile.getName().split("\\.")[1].equals("exe") ||
                            mediaFile.getName().split("\\.")[1].equals("sql") ||
                            mediaFile.getName().split("\\.")[1].equals("")


                    ){
                        FancyToast.makeText(getActivity(), "Unsupported file extension, please upload another file.", FancyToast.LENGTH_SHORT, FancyToast.INFO, R.mipmap.mzizi_app_icon,false ).show();
                        return;
                    }


                    //Object[] fileData =   UtilityFunctions.convertVideoFileToBinary(mediaFile.getPath(), getActivity());

                    FileUploadPayload payload = new FileUploadPayload(
                            mediaFile.getName().split("\\.")[0],
                            student.getStudentID(),
                            teacherMemoID,
                            mediaFile.getName().split("\\.")[1],
                            add_comment.getText().toString().trim()
                            );


                    APIInterface api = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

                    Log.d(getContext().getPackageName().toUpperCase(), "Request Payload: " + payload.toString());

                    dialog.show();

                    File file = new File(mediaFile.getPath());

                    String filename = file.getName();



                    Call<ResponseBody> uploadFileCall = api.uploadFile(
                            //RequestBody.create(MediaType.parse("text/plain"), "title"),
                            MultipartBody.Part.createFormData(
                                    "file",
                                   filename,
                                    RequestBody.create(MediaType.parse("multipart/form-data"), file))
                            ,payload

                    );
                    uploadFileCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            dialog.cancel();
                            if (response.code() == 200) {
                                ResponseBody status = response.body();
                                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                                alert.setTitle("Success");
                                alert.setMessage("Submitted successfully");
                                alert.setCancelable(false);
                                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                        mediaFiles.clear();
                                        add_comment.setText("");
                                        filePath.setText("File Name");
                                    }
                                });
                                alert.show();


                                Log.d(getContext().getPackageName().toUpperCase(), "Fle Upload Status: " + status.toString());
                            }else{
                                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                                alert.setTitle("Failure");
                                alert.setMessage("Error while submitting, please try again");
                                alert.setCancelable(false);
                                alert.setPositiveButton("TRY AGAIN", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                alert.show();
                                Log.d(getContext().getPackageName().toUpperCase(), "Exception sending file: " +  response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            //java.net.UnknownServiceException: CLEARTEXT communication to a4a506a3.ngrok.io not permitted by network security policy
                            //java.io.FileNotFoundException: /storage/emulated/0/WhatsApp/Media/WhatsApp Profile Photos/James Little Friends 20200322_222025.jpg (Permission denied)

                            Log.d(getContext().getPackageName().toUpperCase(), "Exception sending file: " +  t.toString());
                            // TODO
                            FancyToast.makeText(getActivity(), "Ooops, check your internet connection", FancyToast.LENGTH_SHORT, FancyToast.INFO, R.mipmap.mzizi_app_icon,false ).show();
                            dialog.cancel();

                        }
                    });

//                    RequestBody photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//                    MultipartBody.Part photo = MultipartBody.Part.createFormData("photo", file.getName(), photoContent);
//                    apiInterface.upload(photo, new Callback<String>() {
//                        @Override
//                        public void onResponse(Call<String> call, Response<String> response) {
//                            dialog.cancel();
//                            if (response.code() == 200) {
//                                Object status = response.body();
//                                Log.d(getContext().getPackageName().toUpperCase(), "Fle Upload Status: " + status);
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<String> call, Throwable throwable) {
//
//                        }
//                    });

//                    Call<Object> userCall = apiInterface.uploadFileToAspWebService(payload);
//                    userCall.enqueue(new Callback<Object>() {
//                        @Override
//                        public void onResponse(Call<Object> call, final Response<Object> response) {
//
//                            dialog.cancel();
//                            if (response.code() == 200) {
//                                Object status = response.body();
//                                Log.d(getContext().getPackageName().toUpperCase(), "Fle Upload Status: " + status);
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<Object> call, Throwable t) {
//                            dialog.cancel();
//                        }
//                    });


                }

            }
        });


        super.onViewCreated(view, savedInstanceState);
    }

    private ImageView fileThumbnail;
    private TextView filePath, fileSize, fileMime, fileBucketName;
    private ConstraintLayout constraintLayout;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == FILE_REQUEST_CODE
                && resultCode == RESULT_OK
                && data != null) {
            mediaFiles.clear();
            mediaFiles.addAll(data.<MediaFile>getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES));


            View view = getView();

            fileThumbnail = view.findViewById(R.id.file_thumbnail);
//            constraintLayout = view.findViewById(R.id.constraint_results_layout);
            filePath = view.findViewById(R.id.file_path);
//            fileSize = view.findViewById(R.id.file_size);
//            fileMime = view.findViewById(R.id.file_mime);
//            fileBucketName = view.findViewById(R.id.file_bucketname);


            if(mediaFiles.size() > 0){

                MediaFile mediaFile = mediaFiles.get(0);

                String path = mediaFile.getPath();



                Context context = getActivity();

               //filePath.setText(context.getString(R.string.uri, mediaFile.getUri()));
                filePath.setText(mediaFile.getName());
               // Log.d(context.getPackageName().toUpperCase(), "File: " + mediaFile.getName());
//                fileMime.setText(context.getString(R.string.mime, mediaFile.getMimeType()));
//                fileSize.setText(context.getString(R.string.size, mediaFile.getSize()));
//                fileBucketName.setText(context.getString(R.string.bucketname, mediaFile.getBucketName()));
                if (mediaFile.getMediaType() == MediaFile.TYPE_IMAGE
                        || mediaFile.getMediaType() == MediaFile.TYPE_VIDEO) {
                    Glide.with(context)
                            .load(mediaFile.getUri())
                            .fitCenter()
                            .into(fileThumbnail);
                    fileThumbnail.setVisibility(View.VISIBLE);
                } else if (mediaFile.getMediaType() == MediaFile.TYPE_AUDIO) {
                    Glide.with(context)
                            .load(mediaFile.getThumbnail())
                            .placeholder(R.drawable.ic_audio)
                            .into(fileThumbnail);
                    fileThumbnail.setVisibility(View.VISIBLE);
                } else {
                      fileThumbnail.setImageResource(R.drawable.ic_file);
                      fileThumbnail.setVisibility(View.VISIBLE);
                }
            }



        }


        super.onActivityResult(requestCode, resultCode, data);

    }

    public static DiaryFragment newInstance(String param1, String param2, FragmentManager fragmentManager) {
        DiaryFragment.fragmentManager = fragmentManager;

        DiaryFragment fragment = new DiaryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    private void requestMultiPermission(){
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if(multiplePermissionsReport.areAllPermissionsGranted()){
                            mediaFiles.clear();
                            Intent intent = new Intent(getActivity(), FilePickerActivity.class);
                            intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                                    .setCheckPermission(true)
                                    .setSelectedMediaFiles(mediaFiles)
                                    .setShowFiles(true)
                                    .setShowImages(false)
                                    .setShowVideos(false)
                                    .setMaxSelection(1)
                                    .setRootPath(Environment.getExternalStorageDirectory().getPath() + "/Download")
                                    .build());
                            startActivityForResult(intent, FILE_REQUEST_CODE);

                        }

                        if(multiplePermissionsReport.isAnyPermissionPermanentlyDenied()){
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                })
                .withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError dexterError) {

                    }
                })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Need permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings");
        builder.setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
    }

    private void openSettings(){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


}
