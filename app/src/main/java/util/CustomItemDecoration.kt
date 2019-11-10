package util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CustomGridItemDecoration(
    private val spanCount: Int,
    private val itemSpace: Int,
    private val includeEdge: Boolean = true
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        if (includeEdge) offsetsWithEdge(outRect, position, column)
        else offsetsWithoutEdge(outRect, position, column)
    }

    private fun offsetsWithEdge(outRect: Rect, position: Int, column: Int) {
        outRect.left = itemSpace - column * itemSpace / spanCount
        outRect.right = (column + 1) * itemSpace / spanCount

        if (position < spanCount) {
            outRect.top = itemSpace
        }
        outRect.bottom = itemSpace
    }

    private fun offsetsWithoutEdge(outRect: Rect, position: Int, column: Int) {
        outRect.left = column * itemSpace / spanCount
        outRect.right = itemSpace - (column + 1) * itemSpace / spanCount
        if (position >= spanCount) {
            outRect.top = itemSpace
        }
    }
}