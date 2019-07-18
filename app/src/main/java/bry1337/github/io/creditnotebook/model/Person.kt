package bry1337.github.io.creditnotebook.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by edwardbryan.abergas on 07/17/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@Entity
data class Person(@SerializedName("name") var name: String? = null) {

  @PrimaryKey(autoGenerate = true)
  var id: Int = 0

  @SerializedName("phoneNumber")
  var phoneNumber: String? = null

  @SerializedName("debit")
  var debit: Int = 0

  @SerializedName("credit")
  var credit: Int = 0

  @SerializedName("totalCredit")
  var totalCredit: Int = credit - debit

  @SerializedName("date")
  var date: String? = null
}