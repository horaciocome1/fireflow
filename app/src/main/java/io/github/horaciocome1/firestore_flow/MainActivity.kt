package io.github.horaciocome1.firestore_flow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    class Post

    class User

    class Topic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

//    // common way
//    fun getPostsA() {
//        val db = FirebaseFirestore.getInstance()
//        val ref = db.collection("posts")
//        ref.addSnapshotListener { snapshot, error ->
//            if (error != null) {
//                // handle
//            } else if (snapshot != null) {
//                val posts = snapshot.toObjects(Post::class.java)
//                // set posts to UI
//            }
//        }
//    }
//
//    // with fireflow
//    @ExperimentalCoroutinesApi
//    suspend fun getPostsB() {
//        val db = FirebaseFirestore.getInstance()
//        db.collection("posts").getAsFlow<Post>().collect { posts ->
//            // set posts to UI
//        }
//    }
//
//    // with fireflow document
//    @ExperimentalCoroutinesApi
//    suspend fun getPost() {
//        val db = FirebaseFirestore.getInstance()
//        db.collection("posts")
//            .document("1")
//            .getAsFlow<Post>().collect { post ->
//                // set post to UI
//            }
//    }

}