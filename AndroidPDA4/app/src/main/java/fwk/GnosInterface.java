package fwk;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GnosInterface {
    public static final String BASE_URL = "http://118.38.159.9/android_pda/";
    public static final String TEST_URL = "http://jsonplaceholder.typicode.com/";

    // TEST_URL
    @GET("comments")
    Call<ResponseBody> getComment(@Query("postId") int postId);
    // TEST_URL
    @FormUrlEncoded
    @POST("comments")
    Call<ResponseBody> getPostCommentStr(@Field("postId") String postId);


    // BASE_URL
    @FormUrlEncoded
    @POST("RunSql.php")
    Call<ResponseBody> RunSql(@Field("sqlID") String sqlID, @Field("sqlTEXT") String sqlTEXT);
}
