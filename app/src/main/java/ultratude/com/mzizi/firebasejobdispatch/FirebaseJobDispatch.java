package ultratude.com.mzizi.firebasejobdispatch;

import android.content.Context;
import android.os.Bundle;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import ultratude.com.mzizi.modelclasses.Student;

public class FirebaseJobDispatch {

    private Context context;


    public FirebaseJobDispatch(Context context){
        this.context = context;
    }

    public void startDispatch(final Student student ){//the data should be serializable



        // Create a new dispatcher using the Google Play driver.
        final FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));

        //Simple
//        Job myJob = dispatcher.newJobBuilder()
//                .setService(MziziJobService.class) // the JobService that will be called
//                .setTag("my-unique-tag")        // uniquely identifies the job
//                .build();
//
//        //Complex
        Bundle myExtrasBundle = new Bundle();
        myExtrasBundle.putString("StudentID", student.getStudentID());
        myExtrasBundle.putString("appcode", student.getAppcode());


        Job myJob = dispatcher.newJobBuilder()
                // the JobService that will be called
                .setService(MziziJobService.class)
                // uniquely identifies the job
                .setTag("MziziJobDispatch")
                // one-off job
                // TODO: 2020-06-08 This is creared to reduce the number of request
                .setRecurring(false)
                // don't persist past a device reboot
                .setLifetime(Lifetime.FOREVER)
                // start between 0 and 60 seconds from now
                .setTrigger(Trigger.executionWindow(0, 60))
                // don't overwrite an existing job with the same tag
                .setReplaceCurrent(false)
                // retry with exponential backoff
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                // constraints that need to be satisfied for the job to run
                .setConstraints(
                        // only run on an unmetered network
                        Constraint.ON_UNMETERED_NETWORK,

                        Constraint.ON_ANY_NETWORK
                )
                .setExtras(myExtrasBundle)
                .build();

        dispatcher.mustSchedule(myJob);
    }

}
