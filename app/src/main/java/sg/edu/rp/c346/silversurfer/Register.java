package sg.edu.rp.c346.silversurfer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener{
    EditText editTextName, editTextEmail, editTextPassword, editTextConfirmPassword;
    SignInButton buttonGoogle;
    String TAG = "ta";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    ProgressDialog mProgress;
    GoogleApiClient mGoogleApiClient;

    private FirebaseUser mFirebaseUser;
    private String mUsername;
    private String mPhotoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mProgress = new ProgressDialog(this);

//        editTextName = (EditText)findViewById(R.id.etName);
//        editTextEmail = (EditText)findViewById(R.id.etEmail);
//        editTextPassword = (EditText)findViewById(R.id.etPassword);
//        editTextConfirmPassword = (EditText)findViewById(R.id.etCPW);
//        buttonRegister = (Button)findViewById(R.id.btnregister);
        buttonGoogle = (SignInButton) findViewById(R.id.sign_in_button);
        buttonGoogle.setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

//        buttonRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startRegister();
//            }
//        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            default:
                return;
        }
    }
//    private void startRegister() {
//
//        final String name = editTextPassword.getText().toString().trim();
//        String email = editTextEmail.getText().toString().trim();
//        String password = editTextName.getText().toString().trim();
//        String password2 = editTextConfirmPassword.getText().toString().trim();
//
////        Toast.makeText(Register.this, name+email+password+password2 , Toast.LENGTH_SHORT).show();
//
//        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(password2)) {
//
//            Toast.makeText(Register.this, "field validation works", Toast.LENGTH_SHORT).show();
//
//            mProgress.setMessage("Signing up ...");
//            mProgress.show();
//
//            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if(task.isSuccessful()) {
//
//                        Toast.makeText(Register.this, "idk wtf this is", Toast.LENGTH_SHORT).show();
//                        String user_id = mAuth.getCurrentUser().getUid();
//                        DatabaseReference current_user_db = mDatabase.child(user_id);
//                        current_user_db.child("name").setValue(name);
//                        mProgress.dismiss();
//
//                        Intent intent =  new Intent(Register.this, MainActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
//
//                    } else {
//                        mProgress.dismiss();
//                        Toast.makeText(Register.this, "error signing up", Toast.LENGTH_LONG).show();
//                    }
//                }
//            });
//        }
//    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, 9001);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 9001) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed
                Log.e(TAG, result.getStatus().toString());
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        mProgress.setMessage("Signing up ...");
        mProgress.show();
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            mFirebaseUser = mAuth.getCurrentUser();
                            mUsername = mFirebaseUser.getDisplayName();
                            mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
                            String user_id = mAuth.getCurrentUser().getUid();
                            final DatabaseReference current_user_db = mDatabase.child(user_id);
                            current_user_db.child("name").setValue(mUsername);
                            current_user_db.child("photoUrl").setValue(mPhotoUrl);
                            startActivity(new Intent(Register.this, MainActivity.class));
                            mProgress.dismiss();
                            finish();
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }


}
