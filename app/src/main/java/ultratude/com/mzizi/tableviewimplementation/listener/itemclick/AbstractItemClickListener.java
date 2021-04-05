package ultratude.com.mzizi.tableviewimplementation.listener.itemclick;


import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import ultratude.com.mzizi.tableviewimplementation.ITableView;
import ultratude.com.mzizi.tableviewimplementation.adapter.recyclerview.CellRecyclerView;
import ultratude.com.mzizi.tableviewimplementation.handler.SelectionHandler;
import ultratude.com.mzizi.tableviewimplementation.listener.ITableViewListener;



/**
 * Created by evrencoskun on 22.11.2017.
 */

public abstract class AbstractItemClickListener implements RecyclerView.OnItemTouchListener {
    private ITableViewListener mListener;
    @NonNull
    protected GestureDetector mGestureDetector;
    @NonNull
    protected CellRecyclerView mRecyclerView;
    @NonNull
    protected SelectionHandler mSelectionHandler;
    @NonNull
    protected ITableView mTableView;

    public AbstractItemClickListener(@NonNull CellRecyclerView recyclerView, @NonNull ITableView tableView) {
        this.mRecyclerView = recyclerView;
        this.mTableView = tableView;
        this.mSelectionHandler = tableView.getSelectionHandler();

        mGestureDetector = new GestureDetector(mRecyclerView.getContext(), new GestureDetector
                .SimpleOnGestureListener() {

            @Nullable
            MotionEvent start;

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return clickAction(mRecyclerView, e);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return doubleClickAction(e);
            }

            @Override
            public boolean onDown(MotionEvent e) {
                start = e;
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                // Check distance to prevent scroll to trigger the event
                if (start != null && Math.abs(start.getRawX() - e.getRawX()) < 20 && Math.abs
                        (start.getRawY() - e.getRawY()) < 20) {
                    longPressAction(e);
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        // Return false intentionally
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    @NonNull
    protected ITableViewListener getTableViewListener() {
        if (mListener == null) {
            mListener = mTableView.getTableViewListener();
        }
        return mListener;
    }

    abstract protected boolean clickAction(@NonNull RecyclerView view, @NonNull MotionEvent e);

    abstract protected void longPressAction(@NonNull MotionEvent e);

    abstract protected boolean doubleClickAction(MotionEvent e);
}
