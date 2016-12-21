android-sdk-webview
===================
A simple application that uses the android webview sdk to integrate the connect flow within an app

How to add the dependency to your project
-----------------------------------------
You need to add the aar to you project. If you are using gradle you can take an example at this app
structure to see how the dependency has been handled.

###In details:

There are 2 aar in the yoti-webviewsdk module in this project: webviewsdk-prod-release.aar and webviewsdk-prod-debug.aar.
The debug version will display some logs that the release version won't.

Simply add either of those aar in a new aar gradle module let's call it 'yoti-webviewsdk'.
Make sure the file build.gradle of you new module contains the following:
```groovy
configurations.maybeCreate("default")
artifacts.add("default", file('webviewsdk-prod-release.aar'))
```
You can change to the debug aar if you want to see some logs of what is happening.

How does the webview SDK work
-----------------------------

* A Fragment that wraps a webview that will display a connect page and listen to both YOTI schemes: YotiConnectFragment.YOTI_URI_SCHEME
and YotiConnectFragment.YOTI_URI_WEBSITE_SCHEME to decide trigger the yoti app via the 'connect button' of the yoti page. The yoti app
will then, according to the user decision, carry out the requested operation or not.
* In case of success, if one wants his mobile app to be invoked when the user has successfully carried out the yoti request within the Yoti app,
he should make sure that at least one android component is declared in the manifest to listens to the callback defined when the
validation scenario has been created in Yoti Dashboard.
* An example in case of the Yoti Connect Page where the callback is constructed this way : "https://www.yoti.com/connect/thankyou/?token=yourTokenValueHere"
The manifest entry for an android component will have the following intent filter :
```html<pre>
<intent-filter>
    <action android:name="android.intent.action.VIEW"/>
    <category android:name="android.intent.category.DEFAULT"/>
    <category android:name="android.intent.category.BROWSABLE"/>
    <data
        android:pathPrefix="/connect/thankyou/"
        android:host="www.yoti.com"
        android:scheme="https"/>
</intent-filter>
</pre>
```
* Note : if the user denies the request within Yoti app, he will to quit Yoti app manually and no notification will be sent to the calling app.
In order for the host activity to be notified of all the possible outcome, it must implement YotiConnectFragment.OnYotiConnectWebViewListener

How to use the webview SDK
--------------------------
### The ```YotiConnectFragment```
* The fragment only requires the URL to be submitted at creation time. This URL should be the Yoti connect
page that you want to display to your user. (the exact same one that will display a QRCode if opened on a desktop browser)
* This fragment exposes a interface that the host activity must implement ```OnYotiConnectWebViewListener```.
This interface callbacks will be invoked when the submitted URL is invalid or when no Yoti app has been found to resolve
the Yoti requests.

Here is how you instantiate the fragment:
```java
YotiConnectFragment fragment = YotiConnectFragment.newInstance(url);

// url is the URL pointing at the connect page you want your user to use

getFragmentManager()
        .beginTransaction()
        .add(R.id.container, fragment, "YotiFragment")
        .commit();
```
### The ```YotiConnectActivity```
An activity that wraps a YotiConnectFragment that will display a connect page.
In order for the caller to be notified of all the possible outcomes, it must start this activity for result
via Activity.startActivityForResult(Intent, int). One intent parameter is required and it is the URL needed by the
fragment to display the page. This will be passed with the key ```YotiConnectActivity.RGS_CONNECT_PAGE_URL```
Here the possible result returned by this activity:
* If none of those scheme can be resolved, then ```YotiConnectActivity.RESULT_NO_YOTI_APP_FOUND``` will be returned</li>
* if no valid URL is submitted in the intent then ```YotiConnectActivityRESULT_NOT_VALID_URL_SUBMITTED``` will be returned</li>