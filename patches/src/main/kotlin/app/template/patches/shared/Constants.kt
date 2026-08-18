package app.template.patches.shared

import app.morphe.patcher.patch.Compatibility

object Constants {
    val COMPATIBILITY_RHYTHM = setOf(
        Compatibility(
            packageName = "com.psslabs.rhythm",
            supportedVersions = setOf("6.23")
        )
    )

    val COMPATIBILITY_HARMONIUM = setOf(
        Compatibility(
            packageName = "harmonium.music.gameg.real.harmoniumfree",
            supportedVersions = setOf("29.0.1")
        )
    )
}
