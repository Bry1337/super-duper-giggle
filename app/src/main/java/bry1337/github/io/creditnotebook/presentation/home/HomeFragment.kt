package bry1337.github.io.creditnotebook.presentation.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import bry1337.github.io.creditnotebook.MainActivity
import bry1337.github.io.creditnotebook.R
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

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_home, container,  false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel = ViewModelProviders.of(this, ViewModelFactory(activityContext)).get(HomeViewModel::class.java)

    addNewCredit.setOnClickListener { findNavController().navigate(HomeFragmentDirections.actionToTransaction()) }
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    activityContext = context as MainActivity
  }
}