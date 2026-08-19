package app.template.patches.rhythm

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.extensions.InstructionExtensions.replaceInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.template.patches.shared.Constants.COMPATIBILITY_RHYTHM

val unlockProPatch = bytecodePatch(
    name = "Unlock Pro",
    description = "Unlocks all Pro features, taals, variations, scales, Manjeera, Tanpuras, removes purchase banners and ads.",
    default = true
) {
    compatibleWith(COMPATIBILITY_RHYTHM)

    execute {
        // 1. Force Taal.isPremium() to return false (so taals are not restricted as premium)
        Fingerprint(
            definingClass = "Lcom/psslabs/rhythm/model/Taal;",
            name = "isPremium",
            returnType = "Z"
        ).method.replaceInstructions(
            0,
            """
                const/4 v0, 0x0
                return v0
            """
        )

        // 2. Force TaalVariation.isPremium() to return false
        Fingerprint(
            definingClass = "Lcom/psslabs/rhythm/model/TaalVariation;",
            name = "isPremium",
            returnType = "Z"
        ).method.replaceInstructions(
            0,
            """
                const/4 v0, 0x0
                return v0
            """
        )

        // 3. Force m4.f constructor to set isPro = true (b = false) at start of method
        Fingerprint(
            definingClass = "Lm4/f;",
            name = "<init>",
            returnType = "V"
        ).method.addInstructions(
            0,
            """
                const/4 p2, 0x0
            """
        )

        // 4. Force PickerView.setIsEnabled(Z) to always set s = true (Unlocks Scale Picker, Tanpura Voice Picker)
        Fingerprint(
            definingClass = "Lcom/psslabs/rhythm/helper/PickerView;",
            name = "setIsEnabled",
            returnType = "V"
        ).method.addInstructions(
            0,
            """
                const/4 p1, 0x1
            """
        )

        // 5. Unlock PickerView restrictions
        Fingerprint(
            definingClass = "Lcom/psslabs/rhythm/helper/PickerView;",
            name = "h",
            returnType = "Z"
        ).method.replaceInstructions(
            0,
            """
                const/4 v0, 0x0
                return v0
            """
        )

        // 6. Force Ln4/e.n() to return true (Unlocks Manjeera and all instruments)
        Fingerprint(
            definingClass = "Ln4/e;",
            name = "n",
            returnType = "Z"
        ).method.replaceInstructions(
            0,
            """
                const/4 v0, 0x1
                return v0
            """
        )

        // 7. Silence "Please purchase the premium version" purchase prompts (Li4/b.T1())
        Fingerprint(
            definingClass = "Li4/b;",
            name = "T1",
            returnType = "V"
        ).method.addInstructions(
            0,
            """
                return-void
            """
        )

        // 8. Disable Get Premium button click (ListingActivity$b.onClick())
        Fingerprint(
            definingClass = "Lcom/psslabs/rhythm/ListingActivity${'$'}b;",
            name = "onClick",
            returnType = "V"
        ).method.addInstructions(
            0,
            """
                return-void
            """
        )

        // 9. Bypass Store Check (m4.f.n())
        Fingerprint(
            definingClass = "Lm4/f;",
            name = "n",
            returnType = "Z"
        ).method.replaceInstructions(
            0,
            """
                const/4 v0, 0x1
                return v0
            """
        )

        // 10. Disable Banner Ads (k4.d.f())
        Fingerprint(
            definingClass = "Lk4/d;",
            name = "f",
            returnType = "V"
        ).method.addInstructions(
            0,
            """
                return-void
            """
        )

        // 11. Disable Interstitial Ads (k4.d.g())
        Fingerprint(
            definingClass = "Lk4/d;",
            name = "g",
            returnType = "V"
        ).method.addInstructions(
            0,
            """
                return-void
            """
        )

        // 12. Disable Ad Thread Launch (k4.d.k())
        Fingerprint(
            definingClass = "Lk4/d;",
            name = "k",
            returnType = "V"
        ).method.addInstructions(
            0,
            """
                return-void
            """
        )

        // 13. Disable Interstitial Load (k4.d.m())
        Fingerprint(
            definingClass = "Lk4/d;",
            name = "m",
            returnType = "V"
        ).method.addInstructions(
            0,
            """
                return-void
            """
        )

        // 14. Disable Ad Activity Load (k4.d.p())
        Fingerprint(
            definingClass = "Lk4/d;",
            name = "p",
            returnType = "V"
        ).method.addInstructions(
            0,
            """
                return-void
            """
        )

        // 15. Disable Banner Ad Load (k4.d.q())
        Fingerprint(
            definingClass = "Lk4/d;",
            name = "q",
            returnType = "V"
        ).method.addInstructions(
            0,
            """
                return-void
            """
        )
    }
}
