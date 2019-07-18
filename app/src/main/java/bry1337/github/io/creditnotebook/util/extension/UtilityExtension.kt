package bry1337.github.io.creditnotebook.util.extension

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by edwardbryan.abergas on 07/18/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
  val formatter = SimpleDateFormat(format, locale)
  return formatter.format(this)
}

fun getCurrentDateTime(): Date {
  return Calendar.getInstance().time
}