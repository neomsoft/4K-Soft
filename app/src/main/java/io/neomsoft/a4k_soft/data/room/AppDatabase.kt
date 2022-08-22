package io.neomsoft.a4k_soft.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.neomsoft.a4k_soft.data.room.dao.NotesDao
import io.neomsoft.a4k_soft.data.room.entities.NoteEntity

@Database(
    version = 1,
    entities = [NoteEntity::class],
)
abstract class AppDatabase : RoomDatabase() {

    abstract val notesDao: NotesDao

    companion object {

        private const val DATABASE_NAME = "database"

        fun getInstance(context: Context): AppDatabase {
            return Room
                .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
        }
    }
}