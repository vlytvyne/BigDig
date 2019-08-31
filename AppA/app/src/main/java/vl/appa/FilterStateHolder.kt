package vl.appa

object FilterStateHolder {

	val countDisplayTypes: Int
		get() {
			var counter = 0
			if (displayDownloaded) counter++
			if (displayError) counter++
			if (displayUndefined) counter++
			return counter
		}

	var startFromNewest: Boolean = DefaultState.startFromNewest
	var displayDownloaded: Boolean = DefaultState.displayDownloaded
	var displayError: Boolean = DefaultState.displayError
	var displayUndefined: Boolean = DefaultState.displayUndefined

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