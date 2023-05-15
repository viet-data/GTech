package com.gtech.data.source;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.gtech.domain.models.Doctor;

import java.util.List;

// Sample Data Access Object
@Dao
public interface DoctorDao {
    @Insert
    void insert(Doctor doctor);

    @Delete
    void delete(Doctor doctor);

    @Update
    void update(Doctor doctor);

    @Query("SELECT * FROM doctor ORDER BY doctorId ASC")
    LiveData<List<Doctor>> getAllDoctors();
}
