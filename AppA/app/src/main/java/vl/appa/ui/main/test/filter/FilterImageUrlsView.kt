package vl.appa.ui.main.test.filter

interface FilterImageUrlsView {

	var selectionStateFromNewest: Boolean
	var selectionStateDisplayDownloaded: Boolean
	var selectionStateDisplayError: Boolean
	var selectionStateDisplayUndefined: Boolean

	fun closeView()
}