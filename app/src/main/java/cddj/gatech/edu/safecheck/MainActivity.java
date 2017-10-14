package cddj.gatech.edu.safecheck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button registerButton;
    //private EditText userName;
    private EditText userPhone;

    private static String username;
    private static String userPhoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = (Button) findViewById(R.id.registerButton);
        //userName = (EditText) findViewById(R.id.userName);
        userPhone = (EditText) findViewById(R.id.userPhone);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!getUserPhone().isEmpty()) {
                    userPhoneNum = getUserPhone();
                    Intent intent = new Intent(view.getContext(), HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(view.getContext(), "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

//    public String getUsername() {
//        return userName.getText().toString();
//    }

    public String getUserPhone() {
        return userPhone.getText().toString();
    }
}
