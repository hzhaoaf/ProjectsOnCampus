package android.demo;

import java.util.Date;

import android.demo.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

public class Classroom extends Activity implements OnClickListener
{
	public static final String URL = "http://class.byr.cn/cha.php";
	
	public static final String ID_Year = "year";
	public static final String ID_Month = "month";
	public static final String ID_Day = "day";
	public static final String ID_Building = "building";
	public static final String ID_Period = "period";
	public static final String ID_Text = "text";
	
	public static final String[] BuildingTxt = {"主楼", "教一", "教二", "教三", "教四", "教九", "图书馆", "宏福教一", "宏福教二"};
	public static final int[] BuildingVal = {0, 1, 2, 3, 5, 9, 5, 6, 7};
	
	public static final String[] PeriodTxt = {"1-2节", "3-4节", "5-6节", "7-8节", "10-13节"};
	public static final int[] PeriodVal = {1, 3, 5, 7, 10};
	
	public static final int[] PeriodEndTime = {950, 1200, 1520, 1720, 2220};	
	
	private DatePicker datDate;
	private Spinner spnBuilding;
	private Spinner spnPeriod;
	private Button butSubmit;
//	private Button butAbout;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.classroom);
		
		datDate = (DatePicker)findViewById(R.id.datDate);
		spnBuilding = (Spinner)findViewById(R.id.spnBuilding);
		spnPeriod = (Spinner)findViewById(R.id.spnPeriod);
		butSubmit = (Button)findViewById(R.id.butSubmit);
	//	butAbout = (Button)findViewById(R.id.butAbout);
		
		ArrayAdapter<String> adapterBuilding = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, BuildingTxt);
		adapterBuilding.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnBuilding.setAdapter(adapterBuilding);
		Intent intent = getIntent();
		String value= intent.getStringExtra("classroom");
		int num = getBuildingNum(value);
		spnBuilding.setSelection(num);
		
		ArrayAdapter<String> adapterPeriod = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, PeriodTxt);
		adapterPeriod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnPeriod.setAdapter(adapterPeriod);
		Date now = new Date();
		int nowVal = now.getHours() * 100 + now.getMinutes();
		for(int i=0;i<PeriodEndTime.length;i++)
		{
			if(nowVal<PeriodEndTime[i])
			{
				spnPeriod.setSelection(i);
				break;
			}
		}
		
		butSubmit.setOnClickListener(this);
	//	butAbout.setOnClickListener(this);
	}

	private int getBuildingNum(String value) {
	    // TODO Auto-generated method stub
	    for (int i = 0; i < BuildingTxt.length; i++){
		if (value.equals(BuildingTxt[i])){
		    return i;
		}
	    }
	    return 3;
	}

	@Override
	public void onClick(View arg0)
	{
		if(arg0.getId()==R.id.butSubmit)
		{
			Intent intent = new Intent();
			intent.putExtra(ID_Year, datDate.getYear());
			intent.putExtra(ID_Month, datDate.getMonth()+1);
			intent.putExtra(ID_Day, datDate.getDayOfMonth());
			intent.putExtra(ID_Building, spnBuilding.getSelectedItemPosition());
			intent.putExtra(ID_Period, spnPeriod.getSelectedItemPosition());
		//	intent.putExtra(ID_Text, "text");
			intent.setClass(this, ResultActivity.class);
			startActivity(intent);
		}
//		else if(arg0.getId()==R.id.butAbout)
//		{
//	    	new AlertDialog.Builder(this)
//	    	.setTitle("关于北邮空闲教室查询")
//	    	.setMessage("北邮空闲教室查询　V1.0.00\n　By：wuyifan　2011.10\n\n注：本程序查询数据全部来自\n　　http://class.byr.cn")
//	    	.setIcon(R.drawable.icon)
//	    	.setNegativeButton("确定", null)
//	    	.show();
//		}
	}
}

