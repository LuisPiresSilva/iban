package net.luispiressilva.iban.ui.widgets.view


data class GenericStateCard(
        var showingLoading: Boolean = true,
        var showingEmptyContent: Boolean = false,
        var showingError: Boolean = false

) {

    enum class ClickType {
        LOADING,
        EMPTY_CONTENT,
        ERROR
    }

    companion object {
        fun getLoadingEmptyList(): MutableList<GenericStateCard>{
            return mutableListOf(GenericStateCard(showingLoading = true, showingEmptyContent = false, showingError = false))
        }
        fun getEmptyContentEmptyList(): MutableList<GenericStateCard>{
            return mutableListOf(GenericStateCard(showingLoading = false, showingEmptyContent = true, showingError = false))
        }
        fun getErrorEmptyList(): MutableList<GenericStateCard>{
            return mutableListOf(GenericStateCard(showingLoading = false, showingEmptyContent = false, showingError = true))
        }

        fun areItemsTheSame(oldItem: GenericStateCard, newItem: GenericStateCard): Boolean {
            return oldItem == newItem
        }

        fun areContentsTheSame(oldItem: GenericStateCard, newItem: GenericStateCard): Boolean {
            return oldItem.showingLoading == newItem.showingLoading
                    && oldItem.showingEmptyContent == newItem.showingEmptyContent
                    && oldItem.showingError == newItem.showingError
        }


    }

}


