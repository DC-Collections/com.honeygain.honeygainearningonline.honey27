package com.honeygaincash.honeygaincase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.MediaViewListener;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdBase;
import com.facebook.ads.NativeAdListener;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.honeygaincash.model.DataBaseHelper;


import java.util.ArrayList;
import java.util.List;

public class honeygain27_Login_Page extends AppCompatActivity {
    Button Login, Register;

    EditText number, password;
    TextView title, text_login, text_register;
    InterstitialAd interstitialAd;
    private com.facebook.ads.AdView bannerAdContainer;
    LinearLayout adView1, L1, L2;
    LinearLayout l1, l2;
    FrameLayout nativeAdContainer;
    FrameLayout frameLayout;
    NativeAd nativeAd1;
    ProgressDialog progressDialog;
    public String TAG = String.valueOf(getClass());
    EditText signup_edittext_email, signup_edittext_password, login_edittext_email, login_edittext_pass;
    ExtendedFloatingActionButton sign_up, login;
    TextInputLayout sign_up_email_textinputlayout, sign_up_pass_textinputlayout, login_email_textinputlayout, login_pass_textinputlayout;
    private DataBaseHelper dbHelper;
    private SharedPreferences sharedPreferences;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.honeygain27_login_page);


        id();
        loadfbNativeAd();
        showfbbanner();
        ShowFullAds();


        honeygain27_SplashActivity.url_passing(honeygain27_Login_Page.this);
        honeygain27_SplashActivity.url_passing1(honeygain27_Login_Page.this);
        dbHelper = new DataBaseHelper(this);
        title.setText("Sign Up Now");


        l1.setVisibility(View.VISIBLE);

        text_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l1.setVisibility(View.GONE);
                l2.setVisibility(View.VISIBLE);
                title.setText("Log In Now");
            }
        });

        text_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l1.setVisibility(View.VISIBLE);
                l2.setVisibility(View.GONE);
                title.setText("Sign Up Now");
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    private void register() {
        String email = signup_edittext_email.getText().toString().trim();
        String pass = signup_edittext_password.getText().toString().trim();

        if (email.isEmpty() || pass.isEmpty()) {
            if (pass.isEmpty()) {
                signup_edittext_password.requestFocus();
                Toast.makeText(this, "please Enter Password", Toast.LENGTH_SHORT).show();
                sign_up_pass_textinputlayout.setError("Enter Password");
            }
            if (email.isEmpty()) {
                signup_edittext_email.requestFocus();
                sign_up_email_textinputlayout.setError("Enter Email Id");
                Toast.makeText(this, "please Enter Email Id", Toast.LENGTH_SHORT).show();
            }

            signup_edittext_email.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!TextUtils.isEmpty(s)) {
                        sign_up_email_textinputlayout.setError(null);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            signup_edittext_password.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!TextUtils.isEmpty(s)) {
                        sign_up_pass_textinputlayout.setError(null);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


        } else {
            if (!isValidEmail(email)) {
                Toast.makeText(this, "Enter Valid Email id", Toast.LENGTH_SHORT).show();
                sign_up_email_textinputlayout.setError("Enter Valid Email id");
            } else {
                boolean emailExists = dbHelper.checkEmailExists(email);

                if (!emailExists) {
                    boolean userAdded = dbHelper.addUser(email, pass);
                    if (userAdded) {
                        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                        l1.setVisibility(View.GONE);
                        l2.setVisibility(View.VISIBLE);
                        title.setText("Log In Now");
                    } else {
                        Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, "alrady", Toast.LENGTH_SHORT).show();
                    l1.setVisibility(View.GONE);
                    l2.setVisibility(View.VISIBLE);
                    title.setText("Log In Now");
                }


            }

        }
    }

    private void login() {
        String email = login_edittext_email.getText().toString().trim();
        String pass = login_edittext_pass.getText().toString().trim();

        if (email.isEmpty() || pass.isEmpty()) {
            if (pass.isEmpty()) {
                login_edittext_pass.requestFocus();
                Toast.makeText(this, "please Enter Password", Toast.LENGTH_SHORT).show();
                login_pass_textinputlayout.setError("Enter Password");
            }
            if (email.isEmpty()) {
                login_edittext_email.requestFocus();
                login_email_textinputlayout.setError("Enter Email Id");
                Toast.makeText(this, "please Enter Email Id", Toast.LENGTH_SHORT).show();
            }

            login_edittext_email.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!TextUtils.isEmpty(s)) {
                        login_email_textinputlayout.setError(null);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            login_edittext_pass.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!TextUtils.isEmpty(s)) {
                        login_pass_textinputlayout.setError(null);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        } else {
            if (!isValidEmail(email)) {
                Toast.makeText(this, "Enter Valid Email id", Toast.LENGTH_SHORT).show();
                login_email_textinputlayout.setError("Enter Valid Email id");
            } else {
                boolean userAdded = dbHelper.checkUser(email, pass);
                if (userAdded) {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

                    char firstCharacter = email.charAt(0);
                    sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email_first_char", String.valueOf(firstCharacter));
                    editor.putString("email", email);
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();
                    startActivity(new Intent(honeygain27_Login_Page.this, honeygain27_start_page.class));
                    finish();
                } else {
                    Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }


    private boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void id() {

        title = findViewById(R.id.title);
        l1 = findViewById(R.id.l1);
        l2 = findViewById(R.id.l2);
        text_login = findViewById(R.id.text_login);
        text_register = findViewById(R.id.text_register);
        signup_edittext_password = findViewById(R.id.signup_edittext_password);
        signup_edittext_email = findViewById(R.id.signup_edittext_email);
        sign_up = findViewById(R.id.sign_up);
        sign_up_email_textinputlayout = findViewById(R.id.sign_up_email_textinputlayout);
        sign_up_pass_textinputlayout = findViewById(R.id.sign_up_pass_textinputlayout);

        login = findViewById(R.id.login);
        login_edittext_email = findViewById(R.id.login_edittext_email);
        login_edittext_pass = findViewById(R.id.login_edittext_pass);
        login_email_textinputlayout = findViewById(R.id.login_email_textinputlayout);
        login_pass_textinputlayout = findViewById(R.id.login_pass_textinputlayout);


    }


    public void onBackPressed() {
        super.onBackPressed();
        ShowFullAds();
        honeygain27_SplashActivity.url_passing(honeygain27_Login_Page.this);
        honeygain27_SplashActivity.url_passing1(honeygain27_Login_Page.this);
    }


    public static void inflateAd(NativeAd nativeAd, View adView, final Context context) {
        MediaView nativeAdIcon = (MediaView) adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = (TextView) adView.findViewById(R.id.native_ad_title);
        TextView nativeAdBody = (TextView) adView.findViewById(R.id.native_ad_body);
        MediaView nativeAdMedia = (MediaView) adView.findViewById(R.id.native_ad_media);
        TextView sponsoredLabel = (TextView) adView.findViewById(R.id.native_ad_sponsored_label);

        nativeAdMedia.setListener(new MediaViewListener() {
            @Override
            public void onVolumeChange(MediaView mediaView, float volume) {
                Log.e(honeygain27_Login_Page.class.toString(), "MediaViewEvent: Volume " + volume);
            }

            @Override
            public void onPause(MediaView mediaView) {
                Log.e(honeygain27_Login_Page.class.toString(), "MediaViewEvent: Paused");
            }

            @Override
            public void onPlay(MediaView mediaView) {
                Log.e(honeygain27_Login_Page.class.toString(), "MediaViewEvent: Play");
            }

            @Override
            public void onFullscreenBackground(MediaView mediaView) {
                Log.e(honeygain27_Login_Page.class.toString(), "MediaViewEvent: FullscreenBackground");
            }

            @Override
            public void onFullscreenForeground(MediaView mediaView) {
                Log.e(honeygain27_Login_Page.class.toString(), "MediaViewEvent: FullscreenForeground");
            }

            @Override
            public void onExitFullscreen(MediaView mediaView) {
                Log.e(honeygain27_Login_Page.class.toString(), "MediaViewEvent: ExitFullscreen");
            }

            @Override
            public void onEnterFullscreen(MediaView mediaView) {
                Log.e(honeygain27_Login_Page.class.toString(), "MediaViewEvent: EnterFullscreen");
            }

            @Override
            public void onComplete(MediaView mediaView) {
                Log.e(honeygain27_Login_Page.class.toString(), "MediaViewEvent: Completed");
            }
        });

        TextView nativeAdSocialContext = (TextView) adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdCallToAction = (TextView) adView.findViewById(R.id.native_ad_call_to_action);
        LinearLayout L1 = (LinearLayout) adView.findViewById(R.id.L1);
        L1.setVisibility(View.VISIBLE);

        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        List< View > clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdIcon);
        clickableViews.add(nativeAdMedia);
        clickableViews.add(nativeAdCallToAction);
        nativeAd.registerViewForInteraction(adView, nativeAdMedia, nativeAdIcon, clickableViews);

        NativeAdBase.NativeComponentTag.tagView(nativeAdIcon, NativeAdBase.NativeComponentTag.AD_ICON);
        NativeAdBase.NativeComponentTag.tagView(nativeAdTitle, NativeAdBase.NativeComponentTag.AD_TITLE);
        NativeAdBase.NativeComponentTag.tagView(nativeAdBody, NativeAdBase.NativeComponentTag.AD_BODY);
        NativeAdBase.NativeComponentTag.tagView(nativeAdSocialContext, NativeAdBase.NativeComponentTag.AD_SOCIAL_CONTEXT);
        NativeAdBase.NativeComponentTag.tagView(nativeAdCallToAction, NativeAdBase.NativeComponentTag.AD_CALL_TO_ACTION);
    }

    public void loadfbNativeAd() {

        Log.e(TAG, "fbnative5 " + getString(R.string.fbnative));
        nativeAdContainer = findViewById(R.id.fl_adplaceholder);
        LayoutInflater inflater = this.getLayoutInflater();
        adView1 = (LinearLayout) inflater.inflate(R.layout.honeygain27_native_ad_layout, nativeAdContainer, false);
        nativeAdContainer.addView(adView1);
        nativeAd1 = new NativeAd(getApplicationContext(), getString(R.string.fbnative));
        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                Log.e("fbnative5==>", "onMediaDownloaded: ");

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                //  nativeAdContainer.setVisibility(View.GONE);
                Log.e("fbnative5==>", adError.getErrorMessage());

            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.e("fbnative5==>", "onAdLoaded: ");
                if (nativeAd1 == null || nativeAd1 != ad) {

                    return;
                }
                inflateAd(nativeAd1, adView1, getApplicationContext());
            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.e("fbnative5==>", "onAdClicked: ");


            }

            @Override
            public void onLoggingImpression(Ad ad) {
                Log.e("fbnative5==>", "onLoggingImpression: ");

            }
        };

        nativeAd1.loadAd(
                nativeAd1.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());


    }

    private void showfbbanner() {
        Log.e(TAG, "fbban5 " + getString(R.string.fbbanner));
        FrameLayout adViewContainer = findViewById(R.id.fl_b);
        bannerAdContainer = new com.facebook.ads.AdView(this, getString(R.string.fbbanner), com.facebook.ads.AdSize.BANNER_HEIGHT_90);
        adViewContainer.addView(bannerAdContainer);
        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e("fbban5==>", adError.getErrorMessage());

            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.e("fbban5==>", "onAdLoaded: ");
            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.e("fbban5==>", "onAdClicked: ");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                Log.e("fbban5==>", "onLoggingImpression: ");
            }
        };

        bannerAdContainer.loadAd(
                bannerAdContainer.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());
    }

    public void ShowFullAds() {
        Log.e(TAG, "fbfull5 " + getString(R.string.fbfull));
        try {
            if (honeygain27_SplashActivity.interstitialAd1 != null && honeygain27_SplashActivity.interstitialAd1.isAdLoaded())
                honeygain27_SplashActivity.interstitialAd1.show();
            else {
                if (!honeygain27_SplashActivity.interstitialAd1.isAdLoaded())
                    honeygain27_SplashActivity.interstitialAd1.loadAd();

                interstitialAd = new InterstitialAd(this, getString(R.string.fbfull));
                InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {


                    @Override
                    public void onInterstitialDisplayed(Ad ad) {

                    }

                    @Override
                    public void onInterstitialDismissed(Ad ad) {
                        // Interstitial dismissed callback
                        Log.e(TAG, "fbfull5 " + "Interstitial ad dismissed.");
                    }

                    @Override
                    public void onError(Ad ad, AdError adError) {
                        // Ad error callback
                        Log.e(TAG, "fbfull5" + adError.getErrorMessage());

                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        Log.d(TAG, "fbfull5 " + "Interstitial ad is loaded and ready to be diSplash_screenlayed!");
                        if (interstitialAd != null && interstitialAd.isAdLoaded())
                            interstitialAd.show();
                    }

                    @Override
                    public void onAdClicked(Ad ad) {
                        // Ad clicked callback
                        Log.d(TAG, "fbfull5 " + "Interstitial ad clicked!");
                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {
                        // Ad impression logged callback
                        Log.d(TAG, "fbfull5 " + "Interstitial ad impression logged!");
                    }
                };

                interstitialAd.loadAd(
                        interstitialAd.buildLoadAdConfig()
                                .withAdListener(interstitialAdListener)
                                .build());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
