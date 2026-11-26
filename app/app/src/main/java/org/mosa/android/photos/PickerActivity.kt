package org.mosa.android.photos

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Consumer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.mosa.android.photos.viewmodels.IntentsViewModel

class PickerActivity : AppCompatActivity(R.layout.activity_picker) {
    // View models
    private val intentsViewModel by viewModels<IntentsViewModel>()

    // Intents
    private val intentListener = Consumer<Intent> { intentsViewModel.onIntent(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge
        enableEdgeToEdge()

        intentListener.accept(intent)
        addOnNewIntentListener(intentListener)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                loadData()
            }
        }
    }

    override fun onDestroy() {
        removeOnNewIntentListener(intentListener)

        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressedDispatcher.onBackPressed()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private suspend fun loadData() {
        intentsViewModel.parsedIntent.collectLatest {
            it?.handle { parsedIntent ->
                when (parsedIntent) {
                    is IntentsViewModel.ParsedIntent.PickIntent -> {

                    }

                    else -> {
                        Toast.makeText(
                            this, R.string.intent_action_not_supported, Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                }
            }
        }
    }
}
