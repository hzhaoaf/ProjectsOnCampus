package android.fasterdemo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends Activity  implements Runnable{
    ImageView imgViewTime;
    ImageView imgViewProgressTime;
    ImageView imgViewRes;
    ImageView imgViewRightRes;
    ImageView imgViewErrorRes;
    TextView textViewRightRes;
    TextView textViewErrorRes;
    TextView textViewQuestion1;
    TextView textViewQuestion2;

    ImageView imgViewAns;  
    ImageView imgViewRight;
    ImageView imgViewError;
    
    Animation aniButtonQues2;
    Animation aniButtonQues1;
    
    public Animation aniButtonScale;
    Vector<String> vecJudge = new Vector<String>();
    Vector<String> vecJudgeAns = new Vector<String>();
    
    int modeSign;
    int judgeflag;
    
    Intent intentGame;    //
    Bundle bundleGame;//接收游戏模式
    
    String strSumContent;//档模式为sum的时候问题框中呈现的内容
    int numFirst;
    int numSecond;
    int numSum;
    Random rd;
    int numRight;//记录正确答案个数
    int numError;//记录错误答案个数
    
    int indexImage = 0;
    
    public ProgressDialog myDialog = null;
    EditText editName;
    private int[] intImage = {
	    R.drawable.time1,  //为神马在judge模式下, 图片加载不进来？？？？？
	    R.drawable.time2,
	    R.drawable.time3,
	    R.drawable.time4,
	    R.drawable.time5,
	    R.drawable.time6,
	    R.drawable.time7			    
    };
    private final int msg_Key = 0x1234;
    public Handler myHandler;
    Thread t;
    
    MediaPlayer bgMusic;
    SoundPool soundPool;
    HashMap<Integer, Integer> soundPoolMap;
    Activity ma;
    public static final String MYPREFS="mySharedPreference";
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        intentGame = this.getIntent();
        bundleGame = intentGame.getExtras();
        modeSign = bundleGame.getInt("modeSign");
        
        aniButtonScale = AnimationUtils.loadAnimation(this, R.anim.anim);
        
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
          
        aniButtonQues1 = AnimationUtils.loadAnimation(this, R.anim.quetion1);
        aniButtonQues2 = AnimationUtils.loadAnimation(this, R.anim.quetion2);
        
        textViewQuestion1 = (TextView)findViewById(R.id.TextViewQuestion1);
        textViewQuestion2 = (TextView)findViewById(R.id.TextViewQustion2);
        
        numRight = 1;
        numError = 1;
        indexImage = 0;
        
        initSounds();
       
        if (modeSign == 1)
        {
            rd = new Random(System.currentTimeMillis());
            judgeflag = rd.nextInt(20);
            numFirst = rd.nextInt(20);
            numSecond = rd.nextInt(20);
            numSum = numFirst + numSecond - 2 + rd.nextInt(4);//生成的为正确的结果左右误差为5的整数
            strSumContent = Integer.toString(numFirst) + " + " + Integer.toString(numSecond)
        	    + " = " +  Integer.toString(numSum);
            textViewQuestion2.setText(strSumContent);
            numFirst = rd.nextInt(20);
            numSecond = rd.nextInt(20);
            numSum =numFirst + numSecond - 2 + rd.nextInt(4);
            strSumContent = Integer.toString(numFirst) + " + " + Integer.toString(numSecond)
        	    + " = " +  Integer.toString(numSum);
            textViewQuestion1.setText(strSumContent);
        }
        if (modeSign == 3)
        {
            getJudge();//读入判断题文件                    
            textViewQuestion1.setText(vecJudge.get(judgeflag));
            textViewQuestion2.setText(vecJudge.get(judgeflag + 1));
        }

        bgMusic.start();
        //按对勾的响应
        imgViewRight.setOnTouchListener(new OnTouchListener(){
	    @Override
	    public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if ( event.getAction() == MotionEvent.ACTION_DOWN)
		{
		    //算式模式
		    if (modeSign == 1)
		    {			
			 if(checkSumAns())
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
			 
			 textViewQuestion1.setText(textViewQuestion2.getText()); 
			 numFirst = rd.nextInt(20);
		         numSecond = rd.nextInt(20);
		         numSum = numFirst + numSecond - 5 + rd.nextInt(10);//生成的为正确的结果左右误差为5的整数
		         strSumContent = Integer.toString(numFirst) + " + " + Integer.toString(numSecond)
		        	    + " = " +  Integer.toString(numSum);
		         textViewQuestion2.setText(strSumContent);
		         
		         textViewQuestion2.startAnimation(aniButtonQues2);
		         textViewQuestion1.startAnimation(aniButtonQues1);		         	         
		    }
		    //判断题模式
		    if (modeSign == 3)
		    {			  
			if (checkJudgeAns(judgeflag))
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
		    	judgeflag++;
		        judgeflag = judgeflag % 72;
		        textViewQuestion1.setText(vecJudge.get(judgeflag));
		        textViewQuestion2.startAnimation(aniButtonQues2);
		        textViewQuestion2.setText(vecJudge.get(judgeflag + 1));
		       textViewQuestion1.startAnimation(aniButtonQues1);
		       
		     
		    }  
		       v.startAnimation(aniButtonScale);//按钮的动画效果
		}
		else if (event.getAction() == MotionEvent.ACTION_UP)
		{
		}
		return false;
	    }
        });        
        //按下叉的反应
        imgViewError.setOnTouchListener(new OnTouchListener(){
	    @Override
	    public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if ( event.getAction() == MotionEvent.ACTION_DOWN)
		{
		    if (modeSign == 1)
		    {
			if(!checkSumAns())
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
			textViewQuestion1.setText(textViewQuestion2.getText());
			 numFirst = rd.nextInt(20);
		         numSecond = rd.nextInt(20);
		         numSum = numFirst + numSecond - 2 + rd.nextInt(4);//生成的为正确的结果左右误差为5的整数
		         strSumContent = Integer.toString(numFirst) + " + " + Integer.toString(numSecond)
		        	    + " = " +  Integer.toString(numSum);
		         textViewQuestion2.setText(strSumContent);	         
		         textViewQuestion2.startAnimation(aniButtonQues2);
		         textViewQuestion1.startAnimation(aniButtonQues1);	         	         
		    
		         
		    }
		    if (modeSign == 3)
		    {
			  if (!checkJudgeAns(judgeflag))
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
		    	judgeflag++;
		        judgeflag = judgeflag % 72;
		        textViewQuestion2.startAnimation(aniButtonQues2);
		        textViewQuestion2.setText(vecJudge.get(judgeflag + 1));
		       textViewQuestion1.startAnimation(aniButtonQues1);
		       textViewQuestion1.setText(vecJudge.get(judgeflag));
		     
		    }  		    
		    //按钮的动画效果
		       v.startAnimation(aniButtonScale);	      
		}
		else if (event.getAction() == MotionEvent.ACTION_UP)
		{
		    
		}
		return false;
	    }
        });
        
        myHandler = new Handler() {	
	    @Override
	    public void handleMessage(Message msg) {
		/* 这里是处理信息的方法*/
		// TODO Auto-generated method stub
		super.handleMessage(msg);
		switch (msg.what) {
		case msg_Key:
		    /* 在这处理要TextView对象Show时间的事件*/
		 
		            int index;
			    index = indexImage / 5;
		            imgViewProgressTime.setImageResource(intImage[index]);
	
		  
		    if (indexImage > 30)
		     {
			dialog();			
		     }	    
		    break;
		default:
		    break;
		}
	    }
	};       	
         t = new Thread(this);
        t.start();
        ma=GameActivity.this;
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
    public void initSounds()
    {
	bgMusic = MediaPlayer.create(GameActivity.this, R.raw.bgmusic);
	 soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
	 soundPoolMap = new HashMap<Integer, Integer>();
	     // load方法返回一个ID，后面的1为本音乐的权重，暂时无用
	 soundPoolMap.put(1, soundPool.load(this, R.raw.t, 1));
	 soundPoolMap.put(2, soundPool.load(this, R.raw.f, 1));
	 
    }
    
    public void savePreference()
    {
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
    
    public void playSound(int sound, int loop)
    {
	 AudioManager mgr = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
	 float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
	 float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	 float volume = streamVolumeCurrent / streamVolumeMax;
	 soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f); //播放声音
    }
    
    /**
     * 在modeSign为3的情况下读入判断题的文件
     */
    public void getJudge()
    {
	try{
	    	AssetManager am = this.getAssets();
	    	InputStream in = am.open("judge.txt");
	    	BufferedReader bufJudge = new BufferedReader(new InputStreamReader(in, "utf-8"));
	    	String strJudge = bufJudge.readLine();
	    	while(strJudge != null)
	    	{
	    	    String[] strTemp = strJudge.split("\t");
	    	    vecJudge.add(strTemp[0]);
	    	    vecJudgeAns.add(strTemp[1]);
	    	    strJudge = bufJudge.readLine();
	    	}
	    	bufJudge.close();
	//    	am.close();
		}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
    }
    
    /**
     * 在算式情况下检测结果是否正确
     * @return
     */
    public boolean checkSumAns()
    {
	String strTemp = (String) textViewQuestion1.getText();
	int numFirstTemp = Integer.parseInt(strTemp.substring(0, strTemp.indexOf("+") - 1));
	int numSecondTemp = Integer.parseInt(strTemp.substring(strTemp.indexOf("+") + 2, strTemp.indexOf("=") - 1));
	int numSumTemp = Integer.parseInt(strTemp.substring(strTemp.indexOf("=") + 2));
	if (numSumTemp == numFirstTemp + numSecondTemp)
	    return true;
	else
	    return false;
    }
        
    public boolean checkJudgeAns(int pos)
    {
	if (vecJudgeAns.get(pos).equals("T"))
	    return true;
	else
	    return false;
    }
    
    protected void onPause()
    {
	super.onPause();
	bgMusic.release();
	
    }
    public void dialog()
    {
	AlertDialog.Builder builder = new Builder(GameActivity.this);
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
	//	GameActivity.this.finish();
	//	intentGame = new Intent();
		intentGame.setClass(GameActivity.this, OptionActivity.class);
		GameActivity.this.startActivity(intentGame);		
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
    @Override
    public void run() {
	// TODO Auto-generated method stub
		try
		{
		   do
		   {
		        indexImage++;		    
		    	Message msg = new Message();	
			msg.what = msg_Key;
			myHandler.sendMessage(msg);
		    	Thread.sleep(1000);
		   	}while(indexImage <= 30);
		   }
		catch(Exception e)
		{
		    e.printStackTrace();
		}
    }

}
