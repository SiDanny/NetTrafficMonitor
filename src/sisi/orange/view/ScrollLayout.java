package sisi.orange.view;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
//import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;
/**
 * 仿Launcher中的WorkSapce，可以左右滑动切换屏幕的类
 * @author Yao.GUET
 * blog: http://blog.csdn.net/Yao_GUET
 * date: 2011-05-04
 */
public class ScrollLayout extends ViewGroup {
	private static final String TAG = "ScrollLayout";
	private Scroller mScroller;
	private VelocityTracker mVelocityTracker;
	
	private int mCurScreen = 0;
	private int mDefaultScreen = 0;
	
	private static final int TOUCH_STATE_REST = 0;
	private static final int TOUCH_STATE_SCROLLING = 1;
	
	private static final int SNAP_VELOCITY = 600;
	
	private int mTouchState = TOUCH_STATE_REST;
	private int mTouchSlop;
	private float mLastMotionX;
	private SnapListener mSnapListener;
	public ScrollLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
		setBackgroundColor(Color.WHITE);
		//Log.v("ScrollLayout", "default construct");
	}
	
	/***先于上面构造函数执行*/
	public ScrollLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		//Log.v("ScrollLayout", "default style construct");
		mScroller = new Scroller(context);	
		mCurScreen = mDefaultScreen;
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		int childLeft = 0;
		final int childCount = getChildCount();
		Log.v("onLayout", "onLayout count "+childCount);
		//Log.v("onLayout", "l="+l+" t="+t+" r="+r+" b="+b);
		for (int i=0; i<childCount; i++) {
			final View childView = getChildAt(i);
			if (childView.getVisibility() != View.GONE) {
				final int childWidth = childView.getMeasuredWidth();
				childView.layout(childLeft, 0, 
						childLeft+childWidth, childView.getMeasuredHeight());
				childLeft += childWidth;
			}
		}
	}
	
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {   
    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);   
  
        final int width = MeasureSpec.getSize(widthMeasureSpec);   
        // The children are given the same width and height as the scrollLayout   
        final int count = getChildCount();   
        Log.v(TAG, "onMeasure wid="+widthMeasureSpec+" hei="+heightMeasureSpec+" mCount="+count);
        for (int i = 0; i < count; i++) {   
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);   
        }   
        scrollTo(mCurScreen * width, 0);    
        
    }  
    
    /**
     * According to the position of current layout
     * scroll to the destination page.
     */
    private void snapToDestination() {
    	final int screenWidth = getWidth();
    	final int destScreen = (getScrollX()+ screenWidth/2)/screenWidth;
    	snapToScreen(destScreen);
    }
    
    private void snapToScreen(int whichScreen) {
    	// get the valid layout page
    	whichScreen = Math.max(0, Math.min(whichScreen, getChildCount()-1));
    	if (getScrollX() != (whichScreen*getWidth())) {
    		
    		final int delta = whichScreen*getWidth()-getScrollX();
    		mScroller.startScroll(getScrollX(), 0, 
    				delta, 0, Math.abs(delta)*2);
    		mCurScreen = whichScreen; 		   		
    		invalidate();		// Redraw the layout
    		if(mSnapListener!=null){
            	mSnapListener.dealSnap(mCurScreen);
            }
    	}
    }
    
    public void setToScreen(int whichScreen) {
    	whichScreen = Math.max(0, Math.min(whichScreen, getChildCount()-1));    	
    	mCurScreen = whichScreen;
    	scrollTo(whichScreen*getWidth(), 0);
    	if(mSnapListener!=null)
    		mSnapListener.dealSnap(whichScreen);
    }
    
    public int getCurScreen() {
    	return mCurScreen;
    }
    
	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
		
		final int action = event.getAction();
		final float x = event.getX();
		
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			if (!mScroller.isFinished()){
				mScroller.abortAnimation();
			}
			mLastMotionX = x;			
			break;
			
		case MotionEvent.ACTION_MOVE:
			int deltaX = (int)(mLastMotionX - x);
			mLastMotionX = x;			
            scrollBy(deltaX, 0);
			break;
			
		case MotionEvent.ACTION_UP: 
            final VelocityTracker velocityTracker = mVelocityTracker;   
            velocityTracker.computeCurrentVelocity(1000);   
            int velocityX = (int) velocityTracker.getXVelocity();  
            if (velocityX > SNAP_VELOCITY && mCurScreen > 0) {   
                // Fling enough to move left   
            	//Log.v(TAG, "snap left");
                snapToScreen(mCurScreen - 1);   
            } else if (velocityX < -SNAP_VELOCITY   
                    && mCurScreen < getChildCount() - 1) {   
                // Fling enough to move right  
            	//Log.v(TAG, "snap right");
                snapToScreen(mCurScreen + 1);   
            } else {   
                snapToDestination();  
                //Log.v(TAG, "snapToDestination");
                
            }   
            if (mVelocityTracker != null) {   
                mVelocityTracker.recycle();   
                mVelocityTracker = null;   
            }                 
            mTouchState = TOUCH_STATE_REST;   
			break;
		case MotionEvent.ACTION_CANCEL:
			mTouchState = TOUCH_STATE_REST;
			break;
		}		
		return true;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		
		final int action = ev.getAction();
		if ((action == MotionEvent.ACTION_MOVE) && 
				(mTouchState != TOUCH_STATE_REST)) {
			return true;
		}
		
		final float x = ev.getX();
		
		switch (action) {
		case MotionEvent.ACTION_MOVE:
			final int xDiff = (int)Math.abs(mLastMotionX-x);
			if (xDiff>mTouchSlop) {
				mTouchState = TOUCH_STATE_SCROLLING;
				
			}
			break;
			
		case MotionEvent.ACTION_DOWN:
			mLastMotionX = x;
			mTouchState = mScroller.isFinished()? TOUCH_STATE_REST : TOUCH_STATE_SCROLLING;
			break;
			
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			mTouchState = TOUCH_STATE_REST;
			break;
		}
		
		return mTouchState != TOUCH_STATE_REST;
	}

	public void setSnapListener(SnapListener lis){
		mSnapListener = lis;
	}
	
	public interface SnapListener{
		/** 
		 * 滑动改变试图后引发该函数，mCurScreen为结果试图索引
		 * */
		public void dealSnap(int mCurScreen);
	}
	
}

