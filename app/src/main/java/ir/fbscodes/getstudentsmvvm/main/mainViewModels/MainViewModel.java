package ir.fbscodes.getstudentsmvvm.main.mainViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ir.fbscodes.getstudentsmvvm.model.ApiService;
import ir.fbscodes.getstudentsmvvm.model.Student;
import ir.fbscodes.getstudentsmvvm.model.StudentsRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private StudentsRepository repository;
    private MutableLiveData<String> error = new MutableLiveData<>();
    private Disposable disposable;

    public MainViewModel(StudentsRepository repository) {
        this.repository = repository;
        repository.refreshStudents()
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        error.postValue(e.getMessage());
                    }
                });
    }

    public LiveData<List<Student>> getUsersLiveData() {
        return repository.getStudents();
    }

    public LiveData<String> getError() {
        return error;
    }

    @Override
    protected void onCleared() {
        if (!disposable.isDisposed())
            disposable.dispose();
        super.onCleared();
    }
}
