package io.neomsoft.a4k_soft.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import io.neomsoft.a4k_soft.data.entities.Note
import io.neomsoft.a4k_soft.data.entities.response.Response
import io.neomsoft.a4k_soft.data.net.notes.NotesApi
import io.neomsoft.a4k_soft.data.net.notes.entities.Note.Companion.from
import io.neomsoft.a4k_soft.data.net.notes.entities.Note.Companion.toNote
import io.neomsoft.a4k_soft.data.room.AppDatabase
import io.neomsoft.a4k_soft.data.room.entities.NoteEntity
import io.neomsoft.a4k_soft.data.room.entities.NoteEntity.Companion.toNote
import io.neomsoft.a4k_soft.extensions.interval
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class DataRepository(
    private val context: Context,
    private val notesApi: NotesApi,
    private val database: AppDatabase,
): IDataRepository {

    private val dataChangingFlow = MutableStateFlow(false)

    override fun getNotes(): Flow<Response<List<Note>>> {

        return getDataFromServer()
            .map { serverResponse ->  when (serverResponse) {
                is Response.Empty -> serverResponse
                is Response.Success -> {
                    database.notesDao.deleteAll()
                    database.notesDao.insert(serverResponse.data.map { NoteEntity.from(it) })
                    serverResponse
                }
                is Response.Loading -> {
                    val cacheNotes = getDataFromCache()?.let { sortNotes(it) }
                    Response.Loading(cacheNotes)
                }
                is Response.ErrorNetworkConnection -> {
                    val cacheNotes = getDataFromCache()?.let { sortNotes(it) }
                    Response.ErrorNetworkConnection(cacheNotes)
                }
            } }
            .flowOn(Dispatchers.IO)
    }

    private fun getDataFromCache(): List<Note>? {
        return database.notesDao
            .getAll()
            .map { it.toNote() }
            .takeIf { it.isNotEmpty() }
    }

    private fun getDataFromServer(): Flow<Response<List<Note>>> {
        val dataChangingFlow: Flow<Boolean> = dataChangingFlow

        return hasConnection()
            .combine(dataChangingFlow, ::combineDataFromServer)
            .flatMapMerge { if (it is Response.Empty) getDataSnapFromServer() else flowOf(it) }
    }

    private fun combineDataFromServer(
        hasConnection: Boolean,
        dataChanging: Boolean
    ): Response<List<Note>> {
        if (!hasConnection) {
            return Response.ErrorNetworkConnection()
        }

        if (dataChanging) {
            return Response.Loading()
        }

        return Response.Empty()

    }

    private fun getDataSnapFromServer(): Flow<Response<List<Note>>> {
        return flow {
            emit(Response.Loading())

            val data = notesApi.getNotes().map { it.toNote() }
            emit(Response.Success(sortNotes(data)))
        }
    }

    private fun hasConnection(): Flow<Boolean> {
        return interval(1000)
            .map { isNetworkAvailable() }
            .distinctUntilChanged()
    }

    private fun isNetworkAvailable(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = cm.activeNetwork ?: return false
            val actNw = cm.getNetworkCapabilities(nw) ?: return false

            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            return cm.activeNetworkInfo?.isConnected ?: return false
        }
    }

    private fun sortNotes(notes: List<Note>): List<Note> {
        return notes.sortedByDescending { it.id }
    }

    override suspend fun addNote(note: Note): Response<Note> {
        dataChangingFlow.value = true
        database.notesDao.insert(NoteEntity.from(note))
        notesApi.addNote(from(note))
        dataChangingFlow.value = false

        return Response.Success(note)
    }

    override suspend fun updateNote(note: Note): Response<Note> {
        return addNote(note)
    }

    override suspend fun deleteNote(note: Note): Response<Note> {
        database.notesDao.deleteById(note.id)
        dataChangingFlow.value = true
        notesApi.removeNote(note.id)
        dataChangingFlow.value = false

        return Response.Success(note)
    }
}