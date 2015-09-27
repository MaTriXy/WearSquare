package cz.destil.wearsquare.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.Bind;
import cz.destil.wearsquare.R;
import cz.destil.wearsquare.core.BaseActivity;
/**
 * Base activity which handles displaying progress and errors in generic way.
 *
 * @author David Vávra (david@vavra.me)
 */
public abstract class ProgressActivity extends BaseActivity {

    @Bind(R.id.progress)
    ProgressBar vProgress;
    @Bind(R.id.error)
    TextView vError;
    @Bind(R.id.main)
    FrameLayout vMainContainer;
    @Bind(R.id.small_progress)
    ProgressBar vSmallProgress;
    private View vMainView;

    abstract int getMainViewResourceId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        ButterKnife.bind(this);
        vMainView = ((FrameLayout) LayoutInflater.from(this).inflate(getMainViewResourceId(), vMainContainer)).getChildAt(0);
    }

    @Override
    public void startDisconnected() {
        super.startDisconnected();
        showError(getString(R.string.please_connect));
    }

    public void showProgress() {
        vProgress.setVisibility(View.VISIBLE);
        vError.setVisibility(View.GONE);
    }

    public void hideProgress() {
        vProgress.setVisibility(View.GONE);
    }

    public void hideSmallProgress() {
        vSmallProgress.setVisibility(View.GONE);
    }

    public void showSmallProgress() {
        vSmallProgress.setVisibility(View.VISIBLE);
    }

    public void showError(String message) {
        vProgress.setVisibility(View.GONE);
        vError.setVisibility(View.VISIBLE);
        vError.setText(message);
    }

    public View getMainView() {
        return vMainView;
    }

}
