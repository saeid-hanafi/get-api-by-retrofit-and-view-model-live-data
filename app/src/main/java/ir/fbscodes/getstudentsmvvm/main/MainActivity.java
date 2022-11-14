package ir.fbscodes.getstudentsmvvm.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonObject;

import ir.fbscodes.getstudentsmvvm.R;
import ir.fbscodes.getstudentsmvvm.main.mainViewModels.MainViewModel;
import ir.fbscodes.getstudentsmvvm.main.mainViewModels.MainViewModelFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainViewModel mainViewModel = new ViewModelProvider(this, new MainViewModelFactory()).get(MainViewModel.class);
        mainViewModel.getUsersLiveData().observe(this, new Observer<JsonObject>() {
            @Override
            public void onChanged(JsonObject jsonObject) {
                Log.i(TAG, "onChanged: ");
            }
        });
        
        mainViewModel.getErrorLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e(TAG, "onChanged: ");
            }
        });
    }
}