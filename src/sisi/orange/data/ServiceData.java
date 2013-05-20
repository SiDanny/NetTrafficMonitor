package sisi.orange.data;

public class ServiceData {	
	/**0*/
	private int sum;
	/**-1����ǰ�����������������ⲻ�����޸�*/ 
	private int history ;//��λByte
	/**-2ÿ������״̬�ı�ʱ����ӦTrafficStats�������㣬���ɸ����ݽ��д洢*/
	private int tempTraffic ;
	/**-3��ʱ����*/
	private int DateDay =0;	
	/**-4������֪ͨ�ĸ�����������¼�ϴ�֪ͨ��������*/		
	private int notifyData = 0;
	/**-5*/
	private int NOTIFI_SHOW_FLAG = Data.AUTO;
	/**-6Ƶ��֪ͨ��λ*/
	private int notifyUnite = 0;	
	/**-7���ڻ��ֽ���ʹ�õ���*/
	private int line =0;
	/**-8�ƶ������Ƿ����*/
	private boolean netAvaiable = false;
	/**���������*/
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
