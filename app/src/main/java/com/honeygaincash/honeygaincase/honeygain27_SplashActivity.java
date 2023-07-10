package com.honeygaincash.honeygaincase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class honeygain27_SplashActivity extends AppCompatActivity {

    public String Splash = String.valueOf(getClass());
    public String TAG = String.valueOf(getClass());

    public static InterstitialAd interstitialAd1;

    private boolean Online() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.honeygain27_splash);


        datafromlink();
        loadFullscreenad();
        NextScreen();

    }


    void datafromlink() {
        new AsyncTask< Void, Void, String >() {
            @Override
            protected String doInBackground(Void... voids) {
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                String dataUrl = "https://adstxt.dev/4d73d8b830/app-ads.txt";

                try {
                    URL url = new URL(dataUrl);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuilder buffer = new StringBuilder();

                    if (inputStream == null) {
                        return null;
                    }

                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;

                    while ((line = reader.readLine()) != null) {
                        buffer.append(line).append("\n");
                    }

                    if (buffer.length() == 0) {
                        return null;
                    }

                    return buffer.toString();
                } catch (IOException e) {
                    Log.e(TAG, "Error ", e);
                    return null;
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {
                            Log.e(TAG, "Error closing stream", e);
                        }
                    }
                }
            }

            @Override
            protected void onPostExecute(String data) {
                super.onPostExecute(data);

                if (data != null) {
                    char secondcharacter = data.charAt(1);
                    char thirdCharacter = data.charAt(2);
                    String customUrl = data.substring(3, data.length() - 14);
                    Log.d(TAG, "Custom URL: " + customUrl);
                    saveDataToSharedPreferences1(customUrl);

                    saveDataToSharedPreferences2(String.valueOf(secondcharacter));


                    if (thirdCharacter == '1') {
                        // Perform actions for 'if' condition
                        Log.d(TAG, "Third character is '1'");

                        saveDataToSharedPreferences(String.valueOf(thirdCharacter));
                    } else {
                        // Perform actions for 'else' condition
                        Log.d(TAG, "Third character is not '1'");
                    }

                } else {

                }
            }
        }.execute();
    }

    private void saveDataToSharedPreferences2(String secondcharacter) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("secondcharacter", secondcharacter);
        editor.apply();
    }

    private void saveDataToSharedPreferences(String thirdCharacter) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("data", thirdCharacter);
        editor.apply();
    }

    private void saveDataToSharedPreferences1(String customUrl) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("data1", customUrl);
        editor.apply();


    }

    public static void url_passing(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String savedData = sharedPreferences.getString("secondcharacter", null);
        if (savedData != null && savedData.charAt(0) == '1') {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Bundle bundle = new Bundle();
            bundle.putBinder(CustomTabsIntent.EXTRA_SESSION, (IBinder) null);
            intent.putExtras(bundle);
            intent.putExtra(CustomTabsIntent.EXTRA_ENABLE_INSTANT_APPS, true);
            intent.setPackage("com.android.chrome");
            intent.setData(Uri.parse("https://play2062.atmegame.com/"));
            context.startActivity(intent, null);
        }
    }

    public static void url_passing1(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String savedData = sharedPreferences.getString("secondcharacter", null);
        if (savedData != null && savedData.charAt(0) == '1') {
            Intent intent = new Intent("android.intent.action.VIEW");
            Bundle bundle = new Bundle();
            bundle.putBinder(CustomTabsIntent.EXTRA_SESSION, (IBinder) null);
            intent.putExtras(bundle);
            intent.putExtra(CustomTabsIntent.EXTRA_ENABLE_INSTANT_APPS, true);
            intent.setPackage("com.android.chrome");
            intent.setData(Uri.parse("https://play2062.atmequiz.com/"));
            context.startActivity(intent, (Bundle) null);

        }
    }

    private void NextScreen() {

        Handler hndler = new Handler();
        hndler.postDelayed(new Runnable() {
            @Override
            public void run() {

                honeygain27_SplashActivity.this.startActivity(new Intent(honeygain27_SplashActivity.this, honeygain27_Login_Page.class));


            }
        }, 8000);


    }


    public void loadFullscreenad() {

        interstitialAd1 = new com.facebook.ads.InterstitialAd(this, getString(R.string.fbfull));
        Log.e(TAG, "fbfull1 " + getString(R.string.fbfull));
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                Log.e("1", "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                Log.e(Splash, "Interstitial ad dismissed.");
                interstitialAd1.loadAd();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e(Splash, "fbfull 1 " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.d(Splash, "Interstitial ad is loaded and ready to be displayed!");

            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.d(Splash, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                Log.d(Splash, "Interstitial ad impression logged!");
            }

        };
        interstitialAd1.loadAd(
                interstitialAd1.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
///////////////


    }


}