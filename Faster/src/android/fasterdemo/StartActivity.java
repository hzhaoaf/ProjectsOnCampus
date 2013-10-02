package android.fasterdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

public class StartActivity extends Activity {
    ImageButton imgButtonSum;
    ImageButton imgButtonShape;
    ImageButton imgButtonJudge;
    ImageButton imgButtonExit;
    int modeSign;//模式标志，1代表sum，2代表shape 3代表Judge。
    public Animation aniButtonScale;
    Intent intentStart; 
    Bundle bundleStart; 
    
  
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        
        aniButtonScale = AnimationUtils.loadAnimation(this, R.anim.anim);
        imgButtonSum = (ImageButton)findViewById(R.id.imageButtonSum);
        imgButtonShape = (ImageButton)findViewById(R.id.ImageButtonShape);
        imgButtonJudge = (ImageButton)findViewById(R.id.imageButtonJudge);
        imgButtonExit = (ImageButton)findViewById(R.id.imageButtonExit);
	
        intentStart = new Intent(StartActivity.this, OptionActivity.class);
        bundleStart = new Bundle();
        imgButtonSum.setOnClickListener(new OnClickListener(){
	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		aniButtonScale.setAnimationListener(new Animation.AnimationListener()
		{
		    
		    @Override
		    public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			modeSign = 1;
			bundleStart.putInt("modeSign", modeSign);
			intentStart.putExtras(bundleStart);		
			StartActivity.this.startActivity(intentStart);
			
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
        }
        );
        imgButtonShape.setOnClickListener(new OnClickListener(){
	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		aniButtonScale.setAnimationListener(new Animation.AnimationListener()
		{
		    
		    @Override
		    public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
			modeSign = 2;
			bundleStart.putInt("modeSign", modeSign);
			intentStart.putExtras(bundleStart);		
			StartActivity.this.startActivity(intentStart);
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
        
        imgButtonJudge.setOnClickListener(new OnClickListener(){
	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		aniButtonScale.setAnimationListener(new Animation.AnimationListener()
		{	    
		    @Override
		    public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
			modeSign = 3;
			bundleStart.putInt("modeSign", modeSign);
			intentStart.putExtras(bundleStart);		
			StartActivity.this.startActivity(intentStart);
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
        }
        );
        
        imgButtonExit.setOnClickListener(new OnClickListener(){
	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		aniButtonScale.setAnimationListener(new Animation.AnimationListener()
		{	    
		    @Override
		    public void onAnimationStart(Animation animation) {	
			
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
		
		 new AlertDialog.Builder(StartActivity.this)
                 .setIcon(R.drawable.icon1)
                 .setTitle(R.string.app_name)
                 
                 .setNegativeButton("暂不退出", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                         }
                 })
                 .setPositiveButton("点击退出", new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int whichButton) {
                                 finish();
                                 Intent startMain = new
        				 Intent(Intent.ACTION_MAIN);
        				 startMain.addCategory(Intent.CATEGORY_HOME);
        				 startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        				 startActivity(startMain);
        				 System.exit(0);
                         }
                 }).show();
	    }          
        }
        );
    }    

    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
    if(keyCode == KeyEvent.KEYCODE_BACK){

        new AlertDialog.Builder(this)
                .setIcon(R.drawable.icon1)
                .setTitle(R.string.app_name)
                
                .setNegativeButton("暂不退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                })
                .setPositiveButton("点击退出", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                                finish();
                        }
                }).show();
        
        return true;
}else{                
return super.onKeyDown(keyCode, event);
}
}
}