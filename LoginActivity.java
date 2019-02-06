package com.example.a800361.shifterapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.a800361.shifterapp.common.Common;
import com.example.a800361.shifterapp.model.Rider;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import dmax.dialog.SpotsDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {

    Button btnSignIn,btnRegister;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    RelativeLayout rootLayout;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Arkhip_font.ttf")
                .setFontAttrId(R.attr.fontPath).build());
        setContentView(R.layout.activity_login);


        try {
            //Initializing firebase
            auth = FirebaseAuth.getInstance();
            db = FirebaseDatabase.getInstance();
            users = db.getReference(Common.user_rider_tbl);

            //Initializing layout
            rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);

            btnRegister = (Button) findViewById(R.id.btnRegister);
            btnSignIn = (Button) findViewById(R.id.btnSignIn);

            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRegisterDialog();
                }
            });

            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLoginDialog();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showLoginDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("SIGN IN");
        dialog.setMessage("Please use email to sign in");

        LayoutInflater inflater = LayoutInflater.from(this);

        View login_layout = inflater.inflate(R.layout.layout_signin,null);


        final MaterialEditText edtEmail =  login_layout.findViewById(R.id.edtEmail);
        final MaterialEditText edtPassword =  login_layout.findViewById(R.id.edtPassword);
        final MaterialEditText edtName =  login_layout.findViewById(R.id.edtName);
        final MaterialEditText edtPhone =  login_layout.findViewById(R.id.edtPhone);

        dialog.setView(login_layout);

        dialog.setPositiveButton("SIGN IN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                btnSignIn.setEnabled(false);

                final String edtEmail_ = edtEmail.getText().toString();
                final String edtPassword_ = edtPassword.getText().toString();

                if(TextUtils.isEmpty(edtEmail_)){

                    Snackbar.make(rootLayout,"Please enter email address",Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(edtPassword_)){

                    Snackbar.make(rootLayout,"Please enter password",Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(edtPassword_.length() < 6){

                    Snackbar.make(rootLayout,"Password too short",Snackbar.LENGTH_LONG).show();
                    return;
                }


                final SpotsDialog waitingDialog = new SpotsDialog(LoginActivity.this);
//                waitingDialog.show();
                //Now we are login in
                auth.signInWithEmailAndPassword(edtEmail_,edtPassword_).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
//                       waitingDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(),home_.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //  waitingDialog.dismiss();
                        Snackbar.make(rootLayout,"Failed "+e.getMessage(),Snackbar.LENGTH_LONG).show();
                        btnSignIn.setEnabled(true);
                        waitingDialog.dismiss();
                    }
                });


            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void showRegisterDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("REGISTER");
        dialog.setMessage("Please use email to register");

        LayoutInflater inflater = LayoutInflater.from(this);

        View register_layout = inflater.inflate(R.layout.layout_register,null);


        final MaterialEditText edtEmail =  register_layout.findViewById(R.id.edtEmail);
        final MaterialEditText edtPassword =  register_layout.findViewById(R.id.edtPassword);
        final MaterialEditText edtName =  register_layout.findViewById(R.id.edtName);
        final MaterialEditText edtPhone =  register_layout.findViewById(R.id.edtPhone);
        dialog.setView(register_layout);
        dialog.setPositiveButton("REGISTER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                final String edtEmail_ = edtEmail.getText().toString();
                final String edtPassword_ = edtPassword.getText().toString();
                final String edtName_ = edtName.getText().toString();
                final String edtPhone_ = edtPhone.getText().toString();

                if(TextUtils.isEmpty(edtEmail_)){

                    Snackbar.make(rootLayout,"Please enter email address",Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(edtPassword_)){

                    Snackbar.make(rootLayout,"Please enter password",Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(edtName_)){

                    Snackbar.make(rootLayout,"Please enter name",Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(edtPhone_)){

                    Snackbar.make(rootLayout,"Please enter phone number",Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(edtPassword_.length() < 6){

                    Snackbar.make(rootLayout,"Password too short",Snackbar.LENGTH_LONG).show();
                    return;
                }

                //Finally lest register to the system
                auth.createUserWithEmailAndPassword(edtEmail_,edtPassword_).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //temporary Database to store information

                        Rider rider= new Rider();
                        rider.setEmail(edtEmail_);
                        rider.setName(edtName_);
                        rider.setPassword(edtPassword_);
                        rider.setPhone(edtPhone_);
                        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(rider)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Snackbar.make(rootLayout,"Register successful",Snackbar.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(rootLayout,"Failed "+e.getMessage(),Snackbar.LENGTH_LONG).show();
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(rootLayout,"Failed "+e.getMessage(),Snackbar.LENGTH_LONG).show();

                    }
                });


            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();


            }
        });
        dialog.show();
    }
}
