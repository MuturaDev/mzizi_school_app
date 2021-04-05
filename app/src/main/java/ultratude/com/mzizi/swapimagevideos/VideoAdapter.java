package ultratude.com.mzizi.swapimagevideos;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalToDoListResponse;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {


    private Context context;

    public VideoAdapter( Context context){

        this.context = context;
    }


    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card, parent, false);
        return new VideoAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final VideoAdapter.ViewHolder holder, final int position) {


    }


    @Override
    public int getItemCount() {
        return 0;
    }

    //this is a class
    //this will take a hold of the views on the item view.
    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView){
            super(itemView);

        }

        public void bind(PortalToDoListResponse toDoListResponse){

        }

    }



}
