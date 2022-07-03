package com.sertac.artbooktesting.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.sertac.artbooktesting.R
import com.sertac.artbooktesting.adapter.ImageRecyclerAdapter
import com.sertac.artbooktesting.databinding.FragmentImageApiBinding
import com.sertac.artbooktesting.util.Status
import com.sertac.artbooktesting.viewmodel.ArtViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageApiFragment @Inject constructor(
    val imageRecyclerAdapter: ImageRecyclerAdapter
) : Fragment(R.layout.fragment_image_api) {

    private lateinit var viewModel : ArtViewModel
    private var fragmentBinding : FragmentImageApiBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        val binding = FragmentImageApiBinding.bind(view)
        fragmentBinding = binding

        var job: Job? = null

        binding.searchText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()){
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }

        subscriveToObservers()

        binding.imageRecycleView.adapter = imageRecyclerAdapter
        binding.imageRecycleView.layoutManager = GridLayoutManager(requireContext(), 3)
        imageRecyclerAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }
    }

    fun subscriveToObservers(){
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map { imageResult ->
                        imageResult.previewURL
                    }

                    imageRecyclerAdapter.images = urls ?: listOf()

                    fragmentBinding?.let {
                        it.progressBar.visibility = View.GONE
                    }
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_SHORT).show()
                    fragmentBinding?.let {
                        it.progressBar.visibility = View.GONE
                    }
                }

                Status.LOADING -> {
                    fragmentBinding?.let {
                        it.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }
}