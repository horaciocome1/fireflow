package io.github.horaciocome1.fireflow

import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * returns a flow that listens to querySnapshot changes and offers them
 * @param query need to be specified
 */
@ExperimentalCoroutinesApi
@Deprecated("Will be removed on next release", ReplaceWith("ref.asFlow()"))
inline fun <reified T>getAsFlowFromQuery(query: Query): Flow<MutableList<T>> =
    callbackFlow {
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
    }

/**
 * returns a flow that listens to querySnapshot changes and offers them
 */
@ExperimentalCoroutinesApi
@Deprecated("to remove", ReplaceWith("asFlow"))
inline fun <reified T>Query.getAsFlow(): Flow<MutableList<T>> =
    getAsFlowFromQuery(this)

/**
 * returns a flow that listens to querySnapshot changes and offers them
 */
@ExperimentalCoroutinesApi
inline fun <reified T>Query.asFlow(): Flow<MutableList<T>> =
    getAsFlowFromQuery(this)

/**
 * returns a flow that listens to querySnapshot changes and offer it
 */
@ExperimentalCoroutinesApi
fun Query.snapshotAsFlow(): Flow<QuerySnapshot?> =
    callbackFlow {
        val listener = EventListener<QuerySnapshot> { snapshot, error ->
            if (error != null) {
                close(error)
                return@EventListener
            }
            offer(snapshot)
        }
        val registration = addSnapshotListener(listener)
        awaitClose { registration.remove() }
    }

/**
 * returns a flow that listens to documentSnapshot changes and offers them
 * @param reference need to be specified
 */
@ExperimentalCoroutinesApi
@Deprecated("Will be removed on next release", ReplaceWith("ref.asFlow()"))
inline fun <reified T>getAsFlowFromReference(reference: DocumentReference): Flow<T?> =
    callbackFlow {
        val listener = EventListener<DocumentSnapshot> { snapshot, error ->
            if (error != null) {
                close(error)
                return@EventListener
            }
            if (snapshot != null) {
                val obj = snapshot.toObject<T>()
                offer(obj)
            }
        }
        val registration = reference.addSnapshotListener(listener)
        awaitClose { registration.remove() }
    }

/**
 * returns a flow that listens to documentSnapshot changes and offers them
 */
@Deprecated("to remove", ReplaceWith("asFlow"))
@ExperimentalCoroutinesApi
inline fun <reified T>DocumentReference.getAsFlow(): Flow<T?> =
    getAsFlowFromReference(this)

/**
 * returns a flow that listens to documentSnapshot changes and offers them
 */
@ExperimentalCoroutinesApi
inline fun <reified T>DocumentReference.asFlow(): Flow<T?> =
    getAsFlowFromReference(this)

/**
 * returns a flow that listens to documentSnapshot changes and offer it
 */
@ExperimentalCoroutinesApi
fun DocumentReference.snapshotAsFlow(): Flow<DocumentSnapshot?> =
    callbackFlow {
        val listener = EventListener<DocumentSnapshot> { snapshot, error ->
            if (error != null) {
                close(error)
                return@EventListener
            }
            offer(snapshot)
        }
        val registration = addSnapshotListener(listener)
        awaitClose { registration.remove() }
    }