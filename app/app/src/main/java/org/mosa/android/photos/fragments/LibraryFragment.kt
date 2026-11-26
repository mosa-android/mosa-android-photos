package org.mosa.android.photos.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.mosa.android.photos.R
import org.mosa.android.photos.SettingsActivity
import org.mosa.android.photos.ext.getViewProperty
import org.mosa.android.photos.models.AlbumType
import org.mosa.android.photos.models.MediaType
import org.mosa.android.photos.ui.views.ListItem

/**
 * A fragment showing a search bar with categories.
 */
class LibraryFragment : Fragment(R.layout.fragment_library) {
    // Views
    private val favoritesAlbumListItem by getViewProperty<ListItem>(R.id.favoritesAlbumListItem)
    private val photosAlbumListItem by getViewProperty<ListItem>(R.id.photosAlbumListItem)
    private val libraryNestedScrollView by getViewProperty<NestedScrollView>(R.id.libraryNestedScrollView)
    private val trashAlbumListItem by getViewProperty<ListItem>(R.id.trashAlbumListItem)
    private val settingsListItem by getViewProperty<ListItem>(R.id.settingsListItem)
    private val videosAlbumListItem by getViewProperty<ListItem>(R.id.videosAlbumListItem)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(view) { _, windowInsets ->
            val insets = windowInsets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )

            libraryNestedScrollView.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                leftMargin = insets.left
                rightMargin = insets.right
            }

            windowInsets
        }

        photosAlbumListItem.setOnClickListener {
            openAlbum(AlbumType.REELS, MediaType.IMAGE)
        }

        videosAlbumListItem.setOnClickListener {
            openAlbum(AlbumType.REELS, MediaType.VIDEO)
        }

        favoritesAlbumListItem.setOnClickListener {
            openAlbum(AlbumType.FAVORITES)
        }

        trashAlbumListItem.setOnClickListener {
            authenticateToOpenTrash {
                openAlbum(AlbumType.TRASH)
            }
        }

        settingsListItem.setOnClickListener {
            val intent = Intent(context, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun authenticateToOpenTrash(onSuccess: () -> Unit) {
        val context = requireContext()
        val executor = ContextCompat.getMainExecutor(context)

        val authenticators = BiometricManager.Authenticators.BIOMETRIC_STRONG or
                BiometricManager.Authenticators.DEVICE_CREDENTIAL

        val biometricManager = BiometricManager.from(context)
        if (biometricManager.canAuthenticate(authenticators) != BiometricManager.BIOMETRIC_SUCCESS) {
            onSuccess()
            return
        }

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.album_trash))
            .setAllowedAuthenticators(authenticators)
            .build()

        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onSuccess()
                }
            })

        biometricPrompt.authenticate(promptInfo)
    }

    private fun openAlbum(
        albumType: AlbumType,
        fileType: MediaType? = null,
    ) {
        findNavController().navigate(
            R.id.action_mainFragment_to_fragment_album,
            AlbumFragment.createBundle(
                albumType = albumType,
                fileType = fileType,
            )
        )
    }
}
