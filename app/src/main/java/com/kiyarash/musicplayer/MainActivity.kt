package com.kiyarash.musicplayer

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.kiyarash.musicplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
	private lateinit var viewModel: MainViewModel
	private var _binding: ActivityMainBinding? = null
	private val binding get() = _binding!!

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		_binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
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
		val musicFiles = viewModel.getAllAudioFiles(this)
		//Log.d("music", musicFiles.toString())
		binding.recyclerview.adapter = MusicListAdapter(musicFiles)
	}

}