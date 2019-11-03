package net.luispiressilva.iban.utils.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SectionedVerticalSpaceItemDecoration(recycler: RecyclerView,
                                           private val horizontalSpacing: Float,
                                           private val dismissViewTypes: ArrayList<Int> = arrayListOf(),
                                           private val hotFix_firstRowTopPadding: Float = 0F) : RecyclerView.ItemDecoration() {


    init {
        recycler.setPadding(recycler.paddingLeft,
                recycler.paddingTop - (horizontalSpacing.toInt() / 2),
                recycler.paddingRight,
                recycler.paddingBottom - (horizontalSpacing.toInt() / 2))
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val adapter = parent.adapter!!
        val itemPosition = parent.getChildAdapterPosition(view)

        // If position not found
        if (itemPosition == RecyclerView.NO_POSITION) {
            return
        }

        val itemType = adapter.getItemViewType(itemPosition)


        if (!dismissViewTypes.contains(itemType)) {
            outRect.top = horizontalSpacing.toInt() / 2
            outRect.bottom = horizontalSpacing.toInt() / 2
        } else {

            val itemCount = parent.layoutManager?.itemCount ?: 0

            //if is first row put padding top
            if (itemPosition == 0) {
                outRect.top = (horizontalSpacing.toInt() / 2) + hotFix_firstRowTopPadding.toInt()
            }


            //if is last row put padding bottom
            val lastIndex = itemCount - 1
            if (itemPosition == lastIndex) {
                outRect.bottom = horizontalSpacing.toInt() / 2
            }


        }

    }


}
