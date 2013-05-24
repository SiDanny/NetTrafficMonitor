package sisi.orange.data;

public class TraList {
	public static int[] getUpDown(int uid){
		int[] updown = new int[2];
		updown[1] = (int) android.net.TrafficStats.getUidRxBytes(uid);
		updown[0] = (int) android.net.TrafficStats.getUidTxBytes(uid);
		return updown;
	}
}
