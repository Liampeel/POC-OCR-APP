# AI Medical Mobile Application

Android application client to run on and communicate with the base application.

This application is using an optical character recognition algorithm to read the display from a point of care device.

Testing for this application was done on an OMRON Blood Pressure Monitor.

## Prerequisites

* Android Studio - Available from https://developer.android.com/studio
* Virtualisation enabled on your machine/an android device to run the application on

## Running the Application

The application will need to be opened and run in android studio
The application will need to be configured in android studio,
e.g. the project is defined and android studio knows how to find and run the app

Note: if using the emulator the phone model, os and api will need to be defined in
android studio.

Once this is all done, run the base application(follow the README instructions)
prior to running the mobile application to allow for api calls.


## Making API Requests on the app

The server will be running off local host.

So you will need to change the IP in "RetrofitClient"
To the IP of the Computer you are running server on.


*****************************************************************
Warning - Android studio is very resource intensive
It requires alot of storage, memory and processing power to run
please allow for the adequate system resources to be available
*****************************************************************
