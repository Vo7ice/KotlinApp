package com.android.launcher.integration.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;


/**
 * @author huguojin
 */
public class ImportProvider extends ContentProvider {
    public ImportProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        private static final int SCHEMA_VERSION = 1;
        private static final String TAG = "DatabaseHelper";
        private static final boolean LOGD = true;

        private Context mContext;
        private long mMaxItemId;
        private long mMaxScreenId;


        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public DatabaseHelper(Context context, String tableName, int version) {
            super(context, tableName, null, SCHEMA_VERSION);
            if (tableExists(LauncherSettings.Favorites.TABLE_NAME) || tableExists(LauncherSettings.WorkspaceScreens.TABLE_NAME)) {
                Log.e(TAG, "Tables are missing after onCreate has been called. Trying to recreate");
                // This operation is a no-op if the table already exists.
                addFavoritesTable(getWritableDatabase(), true);
                addWorkspacesTable(getWritableDatabase(), true);
            }

            initIds();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            if (LOGD) {
                Log.d(TAG, "creating new launcher database");
            }
            mMaxItemId = 1;
            mMaxScreenId = 0;

            addFavoritesTable(db, false);
            addWorkspacesTable(db, false);

            // Fresh and clean launcher DB.
            mMaxItemId = initializeMaxItemId(db);
            // onEmptyDbCreated();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }


        private boolean tableExists(String tableName) {
            Cursor c = getReadableDatabase().query(
                    true, "sqlite_master", new String[]{"tbl_name"},
                    "tbl_name = ?", new String[]{tableName},
                    null, null, null, null, null);
            try {
                return c.getCount() > 0;
            } finally {
                c.close();
            }
        }

        private void addFavoritesTable(SQLiteDatabase db, boolean optional) {
            LauncherSettings.Favorites.addTableToDb(db, 0, optional);
        }

        private void addWorkspacesTable(SQLiteDatabase db, boolean optional) {
            String ifNotExists = optional ? " IF NOT EXISTS " : "";
            db.execSQL("CREATE TABLE " + ifNotExists + LauncherSettings.WorkspaceScreens.TABLE_NAME + " (" +
                    LauncherSettings.WorkspaceScreens._ID + " INTEGER PRIMARY KEY," +
                    LauncherSettings.WorkspaceScreens.SCREEN_RANK + " INTEGER," +
                    LauncherSettings.ChangeLogColumns.MODIFIED + " INTEGER NOT NULL DEFAULT 0" +
                    ");");
        }

        private void initIds() {
            // In the case where neither onCreate nor onUpgrade gets called, we read the maxId from
            // the DB here
            if (mMaxItemId == -1) {
                mMaxItemId = initializeMaxItemId(getWritableDatabase());
            }
            if (mMaxScreenId == -1) {
                mMaxScreenId = initializeMaxScreenId(getWritableDatabase());
            }
        }

        private long initializeMaxItemId(SQLiteDatabase db) {
            return getMaxId(db, LauncherSettings.Favorites.TABLE_NAME);
        }

        private long initializeMaxScreenId(SQLiteDatabase db) {
            return getMaxId(db, LauncherSettings.WorkspaceScreens.TABLE_NAME);
        }

        static long getMaxId(SQLiteDatabase db, String table) {
            Cursor c = db.rawQuery("SELECT MAX(_id) FROM " + table, null);
            // get the result
            long id = -1;
            if (c != null && c.moveToNext()) {
                id = c.getLong(0);
            }
            if (c != null) {
                c.close();
            }

            if (id == -1) {
                throw new RuntimeException("Error: could not query max id in " + table);
            }

            return id;
        }
    }
}
