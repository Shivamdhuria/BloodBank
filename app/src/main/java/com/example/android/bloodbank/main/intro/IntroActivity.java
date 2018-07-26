package com.example.android.bloodbank.main.intro;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.main.signin.SignInActivity;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.SlideFragmentBuilder;

public class IntroActivity extends MaterialIntroActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.colorBackground)
                        .buttonsColor(R.color.colorAccent)
                        .image(agency.tango.materialintroscreen.R.drawable.ic_next)
                        .title(getString(R.string.intro1title))
                        .description(getString(R.string.intro1description))
                        .build());


        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.colorBackground)
                        .buttonsColor(R.color.colorAccent)
                        .neededPermissions(new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION})

                        .title(getString(R.string.intro2title))
                        .description(getString(R.string.intro2description))
                        .build());


        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorBackground)
                .buttonsColor(R.color.colorAccent)
                .title(getString(R.string.intro3title))
                .description(getString(R.string.intro3description))
                .build());
    }

    @Override
    public void onFinish() {
        super.onFinish();
        Intent signInIntent = new Intent(this, SignInActivity.class);
        startActivity(signInIntent);
    }
}

