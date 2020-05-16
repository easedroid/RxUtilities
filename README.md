# RxUtilities
##This library is created to fulfil my daily needs with common features like location, permission, debug mode logging etc and many more will be added in the future.

I will add some usage sample very soon.


 -To use this library add the follow lines in the project level build.gradle file

    allprojects {
            repositories {
                ...
                maven { url 'https://jitpack.io' }
            }
    }
  
  
 -and add this below line in the app level build.gradle file
 
    dependencies {
              implementation 'com.github.easedroid:RxUtilities:Tag'
    }
