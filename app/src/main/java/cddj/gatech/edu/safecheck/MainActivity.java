package cddj.gatech.edu.safecheck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private Button registerButton;
    private Button loginButton;
    private EditText userName;
    private EditText userPhone;

    private static Account newAccount;

    private static String username;
    private static String userPhoneNum;

    public ArrayList<Account> entries = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = (Button) findViewById(R.id.registerButton);
        loginButton = (Button) findViewById(R.id.loginButton);
        userName = (EditText) findViewById(R.id.userName);
        userPhone = (EditText) findViewById(R.id.userPhone);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!getUserPhone().isEmpty() && !getUsername().isEmpty()) {
                    userPhoneNum = getUserPhone();
                    username = getUsername();

                    newAccount = new Account(userPhoneNum, username);
                    newAccount.getContacts().add("+14254453074");
                    newAccount.getContacts().add("+18882345678");

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("users");

                    myRef.child(newAccount.getNumber()).setValue(newAccount);

                    Intent intent = new Intent(view.getContext(), HomeActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(view.getContext(), "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!getUserPhone().isEmpty() && !getUsername().isEmpty()) {
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("users");
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            entries.clear();
                            Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                            entries.clear();
                            while (items.hasNext()) {
                                DataSnapshot item = items.next();

                                String username, number;
                                ArrayList<String> contacts;
                                username = item.child("username").getValue().toString();
                                number = item.child("number").getValue().toString();
                                contacts = (ArrayList<String>) item.child("contacts").getValue();
                                Account entry = new Account(number, username);
                                entry.getContacts().addAll(contacts);
                                entries.add(entry);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
//                for (Account entry : entries) {
//                    if (getUsername().equals(entry.getUsername()) && getUserPhone().equals(entry.getNumber())) {
//                        Intent intent = new Intent(view.getContext(), HomeActivity.class);
//                        startActivity(intent);
//                    }
//                }
//                Toast toast = Toast.makeText(view.getContext(), "Incorrect login information!",
//                        Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.BOTTOM, 0, 0);
//                toast.show();
                if (loginVerification()) {
                    Intent intent = new Intent(view.getContext(), HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(view.getContext(), "Incorrect login information!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                }
            }
        });
    }

    public String getUsername() {
        return userName.getText().toString();
    }

    public String getUserPhone() {
        return userPhone.getText().toString();
    }

    public static String getUserPhoneNum() {
        return userPhoneNum;
    }

    public static Account getNewAccount() {
        return newAccount;
    }

    public void resetLogin() {
        userName.setText("");
        userPhone.setText("");
    }

    private boolean loginVerification() {
        for (Account entry : entries) {
            if (getUsername().equals(entry.getUsername()) && getUserPhone().equals(entry.getNumber())) {
                newAccount = entry;
                return true;
            }
        }
        return false;
    }
}
