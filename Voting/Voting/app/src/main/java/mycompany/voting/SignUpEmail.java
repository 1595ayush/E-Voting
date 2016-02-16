package mycompany.voting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;


public class SignUpEmail extends AppCompatActivity {
    Button btn;
    Firebase ref;
    EditText name, email, passwords, gender,stayLoc, curLoc, uids;
    String uidkey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_sign_up_email);

        btn = (Button)findViewById(R.id.btn_signUp);
        passwords = (EditText)findViewById(R.id.et_Pass);
        uids = (EditText)findViewById(R.id.et_uid);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = uids.getText().toString();
                final String password = passwords.getText().toString();
                ref= new Firebase("https://e-voting.firebaseio.com/");
                Query query = ref.orderByChild("uid").equalTo(uid);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        System.out.println(dataSnapshot);
                        if(dataSnapshot.getValue()==null)
                        {
                            Toast.makeText(SignUpEmail.this, "No user found. Kindly register.", Toast.LENGTH_LONG).show();
                        }
                        else{
                            //DataSnapshot dataSnapshot1 = dataSnapshot.getChildrenCount();
                            for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                Uid uid = dataSnapshot1.getValue(Uid.class);
                                uidkey = uid.getUid();
                                System.out.println(uid.getPassword());
                                if (password.equals(uid.getPassword()))
                                {
                                    System.out.println("old user");
                                    if(uid.verified==1)
                                    {//take to news page
                                        Intent i = new Intent(SignUpEmail.this, ElectionOptions.class);

                                        startActivity(i);
                                    }
                                    else
                                    {//take to location verification page
                                        Intent i = new Intent(SignUpEmail.this, Location.class);
                                        System.out.println("uid is"+uidkey);
                                        i.putExtra("ACCESS_KEY",uidkey);
                                        startActivity(i);

                                    }
                                }

                                else {
                                    Toast.makeText(SignUpEmail.this, "Username & Passowrd do not match.", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

            }



    });


}


}