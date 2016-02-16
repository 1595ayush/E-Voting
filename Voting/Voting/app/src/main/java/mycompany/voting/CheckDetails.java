package mycompany.voting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CheckDetails extends AppCompatActivity {
    private String m_Text = "";
    Button btn_yes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_details);

        btn_yes = (Button)findViewById(R.id.btn_yes_details);

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CheckDetails.this);
                builder.setTitle("UID");

                // Set up the input
                final EditText input = new EditText(CheckDetails.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setHint("Enter Your 12 digit UID number");

                builder.setView(input);

                // Set up the buttons
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        System.out.println(m_Text);
                        if (m_Text.length() != 12) {
                            Toast.makeText(CheckDetails.this, "UID should be 12 characters", Toast.LENGTH_LONG).show();
                            m_Text = "";
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
        });

    }
}
