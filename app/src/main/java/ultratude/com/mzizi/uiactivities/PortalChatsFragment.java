package ultratude.com.mzizi.uiactivities;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.mzizi.R;

import ultratude.com.mzizi.modelclasses.ParentChatRequest;
import ultratude.com.mzizi.modelclasses.Student;
import ultratude.com.mzizi.report_analytics.ReportAnalytics;
import ultratude.com.mzizi.retrofitokhttp.APIClient;
import ultratude.com.mzizi.retrofitokhttp.APIInterface;
import ultratude.com.mzizi.retrofitokhttp.Pojo.Test;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.ParentChatDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.ParentChat;
import ultratude.com.mzizi.portalchatshelperclasses.Message;
import ultratude.com.mzizi.portalchatshelperclasses.User;


/**
 * Created by Admin on 6/2/2018.
 */

public class PortalChatsFragment extends Fragment implements View.OnClickListener{

    private  List<Message> messagestore = new ArrayList<>();//This is the message store


    public RecyclerView mMessageRecycler;
    private PortalChatAdapter mMessageAdapter;

    //dont forget to use something to show the message has been sent
    EditText textMessage;
    TextView sendMessage;
    public static String messageNotSent = "";
    private ToggleButton enquiryID;
    private static int count = 0;
    private RelativeLayout activity_message_list_ID;

    //test if it was clicked
   // private boolean menuItemClick = true;
    //id of the item
    private int  menuItemID = 1;

    private static boolean clicked = true;



    @Override
    public void onStop() {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
       // UtilityFunctions.closeKeyboard(PortalChatsFragment.this.getContext());
        super.onStop();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ReportAnalytics.reportScreenChangeAnalytic(getContext(), "PortalChats Fragment");
        View view = inflater.inflate(R.layout.activity_message_list, container, false);
        return view;
    }



    int itemSelected = -1;
    String[] enquiryTypes;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Paper.init(getActivity());

        getActivity().setTitle("Chat Service");
        textMessage =  getActivity().findViewById(R.id.edittext_chatbox);
        if(Paper.book().contains("LastWrittenMessage")){
            if(!Paper.book().read("LastWrittenMessage").toString().isEmpty()) {
                textMessage.setText(Paper.book().read("LastWrittenMessage").toString());
                textMessage.requestFocus();
               // UtilityFunctions.showKeyboard(PortalChatsFragment.this.getContext());
            }

        }
        sendMessage =  getActivity().findViewById(R.id.button_chatbox_send);
        sendMessage.setOnClickListener(this);
        mMessageRecycler = getActivity().findViewById(R.id.reclerview_massage_list);
        activity_message_list_ID = getActivity().findViewById(R.id.activity_message_list_ID);
        enquiryID = getActivity().findViewById(R.id.pop_up_department);


         enquiryTypes  = getActivity().getResources().getStringArray(R.array.enquiry_options_array);


//        final PopupMenu menu = new PopupMenu(getActivity().getBaseContext(), enquiryID);
//        menu.getMenu().add(Menu.NONE, 0, 0, "Select enquiry type");
//        menu.getMenu().add(Menu.NONE, 1, 1, "General Enquiry");
//        menu.getMenu().add(Menu.NONE, 2, 2, "Admissions");
//        menu.getMenu().add(Menu.NONE, 3, 3, "Academics");
//        menu.getMenu().add(Menu.NONE, 4, 4, "Finance");
//        menu.getMenu().add(Menu.NONE, 5, 5, "Transport");
//        menu.getMenu().add(Menu.NONE, 6, 6, "Sports");
//        menu.getMenu().add(Menu.NONE, 7,7, "Library");
//
//        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//
//                int i = item.getItemId();
//
//
//
//                if (i == 0) {
//                    //handle share
//                    menu.dismiss();
//                    enquiryID.setChecked(false);
//                    menuItemClick = true;
//
//                } else if (i >0) {
//                    //handle comment
//                    menu.dismiss();
//                    enquiryID.setChecked(false);
//                    menuItemClick = false;
//                    menuItemID = i;
//                }
//
//                return menuItemClick;
//            }
//
//        });


        enquiryID.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {


              AlertDialog.Builder alert =  new AlertDialog.Builder(getActivity());
               alert.setTitle("Select enquiry type");
                alert.setSingleChoiceItems(enquiryTypes, itemSelected, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                enquiryID.setChecked(false);
                               // menuItemClick = false;
                                    menuItemID = i+1;



                            }
                        });
                alert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                            }
                        });



                if(isChecked){
//                    menuItemClick = true;
//                    menu.show();

                    alert.show();

                }

            }
        });


        textMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(messagestore.size()>0){
                    mMessageRecycler.scrollToPosition(messagestore.size()- 1);
                }

            }
        });


        //ACTUAL DATA SHOULD COME FROM TEH DATABASE
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object[] params) {
                ParentMziziDatabase db = ParentMziziDatabase.getInstance(getActivity().getApplicationContext());
                String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                if(studentid == null){
                    studentid  = "0";
                }
                List<ParentChat> parentChatList =  db.getParentChatDAO().getParentChat(Integer.valueOf(studentid));
                return parentChatList;
            }

            @Override
            protected void onPostExecute(Object o) {


                List<ParentChat> parentChatList = (List<ParentChat>)o;

                if(parentChatList.size() > 0){
                    //This is just hard coded
                    //create initial data list
//                    User sender = new User(true);
//                    Message sentmessage1 = new Message("You can send a text, just for trial!",sender, new Date(System.currentTimeMillis()));
//                    Message sentmessage2 = new Message("Tell me what to change, or what to improve, i will appreciate it very much",sender, new Date(System.currentTimeMillis()));
//                    messagestore.add(sentmessage1);
//                    messagestore.add(sentmessage2);


                    for(ParentChat parentChat : parentChatList){
                        User sender = new User(true);//reciepient
                        if(parentChat.getActor().contains("You")){
                            sender = new User(false);//sender
                        }

                        Message message = new Message(parentChat.getMsg(),sender,parentChat.getDatePosted(),false,false,true);
                        messagestore.add(message);
                    }


                    //get recyclerview object

                    //set recyclerview layout manager
                    mMessageRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
                    //create the data adapter with above data list
                    mMessageAdapter = new PortalChatAdapter(getActivity(), messagestore, PortalChatsFragment.this);
                    //set data adapter to recyclerview
                    mMessageRecycler.setAdapter(mMessageAdapter);
                    messageNotSent = "";

                    if(messagestore.size()>0){
                        mMessageRecycler.scrollToPosition(messagestore.size()- 1);
                    }

                }else{
                    //get recyclerview object
                    mMessageRecycler = (RecyclerView) getActivity().findViewById(R.id.reclerview_massage_list);
                    //set recyclerview layout manager
                    mMessageRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
                    //create the data adapter with above data list
                    mMessageAdapter = new PortalChatAdapter(getActivity(), messagestore, PortalChatsFragment.this);
                    //set data adapter to recyclerview
                    mMessageRecycler.setAdapter(mMessageAdapter);
                }


                super.onPostExecute(o);
            }
        };
        asyncTask.execute();
        super.onViewCreated(view, savedInstanceState);
    }



    private  ParentChat parentChat2 = new ParentChat();

    @Override
    public void onClick(View v) {


        //Add a new sent message to the list
        if (v.getId() == R.id.button_chatbox_send) {

//            if(true){
//                Toast.makeText(getActivity(), "Selected: " + menuItemID + " value: " + enquiryTypes[menuItemID], Toast.LENGTH_SHORT).show();
//                return;
//            }
//            //if(menuItemClick){
//                menuItemID =  1;
////                FancyToast.makeText(this, "Please select the Department enquiring to.",FancyToast.LENGTH_SHORT, FancyToast.INFO,true).show();
////                return;
//            }


            String message = textMessage.getText().toString().trim();


            if (message.length() > 0) {

                parentChat2 = new ParentChat();
                parentChat2.setMsg(message.toString().trim());
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
                parentChat2.setDatePosted(sdf.format(calendar.getTime()));
                parentChat2.setActor("0");//sender

                User recipient = new User(false);//reciepient

                Message sentmessage = new Message(parentChat2.getMsg(), recipient, parentChat2.getDatePosted(), true, false, false);
                messagestore.add(sentmessage);//list of messages


                mMessageAdapter = new PortalChatAdapter(getActivity(), messagestore, PortalChatsFragment.this);
                //set data adapter to recyclerview
                mMessageRecycler.setAdapter(mMessageAdapter);
                mMessageRecycler.getAdapter().notifyDataSetChanged();
                //update the message list
                int newMessagePosition = messagestore.size() - 1;
                //notify recycler view insert one new data
                mMessageAdapter.notifyItemChanged(newMessagePosition, sentmessage);
                //Scroll recyclerview to the last message
                mMessageRecycler.scrollToPosition(newMessagePosition);
                textMessage.setText("");
                messageNotSent = "SendingMessage";


                final AsyncTask asyncTask = new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] params) {
                        return ParentMziziDatabase.getInstance(getActivity()).getAuthenticateUserResponseDao().getAuthenticateUserResponse();
                    }

                    @Override
                    protected void onPostExecute(Object o) {


                        List<AuthenticateUserResponse> authenticateUserResponseList = (List<AuthenticateUserResponse>) o;
                        if (authenticateUserResponseList.size() > 0) {
                            if (messagestore.size() > 0) {
                                mMessageRecycler.scrollToPosition(messagestore.size() - 1);
                            }

                            Student student = new Student(authenticateUserResponseList.get(0).getUserID(), authenticateUserResponseList.get(0).getAppcode());
                            if(internetConnection()) {
                                sendRequestForParentChat(student, getActivity(), parentChat2);
                            }else{
                                FancyToast.makeText(getActivity(), "Oops Check your internet connection!", FancyToast.LENGTH_LONG, FancyToast.ERROR, R.mipmap.mzizi_app_icon,false).show();

                                AsyncTask asyncTask = new AsyncTask() {
                                    @Override
                                    protected void onPreExecute() {
                                        super.onPreExecute();
                                    }

                                    @Override
                                    protected Object doInBackground(Object[] params) {
                                        ParentMziziDatabase db = ParentMziziDatabase.getInstance(getActivity().getApplicationContext());
                                        String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                                        if(studentid == null){
                                            studentid  = "0";
                                        }
                                        List<ParentChat> parentChatList =  db.getParentChatDAO().getParentChat(Integer.valueOf(studentid));
                                        return parentChatList;
                                    }

                                    @Override
                                    protected void onPostExecute(Object o) {


                                        List<ParentChat> parentChatList = (List<ParentChat>)o;

                                        if(parentChatList.size() > 0){
                                            //This is just hard coded
                                            //create initial data list
//                    User sender = new User(true);
//                    Message sentmessage1 = new Message("You can send a text, just for trial!",sender, new Date(System.currentTimeMillis()));
//                    Message sentmessage2 = new Message("Tell me what to change, or what to improve, i will appreciate it very much",sender, new Date(System.currentTimeMillis()));
//                    messagestore.add(sentmessage1);
//                    messagestore.add(sentmessage2);

                                            messagestore.clear();
                                            for(ParentChat parentChat : parentChatList){
                                                User sender = new User(true);//reciepient
                                                if(parentChat.getActor().contains("You")){
                                                    sender = new User(false);//sender
                                                }



                                                Message message = new Message(parentChat.getMsg(),sender,parentChat.getDatePosted(),false,false,true);
                                                messagestore.add(message);
                                            }

                                            messageNotSent = "MessageNotSent";

                                            User recipient = new User(false);//reciepient

                                            Message  sentmessage  = new Message(parentChat2.getMsg(),recipient,parentChat2.getDatePosted(),false,true,false);
                                            messagestore.add(sentmessage);//list of messages

                                            //get recyclerview object

//                            //set recyclerview layout manager
//                            mMessageRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
//                            //create the data adapter with above data list
                                            mMessageAdapter = new PortalChatAdapter(getActivity(), messagestore, PortalChatsFragment.this);
                                            //set data adapter to recyclerview
                                            mMessageRecycler.setAdapter(mMessageAdapter);


                                            if(messagestore.size()>0){
                                                mMessageRecycler.scrollToPosition(messagestore.size()- 1);
                                            }

                                        }else{
                                            //get recyclerview object
                                            mMessageAdapter = new PortalChatAdapter(getActivity(), messagestore, PortalChatsFragment.this);
                                            //set data adapter to recyclerview
                                            mMessageRecycler.setAdapter(mMessageAdapter);
                                        }


                                        super.onPostExecute(o);
                                    }
                                };
                                asyncTask.execute();
                            }

                        }


                        super.onPostExecute(o);
                    }
                };

                //test for good network
                Test test = new Test();
                test.setTest(true);

                APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

                Call<Boolean> userCall = apiInterface.testNetworkStability(test);
                userCall.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {


                        if (response.code() == 200) {

                            if (response.body()) {
                                asyncTask.execute();
                            }

                        } else {
                            FancyToast.makeText(getActivity(), "Oops Check your internet connection!", FancyToast.LENGTH_LONG, FancyToast.ERROR, R.mipmap.mzizi_app_icon,false).show();

                            AsyncTask asyncTask = new AsyncTask() {
                                @Override
                                protected void onPreExecute() {
                                    super.onPreExecute();
                                }

                                @Override
                                protected Object doInBackground(Object[] params) {
                                   ParentMziziDatabase db = ParentMziziDatabase.getInstance(getActivity().getApplicationContext());
                                    String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                                    if(studentid == null){
                                        studentid  = "0";
                                    }
                                    List<ParentChat> parentChatList = db.getParentChatDAO().getParentChat(Integer.valueOf(studentid));
                                    return parentChatList;
                                }

                                @Override
                                protected void onPostExecute(Object o) {


                                    List<ParentChat> parentChatList = (List<ParentChat>) o;

                                    if (parentChatList.size() > 0) {
                                        //This is just hard coded
                                        //create initial data list
//                    User sender = new User(true);
//                    Message sentmessage1 = new Message("You can send a text, just for trial!",sender, new Date(System.currentTimeMillis()));
//                    Message sentmessage2 = new Message("Tell me what to change, or what to improve, i will appreciate it very much",sender, new Date(System.currentTimeMillis()));
//                    messagestore.add(sentmessage1);
//                    messagestore.add(sentmessage2);

                                        messagestore.clear();
                                        for (ParentChat parentChat : parentChatList) {
                                            User sender = new User(true);//reciepient
                                            if (parentChat.getActor().contains("You")) {
                                                sender = new User(false);//sender
                                            }


                                            Message message = new Message(parentChat.getMsg(), sender, parentChat.getDatePosted(), false, false, true);
                                            messagestore.add(message);
                                        }

                                        messageNotSent = "MessageNotSent";

                                        User recipient = new User(false);//reciepient

                                        Message sentmessage = new Message(parentChat2.getMsg(), recipient, parentChat2.getDatePosted(), false, true, false);
                                        messagestore.add(sentmessage);//list of messages

                                        //get recyclerview object

//                            //set recyclerview layout manager
//                            mMessageRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
//                            //create the data adapter with above data list
                                        mMessageAdapter = new PortalChatAdapter(getActivity(), messagestore, PortalChatsFragment.this);
                                        //set data adapter to recyclerview
                                        mMessageRecycler.setAdapter(mMessageAdapter);


                                        if (messagestore.size() > 0) {
                                            mMessageRecycler.scrollToPosition(messagestore.size() - 1);
                                        }

                                    } else {
                                        //get recyclerview object
                                        mMessageAdapter = new PortalChatAdapter(getActivity(), messagestore, PortalChatsFragment.this);
                                        //set data adapter to recyclerview
                                        mMessageRecycler.setAdapter(mMessageAdapter);
                                    }


                                    super.onPostExecute(o);
                                }
                            };
                            asyncTask.execute();
                        }

                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        //call.cancel();
                        FancyToast.makeText(getActivity(), "Oops Check your internet connection!", FancyToast.LENGTH_LONG, FancyToast.ERROR, R.mipmap.mzizi_app_icon,false).show();

                        AsyncTask asyncTask = new AsyncTask() {
                            @Override
                            protected void onPreExecute() {
                                super.onPreExecute();
                            }

                            @Override
                            protected Object doInBackground(Object[] params) {
                                ParentMziziDatabase db = ParentMziziDatabase.getInstance(getActivity().getApplicationContext());
                                String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                                if(studentid == null){
                                    studentid  = "0";
                                }
                                List<ParentChat> parentChatList = db.getParentChatDAO().getParentChat(Integer.valueOf(studentid));
                                return parentChatList;
                            }

                            @Override
                            protected void onPostExecute(Object o) {


                                List<ParentChat> parentChatList = (List<ParentChat>) o;

                                if (parentChatList.size() > 0) {
                                    //This is just hard coded
                                    //create initial data list
//                    User sender = new User(true);
//                    Message sentmessage1 = new Message("You can send a text, just for trial!",sender, new Date(System.currentTimeMillis()));
//                    Message sentmessage2 = new Message("Tell me what to change, or what to improve, i will appreciate it very much",sender, new Date(System.currentTimeMillis()));
//                    messagestore.add(sentmessage1);
//                    messagestore.add(sentmessage2);

                                    messagestore.clear();
                                    for (ParentChat parentChat : parentChatList) {
                                        User sender = new User(true);//reciepient
                                        if (parentChat.getActor().contains("You")) {
                                            sender = new User(false);//sender
                                        }


                                        Message message = new Message(parentChat.getMsg(), sender, parentChat.getDatePosted(), false, false, true);
                                        messagestore.add(message);
                                    }

                                    messageNotSent = "MessageNotSent";

                                    User recipient = new User(false);//reciepient

                                    Message sentmessage = new Message(parentChat2.getMsg(), recipient, parentChat2.getDatePosted(), false, true, false);
                                    messagestore.add(sentmessage);//list of messages

                                    //get recyclerview object

//                            //set recyclerview layout manager
//                            mMessageRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
//                            //create the data adapter with above data list
                                    mMessageAdapter = new PortalChatAdapter(getActivity(), messagestore, PortalChatsFragment.this);
                                    //set data adapter to recyclerview
                                    mMessageRecycler.setAdapter(mMessageAdapter);


                                    if (messagestore.size() > 0) {
                                        mMessageRecycler.scrollToPosition(messagestore.size() - 1);
                                    }

                                } else {
                                    //get recyclerview object
                                    mMessageAdapter = new PortalChatAdapter(getActivity(), messagestore, PortalChatsFragment.this);
                                    //set data adapter to recyclerview
                                    mMessageRecycler.setAdapter(mMessageAdapter);
                                }


                                super.onPostExecute(o);
                            }
                        };
                        asyncTask.execute();
                    }
                });


            } else {
                FancyToast.makeText(getActivity(), "No message to Send", FancyToast.LENGTH_SHORT, FancyToast.INFO, R.mipmap.mzizi_app_icon,false).show();
            }
        }





    }


    private void sendRequestForParentChat(Student student, final Context context, final ParentChat chatMessagetoSend){

        ParentChatRequest parentChat = new ParentChatRequest(
                student.getStudentID(),//"24528",
                chatMessagetoSend.getMsg(),
                "0",
                student.getAppcode(),
                String.valueOf(menuItemID)
        );


//        Log.d(getActivity().getPackageName().toUpperCase(), parentChat.toString());
//
//
//        if(true){
//            Toast.makeText(getActivity(), "Selected: " + menuItemID + " value: " + enquiryTypes[menuItemID-1], Toast.LENGTH_SHORT).show();
//            return;
//        }



        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<List<ParentChat>> userCall = apiInterface.sendParentChat(parentChat);
        userCall.enqueue(new Callback<List<ParentChat>>() {
            @Override
            public void onResponse(Call<List<ParentChat>> call, final Response<List<ParentChat>> response) {
                if(response.code() == 200){
                    AsyncTask asyncTask = new AsyncTask() {


                        @Override
                        protected Object doInBackground(Object[] params) {
                            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context.getApplicationContext());
                            ParentChatDAO parentChatDAO = db.getParentChatDAO();
                            String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                            if(studentid == null){
                                studentid  = "0";
                            }
                            parentChatDAO.deleteParentChat(Integer.valueOf(studentid));
                            for(ParentChat res : response.body()){
                                res.setStudID(Integer.valueOf(studentid));
                            }
                            parentChatDAO.insertParentChat(response.body());
                            return response.body();
                        }

                        @Override
                        protected void onPostExecute(Object o) {

                            List<ParentChat> parentChatList = (List<ParentChat>)o;
                            messagestore.clear();
                            for(ParentChat parentChat : parentChatList){
                                User recipient = new User(true);//reciepient
                                if(parentChat.getActor().contains("You")){
                                    recipient = new User(false);//sender
                                }

                                Message sentmessage = new Message(parentChat.getMsg(),recipient,parentChat.getDatePosted(),false,false,true);

                                messagestore.add(sentmessage);//list of messages



                                mMessageAdapter = new PortalChatAdapter(getActivity(), messagestore, PortalChatsFragment.this);
                                //set data adapter to recyclerview
                                mMessageRecycler.setAdapter(mMessageAdapter);
                                messageNotSent = "MessageSent";
                                //update the message list
                                int newMessagePosition = messagestore.size() - 1;
                                //notify recycler view insert one new data
                                // mMessageAdapter.notifyItemChanged(newMessagePosition, sentmessage);
                                //Scroll recyclerview to the last message
                                mMessageRecycler.scrollToPosition(newMessagePosition);



                            }

//                            //update the message list
//                            int newMessagePosition = messagestore.size() - 1;
//                            //notify recycler view insert one new data
//                            mMessageAdapter.notifyItemChanged(newMessagePosition, sentmessage);
//                            //Scroll recyclerview to the last message
//                            mMessageRecycler.scrollToPosition(newMessagePosition);
//                            //empty teh input edit text box
//                            textMessage.setText("");

                            super.onPostExecute(o);
                        }
                    };
                    asyncTask.execute();
                }
            }

            @Override
            public void onFailure(Call<List<ParentChat>> call, Throwable t) {


                AsyncTask asyncTask = new AsyncTask() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }

                    @Override
                    protected Object doInBackground(Object[] params) {
                        ParentMziziDatabase db = ParentMziziDatabase.getInstance(getActivity().getApplicationContext());
                        String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                        if(studentid == null){
                            studentid  = "0";
                        }
                        List<ParentChat> parentChatList =  db.getParentChatDAO().getParentChat(Integer.valueOf(studentid));
                        return parentChatList;
                    }

                    @Override
                    protected void onPostExecute(Object o) {


                        List<ParentChat> parentChatList = (List<ParentChat>)o;

                        if(parentChatList.size() > 0){
                            //This is just hard coded
                            //create initial data list
//                    User sender = new User(true);
//                    Message sentmessage1 = new Message("You can send a text, just for trial!",sender, new Date(System.currentTimeMillis()));
//                    Message sentmessage2 = new Message("Tell me what to change, or what to improve, i will appreciate it very much",sender, new Date(System.currentTimeMillis()));
//                    messagestore.add(sentmessage1);
//                    messagestore.add(sentmessage2);

                            messagestore.clear();
                            for(ParentChat parentChat : parentChatList){
                                User sender = new User(true);//reciepient
                                if(parentChat.getActor().contains("You")){
                                    sender = new User(false);//sender
                                }



                                Message message = new Message(parentChat.getMsg(),sender,parentChat.getDatePosted(),false,false,true);
                                messagestore.add(message);
                            }

                            messageNotSent = "MessageNotSent";

                            User recipient = new User(false);//reciepient

                            Message  sentmessage  = new Message(parentChat2.getMsg(),recipient,parentChat2.getDatePosted(),false,true,false);
                            messagestore.add(sentmessage);//list of messages

                            //get recyclerview object

//                            //set recyclerview layout manager
//                            mMessageRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
//                            //create the data adapter with above data list
                            mMessageAdapter = new PortalChatAdapter(getActivity(), messagestore, PortalChatsFragment.this);
                            //set data adapter to recyclerview
                            mMessageRecycler.setAdapter(mMessageAdapter);


                            if(messagestore.size()>0){
                                mMessageRecycler.scrollToPosition(messagestore.size()- 1);
                            }

                        }else{
                            //get recyclerview object
                            mMessageAdapter = new PortalChatAdapter(getActivity(), messagestore, PortalChatsFragment.this);
                            //set data adapter to recyclerview
                            mMessageRecycler.setAdapter(mMessageAdapter);
                        }


                        super.onPostExecute(o);
                    }
                };
                asyncTask.execute();


            }
        });

    }



    public boolean internetConnection(){
        if(getActivity() != null) {
            final ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if ((mobile.getState() == NetworkInfo.State.CONNECTED) || wifi.getState() == NetworkInfo.State.CONNECTED) {
                return true;//connected
            }
        }
        return false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
       // UtilityFunctions.closeKeyboard(PortalChatsFragment.this.getContext());

        if(messageNotSent.contains("MessageNotSent") || messageNotSent.contains("SendingMessage")){


           // Toast.makeText(getActivity(), parentChat2.getMsg(), Toast.LENGTH_SHORT).show();
            Paper.book().write("LastWrittenMessage", parentChat2.getMsg());
           // UtilityFunctions.closeKeyboard(PortalChatsFragment.this.getContext());


        }else if(messageNotSent == "" || messageNotSent.contains("MessageSent")){
            Paper.book().delete("LastWrittenMessage");
          //  UtilityFunctions.closeKeyboard(PortalChatsFragment.this.getContext());
                       // finish();
                        //((MainActivity) getActivity()).backToHome();
            //Toast.makeText(getActivity(), "Dint have message", Toast.LENGTH_SHORT).show();
        }
    }





    //ADPATER
   private class PortalChatAdapter extends RecyclerView.Adapter {

        private Context mContext;
        private List<Message> mMessageList = new ArrayList<>();

        private static final int VIEW_TYPE_MESSAGE_SENT=1;
        private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
        private PortalChatsFragment portalChatsFragment;


        public PortalChatAdapter(Context context, List<Message> messageList, PortalChatsFragment portalChatsFragment){
            this.mContext = context;
            this.mMessageList = messageList;
            this.portalChatsFragment = portalChatsFragment;

        }

        //detemines the appropriate ViewType accoring to the sender of the message
        @Override
        public int getItemViewType(int position) {
            Message message =  mMessageList.get(position);


            //determine if the user is a sender or its the loginRecipient
            if(message.getSender().getIsSender()){
                //if the current user is the sender of the message
                return VIEW_TYPE_MESSAGE_SENT;
            }else{
                return VIEW_TYPE_MESSAGE_RECEIVED;
            }
        }

        //inflates the appropriate layout according to the ViewType
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;


            if(viewType == VIEW_TYPE_MESSAGE_SENT){
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent, parent, false);

                return new SentMessageHolder(view);
            }else if(viewType == VIEW_TYPE_MESSAGE_RECEIVED){
                view = LayoutInflater.from (parent.getContext()).inflate(R.layout.item_message_recipient, parent, false);
                return new RecipientMessageHolder(view);
            }

            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Message message =  mMessageList.get(position);

            if(message.getSender().getIsSender()){
                SentMessageHolder sentHolder = (SentMessageHolder) holder;
                sentHolder.bind(message);
            }else{
                RecipientMessageHolder receivedHolder = (RecipientMessageHolder) holder;
                receivedHolder.bind(message);
            }

        }

        @Override
        public int getItemCount() {
            return mMessageList.size();
        }

        //each viewholder holds member views that can be bounded to particular information contained in a message

        //the school that sent the message
        private class SentMessageHolder extends RecyclerView.ViewHolder{

            private TextView messageText;
            private TextView timeText;
            private ImageView profileImage;
            private RelativeLayout layout_to_change_btn_bubble;
            private TextView view_text_id;



            SentMessageHolder(View itemView){
                super(itemView);
                view_text_id = itemView.findViewById(R.id.view_text_id);
                messageText =  itemView.findViewById(R.id.text_message_body_sent);
                timeText =  itemView.findViewById(R.id.text_message_time);
                profileImage  =  itemView.findViewById(R.id.image_message_profile);//visibility
                layout_to_change_btn_bubble = itemView.findViewById(R.id.layout_to_change_btn_bubble);//background



            }
            /*we should implemnt a bind(object) method within the DataHolder class.
             * This gives view binding to the DataHolder class rather than to onBindViewHolder.
             *   hence producing cleaner code amidst multiple ViewHolders and ViewTypes.
             *   it also, allows aus to add easily OnClickListeners, if necessary*/
            void bind(Message message){


                int position = getAdapterPosition();
                messageText.setText(message.getMessage());
                timeText.setText(message.getCreatedAt());
                layout_to_change_btn_bubble.setBackground(portalChatsFragment.getResources().getDrawable(R.drawable.incoming_speach_bubble));
                view_text_id.setVisibility(View.INVISIBLE);
                profileImage.setVisibility(View.VISIBLE);

                if(position > 0){
                    Message previousMessage =   mMessageList.get(position - 1);
                    if(previousMessage.getSender().getIsSender()){
                        view_text_id.setVisibility(View.GONE);
                        profileImage.setVisibility(View.GONE);
                        layout_to_change_btn_bubble.setBackground(portalChatsFragment.getResources().getDrawable(R.drawable.incoming_speach_buble_again));
                    }

                }

            }

        }

        //the login user, is the recipient of the message
        private class RecipientMessageHolder extends RecyclerView.ViewHolder{
            private TextView messageText;
            private TextView timeText;
            private  ImageView profileImage;
            private ImageView success_ID;
            private ImageView failure_ID;
            private ProgressBar sending_ID;
            private RelativeLayout layout_to_change_btn_bubble;
            private TextView view_text_id;
            private TextView txt_label_tap_resend;
            Animation anim;

            RecipientMessageHolder(View itemView){
                super(itemView);

                view_text_id = itemView.findViewById(R.id.view_text_id);
                messageText =  itemView.findViewById(R.id.text_message_body_recipient);
                timeText =  itemView.findViewById(R.id.text_message_time);
                profileImage  =  itemView.findViewById(R.id.image_message_profile);//visibility
                success_ID = itemView.findViewById(R.id.success_ID);
                failure_ID = itemView.findViewById(R.id.failure_ID);
                sending_ID = itemView.findViewById(R.id.sending_ID);
                txt_label_tap_resend = itemView.findViewById(R.id.txt_label_tap_resend);
                layout_to_change_btn_bubble = itemView.findViewById(R.id.layout_to_change_btn_bubble);//background
                layout_to_change_btn_bubble.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Message message =  mMessageList.get(getAdapterPosition());
                        if(message.isShowCanceled()){
                            portalChatsFragment.textMessage.setText(message.getMessage());
                            mMessageList.remove(getAdapterPosition());
                            portalChatsFragment.mMessageRecycler.getAdapter().notifyItemRemoved(getAdapterPosition());
                            portalChatsFragment.mMessageRecycler.getAdapter().notifyDataSetChanged();
                        }
                    }
                });

            }

            void bind(Message message){

                messageText.setText(message.getMessage());
                timeText.setText(message.getCreatedAt());
                if(message.isShowLoading()) {
                    sending_ID.setVisibility(View.VISIBLE);
                    failure_ID.setVisibility(View.GONE);
                    success_ID.setVisibility(View.GONE);
                }else if(message.isShowCanceled()){
                    success_ID.setVisibility(View.GONE);
                    sending_ID.setVisibility(View.GONE);
                    failure_ID.setVisibility(View.VISIBLE);
                    txt_label_tap_resend.setVisibility(View.VISIBLE);
                    anim = AnimationUtils.loadAnimation(portalChatsFragment.getActivity(), R.anim.anim_blink);
                    txt_label_tap_resend.startAnimation(anim);
                }else if(message.isShowsuccess()){
                    success_ID.setVisibility(View.VISIBLE);
                    sending_ID.setVisibility(View.GONE);
                    failure_ID.setVisibility(View.GONE);
                }
                layout_to_change_btn_bubble.setBackground(portalChatsFragment.getResources().getDrawable(R.drawable.out_going_speech_bubble));
                view_text_id.setVisibility(View.INVISIBLE);
                profileImage.setVisibility(View.VISIBLE);

                int position = getAdapterPosition();
                if(position > 0){
                    Message previousMessage =   mMessageList.get(position - 1);
                    if(!previousMessage.getSender().getIsSender()){
                        view_text_id.setVisibility(View.GONE);
                        profileImage.setVisibility(View.GONE);
                        layout_to_change_btn_bubble.setBackground(portalChatsFragment.getResources().getDrawable(R.drawable.out_going_speech_bubble_again));
                    }

                }


            }

        }

    }

}
