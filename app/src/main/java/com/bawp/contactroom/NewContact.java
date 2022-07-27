package com.bawp.contactroom;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bawp.contactroom.model.Contact;
import com.bawp.contactroom.model.ContactViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class NewContact extends AppCompatActivity {
    public static final String NAME_REPLY = "name_reply";
    public static final String OCCUPATION = "occupation";
    private EditText enterName;
    private EditText enterOccupation;
    private Button saveInfoButton;
    private int contactId = 0;
    private Boolean isEdit = false;
    private Button updateButton;
    private Button deleteButton;

    private ContactViewModel contactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        enterName = findViewById(R.id.enter_name);
        enterOccupation = findViewById(R.id.enter_occupation);
        saveInfoButton = findViewById(R.id.save_button);

        contactViewModel = new ViewModelProvider.AndroidViewModelFactory(NewContact.this
                .getApplication())
                .create(ContactViewModel.class);

        if (getIntent().hasExtra(MainActivity.CONTACT_ID)) {
            contactId = getIntent().getIntExtra(MainActivity.CONTACT_ID, 0);

            contactViewModel.get(contactId).observe(this, contact -> {
                if (contact != null) {
                    enterOccupation.setText(contact.getOccupation());
                    enterName.setText(contact.getName());
                }
            });
            isEdit = true;

        }


        saveInfoButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();

            if (!TextUtils.isEmpty(enterName.getText())
                    && !TextUtils.isEmpty(enterOccupation.getText())) {
                String name = enterName.getText().toString().trim();
                String occupation = enterOccupation.getText().toString().trim();

                replyIntent.putExtra(NAME_REPLY, name);
                replyIntent.putExtra(OCCUPATION, occupation);
                Toast.makeText(this, "SAVED", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, replyIntent);
            }
            finish();

        });

        deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(view -> edit(true));
        //Update button
        updateButton = findViewById(R.id.update_button);
        updateButton.setOnClickListener(view -> edit(false));


        if (isEdit) {
            saveInfoButton.setVisibility(View.GONE);
        } else {
            updateButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
        }

    }

    private void edit(Boolean isDelete) {
        String name = enterName.getText().toString().trim();
        String occupation = enterOccupation.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(occupation)) {
            Snackbar.make(enterName, R.string.empty, Snackbar.LENGTH_SHORT)
                    .show();
        } else {
            Contact contact = new Contact();
            contact.setId(contactId);
            contact.setName(name);
            contact.setOccupation(occupation);
            if (isDelete) {
                ContactViewModel.delete(contact);
                Toast.makeText(this, "DELETED", Toast.LENGTH_SHORT).show();
            } else {
                ContactViewModel.update(contact);
                Toast.makeText(this, "UPDATED", Toast.LENGTH_SHORT).show();
            }
            finish();

        }
    }
}