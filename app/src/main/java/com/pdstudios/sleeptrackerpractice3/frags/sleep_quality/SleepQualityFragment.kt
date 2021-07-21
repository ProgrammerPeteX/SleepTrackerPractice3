package com.pdstudios.sleeptrackerpractice3.frags.sleep_quality

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
import com.pdstudios.sleeptrackerpractice3.databinding.FragmentSleepQualityBinding

class SleepQualityFragment: Fragment() {

    private lateinit var binding: FragmentSleepQualityBinding
    private lateinit var viewModel: SleepQualityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Args
        val args = SleepQualityFragmentArgs.fromBundle(requireArguments())

        //Binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sleep_quality, container, false)

        //Database
        val application = requireNotNull(this.activity).application
        val dao = SleepNightDatabase.getInstance(application).sleepNightDatabaseDao

        //ViewModel
        val factory = SleepQualityFragmentFactory(args.key, dao)
        viewModel = ViewModelProvider(this, factory).get(SleepQualityViewModel::class.java)
        binding.sleepQualityViewModel = viewModel
        binding.lifecycleOwner = this

        //Observers
        viewModel.navigateToTracking.observe(viewLifecycleOwner) { navigate ->
            navigate?.let {
                this.findNavController().navigate(SleepQualityFragmentDirections.actionSleepQualityFragmentToSleepTrackingFragment())
            }
        }


        return binding.root
    }
}