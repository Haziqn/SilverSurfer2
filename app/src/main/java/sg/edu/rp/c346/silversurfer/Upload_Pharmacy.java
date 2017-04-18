package sg.edu.rp.c346.silversurfer;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Upload_Pharmacy extends AppCompatActivity {

    private EditText etTitle;
    private EditText etAddress;
    private EditText etOpenHouse;
    private Button btnSubmit;

    private DatabaseReference mDatabase;

    private ProgressDialog Progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__pharmacy);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Pharmacies");

        etTitle = (EditText)findViewById(R.id.titleP);
        etAddress = (EditText)findViewById(R.id.addressP);
        etOpenHouse = (EditText)findViewById(R.id.openhoursP);
        btnSubmit = (Button)findViewById(R.id.submitbutton);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }

        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Add Pharmacy");

        Progress = new ProgressDialog(this);
    }

    private void startPosting() {
        Progress.setMessage("Uploading");
        Progress.show();

        final String title_val = etTitle.getText().toString().trim();
        final String address_val = etAddress.getText().toString().trim();
        final String openHours_val = etOpenHouse.getText().toString().trim();
        DatabaseReference mPost = mDatabase.push();

        mPost.child("title").setValue(title_val);
        mPost.child("address").setValue(address_val);
        mPost.child("openHours").setValue(openHours_val);
        Progress.dismiss();
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
