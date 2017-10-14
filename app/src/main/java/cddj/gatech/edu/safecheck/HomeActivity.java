package cddj.gatech.edu.safecheck;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by juliachen on 10/14/17.
 */

public class HomeActivity extends AppCompatActivity {

    private Button addContactButton;
    private Button checkInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addContactButton = (Button) findViewById(R.id.addContactsButton);
        checkInButton = (Button) findViewById(R.id.checkInButton);

        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SendTextActivity.class);
                startActivity(intent);
            }
        });

        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LoadContactsActivity.class);
                startActivity(intent);
            }
        });
    }
}
