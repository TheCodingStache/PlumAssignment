package com.dmspallas.plumassignment.presentation.squad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dmspallas.plumassignment.R

import android.content.Intent

import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.dmspallas.plumassignment.GlideApp
import com.dmspallas.plumassignment.data.remote.db.CharacterDatabase
import com.dmspallas.plumassignment.data.remote.db.CharacterRepository
import com.dmspallas.plumassignment.databinding.ActivitySquadDetailsBinding
import com.dmspallas.plumassignment.presentation.MainActivity
import com.dmspallas.plumassignment.util.PreferencesServiceImpl
import com.google.android.material.appbar.CollapsingToolbarLayout
import javax.inject.Inject


class SquadDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySquadDetailsBinding
    private lateinit var fireSquadViewModel: FireSquadViewModel
    lateinit var squadAdapter: SquadViewAdapter

    @Inject
    lateinit var repository: CharacterRepository

    @Inject
    lateinit var fireSquadViewModelFactory: FireSquadViewModelFactory

    @Inject
    lateinit var impl: PreferencesServiceImpl

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_squad_details)

        val dao = CharacterDatabase.getInstance(application).dao
        repository = CharacterRepository(dao)
        impl = PreferencesServiceImpl(this)
        fireSquadViewModelFactory = FireSquadViewModelFactory(this, repository, impl, intent.extras)
        fireSquadViewModel =
            ViewModelProvider(this, fireSquadViewModelFactory)[FireSquadViewModel::class.java]
        binding.viewModel = fireSquadViewModel
        binding.lifecycleOwner = this

        val collapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.collapse)
        val descriptionTextView = findViewById<TextView>(R.id.description)
        val heroTextView = findViewById<TextView>(R.id.name)
        val characterImageView = findViewById<ImageView>(R.id.hero_image)
        val fireButton = findViewById<Button>(R.id.fire)
        val heroName = intent.getStringExtra("name");
        val description = intent.getStringExtra("description")
        val image = intent.getStringExtra("image")
        if (description == "") {
            descriptionTextView.text = "No available information for this character"
        } else {
            descriptionTextView.text = description
        }

        heroTextView.text = heroName
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapseAppBar);
        GlideApp.with(this).load(image).into(characterImageView)
        displayCharacterList()
        fireButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.dialogTitle)
            builder.setMessage(R.string.dialogMessage)
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                fireSquadViewModel.deleteButton()
                Toast.makeText(this, "${heroTextView.text} fired from squad", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            builder.setNegativeButton("No") { dialogInterface, which ->
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }

    private fun displayCharacterList() {
        fireSquadViewModel.heroes.observe(this) { characters ->
            Log.i("TAG", characters.toString())
        }
    }
}