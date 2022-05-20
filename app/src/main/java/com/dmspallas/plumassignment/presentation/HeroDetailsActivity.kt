package com.dmspallas.plumassignment.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dmspallas.plumassignment.GlideApp
import com.dmspallas.plumassignment.R
import com.dmspallas.plumassignment.data.remote.db.CharacterDatabase
import com.dmspallas.plumassignment.data.remote.db.CharacterRepository
import com.dmspallas.plumassignment.databinding.ActivityHeroDetailsBinding
import com.dmspallas.plumassignment.util.PreferencesServiceImpl
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.fragment_characters.*
import kotlinx.android.synthetic.main.fragment_characters.view.*
import javax.inject.Inject

class HeroDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHeroDetailsBinding
    private lateinit var squadViewModel: SquadViewModel
    lateinit var squadAdapter: SquadViewAdapter

    @Inject
    lateinit var repository: CharacterRepository

    @Inject
    lateinit var squadViewModelFactory: SquadViewModelFactory

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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hero_details)

        val dao = CharacterDatabase.getInstance(application).dao
        repository = CharacterRepository(dao)
        impl = PreferencesServiceImpl(this)
        squadViewModelFactory = SquadViewModelFactory(this, repository, impl, intent.extras)
        squadViewModel = ViewModelProvider(this, squadViewModelFactory)[SquadViewModel::class.java]
        binding.viewModel = squadViewModel
        binding.lifecycleOwner = this

        val collapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.collapse)
        val descriptionTextView = findViewById<TextView>(R.id.description)
        val heroTextView = findViewById<TextView>(R.id.name)
        val characterImageView = findViewById<ImageView>(R.id.hero_image)
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
    }

    private fun displayCharacterList() {
        squadViewModel.heroes.observe(this) {
            Log.i("TAG", it.toString())
        }
    }
}