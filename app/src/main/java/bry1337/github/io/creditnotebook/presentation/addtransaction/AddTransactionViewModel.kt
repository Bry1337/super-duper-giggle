package bry1337.github.io.creditnotebook.presentation.addtransaction

import android.view.View
import androidx.lifecycle.MutableLiveData
import bry1337.github.io.creditnotebook.R
import bry1337.github.io.creditnotebook.base.BaseViewModel
import bry1337.github.io.creditnotebook.model.Finance
import bry1337.github.io.creditnotebook.model.Person
import bry1337.github.io.creditnotebook.model.dao.FinanceDao
import bry1337.github.io.creditnotebook.model.dao.PersonDao
import bry1337.github.io.creditnotebook.util.extension.getCurrentDateTime
import bry1337.github.io.creditnotebook.util.extension.toString
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by edwardbryan.abergas on 07/18/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class AddTransactionViewModel(private val personDao: PersonDao, private val financeDao: FinanceDao) : BaseViewModel() {

  val errorMessage: MutableLiveData<Int> = MutableLiveData()
  val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

  private lateinit var subscription: Disposable

  override fun onCleared() {
    super.onCleared()
    subscription.dispose()
  }

  fun createPersonObjectForCredit(personName: String, phoneNumber: String, creditAmount: Int) {
    val person = Person(personName)
    person.phoneNumber = phoneNumber
    person.credit = creditAmount
  }

  private fun insertNewEntryForCredit(person: Person) {
    subscription = Observable.fromCallable { personDao.insert(person) }
        .concatMap { personId ->
          val date = getCurrentDateTime()
          val dateInString = date.toString("yyyy/MM/dd HH:mm:ss")
          val finance = Finance(personId.toInt())
          finance.credit = person.credit
          finance.date = dateInString
          Observable.fromCallable { financeDao.insert(finance) }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { onInsertProcessStart() }
        .doOnTerminate { loadingVisibility.value = View.GONE }
        .subscribe({ result ->
          onSuccessfulInsert(result)
        }, {
          onInsertError()
        })
  }

  private fun onSuccessfulInsert(result: Long) {

  }

  private fun onInsertError() {
    errorMessage.value = R.string.error_adding_transaction
  }

  private fun onInsertProcessStart() {
    loadingVisibility.value = View.VISIBLE
    errorMessage.value = null
  }

  private fun onInsertProcessFinish() {
    loadingVisibility.value = View.GONE
  }
}