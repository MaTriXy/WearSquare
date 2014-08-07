package cz.destil.wearsquare.core;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import com.mariux.teleport.lib.TeleportClient;

import cz.destil.wearsquare.event.ExitEvent;
import cz.destil.wearsquare.util.DebugLog;

/**
 * Base activity for all others, handles Teleport, Otto and detecting connected state.
 *
 * @author David Vávra (david@vavra.me)
 */
public abstract class BaseActivity extends Activity {

    private TeleportClient mTeleportClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DebugLog.d(this + " onCreate");
        mTeleportClient = new TeleportClient(this);
        App.bus().register(this);
    }

    @Override
    protected void onDestroy() {
        DebugLog.d(this + " onDestroy");
        App.bus().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        DebugLog.d(this + " onStart");
        mTeleportClient.connect();
        Wearable.NodeApi.getConnectedNodes(mTeleportClient.getGoogleApiClient()).setResultCallback(new ResultCallback<NodeApi
                .GetConnectedNodesResult>() {


            @Override
            public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                if (getConnectedNodesResult.getNodes().size() > 0) {
                    startConnected();
                } else {
                    startDisconnected();
                }
            }
        });
    }

    /**
     * Override in child to handle disconnected state.
     */
    protected void startDisconnected() {
        DebugLog.d("start disconnected");
    }

    /**
     * Override in child to handle connected state.
     */
    protected void startConnected() {
        DebugLog.d("start connected");
    }

    @Override
    protected void onStop() {
        super.onStop();
        DebugLog.d("onStop");
        mTeleportClient.disconnect();
    }

    public TeleportClient teleport() {
        return mTeleportClient;
    }

    public void finishOtherActivities() {
        App.bus().post(new ExitEvent());
    }
}
