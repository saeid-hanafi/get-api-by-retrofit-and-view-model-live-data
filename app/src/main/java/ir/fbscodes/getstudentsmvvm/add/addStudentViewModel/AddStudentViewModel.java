package ir.fbscodes.getstudentsmvvm.add.addStudentViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.core.Completable;
import ir.fbscodes.getstudentsmvvm.model.StudentsRepository;

public class AddStudentViewModel extends ViewModel {
    private StudentsRepository repository;
    private MutableLiveData<Boolean> addStudentProgressBar = new MutableLiveData<>();

    public AddStudentViewModel(StudentsRepository repository) {
        this.repository = repository;
    }

    public Completable saveStudent(String firstName, String lastName, String mail, String avatar) {
        addStudentProgressBar.postValue(true);
        return repository.save(firstName, lastName, mail, avatar).doFinally(() -> addStudentProgressBar.postValue(false)).ignoreElement();
    }

    public LiveData<Boolean> getAddStudentProgressBar() {
        return addStudentProgressBar;
    }
}
