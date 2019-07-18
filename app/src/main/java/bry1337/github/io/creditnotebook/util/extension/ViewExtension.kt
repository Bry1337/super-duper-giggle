package bry1337.github.io.creditnotebook.util.extension

import android.content.ContextWrapper
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by edwardbryan.abergas on 07/18/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
fun View.getParentActivity(): AppCompatActivity? {
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}