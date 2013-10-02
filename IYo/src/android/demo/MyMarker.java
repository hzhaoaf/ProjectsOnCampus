package android.demo;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MyMarker extends ItemizedOverlay<OverlayItem>{
    private ArrayList<OverlayItem> overlays = new ArrayList<OverlayItem>();
    private Context mContext;
	public MyMarker(Drawable defaultMarker) {
		super(defaultMarker);
		// TODO Auto-generated constructor stub
	}
	public MyMarker(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;
	}

	public void addOverlay(OverlayItem overlayItem)
	{
		overlays.add(overlayItem);
		populate();
	}
	
	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return overlays.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return overlays.size();
	}
	
//	protected boolean onTap(int index) {
//		  OverlayItem item = overlays.get(index);
//		  AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
//		  dialog.setTitle(item.getTitle());
//		  dialog.setMessage(item.getSnippet());
//		  dialog.show();
//		  return true;
//		}
}
