package com.dmspallas.plumassignment.presentation.character

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dmspallas.plumassignment.GlideApp
import com.dmspallas.plumassignment.R
import com.dmspallas.plumassignment.data.remote.db.CharacterDatabase
import com.dmspallas.plumassignment.data.remote.db.CharacterRepository
import com.dmspallas.plumassignment.databinding.ActivityHeroDetailsBinding
import com.dmspallas.plumassignment.presentation.MainActivity
import com.dmspallas.plumassignment.presentation.squad.SquadViewAdapter
import com.dmspallas.plumassignment.presentation.squad.SquadViewModel
import com.dmspallas.plumassignment.presentation.squad.SquadViewModelFactory
import com.dmspallas.plumassignment.util.PreferencesServiceImpl
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
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
        val hireButton = findViewById<Button>(R.id.hire)
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
        hireButton.setOnClickListener {
            squadViewModel.existsByName(heroName.toString()).observe(this) { result ->
                if (!result) {
                    squadViewModel.saveButton()
                    Toast.makeText(
                        this,
                        "${heroTextView.text} hired successfully!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "${heroTextView.text} already belongs to squad!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

private fun displayCharacterList() {
    squadViewModel.heroes.observe(this) { characters ->
        Log.i("TAG", characters.toString())
    }
}
}