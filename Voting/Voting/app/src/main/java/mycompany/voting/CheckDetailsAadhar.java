package mycompany.voting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

public class CheckDetailsAadhar extends AppCompatActivity {
    Intent intent ;
    private String m_Text = "";
    private String m_re_Text="";
    Button btn_yes;
    private TextView dob,name,uid,gender,location;
    Firebase ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_check_details_aadhar);
        intent = getIntent();
        ref=new Firebase("https://e-voting.firebaseio.com/");
        name = (TextView) findViewById(R.id.textView_putName);
        gender = (TextView) findViewById(R.id.textView_putGender);
        location= (TextView) findViewById(R.id.textView_putLocation);
        uid = (TextView) findViewById(R.id.textView_putUid);
        dob = (TextView) findViewById(R.id.textView_putyob);
        name.setText(intent.getStringExtra("name"));
        uid.setText(intent.getStringExtra("uid"));
        gender.setText(intent.getStringExtra("gender"));
        location.setText(intent.getStringExtra("location"));
        dob.setText(intent.getStringExtra("dob"));
        btn_yes = (Button)findViewById(R.id.btn_yes_details_aadhar);

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CheckDetailsAadhar.this);
                builder.setTitle("Password");

                // Set up the input
                final EditText input = new EditText(CheckDetailsAadhar.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                input.setHint("Enter Password");


                builder.setView(input);


                // Set up the buttons
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(CheckDetailsAadhar.this);
                        builder.setTitle("Confirm Password");

                        // Set up the input
                        final EditText reinput = new EditText(CheckDetailsAadhar.this);
                        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                        reinput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        reinput.setHint("Reenter Password");


                        builder.setView(reinput);

                        // Set up the buttons
                        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int flag = 0;
                                m_Text = input.getText().toString();
                                m_re_Text = reinput.getText().toString();
                                System.out.println(m_Text);
                                if(m_Text.length() < 6){
                                    Toast.makeText(CheckDetailsAadhar.this, "Passwords Minimum Length is 6 characters", Toast.LENGTH_LONG).show();
                                    m_Text = "";
                                    m_re_Text = "";
                                    flag = 1;
                                }

                                if (!m_Text.equals(m_re_Text) && flag ==  0) {
                                    Toast.makeText(CheckDetailsAadhar.this, "Passwords Don't Match!", Toast.LENGTH_LONG).show();
                                    m_Text = "";
                                    m_re_Text = "";
                                }
                                if(m_Text.equals(m_re_Text) && flag==0)
                                {

                                    //HASH MAP
                                    Map<String,Object> map= new HashMap<String,Object>();
                                    map.put("clocation",intent.getStringExtra("location"));
                                    map.put("slocation",intent.getStringExtra("location"));
                                    map.put("name",intent.getStringExtra("name"));
                                    map.put("gender",intent.getStringExtra("gender"));
                                    map.put("uid",intent.getStringExtra("uid"));
                                    map.put("password",m_re_Text);
                                    map.put("verified",0);
                                    map.put("election_city",0);
                                    map.put("election_state",0);
                                    map.put("election_country",0);

                                    ref.push().setValue(map);

                                }
                            }

                        });/*
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });*/

                            builder.show();


                        }
                    }

                    );/*
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });*/

                    builder.show();
                }
            }

            );


        }


    }


