package sisi.orange.data;

public class ServiceData {	
	/**0*/
	private int sum;
	/**-1今天前所用流量，除设置外不允许修改*/ 
	private int history ;//单位Byte
	/**-2每次网络状态改变时，相应TrafficStats数据清零，故由该数据进行存储*/
	private int tempTraffic ;
	/**-3临时日期*/
	private int DateDay =0;	
	/**-4定流量通知的辅助参数，记录上次通知的数据量*/		
	private int notifyData = 0;
	/**-5*/
	private int NOTIFI_SHOW_FLAG = Data.AUTO;
	/**-6频率通知单位*/
	private int notifyUnite = 0;	
	/**-7用于划分今日使用的线*/
	private int line =0;
	/**-8移动网络是否可用*/
	private boolean netAvaiable = false;
	/**流量检测间隔*/
	private int delay = 15000;
	
	public ServiceData(long sum, long history, long tempTraffic,
			int dateDay, long notifyData, int NOTIFI_SHOW_FLAG, int backgroundIndex){
		
	}
	
	public ServiceData(){
		
	}
	
	public int getNOTIFI_SHOW_FLAG() {
		return NOTIFI_SHOW_FLAG;
	}
	public void setNOTIFI_SHOW_FLAG(int nOTIFI_SHOW_FLAG) {
		NOTIFI_SHOW_FLAG = nOTIFI_SHOW_FLAG;
	}
	public boolean isNetAvaiable() {
		return netAvaiable;
	}
	public void setNetAvaiable(boolean netAvaiable) {
		this.netAvaiable = netAvaiable;
	}

	public int getHistory() {
		return history;
	}
	public void setHistory(int history) {
		this.history = history;
	}
	public int getTempTraffic() {
		return tempTraffic;
	}
	public void setTempTraffic(int tempTraffic) {
		this.tempTraffic = tempTraffic;
	}
	public int getDateDay() {
		return DateDay;
	}
	public void setDateDay(int dateday) {
		this.DateDay = dateday;
	}

	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public int getDelay() {
		return delay;
	}
	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public int getNotifyData() {
		return notifyData;
	}

	public void setNotifyData(int notifyData) {
		this.notifyData = notifyData;
	}
	
	public long getMonthUseData(){
		return history+tempTraffic;
	}
	
	public long getTodayUseData(){
		return history+tempTraffic-line;
	}

	public int getNotifyUnite() {
		return notifyUnite;
	}

	public void setNotifyUnite(int notifyUnite) {
		this.notifyUnite = notifyUnite;
	}
}
