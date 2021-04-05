package ultratude.com.mzizi.filepicker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.crashreport.Catcho;
import ultratude.com.mzizi.filepicker.config.Configurations;
import ultratude.com.mzizi.filepicker.model.MediaFile;
import ultratude.com.mzizi.filepicker.test_activity.FilePickerActivity;
import ultratude.com.mzizi.filepicker.utils.FilePickerProvider;
import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;


public class FilesActivity extends AppCompatActivity {
    private final static int FILE_REQUEST_CODE = 1;

    private ArrayList<MediaFile> mediaFiles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Catcho.Builder(this)
                .recipients(Constants.CRASH_REPORT_EMAIL)
                .build();
        setContentView(R.layout.files_activity_layout);


        findViewById(R.id.launch_imagePicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.launch_videoPicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilesActivity.this, FilePickerActivity.class);
                intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                        .setCheckPermission(true)
                        .setSelectedMediaFiles(mediaFiles)
                        .enableVideoCapture(true)
                        .setShowImages(false)
                        .setMaxSelection(10)
                        .setIgnorePaths(".*WhatsApp.*")
                        .build());
                startActivityForResult(intent, FILE_REQUEST_CODE);
            }
        });

        findViewById(R.id.launch_audioPicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilesActivity.this, FilePickerActivity.class);
//                MediaFile file = null;
//                for (int i = 0; i < mediaFiles.size(); i++) {
//                    if (mediaFiles.get(i).getMediaType() == MediaFile.TYPE_AUDIO) {
//                        file = mediaFiles.get(i);
//                    }
//                }

                //Log.d(FilesActivity.this.getPackageName().toUpperCase(), "Files is null? " + String.valueOf(file ==  null));
                intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                        .setCheckPermission(true)
                        .setSelectedMediaFiles(mediaFiles)
                        .setShowImages(false)
                        .setShowVideos(false)
                        .setShowAudios(true)
                        .setShowFiles(true)
                        //.setSingleChoiceMode(true)
                        //.setSelectedMediaFile(file)
                        .setMaxSelection(1)
                        .setRootPath(Environment.getExternalStorageDirectory().getPath() + "/")
                        .build());
                startActivityForResult(intent, FILE_REQUEST_CODE);
            }
        });

        findViewById(R.id.launch_filePicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilesActivity.this, FilePickerActivity.class);
                intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                        .setCheckPermission(true)
                        .setSelectedMediaFiles(mediaFiles)
                        .setShowFiles(true)
                        .setShowImages(false)
                        .setShowVideos(false)
                        .setMaxSelection(1)
                        .setRootPath(Environment.getExternalStorageDirectory().getPath() + "/Download")
                        .build());
                startActivityForResult(intent, FILE_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_REQUEST_CODE
                && resultCode == RESULT_OK
                && data != null) {
            mediaFiles.addAll(data.<MediaFile>getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES));
            RecyclerView recyclerView = findViewById(R.id.file_list);
             FileListAdapter   fileListAdapter = new FileListAdapter(mediaFiles, FilesActivity.this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FilesActivity.this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(fileListAdapter);

//            mediaFiles.clear();

            //fileListAdapter.notifyDataSetChanged();
            //fileListAdapter.notifyItemRangeInserted(data.<MediaFile>getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES).size(), mediaFiles.size());

        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share_log) {
            shareLogFile();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void shareLogFile() {
        File logFile = new File(getExternalCacheDir(), "logcat.txt");
        try {
            if (logFile.exists())
                logFile.delete();
            logFile.createNewFile();
            Runtime.getRuntime().exec("logcat -f " + logFile.getAbsolutePath() + " -t 100 *:W Glide:S " + FilePickerActivity.TAG);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (logFile.exists()) {
            Intent intentShareFile = new Intent(Intent.ACTION_SEND);
            intentShareFile.setType("text/plain");
            intentShareFile.putExtra(Intent.EXTRA_STREAM,
                    FilePickerProvider.getUriForFile(this, logFile));
            intentShareFile.putExtra(Intent.EXTRA_SUBJECT, "FilePicker Log File");
            intentShareFile.putExtra(Intent.EXTRA_TEXT, "FilePicker Log File");
            startActivity(Intent.createChooser(intentShareFile, "Share"));
        }
    }
}
