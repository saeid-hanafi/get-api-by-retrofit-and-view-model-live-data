package ir.fbscodes.getstudentsmvvm.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StudentDao {
    @Query("SELECT * FROM `students`")
    LiveData<List<Student>> getStudents();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Student> students);
}
