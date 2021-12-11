package io.github.horaciocome1.fireflow

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

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
