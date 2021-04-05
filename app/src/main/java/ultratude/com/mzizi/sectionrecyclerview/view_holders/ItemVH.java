package ultratude.com.mzizi.sectionrecyclerview.view_holders;


import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cruxlab.sectionedrecyclerview.lib.BaseSectionAdapter;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.sectionrecyclerview.adapters.BaseAdapter;


public class ItemVH extends BaseSectionAdapter.ItemViewHolder {

    private BaseAdapter adapter;
    private ImageButton btnDuplicate, btnChange, btnRemove, btnHeader;
    private TextView tvText;

    public ItemVH(View itemView) {
        super(itemView);
        this.btnDuplicate = itemView.findViewById(R.id.ibtn_duplicate);
        this.btnChange = itemView.findViewById(R.id.ibtn_change);
        this.btnRemove = itemView.findViewById(R.id.ibtn_remove);
        this.btnHeader = itemView.findViewById(R.id.ibtn_header);
        this.tvText = itemView.findViewById(R.id.tv_text);
    }

    public void bind(final BaseAdapter adapter, final String string) {
        this.adapter = adapter;
        tvText.setText(string);
        btnDuplicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // The adapter position is used to calculate what section index corresponds to this
                // ViewHolder. So, this method returns -1 when the ViewHolder is not used in any
                // RecyclerView.
                // In our case it can happen when we click on a delete button of the view whose removal
                // animation is still in progress.
                int sectionPos = getSectionAdapterPosition();
                if (sectionPos == -1) return;

                adapter.duplicateItem(sectionPos);

            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // The adapter position is used to calculate what section index corresponds to this
                // ViewHolder. So, this method returns -1 when the ViewHolder is not used in any
                // RecyclerView.
                // In our case it can happen when we click on a delete button of the view whose removal
                // animation is still in progress.
                int sectionPos = getSectionAdapterPosition();
                if (sectionPos == -1) return;

                adapter.changeItem(sectionPos);
            }
        });
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // The adapter position is used to calculate what section index corresponds to this
                // ViewHolder. So, this method returns -1 when the ViewHolder is not used in any
                // RecyclerView.
                // In our case it can happen when we click on a delete button of the view whose removal
                // animation is still in progress.
                int sectionPos = getSectionAdapterPosition();
                if (sectionPos == -1) return;

                adapter.removeItem(sectionPos);
            }
        });
        btnHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.updateHeaderVisibility(!adapter.isHeaderVisible());

                // The adapter position is used to calculate what section index corresponds to this
                // ViewHolder. So, this method returns -1 when the ViewHolder is not used in any
                // RecyclerView.
                // In our case it can happen when we click on a delete button of the view whose removal
                // animation is still in progress.
                int section = getSection();
                if (section < 0) return;

                // We update all items of this section to change the visibility icon.
                // We can also update all items (and header) calling
                // adapter.sectionManager.updateSection(getSection()).
                adapter.notifyItemRangeChanged(0, adapter.getItemCount());
            }
        });
        // We update visibility icon only when binding this ViewHolder.
        btnHeader.setImageResource(adapter.isHeaderVisible() ? R.drawable.about_icon2 : R.drawable.about_icon);
    }

    public void removeItem(int pos) {
        adapter.removeItem(pos);
    }

}