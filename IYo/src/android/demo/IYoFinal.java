package android.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class IYoFinal extends Activity {
    /** Called when the activity is first created. */
    ImageView imgExplore;
    ImageView imgRoam;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuui);
        
        imgExplore = (ImageView)findViewById(R.id.imageExplore);
        imgRoam   = (ImageView)findViewById(R.id.imageRoam);
        
        imgExplore.setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(IYoFinal.this, IBuptActivity.class);
		startActivity(intent);
		
	    }
	});
        
    imgRoam.setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(IYoFinal.this, Roam.class);
		startActivity(intent);
		
	    }
	});
    }
}