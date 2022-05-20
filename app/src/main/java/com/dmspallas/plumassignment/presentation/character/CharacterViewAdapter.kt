package com.dmspallas.plumassignment.presentation.character

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dmspallas.plumassignment.GlideApp
import com.dmspallas.plumassignment.R
import com.dmspallas.plumassignment.domain.model.CharacterModel

class CharacterViewAdapter(private val context: Context) :
    RecyclerView.Adapter<CharacterViewAdapter.DataViewHolder>() {
    private var charactersList: List<CharacterModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentItem = charactersList[position]
        holder.name.text = currentItem.name
        holder.description.text = currentItem.description
        if (currentItem.thumbnail.contains("image_not_available")) {
            holder.thumbnail.setImageResource(R.drawable.ic_baseline_not_available_24)
        } else {
            GlideApp.with(context).load(
                currentItem.thumbnail.plus("/standard_fantastic.").plus(currentItem.thumbnailExt)
            ).into(holder.thumbnail)
        }
        holder.characterLayout.setOnClickListener {
            val intent = Intent(context, HeroDetailsActivity::class.java)
            intent.putExtra("name", currentItem.name)
            intent.putExtra("description", currentItem.description)
            intent.putExtra(
                "image",
                currentItem.thumbnail.plus("/portrait_incredible.").plus(currentItem.thumbnailExt)
            )
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = charactersList.size

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val description: TextView = itemView.findViewById(R.id.description)
        val name: TextView = itemView.findViewById(R.id.name)
        val thumbnail: ImageView = itemView.findViewById(R.id.thumbnail)
        val characterLayout: LinearLayout = itemView.findViewById(R.id.charactersLinearLayout)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addData(list: List<CharacterModel>) {
        charactersList = list
        notifyDataSetChanged()
    }
}