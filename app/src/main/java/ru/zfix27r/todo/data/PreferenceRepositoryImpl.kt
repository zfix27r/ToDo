package ru.zfix27r.todo.data

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.zfix27r.todo.R
import ru.zfix27r.todo.domain.PreferenceRepository
import ru.zfix27r.todo.domain.common.SortType
import javax.inject.Inject

class PreferenceRepositoryImpl @Inject constructor(@ApplicationContext context: Context) :
    PreferenceRepository {
    private val preferenceManager = PreferenceManager.getDefaultSharedPreferences(context)

    private val notesMenuSortKey = context.getString(R.string.notes_menu_sort_key)

    override fun getActiveSortNotes(): SortType {
        val currentSort = preferenceManager.getInt(notesMenuSortKey, NOTES_MENU_SORT_DEFAULT)
        return SortType.values()[currentSort]
    }

    override fun setActiveSortNotes(sortType: SortType) {
        preferenceManager.edit { putInt(notesMenuSortKey, sortType.ordinal) }
    }

    companion object {
        const val NOTES_MENU_SORT_DEFAULT = 0
    }
}