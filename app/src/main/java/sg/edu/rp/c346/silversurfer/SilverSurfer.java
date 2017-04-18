package sg.edu.rp.c346.silversurfer;

import android.app.Application;
import android.os.Bundle;

import com.firebase.client.Firebase;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by 15017523 on 1/15/2017.
 */

public class SilverSurfer extends Application {
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
