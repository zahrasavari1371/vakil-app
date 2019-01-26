package seen.jackiechan.mim.testforadl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * authored by https://twitter.com/96rajabi
 * MrSQl v 2.1
 */
public class MrSQl extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mehrGostar";


    private static final String[] FILTER_COLUMNS = {"$change", "serialVersionUID", "id"};

    public MrSQl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONFIG_TABLE = "CREATE TABLE config(id INTEGER PRIMARY KEY , name TEXT,value TEXT)";

        String CREATE_COUNTRY_TABLE = "CREATE TABLE country (id INTEGER PRIMARY KEY, iCountryId INTEGER, vCountry TEXT," +
                "vCountryCode TEXT, vCountryCodeISO_3 TEXT, vPhoneCode TEXT, eStatus TEXT)";

        String CREATE_INTROS_TABLE = "CREATE TABLE intros (id INTEGER PRIMARY KEY, intro_id INTEGER, title TEXT," +
                "content TEXT, picture TEXT, created_at TEXT, updated_at TEXT)";

        db.execSQL(CREATE_CONFIG_TABLE);
        db.execSQL(CREATE_INTROS_TABLE);
        db.execSQL(CREATE_COUNTRY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS intros");
        db.execSQL("DROP TABLE IF EXISTS config");
        db.execSQL("DROP TABLE IF EXISTS country");
        onCreate(db);
    }

    private String insertQueryBuilder(Object model) {
        Field[] fields = model.getClass().getDeclaredFields();
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (Field field : fields) {
            try {
                if (!Arrays.asList(FILTER_COLUMNS).contains(field.getName())) {
                    columns.append(field.getName()).append(", ");
                    values.append("'").append(field.get(model)).append("', ");
                }
            } catch (Exception err) {
                Log.e("MrSQl", "insertQueryBuilder", err);
            }
        }
        return "INSERT INTO " + model.getClass().getSimpleName().toLowerCase() + columns.substring(0, columns.length() - 2) + ") values " + values.substring(0, values.length() - 2) + ")";
    }

    private String getModelValue(Object model, String name) {
        Field[] fields = model.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                if (!Arrays.asList(FILTER_COLUMNS).contains(name)) {
                    return String.valueOf(field.get(model));
                }
            } catch (Exception err) {
                Log.e("MrSQl", "insertQueryBuilder", err);
            }
        }
        return null;
    }

    private ContentValues insertContentValues(Object model) {
        ContentValues values = new ContentValues();
        Field[] fields = model.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                if (!Arrays.asList(FILTER_COLUMNS).contains(field.getName())) {
                    values.put(field.getName(), String.valueOf(field.get(model)));
                }
            } catch (Exception err) {
                Log.e("MrSQl", "insertContentValues", err);
            }
        }
        return values;
    }

    private boolean executeQuery(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result;
        Cursor cursor = db.rawQuery(query, null);
        result = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return result;
    }

    public void insert(Object model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = insertContentValues(model);
        db.insert(model.getClass().getSimpleName().toLowerCase(), null, values);
        db.close();
    }

    public void update(Object model, String column) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = insertContentValues(model);
        db.update(model.getClass().getSimpleName().toLowerCase(), values, column + "='" + getModelValue(model, column) + "'", null);
        db.close();
    }

    public void delete(Object model, String column) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] strings = new String[]{getModelValue(model, column)};
        db.delete(model.getClass().getSimpleName().toLowerCase(), column + " = ?", strings);
        db.close();
    }

    public boolean exists(Class model, String column, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + model.getSimpleName().toLowerCase() + " WHERE " + column + "='" + value + "'", null);
        boolean exist = cursor.getCount() > 0;
        cursor.close();
        return exist;
    }

    public void updateOrCreate(Object model, String column) {
        if (exists(model.getClass(), column, getModelValue(model, column)))
            update(model, column);
        else
            insert(model);
    }

    public <T> void updateOrCreate(List<T> models, String column) {
        for (int i = 0; i < models.size(); i++) {
            updateOrCreate(models.get(i), column);
        }
    }

    public <T> List<T> all(Class<T> model) {
        List<T> objects = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Field[] fields = model.getDeclaredFields();
        String query = "SELECT * FROM " + model.getSimpleName().toLowerCase() + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                JSONObject jsonObject = new JSONObject();
                for (Field field : fields) {
                    try {
                        jsonObject.put(field.getName(), cursor.getString(cursor.getColumnIndex(field.getName())));
                    } catch (Exception ignored) {
                    }
                }
                Gson gson = new Gson();
                objects.add(gson.fromJson(jsonObject.toString(), model));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return objects;
    }

    public <T> T get(Class<T> model, String column, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        Field[] fields = model.getDeclaredFields();
        Cursor cursor = db.rawQuery("SELECT * FROM " + model.getSimpleName().toLowerCase() + " WHERE " + column + " = '" + value + "';", null);

        if (cursor != null)
            cursor.moveToFirst();

        try {
            if (cursor == null) return null;

            JSONObject jsonObject = new JSONObject();
            for (Field field : fields) {
                try {
                    jsonObject.put(field.getName(), cursor.getString(cursor.getColumnIndex(field.getName())));
                } catch (Exception ignored) {
                }
            }
            Gson gson = new Gson();
            cursor.close();
            return gson.fromJson(jsonObject.toString(), model);
        } catch (Exception err) {
            if (!cursor.isClosed())
                cursor.close();
            return null;
        }
    }
}
