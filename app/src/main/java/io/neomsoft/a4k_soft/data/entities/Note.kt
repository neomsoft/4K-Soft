package io.neomsoft.a4k_soft.data.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * Note. Клас який використовується в додатку.
 *
 * @param id ідентифікатор нотатки.
 * @param title основний текст нотатки.
 * @param description детальний опис нотатки.
 * @param changeDate дана останнього редагування нотатки. Format [Date].
 */
@Parcelize
data class Note(
    val id: String,
    val title: String,
    val description: String,
    val changeDate: Date,
) : Parcelable
