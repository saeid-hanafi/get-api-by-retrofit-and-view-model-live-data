package ir.fbscodes.getstudentsmvvm.add;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ir.fbscodes.getstudentsmvvm.R;
import ir.fbscodes.getstudentsmvvm.add.addStudentViewModel.AddStudentViewModel;
import ir.fbscodes.getstudentsmvvm.main.mainViewModels.MyViewModelFactory;
import ir.fbscodes.getstudentsmvvm.model.ApiServiceProvider;
import ir.fbscodes.getstudentsmvvm.model.StudentsDatabase;
import ir.fbscodes.getstudentsmvvm.model.StudentsRepository;

public class AddNewStudentActivity extends AppCompatActivity {
    private static final String TAG = "AddNewStudentActivity";
    private Disposable disposable;
    private AddStudentViewModel addStudentViewModel;
    private LottieAnimationView lottieAnimationView;
    private NestedScrollView nestedScrollView;
    private ExtendedFloatingActionButton fabSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_student);

        Toolbar toolbar = findViewById(R.id.add_new_student_toolbar);
        setSupportActionBar(toolbar);

        addStudentViewModel = new ViewModelProvider(this, new MyViewModelFactory(new StudentsRepository(StudentsDatabase.getInstance(this).getStudentDao(), ApiServiceProvider.getApiService())))
                .get(AddStudentViewModel.class);

        lottieAnimationView = findViewById(R.id.progress_bar_add_new_student);
        nestedScrollView = findViewById(R.id.add_new_student_scroll);
        fabSave = findViewById(R.id.fab_addNewStudent_save);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        TextInputEditText firstNameEt = findViewById(R.id.add_new_student_firstname_text);
        TextInputEditText lastNameEt = findViewById(R.id.add_new_student_lastname_text);
        TextInputEditText emailEt = findViewById(R.id.add_new_student_email_text);
        TextInputEditText avatarEt = findViewById(R.id.add_new_student_avatar_text);

        fabSave.setOnClickListener(v -> {
            addStudentViewModel.getAddStudentProgressBar().observe(this, status -> {
                nestedScrollView.setVisibility(status?View.GONE:View.VISIBLE);
                fabSave.setVisibility(status?View.GONE:View.VISIBLE);
                lottieAnimationView.setVisibility(status?View.VISIBLE:View.GONE);
            });

            if (firstNameEt != null && lastNameEt != null &&
                emailEt != null && avatarEt != null) {
                if (firstNameEt.length() > 0 && lastNameEt.length() > 0 &&
                    emailEt.length() > 0 && avatarEt.length() > 0) {
                    String firstName = firstNameEt.getText().toString();
                    String lastName = lastNameEt.getText().toString();
                    String email = emailEt.getText().toString();
                    String avatar = avatarEt.getText().toString();

                    addStudentViewModel.saveStudent(firstName, lastName, email, avatar)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new CompletableObserver() {
                                @Override
                                public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                                    disposable = d;
                                }

                                @Override
                                public void onComplete() {
                                    finish();
                                }

                                @Override
                                public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                    Log.e(TAG, "onError: "+e.getMessage());
                                }
                            });
                }else{
                    Snackbar.make(fabSave, "All Fields Is Require2!", Snackbar.LENGTH_SHORT).show();
                }
            }else{
                Snackbar.make(fabSave, "All Fields Is Require1!", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!disposable.isDisposed())
            disposable.dispose();
    }
}