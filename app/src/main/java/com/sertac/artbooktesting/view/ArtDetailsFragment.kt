package com.sertac.artbooktesting.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.sertac.artbooktesting.R
import com.sertac.artbooktesting.databinding.FragmentArtDetailBinding
import com.sertac.artbooktesting.util.Status
import com.sertac.artbooktesting.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtDetailsFragment @Inject constructor(
    val glide : RequestManager
) :Fragment(R.layout.fragment_art_detail) {

    private var fragmentBinding: FragmentArtDetailBinding? = null
    private lateinit var viewModel: ArtViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        val binding = FragmentArtDetailBinding.bind(view)
        fragmentBinding = binding

        subscribeToObservers()

        binding.artImageView.setOnClickListener {
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.saveButton.setOnClickListener {
            viewModel.makeArt(binding.nameText.text.toString(), binding.artistText.text.toString(), binding.yearText.text.toString())

        }
    }

    private fun subscribeToObservers(){
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url ->
            fragmentBinding?.let {
                glide.load(url).into(it.artImageView)
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMessage()
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_SHORT).show()
                }

                Status.LOADING -> {

                }
            }
        })
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}