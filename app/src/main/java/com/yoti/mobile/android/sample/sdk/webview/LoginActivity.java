package com.yoti.mobile.android.sample.sdk.webview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yoti.mobile.android.app.sdk.webview.YotiConnectActivity;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    //private static final String MY_CONNECT_PAGE_URL = "https://www.yoti.com/connect/8ba48e18-45c0-46f2-ba26-ce59c2a22df4";
    private static final String MY_CONNECT_PAGE_URL = "https://staging0.www.yoti.com/connect/446dd416-42cd-45a5-b552-d347ca86072b";
    private static final int YOTI_CONNECT_REQEST_CODE = 1234;


    private Button connectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        connectButton = (Button) findViewById(R.id.connectButton);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), YotiConnectActivity.class);
                intent.putExtra(YotiConnectActivity.ARGS_CONNECT_PAGE_URL, MY_CONNECT_PAGE_URL);
                startActivityForResult(intent, YOTI_CONNECT_REQEST_CODE);
            }
        });

        processIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        processIntent(intent);
    }

    private void processIntent(Intent intent) {
        Uri uri = intent.getData();
        // This is invoked by the yoti app after a connect request has been successfully done.
        // This where you might want to process the token that would be returned in the callback URL
        if (uri != null) {

            // Display the success screen
            Intent i = new Intent(this, AccountActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle potential error code.
        if(requestCode == YOTI_CONNECT_REQEST_CODE){
            switch (resultCode){
                case YotiConnectActivity.RESULT_NO_YOTI_APP_FOUND:
                    Toast.makeText(this, "No Yoti app found on this device, please install Yoti to continue", Toast.LENGTH_LONG).show();
                    break;

                case YotiConnectActivity.RESULT_NOT_VALID_URL_SUBMITTED:
                    Toast.makeText(this, "The Url submitted is invalid please check that you can open the link in order to continue", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}
