package io.github.horaciocome1.fireflow

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
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
}.flowOn(coroutineContext)

/**
 * returns a flow that listens to querySnapshot changes and offers them
 * @param coroutineContext default is Dispatchers.IO
 */
@ExperimentalCoroutinesApi
@Deprecated("to remove", ReplaceWith("asFlow"))
inline fun <reified T> Query.getAsFlow(
    coroutineContext: CoroutineContext = Dispatchers.IO
): Flow<MutableList<T>> = getAsFlowFromQuery(this, coroutineContext)

/**
 * returns a flow that listens to querySnapshot changes and offers them
 * @param coroutineContext default is Dispatchers.IO
 */
@ExperimentalCoroutinesApi
inline fun <reified T> Query.asFlow(
    coroutineContext: CoroutineContext = Dispatchers.IO
): Flow<MutableList<T>> = getAsFlowFromQuery(this, coroutineContext)

/**
 * returns a flow that listens to querySnapshot changes and offer it
 * @param coroutineContext default is Dispatchers.IO
 */
@ExperimentalCoroutinesApi
fun Query.snapshotAsFlow(
    coroutineContext: CoroutineContext = Dispatchers.IO
): Flow<QuerySnapshot?> = callbackFlow {
    val listener = EventListener<QuerySnapshot> { snapshot, error ->
        if (error != null) {
            close(error)
            return@EventListener
        }
        offer(snapshot)
    }
    val registration = addSnapshotListener(listener)
    awaitClose { registration.remove() }
}.flowOn(coroutineContext)

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
    val listener = EventListener<DocumentSnapshot> { snapshot, error ->
        if (error != null) {
            close(error)
            return@EventListener
        }
        offer(snapshot?.toObject<T>())
    }
    val registration = reference.addSnapshotListener(listener)
    awaitClose { registration.remove() }
}.flowOn(coroutineContext)

/**
 * returns a flow that listens to documentSnapshot changes and offers them
 */
@Deprecated("to remove", ReplaceWith("asFlow"))
@ExperimentalCoroutinesApi
inline fun <reified T> DocumentReference.getAsFlow(
    coroutineContext: CoroutineContext = Dispatchers.IO
): Flow<T?> = getAsFlowFromReference(this, coroutineContext)

/**
 * returns a flow that listens to documentSnapshot changes and offers them
 */
@ExperimentalCoroutinesApi
inline fun <reified T> DocumentReference.asFlow(
    coroutineContext: CoroutineContext = Dispatchers.IO
): Flow<T?> = getAsFlowFromReference(this, coroutineContext)

/**
 * returns a flow that listens to documentSnapshot changes and offer it
 */
@ExperimentalCoroutinesApi
fun DocumentReference.snapshotAsFlow(
    coroutineContext: CoroutineContext = Dispatchers.IO
): Flow<DocumentSnapshot?> = callbackFlow {
    val listener = EventListener<DocumentSnapshot> { snapshot, error ->
        if (error != null) {
            close(error)
            return@EventListener
        }
        offer(snapshot)
    }
    val registration = addSnapshotListener(listener)
    awaitClose { registration.remove() }
}.flowOn(coroutineContext)
