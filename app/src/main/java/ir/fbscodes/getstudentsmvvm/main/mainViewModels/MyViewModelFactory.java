package ir.fbscodes.getstudentsmvvm.main.mainViewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ir.fbscodes.getstudentsmvvm.add.addStudentViewModel.AddStudentViewModel;
import ir.fbscodes.getstudentsmvvm.model.StudentsRepository;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    private StudentsRepository repository;

    public MyViewModelFactory(StudentsRepository studentsRepository) {
        repository = studentsRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class))
            return (T) new MainViewModel(repository);
        else if (modelClass.isAssignableFrom(AddStudentViewModel.class))
            return (T) new AddStudentViewModel(repository);
        else
            throw new IllegalArgumentException("Arguments is not valid!");
    }
}
