package ru.zfix27r.todo.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import ru.zfix27r.todo.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}