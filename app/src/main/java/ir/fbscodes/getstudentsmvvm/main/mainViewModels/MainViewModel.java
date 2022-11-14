package ir.fbscodes.getstudentsmvvm.main.mainViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;

import ir.fbscodes.getstudentsmvvm.model.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private ApiService apiService;
    private MutableLiveData<JsonObject> usersLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public MainViewModel(ApiService apiService) {
        this.apiService = apiService;
        apiService.getStudents(1).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                usersLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                errorLiveData.setValue(t.getMessage());
            }
        });
    }

    public LiveData<JsonObject> getUsersLiveData() {
        return usersLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }
}
