package ultratude.com.mzizi.sectionrecyclerview;

import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import android.view.View;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.cruxlab.sectionedrecyclerview.lib.BaseSectionAdapter;
import com.cruxlab.sectionedrecyclerview.lib.SectionItemSwipeCallback;

import ultratude.com.mzizi.sectionrecyclerview.view_holders.ItemVH;


public class DemoSwipeCallback extends SectionItemSwipeCallback {

    public int color;
    public Drawable deleteIcon;
    private ColorDrawable background;

    public DemoSwipeCallback(int color, Drawable deleteIcon) {
        this.color = color;
        this.background = new ColorDrawable();
        this.deleteIcon = deleteIcon;
    }

    @Override
    public int getSwipeDirFlags(RecyclerView recyclerView, BaseSectionAdapter.ItemViewHolder viewHolder) {
        return ItemTouchHelper.LEFT;
    }

    @Override
    public void onSwiped(BaseSectionAdapter.ItemViewHolder viewHolder, int direction) {
        ItemVH itemViewHolder = (ItemVH) viewHolder;
        int sectionPos = itemViewHolder.getSectionAdapterPosition();
        // This method can return -1 when getAdapterPosition() of the corresponding
        // RecyclerView.ViewHolder returns -1 or when this ViewHolder isn't used
        // in any RecyclerView.
        if (sectionPos == -1) return;
        itemViewHolder.removeItem(sectionPos);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, BaseSectionAdapter.ItemViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;
        int itemHeight = itemView.getBottom() - itemView.getTop();
        background.setColor(color);
        background.setBounds((int) (itemView.getRight() + dX), itemView.getTop(), itemView.getRight(), itemView.getBottom());
        background.draw(c);
        int deleteIconTop = itemView.getTop() + (itemHeight - deleteIcon.getIntrinsicHeight()) / 2;
        int deleteIconMargin = (itemHeight - deleteIcon.getIntrinsicHeight()) / 2;
        int deleteIconLeft = itemView.getRight() - deleteIconMargin - deleteIcon.getIntrinsicWidth();
        int deleteIconRight = itemView.getRight() - deleteIconMargin;
        int deleteIconBottom = deleteIconTop + deleteIcon.getIntrinsicHeight();
        deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
        deleteIcon.draw(c);
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

}