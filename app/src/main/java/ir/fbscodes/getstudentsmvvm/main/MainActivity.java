package ir.fbscodes.getstudentsmvvm.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonObject;

import java.util.List;

import ir.fbscodes.getstudentsmvvm.R;
import ir.fbscodes.getstudentsmvvm.main.mainViewModels.MainViewModel;
import ir.fbscodes.getstudentsmvvm.main.mainViewModels.MainViewModelFactory;
import ir.fbscodes.getstudentsmvvm.model.ApiServiceProvider;
import ir.fbscodes.getstudentsmvvm.model.Student;
import ir.fbscodes.getstudentsmvvm.model.StudentsDatabase;
import ir.fbscodes.getstudentsmvvm.model.StudentsRepository;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainViewModel mainViewModel = new ViewModelProvider(this, new MainViewModelFactory(new StudentsRepository(StudentsDatabase.getInstance(getApplicationContext()).getStudentDao(), ApiServiceProvider.getApiService())))
                .get(MainViewModel.class);
        mainViewModel.getUsersLiveData().observe(this, students -> {
            Log.i(TAG, "onCreate: ");
        });

        mainViewModel.getError().observe(this, error -> {
            Log.e(TAG, "onCreate: " + error);
        });
    }
}