package sisi.orange.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * 125行会在初次启动出错
 * @author Administrator
 *
 */
public class MyProvider extends ContentProvider{
	DBlite dBlite;         
	SQLiteDatabase db;
	private static final UriMatcher sMatcher;        
	static{         
		sMatcher = new UriMatcher(UriMatcher.NO_MATCH);                 
		sMatcher.addURI(DBStr.AUTOHORITY,DBStr.TNAME, DBStr.ITEM); 
		sMatcher.addURI(DBStr.AUTOHORITY, DBStr.LIST_TNAME, DBStr.ITEM_ID);        
		}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		db = dBlite.getWritableDatabase();             
		int count = 0;                
		switch (sMatcher.match(uri)) 
		{         
		case DBStr.ITEM:     
			count = db.delete(DBStr.TNAME,selection, selectionArgs);  
			break;               
		case DBStr.ITEM_ID:    
			count = db.delete(DBStr.LIST_TNAME,selection, selectionArgs);              
			break;                 
		default:       
			throw new IllegalArgumentException("Unknown URI"+uri);               
		}               
		getContext().getContentResolver().notifyChange(uri, null);            
		return count; 	
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		
		switch (sMatcher.match(uri)) { 
        case DBStr.ITEM: 
            return DBStr.CONTENT_TYPE; 
        case DBStr.ITEM_ID:         
            return DBStr.CONTENT_ITEM_TYPE; 
        default: 
            throw new IllegalArgumentException("Unknown URI"+uri); 
        }
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		db = dBlite.getWritableDatabase();                     
		Cursor c=null;
		switch (sMatcher.match(uri)) {
		case DBStr.ITEM:
			//Log.e("myProvider","insert DBStr.ITEM");
			c = db.query(DBStr.TNAME, new String[]{DBStr.DAY}, DBStr.DAY+
					"=?",new String[]{values.getAsString(DBStr.DAY)}, null, null, null);
			if(c.getCount()==0){
				db.insert(DBStr.TNAME,null,values); 
			}
			break;
		case DBStr.ITEM_ID:
			c = db.query(DBStr.LIST_TNAME, new String[]{DBStr.UID}, DBStr.UID+
					"=?",new String[]{values.getAsString(DBStr.UID)}, null, null, null);
			if(c.getCount()==0){
				try {
					db.insertOrThrow(DBStr.LIST_TNAME,null,values);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI"+uri);
		}
		c.close();
		return uri;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		this.dBlite = new DBlite(this.getContext()); 
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		db = dBlite.getReadableDatabase();     
		Cursor c;                   
		switch (sMatcher.match(uri)) {             
		case DBStr.ITEM:       
			//Log.e("myProvider","query DBStr.ITEM");
			c = db.query(DBStr.TNAME, projection, selection, selectionArgs,
					null, null, sortOrder);                              
			break;                
		case DBStr.ITEM_ID:      
			//Log.e("myProvider","query DBStr.ITEM_ID");
			c = db.query(DBStr.LIST_TNAME, projection, selection,selectionArgs, null, null, sortOrder);                
			break;               
		default:                                    
			throw new IllegalArgumentException("Unknown URI"+uri);        
		}                           
		return c; 
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		db = dBlite.getWritableDatabase();
		int rows;               
		switch (sMatcher.match(uri)) {             
		case DBStr.ITEM:  
			//Log.e("myProvider","update DBStr.ITEM");
			rows = db.update(DBStr.TNAME, values, selection, selectionArgs);
			break;
		case DBStr.ITEM_ID:
			//Log.e("myProvider","update DBStr.ITEM_ID");
			rows = db.updateWithOnConflict(DBStr.LIST_TNAME, values, selection, selectionArgs, SQLiteDatabase.CONFLICT_REPLACE);
			if(rows==0)
				db.insert(DBStr.LIST_TNAME, null, values);
		default:
			rows=0;
		}		
		getContext().getContentResolver().notifyChange(uri, null);
		//Log.v("myProvider","update "+rows);
		return rows;
	}
}