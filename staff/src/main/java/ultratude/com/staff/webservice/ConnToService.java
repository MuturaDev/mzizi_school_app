package ultratude.com.staff.webservice;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.staff.webservice.ResponseModels.Staff;
import ultratude.com.staff.webservice.DataAccessObjects.StaffDao;
import ultratude.com.staff.webservice.ResponseModels.UltraData;
import ultratude.com.staff.webservice.DataAccessObjects.UltraDataDao;
import ultratude.com.staff.webservice.RetrofitOkHTTP.APIClient;
import ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface;
import ultratude.com.staff.webservice.RetrofitOkHTTP.Models.UltradataRequest;

public class ConnToService {

    Context context;

    public ConnToService(Context context){
        this.context = context;
    }

    public boolean internetConnection(Context context) {
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((mobile.getState() == NetworkInfo.State.CONNECTED) || wifi.getState() == NetworkInfo.State.CONNECTED) {


            return true;//connected

        }
        return false;
    }


    public void sendDataToAPI(){


        if (internetConnection(context)) {


            //REMEMBER THIS IS NOT WORKING BECAUSE YOU HAVE NOT HOSTED THE API TO CLOUD
            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.


//                                    //request to PortalStudentInfo


            List<Object> list = new UltraDataDao(context).getUltraDataList();
            if(list.size() > 0) {

                List<UltraData> data = (List<UltraData>) list.get(0);
                final int[] ultraIDs = (int[]) list.get(1);

                Staff staff = new StaffDao(context).getUserThatSignedUp();
                if (staff != null) {


//                public String StudentID_Barcode;
//                public String latitude ;
//                public String longitude ;
//                public String bus_Activity;
//                public String Appcode ;


                    List<UltradataRequest> requestdata = new ArrayList<>();
                    for (UltraData ultradata : data) {
                        UltradataRequest datarequest = new UltradataRequest();
                        datarequest.setAppcode(staff.getAppcode());
                        datarequest.setBus_Activity(ultradata.getBus_Activity());
                        datarequest.setLatitude(String.valueOf(ultradata.getLatitude()));
                        datarequest.setLongitude(String.valueOf(ultradata.getLongitude()));
                        datarequest.setStudentID_Barcode(ultradata.getBarcodeValue());
                        datarequest.setStaff_ID(staff.getStaff_ID());

                        requestdata.add(datarequest);
                    }


                    Call<String> userCall = apiInterface.sendUltraData(requestdata);
                    userCall.enqueue(new Callback<String>() {
                                         @Override
                                         public void onResponse(Call<String> call, Response<String> response) {
                                             if (response.isSuccessful()) {
                                                 if (response.body() == "true") {

                                                     //now you can delete all data
                                                     new UltraDataDao(context).updateRecordToSent(ultraIDs);

                                                 } else if (response.body() == "false") {

                                                 } else {

                                                 }
                                             } else {
                                                 //you dont have to delete all the data
                                             }

                                         }

                                         @Override
                                         public void onFailure(Call<String> call, Throwable t) {

                                             Toast.makeText(context, "Oops Check your internnect connection", Toast.LENGTH_SHORT).show();

                                         }
                                     }


                    );

                }
            }



        }



    }



    //this will bold all the classes of oKhTTP


}
