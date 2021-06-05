# fireflow
[![](https://jitpack.io/v/horaciocome1/fireflow.svg)](https://jitpack.io/#horaciocome1/fireflow) . [![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0) [![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=19)

## Getting Started
Android library that aims to hide Firebase Firestore listener implementation and expose only a Kotlin Flow of specified type. ;)  
Works for ColletionReferences, Queries, and DocumentReferences.

## Pre-requisites
Check the pre-requisites for Firebase Firestore and for Kotlin Flow on respective documentation.  
Be familiar with Kotlin Coroutines.  
Based on versions **23.0.1** and **1.4.1** of _firebase-firestore-ktx_ and _kotlinx-coroutines-play-services_ respectively

## Adding to your project
Lets start by adding a corresponding repository in your _root_ `build.gradle` file.
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
 ```
The next task is to add the dependency to your _app_ `build.gradle` file.
```gradle
dependencies {
    ...
    implementation 'com.github.horaciocome1:fireflow:0.0.1'
}
```
Now you ready to go.  
You might want to _**sync your project**_ first. ;)

## How to use
### without fireflow
```kotlin
val db = FirebaseFirestore.getInstance()
val ref = db.collection("posts")
ref.addSnapshotListener { snapshot, error ->
    if (error != null) {
        // handle
    } else if (snapshot != null) {
        val posts = snapshot.toObjects(Post::class.java)
        // set posts to UI
    }
}
```
### with fireflow
```kotlin
val db = FirebaseFirestore.getInstance()
db.collection("posts").getAsFlow<Post>().collect { posts ->
    // set posts to UI
}
```
You can also read documents as flows
```kotlin
val db = FirebaseFirestore.getInstance()
db.collection("posts").document("1")
    .getAsFlow<Post>().collect { post ->
        // set post to UI
    }
```

## Troubleshooting
There is no Java support.  
For other things please open an Issue or reach me via [hcome@pm.me](mailto:hcome@pm.me)

## Licenses
   Copyright 2021 Horácio Flávio Comé Júnior

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
### Not maintained by us
   [Firebase Firestore](https://firebase.google.com/docs/firestore/) is a product from Google.  
   [Kotlin](https://kotlinlang.org/) is a product from Jetbrains.

## How to contribute
We open to suggestions of any kind.
Email me, open pull requests, etc.

## More
Hope you development experience gets smootherrrrr.  
If you want to see another utility please check my work on Recyclerview.    
[Simple RecyclerView Listener](https://github.com/horaciocome1/simple-recyclerview-listener)  
[Simple RecyclerView Adapter](https://github.com/horaciocome1/simple-recyclerview-adapter)
