package android.demo;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends Activity implements OnClickListener
{
	private TextView txtCondition;
	private ListView listResult;
	private Button butBack;
	
	private int year, month, day, building, period;
	private ArrayList<String> classList = new ArrayList<String>();
		
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		
		txtCondition = (TextView)findViewById(R.id.txtCondition);
		listResult = (ListView)findViewById(R.id.listResult);
		butBack = (Button)findViewById(R.id.butBack);
		
		butBack.setOnClickListener(this);
		
		Bundle bundle = getIntent().getExtras();
		year = bundle.getInt(Classroom.ID_Year);
		month = bundle.getInt(Classroom.ID_Month);
		day = bundle.getInt(Classroom.ID_Day);
		building = bundle.getInt(Classroom.ID_Building);
		period = bundle.getInt(Classroom.ID_Period);
		
		String condition = String.format("%04d-%02d-%02d　%s　%s", year, month, day, Classroom.PeriodTxt[period], Classroom.BuildingTxt[building]);
		txtCondition.setText(condition);
		
		doQuery();
	}

	@Override
	public void onClick(View arg0)
	{
		if(arg0.getId()==R.id.butBack)
		{
			finish();
		}
	}
	
	private void doQuery()
	{
		waitdialog = ProgressDialog.show(this, "请稍候", "正在连接服务器...", true);
		queryThread.start();
    }

	private String req = null;
	private String res = null;
	private ProgressDialog waitdialog = null;
	private Handler handler = new Handler();;

	private Thread queryThread = new Thread()
	{
		public void run()
		{
			req = String.format("year=%d&month=%d&day=%d&period=%d&building=%d&radio=%s", year, month, day, Classroom.PeriodVal[period], Classroom.BuildingVal[building], "text");
			res = Http.HttpPost(Classroom.URL, req);
			
			if(res!=null) 
			{
				//不加转义的正则表达式：<div class='line'>([^<>]+)\((\d+)\)</div><br/>
				String regular = "<div class='line'>([^<>]+)\\((\\d+)\\)</div><br/>";
				Pattern pattern = Pattern.compile(regular, Pattern.MULTILINE | Pattern.DOTALL);
				Matcher matcher = pattern.matcher(res);
				while(matcher.find())
				{
					String classText = String.format("教室:%s  座位:%s", matcher.group(1), matcher.group(2));
					classList.add(classText);
				}
			}
		
			handler.post(queryRunnable);
		}
	};
	
	final Runnable queryRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			if(waitdialog.isShowing()) waitdialog.dismiss();
			
			if(res==null)
			{
				Toast.makeText(ResultActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
			}
			else if(classList.isEmpty())
			{
				Toast.makeText(ResultActivity.this, "抱歉，当前没有自习室！", Toast.LENGTH_SHORT).show();
			}
			else
			{
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(ResultActivity.this, android.R.layout.simple_list_item_1, classList);
				listResult.setAdapter(adapter);
			}
		}
	};
}
