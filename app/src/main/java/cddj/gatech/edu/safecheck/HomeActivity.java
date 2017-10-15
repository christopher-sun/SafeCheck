package cddj.gatech.edu.safecheck;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.provider.ContactsContract;
import android.widget.Toast;

import static java.security.AccessController.getContext;

/**
 * Created by juliachen on 10/14/17.
 */

public class HomeActivity extends AppCompatActivity {

    private Button addContactButton;
    private Button checkInButton;

    static final int PICK_CONTACT=1;

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
//                Intent intent = new Intent(view.getContext(), LoadContactsActivity.class);
//                startActivity(intent);
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent, PICK_CONTACT);
            }
        });
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        if (reqCode == PICK_CONTACT && resultCode == RESULT_OK) {
            // Get the URI and query the content provider for the phone number
            Uri contactUri = data.getData();
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
            String[] projection2 = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
            Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
            Cursor cursor2 = getContentResolver().query(contactUri, projection2, null, null, null);
            String number = null, name = null;
            // If the cursor returned is valid, get the phone number
            if (cursor != null && cursor.moveToFirst()) {
                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                number = cursor.getString(numberIndex);
                cursor.close();
            }

            if (cursor2 != null && cursor2.moveToFirst()) {
                int nameIndex = cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                name = cursor2.getString(nameIndex);
                cursor2.close();
            }
            Intent newIntent = new Intent(this, LoadContactsActivity.class);
            newIntent.putExtra("name", name);
            newIntent.putExtra("number", number);
            startActivity(newIntent);
        }
    }
}
