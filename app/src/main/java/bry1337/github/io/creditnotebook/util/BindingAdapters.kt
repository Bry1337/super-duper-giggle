package bry1337.github.io.creditnotebook.util

import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import bry1337.github.io.creditnotebook.util.extension.getParentActivity

/**
 * Created by edwardbryan.abergas on 07/18/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@BindingAdapter("mutableVisibility")
fun setMutableVisibility(view: View, visibility: MutableLiveData<Int>?) {
  val parentActivity: AppCompatActivity? = view.getParentActivity()
  if (parentActivity != null && visibility != null) {
    visibility.observe(parentActivity, Observer { value -> view.visibility = value ?: View.VISIBLE })
  }
}

@BindingAdapter("mutableText")
fun setMutableText(view: TextView, text: MutableLiveData<String>?) {
  val parentActivity: AppCompatActivity? = view.getParentActivity()
  if (parentActivity != null && text != null) {
    text.observe(parentActivity, Observer { value -> view.text = value ?: "" })
  }
}

@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
  view.adapter = adapter
}
