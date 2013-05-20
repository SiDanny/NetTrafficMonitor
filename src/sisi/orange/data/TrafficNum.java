package sisi.orange.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import android.util.Log;
public class TrafficNum {
	private static final String fileName = "proc/self/net/dev";
	
	public synchronized static int getNum(Boolean isMobile, int basic){
		
		return deal1(getString(), isMobile,basic);
	}
	
	private static String getString(){
		File file = new File(fileName);
		StringBuffer buf = new StringBuffer();
		BufferedReader reader = null;
		try {
		    reader = new BufferedReader(new FileReader(file));
		    String tempString = null;
		    while ((tempString = reader.readLine()) != null){
		    	buf.append(tempString);
		    	buf.append('\n');
		    }
		    reader.close();
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null){
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return buf.toString();
	}
	
	private static int deal1(String info, Boolean isMobile,int basic) {
		// TODO Auto-generated method stub
		String[] flag;
		if(isMobile)
			flag = new String[]{"ccinet0:", "rmnet0:"};
		else
			flag = new String[]{"mlan0:", "eth0:"};
		int[] data = new int[]{-1,-1};		
		int index=-1, indexlast = -1;
		for(String str : flag){
			index = info.indexOf(str);
			if(index!=-1){
				index +=str.length();
				if(str.equals("rmnet0:"))
					basic = 0;
				break;
			}	
		}
		if(index==-1)
		{
			Log.e("num","String erro");
			return basic;
		}
		while(info.charAt(index)>'9'||info.charAt(index)<'1')
			index++;
		indexlast = info.indexOf(' ',index);
		try {
			data[0] = Integer.parseInt(info.substring(index,indexlast));
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			Log.e("TrafficNum","0 "+info.substring(index,indexlast+10)+" "+indexlast);
			e1.printStackTrace();
		}
		while(info.charAt(indexlast)>'9'||info.charAt(indexlast)<'0')
			indexlast++;
		try {
			data[1] = Integer.parseInt(info.substring(indexlast,info.indexOf(' ',indexlast)-1));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			Log.e("TrafficNum","1 "+info.substring(indexlast,info.indexOf(' ',indexlast)+10)+" "+indexlast);
			e.printStackTrace();
		}
	//	Log.v("TrafficNum",data[0]+" "+data[1]+" "+basic);
		basic =  data[0]+data[1]+basic;		
		return basic;
	}
}

