package com.example.abc.myapplication18;


        import okhttp3.ResponseBody;
        import retrofit2.Call;
        import retrofit2.http.GET;

public interface JsonService {

    @GET("get_data.json")
    Call<ResponseBody> getJsonList();
}
