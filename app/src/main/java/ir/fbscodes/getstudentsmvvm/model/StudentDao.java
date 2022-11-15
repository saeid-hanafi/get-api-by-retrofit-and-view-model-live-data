package ir.fbscodes.getstudentsmvvm.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StudentDao {
    @Query("SELECT * FROM `students` WHERE 1 ORDER BY `id` DESC")
    LiveData<List<Student>> getStudents();

    @Query("DELETE FROM `students` WHERE 1")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Student> students);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addStudent(Student student);
}
