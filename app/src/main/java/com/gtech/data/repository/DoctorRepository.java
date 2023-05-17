package com.gtech.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.gtech.data.database.DoctorDao;
import com.gtech.data.database.DoctorDatabase;
import com.gtech.data.model.Doctor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DoctorRepository {
    private DoctorDao doctorDao;
    private LiveData<List<Doctor>> doctorList;

    // Create ExecutorService to allow performing data operations on separate thread asynchronously
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public DoctorRepository(Application application) {
        DoctorDatabase database = DoctorDatabase.getInstance(application);
        doctorDao = database.doctorDao();
        doctorList = doctorDao.getAllDoctors();
    }

    public void insert(Doctor doctor) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                doctorDao.insert(doctor);
            }
        });
    }
    public void delete(Doctor doctor) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                doctorDao.delete(doctor);
            }
        });
    }
    public void update(Doctor doctor) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                doctorDao.update(doctor);
            }
        });
    }
    public LiveData<List<Doctor>> getAllDoctors() {
        return doctorList;
    }
}
