package com.teskalabs.cvio.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;

import com.teskalabs.cvio.CatVision;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private FirebaseAnalytics mFirebaseAnalytics;

	private CatVision catvision;
	private int CATVISION_REQUEST_CODE = 100;

	/****************************************** Activity Lifecycle methods ************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

		catvision = CatVision.getInstance();
		catvision.setClientHandle(CatVision.DEFAULT_CLIENT_HANDLE);
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CATVISION_REQUEST_CODE) {
			catvision.onActivityResult(this, resultCode, data);
        }
    }

    /****************************************** Menu *******************************/

    private final int menuItemStartCaptureId = 1;
    private final int menuItemStopCaptureId = 2;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        menu.clear();

        int menuGroup1Id = 1;

        if (catvision.isStarted()) {
			menu.add(menuGroup1Id, menuItemStopCaptureId, 1, "Stop capture");
        } else {
			menu.add(menuGroup1Id, menuItemStartCaptureId, 1, "Start capture");

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "start_capture");
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "menu_item");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case menuItemStartCaptureId:
				catvision.requestStart(this, CATVISION_REQUEST_CODE);
                return true;

            case menuItemStopCaptureId:
				catvision.stop();
                return true;

			default:
                return super.onOptionsItemSelected(item);
        }
    }

	public void onClickDemoArea(View v) {
		Intent intent = new Intent(getApplicationContext(), TestAreaActivity.class);
		startActivity(intent);
	}

}
