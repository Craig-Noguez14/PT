package infinitetides.phantomtactics.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;

import infinitetides.phantomtactics.R;
import infinitetides.phantomtactics.util.Bootstrapper;

public class MainMenuActivity extends Activity  {

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        if(Bootstrapper.getGoogleApiHelper().isConnected())
        {
            //Get google api client
            mGoogleApiClient = Bootstrapper.getGoogleApiHelper().mGoogleApiClient;
        }
    }

    public void onSignOutClicked(View view) {
        mGoogleApiClient.disconnect();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
