package bry1337.github.io.creditnotebook.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import bry1337.github.io.creditnotebook.model.Finance

/**
 * Created by edwardbryan.abergas on 07/17/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@Dao
interface FinanceDao {

  @get:Query("Select * from finance")
  val all: List<Finance>

  @Query("Select * from finance where personId = :personId")
  fun getFinanceByPerson(personId: Int): Finance

  @Query("Select * from finance where id = :id")
  fun getFinance(id: Int): Finance

  @Query("Select SUM(credit) as total from finance where personId = :id")
  fun getAllCreditsOfPerson(id: Int): Double

  @Query("Select SUM(credit) - SUM(debit) as total from finance where personId = :id")
  fun getTotalCreditsOfPerson(id: Int): Double

  @Query("Select * from finance where personId = :id")
  fun getTransactionhistoryOfPerson(id: Int): List<Finance>

  @Insert
  fun insert(finance: Finance): Long

  @Query("Delete from finance")
  fun deleteAll()

  @Query("Delete from finance where personId = :id")
  fun deleteTransactionsOfPerson(id: Int)

  @Delete
  fun delete(finance: Finance)
}