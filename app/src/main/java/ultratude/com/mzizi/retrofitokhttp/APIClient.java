package ultratude.com.mzizi.retrofitokhttp;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;
import ultratude.com.mzizi.webservice.mziziapiconstantsurl.MziziURLConstants;

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

        String baseurl = Paper.book().read(Constants.PORTAL_URL_ENABLED) == null ?
                "https://androidv2.mzizi.co.ke/"
                            :
                Paper.book().read(Constants.PORTAL_URL_ENABLED).toString();

        //baseurl = baseurl.replace("http://goo.gl/Jqko8Z", "https://androidv2.mzizi.co.ke");

        baseurl = baseurl + "/";

            //https://android.mzizi.co.ke/api/FilteredPortalStudentInfo
        retrofit = new Retrofit.Builder()
                //.baseUrl("https://android.mzizi.co.ke")
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
}
