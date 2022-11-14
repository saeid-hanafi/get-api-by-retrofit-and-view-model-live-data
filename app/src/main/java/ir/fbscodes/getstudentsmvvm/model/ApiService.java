package ir.fbscodes.getstudentsmvvm.model;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/users/")
    @Headers("Accept:application/json")
    Call<JsonObject> getStudents(@Query("page") int page);
}
