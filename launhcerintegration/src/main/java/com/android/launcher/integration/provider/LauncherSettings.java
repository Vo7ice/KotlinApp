package com.android.launcher.integration.provider;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author huguojin
 * @date 2018/4/8
 */

public class LauncherSettings {

    public static class ProviderConfig {
        private static final String AUTHORITY = "com.android.launcher3.settings".intern();
    }

    static abstract interface ChangeLogColumns extends BaseColumns {
        public static final String MODIFIED = "modified";
    }

    public static abstract interface BaseLauncherColumns extends ChangeLogColumns {
        public static final String ICON = "icon";
        public static final String ICON_ID = "iconId";
        public static final String ICON_PACKAGE = "iconPackage";
        public static final String ICON_RESOURCE = "iconResource";
        public static final String ICON_TYPE = "iconType";
        public static final int ICON_TYPE_BITMAP = 1;
        public static final int ICON_TYPE_RESOURCE = 0;
        public static final String INTENT = "intent";
        public static final String ITEM_TYPE = "itemType";
        public static final int ITEM_TYPE_APPLICATION = 0;
        public static final int ITEM_TYPE_SHORTCUT = 1;
        public static final String TITLE = "title";
    }

    public static final class AppWidgets {
        public static final Uri CONTENT_URI = Uri.parse("content://" + ProviderConfig.AUTHORITY + "/appwidgets");
        public static final String EXTRA_APPWIDGET_IDS = "appwidget_ids";
        public static final String METHOD_REMOVE_APPWIDGET_IDS = "remove_appwidget_ids";
    }

    public static final class WorkspaceScreens implements ChangeLogColumns {
        public static final String SCREEN_RANK = "screenRank";
        public static final String TABLE_NAME = "workspaceScreens";
        public static final Uri CONTENT_URI = Uri.parse("content://" + ProviderConfig.AUTHORITY + "/" + TABLE_NAME);
    }

    public static final class Settings {
        public static final Uri CONTENT_URI = Uri.parse("content://" + ProviderConfig.AUTHORITY + "/settings");
        public static final String EXTRA_DEFAULT_VALUE = "default_value";
        public static final String EXTRA_VALUE = "value";
        public static final String METHOD_GET_BOOLEAN = "get_boolean_setting";
        public static final String METHOD_SET_BOOLEAN = "set_boolean_setting";
        public static final String METHOD_REMOVE_GHOST_WIDGETS = "remove_ghost_widgets";
    }

    public static final class Favorites implements BaseLauncherColumns {

        public static final String TABLE_NAME = "favorites";

        public static final String APPWIDGET_ID = "appWidgetId";
        public static final String APPWIDGET_PROVIDER = "appWidgetProvider";

        public static final String SCREEN = "screen";
        public static final String CELLX = "cellX";
        public static final String CELLY = "cellY";
        public static final String SPANX = "spanX";
        public static final String SPANY = "spanY";
        public static final String CONTAINER = "container";

        public static final String PROFILE_ID = "profileId";
        public static final String RESTORED = "restored";
        public static final String OPTIONS = "options";
        public static final String RANK = "rank";

        @Deprecated
        static final String DISPLAY_MODE = "displayMode";
        @Deprecated
        static final String IS_SHORTCUT = "isShortcut";

        public static final int ITEM_TYPE_FOLDER = 2;
        public static final int ITEM_TYPE_APPWIDGET = 4;

        @Deprecated
        public static final int ITEM_TYPE_WIDGET_CLOCK = 1000;
        @Deprecated
        public static final int ITEM_TYPE_WIDGET_SEARCH = 1001;
        @Deprecated
        public static final int ITEM_TYPE_WIDGET_PHOTO_FRAME = 1002;

        private static final String URI_PARAM_IS_EXTERNAL_ADD = "isExternalAdd";

        public static final Uri CONTENT_URI = Uri.parse("content://" + ProviderConfig.AUTHORITY + "/" + TABLE_NAME);
        public static final Uri CONTENT_URI_EXTERNAL_ADD = Uri.parse("content://" + ProviderConfig.AUTHORITY
                + "/" + TABLE_NAME + "?" + URI_PARAM_IS_EXTERNAL_ADD + "=true");

        public static final int CONTAINER_DESKTOP = -100;
        public static final int CONTAINER_HOTSEAT = -101;

        public static final String containerToString(int container) {
            switch (container) {
                case CONTAINER_DESKTOP:
                    return "desktop";
                case CONTAINER_HOTSEAT:
                    return "hotseat";
                default:
                    return String.valueOf(container);
            }
        }

        public static final String itemTypeToString(int type) {
            switch (type) {
                case ITEM_TYPE_APPLICATION:
                    return "APP";
                case ITEM_TYPE_SHORTCUT:
                    return "SHORTCUT";
                case ITEM_TYPE_FOLDER:
                    return "FOLDER";
                case ITEM_TYPE_APPWIDGET:
                    return "WIDGET";
                default:
                    return String.valueOf(type);
            }
        }

        public static Uri getContentUri(long paramLong) {
            return Uri.parse("content://" + ProviderConfig.AUTHORITY + "/" + "favorites" + "/" + paramLong);
        }

        public static void addTableToDb(SQLiteDatabase db, long myProfileId, boolean optional) {
            String ifNotExists = optional ? " IF NOT EXISTS " : "";
            db.execSQL("CREATE TABLE " + ifNotExists + TABLE_NAME + " (" +
                    "_id INTEGER PRIMARY KEY," +
                    "title TEXT," +
                    "intent TEXT," +
                    "container INTEGER," +
                    "screen INTEGER," +
                    "cellX INTEGER," +
                    "cellY INTEGER," +
                    "spanX INTEGER," +
                    "spanY INTEGER," +
                    "itemType INTEGER," +
                    "appWidgetId INTEGER NOT NULL DEFAULT -1," +
                    "iconPackage TEXT," +
                    "iconResource TEXT," +
                    "icon BLOB," +
                    "appWidgetProvider TEXT," +
                    "modified INTEGER NOT NULL DEFAULT 0," +
                    "restored INTEGER NOT NULL DEFAULT 0," +
                    "profileId INTEGER DEFAULT " + myProfileId + "," +
                    "rank INTEGER NOT NULL DEFAULT 0," +
                    "options INTEGER NOT NULL DEFAULT 0" +
                    ");");
        }
    }
}
