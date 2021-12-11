package io.github.horaciocome1.fireflow

import com.google.firebase.FirebaseException
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

/**
 * returns a flow that listens to documentSnapshot changes and offers them
 * @param reference need to be specified
 * @param coroutineContext default is Dispatchers.IO
 */
@ExperimentalCoroutinesApi
@Deprecated("Will be removed on next release", ReplaceWith("ref.asFlow()"))
inline fun <reified T> getAsFlowFromReference(
    reference: DocumentReference,
    coroutineContext: CoroutineContext = Dispatchers.IO
): Flow<T?> = callbackFlow {
    try {
        val listener = EventListener<DocumentSnapshot> { snapshot, error ->
            if (error != null) {
                close(error)
                return@EventListener
            }
            offer(snapshot?.toObject<T>())
        }
        val registration = reference.addSnapshotListener(listener)
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
 * returns a flow that listens to querySnapshot changes and offers them
 * @param query need to be specified
 * @param coroutineContext default is Dispatchers.IO
 */
@ExperimentalCoroutinesApi
@Deprecated("Will be removed on next release", ReplaceWith("ref.asFlow()"))
inline fun <reified T> getAsFlowFromQuery(
    query: Query,
    coroutineContext: CoroutineContext = Dispatchers.IO
): Flow<MutableList<T>> = callbackFlow {
    try {
        val listener = EventListener<QuerySnapshot> { snapshot, error ->
            if (error != null) {
                close(error)
                return@EventListener
            }
            if (snapshot != null) {
                val objets = snapshot.toObjects(T::class.java)
                offer(objets)
            }
        }
        val registration = query.addSnapshotListener(listener)
        awaitClose { registration.remove() }
    } catch (e: FirebaseFirestoreException) {
        close(e)
    } catch (e: FirebaseException) {
        close(e)
    } catch (e: Exception) {
        close(e)
    }
}.flowOn(coroutineContext)
