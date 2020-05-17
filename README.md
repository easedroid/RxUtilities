# RxUtilities
[![](https://jitpack.io/v/easedroid/RxUtilities.svg)](https://jitpack.io/#easedroid/RxUtilities)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[ ![Download](https://api.bintray.com/packages/easedroid/RxUtilities/RxUtilities/images/download.svg) ](https://bintray.com/easedroid/RxUtilities/RxUtilities/_latestVersion)
[![Open Source Love](https://badges.frapsoft.com/os/v1/open-source.png?v=103)](https://github.com/ellerbrock/open-source-badges/)
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
     
 -Usage samples
 
      -First in application class onCreate add below lines
               
                RxHelper.init(applicationContext).initializeTimber()
                RxLocations.init(applicationContext)   
      -In Activity or Fragment you need to add below code in the onCreate
                
                RxLocations.init(this)
                            .getLocationStream()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({location ->
                                // Here is your location
                            }, {error ->
                                error.printStackTrace()
                            })                       
      -Or if you want to modify location call you can use below code
      
                val builder = RxLocations.Builder()
                        val loc = builder.with(this)
                                        .requestRuntimePermission(true)
                                        .setFastestInterval(3000)
                                        .setInterval(8000)
                                        .build()  
                
                loc.getLocationStream()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({location ->
                            // Here is your location
                                RxHelper.print(location.toString())
                            }, {error ->
                                error.printStackTrace()
                            })                                            
            
             
     
#Stay tuned     
    
