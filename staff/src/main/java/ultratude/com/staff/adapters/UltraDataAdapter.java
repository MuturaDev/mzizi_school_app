package ultratude.com.staff.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


import java.util.List;

import ultratude.com.staff.R;
import ultratude.com.staff.webservice.DataAccessObjects.TransportStudentsDao;
import ultratude.com.staff.webservice.ResponseModels.TransportStudents;
import ultratude.com.staff.webservice.Database.ConnSQLiteContract;

/**
 * Created by Admin on 3/31/2018.
 */

public class UltraDataAdapter extends CursorAdapter {



    public UltraDataAdapter(Context context, Cursor data){
        super(context, data,0);


    }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.transport_listitem_layout,parent,false);

    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String studentFullName = "";
        String registrationNo = "";
        String classStream = "";
        String barcode = "";
        boolean statusSent = false;



        List<TransportStudents> transportStudentsList = new TransportStudentsDao(context).getAllStudents();

        StringBuilder sb = new StringBuilder();

        //for getting specific student name using student barcode
        for(int i = 0; i< transportStudentsList.size(); i++){
            String scannedBarcode = cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.UltraDataDef.STUDENTID_BARCODE));
            String storedBarcode = transportStudentsList.get(i).getBarcode();



            sb.append(storedBarcode);
            sb.append(",\n");

           // setTitle(String.valueOf(storedBarcode + "  " + scannedBarcode));

            if(scannedBarcode.equalsIgnoreCase(storedBarcode)){


                studentFullName = transportStudentsList.get(i).getStudentFullName();
                registrationNo = transportStudentsList.get(i).getRegistrationNumber();
                classStream = transportStudentsList.get(i).getClassStream();
                barcode = scannedBarcode;
                statusSent = cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.UltraDataDef.STATUS_SENT)).equals("true");
                //  setTitle(String.valueOf(storedBarcode + "  " + scannedBarcode));

                break;
            }

        }


//        AlertDialog.Builder al = new AlertDialog.Builder(this);
//        al.setCancelable(false);
//        al.setMessage(sb.toString());
//        al.show();



        //Toast.makeText(this, String.valueOf(list.size()), Toast.LENGTH_LONG).show();



        if(studentFullName != ""){
            view.findViewById(R.id.valid).setVisibility(View.VISIBLE);
            view.findViewById(R.id.invalid).setVisibility(View.INVISIBLE);

            ((TextView) view.findViewById(R.id.latitude_value)).setText(registrationNo);
            ((TextView) view.findViewById(R.id.longitude_value)).setText(classStream);
            ((TextView) view.findViewById(R.id.student_name)).setText(studentFullName);
            ((TextView) view.findViewById(R.id.school_bus)).setText(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.UltraDataDef.BUS_ACTIVITY)));

            if(statusSent){
                ((TextView) view.findViewById(R.id.sent_status)).setText("SENT");
                ((TextView) view.findViewById(R.id.sent_status)).setBackgroundResource(android.R.color.holo_green_dark);

            }else{
                ((TextView) view.findViewById(R.id.sent_status)).setText("NOT SENT");
                ((TextView) view.findViewById(R.id.sent_status)).setBackgroundResource(android.R.color.holo_red_dark);


            }

        }else{

            view.findViewById(R.id.valid).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.invalid).setVisibility(View.VISIBLE);
            ((TextView)view.findViewById(R.id.invalid)).setText("Student (" + cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.UltraDataDef.STUDENTID_BARCODE)) + " ) not on transport");
        }




    }
}
