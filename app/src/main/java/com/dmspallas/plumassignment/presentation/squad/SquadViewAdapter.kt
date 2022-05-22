package com.dmspallas.plumassignment.presentation.squad

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
import com.dmspallas.plumassignment.data.remote.db.CharacterEntity

class SquadViewAdapter(private val context: Context) :
    RecyclerView.Adapter<SquadViewAdapter.DataViewHolder>(){
    private var characterEntity: List<CharacterEntity> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.squad_item_row, parent, false)
        return DataViewHolder(view)
    }
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentItem = characterEntity[position]
        holder.name.text = currentItem.name
        holder.description.text = currentItem.description
        GlideApp.with(context).load(
            currentItem.image
        ).into(holder.thumbnail)
        holder.characterLayout.setOnClickListener {
            val intent = Intent(context, SquadDetailsActivity::class.java)
            intent.putExtra("name", currentItem.name)
            intent.putExtra("description", currentItem.description)
            intent.putExtra("image", currentItem.image)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return characterEntity.size
    }


    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val description: TextView = itemView.findViewById(R.id.description)
        val name: TextView = itemView.findViewById(R.id.name)
        val thumbnail: ImageView = itemView.findViewById(R.id.thumbnail)
        val characterLayout: LinearLayout = itemView.findViewById(R.id.charactersLinearLayout)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addData(list: List<CharacterEntity>) {
        characterEntity = list
        notifyDataSetChanged()
    }
}