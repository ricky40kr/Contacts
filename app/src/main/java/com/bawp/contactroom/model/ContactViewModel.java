package com.bawp.contactroom.model;


import android.app.Application;
import android.app.ListActivity;

import com.bawp.contactroom.data.ContactRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ContactViewModel extends AndroidViewModel {

    public static ContactRepository repository;
    public final LiveData<List<Contact>> allContacts;


    public ContactViewModel(@NonNull Application application) {
        super(application);
        repository = new ContactRepository(application);
        allContacts = repository.getAllData();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    public static void insert(Contact contact) {
        repository.insert(contact);
    }

    public LiveData<Contact> get(int id) {
        return repository.get(id);
    }

    public static void update(Contact contact) {
        repository.update(contact);
    }

    public static void delete(Contact contact) {
        repository.delete(contact);
    }

    public static void deleteAll() {
        repository.deleteAll();
    }
}
