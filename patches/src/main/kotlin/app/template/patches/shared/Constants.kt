package app.template.patches.shared

import app.morphe.patcher.patch.ApkFileType
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

object Constants {
    val COMPATIBILITY_RHYTHM = Compatibility(
        name = "Rhythm Free",
        packageName = "com.psslabs.rhythm",
        apkFileType = ApkFileType.APK,
        appIconColor = 0xFF8C00,
        targets = listOf(
            AppTarget(
                version = null,
                isExperimental = true
            ),
            AppTarget(
                version = "6.23"
            )
        )
    )
}
