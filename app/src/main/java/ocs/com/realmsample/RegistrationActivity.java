package ocs.com.realmsample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;

public class RegistrationActivity extends AppCompatActivity {

    EditText etFirstName, etLastName, etEmail, etDOB, etGender, etPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etDOB = (EditText) findViewById(R.id.etDOB);
        etGender = (EditText) findViewById(R.id.etGender);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Realm realm = Realm.getDefaultInstance();
                UserInfo userInfo = realm.where(UserInfo.class)
                        .equalTo("email", etEmail.getText().toString())
                        .equalTo("password", etPassword.getText().toString()).findFirst();

                if (userInfo == null) {
                    realm.beginTransaction();
                    userInfo = realm.createObject(UserInfo.class);
                    userInfo.setFirstName(etFirstName.getText().toString());
                    userInfo.setLastName(etLastName.getText().toString());
                    userInfo.setEmail(etEmail.getText().toString());
                    userInfo.setDob(etDOB.getText().toString());
                    userInfo.setPassword(etPassword.getText().toString());
                    userInfo.setGender(etGender.getText().toString());
                    realm.commitTransaction();
                    Toast.makeText(RegistrationActivity.this, "User Registered Successfully !!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                    finish();

                } else {
                    Toast.makeText(RegistrationActivity.this, "User already registerd !! try with other email id", Toast.LENGTH_SHORT).show();
                }
                realm.close();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
