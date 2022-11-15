package ir.fbscodes.getstudentsmvvm.main.mainViewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ir.fbscodes.getstudentsmvvm.model.ApiService;
import ir.fbscodes.getstudentsmvvm.model.StudentsRepository;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    private StudentsRepository repository;

    public MainViewModelFactory(StudentsRepository studentsRepository) {
        repository = studentsRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainViewModel(repository);
    }
}
