package ultratude.com.mzizi.sectionrecyclerview;


import android.graphics.drawable.Drawable;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cruxlab.sectionedrecyclerview.lib.SectionDataManager;
import com.cruxlab.sectionedrecyclerview.lib.SectionHeaderLayout;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.crashreport.Catcho;
import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;
import ultratude.com.mzizi.sectionrecyclerview.adapters.BaseAdapter;
import ultratude.com.mzizi.sectionrecyclerview.adapters.HeaderlessAdapter;
import ultratude.com.mzizi.sectionrecyclerview.adapters.SimpleAdapter;


public class SectionRecyclerExample extends AppCompatActivity {

    private int[] colors;
    private DemoSwipeCallback[] callbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Catcho.Builder(this)
                .recipients(Constants.CRASH_REPORT_EMAIL)
                .build();
        setContentView(R.layout.section_recycler_example_layout);
        initFields();

        // Initialize your RecyclerView with a successor of LinearLayoutManager:
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);

        // Create SectionDataManager and set its adapter to the RecyclerView.
        // After that you can use SectionDataManager, that implements SectionManager interface,
        // to add/remove/replace sections in your RecyclerView.
        SectionDataManager sectionDataManager = new SectionDataManager();
        RecyclerView.Adapter adapter = sectionDataManager.getAdapter();
        recyclerView.setAdapter(adapter);

        // You can customize item swiping behaviour for each section individually.
        // To enable this feature, create ItemTouchHelper initialized with SectionDataManager's callback
        // and attach it to your RecyclerView:
        ItemTouchHelper.Callback callback = sectionDataManager.getSwipeCallback();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // To enable displaying pinned headers, attach SectionHeaderLayout to your RecyclerView and
        // SectionDataManager. After this you can manage header pinned state with your adapter.
        // You can disable displaying pinned headers any time by calling detach().
        SectionHeaderLayout sectionHeaderLayout = findViewById(R.id.section_header_layout);
        sectionHeaderLayout.attachTo(recyclerView, sectionDataManager);

        for (int i = 0; i < 6; i++) {
            if (i % 4 == 3) {
                // Adding a section without header to the end of the list, passing a SimpleSectionAdapter.
                sectionDataManager.addSection(new HeaderlessAdapter());
                continue;
            }
            //int color = colors[i / 4 % 3];
            int color = colors[0];
          //  BaseAdapter sectionAdapter  = new DefaultAdapter(color, sectionDataManager, true, true);
            BaseAdapter sectionAdapter;
//            if (i % 4 == 0) {
//                //sectionAdapter = new DefaultAdapter(color, sectionDataManager, true, true);
//            }
           // if (i % 4 == 1) {
              sectionAdapter = new SimpleAdapter(color, sectionDataManager, true, true);
//           }
//           else {
//                sectionAdapter = new SmartAdapter(color, sectionDataManager, true, true);
//           }
           // DemoSwipeCallback swipeCallback = callbacks[i / 4 % 3];
            DemoSwipeCallback swipeCallback = callbacks[i / 4 % 3];

            // Adding a section with header to the end of the list, passing SectionAdapter,
            // DemoSwipeCallback to customize swiping behavior for items in this section and
            // a header type, that is used to determine, that sections have the same HeaderViewHolder
            // for further caching and reusing.
            sectionDataManager.addSection(sectionAdapter, swipeCallback, sectionAdapter.type);
        }

        // When using GridLayoutManager set SpanSizeLookup as follows to display full width
        // section headers. It uses SectionDataManager's implementation of PositionManager
        // to determine whether the item at the given position is a header:
        // final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        // recyclerView.setLayoutManager(gridLayoutManager);
        // final PositionManager posManager = sectionDataManager;
        // gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
        //    @Override
        //    public int getSpanSize(int position) {
        //        if (posManager.isHeader(position)) {
        //            return gridLayoutManager.getSpanCount();
        //        } else {
        //            return 1;
        //        }
        //    }
        // });
    }

    private void initFields() {
        colors = new int[3];
        colors[0] = getResources().getColor(R.color.colorPrimary);
        colors[1] = getResources().getColor(R.color.homeText);
        colors[2] = getResources().getColor(R.color.confirmation_success);
        Drawable deleteIcon = getResources().getDrawable(R.drawable.exam);
        callbacks = new DemoSwipeCallback[3];
        callbacks[0] = new DemoSwipeCallback(colors[0], deleteIcon);
        callbacks[1] = new DemoSwipeCallback(colors[1], deleteIcon);
        callbacks[2] = new DemoSwipeCallback(colors[2], deleteIcon);
    }

}
