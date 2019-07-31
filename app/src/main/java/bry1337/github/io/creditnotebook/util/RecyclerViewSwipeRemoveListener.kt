package bry1337.github.io.creditnotebook.util

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import bry1337.github.io.creditnotebook.R

/**
 * Created by edwardbryan.abergas on 07/30/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class RecyclerViewSwipeRemoveListener(private val context: Context, private val listener: OnRemoveItemListener,
    dragDirs: Int, swipeDirs: Int) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
  private val p = Paint()

  override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
      target: RecyclerView.ViewHolder): Boolean {
    return false
  }

  override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    val position = viewHolder.adapterPosition
    if (direction == ItemTouchHelper.LEFT) {
      listener.onRemoveItem(position)
    }
  }

  override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float,
      dY: Float,
      actionState: Int, isCurrentlyActive: Boolean) {

    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

      val itemView = viewHolder.itemView
      val height = itemView.bottom.toFloat() - itemView.top.toFloat()
      val width = height / 3

      p.color = ContextCompat.getColor(context, R.color.colorRed500)
      val background = RectF(itemView.right.toFloat() + dX, itemView.top.toFloat(), itemView.right.toFloat(),
          itemView.bottom.toFloat())
      c.drawRect(background, p)
      val icon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_delete)
      val iconDest = RectF(itemView.right.toFloat() - 2 * width, itemView.top.toFloat() + width,
          itemView.right.toFloat() - width, itemView.bottom.toFloat() - width)
      c.drawBitmap(icon, null, iconDest, p)
      super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
  }
}
