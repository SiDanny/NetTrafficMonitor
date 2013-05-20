package sisi.orange.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/***
 * ����װʱ����ʹ�����º�����ֻ�е�getContentProvide����ʱ�����������򴴽�
 * ����װʱ�����ڸ����ݿ⣬��ʹ��onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
 * ����newVersion Ϊ���캯���е���ֵ
 *
 */
public class DBlite extends SQLiteOpenHelper { 
	private final static String sqlsqlCreateListTable =  "create table "+DBStr.LIST_TNAME+" ("+
			DBStr.UID+" integer primary key not null, "+
			DBStr.LABEL+" char(20) not null,"+
			DBStr.PACKAGE+" char(20) ,"+
	        DBStr.ICON+" blob, "+
	        DBStr.UPNUM+" long , "+
	        DBStr.DOWNNUM+" long );";
	private final static String sqlCreateTypeTable =  "create table "+DBStr.TNAME+" ("+
			DBStr.DAY+" integer primary key not null, "+
    		DBStr.MOBILE+" integer default 0 , "+
			DBStr.WIFI+" integer default 0);";
	
    public DBlite(Context context) { 
            super(context, DBStr.DBNAME, null, DBStr.VERSION);             
    } 
    
    @Override
    public void onCreate(SQLiteDatabase db) { 
            // TODO Auto-generated method stub 
    	
    	db.execSQL(sqlsqlCreateListTable);
    	db.execSQL(sqlCreateTypeTable);
    	ContentValues values = new ContentValues();
    	for(int i=-9; i!=32; i++){
    		values.put(DBStr.DAY, i);
    		db.insert(DBStr.TNAME, null, values);
    	}
    } 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { 
    	// TODO Auto-generated method stub 
    	//db.execSQL("drop database if exists");
    	switch (oldVersion){
    		case 7:{
    			db.execSQL(sqlsqlCreateListTable);
    	    	db.execSQL(sqlCreateTypeTable);
    	    	ContentValues values = new ContentValues();

    			break;
    		}
    		default:
    			break;
    	}
    	
    }  
}
