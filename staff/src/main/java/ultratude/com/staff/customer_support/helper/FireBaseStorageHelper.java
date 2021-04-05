package ultratude.com.staff.customer_support.helper;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import ultratude.com.staff.customer_support.interfaces.ImageUploadListener;
import ultratude.com.staff.customer_support.util.Logger;

/**
 * Created by Prajwal on 17/07/17.
 */

public class FireBaseStorageHelper {
    private final String TAG = FireBaseStorageHelper.class.getSimpleName();
    private ImageUploadListener imageUploadListener;

    public void uploadImageToStorage(Uri imageUri, String folderName) {

        final StorageReference rootStorageRef = FirebaseStorage.getInstance().getReference();

        final UploadTask imgUploadTask = rootStorageRef.child(folderName + "/" + new File(imageUri.getLastPathSegment())).putFile(imageUri);

        imgUploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Logger.e(TAG, "Image upload failed");

                if (imageUploadListener != null) {
                    imageUploadListener.onUploaded(null);
                }
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                if (taskSnapshot.getMetadata() != null) {

                    //https://firebase.google.com/docs/storage/android/upload-files#get_a_download_url
                        Task<Uri> urlTask = imgUploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    throw task.getException();
                                }

                                // Continue with the task to get the download URL
                                return rootStorageRef.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                        Uri downloadUri = task.getResult();

                                        Logger.d(TAG, "Image URL : " + downloadUri);
                                        if (imageUploadListener != null) {
                                            imageUploadListener.onUploaded(downloadUri);
                                        }


                                } else {
                                    // Handle failures
                                    // ...
                                }
                            }
                        });


                } else {
                    if (imageUploadListener != null) {
                        imageUploadListener.onUploaded(null);
                    }
                }
            }
        });
    }

    public void setImageUploadListener(ImageUploadListener imageUploadListener) {
        this.imageUploadListener = imageUploadListener;
    }
}
