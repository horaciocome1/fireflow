/*
 * Copyright 2022 Horácio Flávio Comé Júnior
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
 * @param coroutineContext default is Dispatchers.IO
 */
@ExperimentalCoroutinesApi
fun DocumentReference.snapshotAsFlow(
    coroutineContext: CoroutineContext = Dispatchers.IO,
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
    }
}.flowOn(coroutineContext)

/**
 * returns a flow that listens to querySnapshot changes and offer it
 * @param coroutineContext default is Dispatchers.IO
 */
@ExperimentalCoroutinesApi
fun Query.snapshotAsFlow(
    coroutineContext: CoroutineContext = Dispatchers.IO,
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
    }
}.flowOn(coroutineContext)
