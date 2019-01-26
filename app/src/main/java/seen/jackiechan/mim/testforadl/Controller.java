package seen.jackiechan.mim.testforadl;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

public class Controller extends MultiDexApplication {

    public static final String TAG = Controller.class.getSimpleName();
    private static Controller controller;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        controller = this;

        try
        {
            FontsOverride.setDefaultFont(this, "DEFAULT", "fonts/iransans_bold.ttf");
            FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/iransans_light.ttf");
            FontsOverride.setDefaultFont(this, "SERIF", "fonts/iransans_bold.ttf");
            FontsOverride.setDefaultFont(this, "SANS_SERIF", "fonts/iransans_medium.ttf");
        }
        catch (Exception err)
        {
            Log.e("Controller", "font change error", err);
        }

    }

    public static synchronized Controller getPermission() {
        return controller;
    }

}
