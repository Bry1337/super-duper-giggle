package bry1337.github.io.creditnotebook.presentation.home

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import bry1337.github.io.creditnotebook.R
import bry1337.github.io.creditnotebook.base.BaseViewModel
import bry1337.github.io.creditnotebook.model.Person
import bry1337.github.io.creditnotebook.model.dao.FinanceDao
import bry1337.github.io.creditnotebook.model.dao.PersonDao
import bry1337.github.io.creditnotebook.util.OnRemoveItemListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by edwardbryan.abergas on 07/17/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class HomeViewModel(private val personDao: PersonDao,
    private val financeDao: FinanceDao) : BaseViewModel(), OnRemoveItemListener {

  val creditListAdapter: CreditListAdapter = CreditListAdapter()
  val errorMessage: MutableLiveData<Int> = MutableLiveData()
  val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
  val totalCredits: MutableLiveData<Double> = MutableLiveData()
  val emptyList: MutableLiveData<Int> = MutableLiveData()

  private lateinit var removeItemDialog: AlertDialog
  private lateinit var personList: ArrayList<Person>
  private lateinit var subscription: Disposable
  private var person: Person? = null
  private var pos: Int = 0
  private var credit: Double = 0.0

  override fun onCleared() {
    super.onCleared()
    if(::subscription.isInitialized) {
      subscription.dispose()
    }
  }

  fun createRemoveItemDialog(context: Context) {
    removeItemDialog = AlertDialog.Builder(context)
        .setTitle(R.string.delete)
        .setMessage(R.string.are_you_sure)
        .setPositiveButton(R.string.ok) { _, _ ->
          deleteFromDb()
          dismissAndSetDefaultValue()
        }
        .setNegativeButton(R.string.undo) { _, _ ->
          undoRemoveItem()
          dismissAndSetDefaultValue()
        }
        .create()
  }

  fun setAdapterclickListener(creditClickListener: CreditClickListener) {
    creditListAdapter.setCreditClickListener(creditClickListener)
  }

  fun loadPersonList() {
    subscription =
        Observable.fromCallable { personDao.all }.concatMap { persons ->
          val personList: ArrayList<Person> = ArrayList()
          for (person: Person in persons) {
            person.credit = financeDao.getTotalCreditsOfPerson(person.id)
            personList.add(person)
            credit += person.credit
          }
          Observable.just(personList)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePersonListStart() }
            .doOnTerminate { onRetrievePersonListFinish() }
            .subscribe({ result ->
              onRetrievePersonListSuccess(result)
            }, {
              onRetrievePersonListError()
            })
  }

  private fun deleteFromDb(){
    subscription = Observable.fromCallable { personDao.deleteAPerson(person!!) }
        .concatMap { Observable.just(financeDao.deleteTransactionsOfPerson(person!!.id)) }
        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe({
          onDeleteSuccess()
        }, {
          onDeleteError()
        })
  }

  private fun onDeleteSuccess() {
    loadPersonList()
  }

  private fun onDeleteError() {
    errorMessage.value = R.string.error_deleting
  }

  private fun onRetrievePersonListError() {
    errorMessage.value = R.string.error_loading_list
  }

  private fun onRetrievePersonListSuccess(result: List<Person>) {
    if (result.isEmpty()) {
      emptyList.value = R.string.empty_credit_list
    } else {
      emptyList.value = null
      creditListAdapter.updateCreditList(result)
      personList = ArrayList(result)
    }
  }

  private fun onRetrievePersonListStart() {
    loadingVisibility.value = View.VISIBLE
    errorMessage.value = null
    totalCredits.value = null
    emptyList.value = null
    credit = 0.0
  }

  private fun onRetrievePersonListFinish() {
    loadingVisibility.value = View.GONE
    totalCredits.value = credit
  }

  override fun onRemoveItem(position: Int) {
    pos = position
    person = personList[position]
    personList.removeAt(position)
    creditListAdapter.updateCreditList(personList)
    removeItemDialog.show()
  }

  private fun undoRemoveItem() {
    personList.add(pos, person!!)
    creditListAdapter.updateCreditList(personList)
  }

  private fun dismissAndSetDefaultValue() {
    removeItemDialog.dismiss()
    pos = 0
    person = null
  }


}