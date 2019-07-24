package bry1337.github.io.creditnotebook.presentation.persontransaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import bry1337.github.io.creditnotebook.R
import bry1337.github.io.creditnotebook.databinding.ItemPersonTransactionBinding
import bry1337.github.io.creditnotebook.model.Finance
import bry1337.github.io.creditnotebook.util.OnBindViewHolder

/**
 * Created by edwardbryan.abergas on 07/24/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class TransactionListAdapter : RecyclerView.Adapter<TransactionListAdapter.TransactionViewHolder>() {

  private lateinit var financeList: List<Finance>

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
    val binding: ItemPersonTransactionBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
        R.layout.item_person_transaction, parent, false)
    return TransactionViewHolder(binding)
  }

  override fun getItemCount(): Int {
    return if(::financeList.isInitialized) financeList.size else 0
  }

  override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
    holder.onBind(financeList[position])
  }

  fun updateFinanceList(financeList: List<Finance>){
    this.financeList = financeList
    notifyDataSetChanged()
  }

  inner class TransactionViewHolder(private val binding: ItemPersonTransactionBinding) : RecyclerView.ViewHolder(
      binding.root), OnBindViewHolder<Finance> {
    private val viewModel = TransactionViewModel()

    override fun onBind(obj: Finance) {
      viewModel.bind(obj)
      binding.viewModel = viewModel
    }
  }
}