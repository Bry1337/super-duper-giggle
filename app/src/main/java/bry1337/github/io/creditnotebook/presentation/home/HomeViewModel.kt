package bry1337.github.io.creditnotebook.presentation.home

import android.view.View
import androidx.lifecycle.MutableLiveData
import bry1337.github.io.creditnotebook.R
import bry1337.github.io.creditnotebook.base.BaseViewModel
import bry1337.github.io.creditnotebook.model.Person
import bry1337.github.io.creditnotebook.model.dao.FinanceDao
import bry1337.github.io.creditnotebook.model.dao.PersonDao
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by edwardbryan.abergas on 07/17/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class HomeViewModel(private val personDao: PersonDao, private val financeDao: FinanceDao) : BaseViewModel() {

  val creditListAdapter: CreditListAdapter = CreditListAdapter()
  val errorMessage: MutableLiveData<Int> = MutableLiveData()
  val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
  val totalCredits: MutableLiveData<Int> = MutableLiveData()
  private lateinit var subscription: Disposable
  private var credit: Int = 0

  override fun onCleared() {
    super.onCleared()
    subscription.dispose()
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

  private fun onRetrievePersonListError() {
    errorMessage.value = R.string.error_loading_list
  }

  private fun onRetrievePersonListSuccess(result: List<Person>?) {
    creditListAdapter.updateCreditList(result!!)
  }

  private fun onRetrievePersonListStart() {
    loadingVisibility.value = View.VISIBLE
    errorMessage.value = null
    totalCredits.value = null
    credit = 0
  }

  private fun onRetrievePersonListFinish() {
    loadingVisibility.value = View.GONE
    totalCredits.value = credit
  }


}