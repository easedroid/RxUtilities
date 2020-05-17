# RxUtilities
[![](https://jitpack.io/v/easedroid/RxUtilities.svg)](https://jitpack.io/#easedroid/RxUtilities)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[ ![Download](https://api.bintray.com/packages/easedroid/RxUtilities/RxUtilities/images/download.svg) ](https://bintray.com/easedroid/RxUtilities/RxUtilities/_latestVersion)
## It includes reactive location feature using RxJava and RxAndroid, very easy to use. More features will be added soon such as running location service in foreground when needed as well simple service to get desired result

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
              implementation 'com.github.easedroid:RxUtilities:CURRENT_VERSION'
    }
    
 -to compile with above library you need to add RxAndroid into your project
 
    dependencies {
               implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
               implementation 'io.reactivex.rxjava2:rxjava:2.2.19'  
     }
     
#Stay tuned     
    
