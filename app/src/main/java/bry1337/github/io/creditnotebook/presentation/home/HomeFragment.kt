package bry1337.github.io.creditnotebook.presentation.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import bry1337.github.io.creditnotebook.MainActivity
import bry1337.github.io.creditnotebook.R
import bry1337.github.io.creditnotebook.databinding.FragmentHomeBinding
import bry1337.github.io.creditnotebook.injection.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by edwardbryan.abergas on 07/17/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class HomeFragment : Fragment() {

  private lateinit var viewModel: HomeViewModel
  private lateinit var activityContext: MainActivity
  private lateinit var binding: FragmentHomeBinding


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = FragmentHomeBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel = ViewModelProviders.of(this, ViewModelFactory(activityContext)).get(HomeViewModel::class.java)
    binding.viewModel = viewModel

    viewModel.totalCredits.observe(this, Observer { totalCredit ->
      if (totalCredit != null) tvTotalCredit.text = String.format(getString(R.string.total_credit_placeholder),
          totalCredit.toString())
    })

    addNewCredit.setOnClickListener { findNavController().navigate(HomeFragmentDirections.actionToTransaction()) }
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    activityContext = context as MainActivity
  }

  override fun onResume() {
    super.onResume()
    viewModel.loadPersonList()
  }
}