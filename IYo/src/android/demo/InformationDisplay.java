package android.demo;

import java.util.Vector;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextPaint;
import android.webkit.WebSettings.TextSize;
import android.widget.ImageView;
import android.widget.TextView;

public class InformationDisplay extends Activity{
    ImageView imageInformation;
  //  Vector<Building> vecBuilding = new Vector<Building>(50);
    Building building = new  Building();
    int buildingNum;
    
    //定义所有控件
    TextView textBuildingTitle;
    TextView textNewsTime1;
    TextView textNewsTime2;
    TextView textNewsTime3;
    TextView textNewsContent1;
    TextView textNewsContent2;
    TextView textNewsContent3;
    
    TextView textOpenTime;
    
    ImageView imgBuilding;
    ImageView imgFollowButton;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buildinfor);
        
        textBuildingTitle = (TextView)findViewById(R.id.textBuildingTitle);
        textNewsTime1 = (TextView)findViewById(R.id.textNewsTime1);
        textNewsTime2 = (TextView)findViewById(R.id.textNewsTime2);
        textNewsTime3 = (TextView)findViewById(R.id.textNewsTime3);

        textNewsContent1 = (TextView)findViewById(R.id.textNewsContent1);
        textNewsContent2 = (TextView)findViewById(R.id.textNewsContent2);
        textNewsContent3 = (TextView)findViewById(R.id.textNewsContent3);   

        textOpenTime = (TextView)findViewById(R.id.textOpenTime);
        
        imgBuilding = (ImageView)findViewById(R.id.imageBuilding);
        imgFollowButton = (ImageView)findViewById(R.id.imageFollowButton);
       
        buildingNum = this.getIntent().getExtras().getInt(Constant.buildingNum);
   //     vecBuilding = (Vector<Building>)getIntent().getSerializableExtra(Constant.buildingSet);
        building = (Building)getIntent().getSerializableExtra(Constant.building);  
        
        textBuildingTitle.setText(building.name);
        textBuildingTitle.setTextAppearance(this, TextPaint.FAKE_BOLD_TEXT_FLAG);
        textBuildingTitle.setTextColor(Color.BLACK);
        
        String[] strNews = new String[5];
        String[] strNewsTime = new String[5];
        String[] strNewsContent = new String[5];
        
        if (building.news != null)
            strNews = building.news.split("\t");
        for (int i = 0; i < strNews.length; i++)
        {
            String[] temp = strNews[i].split(" ");
            strNewsTime[i] = temp[0];
            strNewsContent[i] = temp[1];
        }
        if (strNewsTime[0] != null && strNewsContent[0] != null)
        {
            textNewsTime1.setText(strNewsTime[0]);
            textNewsTime1.setTextAppearance(this, TextPaint.FAKE_BOLD_TEXT_FLAG);
            textNewsTime1.setTextColor(Color.BLACK);
            
            textNewsContent1.setText(strNewsContent[0]);
        }
        if (strNewsTime[1] != null && strNewsContent[1] != null)
        {
            textNewsTime2.setText(strNewsTime[1]);
            textNewsTime2.setTextAppearance(this, TextPaint.FAKE_BOLD_TEXT_FLAG);
            textNewsTime2.setTextColor(Color.BLACK);
            textNewsContent2.setText(strNewsContent[1]);
           
        }
        if (strNewsTime[2] != null && strNewsContent[2] != null)
        {
            textNewsTime3.setText(strNewsTime[2]);
            textNewsTime3.setTextAppearance(this, TextPaint.FAKE_BOLD_TEXT_FLAG);
            textNewsTime3.setTextColor(Color.BLACK);
            textNewsContent3.setText(strNewsContent[2]);
           

        }
        if (building.openTime != null){
            textOpenTime.setText("开放时间\n" + "\n" + building.openTime);
            textOpenTime.setTextColor(Color.BLUE);
        }
            
        int [] num = {
        	R.drawable.pich_0000, R.drawable.pich_0001, R.drawable.pich_0002, R.drawable.pich_0003, 
        	R.drawable.pich_0004, R.drawable.pich_0005, R.drawable.pich_0006, R.drawable.pich_0007, 
        	R.drawable.pich_0008, R.drawable.pich_0009, R.drawable.pich_0010, R.drawable.pich_0000, 
        	R.drawable.pich_0012, R.drawable.pich_0013, R.drawable.pich_0014, R.drawable.pich_0015, 
        	R.drawable.pich_0016, R.drawable.pich_0000, R.drawable.pich_0018, R.drawable.pich_0019, 
        	R.drawable.pich_0000, R.drawable.pich_0021, R.drawable.pich_0022, R.drawable.pich_0023, 
        	R.drawable.pich_0000, R.drawable.pich_0025, R.drawable.pich_0026, R.drawable.pich_0000, 
        	R.drawable.pich_0000, R.drawable.pich_0039, R.drawable.pich_0030, R.drawable.pich_0031, 
        	R.drawable.pich_0032, R.drawable.pich_0033, R.drawable.pich_0034, R.drawable.pich_0000, 
        	R.drawable.pich_0000, R.drawable.pich_0037, R.drawable.pich_0038, R.drawable.pich_0039, 
        	R.drawable.pich_0040};
        Drawable drawable = getResources().getDrawable(num[buildingNum]);
        imgBuilding.setBackgroundDrawable(drawable);
        }
}
	
