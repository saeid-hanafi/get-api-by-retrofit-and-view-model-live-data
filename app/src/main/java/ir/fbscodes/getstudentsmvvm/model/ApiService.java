package ir.fbscodes.getstudentsmvvm.model;

import com.google.gson.JsonObject;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/users/")
    @Headers("Accept:application/json")
    Single<JsonObject> getStudents(@Query("page") int page);

    @POST("api/users/")
    Single<Student> addStudent(@Body JsonObject body);
}
