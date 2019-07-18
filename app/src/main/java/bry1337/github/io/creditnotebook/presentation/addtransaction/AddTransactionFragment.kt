package bry1337.github.io.creditnotebook.presentation.addtransaction

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import bry1337.github.io.creditnotebook.MainActivity
import bry1337.github.io.creditnotebook.databinding.FragmentAddTransactionBinding
import bry1337.github.io.creditnotebook.injection.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_add_transaction.*

/**
 * Created by edwardbryan.abergas on 07/17/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class AddTransactionFragment : Fragment() {

  private lateinit var viewModel: AddTransactionViewModel
  private lateinit var activityContext: MainActivity
  private lateinit var binding: FragmentAddTransactionBinding

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = FragmentAddTransactionBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel = ViewModelProviders.of(this, ViewModelFactory(activityContext))
        .get(AddTransactionViewModel::class.java)

    llDebit.setOnClickListener {
      layoutDebit.visibility = View.VISIBLE
      layoutCredit.visibility = View.GONE
    }

    llCredit.setOnClickListener {
      layoutCredit.visibility = View.VISIBLE
      layoutDebit.visibility = View.GONE
    }
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    activityContext = context as MainActivity
  }
}