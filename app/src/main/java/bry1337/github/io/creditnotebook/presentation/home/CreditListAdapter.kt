package bry1337.github.io.creditnotebook.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import bry1337.github.io.creditnotebook.R
import bry1337.github.io.creditnotebook.databinding.ItemPersonTotalCreditBinding
import bry1337.github.io.creditnotebook.model.Person
import bry1337.github.io.creditnotebook.util.OnBindViewHolder

/**
 * Created by edwardbryan.abergas on 07/23/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
typealias CreditClickListener = (Person) -> Unit

class CreditListAdapter : RecyclerView.Adapter<CreditListAdapter.CreditListViewHolder>() {

  private lateinit var creditList: List<Person>
  private lateinit var creditClickListener: CreditClickListener

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditListViewHolder {
    val binding: ItemPersonTotalCreditBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
        R.layout.item_person_total_credit, parent, false)
    return CreditListViewHolder(binding)
  }

  override fun getItemCount(): Int {
    return if (::creditList.isInitialized) creditList.size else 0
  }

  override fun onBindViewHolder(holder: CreditListViewHolder, position: Int) {
    holder.onBind(creditList[position])
    holder.itemView.setOnClickListener { creditClickListener(creditList[position]) }
  }

  fun updateCreditList(personList: List<Person>) {
    this.creditList = personList
    notifyDataSetChanged()
  }

  fun setCreditClickListener(creditClickListener: CreditClickListener){
    this.creditClickListener = creditClickListener
  }

  inner class CreditListViewHolder(private val binding: ItemPersonTotalCreditBinding) :
      RecyclerView.ViewHolder(binding.root), OnBindViewHolder<Person> {

    private val viewModel = CreditViewModel()

    override fun onBind(obj: Person) {
      viewModel.bind(obj)
      binding.viewModel = viewModel
    }
  }
}