package io.neomsoft.a4k_soft.data.room.dao

import androidx.room.*
import io.neomsoft.a4k_soft.data.room.entities.NoteEntity

@Dao
abstract class NotesDao {

    @Query("SELECT * FROM notes")
    abstract fun getAll(): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(items: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(items: List<NoteEntity>)

    @Query("DELETE FROM notes WHERE id = :id")
    abstract fun deleteById(id: String)

    @Query("DELETE FROM notes")
    abstract fun deleteAll()
}