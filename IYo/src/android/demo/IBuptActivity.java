package android.demo;


import java.io.InputStream;
import java.util.List;
import java.util.Vector;

import android.demo.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class IBuptActivity extends MapActivity{
    /** Called when the activity is first created. */
    MapView mainMap;
    List<Overlay> overlays;
    private View popView;
    LocationManager mLocationManager;
    Location mLocation;
    GeoPoint point;
    String locationProvider;
    AutoCompleteTextView search;
    
    ImageView imgStudying;
    ImageView imgDinning;
    ImageView imgAccomodation;
    ImageView imgChuxing;
    ImageView imgPurchase;
    ImageView imgOffice;
    ImageView imgEntertainment;
    ImageView imgMoreOptions;
    ImageView imgViewSearch;
    
    Button buttonClassroom;
    
      //读入楼层信息
    static Vector<Building> vecBuildingSet = new Vector<Building>();
    static Building building = new Building();
    
   static int buildingNum; //楼层编号
    
    public static final String[] COUNTRIES = new String[]{
	"主","主楼", "教", "教一", "教二", "教三", "教四", "教九", "学","学一", "学二", "学三",
	"学四", "学五", "学六", "学八", "学九", "学十", "学十一", "学十三", "留学生公寓",
	"篮球场", "排球场", "体育馆", "运动馆", "运动场", "北邮科技园", "网络中心辅导基地", "学生活中中心", "行政楼",
	"校医院", "财","财务处后勤楼", "基建处保卫处", "图书馆", "科学会堂", "北邮科技大厦", "邮局", "学院超市", "超市发",
	"学林书店", "学生餐厅", "新食堂", "教工餐厅", "综合服务楼"
    };
    static String strClassroomChosen = new String();
    /**
    点击marker弹出框
*/
 private final ItemizedOverlay.OnFocusChangeListener onFocusChangeListener = new ItemizedOverlay.OnFocusChangeListener() {
     
     public void onFocusChanged(ItemizedOverlay overlay, OverlayItem newFocus) {
      //创建气泡窗口

      if (popView != null) {
         popView.setVisibility(View.GONE);
      }
      if (newFocus != null) 
      {
         MapView.LayoutParams geoLP = (MapView.LayoutParams) popView.getLayoutParams();
         geoLP.point = newFocus.getPoint();//这行用于popView的定位
          TextView title = (TextView) popView.findViewById(R.id.map_bubbleTitle);
          title.setText(newFocus.getTitle());
          strClassroomChosen = newFocus.getTitle();
          getBuildingNum(newFocus.getTitle());
          title.setTextColor(Color.BLACK);
          TextView desc = (TextView) popView.findViewById(R.id.map_bubbleText);
          if (newFocus.getSnippet() == null || newFocus.getSnippet().length() == 0)
          {
	   	desc.setVisibility(View.GONE);
          } 
          else 
         {
	   	desc.setVisibility(View.VISIBLE);
	//   	desc.setText(newFocus.getSnippet());
         }
          mainMap.updateViewLayout(popView, geoLP);
          popView.setVisibility(View.VISIBLE);
        }
      
      
  }

    private void getBuildingNum(String title) {
	// TODO Auto-generated method stub
	for (int i = 0; i < vecBuildingSet.size(); i++)
	{
	    if (title.equals(vecBuildingSet.get(i).name))
	    {
		buildingNum = vecBuildingSet.get(i).num;
		break;
	    }
	    
	}
    }
 };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //探索模式下的菜单选项
        imgStudying = (ImageView)findViewById(R.id.imageStudy);
        imgDinning = (ImageView)findViewById(R.id.imageDining);
        imgAccomodation = (ImageView)findViewById(R.id.imageAccomodation);
        imgChuxing = (ImageView)findViewById(R.id.imageChuxing);
        imgEntertainment = (ImageView)findViewById(R.id.imageEntertainment);
        imgOffice = (ImageView)findViewById(R.id.imageOffice);
        imgPurchase = (ImageView)findViewById(R.id.imagePurchase);
             
        imgMoreOptions = (ImageView)findViewById(R.id.imageMoreOptions);
        
        search = (AutoCompleteTextView)findViewById(R.id.editSearch);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, COUNTRIES);
        search.setAdapter(adapter);
        
        imgViewSearch = (ImageView)findViewById(R.id.imageSearch);
        
        buttonClassroom = (Button)findViewById(R.id.buttonClassRoom);
        buttonClassroom.setVisibility(View.GONE);
        
        //获取位置信息，加载地图
        mainMap = (MapView)findViewById(R.id.mainMap);
        mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setBearingRequired(true);
        criteria.setCostAllowed(true);
        locationProvider = mLocationManager.getBestProvider(criteria, false);
        mLocation = mLocationManager.getLastKnownLocation(locationProvider);
        if (mLocation == null){
            mLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        point = new GeoPoint((int)(mLocation.getLatitude() * 1e6), (int)(mLocation.getLongitude() * 1e6));
        
        //设置搜索框的按钮
        imgViewSearch.setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		String strBuildingName = search.getText().toString();
		int num = 0;
		for (int i = 0; i < vecBuildingSet.size(); i++){
		    if (strBuildingName.equals(vecBuildingSet.get(i).name))
			{
				num = vecBuildingSet.get(i).num;
				break;
			}
		}
	    updateSearchRes(num);
	    }
	});
        
        //设置popView的信息
        LayoutInflater inflater = (LayoutInflater)getLayoutInflater();
        popView = inflater.inflate(R.layout.overlay_pop, null);
        mainMap.addView( popView,
              new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT, MapView.LayoutParams.WRAP_CONTENT,
            null, MapView.LayoutParams.BOTTOM_CENTER));
        popView.setVisibility(View.GONE);
        
        MapController mapController = mainMap.getController();
        mainMap.setBuiltInZoomControls(true);
        mapController.setZoom(16);
        mapController.animateTo(point);
        
        //  添加overlay
        overlays = mainMap.getOverlays();
        Drawable drawable = getResources().getDrawable(R.drawable.marker);
        MyMarker overlay = new MyMarker(drawable,this);
      //设置显示/隐藏泡泡的监听器
       overlay.setOnFocusChangeListener(onFocusChangeListener);
       overlay.addOverlay(new OverlayItem(point, "your location", "I am here"));
       overlays.add(overlay); 
       
       //获得楼层的所有信息
       vecBuildingSet = readBuildingInfor("buildinginformation.xml");       
       
       //添加popView的事件响应
       popView.setOnClickListener(new OnClickListener() {     
        @Override
        public void onClick(View v) {
    	// TODO Auto-generated method stub
            Intent intent = new Intent(IBuptActivity.this, InformationDisplay.class);
            Bundle mBundle = new Bundle();
            building = vecBuildingSet.get(buildingNum);
            intent.putExtra(Constant.buildingNum, buildingNum);
            mBundle.putSerializable(Constant.building, building);
            intent.putExtras(mBundle);
	    startActivity(intent);
	    popView.setVisibility(View.GONE);
        }
    });

       
       //添ImageStudy加菜单事件
       imgStudying.setOnClickListener(new OnClickListener() {        
        @Override
        public void onClick(View v) {
    	// TODO Auto-generated method stub
            buttonClassroom.setVisibility(View.VISIBLE);
            updateMarker(Constant.typeStudying);
        }
    });
       //添加ImageDinning添加菜单事件
       imgDinning.setOnClickListener(new OnClickListener() {
        
        @Override
        public void onClick(View v) {
    	// TODO Auto-generated method stub
            updateMarker(Constant.typeDinning);
        }
    });
       //添加imgAccomodation事件
       imgAccomodation.setOnClickListener(new OnClickListener() {
        
        @Override
        public void onClick(View v) {
    	// TODO Auto-generated method stub
            updateMarker(Constant.typeAccomodation);
        }
    });
       //添加imgEntertainment事件
       imgEntertainment.setOnClickListener(new OnClickListener() {
        
        @Override
        public void onClick(View v) {
    	// TODO Auto-generated method stub
            updateMarker(Constant.typeEntertainment);
        }
    });
       /*出行暂无
       //添加imgChuxing事件
       imgChuxing.setOnClickListener(new OnClickListener() {
        
        @Override
        public void onClick(View v) {
    	// TODO Auto-generated method stub
    		updateMarker(Constant.typeChuxing);
        }
    });*/
       //添加imgOffice事件
       imgOffice.setOnClickListener(new OnClickListener() {
	        
	        @Override
	        public void onClick(View v) {
	    	// TODO Auto-generated method stub
	    		updateMarker(Constant.typeOffice);
	        }
	    });
       
       //添加imgPurchase事件
       imgPurchase.setOnClickListener(new OnClickListener() {
	        
	        @Override
	        public void onClick(View v) {
	    	// TODO Auto-generated method stub
	    		updateMarker(Constant.typePurchase);
	        }
	    });
       //添加buttonClassroom事件
       buttonClassroom.setOnClickListener(new OnClickListener() {
        
        @Override
        public void onClick(View v) {
    	// TODO Auto-generated method stub
            Intent intent = new Intent(IBuptActivity.this, Classroom.class);
            intent.putExtra("classroom", strClassroomChosen);
            startActivity(intent);
    		
        }
    });
    }
    
    //读入所有楼层的信息
    public Vector<Building>readBuildingInfor(String strFileName){
	
	InputStream is;
	Vector<Building> vecBuilding = new Vector<Building>();
	try {
	    is = getResources().getAssets().open(strFileName);
	    ReadXML readXML = new ReadXML(is);
	    vecBuilding = readXML.viewXML();
	} 
	catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return vecBuilding;
    }
    
    public void updateSearchRes(int num){
	Drawable drawable = getResources().getDrawable(R.drawable.marker);
        overlays.clear();
        MyMarker overlay = new MyMarker(drawable,this);
        GeoPoint point  = null;
        overlay.setOnFocusChangeListener(onFocusChangeListener);
        double latitude = 0;
        double longitude = 0;
        
        latitude = vecBuildingSet.get(num).getLatCenter();
	longitude = vecBuildingSet.get(num).getLongCenter();
	point = new GeoPoint((int) (latitude * 1e6), (int)(longitude * 1e6));
	OverlayItem overlayItem = new OverlayItem(point, vecBuildingSet.get(num).name, "");
	overlay.addOverlay(overlayItem);
	overlays.add(overlay);
	mainMap.getController().setZoom(16);
	
	overlays.add(overlay);
	mainMap.invalidate();
//	if (point == null)
//	    point = new GeoPoint((int)(latitude * 1e6), (int)(longitude * 1e6));
        mainMap.getController().animateTo(point);
    }
    public void updateMarker(int type){
	
	Drawable drawable = getResources().getDrawable(R.drawable.marker);
        overlays.clear();
        MyMarker overlay = new MyMarker(drawable,this);
        GeoPoint point  = null;
        overlay.setOnFocusChangeListener(onFocusChangeListener);
        double latitude = 0;
        double longitude = 0;
        if (type == Constant.typeStudying){
            for (int i = 0; i < 6; i++)
            {
        	latitude = vecBuildingSet.get(i).getLatCenter();
        	longitude = vecBuildingSet.get(i).getLongCenter();
        	point = new GeoPoint((int) (latitude * 1e6), (int)(longitude * 1e6));
        	OverlayItem overlayItem = new OverlayItem(point, vecBuildingSet.get(i).name, "");
        	overlay.addOverlay(overlayItem);
        	overlays.add(overlay);
        	mainMap.getController().setZoom(16);
            }
        }
        if (type == Constant.typeDinning){
            for (int i = 37; i < 40; i++)
            {
        	latitude = vecBuildingSet.get(i).getLatCenter();
        	longitude = vecBuildingSet.get(i).getLongCenter();
        	point = new GeoPoint((int) (latitude * 1e6), (int)(longitude * 1e6));
        	OverlayItem overlayItem = new OverlayItem(point, vecBuildingSet.get(i).name, "");
        	overlay.addOverlay(overlayItem);
        	overlays.add(overlay);
        	mainMap.getController().setZoom(16);
            }
        }
        if (type == Constant.typeAccomodation){
            for (int i = 6; i < 19; i++)
            {
        	latitude = vecBuildingSet.get(i).getLatCenter();
        	longitude = vecBuildingSet.get(i).getLongCenter();
        	point = new GeoPoint((int) (latitude * 1e6), (int)(longitude * 1e6));
        	OverlayItem overlayItem = new OverlayItem(point, vecBuildingSet.get(i).name, "");
        	overlay.addOverlay(overlayItem);
        	overlays.add(overlay);
        	mainMap.getController().setZoom(16);
            }
        }
        //娱乐
        if (type == Constant.typeEntertainment)
        {
            for (int i = 19; i < 23; i++)
            {
        	latitude = vecBuildingSet.get(i).getLatCenter();
        	longitude = vecBuildingSet.get(i).getLongCenter();
        	point = new GeoPoint((int) (latitude * 1e6), (int)(longitude * 1e6));
        	OverlayItem overlayItem = new OverlayItem(point, vecBuildingSet.get(i).name, "");
        	overlay.addOverlay(overlayItem);
        	overlays.add(overlay);
        	mainMap.getController().setZoom(16);
            }
            
        }
        
        //办公
        if (type == Constant.typeOffice){
            for (int i = 24; i < 30; i++){
        	latitude = vecBuildingSet.get(i).getLatCenter();
        	longitude = vecBuildingSet.get(i).getLongCenter();
        	point = new GeoPoint((int) (latitude * 1e6), (int)(longitude * 1e6));
        	OverlayItem overlayItem = new OverlayItem(point, vecBuildingSet.get(i).name, "");
        	overlay.addOverlay(overlayItem);
        	overlays.add(overlay);
        	mainMap.getController().setZoom(16);
            }
            	
        }
        //购物
        if (type == Constant.typePurchase){
            for (int i = 34; i < 37; i++){
        	latitude = vecBuildingSet.get(i).getLatCenter();
        	longitude = vecBuildingSet.get(i).getLongCenter();
        	point = new GeoPoint((int) (latitude * 1e6), (int)(longitude * 1e6));
        	OverlayItem overlayItem = new OverlayItem(point, vecBuildingSet.get(i).name, "");
        	overlay.addOverlay(overlayItem);
        	overlays.add(overlay);
        	mainMap.getController().setZoom(16);
            }
        }
        //出行
        if (type == Constant.typeChuxing){

        }
        
        overlays.add(overlay);
	mainMap.invalidate();
	if (point == null)
	    point = new GeoPoint((int)(latitude * 1e6), (int)(longitude * 1e6));
        mainMap.getController().animateTo(point);
}
    @Override
    protected boolean isRouteDisplayed() {
	// TODO Auto-generated method stub
	return false;
    }
}