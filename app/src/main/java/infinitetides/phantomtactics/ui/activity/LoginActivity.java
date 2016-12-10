package infinitetides.phantomtactics.ui.activity;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.api.GoogleApiClient;

import infinitetides.phantomtactics.R;
import infinitetides.phantomtactics.util.GoogleApiHelper;

public class LoginActivity extends AppCompatActivity implements  View.OnClickListener, GoogleApiHelper.ConnectionCallbacks {
    public static final String TAG = "LoginActivity";
    public static final int REQUEST_GOOGLE_API_CLIENT_CONNECT = 6;
    private static final String GOOGLE_API_CLIENT_FRAGMENT = "GOOGLE_API_CLIENT_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.sign_in_button).setOnClickListener(this);
        Log.d(TAG, "onCreate(): In Login");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart(): Connecting to Google APIs");
       // mGoogleApiClient.connect();

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop(): Disconnecting from Google APIs");
        //mGoogleApiClient.disconnect();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
                if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {

                    String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    String accountType = data.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);
                    if (accountName != null && accountType != null) {
                        GoogleApiHelper googleApiClientFragment;

                        googleApiClientFragment = GoogleApiHelper.newInstance(accountName, 5);

                        FragmentManager fm = this.getSupportFragmentManager();
                        Fragment oldFragment = fm.findFragmentByTag(GOOGLE_API_CLIENT_FRAGMENT);
                        if (oldFragment != null) {
                            fm.beginTransaction().remove(oldFragment).commitAllowingStateLoss();
                        }
                        fm.beginTransaction().add(googleApiClientFragment, GOOGLE_API_CLIENT_FRAGMENT).commitAllowingStateLoss();
                    }
                }
    }

    @Override
    public void onConnected(GoogleApiClient googleApiClient, int action) {
        // It is our responsible to call googleApiClient.disconnect, not GoogleApiClientFragment.
        FragmentManager fm = this.getSupportFragmentManager();
        Fragment oldFragment = fm.findFragmentByTag(GOOGLE_API_CLIENT_FRAGMENT);
        if (oldFragment != null) {
            fm.beginTransaction().remove(oldFragment).commitAllowingStateLoss();
        }

        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra("accountName", AccountManager.KEY_ACCOUNT_NAME);
        startActivity(intent);
    }

    @Override
    public void onCancel(int action) {
        FragmentManager fm = this.getSupportFragmentManager();
        Fragment oldFragment = fm.findFragmentByTag(GOOGLE_API_CLIENT_FRAGMENT);
        if (oldFragment != null) {
            fm.beginTransaction().remove(oldFragment).commitAllowingStateLoss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                startActivityForResult(AccountPicker.newChooseAccountIntent(null,
                        null, new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE}, true, null, null, null, null),
                        0);
                break;
        }
    }

}
