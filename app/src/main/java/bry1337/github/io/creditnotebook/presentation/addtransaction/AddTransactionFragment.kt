package bry1337.github.io.creditnotebook.presentation.addtransaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import bry1337.github.io.creditnotebook.R
import kotlinx.android.synthetic.main.fragment_add_transaction.*

/**
 * Created by edwardbryan.abergas on 07/17/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class AddTransactionFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_add_transaction, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    llDebit.setOnClickListener {
      layoutDebit.visibility = View.VISIBLE
      layoutCredit.visibility = View.GONE
    }

    llCredit.setOnClickListener {
      layoutCredit.visibility = View.VISIBLE
      layoutDebit.visibility = View.GONE
    }
  }
}