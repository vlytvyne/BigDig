package vl.appa.ui.main.history

import android.database.Cursor

interface HistoryView {

	fun setHistoryListData(cursor: Cursor)
}