package fwk;

import org.json.JSONArray;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;

public interface RetrofitInterface {

    @GET("android_pda/pda_login.php")
    Call<JSONArray> get_data();


//    public static final Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("http://118.38.159.9/")
////            .addConverterFactory(GsonConverterFactory.create())
//            .addConverterFactory(ScalarsConverterFactory.create())
//            .build();

}
