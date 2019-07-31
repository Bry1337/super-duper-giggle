package bry1337.github.io.creditnotebook.presentation.addtransaction

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import bry1337.github.io.creditnotebook.base.MainActivity
import bry1337.github.io.creditnotebook.databinding.FragmentAddTransactionBinding
import bry1337.github.io.creditnotebook.injection.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_add_transaction.*
import kotlinx.android.synthetic.main.layout_credit.*
import kotlinx.android.synthetic.main.layout_debit.*

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
    viewModel.errorMessage.observe(this, Observer<Int> { errorMessage ->
      if (errorMessage != null) showError(errorMessage)
    })
    viewModel.successMessage.observe(this, Observer { successMessage ->
      showSuccessMessage(successMessage)
    })
    viewModel.personObject.observe(this, Observer { personObject ->
      if (personObject != null) {
        edtPersonName.setText(personObject.name)
        edtPersonName.isEnabled = false
      }
    })
    llDebit.setOnClickListener {
      layoutDebit.visibility = View.VISIBLE
      layoutCredit.visibility = View.GONE
    }

    llCredit.setOnClickListener {
      layoutCredit.visibility = View.VISIBLE
      layoutDebit.visibility = View.GONE
    }

    btnAddTransaction.setOnClickListener {
      if (layoutCredit.visibility == View.VISIBLE) {
        if (viewModel.fieldsNotEmptyForCredit(
                edtPersonName.text.toString(),
                edtPhoneNumber.text.toString(),
                edtCredit.text.toString())) {
          viewModel.createPersonObject(
              edtPersonName.text.toString(),
              edtPhoneNumber.text.toString(),
              edtCredit.text.toString().toDouble(), 0.0)
        }
      } else {
        if (viewModel.fieldsNotEmptyForDebit(
                edtPersonName.text.toString(),
                edtDebit.text.toString())) {
          viewModel.createPersonObject(
              edtPersonName.text.toString(),
              "",
              0.0, edtDebit.text.toString().toDouble())
        }
      }
    }
    initArguments()
  }

  private fun initArguments() {
    if (AddTransactionFragmentArgs.fromBundle(arguments!!).personId != 0) {
      viewModel.loadPersonInformation(AddTransactionFragmentArgs.fromBundle(arguments!!).personId)
    }
  }

  private fun showSuccessMessage(successMessage: Int?) {
    Toast.makeText(activityContext, successMessage!!, Toast.LENGTH_SHORT).show()
    findNavController().navigateUp()
  }

  private fun showError(errorMessage: Int) {
    Toast.makeText(activityContext, errorMessage, Toast.LENGTH_SHORT).show()
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    activityContext = context as MainActivity
  }
}