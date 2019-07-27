package fwk;

import org.json.JSONArray;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitInterface {

    @GET("something/")
    Call<JSONArray> get_data();

}
