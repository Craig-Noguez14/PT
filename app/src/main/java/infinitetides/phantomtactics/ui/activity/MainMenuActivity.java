package infinitetides.phantomtactics.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;

import infinitetides.phantomtactics.R;
import infinitetides.phantomtactics.util.GoogleApiHelper;

public class MainMenuActivity extends Activity  {

    private GoogleApiHelper googleApiHelper;
    private  GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        Intent intent = getIntent();
        googleApiHelper = GoogleApiHelper.newInstance(intent.getStringExtra("accountName"), 2);
        mGoogleApiClient = googleApiHelper.mGoogleApiClient;
    }

    public void onSignOutClicked(View view) {
        mGoogleApiClient.disconnect();
        googleApiHelper.mGoogleApiClient = null;
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
