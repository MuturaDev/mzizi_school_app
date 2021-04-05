package ultratude.com.mzizi;

import android.app.Application;
import android.provider.MediaStore;

import com.codewaves.youtubethumbnailview.ThumbnailLoader;

public class MziziSchoolApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ThumbnailLoader.initialize(getApplicationContext());
    }
}
