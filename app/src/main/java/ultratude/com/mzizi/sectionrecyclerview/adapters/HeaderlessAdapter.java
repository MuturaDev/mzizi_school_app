package ultratude.com.mzizi.sectionrecyclerview.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.cruxlab.sectionedrecyclerview.lib.SimpleSectionAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.sectionrecyclerview.view_holders.SimpleItemVH;

public class HeaderlessAdapter extends SimpleSectionAdapter<SimpleItemVH> {

    public ArrayList<String> strings = new ArrayList<>(Arrays.asList(
            "This is a section without header.",
            "Use header buttons to pin/unpin header, duplicate/change/remove section.",
            "Use section with header item buttons to hide/show header, duplicate/change/remove item.",
            "Use section without header item buttons to duplicate/change/remove item range."));

    @Override
    public SimpleItemVH onCreateItemViewHolder(ViewGroup parent, short type) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_item_vh, parent, false);
        return new SimpleItemVH(view);
    }

    @Override
    public void onBindItemViewHolder(SimpleItemVH holder, int position) {
        holder.bind(this, strings.get(position));
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    // Inserting some same items to the RecyclerView.
    public void duplicateItems(int pos) {
        String text = strings.get(pos);
        int cnt = new Random().nextInt(3) + 1;
        for (int i = 0; i < cnt; i++) {
            strings.add(pos + 1, text);
        }
        notifyItemRangeInserted(pos + 1, cnt);
    }

    // Removing this and next same items from the RecyclerView.
    public void removeItems(int pos) {
        String text = strings.get(pos);
        int cnt = 0;
        while (pos < getItemCount() && strings.get(pos).equals(text)) {
            strings.remove(pos);
            cnt++;
        }
        notifyItemRangeRemoved(pos, cnt);
    }

    // Updating this and next same item's text.
    public void changeItems(int pos) {
        String text = strings.get(pos);
        int changePos = pos;
        while (changePos < getItemCount() && strings.get(changePos).equals(text)) {
            strings.set(changePos, text + " *changed*");
            changePos++;
        }
        notifyItemRangeChanged(pos, changePos - pos);
    }

}