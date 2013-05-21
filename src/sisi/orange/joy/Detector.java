package sisi.orange.joy;
import sisi.orange.view.R;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
public class Detector implements OnGestureListener{
	public final static int LEFT =R.layout.first;
	public final static int RIGHT =R.layout.first+1;
	public final static int UP =R.layout.first+2;
	public final static int DOWN =R.layout.first+3;
	public final static int VERT =R.layout.first+4;
	public final static int LONGPRESS =R.layout.first+5;
	private Handle hand;
	private float[] minmax = new float[6];
	private float mX,mY;
	private int result;
	private static int wid,hei;
	private GestureDetector gd;
	private static final float TOUCH_TOLERANCE = 5;
	public Detector(int w,int h){
		wid=w;
		hei=h;
		gd = new GestureDetector(this);
	}
	
    public boolean onTouchEvent(MotionEvent event) {
    	  float x = event.getX();
          float y = event.getY();
          switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
        	  minmax[0]=minmax[2]=x;
        	  minmax[1]=minmax[3]=y;
        	  break;
          case MotionEvent.ACTION_MOVE:
        	  touch_move(x, y);
        	  break;
          case MotionEvent.ACTION_UP:
        	  minmax[4]=x;
        	  minmax[5]=y;
        	  judge(hand);
        	  break;
          }
          return gd.onTouchEvent(event);
    }
	
	private void touch_move(float x, float y) {
		// TODO Auto-generated method stub
		float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            if((minmax[2]*minmax[2]+(minmax[3]-hei)*(minmax[3]-hei))>(x*x+(y-hei)*(y-hei)))
            {
            	minmax[2]=x;
            	minmax[3]=y;
            }
            mX = x;
            mY = y;
        }
	}
	private void judge(Handle de){
		float[] cor = minmax; 
		float x = cor[2]-cor[0];
		float y = cor[3]-cor[5];
		double dis = Math.sqrt((x*x+y*y));
		float cube = Math.min(wid, hei);
		int i=0;		
		if(cor[4]-cor[0]<cube/8){
			i=i|1;
		}
	    if(cor[5]-cor[1]<cube/6){	  
	    	i=i|2;	    	
	    }
	    if((x>0&&y<0)||dis>cube/4){
			i=i|4;
		}
	    if(i==0){
	    	result = VERT;
	    }
	    if(hand!=null)
	    	hand.hand(result,i);
	    result=0;
	}
	
	public void setListener(Handle hand) {
		// TODO Auto-generated method stub
		this.hand = hand;
	}
	
	public interface Handle{
		public void hand(int result,int reason);
	}

	

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		result = LONGPRESS;
		if(hand!=null)
	    	hand.hand(result,0);
	    result=0;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	public void recycle(){
		gd=null;
		wid=0;
		hei=0;
		result=0;
		mY=0;
		mX=0;
		minmax=null;
		hand = null;
	}
}
