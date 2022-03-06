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

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

/**
 * returns a flow that listens to querySnapshot changes and offers them
 * @param coroutineContext default is Dispatchers.IO
 */
@ExperimentalCoroutinesApi
inline fun <reified T> Query.asFlow(
    coroutineContext: CoroutineContext = Dispatchers.IO,
): Flow<MutableList<T>> = snapshotAsFlow(coroutineContext).map {
    it?.toObjects(T::class.java) ?: mutableListOf()
}

/**
 * returns a flow that listens to documentSnapshot changes and offers them
 * @param coroutineContext default is Dispatchers.IO
 */
@ExperimentalCoroutinesApi
inline fun <reified T> DocumentReference.asFlow(
    coroutineContext: CoroutineContext = Dispatchers.IO,
): Flow<T?> = snapshotAsFlow(coroutineContext).map {
    it?.toObject(T::class.java)
}
