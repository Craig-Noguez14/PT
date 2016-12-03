package infinitetides.phantomtactics.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;

import infinitetides.phantomtactics.R;
import infinitetides.phantomtactics.util.Bootstrapper;

public class LoginActivity extends Activity implements  View.OnClickListener {

    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(Bootstrapper.getGoogleApiHelper().isConnected())
        {
            //Get google api client
            mGoogleApiClient = Bootstrapper.getGoogleApiHelper().mGoogleApiClient;
        }

        findViewById(R.id.sign_in_button).setOnClickListener(this);
    }

    /*@Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart(): Connecting to Google APIs");
       // mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop(): Disconnecting from Google APIs");
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d(TAG, "onConnected(): Connection successful");

        // As a demonstration, we are registering this activity as a handler for
        // invitation and match events.

        // This is *NOT* required; if you do not register a handler for
        // invitation events, you will get standard notifications instead.
        // Standard notifications may be preferable behavior in many cases.
        //Games.Invitations.registerInvitationListener(mGoogleApiClient, this); //TODO: MOVE TO MAIN MENU?

        // Likewise, we are registering the optional MatchUpdateListener, which
        // will replace notifications you would get otherwise. You do *NOT* have
        // to register a MatchUpdateListener.
        //Games.TurnBasedMultiplayer.registerMatchUpdateListener(mGoogleApiClient, this); //TODO: MOVE TO MAIN MENU?
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended():  Trying to reconnect.");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed(): attempting to resolve");
        if (mResolvingConnectionFailure) {
            // Already resolving
            Log.d(TAG, "onConnectionFailed(): ignoring connection failure, already resolving.");
            return;
        }

        // Launch the sign-in flow if the button was clicked or if auto sign-in is enabled
        if (mAutoStartSignInFlow) {
            mAutoStartSignInFlow = false;

            mResolvingConnectionFailure = GameUtils.resolveConnectionFailure(this,
                    mGoogleApiClient, connectionResult, RC_SIGN_IN,
                    getString(R.string.signin_other_error));
        }
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                findViewById(R.id.sign_in_button).setVisibility(View.GONE);
                mGoogleApiClient.connect();
                Intent intent = new Intent(this, MainMenuActivity.class);
                startActivity(intent);
                break;
        }
    }

}
