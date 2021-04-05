package ultratude.com.staff.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ultratude.com.staff.expandablelist.ChildInfo;
import ultratude.com.staff.expandablelist.GroupInfo;
import ultratude.com.staff.R;

/**
 * Created by James on 12/05/2019.
 */

public class CustomAdapter extends BaseExpandableListAdapter{

    private Context mContext;
    private ArrayList<GroupInfo> depList;

    public CustomAdapter(Context context, ArrayList<GroupInfo> depList){
        this.mContext = context;
        this.depList = depList;
    }


    //CHILD
    @Override
    public Object getChild(int groupPosition, int childPosition) {

        ArrayList<ChildInfo> productList = depList.get(groupPosition).getChildInfoList();
        return productList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildInfo detailInfo = (ChildInfo) getChild(groupPosition, childPosition);


        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.ex_child_item, null);
        }

        TextView txt_leavetypename_ID = convertView.findViewById(R.id.txt_leavetypename_ID);
        TextView txt_days_entitled_ID = convertView.findViewById(R.id.txt_days_entitled_ID);
        TextView txt_leave_balance_ID = convertView.findViewById(R.id.txt_leave_balance_ID);
        txt_leavetypename_ID.setText(detailInfo.getLeaveTypeName());
        txt_days_entitled_ID.setText(detailInfo.getDaysEntitled());
        txt_leave_balance_ID.setText(detailInfo.getLeaveBalance());

        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return depList.get(groupPosition).getChildInfoList().size();
    }


    //GROUP
    @Override
    public Object getGroup(int groupPosition) {
        return depList.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        GroupInfo headerInfo = (GroupInfo) getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.ex_group_item, null);
        }

        TextView heading =  convertView.findViewById(R.id.group);
        heading.setText(headerInfo.getEntitlement().trim());
        return convertView;
    }


    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public int getGroupCount() {
        return depList.size();
    }
}
