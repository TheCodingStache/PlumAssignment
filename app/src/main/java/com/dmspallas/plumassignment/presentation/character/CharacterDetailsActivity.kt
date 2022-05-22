package com.dmspallas.plumassignment.presentation.character

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dmspallas.plumassignment.GlideApp
import com.dmspallas.plumassignment.R
import com.dmspallas.plumassignment.data.remote.db.CharacterDatabase
import com.dmspallas.plumassignment.data.remote.db.CharacterRepository
import com.dmspallas.plumassignment.databinding.ActivityHeroDetailsBinding
import com.dmspallas.plumassignment.presentation.MainActivity
import com.dmspallas.plumassignment.presentation.squad.HireSquadViewModel
import com.dmspallas.plumassignment.presentation.squad.HireSquadViewModelFactory
import javax.inject.Inject

class CharacterDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHeroDetailsBinding
    private lateinit var hireSquadViewModel: HireSquadViewModel
    private lateinit var hireButton: Button
    private lateinit var heroTextView: TextView
    private lateinit var characterImageView: ImageView

    @Inject
    lateinit var repository: CharacterRepository

    @Inject
    lateinit var hireSquadViewModelFactory: HireSquadViewModelFactory


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpViewModel()
        heroTextView = findViewById(R.id.name)
        characterImageView = findViewById(R.id.hero_image)
        hireButton = findViewById(R.id.hire)
        val image = intent.getStringExtra("image")
        GlideApp.with(this).load(image).into(characterImageView)
        onClick()
        decorChanges()
        displayCharacterList()
    }

    private fun setUpViewModel() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hero_details)
        val dao = CharacterDatabase.getInstance(application).dao
        repository = CharacterRepository(dao)
        hireSquadViewModelFactory = HireSquadViewModelFactory(this, repository, intent.extras)
        hireSquadViewModel = ViewModelProvider(this, hireSquadViewModelFactory)[HireSquadViewModel::class.java]
        binding.viewModel = hireSquadViewModel
        binding.lifecycleOwner = this
    }

    private fun displayCharacterList() {
        hireSquadViewModel.heroes.observe(this) { characters ->
            Log.i("TAG", characters.toString())
        }
    }

    private fun decorChanges() {
        supportActionBar?.hide()
        window.statusBarColor = R.color.transparent
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun onClick() {
        hireButton.setOnClickListener {
            hireSquadViewModel.existsByName(heroTextView.text.toString()).observe(this) { result ->
                if (!result) {
                    hireSquadViewModel.saveButton()
                    Toast.makeText(
                        this,
                        heroTextView.text.toString() + " " + resources.getString(R.string.toast_hired_successfully) ,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        heroTextView.text.toString() + " " + resources.getString(R.string.toast_belongs_to_squad),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }
}