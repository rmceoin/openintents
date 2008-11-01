package org.openintents.updatechecker.activity;

import org.openintents.updatechecker.R;
import org.openintents.updatechecker.UpdateChecker;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StatFs;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class UpdateCheckerActivity extends Activity {
	private static final String TAG = "UpdateChecker";
	public static final String EXTRA_LATEST_VERSION = "latest_version";
	public static final String EXTRA_COMMENT = "comment";
	private String mPackageName = null;
	private RadioGroup mRadioGroup;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "create");
		super.onCreate(savedInstanceState);

		// check for a new version of UpdateChecker
		// Update.check(this);

		setContentView(R.layout.main);

		TextView view;
		updateText(getIntent());

		// view = (TextView) findViewById(R.id.text);
		//
		// view.setText(getString(R.string.about_text, getVersionNumber(),
		// getSDInfo(), getOSInfo()));

		mPackageName = getIntent().getStringExtra(
				UpdateChecker.EXTRA_PACKAGE_NAME);

		mRadioGroup = (RadioGroup) findViewById(R.id.action_choice);

		Button button = (Button) findViewById(R.id.ok);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				switch (mRadioGroup.getCheckedRadioButtonId()) {
				case R.id.do_update:
					startActivity((Intent)getIntent().getParcelableExtra(UpdateChecker.EXTRA_UPDATE_INTENT));
					break;
				case R.id.remind_me_later:
					updateUpdateTime();
					break;
				case R.id.ignore_this_update:
					updateLastIgnoredVersion();
					break;

				}
			}
		});

	}

	protected void updateLastIgnoredVersion() {
		Toast.makeText(this, "TODO ignore this version", Toast.LENGTH_LONG).show();
		
	}

	protected void updateUpdateTime() {
		Toast.makeText(this, "TODO remind me later", Toast.LENGTH_LONG).show();		
	}

	private void updateText(Intent intent) {
		TextView view = (TextView) findViewById(R.id.text_update);

		String appName = intent.getStringExtra(UpdateChecker.EXTRA_APP_NAME);
		String comment = intent.getStringExtra(UpdateChecker.EXTRA_COMMENT);
		if (appName != null && comment != null) {
			view.setText(getString(R.string.update_available_2, appName,
					comment));
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (mPackageName != null) {
			((NotificationManager) getSystemService(NOTIFICATION_SERVICE))
					.cancel(mPackageName.hashCode());
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		Log.v(TAG, "new intent");
		updateText(intent);
		mPackageName = intent.getStringExtra(UpdateChecker.EXTRA_PACKAGE_NAME);

	}

	private String getSDInfo() {
		StatFs stats = new StatFs("/sdcard");
		int space = stats.getAvailableBlocks() * stats.getBlockSize();
		return getString(R.string.free_space, String.valueOf(space));
	}

	private String getOSInfo() {

		return getString(R.string.os_info, Build.BOARD, Build.BRAND,
				Build.DEVICE, Build.ID, Build.MODEL);

	}

	/**
	 * Get current version number.
	 * 
	 * @return
	 */
	private String getVersionNumber() {
		String version = "?";
		try {
			PackageInfo pi = getPackageManager().getPackageInfo(
					getPackageName(), 0);
			version = pi.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			Log.e(TAG, "Package name not found", e);
		}
		;
		return version;
	}

	/**
	 * Get application name.
	 * 
	 * @return
	 */
	private String getApplicationName() {
		String name = "?";
		try {
			PackageInfo pi = getPackageManager().getPackageInfo(
					getPackageName(), 0);
			name = getString(pi.applicationInfo.labelRes);
		} catch (PackageManager.NameNotFoundException e) {
			Log.e(TAG, "Package name not found", e);
		}
		;
		return name;
	}

}