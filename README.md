# PFA



This project aimed to simulate and correct color bliness and other conditions of the eye on an Android device.

Sources are split between an Android Studio code in the folder **pfa-repository** and a [Unity](https://unity3d.com/fr/) version in the root folder **unity**



#### Android Studio

This code uses [OpenCv](https://opencv.org) library because it's more efficient and convinent than the Camera2 API about image processing.

#### Unity

There are multiple side projects, but the main one is **MainProject** and it's the one that is currently developed.

In Unity, we prefere to use shader because it's exactly the purpose of our application and it's really efficient on our device.