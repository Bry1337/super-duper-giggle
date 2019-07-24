package bry1337.github.io.creditnotebook.presentation.persontransaction

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
import bry1337.github.io.creditnotebook.databinding.FragmentPersonTransactionHistoryBinding
import bry1337.github.io.creditnotebook.injection.ViewModelFactory
import bry1337.github.io.creditnotebook.model.Person
import bry1337.github.io.creditnotebook.presentation.home.HomeFragmentDirections
import kotlinx.android.synthetic.main.fragment_person_transaction_history.*

/**
 * Created by edwardbryan.abergas on 07/24/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class PersonTransactionFragment : Fragment() {

  private lateinit var viewModel: PersonTransactionViewModel
  private lateinit var binding: FragmentPersonTransactionHistoryBinding
  private lateinit var activityContext: MainActivity

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    this.activityContext = context as MainActivity
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = FragmentPersonTransactionHistoryBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel = ViewModelProviders.of(this, ViewModelFactory(activityContext))
        .get(PersonTransactionViewModel::class.java)
    binding.viewModel = viewModel
    viewModel.totalCredits.observe(this, Observer { totalCredit ->
      if (totalCredit != 0) tvPersonTransactionTotalCredits.text = String.format(
          getString(R.string.total_credit_placeholder), totalCredit.toString())
    })
    val personId = PersonTransactionFragmentArgs.fromBundle(arguments!!).personId
    viewModel.loadTransactionHistory(personId)

    btnAddNewTransaction.setOnClickListener {
      findNavController().navigate(PersonTransactionFragmentDirections.actionToAddTransaction(personId))
    }
  }

}