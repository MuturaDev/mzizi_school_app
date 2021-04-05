package ultratude.com.mzizi.sectionrecyclerview.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cruxlab.sectionedrecyclerview.lib.SectionManager;


import java.util.ArrayList;
import java.util.Arrays;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.sectionrecyclerview.view_holders.HeaderVH;

public class SimpleAdapter extends BaseAdapter<HeaderVH> {

    public SimpleAdapter(int color, SectionManager sectionManager, boolean isHeaderVisible, boolean isHeaderPinned) {
        super(color, sectionManager, isHeaderVisible, isHeaderPinned);
        this.type = 2;
        this.strings = new ArrayList<>(Arrays.asList(
                "Each section can manage its header visibility.",
                "Section headers can pin to the top of the RecyclerView automatically.",
                "Top header behaves just like a regular list item (e.g. handles clicks).",
                "To enable this feature you should attach SectionHeaderLayout to your RecyclerView and SectionDataManager.",
                "RecyclerView should be SectionHeaderLayout's direct child."));
    }

    @Override
    public BaseAdapter getCopy() {
        SimpleAdapter copy = new SimpleAdapter(color, sectionManager, isHeaderVisible(), isHeaderPinned());
        copy.strings = new ArrayList<>(strings);
        return copy;
    }

    @Override
    public BaseAdapter getNext() {
        return new SmartAdapter(color, sectionManager, isHeaderVisible(), isHeaderPinned());
    }

    @Override
    public HeaderVH onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_header_vh, parent, false);
        return new HeaderVH(view);
    }

}