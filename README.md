### Want a new way to ask for rating?

### Rate Dialog is here for you.

![](screenshot_1.png)
![](screenshot_1.png)

With this dialog your users are asked to rate the app and depending their rate the dialog show them the possibility to sen you an email or to go on the playstore.

![](https://s31.postimg.org/8d3vqcepn/ezgif_458605017.gif)

## Setup
Download the aar file here : [rate_dialog-1.0.aar](https://github.com/SylvainLagache/rate-dialog/blob/master/RateDialog/export/rate_dialog-1.1.0.aar)

Put the aar file in your libs directory

Add in your build.gradle : `compile(name:'rate_dialog-1.2.0', ext:'aar')`

Add in your activity
```java
        AppRate.with(this)
                .setFirstShow(1) //First time showed
                .setShowInterval(1) //Show interval
                .setIntervalMultiplier(1.0F) //Show interval multiplier
                .setAppPackage("com.your.package") //App package
                .addEmailButton("your.email@gmail.com", "This is the object") //Add an email button
                .addBadRateButton("FAQ",
                        new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.lagache.sylvain.xhomebar"))
                ); //Your FAQ intent

        if (savedInstanceState == null || !savedInstanceState.getBoolean(ARG_ALREADY_STARTED)) {
            AppRate.showDialogIfNeeded(this);
            //or
            AppRate.showDialogIfNeeded(this,
                    "Rate this app", //Dialog title
                    "How much did you like this app ?", //Dialog message
                    "Cancel", //Cancel button text
                    "Never show again"); //Never show again checkbox text
        }
```
