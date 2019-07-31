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
data class Finance(@SerializedName("personId") var personId: Int = 0) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @SerializedName("phoneNumber")
    var phoneNumber: String? = null

    @SerializedName("debit")
    var debit: Double = 0.0

    @SerializedName("credit")
    var credit: Double = 0.0

    @SerializedName("date")
    var date: String? = null

}