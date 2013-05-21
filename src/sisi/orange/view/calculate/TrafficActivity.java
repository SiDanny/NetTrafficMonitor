package sisi.orange.view.calculate;
import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import sisi.orange.data.Data;
import sisi.orange.data.ServiceData;
import sisi.orange.view.AbstractDemoChart;
import sisi.orange.view.R;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * activity必须保存ServiceConnection中传入的IBinder，onServiceConnected才会被调用
 * Service必须写入IBinder派生的内部类，并在onBinder（）中返回
 * @author Administrator
 */
public class TrafficActivity extends Activity{
	protected static final int UPDATE = R.layout.first;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    
        setContentView(R.layout.first);
        //背景
        //layout.setBackgroundDrawable(new FlowerDraw(backIndex,getResources()));	
        LinearLayout chartLayout = (LinearLayout) findViewById(R.id.chart_layout);
        chartLayout.addView(getChartGraphicalView());
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub	
		Log.v("traActivity","destory");
		super.onDestroy();
		System.gc();
	}
	
	public GraphicalView getChartGraphicalView() {
		double[][] list = Data.getListDateTrafficInfo(this);

		AbstractDemoChart abstractDemoChart = new AbstractDemoChart();
		String[] titles = new String[] { "Mobile", "WIFI"};
	    List<double[]> x = new ArrayList<double[]>();
	    for (int i = 0; i < titles.length; i++) {
	    	x.add(list[0]);
	    }
	    List<double[]> values = new ArrayList<double[]>();

	    values.add(list[1]);
	    values.add(list[2]);	    
	    int[] colors = new int[] { Color.BLUE, Color.GREEN};
	    PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.TRIANGLE };
	    XYMultipleSeriesRenderer renderer = abstractDemoChart.buildRenderer(colors, styles);
	    int length = renderer.getSeriesRendererCount();
	    for (int i = 0; i < length; i++) {
	      ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
	    }
	    //依次是颜色风格设置，顶部标题，x标题，Y标题
	    double[] miax = getMinMax(list[2], list[1]);
	    abstractDemoChart.setChartSettings(renderer, "", "Day", "Traffic", 0.5, 31.5, miax[0], miax[1],
	        Color.LTGRAY, Color.LTGRAY);
	    renderer.setXLabels(12);
	    renderer.setYLabels(10);
	    renderer.setShowGrid(true);
	    renderer.setXLabelsAlign(Align.RIGHT);
	    renderer.setYLabelsAlign(Align.RIGHT);
	    renderer.setZoomButtonsVisible(true);
	    renderer.setPanLimits(new double[] { -1, 32, -1, 32 });
	    renderer.setZoomLimits(new double[] { 0, 32, 0, 32 });
	    renderer.setMarginsColor(android.R.color.transparent);
		return ChartFactory.getLineChartView(this, 
				abstractDemoChart.buildDataset(titles, x, values), renderer);
	}

	
	
	private double[] getMinMax(double[] ds, double[] ds2) {
		// TODO Auto-generated method stub
		double[] miax = new double[]{0,0};
		for(double sor: ds){
			if(miax[0]>sor)
				miax[0] = sor;
			if(miax[1]<sor)
				miax[1] = sor;
		}
		for(double sor: ds2){
			if(miax[0]>sor)
				miax[0] = sor;
			if(miax[1]<sor)
				miax[1] = sor;
		}
		return miax;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		try {
			updateView();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.v("traActivity","resume erro");			
		}
		super.onResume();
	}

	protected void updateView() {
		// TODO Auto-generated method stub		
		ServiceData dataMobile = Data.getServiceData(this, "mobile");
		ServiceData dataWifi = Data.getServiceData(this, "wifi");
		
		showTextView(new int[]{R.id.sumView, R.id.historyView, 
				R.id.todayView, R.id.historyWifi,R.id.todayWifi}, 
				new long[]{dataMobile.getSum(), dataMobile.getMonthUseData(),
				dataMobile.getTodayUseData(), dataWifi.getMonthUseData(),
				dataWifi.getTodayUseData()});
	}
	
	private void showTextView(int[] ids,long[] targets){
		assert(ids.length == targets.length);
		TextView textView;
		for(int index=0; index!=ids.length; index++){	
			textView = ((TextView)(findViewById(ids[index])));
			textView.setText(formateFileSize(targets[index]));
		}
	}

	 //系统函数，字符串转换 long -String (kb)
    private String formateFileSize(long size){
    	return Formatter.formatFileSize(this, size<0?0:size); 
    }
}