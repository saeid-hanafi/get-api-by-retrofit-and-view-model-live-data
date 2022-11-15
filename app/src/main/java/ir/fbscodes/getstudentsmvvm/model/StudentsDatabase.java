package ir.fbscodes.getstudentsmvvm.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Student.class, exportSchema = false, version = 1)
public abstract class StudentsDatabase extends RoomDatabase {
    private static StudentsDatabase studentsDatabase;

    public static StudentsDatabase getInstance(Context context) {
        if (studentsDatabase == null)
            studentsDatabase = Room.databaseBuilder(context, StudentsDatabase.class, "db_students").allowMainThreadQueries().build();
        return studentsDatabase;
    }

    public abstract StudentDao getStudentDao();
}
