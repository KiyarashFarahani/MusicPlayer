package com.kiyarash.musicplayer

import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class MusicListAdapter(private val data: List<MainViewModel.AudioFile>) :
	RecyclerView.Adapter<MusicListAdapter.ViewHolder>() {
	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val musicName: TextView = itemView.findViewById(R.id.musicNameTextView)
		val artistName: TextView = itemView.findViewById(R.id.artistNameTextView)
		val artworkImageView: ImageView = itemView.findViewById(R.id.artworkImageView)
		val cardView: CardView = itemView.findViewById(R.id.musicCardView)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val layout = LayoutInflater
			.from(parent.context)
			.inflate(R.layout.item_view, parent, false)

		return ViewHolder(layout)
	}

	override fun getItemCount(): Int {
		return data.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.musicName.text = data[position].title
		holder.artistName.text = data[position].artist

		if (data[position].albumId != null)
			holder.artworkImageView.setImageBitmap(data[position].albumId)

		holder.cardView.setOnClickListener {
			var music: MediaPlayer? = null
			Log.v("Music", data[position].path)
			music = MediaPlayer().apply {
				setDataSource(data[position].path)
				prepare()
				start()
			}
		}

	}
}