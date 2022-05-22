package com.dmspallas.plumassignment.presentation.character

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmspallas.plumassignment.R
import com.dmspallas.plumassignment.data.remote.db.CharacterDatabase
import com.dmspallas.plumassignment.data.remote.db.CharacterEntity
import com.dmspallas.plumassignment.data.remote.db.CharacterRepository
import com.dmspallas.plumassignment.domain.model.Character
import com.dmspallas.plumassignment.presentation.squad.SquadViewAdapter
import com.dmspallas.plumassignment.presentation.squad.SquadViewModel
import com.dmspallas.plumassignment.presentation.squad.SquadViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_characters.*
import kotlinx.android.synthetic.main.fragment_characters.view.*
import javax.inject.Inject

@AndroidEntryPoint
class CharacterFragment : Fragment() {
    private lateinit var viewModel: HireCharacterViewModel
    private lateinit var squadViewModel: SquadViewModel

    @Inject
    lateinit var viewModelFactory: HireCharacterViewModelFactory

    lateinit var squadViewModelFactory: SquadViewModelFactory

    @Inject
    lateinit var repository: CharacterRepository

    lateinit var adapter: CharacterViewAdapter
    lateinit var squadAdapter: SquadViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_characters, container, false)
        view.recycler_view.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        view.recycler_view_horizontal.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        view.recycler_view.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
        val dao = activity?.let { CharacterDatabase.getInstance(it).dao }
        repository = CharacterRepository(dao!!)
        squadAdapter = SquadViewAdapter(requireContext())
        adapter = CharacterViewAdapter(requireContext())
        view.recycler_view.adapter = adapter
        view.recycler_view_horizontal.adapter = squadAdapter
        setUpViewModel()
        observeLoader()
        observeData()
        displayCharacterList()
        return view
    }

    private fun observeData() {
        viewModel.characters.observe(
            this as LifecycleOwner
        ) { result ->
            if (result.getOrNull() != null) {
                val gson = GsonBuilder().setPrettyPrinting().create()
                Log.d("TAG", "observeWork: ${gson.toJson(result.getOrNull())}")
                renderData(result.getOrNull()!!)
            } else if (result.isFailure) {
                Snackbar.make(
                    relativeLayout,
                    R.string.generic_error,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun displayCharacterList() {
        squadViewModel.heroes.observe(this as LifecycleOwner) {
            Log.i("haha", it.toString())
            renderDataHorizontal(it)
        }
    }

    private fun observeLoader() {
        viewModel.loader.observe(this as LifecycleOwner) { loading ->
            when (loading) {
                true -> loader.visibility = View.VISIBLE
                else -> loader.visibility = View.GONE
            }
        }
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[HireCharacterViewModel::class.java]
        squadViewModelFactory = SquadViewModelFactory(this, repository)
        squadViewModel = ViewModelProvider(this, squadViewModelFactory)[SquadViewModel::class.java]

    }

    private fun renderData(characters: List<Character>) {
        adapter.addData(characters)
    }

    private fun renderDataHorizontal(characters: List<CharacterEntity>) {
        squadAdapter.addData(characters)
        if (squadAdapter.itemCount > 0) {
            recycler_view_horizontal.visibility = View.VISIBLE
            squad.visibility = View.VISIBLE
        } else {
            recycler_view_horizontal.visibility = View.GONE
            squad.visibility = View.GONE
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CharacterFragment().apply { }
    }
}