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

public class Upload_Fitness extends AppCompatActivity {

    private EditText etTitle;
    private EditText etDesc;
    private Button btnSubmit;

    private DatabaseReference mDatabase;

    private ProgressDialog Progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_fitness);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Fitness");


        etTitle = (EditText)findViewById(R.id.titleF);
        etDesc = (EditText)findViewById(R.id.descF);
        btnSubmit = (Button)findViewById(R.id.submitbutton);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Add Tips");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }

        });

        Progress = new ProgressDialog(this);
    }

    private void startPosting() {
        Progress.setMessage("Uploading");
        Progress.show();

        final String title_val = etTitle.getText().toString().trim();
        final String desc_val = etDesc.getText().toString().trim();
        DatabaseReference mPost = mDatabase.push();

        mPost.child("title").setValue(title_val);
        mPost.child("desc").setValue(desc_val);
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
