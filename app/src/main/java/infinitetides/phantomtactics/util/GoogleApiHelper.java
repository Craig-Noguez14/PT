package infinitetides.phantomtactics.util;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

public class GoogleApiHelper implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = GoogleApiHelper.class.getSimpleName();
    Context context;
    public static GoogleApiClient mGoogleApiClient;

    public GoogleApiHelper(Context context) {
        this.context = context;
        checkPlayServices();
        buildGoogleApiClient();
    }

    public boolean isConnected() {
        if (mGoogleApiClient != null) {
            return true;
        } else {
            return false;
        }
    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();

        mGoogleApiClient.connect();
    }

    public boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            /*if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, context, PLAY_SERVICES_REQUEST_CODE).show();
            } else {
            }*/
            Log.d(TAG, "Play Services is not suppported on this device");
            return false;
        } else {
            Log.d(TAG, "Looks good m8");
        }
        return true;
    }


    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnectionSuccess: googleApiClient.connect()");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended: googleApiClient.connect()");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed: connectionResult.toString() = " + connectionResult.toString());
    }
}
