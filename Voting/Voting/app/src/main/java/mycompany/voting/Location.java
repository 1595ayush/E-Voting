package mycompany.voting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class Location extends AppCompatActivity {

    Intent intent;
    Firebase ref;
    String uidkey,key;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_location);
        intent = getIntent();
        textView = (TextView) findViewById(R.id.textView_successReg_location);
        ref=new Firebase("https://e-voting.firebaseio.com/");
        Query query = ref.orderByChild("uid").equalTo(intent.getStringExtra("ACCESS_KEY"));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Uid uid = dataSnapshot1.getValue(Uid.class);
                    textView.setText(uid.getClocation());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);

        String[] items = new String[]{"Mumbai", "Bengaluru"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    String uidkey = intent.getStringExtra("ACCESS_KEY");
                    Query query = ref.orderByChild("uid").equalTo(uidkey);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Uid uids = snapshot.getValue(Uid.class);
                                key = snapshot.getKey();
                                ref.child(key).child("clocation").setValue("Mumbai");
                                textView.setText("Mumbai");
                            }

                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
;
                   // ref.child("userabc").child("slocation").setValue("Mumbai");

                } else {
                    String uidkey = intent.getStringExtra("ACCESS_KEY");
                    Query query = ref.orderByChild("uid").equalTo(uidkey);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Uid uids = snapshot.getValue(Uid.class);
                                key = snapshot.getKey();
                                ref.child(key).child("clocation").setValue("Bengaluru");
                                textView.setText("Bengaluru");
                            }

                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });

                   // ref.child("userabc").child("slocation").setValue("Bengaluru");
                    //textView.setText("Bengaluru");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        //
    }
}
