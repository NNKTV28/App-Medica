package com.example.appmedica.ui.settings

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.example.appmedica.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        findPreference<ListPreference>("theme")?.setOnPreferenceChangeListener { _, newValue ->
            ThemeHelper.applyTheme(newValue as String)
            true
        }
    }
}
