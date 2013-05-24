package sisi.orange.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sisi.orange.view.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AppListAdapter extends BaseAdapter {

	public List<AppInfoItem> list=null;
	private LayoutInflater mInflater;
	private Context context;
	
	public AppListAdapter(Context context)
	{
		this.context=context;
		mInflater=LayoutInflater.from(context);
	}
	
	public void setList(List<AppInfoItem> applist)
	{
		this.list=applist;
	}
	
	public int getCount() {
		return (list==null) ? 0 : list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	private Map<Integer,View> viewMap=new HashMap<Integer,View>();
	
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		convertView=this.viewMap.get(position);
		
		if(convertView==null){
			convertView=mInflater.inflate(R.layout.app_item, null);
			holder=new ViewHolder();
			holder.imgAppIcon=(ImageView)convertView.findViewById(R.id.app_icon);
			holder.txtAppName=(TextView)convertView.findViewById(R.id.app_name);
			holder.txtRxBytes=(TextView)convertView.findViewById(R.id.txt_rxbytes);
			holder.txtTxBytes=(TextView)convertView.findViewById(R.id.txt_txbytes);
			//holder.txtIsCustom=(TextView)convertView.findViewById(R.id.IsCustom);
			convertView.setTag(holder);
			viewMap.put(position, convertView);
		} else {
			holder=(ViewHolder)convertView.getTag();
		}
		AppInfoItem appinfo=(AppInfoItem)getItem(position);
		if(appinfo!=null)
		{
			holder.imgAppIcon.setImageDrawable(appinfo.getIcon());
			holder.txtAppName.setText(appinfo.getName());
			holder.txtRxBytes.setText(context.getText(R.string.DOWN)+appinfo.getRxBytesFormat());
			holder.txtTxBytes.setText(context.getText(R.string.UP)+appinfo.getTxBytesFormat());
		}
		return convertView;
		
	}
	
	static class ViewHolder
	{
		TextView txtAppName;
		ImageView imgAppIcon;
		TextView txtRxBytes;
		TextView txtTxBytes;
		TextView txtIsCustom;
	}

}
