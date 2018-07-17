package tz.co.neelansoft.handyman;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by landre on 02/07/2018.
 */

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";
    private static final int REQUEST_CODE = 700;
    public static final String EXTRA_USER_ID = "extra_user_id";
    private EditText mEditName;
    private EditText mEditMobile;
    private EditText mEditDescription;
    private EditText mEditAddress;
    private EditText mEditEmail;
    private EditText mEditPassword;
    private Spinner  mSpinnerService;
    private Button   mButtonRegister;
    private Button   mButtonCancel;
    private TextView mTextViewSignin;

    private CheckBox mCheckboxShow;
    private CheckBox mCheckboxServiceProvider;

    private boolean isServiceProvider = false;


    private boolean mEmailOk=false;
    private boolean mPasswordOk=false;

    private DatabaseReference mFirebaseReference;
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup_layout);

        mFirebaseAuth = FirebaseAuth.getInstance();


       // mSpinnerService = findViewById(R.id.sp_provider_service);
        mEditName       = findViewById(R.id.etName);
        mEditEmail      = findViewById(R.id.etEmail);
   //     mEditDescription= findViewById(R.id.et_provider_name);
        mEditMobile     = findViewById(R.id.etPhone);
     //   mEditAddress    = findViewById(R.id.et_provider_name);
        mEditPassword   = findViewById(R.id.etPassword);

        mButtonCancel   = findViewById(R.id.btn_cancel);
        mButtonRegister = findViewById(R.id.btn_signup);

        mTextViewSignin = findViewById(R.id.btn_signin);

        mCheckboxServiceProvider = findViewById(R.id.cbIsServiceProvider);
        mCheckboxShow = findViewById(R.id.cbShowPassword);

        mCheckboxServiceProvider.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isServiceProvider = true;
                }
                else{
                    isServiceProvider = false;
                }
            }
        });

        mCheckboxShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mEditPassword.setTransformationMethod(null);
                }
                else{

                    mEditPassword.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        mButtonCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        mEditEmail.addTextChangedListener(new TextWatcher() {
            final EmailValidator emailValidator = new EmailValidator();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(emailValidator.validate(mEditEmail.getText().toString())){
                    mEmailOk = true;
                    mEditEmail.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
                else {
                    showEmailError();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mEmailOk && mPasswordOk) enableSignup();
            }
        });

        mEditPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if((mEditPassword.getText().toString().length() < 6)){
                    showPasswordError();
                }
                else {
                    mPasswordOk = true;
                    mEditPassword.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if(mEmailOk && mPasswordOk) enableSignup();
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
/*
        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.services,android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerService.setAdapter(spinnerAdapter);*/
    }
    private void showPasswordError(){
        mEditPassword.setError("Password too short");
        mEditPassword.setTextColor(getResources().getColor(R.color.color_error));
        mButtonRegister.setTextColor(getResources().getColor(R.color.black));
        mButtonRegister.setBackgroundColor(getResources().getColor(R.color.colorDisabled));
        mButtonRegister.setEnabled(false);
    }
    private void showEmailError(){
        mEditEmail.setError("Invalid email");
        mEditEmail.setTextColor(getResources().getColor(R.color.color_error));
        mButtonRegister.setTextColor(getResources().getColor(R.color.black));
        mButtonRegister.setBackgroundColor(getResources().getColor(R.color.colorDisabled));
        mButtonRegister.setEnabled(false);
    }

    private void enableSignup(){

            mButtonRegister.setTextColor(getResources().getColor(R.color.white));
            mButtonRegister.setBackgroundResource(R.drawable.button_background);
            mButtonRegister.setEnabled(true);

    }
    private void registerUser() {

        String email = mEditEmail.getText().toString();
        final String name = mEditName.getText().toString();

        String passwd = mEditPassword.getText().toString();


        mFirebaseAuth.createUserWithEmailAndPassword(email, passwd)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser newUser = mFirebaseAuth.getCurrentUser();
                            if (!TextUtils.isEmpty(name)) {
                                UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name)
                                        .build();
                                newUser.updateProfile(changeRequest)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    updateUI(mFirebaseAuth.getCurrentUser());
                                                }
                                            }
                                        });
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "User signup: " + e.getMessage(), e);
                        updateUI(null);
                    }
                });

    }

    private void registerServiceProvider(FirebaseUser user){

        startActivity(new Intent(SignUpActivity.this,AddHandymanActivity.class).putExtra(EXTRA_USER_ID,user.getUid()));
        finish();
    }

    private void updateUI(FirebaseUser user){
        if(user != null){
            if(isServiceProvider){
            registerServiceProvider(user);
            }
            else{
            loginUser(user);
            }
        }
    }

    private void loginUser(FirebaseUser user){
        startActivity(new Intent(SignUpActivity.this,MainActivity.class).putExtra(EXTRA_USER_ID,user.getUid()));
        finish();
    }
}
