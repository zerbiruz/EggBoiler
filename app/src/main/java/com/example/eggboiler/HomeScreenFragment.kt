package com.example.eggboiler

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.eggboiler.databinding.FragmentHomeScreenBinding

class HomeScreenFragment : Fragment() {

    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var viewModelFactory: CountDownViewModelFactory
    private lateinit var viewModel: CountDownViewModel
    private var maxTime: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home_screen,
            container,
            false
        )

        viewModelFactory = CountDownViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(CountDownViewModel::class.java)

        viewModel.isRunning.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.btnStart.text = "Stop"
                binding.tvTimeRemain.text = "00:00"
                binding.etTimeValue.visibility = View.GONE
            } else {
                binding.btnStart.text = "Start"
                binding.etTimeValue.visibility = View.VISIBLE
            }
        })

        viewModel.currentTimeString.observe(viewLifecycleOwner, Observer {
            binding.tvTimeRemain.text = it
        })

        viewModel.currentTime.observe(viewLifecycleOwner, Observer {
            if (it == 0L) {
                binding.btnStart.text = "Start"
                binding.etTimeValue.visibility = View.VISIBLE
            }
        })

        binding.btnStart.setOnClickListener {
            maxTime = binding.etTimeValue.text.toString().toLong() * 60000L
            viewModel.maxTime.value = maxTime

            if (viewModel.isRunning.value == true) {
                viewModel.stopCountdown()
                binding.tvTimeRemain.text = "00:00"
                viewModel.isRunning.value = !viewModel.isRunning.value!!
            } else {
                viewModel.startCountdown()
                viewModel.isRunning.value = !viewModel.isRunning.value!!
            }
        }

        return binding.root
    }
}