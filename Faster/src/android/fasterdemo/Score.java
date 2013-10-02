package android.fasterdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
public class Score extends Activity{
    String [] strUserName = {"张三", "李四", "王二", "Jim", "Tom"};
    String [] strScore = {"20", "15", "10", "10", "5"};
    String strName;
    String strRes;
    
    TextView textViewHighScoreTitle;
    TextView textViewUserName;
    TextView textViewScore;
    
    TextView textViewUser1;
    TextView textViewUser2;
    TextView textViewUser3;
    TextView textViewUser4;
    TextView textViewUser5;
    
    TextView textViewScoreRes1;
    TextView textViewScoreRes2;
    TextView textViewScoreRes3;
    TextView textViewScoreRes4;
    TextView textViewScoreRes5;
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);
        textViewHighScoreTitle = (TextView)findViewById(R.id.textViewHighScoreTitle);
        textViewUserName = (TextView)findViewById(R.id.textViewUserName);
        textViewScore = (TextView)findViewById(R.id.textViewScore);
        
        textViewUser1 = (TextView)findViewById(R.id.textViewUser1);
        textViewUser2 = (TextView)findViewById(R.id.textViewUser2);
        textViewUser3 = (TextView)findViewById(R.id.textViewUser3);
        textViewUser4 = (TextView)findViewById(R.id.textViewUser4);
        textViewUser5 = (TextView)findViewById(R.id.textViewUser5);
        
        textViewScoreRes1 = (TextView)findViewById(R.id.textViewScoreRes1);
        textViewScoreRes2 = (TextView)findViewById(R.id.textViewScoreRes2);
        textViewScoreRes3 = (TextView)findViewById(R.id.textViewScoreRes3);
        textViewScoreRes4 = (TextView)findViewById(R.id.textViewScoreRes4);
        textViewScoreRes5 = (TextView)findViewById(R.id.textViewScoreRes5);
        loadPreference();
    //    preProcessTextView();
        
        for (int i = 0; i < strScore.length; i++)
        {
            int newScore = Integer.parseInt(strRes);
            int newTemp = Integer.parseInt(strScore[i]);
            if ( newScore > newTemp)
            {
        	
        	for (int j = strUserName.length - 1; j >  i; j--)
        	{
        	    strUserName[j] = strUserName[j - 1];
        	    strScore[j] = strScore[j - 1];
        	}
        	strUserName[i] = strName;
        	strScore[i] = strRes;
        	break;
            }
        }
        updateTextView(); 
    }
    
    public void loadPreference()
    {
	int mode = Activity.MODE_PRIVATE;
	SharedPreferences mySharedPreference=getSharedPreferences(GameActivity.MYPREFS,mode);
	strName = mySharedPreference.getString("username", null);
	strRes = mySharedPreference.getString("score", null);
    }
    
    public void preProcessTextView()
    {
	strUserName[0] = textViewUser1.getText().toString();
	strUserName[1] = textViewUser2.getText().toString();
	strUserName[2] = textViewUser3.getText().toString();
	strUserName[3] = textViewUser4.getText().toString();
	strUserName[4] = textViewUser5.getText().toString();
	
	strScore[0] = textViewScoreRes1.getText().toString();
	strScore[1] = textViewScoreRes2.getText().toString();
	strScore[2] = textViewScoreRes3.getText().toString();
	strScore[3] = textViewScoreRes4.getText().toString();
	strScore[4] = textViewScoreRes5.getText().toString();
    }
    public void updateTextView()
    {
	textViewUser1.setText(strUserName[0]);
	textViewUser2.setText(strUserName[1]);
	textViewUser3.setText(strUserName[2]);
	textViewUser4.setText(strUserName[3]);
	textViewUser5.setText(strUserName[4]);
	
	textViewScoreRes1.setText(strScore[0]);
	textViewScoreRes2.setText(strScore[1]);
	textViewScoreRes3.setText(strScore[2]);
	textViewScoreRes4.setText(strScore[3]);
	textViewScoreRes5.setText(strScore[4]);
	
    }
}
