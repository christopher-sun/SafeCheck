package cddj.gatech.edu.safecheck;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

/**
 * Created by juliachen on 10/14/17.
 */

public class LoadContactsActivity extends AppCompatActivity {

    private ArrayList<String> phoneNums;
    private String userPhoneNum;
    private static Account newAccount;
    private String name, number;
    private TextView nameField, numberField;
    private Button confirmButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

//        phoneNums = setupCursorAdapter();
//        userPhoneNum = MainActivity.getUserPhoneNum();

        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        name = extras.getString("name");
        number = extras.getString("number");

        nameField = findViewById(R.id.NameTextView);
        numberField = findViewById(R.id.NumberTextView);
        confirmButton = findViewById(R.id.confirmButton);

        nameField.setText(name);
        numberField.setText(number);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newAccount = addContacts();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("users");
                myRef.child(newAccount.getNumber()).setValue(newAccount);

                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private Account addContacts() {
        newAccount = MainActivity.getNewAccount();
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReferenceFromUrl("https://safecheck-2017.firebaseio.com/users/" + newAccount.getNumber());
        newAccount.getContacts().add(number);
//        int i = 0;
//        for (String num : phoneNums) {
//            newAccount.getContacts().add(num);
//            i++;
//            if (i == 10) {
//                break;
//            }
//        }
        return newAccount;
    }
//
//    private ArrayList<String> setupCursorAdapter() {
//        ArrayList<String> list = new ArrayList<>();
//        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
//        while (phones.moveToNext()) {
//            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//            list.add(phoneNumber);
//        }
//        phones.close();
//        return list;
//    }
}
