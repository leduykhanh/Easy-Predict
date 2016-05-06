package jangkoo.predict;

import jangkoo.predict.adapter.NavDrawerListAdapter;
import jangkoo.predict.admanager.AdViewHelper;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import jangkoo.predict.model.NavDrawerItem;
import jangkoo.predict.sql.SQLiteDBHelper;
import jangkoo.predict.utils.CreateDialogFragment;

import java.util.ArrayList;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity implements CreateDialogFragment.CreateDialogListener,FragmentDrawer.FragmentDrawerListener {
	public static final String API_KEY = "AIzaSyBTmh-CQ9brVhmOJlYK1CjFt4Y3QpSqjgg";
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	public static SQLiteDBHelper mDBHelper;
	public CreateDialogFragment dialog;
	private Toolbar mToolbar;
	private FragmentDrawer drawerFragment;
	private InterstitialAd interstitialAd;
	private LoginButton loginButton;
	private TextView skipLoginButton;
	private CallbackManager callbackManager;
	private ProfilePictureView profilePictureView;
	AdViewHelper mAdViewHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FacebookSdk.sdkInitialize(this);
		OneSignal.startInit(this).init();
		setContentView(R.layout.activity_main);
		mDBHelper = new SQLiteDBHelper(this);
		interstitialAd = newInterstitialAd();
		loadInterstitial();
		callbackManager = CallbackManager.Factory.create();
		profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);
		Profile profile = Profile.getCurrentProfile();
		if(profile!=null) {
			profilePictureView.setProfileId(profile.getId());
			//name.setText(profile.getName());
		}
		loginButton = (LoginButton) findViewById(R.id.login_button);
		loginButton.setReadPermissions("public_profile");

		//callback after login
		loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
			@Override
			public void onSuccess(LoginResult loginResult) {
				Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
				Profile profile = Profile.getCurrentProfile();
				profilePictureView.setProfileId(profile.getId());
				//name.setText(profile.getName());
			}

			@Override
			public void onCancel() {
				Toast.makeText(MainActivity.this, "Login canceled", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onError(FacebookException exception) {
				Toast.makeText(MainActivity.this, "Login error", Toast.LENGTH_SHORT).show();
				Log.e("FB", "DCMFBEROOR");
			}
		});

		dialog = new CreateDialogFragment();
		mTitle = mDrawerTitle = getTitle();

		mToolbar = (Toolbar) findViewById(R.id.toolbar);

		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		drawerFragment = (FragmentDrawer)
				getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
		drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
		drawerFragment.setDrawerListener(this);
		loginButton.setFragment(drawerFragment);
		// display the first navigation drawer view on app launch
		displayView(0);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(5);
		}

	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		Fragment fragment = getFragmentManager().findFragmentByTag("home");
		if (fragment instanceof HomeFragment) {
			((HomeFragment)fragment).refresh();
		}
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {

	}

	@Override
	public void onDrawerItemSelected(View view, int position) {
		displayView(position);

	}
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		callbackManager.onActivityResult(requestCode, resultCode, data);
//	}
	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item

			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
//		if (mDrawerToggle.onOptionsItemSelected(item)) {
//			return true;
//		}
		// Handle action bar actions click
		switch (item.getItemId()) {
//		case R.id.action_settings:
//			return true;
		case R.id.share:
			showInterstitial();
			Fragment fragment = getFragmentManager().findFragmentByTag("home");
			if (fragment instanceof HomeFragment) {
				((HomeFragment)fragment).postPicture();
			}
			if (fragment instanceof QuotesFragment) {
				((QuotesFragment)fragment).postPicture();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
//		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		//showInterstitial();
		Fragment fragment = null;
		String tag = "home";
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			setTitle("Your detail");
			break;
		case 1:
			fragment = new RankFragment();
			setTitle("Top ranking");
			break;
		case 2:
			fragment = new NgheNhacXuanFragment();
			break;
		case 3:
			fragment = new CommunityFragment();
			break;
		case 4:
			fragment = new QuotesFragment();
			setTitle("Famous Quotes");
			break;
		case 5:
			fragment = new PredictsFragment();
			setTitle("Available predicts");
			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.container_body, fragment,tag).commit();

			// update selected item and title, then close the drawer
//			mDrawerList.setItemChecked(position, true);
//			mDrawerList.setSelection(position);
//			setTitle(navMenuTitles[position]);
//			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		//mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		//mDrawerToggle.onConfigurationChanged(newConfig);
	}
	public void showCreateDialog() {
		// Create an instance of the dialog fragment and show it

		dialog.show(getFragmentManager(), "NoticeDialogFragment");

	}
	private InterstitialAd newInterstitialAd() {
		InterstitialAd interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId("ca-app-pub-8010283700967425/3558703394");
		interstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {

			}

			@Override
			public void onAdFailedToLoad(int errorCode) {

			}

			@Override
			public void onAdClosed() {
				// Proceed to the next level.

			}
		});
		return interstitialAd;
	}

	private void showInterstitial() {
		// Show the ad if it's ready. Otherwise toast and reload the ad.
		if (interstitialAd != null && interstitialAd.isLoaded()) {
			interstitialAd.show();
		} else {
			Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();

		}
	}

	private void loadInterstitial() {
		// Disable the next level button and load the ad.

		AdRequest adRequest = new AdRequest.Builder()
				.setRequestAgent("android_studio:ad_template").build();
		interstitialAd.loadAd(adRequest);
	}
}
