package mycompany.voting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ElectionOptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_election_options);
    }
    @Override
    public void onBackPressed() {
        //
    }
}
