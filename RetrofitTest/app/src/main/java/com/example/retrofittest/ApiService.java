package com.example.retrofittest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    public static final String API_URL = "http://jsonplaceholder.typicode.com/";
    public static final String API_URL2 = "http://118.38.159.9/android_pda/";

    // Int version
    @GET("comments")
    Call<ResponseBody> getComment(@Query("postId") int postId);

    @POST("comments")
    Call<ResponseBody> getPostComment(@Query("postId") int postId);

    // String version
    @GET("comments")
    Call<ResponseBody> getCommentStr(@Query("postId") String postId);

    @FormUrlEncoded
    @POST("comments")
    Call<ResponseBody> getPostCommentStr(@Field("postId") String postId);

    @FormUrlEncoded
    @POST("RunSql.php")
    Call<ResponseBody> RunSql(@Field("sqlID") String sqlID, @Field("sqlTEXT") String sqlTEXT);
}
