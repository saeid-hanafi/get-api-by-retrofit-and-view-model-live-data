package ir.fbscodes.getstudentsmvvm.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import ir.fbscodes.getstudentsmvvm.R;
import ir.fbscodes.getstudentsmvvm.add.AddNewStudentActivity;
import ir.fbscodes.getstudentsmvvm.main.mainViewModels.MainViewModel;
import ir.fbscodes.getstudentsmvvm.main.mainViewModels.MyViewModelFactory;
import ir.fbscodes.getstudentsmvvm.model.ApiServiceProvider;
import ir.fbscodes.getstudentsmvvm.model.StudentsDatabase;
import ir.fbscodes.getstudentsmvvm.model.StudentsRepository;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        ExtendedFloatingActionButton fabMain = findViewById(R.id.fab_main);
        fabMain.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddNewStudentActivity.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        MainViewModel mainViewModel = new ViewModelProvider(this, new MyViewModelFactory(new StudentsRepository(StudentsDatabase.getInstance(getApplicationContext()).getStudentDao(), ApiServiceProvider.getApiService())))
                .get(MainViewModel.class);
        mainViewModel.getUsersLiveData().observe(this, students -> {
            studentAdapter = new StudentAdapter(students);
            recyclerView.setAdapter(studentAdapter);
        });

        mainViewModel.getError().observe(this, error -> {
            Log.e(TAG, "onCreate: " + error);
        });

        mainViewModel.getProgressBarStatus().observe(this, mustShow -> {
            findViewById(R.id.progress_bar_main).setVisibility(mustShow? View.VISIBLE: View.GONE);
            findViewById(R.id.fab_main).setVisibility(mustShow? View.GONE: View.VISIBLE);
        });
    }
}