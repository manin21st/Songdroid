package fwk;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConnection {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://118.38.159.9/android_pda/")
            .addConverterFactory(GsonConverterFactory.create())
//            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    public RetrofitInterface server = retrofit.create(RetrofitInterface.class);

}