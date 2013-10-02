package android.fasterdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class OptionActivity extends Activity {
    ImageButton imgButtonPlay;
    ImageButton imgButtonScore;
    ImageButton imgButtonHelp;
    ImageButton imgButtonExit;
    Animation  aniButtonScale;
    
    
    TextView textViewContent;
    Intent intentMode;
    Bundle bundleMode;
    int modeSign;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option);
        
        intentMode = this.getIntent();
        bundleMode = intentMode.getExtras();
        
        modeSign =  bundleMode.getInt("modeSign");
        
        imgButtonPlay = (ImageButton)findViewById(R.id.imageButtonPlay);
        imgButtonScore = (ImageButton)findViewById(R.id.imageButtonSore);
        imgButtonHelp = (ImageButton)findViewById(R.id.imageButtonHelp);
        imgButtonExit = (ImageButton)findViewById(R.id.imageButtonExit);
        aniButtonScale = AnimationUtils.loadAnimation(this, R.anim.anim);
        imgButtonPlay.setOnClickListener(new OnClickListener(){       
	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		
		aniButtonScale.setAnimationListener(new Animation.AnimationListener()
		{
		    
		    @Override
		    public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
			
		    }
		    @Override
		    public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub		
		    }	    
		    @Override
		    public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub
			if (modeSign == 2)
			{			
			    intentMode = new Intent(OptionActivity.this, GameShapeActivity.class);
			}   
			else
			{    
			    intentMode = new Intent(OptionActivity.this, GameActivity.class);
			    
			}
			intentMode.putExtras(bundleMode);
			startActivityForResult(intentMode, 0);
		    } 
		});
		v.startAnimation(aniButtonScale);
	    }    
        });
        
        imgButtonScore.setOnClickListener(new OnClickListener(){       
 	    @Override
 	    public void onClick(View v) {
 		// TODO Auto-generated method stub
 		
 		aniButtonScale.setAnimationListener(new Animation.AnimationListener()
 		{
 		    
 		    @Override
 		    public void onAnimationStart(Animation animation) {
 			// TODO Auto-generated method stub
 		
 		    }
 		    @Override
 		    public void onAnimationRepeat(Animation animation) {
 			// TODO Auto-generated method stub		
 		    }	    
 		    @Override
 		    public void onAnimationEnd(Animation animation) {
 			// TODO Auto-generated method stub
 			
 			if (modeSign == 2)
 			{		
 			   intentMode = new Intent(OptionActivity.this, Score.class);
 			}   
 			else
 			{    
 			    intentMode = new Intent(OptionActivity.this, Score.class);
 			    
 			}
 			intentMode.putExtras(bundleMode);
 			startActivityForResult(intentMode, 0);
 		    } 
 		});
 		v.startAnimation(aniButtonScale);
 	    }    
         });
        imgButtonHelp.setOnClickListener(new OnClickListener(){       
   	    @Override
   	    public void onClick(View v) {
   		// TODO Auto-generated method stub
   		
   		aniButtonScale.setAnimationListener(new Animation.AnimationListener()
   		{
   		    
   		    @Override
   		    public void onAnimationStart(Animation animation) {
   			// TODO Auto-generated method stub
   			
   			if (modeSign == 1)
   			{		
   			     new AlertDialog.Builder(OptionActivity.this).setTitle("SUM说明").setMessage(R.string.strSumHelp)
   			    .setPositiveButton("知道了",
   				    new DialogInterface.OnClickListener() {
   			        @Override
   			        public void onClick(DialogInterface dialog, int which) {
   			    	// TODO Auto-generated method stub
   			        }
   			    }
   			    ).show();
   			}   
   			
   			if (modeSign == 2)
   			{		
   			     new AlertDialog.Builder(OptionActivity.this).setTitle("Shape说明").setMessage(R.string.strShapeHelp)
   			    .setPositiveButton("知道了",
   				    new DialogInterface.OnClickListener() {
   			        @Override
   			        public void onClick(DialogInterface dialog, int which) {
   			    	// TODO Auto-generated method stub
   			        }
   			    }
   			    ).show();
   			} 
   			
   			if (modeSign == 3)
   			{		
   			     new AlertDialog.Builder(OptionActivity.this).setTitle("Judge说明").setMessage(R.string.strJudgeHelp)
   			    .setPositiveButton("知道了",
   				    new DialogInterface.OnClickListener() {
   			        @Override
   			        public void onClick(DialogInterface dialog, int which) {
   			    	// TODO Auto-generated method stub
   			        }
   			    }
   			    ).show();
   			} 
   			
   			


   		    }
   		    @Override
   		    public void onAnimationRepeat(Animation animation) {
   			// TODO Auto-generated method stub		
   		    }	    
   		    @Override
   		    public void onAnimationEnd(Animation animation) {
   			// TODO Auto-generated method stub
   		    } 
   		});
   		v.startAnimation(aniButtonScale);
   	    }    
           });
   
        
        imgButtonExit.setOnClickListener(new OnClickListener(){       
 	    @Override
 	    public void onClick(View v) {
 		// TODO Auto-generated method stub
 		
 		aniButtonScale.setAnimationListener(new Animation.AnimationListener()
 		{
 		    
 		    @Override
 		    public void onAnimationStart(Animation animation) {
 			// TODO Auto-generated method stub
 	
 			    intentMode = new Intent(OptionActivity.this, StartActivity.class);
 			   OptionActivity.this.startActivity(intentMode);
 		    }
 		    @Override
 		    public void onAnimationRepeat(Animation animation) {
 			// TODO Auto-generated method stub		
 		    }	    
 		    @Override
 		    public void onAnimationEnd(Animation animation) {
 			// TODO Auto-generated method stub
 		    } 
 		});
 		v.startAnimation(aniButtonScale);
 	    }    
         });
    }

    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	super.onActivityResult(requestCode, resultCode, data);
	
	switch (resultCode)
	{
	case RESULT_OK:
	    Bundle bundleScore = data.getExtras();
	//    int score = bundleScore.getInt("score");
	}
    }
}