package org.homesharing.lockbox.data.network.dto.post_image

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable
internal value class PostImageResponseDto(val link: String)
