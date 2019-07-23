package bry1337.github.io.creditnotebook.presentation.home

import androidx.lifecycle.MutableLiveData
import bry1337.github.io.creditnotebook.base.BaseViewModel
import bry1337.github.io.creditnotebook.model.Person

/**
 * Created by edwardbryan.abergas on 07/23/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class CreditViewModel: BaseViewModel(){
  private val id = MutableLiveData<Int>()

  private val name = MutableLiveData<String>()

  private val credit = MutableLiveData<String>()


  fun bind(person: Person){
    id.value = person.id
    name.value = person.name
    credit.value = person.credit.toString()
  }

  fun getName(): MutableLiveData<String>{
    return name
  }

  fun getCredit(): MutableLiveData<String>{
    return credit
  }
}