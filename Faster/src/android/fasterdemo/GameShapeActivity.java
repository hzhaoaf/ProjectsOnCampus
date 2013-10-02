package android.fasterdemo;

import java.util.HashMap;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameShapeActivity extends Activity  implements Runnable{
    ImageView imgViewTime;
    ImageView imgViewProgressTime;
    ImageView imgViewRes;
    ImageView imgViewRightRes;
    ImageView imgViewErrorRes;
    TextView textViewRightRes;
    TextView textViewErrorRes;

    ImageView imgViewAns;  
    ImageView imgViewRight;
    ImageView imgViewError;
    
    Animation aniLayoutQues2;
    Animation aniLayoutQues1;
    
    Layout layoutQuestion1;
    Layout layoutQuestion2;    
    ImageView imgViewQuestion11;
    ImageView imgViewQuestion12;
    ImageView imgViewQuestion13;
    ImageView imgViewQuestion14;
    TextView textViewQuestion1;
    
    ImageView imgViewQuestion21;
    ImageView imgViewQuestion22;
    ImageView imgViewQuestion23;
    ImageView imgViewQuestion24;
    TextView textViewQuestion2;
    LinearLayout llQuestion1;
    LinearLayout llQuestion2;
    
    public Animation aniButtonScale;
    
    int modeSign;
    
    Intent intentGameShape;    //
    Bundle bundleGameShape;//Shape模式

    Random rd;
    int numRight;//记录正确答案个数
    int numError;//记录错误答案个数
    EditText editName;
   private  int[] intQuestionImage = {
	    R.drawable.triangle2,
	    R.drawable.square2,
	    R.drawable.star2,
	    R.drawable.hexagon2
    };
    
    private Integer[] intImage = {
	    R.drawable.time1,  
	    R.drawable.time2,
	    R.drawable.time3,
	    R.drawable.time4,
	    R.drawable.time5,
	    R.drawable.time6,
	    R.drawable.time7			    
    };
    int timeFlag;
    boolean sleeping;
    boolean startState;
    //控制生成图形的索引
    int first;
    int second;
    int third;
    int sum;
    public static final String MYPREFS="mySharedPreference";
    MediaPlayer bgMusic;
    SoundPool soundPool;
    HashMap<Integer, Integer> soundPoolMap;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameshape);
        
        intentGameShape = this.getIntent();
        bundleGameShape = intentGameShape.getExtras();
        modeSign = bundleGameShape.getInt("modeSign");
        
        aniButtonScale = AnimationUtils.loadAnimation(this, R.anim.anim);
        aniLayoutQues1 = AnimationUtils.loadAnimation(this, R.anim.quetion1);
        
        imgViewTime = (ImageView)findViewById(R.id.imageViewTime);
        imgViewProgressTime = (ImageView)findViewById(R.id.imageViewProgresserTime);
        imgViewRes = (ImageView)findViewById(R.id.imageViewResult);
        imgViewRightRes = (ImageView)findViewById(R.id.imageViewRightRes);
        imgViewErrorRes = (ImageView)findViewById(R.id.imageViewErrorRes);
        textViewRightRes = (TextView)findViewById(R.id.textViewRightRes);
        textViewErrorRes = (TextView)findViewById(R.id.textViewErrorRes);
               
        imgViewAns = (ImageView)findViewById(R.id.ImageViewAns);
        imgViewRight = (ImageView)findViewById(R.id.imageViewRight);
        imgViewError = (ImageView)findViewById(R.id.imageViewError);
        
        imgViewQuestion11 = (ImageView)findViewById(R.id.imageViewQuestion11);
        imgViewQuestion12 = (ImageView)findViewById(R.id.imageViewQuestion12);
        imgViewQuestion13 = (ImageView)findViewById(R.id.imageViewQuestion13);
        imgViewQuestion14 = (ImageView)findViewById(R.id.imageViewQuestion14);
        textViewQuestion1 = (TextView)findViewById(R.id.textViewQuestion1);
        
        imgViewQuestion21 = (ImageView)findViewById(R.id.imageViewQuestion21);
        imgViewQuestion22 = (ImageView)findViewById(R.id.imageViewQuestion22);
        imgViewQuestion23 = (ImageView)findViewById(R.id.imageViewQuestion23);
        imgViewQuestion24 = (ImageView)findViewById(R.id.imageViewQuestion24);
        
        textViewQuestion2 = (TextView)findViewById(R.id.textViewQuestion2);
         
        aniLayoutQues1 = AnimationUtils.loadAnimation(this, R.anim.quetion1);
        aniLayoutQues2 = AnimationUtils.loadAnimation(this, R.anim.quetion2);
        
        llQuestion1 =(LinearLayout) findViewById(R.id.layoutQuestion1);
        llQuestion2 = (LinearLayout) findViewById(R.id.layoutQuestion2);
        numRight = 1;
        numError = 1;
        
        initSounds();
        
        //初始化
        rd = new Random(System.currentTimeMillis());
        first = rd.nextInt(4);
        second = rd.nextInt(4);
        third = rd.nextInt(4);
        timeFlag = 0;
        sum = 10;
        imgViewQuestion11.setImageResource(intQuestionImage[first]);
        imgViewQuestion12.setImageResource(intQuestionImage[second]);
        imgViewQuestion13.setImageResource(intQuestionImage[third]);
        textViewQuestion1.setText(Integer.toString(sum));
        first = rd.nextInt(4);
        second = rd.nextInt(4);
        third = rd.nextInt(4);
        sum = imgValue(first) + imgValue(second) + imgValue(third) - 2 + rd.nextInt(3);
        imgViewQuestion21.setImageResource(this.intQuestionImage[first]);
        imgViewQuestion22.setImageResource(this.intQuestionImage[second]);
        imgViewQuestion23.setImageResource(this.intQuestionImage[third]);
        textViewQuestion2.setText(Integer.toString(sum));
        sleeping = false;
        startState = false;
        new Thread(this).start();
}
     

                public boolean onKeyDown(int keyCode, KeyEvent event) {
                       
                if(keyCode == KeyEvent.KEYCODE_BACK){

                    bgMusic.release();
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
            
        

    @Override
    public void run() {
	// TODO Auto-generated method stub
		try{
		    while (!sleeping)
		    {
			update();
			Thread.sleep(1000);
		    }	    	
		    }catch(Exception e)
		{
		    e.printStackTrace();
		}	
    }    
    public void update()
    {
	if (startState == false)
	{
	    bgMusic.start();
	    startState = true;
	}
	timeFlag++;
	int index = timeFlag / 8;
	imgViewProgressTime.setImageResource(intImage[index]);
	if (timeFlag >= 30)
	{
	    dialog();
	    sleeping = true;
	}
	
	   imgViewRight.setOnTouchListener(new OnTouchListener(){
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if ( event.getAction() == MotionEvent.ACTION_DOWN)
			{
			    //图形模式
			    if (checkAns())
			    {
				String strTemp = " = "+Integer.toString(numRight++);
				 textViewRightRes.setText(strTemp);
				 playSound(1, 0);	
			    }
			    else
			    {
				  String strTemp = " = "+Integer.toString(numError++);
			          textViewErrorRes.setText(strTemp);
			          playSound(2, 0);
			    }
			     first = rd.nextInt(4);
			     second = rd.nextInt(4);
			     third = rd.nextInt(4);
			     sum = imgValue(first)  + imgValue(second)  + imgValue(third)  - 2 + rd.nextInt(3);
			     imgViewQuestion11.setImageResource(intQuestionImage[first]);
			     imgViewQuestion12.setImageResource(intQuestionImage[second]);
			     imgViewQuestion13.setImageResource(intQuestionImage[third]);
			     textViewQuestion1.setText(Integer.toString(sum));
			     
			      v.startAnimation(aniButtonScale);//按钮的动画效果
			      llQuestion1.startAnimation(aniLayoutQues1);
			      llQuestion2.startAnimation(aniLayoutQues2);
			        
			}
			else if (event.getAction() == MotionEvent.ACTION_UP)
			{
			}
			return false;
		    }
	        });        
	        //按下叉的反应
	        imgViewError.setOnTouchListener(new OnTouchListener(){
		    public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if ( event.getAction() == MotionEvent.ACTION_DOWN)
			{
			    if (!checkAns())
			    {
				String strTemp = " = "+Integer.toString(numRight++);
			            textViewRightRes.setText(strTemp);
			            playSound(1, 0);
			    }
			    else
			    {
				 String strTemp = " = "+Integer.toString(numError++);
			          textViewErrorRes.setText(strTemp);
			          playSound(2, 0);
			    }
			    //按钮的动画效果
			    
			     first = rd.nextInt(4);
			     second = rd.nextInt(4);
			     third = rd.nextInt(4);
			     sum = imgValue(first) + imgValue(second) + imgValue(third) - 2 + rd.nextInt(3);
			     imgViewQuestion11.setImageResource(intQuestionImage[first]);
			     imgViewQuestion12.setImageResource(intQuestionImage[second]);
			     imgViewQuestion13.setImageResource(intQuestionImage[third]);
			     textViewQuestion1.setText(Integer.toString(sum));
			     llQuestion1.startAnimation(aniLayoutQues1);
			      llQuestion2.startAnimation(aniLayoutQues2);  
			     v.startAnimation(aniButtonScale);	      
			       
			}
			else if (event.getAction() == MotionEvent.ACTION_UP)
			{
			   
			}
			return false;
		    }
	        });
	        
//	        myHandler = new Handler() {	
//		    @Override
//		    public void handleMessage(Message msg) {
//			/* 这里是处理信息的方法*/
//			// TODO Auto-generated method stub
//			super.handleMessage(msg);
//			switch (msg.what) {
//			case msg_Key:
//			    /* 在这处理要TextView对象Show时间的事件*/
//			    int index;
//			    index = indexImage / 5;
//			    imgViewProgressTime.setBackgroundResource(intImage[index]);
//			    if (timeFlag > 30)
//			     {
//				dialog();			
//			     }	    
//			    break;
//			default:
//			    break;
//			}
//		    }
//		};       	
    }
    
    public boolean checkAns()
    {
	if (sum == imgValue(first) + imgValue(second) + imgValue(third ))
	    return true;
	else
	    return false;
    }
    public int imgValue(int a)
    {
	if (a == 0)
	    return 3;
	else if (a == 1)
	    return 4;
	else if (a == 2)
	    return 5;
	else
	    return 6;
    }
    public void playSound(int sound, int loop)
    {
	 AudioManager mgr = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
	 float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
	 float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	 float volume = streamVolumeCurrent / streamVolumeMax;
	 soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f); //播放声音
    }
    
    public void initSounds()
    {
	bgMusic = MediaPlayer.create(GameShapeActivity.this, R.raw.bgmusic);
	 soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
	 soundPoolMap = new HashMap<Integer, Integer>();
	     // load方法返回一个ID，后面的1为本音乐的权重，暂时无用
	 soundPoolMap.put(1, soundPool.load(this, R.raw.t, 1));
	 soundPoolMap.put(2, soundPool.load(this, R.raw.f, 1));
	 
    }
    public void dialog()
    {
	AlertDialog.Builder builder = new Builder(GameShapeActivity.this);
	builder.setTitle("请输入您的姓名");
	editName = new EditText(this);
	builder.setIcon(android.R.drawable.ic_dialog_info);
	builder.setView(editName);
	builder.setPositiveButton("确认", new 	OnClickListener() {  
	    @Override
	    public void onClick(DialogInterface dialog, int which) 
	    {
		savePreference();
		dialog.dismiss();    

		intentGameShape.setClass(GameShapeActivity.this, OptionActivity.class);
		GameShapeActivity.this.startActivity(intentGameShape);		
	    }
	  });  
	builder.setNegativeButton("取消", new OnClickListener() {  
	    @Override
	    public void onClick(DialogInterface dialog, int which) 
	    {
		    dialog.dismiss();
	    }
	});  
	builder.create().show();
    }
    public void savePreference()
    {
	EditText editName = new EditText(this);
	String strRight = textViewRightRes.getText().toString();
	String strError = textViewErrorRes.getText().toString();
	int posRight = strRight.indexOf("=");
	int posError = strRight.indexOf("=");
	String scoreRight = strRight.substring(posRight + 2);
	String scoreError = strError.substring(posError  + 2); 
	String strName = editName.getText().toString();
	int mode = Activity.MODE_PRIVATE;
	String score = Integer.toString(Integer.parseInt(scoreRight) - Integer.parseInt(scoreError));
	SharedPreferences mySharedPreference=getSharedPreferences(MYPREFS,mode);
	SharedPreferences.Editor editor=mySharedPreference.edit();
	editor.putString("username", strName);
	editor.putString("score", score);
	editor.commit();
    }
}
