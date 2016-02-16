package mycompany.voting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class MainActivity extends Activity {
    Firebase ref ;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the main content layout of the Activity
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);

        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignUpEmail.class);
                startActivity(i);
            }
        });

    }

    //product barcode mode
    public void scanBar(View v) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(MainActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    //product qr code mode
    public void scanQR(View v) {
        System.out.println("hi");
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(MainActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    //alert dialog for downloadDialog
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Firebase ref = new Firebase("https://e-voting.firebaseio.com/");

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

                String uid_test = "uid", name_test = "name", gender_test= "gender", dist_test = "dist", state_test="state", yob_test = "yob";
                final int uid_start, name_start, gender_start, dist_start, state_start, yob_start;

                uid_start=contents.indexOf(uid_test);
                name_start=contents.indexOf(name_test);
                gender_start=contents.indexOf(gender_test);
                dist_start=contents.indexOf(dist_test);
                state_start=contents.indexOf(state_test);
                yob_start=contents.indexOf(yob_test);

                final String uid = contents.substring(uid_start+5, name_start-2);
                final String name = contents.substring(name_start+6, gender_start-2);
                final String gender = contents.substring(gender_start+8, gender_start+9);
                final String location = contents.substring(dist_start + 6, state_start - 2);
                final String dob = contents.substring(yob_start + 5, yob_start + 9);
                System.out.println(uid + name + gender + location + dob);
                Query query = ref.orderByChild("uid").equalTo(uid);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        System.out.println(dataSnapshot);
                        if(dataSnapshot.getValue()!=null) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Uid uids = snapshot.getValue(Uid.class);
                                System.out.println("snapshot=" + snapshot);
                                if (snapshot.getValue() == null) {
                                    Intent i = new Intent(MainActivity.this, CheckDetailsAadhar.class);
                                    i.putExtra("uid", uid);
                                    i.putExtra("name", name);
                                    i.putExtra("gender", gender);
                                    i.putExtra("location", location);
                                    i.putExtra("dob", dob);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(MainActivity.this, "You have alreay registered", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(MainActivity.this, SignUpEmail.class);
                                    startActivity(i);
                                }
                            }
                        }
                        else
                        {
                                Intent i = new Intent(MainActivity.this, CheckDetailsAadhar.class);
                                i.putExtra("uid", uid);
                                i.putExtra("name", name);
                                i.putExtra("gender", gender);
                                i.putExtra("location", location);
                                i.putExtra("dob", dob);
                                startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                toast.show();

                //get uid, name, dist from aadhaar


            }
        }
    }
}