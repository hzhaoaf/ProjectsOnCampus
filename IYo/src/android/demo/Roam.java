package android.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Roam extends Activity {
    /** Called when the activity is first created. */
    TextView textViewRoam;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roamui);
        
        textViewRoam   = (TextView)findViewById(R.id.textViewRoam);
        

    }
}