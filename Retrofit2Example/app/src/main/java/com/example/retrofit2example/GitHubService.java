package com.example.retrofit2example;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GitHubService {
    @GET("/users/{ID}")
    Call<User> getRepos(@Path("ID") String ID);

    @POST("/users/{ID}")
    Call<User> postRepos(@Path("ID") String ID, @Body User user);
}
