package sg.edu.rp.c346.silversurfer;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Account extends AppCompatActivity {
    private EditText etName;
    private EditText etEmail;
    private ImageView ivProfile;
    private Button updatebtn;

    private DatabaseReference mDatabase;

    private ProgressDialog Progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        Progress = new ProgressDialog(this);
        etName = (EditText)findViewById(R.id.etPassword);
        etEmail = (EditText)findViewById(R.id.etEmail);
        updatebtn = (Button)findViewById(R.id.updateBtn);
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });
    }
    private void startPosting() {
        Progress.setMessage("Updating");
        Progress.show();

        final String name_val = etName.getText().toString().trim();
        final String email_val = etEmail.getText().toString().trim();
        DatabaseReference mPost = mDatabase.push();

        mPost.child("name").setValue(name_val);
        mPost.child("email").setValue(email_val);
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
