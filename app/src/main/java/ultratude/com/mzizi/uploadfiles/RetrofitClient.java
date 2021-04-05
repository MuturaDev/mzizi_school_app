//package ultratude.com.mzizi.uploadfiles;
//
//import android.support.annotation.NonNull;
//
//import java.io.IOException;
//
//import okhttp3.HttpUrl;
//import okhttp3.Interceptor;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import okhttp3.logging.HttpLoggingInterceptor;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class RetrofitClient {
//    private static Retrofit retrofit = null;
//
//    public static Retrofit getClient(String baseUrl, Interceptor interceptor) {
//        if(retrofit == null) {
//            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            builder.networkInterceptors().add(httpLoggingInterceptor);
//            builder.interceptors().add(interceptor);
//            OkHttpClient client = builder.build();
//
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(baseUrl)
//                    // AnnotationStrategy avoids SimpleXml crashes from empty tags
//                    //.addConverterFactory(SimpleXmlConverterFactory.createNonStrict(new Persister(new AnnotationStrategy())))
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(client)
//                    .build();
//        }
//        return retrofit;
//    }
//}
//
//
//public class ApiKeyInterceptor implements Interceptor {
//
//    private String mApiKey;
//
//    ApiKeyInterceptor(String apiKey) {
//        mApiKey = apiKey;
//    }
//
//    @Override
//    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
//        HttpUrl url = chain.request().url()
//                .newBuilder()
//                .addQueryParameter("key", mApiKey)
//                .build();
//        Request request = chain.request().newBuilder().url(url).build();
//        return chain.proceed(request);
//    }
//}
//
//public class ApiUtils {
//    //private static final String API_KEY = [KEY HERE]";
//    private static final String BASE_URL = "http://example.api.com";
//
//    public static RetrofitService getService() {
//        return RetrofitClient.getClient(BASE_URL, new ApiKeyInterceptor("")).create(RetrofitService.class);
//    }
//}
