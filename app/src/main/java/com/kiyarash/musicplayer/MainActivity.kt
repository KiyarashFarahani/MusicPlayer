package com.kiyarash.musicplayer

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
	private lateinit var viewModel: MainViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		viewModel = MainViewModel()
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			if (ContextCompat.checkSelfPermission(
					this,
					android.Manifest.permission.READ_MEDIA_AUDIO
				)
				!= PackageManager.PERMISSION_GRANTED
			) {
				val requestPermissionLauncher = registerForActivityResult(
					ActivityResultContracts.RequestPermission()
				) { isGranted: Boolean ->
					if (isGranted) {
						readMusicFiles()
					} else {
						// Permission denied, handle accordingly
					}
				}

				requestPermissionLauncher.launch(android.Manifest.permission.READ_MEDIA_AUDIO)
			} else {
				readMusicFiles()
			}
		} else {

			if (ContextCompat.checkSelfPermission(
					this,
					android.Manifest.permission.READ_EXTERNAL_STORAGE
				)
				!= PackageManager.PERMISSION_GRANTED
			) {

				val requestPermissionLauncher = registerForActivityResult(
					ActivityResultContracts.RequestPermission()
				) { isGranted: Boolean ->
					if (isGranted) {
						readMusicFiles()
					} else {
						// Permission denied
					}
				}

				requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
			} else {
				readMusicFiles()
			}

		}
	}

	private fun readMusicFiles() {
		val play: Button = findViewById(R.id.play)
		val pause: Button = findViewById(R.id.pause)
		val stop: Button = findViewById(R.id.stop)
		val name: TextView = findViewById(R.id.nameTextView)
		val musicFiles = viewModel.getAllAudioFiles(this)
		Log.d("music", musicFiles.toString())
		//name.text = musicFiles[0].title
		//val music = MediaPlayer.create(this, R.raw.let_me_down_slowly)
		/*val music: MediaPlayer = MediaPlayer()
		music.setDataSource(musicFiles[0].path)
		play.setOnClickListener {
			music.start()
		}
		pause.setOnClickListener {
			music.pause()
		}
		stop.setOnClickListener {
			music.stop()
			music.prepare()
			music.seekTo(0)
		}
*/
	}

}