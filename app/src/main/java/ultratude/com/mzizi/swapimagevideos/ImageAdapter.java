package ultratude.com.mzizi.swapimagevideos;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalToDoListResponse;


//http://www.devexchanges.info/2016/11/cards-stack-like-tinder-application-in.html
//https://github.com/flschweiger/SwipeStack/blob/master/sample/src/main/java/link/fls/swipestacksample/MainActivity.java
//https://github.com/flschweiger/SwipeStack
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {


    private Context context;

    public ImageAdapter( Context context){

        this.context = context;
    }


    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card, parent, false);
        return new ImageAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ImageAdapter.ViewHolder holder, final int position) {


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