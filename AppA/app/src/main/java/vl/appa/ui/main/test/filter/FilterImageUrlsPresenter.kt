package vl.appa.ui.main.test.filter

import vl.appa.data.filters.FilterImageUrlsStateHolder

class FilterImageUrlsPresenter(private val view: FilterImageUrlsView) {

	init {
		view.selectionStateFromNewest = FilterImageUrlsStateHolder.startFromNewest
		view.selectionStateDisplayDownloaded = FilterImageUrlsStateHolder.displayDownloaded
		view.selectionStateDisplayError = FilterImageUrlsStateHolder.displayError
		view.selectionStateDisplayUndefined = FilterImageUrlsStateHolder.displayUndefined
	}

	fun resetFilter() {
		view.selectionStateFromNewest = FilterImageUrlsStateHolder.DefaultState.startFromNewest
		view.selectionStateDisplayDownloaded = FilterImageUrlsStateHolder.DefaultState.displayDownloaded
		view.selectionStateDisplayError = FilterImageUrlsStateHolder.DefaultState.displayError
		view.selectionStateDisplayUndefined = FilterImageUrlsStateHolder.DefaultState.displayUndefined
	}

	fun apply() {
		with(FilterImageUrlsStateHolder) {
			startFromNewest = view.selectionStateFromNewest
			displayDownloaded = view.selectionStateDisplayDownloaded
			displayError = view.selectionStateDisplayError
			displayUndefined = view.selectionStateDisplayUndefined
		}
		view.closeView()
	}

}