package sisi.orange.data;


public class TrafficString {
	public static StringBuffer format(int printNum){
		StringBuffer temp=new StringBuffer();

		if(printNum<0){
			temp.append("0KB");
		}else if(0<=printNum&&printNum<1024)//С��1KB
		{
			temp.append(String.format("%.4gKB ",printNum/1024.0f));
		}
		else if(printNum<102400)//С��100KB
		{
			
			temp.append(String.format("%.4gKB ",printNum/1024.0f));
		}
		else if(printNum<1048576)//С��1MB
		{
			
			temp.append(String.format("%.4gKB ",printNum/1024.0f));
		}
		else//����1MB
		{			
			temp.append(String.format("%.4gMB ",printNum/1048576.0f));
		}
		return temp;
	}
}
