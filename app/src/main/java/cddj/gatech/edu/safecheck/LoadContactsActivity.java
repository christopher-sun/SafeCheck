package cddj.gatech.edu.safecheck;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        phoneNums = setupCursorAdapter();
        userPhoneNum = MainActivity.getUserPhoneNum();
        setContentView(R.layout.activity_contacts);

        newAccount = addContacts();

        FirebaseDatabase database = FirebaseDatabase.getInstance().getInstance();
        DatabaseReference myRef = database.getReference("users");
        myRef.child(newAccount.getNumber()).setValue(newAccount);
    }

    private Account addContacts() {
        newAccount = MainActivity.getNewAccount();
        for (String num : phoneNums) {
            newAccount.getContacts().add(num);
        }
        return newAccount;
    }

    private ArrayList<String> setupCursorAdapter() {
        ArrayList<String> list = new ArrayList<>();
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            list.add(phoneNumber);
        }
        phones.close();
        return list;
    }
}
