package tz.co.neelansoft.handyman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by landre on 02/07/2018.
 */

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        startActivity(new Intent(this,SignUpActivity.class));
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            startActivity(new Intent(this,SignUpActivity.class));
            finish();
        }
    }
}
