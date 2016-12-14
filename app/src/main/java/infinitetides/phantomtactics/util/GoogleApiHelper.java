package infinitetides.phantomtactics.util;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

import infinitetides.phantomtactics.R;
import infinitetides.phantomtactics.ui.activity.LoginActivity;

/**
 * Created by yccheok on 9/1/2016.
 */
public class GoogleApiHelper extends Fragment implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public interface ConnectionCallbacks {
        void onConnected(GoogleApiClient googleApiClient, int action);
        void onCancel(int action);
    }

    private static final int RC_SIGN_IN = 9001;

    public static GoogleApiHelper newInstance(String accountName, int action) {
        GoogleApiHelper googleApiClientFragment = new GoogleApiHelper();
        Bundle bundle = new Bundle();
        bundle.putString(INTENT_EXTRA_ACCOUNT_NAME, accountName);
        bundle.putInt(INTENT_EXTRA_ACTION, action);
        googleApiClientFragment.setArguments(bundle);
        return googleApiClientFragment;
    }

    /**
     * Handles resolution callbacks.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LoginActivity.REQUEST_GOOGLE_API_CLIENT_CONNECT) {
            if (resultCode == Activity.RESULT_OK) {
                mGoogleApiClient.connect();
            } else {
                Activity activity = this.getActivity();
                if (activity instanceof ConnectionCallbacks) {
                    ConnectionCallbacks connectionCallbacks = (ConnectionCallbacks)activity;
                    connectionCallbacks.onCancel(action);
                }
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        Bundle bundle = this.getArguments();
        this.accountName = bundle.getString(INTENT_EXTRA_ACCOUNT_NAME);
        this.action = bundle.getInt(INTENT_EXTRA_ACTION);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "On Resume");
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this.getContext())
                    .setAccountName(accountName)
                    .addApi(Games.API)
                    .addScope(Games.SCOPE_GAMES)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .setViewForPopups(this.getActivity().findViewById(android.R.id.content))
                    .build();

            mGoogleApiClient.connect();
        }
    }

   /* private int getActivityView(Activity activity) {
        if (activity.getClass() == LoginActivity.class) {
            return this.getActivity().findViewById(android.R.id.login_layout);
        }
    }*/

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "GoogleApiClient connected");

        Activity activity = this.getActivity();
        if (activity instanceof ConnectionCallbacks) {
            Log.i(TAG, "In on connected if");
            ConnectionCallbacks connectionCallbacks = (ConnectionCallbacks)activity;
            connectionCallbacks.onConnected(mGoogleApiClient, action);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "GoogleApiClient connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "GoogleApiClient connection failed: " + connectionResult.toString());

        if (!connectionResult.hasResolution()) {
            // show the localized error dialog.
            GoogleApiAvailability.getInstance().getErrorDialog(this.getActivity(), connectionResult.getErrorCode(), 0).show();
            return;
        } else {
            GameUtils.resolveConnectionFailure(this.getActivity(),
                    mGoogleApiClient, connectionResult, RC_SIGN_IN,
                    getString(R.string.signin_other_error));
        }
        try {
            connectionResult.startResolutionForResult(this.getActivity(), LoginActivity.REQUEST_GOOGLE_API_CLIENT_CONNECT);
        } catch (IntentSender.SendIntentException e) {
            Log.e(TAG, "Exception while starting resolution activity", e);
        }
    }

    private String accountName = null;
    private int action = -1;
    public static GoogleApiClient mGoogleApiClient;

    private static final String INTENT_EXTRA_ACCOUNT_NAME = "INTENT_EXTRA_ACCOUNT_NAME";
    private static final String INTENT_EXTRA_ACTION = "INTENT_EXTRA_ACTION";

    private static final String TAG = "GoogleApiClientFragment";
}