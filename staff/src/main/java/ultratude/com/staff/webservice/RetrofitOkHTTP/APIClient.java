package ultratude.com.staff.webservice.RetrofitOkHTTP;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by James on 22/09/2018.
 */

 public class APIClient {

    //retrofit provides with it, a list of annotations for each of the HTTP methods: @GET, @POST, @DELETE, @PATCH or @HEAD
    private static Retrofit retrofit;

        //this method will be calle every time while setting up retrofit interface.
    public static Retrofit getClient(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(2000, TimeUnit.SECONDS)

                .connectTimeout(2000, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();
            //https://android.mzizi.co.ke/api/FilteredPortalStudentInfo
        retrofit = new Retrofit.Builder()
                //.baseUrl("https://android.mzizi.co.ke")
                .baseUrl(UrlEndpoints.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
        }
}
