package ultratude.com.mzizi.sectionrecyclerview.view_holders;


import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cruxlab.sectionedrecyclerview.lib.BaseSectionAdapter;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.sectionrecyclerview.adapters.HeaderlessAdapter;


public class SimpleItemVH extends BaseSectionAdapter.ItemViewHolder {

    private ImageButton btnDuplicate, btnChange, btnRemove;
    private TextView tvText;

    public SimpleItemVH(View itemView) {
        super(itemView);
        this.btnDuplicate = itemView.findViewById(R.id.ibtn_duplicate);
        this.btnChange = itemView.findViewById(R.id.ibtn_change);
        this.btnRemove = itemView.findViewById(R.id.ibtn_remove);
        this.tvText = itemView.findViewById(R.id.tv_text);
    }

    public void bind(final HeaderlessAdapter adapter, final String string) {
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

                adapter.duplicateItems(sectionPos);

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

                adapter.changeItems(sectionPos);
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

                adapter.removeItems(sectionPos);
            }
        });
    }

}