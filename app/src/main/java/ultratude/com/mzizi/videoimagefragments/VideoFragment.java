package ultratude.com.mzizi.videoimagefragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.codewaves.youtubethumbnailview.ThumbnailLoadingListener;
import com.codewaves.youtubethumbnailview.ThumbnailView;

import org.greenrobot.eventbus.EventBus;


import java.util.List;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.Utils.UtilityFunctions;
import ultratude.com.mzizi.eventbus.MessageEvent;
import ultratude.com.mzizi.modelclasses.response.YoutubeVideoGalleryResponse;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;




public class VideoFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayout pb_loading_video_progress;
    private TextView no_content_text;


    public VideoFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.video_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
         recyclerView = view.findViewById(R.id.video_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
//                linearLayoutManager.getOrientation());
//        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);


        pb_loading_video_progress = view.findViewById(R.id.pb_loading_video_progress);
        no_content_text = view.findViewById(R.id.no_content_text);


        AsyncTask asyncTask = new AsyncTask() {

            @Override
            protected void onPreExecute() {
                showProgress(true);
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Object o) {
                showProgress(false);
                List<YoutubeVideoGalleryResponse> responseList = (List<YoutubeVideoGalleryResponse>) o;
                if(!responseList.isEmpty()){

                    VideoFragmentAdapter adapter = new VideoFragmentAdapter(responseList, getActivity());
                    recyclerView.setAdapter(adapter);


                    showNoContent(false);
                }else{

                    showNoContent(true);
                }
                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                ParentMziziDatabase db = ParentMziziDatabase.getInstance(getActivity());
                String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                if(studentid == null){
                    studentid  = "0";
                }
                return db.getYoutubeVideoGalleryResponseDAO().getYoutubeVideoGalleryResponse(Integer.valueOf(studentid));

            }
        };
        asyncTask.execute();





        super.onViewCreated(view, savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = 0;

            if(isAdded())
                shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);



            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            recyclerView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });


            pb_loading_video_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            pb_loading_video_progress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    pb_loading_video_progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            pb_loading_video_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    private void showNoContent(boolean show){
        if(show){
            no_content_text.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }else{
            no_content_text.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
    }





    private class VideoFragmentAdapter extends RecyclerView.Adapter<VideoFragmentAdapter.ViewHolder>{

        final List<YoutubeVideoGalleryResponse> responseList;
        final Context context;

        public VideoFragmentAdapter(final List<YoutubeVideoGalleryResponse> responseList, final Context context){
            this.responseList = responseList;
            this.context = context;
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            private TextView video_name;
            private ReadMoreTextView video_body;
            private LinearLayout video_item_layout;
            private ThumbnailView thumbnailView;


            public ViewHolder(final View view){
                super(view);

                video_name  = view.findViewById(R.id.video_name);
                video_body = view.findViewById(R.id.video_body);
                video_body.setTrimLines(5);
                video_item_layout = view.findViewById(R.id.video_item_layout);
                video_item_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //CALL The activity
                        EventBus.getDefault().post(new MessageEvent(responseList.get(getAdapterPosition())));
                    }
                });

                thumbnailView = view.findViewById(R.id.thumbnail);
                thumbnailView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //CALL The activity
                        EventBus.getDefault().post(new MessageEvent(responseList.get(getAdapterPosition())));
                    }
                });



            }



            public void bind(final YoutubeVideoGalleryResponse response){
                video_name.setText(response.getName().replace("\n", "").toUpperCase());

                video_body.setText(response.getBody());

                String videourl = "https://www.youtube.com/watch?v=" + UtilityFunctions.getYoutubeID(response.getVideoLink());

                final String TAG = context.getPackageName().toUpperCase();
                //Log.d(TAG, videourl);
                thumbnailView.loadThumbnail(videourl, new ThumbnailLoadingListener() {
                    @Override
                    public void onLoadingStarted(@NonNull String url, @NonNull View view) {
                        Log.i(TAG, "Thumbnail load started.");
                    }

                    @Override
                    public void onLoadingComplete(@NonNull String url, @NonNull View view) {
                        Log.i(TAG, "Thumbnail load finished.");
                    }

                    @Override
                    public void onLoadingCanceled(@NonNull String url, @NonNull View view) {
                        Log.i(TAG, "Thumbnail load canceled.");
                    }

                    @Override
                    public void onLoadingFailed(@NonNull String url, @NonNull View view, Throwable error) {
                        Log.e(TAG, "Thumbnail load failed. " + error.getMessage());
                    }
                });


            }
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.video_fragment_item_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
            holder.bind(responseList.get(i));
        }

        @Override
        public int getItemCount() {
            return responseList.size();
        }
    }





}
