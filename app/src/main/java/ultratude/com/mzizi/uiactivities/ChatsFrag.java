package ultratude.com.mzizi.uiactivities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.helperactivityclasses.ChatRecyclerViewAdapter;

/*
 * Created by Admin on 5/21/2018.
 */

public class ChatsFrag extends Fragment implements View.OnClickListener{

    static final String SHARE_NAME = "storemessages";

    static SharedPreferences pshare;
    private RecyclerView chatsview;
    private TextInputLayout sendMessageLayout;
    private EditText sendMessagetext;
    TextView sendMessagebtn;

    int messageCount =0;


    String message;
    static FragmentManager fragmentManager;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public ChatsFrag(){

    }




    public static ChatsFrag newInstance(String param1, String param2, FragmentManager fragmentManager) {
        ChatsFrag.fragmentManager = fragmentManager;

        ChatsFrag fragment = new ChatsFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    //ALL INITIALIZAATIONS SHOULD BE DONE HERE



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_messages_layout, container, false);
    }

   private static List<Object> storeMessageDetail = new ArrayList<>();


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {


        String message = ( getArguments() != null) ? getArguments().getString("message") : "has no message to display";

        Toast.makeText(getActivity(),message , Toast.LENGTH_LONG).show();
        super.onStart();


        chatsview = getActivity().findViewById(R.id.direct_messages_recycle);
        sendMessageLayout =  getActivity().findViewById(R.id.textmessagelayout);
        sendMessagetext =  getActivity().findViewById(R.id.textmessage);
        sendMessagebtn = getActivity().findViewById(R.id.sendMessage);
        sendMessagebtn.setOnClickListener(this);



        pshare = getActivity().getSharedPreferences(SHARE_NAME, 0);
        SharedPreferences.Editor edit = pshare.edit();


        SimpleDateFormat sf = new SimpleDateFormat("dd MMM YYYY HH:mm:ss");
        storeMessageDetail.add(R.drawable.profile3);
        storeMessageDetail.add(getResources().getString(R.string.in_news));
        //should use the Calendar class
        storeMessageDetail.add(sf.format(new Date(System.currentTimeMillis())));

        super.onActivityCreated(savedInstanceState);
    }







    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.sendMessage) {
            String message = sendMessagetext.getText().toString();

            if (message.isEmpty()) {
                sendMessageLayout.setErrorEnabled(true);
                sendMessagetext.requestFocus();
                sendMessageLayout.setError("Nothing to send");
            } else {

                List<Object> storeMessageDetails = new ArrayList<>();

                    //these are just hardcoded textmessage

                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

                //This the new message
                storeMessageDetails.add(R.drawable.stud_icon);
                storeMessageDetails.add(message);
                storeMessageDetails.add(sf.format(new Date(System.currentTimeMillis())));

                //change the toolbar title text

                LinearLayoutManager lm = new LinearLayoutManager(getActivity() , LinearLayoutManager.VERTICAL, false);
                RecyclerView rs =  getActivity().findViewById(R.id.direct_messages_recycle);
               // Context context, ArrayList<Integer> picturesUrls, ArrayList<String> text, ArrayList<String> time

                //This is just to look if there are errors.
                rs.setLayoutManager(lm);
               // rs.setAdapter(new ChatRecyclerViewAdapter(this, storeMessageDetails));
                rs.notifyAll();



            }
        }
    }



    //THIS IS WHAT WE ARE ADDING THEN WE CHANGE





}
