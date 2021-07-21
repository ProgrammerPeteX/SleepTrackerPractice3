package com.pdstudios.sleeptrackerpractice3.frags.sleep_tracking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pdstudios.sleeptrackerpractice3.R
import com.pdstudios.sleeptrackerpractice3.database.SleepNightDatabase
import com.pdstudios.sleeptrackerpractice3.databinding.FragmentSleepTrackingBinding

class SleepTrackingFragment: Fragment() {

    private lateinit var binding: FragmentSleepTrackingBinding
    private lateinit var viewModel: SleepTrackingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sleep_tracking, container, false)

        //Database
        val application = requireNotNull(this.activity).application
        val dao = SleepNightDatabase.getInstance(application).sleepNightDatabaseDao

        //ViewModel
        val factory = SleepTrackingViewModelFactory(dao)
        viewModel = ViewModelProvider(this, factory).get(SleepTrackingViewModel::class.java)
        binding.sleepTrackingViewModel = viewModel
        binding.lifecycleOwner = this

        //Observers
        viewModel.navigateToQuality.observe(viewLifecycleOwner) {night ->
            night?.let {
                this.findNavController().navigate(SleepTrackingFragmentDirections.actionSleepTrackingFragmentToSleepQualityFragment(night.nightId))
            }
        }

        viewModel.deleteAction.observe(viewLifecycleOwner) { isDelete ->
            isDelete?.let {
                val textDeleteKey = binding.editTextDeleteId.text
                textDeleteKey?.let {
                    viewModel.deleteKey.value = it.toString().toLong()
                }
            }
        }

        return binding.root
    }
}