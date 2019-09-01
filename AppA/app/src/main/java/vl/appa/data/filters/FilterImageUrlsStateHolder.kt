package vl.appa.data.filters

object FilterImageUrlsStateHolder {

	val countDisplayTypes: Int
		get() {
			var counter = 0
			if (displayDownloaded) counter++
			if (displayError) counter++
			if (displayUndefined) counter++
			return counter
		}

	var startFromNewest: Boolean =
		FilterImageUrlsStateHolder.DefaultState.startFromNewest
	var displayDownloaded: Boolean =
		FilterImageUrlsStateHolder.DefaultState.displayDownloaded
	var displayError: Boolean =
		FilterImageUrlsStateHolder.DefaultState.displayError
	var displayUndefined: Boolean =
		FilterImageUrlsStateHolder.DefaultState.displayUndefined

	override fun toString(): String {
		return "startFromNewest: $startFromNewest\n" +
				"displayDownloaded: $displayDownloaded\n" +
				"displayError: $displayError\n" +
				"displayUndefined: $displayUndefined\n"
	}

	object DefaultState {

		val startFromNewest: Boolean = false
		val displayDownloaded: Boolean = false
		val displayError: Boolean = false
		val displayUndefined: Boolean = false

	}
}