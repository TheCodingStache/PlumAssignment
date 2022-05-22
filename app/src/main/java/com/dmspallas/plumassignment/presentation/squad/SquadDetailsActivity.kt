package com.dmspallas.plumassignment.presentation.squad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dmspallas.plumassignment.R

import android.content.Intent

import android.util.Log
import android.view.WindowManager
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
import javax.inject.Inject


class SquadDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySquadDetailsBinding
    private lateinit var fireSquadViewModel: FireSquadViewModel
    private lateinit var fireButton: Button
    private lateinit var heroTextView: TextView
    private lateinit var characterImageView: ImageView

    @Inject
    lateinit var repository: CharacterRepository

    @Inject
    lateinit var fireSquadViewModelFactory: FireSquadViewModelFactory

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
        fireButton = findViewById(R.id.fire)
        val image = intent.getStringExtra("image")
        GlideApp.with(this).load(image).into(characterImageView)
        onClick()
        decorChanges()
        displayCharacterList()

    }

    private fun setUpViewModel() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_squad_details)
        val dao = CharacterDatabase.getInstance(application).dao
        repository = CharacterRepository(dao)
        fireSquadViewModelFactory = FireSquadViewModelFactory(this, repository, intent.extras)
        fireSquadViewModel =
            ViewModelProvider(this, fireSquadViewModelFactory)[FireSquadViewModel::class.java]
        binding.viewModel = fireSquadViewModel
        binding.lifecycleOwner = this
    }

    private fun displayCharacterList() {
        fireSquadViewModel.heroes.observe(this) { characters ->
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
        fireButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.dialogTitle)
            builder.setMessage(R.string.dialogMessage)
            builder.setPositiveButton(R.string.yes) { _, _ ->
                fireSquadViewModel.deleteButton()
                Toast.makeText(
                    this,
                    heroTextView.text.toString() + " " + resources.getString(R.string.dialog_response),
                    Toast.LENGTH_SHORT
                )
                    .show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            builder.setNegativeButton(R.string.no) { _, _ ->
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }
}