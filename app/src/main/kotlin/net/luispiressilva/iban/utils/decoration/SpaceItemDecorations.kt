package net.luispiressilva.iban.utils.decoration

import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecorations {
    companion object {

        fun sectionedGridSpaceItemDecoration(recycler: RecyclerView,
                                             horizontalSpacing : Float,
                                             verticalSpacing : Float)
                = SectionedGridSpaceItemDecoration(recycler, horizontalSpacing, verticalSpacing)

        fun sectionedGridSpaceItemDecoration(recycler: RecyclerView,
                                             horizontalSpacing : Float,
                                             verticalSpacing : Float,
                                             dismissViewTypes : ArrayList<Int>,
                                             hotFix_firstRowTopPadding : Float = 0F
//                                            , sectionItemCount : (itemType: Int) -> Int,
//                                            sectionFirstItemIndex : (itemType: Int) -> Int,
//                                            sectionLastItemIndex : (itemType: Int) -> Int
        )
                = SectionedGridSpaceItemDecoration(recycler, horizontalSpacing, verticalSpacing, dismissViewTypes, hotFix_firstRowTopPadding
//                , sectionItemCount,
//                sectionFirstItemIndex,
//                sectionLastItemIndex
        )

        fun sectionedVerticalSpaceItemDecoration(recycler: RecyclerView, horizontalSpacing : Float) = SectionedVerticalSpaceItemDecoration(
                recycler,
                horizontalSpacing)

        fun sectionedVerticalSpaceItemDecoration(recycler: RecyclerView, horizontalSpacing : Float, dismissViewTypes : ArrayList<Int>, hotFix_firstRowTopPadding : Float = 0F) =
                SectionedVerticalSpaceItemDecoration(recycler, horizontalSpacing, dismissViewTypes, hotFix_firstRowTopPadding)
    }
}