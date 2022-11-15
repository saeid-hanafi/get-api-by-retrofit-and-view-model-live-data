package ir.fbscodes.getstudentsmvvm.model;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

public class StudentsRepository {
    private static final String TAG = "StudentsRepository";
    private StudentDao studentDao;
    private ApiService apiService;

    public StudentsRepository(StudentDao studentDao, ApiService apiService) {
        this.studentDao = studentDao;
        this.apiService = apiService;
    }

    public Completable refreshStudents() {
        return apiService.getStudents(1).doOnSuccess(jsonObject -> {
            try {
                Gson gson = new Gson();
                if (jsonObject.has("total") && jsonObject.has("data")) {
                    JsonArray studentsData = jsonObject.getAsJsonArray("data");
                    if (studentsData.size() > 0) {
                        List<Student> students = gson.fromJson(studentsData.toString(), new TypeToken<List<Student>>() {}.getType());
                        studentDao.insertAll(students);
                    }else{
                        Log.e(TAG, "onResponse: Total Or Student Data Is Zero!");
                    }
                }else{
                    Log.e(TAG, "onResponse: Total Or Student Data Not Fount!");
                }
            } catch (JsonParseException e) {
                e.printStackTrace();
            }
        }).ignoreElement();
    }

    public LiveData<List<Student>> getStudents() {
        return studentDao.getStudents();
    }
}
