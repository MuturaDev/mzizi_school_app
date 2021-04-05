package ultratude.com.mzizi.tableviewimplementation.tableviewexample.tableview;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.hanks.library.AnimateCheckBox;

import java.util.HashMap;
import java.util.Map;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.elegantnumberbutton.ElegantNumberButton;
import ultratude.com.mzizi.tableviewimplementation.adapter.AbstractTableAdapter;
import ultratude.com.mzizi.tableviewimplementation.adapter.recyclerview.holder.AbstractViewHolder;
import ultratude.com.mzizi.tableviewimplementation.sort.SortState;

import ultratude.com.mzizi.tableviewimplementation.tableviewexample.tableview.holder.CellViewHolder;
import ultratude.com.mzizi.tableviewimplementation.tableviewexample.tableview.holder.ColumnHeaderViewHolder;
import ultratude.com.mzizi.tableviewimplementation.tableviewexample.tableview.holder.OrderQuantityCellViewHolder;
import ultratude.com.mzizi.tableviewimplementation.tableviewexample.tableview.holder.RowHeaderViewHolder;
import ultratude.com.mzizi.tableviewimplementation.tableviewexample.tableview.model.Cell;
import ultratude.com.mzizi.tableviewimplementation.tableviewexample.tableview.model.ColumnHeader;
import ultratude.com.mzizi.tableviewimplementation.tableviewexample.tableview.model.RowHeader;


/**
 * Created by evrencoskun on 11/06/2017.
 * <p>
 * This is a sample of custom TableView Adapter.
 */

public class TableViewAdapter extends AbstractTableAdapter<ColumnHeader, RowHeader, Cell> {

    // Cell View Types by Column Position
    private static final int MOOD_CELL_TYPE = 1;
    private static final int GENDER_CELL_TYPE = 2;
    private static final int ORDER_QUANTITY_CELL_TYPE = 3;
    // add new one if it necessary..

    private static final String LOG_TAG = TableViewAdapter.class.getSimpleName();

    //public List<AnimateCheckBox> animateCheckBoxList = new ArrayList<>();

    @NonNull
    private TableViewModel mTableViewModel;
    private Context context;
    @NonNull
    private TextView checkoutOrderCountText,checkoutCountTextBottomSheet;

    public TableViewAdapter(@NonNull TableViewModel tableViewModel, Context context,@NonNull TextView checkoutOrderCountText, TextView checkoutCountTextBottomSheet) {
        super();
        this.mTableViewModel = tableViewModel;
        this.context = context;
        this.checkoutOrderCountText = checkoutOrderCountText;
        this.checkoutCountTextBottomSheet = checkoutCountTextBottomSheet;
    }

    /**
     * This is where you create your custom Cell ViewHolder. This method is called when Cell
     * RecyclerView of the TableView needs a new RecyclerView.ViewHolder of the given type to
     * represent an item.
     *
     * @param viewType : This value comes from "getCellItemViewType" method to support different
     *                 type of viewHolder as a Cell item.
     * @see #getCellItemViewType(int);
     */
    @NonNull
    @Override
    public AbstractViewHolder onCreateCellViewHolder(@NonNull ViewGroup parent, int viewType) {
        //TODO check
        Log.e(LOG_TAG, " onCreateCellViewHolder has been called");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View layout;

        switch (viewType) {
//            case MOOD_CELL_TYPE:
//                // Get image cell layout which has ImageView on the base instead of TextView.
//                layout = inflater.inflate(R.layout.table_view_image_cell_layout, parent, false);
//
//                return new MoodCellViewHolder(layout);
//            case GENDER_CELL_TYPE:
//                // Get image cell layout which has ImageView instead of TextView.
//                layout = inflater.inflate(R.layout.table_view_image_cell_layout, parent, false);
//
//                return new GenderCellViewHolder(layout);
            case ORDER_QUANTITY_CELL_TYPE:

                layout = inflater.inflate(R.layout.table_view_elegant_number_button_cell_layout, parent, false);
                return new OrderQuantityCellViewHolder(layout);
            default:
                // For cells that display a text
                layout = inflater.inflate(R.layout.table_view_cell_layout, parent, false);

                // Create a Cell ViewHolder
                return new CellViewHolder(layout);
        }
    }

    /**
     * That is where you set Cell View Model data to your custom Cell ViewHolder. This method is
     * Called by Cell RecyclerView of the TableView to display the data at the specified position.
     * This method gives you everything you need about a cell item.
     *
     * @param holder         : This is one of your cell ViewHolders that was created on
     *                       ```onCreateCellViewHolder``` method. In this example we have created
     *                       "CellViewHolder" holder.
     * @param cellItemModel  : This is the cell view model located on this X and Y position. In this
     *                       example, the model class is "Cell".
     * @param columnPosition : This is the X (Column) position of the cell item.
     * @param rowPosition    : This is the Y (Row) position of the cell item.
     * @see #onCreateCellViewHolder(ViewGroup, int) ;
     */
    public static Map<Integer, String> bindDataQuantity = new HashMap<>();

    @Override
    public void onBindCellViewHolder(@NonNull AbstractViewHolder holder, @Nullable Cell cellItemModel, int
            columnPosition, final int rowPosition) {

        switch (holder.getItemViewType()) {
//            case MOOD_CELL_TYPE:
//                MoodCellViewHolder moodViewHolder = (MoodCellViewHolder) holder;
//                moodViewHolder.cell_image.setImageResource(mTableViewModel.getDrawable((int) cellItemModel
//                        .getData(), false));
//                break;
//            case GENDER_CELL_TYPE:
//                GenderCellViewHolder genderViewHolder = (GenderCellViewHolder) holder;
//                genderViewHolder.cell_image.setImageResource(mTableViewModel.getDrawable((int)
//                        cellItemModel.getData(), true));
//                break;
            case ORDER_QUANTITY_CELL_TYPE:
                OrderQuantityCellViewHolder quantityCellViewHolder = (OrderQuantityCellViewHolder) holder;
                quantityCellViewHolder.numberButton.setNumber(cellItemModel.getData().toString());
                quantityCellViewHolder.numberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                    @Override
                    public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {

                        bindDataQuantity.put(rowPosition, String.valueOf(newValue));
                        Log.d(context.getPackageName().toUpperCase(), "Elegant Number Button: " + bindDataQuantity);
                    }
                });
                break;
            default:
                // Get the holder to update cell item text
                CellViewHolder viewHolder = (CellViewHolder) holder;
                viewHolder.setCell(cellItemModel);
                break;
        }
    }

    /**
     * This is where you create your custom Column Header ViewHolder. This method is called when
     * Column Header RecyclerView of the TableView needs a new RecyclerView.ViewHolder of the given
     * type to represent an item.
     *
     * @param viewType : This value comes from "getColumnHeaderItemViewType" method to support
     *                 different type of viewHolder as a Column Header item.
     * @see #getColumnHeaderItemViewType(int);
     */
    @NonNull
    @Override
    public AbstractViewHolder onCreateColumnHeaderViewHolder(@NonNull ViewGroup parent, int viewType) {
        // TODO: check
        //Log.e(LOG_TAG, " onCreateColumnHeaderViewHolder has been called");
        // Get Column Header xml Layout
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_column_header_layout, parent, false);

        // Create a ColumnHeader ViewHolder
        return new ColumnHeaderViewHolder(layout, getTableView());
    }

    /**
     * That is where you set Column Header View Model data to your custom Column Header ViewHolder.
     * This method is Called by ColumnHeader RecyclerView of the TableView to display the data at
     * the specified position. This method gives you everything you need about a column header
     * item.
     *
     * @param holder                : This is one of your column header ViewHolders that was created
     *                              on ```onCreateColumnHeaderViewHolder``` method. In this example
     *                              we have created "ColumnHeaderViewHolder" holder.
     * @param columnHeaderItemModel : This is the column header view model located on this X
     *                              position. In this example, the model class is "ColumnHeader".
     * @param columnPosition        : This is the X (Column) position of the column header item.
     * @see #onCreateColumnHeaderViewHolder(ViewGroup, int) ;
     */
    @Override
    public void onBindColumnHeaderViewHolder(@NonNull AbstractViewHolder holder, @Nullable ColumnHeader
            columnHeaderItemModel, int columnPosition) {

        // Get the holder to update cell item text
        ColumnHeaderViewHolder columnHeaderViewHolder = (ColumnHeaderViewHolder) holder;
        columnHeaderViewHolder.setColumnHeader(columnHeaderItemModel);
    }

    /**
     * This is where you create your custom Row Header ViewHolder. This method is called when
     * Row Header RecyclerView of the TableView needs a new RecyclerView.ViewHolder of the given
     * type to represent an item.
     *
     * @param viewType : This value comes from "getRowHeaderItemViewType" method to support
     *                 different type of viewHolder as a row Header item.
     * @see #getRowHeaderItemViewType(int);
     */
    @NonNull
    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Get Row Header xml Layout
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_row_header_layout, parent, false);

        // Create a Row Header ViewHolder
        return new RowHeaderViewHolder(layout);
    }


    /**
     * That is where you set Row Header View Model data to your custom Row Header ViewHolder. This
     * method is Called by RowHeader RecyclerView of the TableView to display the data at the
     * specified position. This method gives you everything you need about a row header item.
     *
     * @param holder             : This is one of your row header ViewHolders that was created on
     *                           ```onCreateRowHeaderViewHolder``` method. In this example we have
     *                           created "RowHeaderViewHolder" holder.
     * @param rowHeaderItemModel : This is the row header view model located on this Y position. In
     *                           this example, the model class is "RowHeader".
     * @param rowPosition        : This is the Y (row) position of the row header item.
     * @see #onCreateRowHeaderViewHolder(ViewGroup, int) ;
     */

    public static Map<Integer, Boolean> bindData = new HashMap<>();


    @Override
    public void onBindRowHeaderViewHolder(@NonNull AbstractViewHolder holder, @Nullable RowHeader rowHeaderItemModel,
                                          final int rowPosition) {

        // Get the holder to update row header item text
        RowHeaderViewHolder rowHeaderViewHolder = (RowHeaderViewHolder) holder;
        rowHeaderViewHolder.row_header_textview.setText(String.valueOf(rowHeaderItemModel.getData()));

        //TEST -- this is still a way but... we did what we did, in TableViewListener, onRowHeaderClicked.
        //animateCheckBoxList.add(rowHeaderViewHolder.anim_checkbox);

        rowHeaderViewHolder.anim_checkbox.setOnCheckedChangeListener(new AnimateCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, final boolean isChecked) {

                Log.d(context.getPackageName().toUpperCase(), "Row Position" +
                        " " + isChecked + " " + rowPosition);


                if(bindData.containsKey(rowPosition)) {
                    bindData.remove(rowPosition);
                }else{
                    bindData.put(rowPosition, isChecked);
                }
                Log.d(context.getPackageName().toUpperCase(), "Map Content: " + TableViewAdapter.bindData.toString());

                checkoutOrderCountText.setText(String.valueOf(bindData.size()));
                checkoutCountTextBottomSheet.setText(String.valueOf(bindData.size()));
                bounce(checkoutOrderCountText);
                bounce(checkoutCountTextBottomSheet);
            }
        });

    }

    public void bounce(View view){
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_bounce));
    }


    @NonNull
    @Override
    public View onCreateCornerView(@NonNull ViewGroup parent) {
        // Get Corner xml layout
        View corner = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_corner_layout, parent, false);
        corner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortState sortState = TableViewAdapter.this.getTableView()
                        .getRowHeaderSortingStatus();
                if (sortState != SortState.ASCENDING) {
                    Log.d("TableViewAdapter", "Order Ascending");
                    TableViewAdapter.this.getTableView().sortRowHeader(SortState.ASCENDING);
                } else {
                    Log.d("TableViewAdapter", "Order Descending");
                    TableViewAdapter.this.getTableView().sortRowHeader(SortState.DESCENDING);
                }
            }
        });
        return corner;
    }

    @Override
    public int getColumnHeaderItemViewType(int position) {
        // The unique ID for this type of column header item
        // If you have different items for Cell View by X (Column) position,
        // then you should fill this method to be able create different
        // type of CellViewHolder on "onCreateCellViewHolder"
        return 0;
    }

    @Override
    public int getRowHeaderItemViewType(int position) {
        // The unique ID for this type of row header item
        // If you have different items for Row Header View by Y (Row) position,
        // then you should fill this method to be able create different
        // type of RowHeaderViewHolder on "onCreateRowHeaderViewHolder"
        return 0;
    }

    @Override
    public int getCellItemViewType(int column) {
        // The unique ID for this type of cell item
        // If you have different items for Cell View by X (Column) position,
        // then you should fill this method to be able create different
        // type of CellViewHolder on "onCreateCellViewHolder"
        switch (column) {
//            case TableViewModel.MOOD_COLUMN_INDEX:
//                return MOOD_CELL_TYPE;
//            case TableViewModel.GENDER_COLUMN_INDEX:
//                return GENDER_CELL_TYPE;
            case TableViewModel.ORDER_QUANTITY_CELL_TYPE:
                return ORDER_QUANTITY_CELL_TYPE;
            default:
                // Default view type
                return 0;
        }
    }
}
