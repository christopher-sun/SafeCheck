package cddj.gatech.edu.safecheck;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by juliachen on 10/14/17.
 */

class SendTextActivity extends AppCompatActivity {

    private EditText editText;
    private Button sendButton;

    private SmsManager smsManager = SmsManager.getDefault();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_text);

        editText = (EditText) findViewById(R.id.editText);
        sendButton = (Button) findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


    }

    public String getText() {
        return editText.getText().toString();
    }
}
