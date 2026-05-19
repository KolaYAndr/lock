package org.homesharing.lockbox.presentation.navigation

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

internal fun <T : Any> navBackstackSaver(serializer: KSerializer<T>): Saver<SnapshotStateList<T>, String> =
    Saver(
        save = { backstack ->
            Json.encodeToString(ListSerializer(serializer), backstack.toList())
        },
        restore = { json ->
            Json.decodeFromString(ListSerializer(serializer), json).toMutableStateList()
        }
    )
