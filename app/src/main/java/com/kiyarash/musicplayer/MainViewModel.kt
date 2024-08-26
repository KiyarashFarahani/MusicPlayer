package com.kiyarash.musicplayer

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import java.io.InputStream

class MainViewModel {
	fun getAllAudioFiles(context: Context): List<AudioFile> {
		val audioList = mutableListOf<AudioFile>()
		val projection = arrayOf(
			MediaStore.Audio.Media._ID,
			MediaStore.Audio.Media.TITLE,
			MediaStore.Audio.Media.ARTIST,
			MediaStore.Audio.Media.DATA,
			MediaStore.Audio.Media.ALBUM_ID
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
			val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)

			while (it.moveToNext()) {
				val id = it.getLong(idColumn)
				val title = it.getString(titleColumn)
				val artist = it.getString(artistColumn)
				val path = it.getString(dataColumn)
				val albumId = cursor.getLong(albumIdColumn)
				val artworkUri = ContentUris.withAppendedId(
					Uri.parse("content://media/external/audio/albumart"),
					albumId
				)
				val artwork = try {
					val inputStream: InputStream? =
						context.contentResolver.openInputStream(artworkUri)
					BitmapFactory.decodeStream(inputStream)
				} catch (e: Exception) {
					null
				}
				audioList.add(AudioFile(id, title, artist, path, artwork))
			}
		}

		return audioList
	}

	data class AudioFile(
		val id: Long,
		val title: String,
		val artist: String,
		val path: String,
		val albumId: Bitmap?
	)
}