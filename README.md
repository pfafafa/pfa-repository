# PFA



This project is aimed to simulate and correct color blindess and other conditions of the eye on an Android device by using *Mixed Reality*. The app is compatible with **Google Cardboard**.

Sources are split in two folders. The Android Studio version is in the folder **pfa-repository** while the Unity version in the folder **unity/MainProject**. note that the Android Studio version is *obsolete*. 

## About our app

##### Android Studio version

This version of the app is not complete and was abandoned over our second implementation on Unity. We gave up Android Studio because of material bugs we couldn't fix and because of the complexity of the code. However, the app is functionnal for what was done so we decided to leave it in the repository in case in might interest someone desiring to implement a similar app on Android Studio. The bugs probably aren't irredeemable, but the lack of time and the submission deadlines constrained us to look for other options.

This code uses [OpenCv](https://opencv.org). It implements the video stream recovery and display as well as a functionnal colorblindness simulation (deuteranopia, protanopia & tritanopia). The colorblindness correction does not work on most recent smartphones (e.g. Huawei HONOR 5X, Android Lollipop 6.0.1), but happened to work on older ones.

##### Unity version

This is the up-to-date version of our project. The functionalities implemented are seperated into disctinct folders. Each of them contain test scenes and C# scripts.
* **Camera**: retrieval of the camera stream without any image treatment.
  * *CameraFlow*: retrieves the stream
  * *CameraAndroidDaltonism*: Displays the stream. Works only on the Unity Editor.
  * *CameraDaltonisme*: does the same, but works only on phone.
  * *CameraAndroid*: combination of both scenes above. 
* **Daltonisme**: simulation and correction of 8 types of colorblindness via the *ChannelMixer* shader and the *ColorBlindFilter* script. The matrixes used are contained in *ColorBlindMatrix*
* **Interface**: contains all the different pages of our interface and the scripts to load them.
* **SideBySide**: perform stereoscopic rendering via the ShadersideBySide shader (side-by-side vision adaptated to the cardboard viewer. The disparity and field of view are adjustable. This implementation was gratefully borrowed from [Long Qian](http://longqian.me/2016/10/16/google-cardboard-as-augmented-reality-headset/).

The remaining folders (*Images*, *Design*) contain divers kinds of ressources for our app.

## How to use
If you just want to install and test our app on your phone, you can just download the latest APK from the release page. It will the the Unity version of our app. The minimum Android API level is **Android 4.4 *'KitKat'*** (API level 19).

Otherwise you can view and build the APK yourself in the Unity Editor:
1. Download the *unity* folder and open the project on Unity
2. In build settings, make sure the "Scenes In Build" frame contains all of the scenes contained in the */Assets/Interface* folder. If any are missing, slide them in.
3. Switch plateform to Android if not done already.
4. Build and run on your device.

For each functionnality implemented, test scenes are available in the corresponding folders.

## Credit
This project was developed by Ismaël Hervé, Adélaïde Genay, Timothé Arroyo, Coline Farcy and Franck Triat. We are computer science French undergraduates at ENSEIRB MATMECA in Bordeaux, France. The license used is GPL-3.0, feel free to use our work. 