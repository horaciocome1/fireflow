package io.github.horaciocome1.fireflow

import com.google.firebase.FirebaseException
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

/**
 * returns a flow that listens to documentSnapshot changes and offer it
 */
@ExperimentalCoroutinesApi
fun DocumentReference.snapshotAsFlow(
    coroutineContext: CoroutineContext = Dispatchers.IO
): Flow<DocumentSnapshot?> = callbackFlow {
    try {
        val listener = EventListener<DocumentSnapshot> { snapshot, error ->
            if (error != null) {
                close(error)
                return@EventListener
            }
            offer(snapshot)
        }
        val registration = addSnapshotListener(listener)
        awaitClose { registration.remove() }
    } catch (e: FirebaseFirestoreException) {
        close(e)
    } catch (e: FirebaseException) {
        close(e)
    } catch (e: Exception) {
        close(e)
    }
}.flowOn(coroutineContext)

/**
 * returns a flow that listens to querySnapshot changes and offer it
 * @param coroutineContext default is Dispatchers.IO
 */
@ExperimentalCoroutinesApi
fun Query.snapshotAsFlow(
    coroutineContext: CoroutineContext = Dispatchers.IO
): Flow<QuerySnapshot?> = callbackFlow {
    try {
        val listener = EventListener<QuerySnapshot> { snapshot, error ->
            if (error != null) {
                close(error)
                return@EventListener
            }
            offer(snapshot)
        }
        val registration = addSnapshotListener(listener)
        awaitClose { registration.remove() }
    } catch (e: FirebaseFirestoreException) {
        close(e)
    } catch (e: FirebaseException) {
        close(e)
    } catch (e: Exception) {
        close(e)
    }
}.flowOn(coroutineContext)
