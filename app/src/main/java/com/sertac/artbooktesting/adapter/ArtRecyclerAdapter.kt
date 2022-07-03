package com.sertac.artbooktesting.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.sertac.artbooktesting.R
import com.sertac.artbooktesting.databinding.ArtRowBinding
import com.sertac.artbooktesting.roomdb.Art
import org.w3c.dom.Text
import javax.inject.Inject

class ArtRecyclerAdapter @Inject constructor(
    val glide : RequestManager
) : RecyclerView.Adapter<ArtRecyclerAdapter.ArtViewHolder>() {

    class ArtViewHolder(var view:ArtRowBinding) : RecyclerView.ViewHolder(view.root){

    }

    private val diffUtil = object : DiffUtil.ItemCallback<Art>() {
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var arts : List<Art>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.art_row, parent, false)
        val view = DataBindingUtil.inflate<ArtRowBinding>(LayoutInflater.from(parent.context), R.layout.art_row, parent, false )
        return ArtViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
//        val imageView = holder.itemView.findViewById<ImageView>(R.id.artRowImageView)
//        val nameText = holder.itemView.findViewById<TextView>(R.id.artRowNameText)
//        val artistNameText = holder.itemView.findViewById<TextView>(R.id.artRowArtistNameText)
//        val yearText = holder.itemView.findViewById<TextView>(R.id.artRowYearText)
//        val xmlArt =

        val art = arts[position]

//        holder.itemView.apply {
//            nameText.text = "Name: ${art.name}"
//            artistNameText.text = "Artist Name: ${art.artistName}"
//            yearText.text = "Year: ${art.year}"
//            glide.load(art.imageUrl).into(imageView)
//        }

        holder.view.xmlArt = art
//        holder.view.artRowNameText.text = "Name: ${art.name}"
        holder.view.artRowArtistNameText.text = "Artist Name: ${art.artistName}"
        holder.view.artRowYearText.text = "Year: ${art.year}"
        glide.load(art.imageUrl).into(holder.view.artRowImageView)
    }

    override fun getItemCount(): Int {
        return arts.size
    }
}