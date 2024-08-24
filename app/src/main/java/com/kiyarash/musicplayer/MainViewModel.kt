package com.kiyarash.musicplayer

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore

class MainViewModel {
	fun getAllAudioFiles(context: Context): List<AudioFile> {
		val audioList = mutableListOf<AudioFile>()
		val projection = arrayOf(
			MediaStore.Audio.Media._ID,
			MediaStore.Audio.Media.TITLE,
			MediaStore.Audio.Media.ARTIST,
			MediaStore.Audio.Media.DATA // Path to the file
		)

		val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"

		val cursor: Cursor? = context.contentResolver.query(
			MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
			projection,
			selection,
			null,
			null
		)

		cursor?.use {
			val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
			val titleColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
			val artistColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
			val dataColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

			while (it.moveToNext()) {
				val id = it.getLong(idColumn)
				val title = it.getString(titleColumn)
				val artist = it.getString(artistColumn)
				val path = it.getString(dataColumn)

				audioList.add(AudioFile(id, title, artist, path))
			}
		}

		return audioList
	}

	// Data class to hold audio file information
	data class AudioFile(
		val id: Long,
		val title: String,
		val artist: String,
		val path: String
	)
}