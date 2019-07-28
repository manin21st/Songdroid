package fwk;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @GET("pda_login.php")
    Call<ResponseBody> get_data();

    @FormUrlEncoded
    @POST("RunSql.php")
    Call<ResponseBody> RunSql(@Field("sqlID") String sqlID, @Field("sqlTEXT") String sqlTEXT);


//    public static final Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("http://118.38.159.9/")
////            .addConverterFactory(GsonConverterFactory.create())
//            .addConverterFactory(ScalarsConverterFactory.create())
//            .build();

}
