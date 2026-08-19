package app.template.patches.rhythm

import app.morphe.patcher.Fingerprint
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

        // 3. Force m4.f constructor to set isPro = true (b = false) so all taals & variations load
        Fingerprint(
            definingClass = "Lm4/f;",
            name = "<init>",
            returnType = "V"
        ).method.replaceInstructions(
            0,
            """
                invoke-direct {p0}, Ljava/lang/Object;-><init>()V
                new-instance v0, Ljava/util/ArrayList;
                invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V
                iput-object v0, p0, Lm4/f;->e:Ljava/util/ArrayList;
                new-instance v0, Ljava/util/ArrayList;
                invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V
                iput-object v0, p0, Lm4/f;->f:Ljava/util/ArrayList;
                new-instance v0, Ljava/util/ArrayList;
                invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V
                iput-object v0, p0, Lm4/f;->g:Ljava/util/ArrayList;
                new-instance v0, Ljava/util/ArrayList;
                invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V
                iput-object v0, p0, Lm4/f;->h:Ljava/util/ArrayList;
                iput-object p1, p0, Lm4/f;->a:Landroid/content/Context;
                const/4 v0, 0x0
                iput-boolean v0, p0, Lm4/f;->b:Z
                iput-object p3, p0, Lm4/f;->c:Lk4/z;
                iput-object p4, p0, Lm4/f;->d:Lm4/f$a;
                return-void
            """
        )

        // 4. Force PickerView.setIsEnabled(Z) to always set s = true (Unlocks Scale Picker, Tanpura Voice Picker)
        Fingerprint(
            definingClass = "Lcom/psslabs/rhythm/helper/PickerView;",
            name = "setIsEnabled",
            returnType = "V"
        ).method.replaceInstructions(
            0,
            """
                const/4 p1, 0x1
                iput-boolean p1, p0, Lcom/psslabs/rhythm/helper/PickerView;->s:Z
                invoke-direct {p0}, Lcom/psslabs/rhythm/helper/PickerView;->m()V
                return-void
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
        ).method.replaceInstructions(
            0,
            """
                return-void
            """
        )

        // 8. Disable StoreActivity launcher (StoreActivity.Z1())
        Fingerprint(
            definingClass = "Lcom/psslabs/rhythm/StoreActivity;",
            name = "Z1",
            returnType = "V"
        ).method.replaceInstructions(
            0,
            """
                return-void
            """
        )

        // 9. Disable Get Premium button click (ListingActivity$b.onClick())
        Fingerprint(
            definingClass = "Lcom/psslabs/rhythm/ListingActivity${'$'}b;",
            name = "onClick",
            returnType = "V"
        ).method.replaceInstructions(
            0,
            """
                return-void
            """
        )

        // 10. Bypass Store Check (m4.f.n())
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

        // 11. Disable Banner Ads (k4.d.f())
        Fingerprint(
            definingClass = "Lk4/d;",
            name = "f",
            returnType = "V"
        ).method.replaceInstructions(
            0,
            """
                return-void
            """
        )

        // 12. Disable Interstitial Ads (k4.d.g())
        Fingerprint(
            definingClass = "Lk4/d;",
            name = "g",
            returnType = "V"
        ).method.replaceInstructions(
            0,
            """
                return-void
            """
        )

        // 13. Disable Ad Thread Launch (k4.d.k())
        Fingerprint(
            definingClass = "Lk4/d;",
            name = "k",
            returnType = "V"
        ).method.replaceInstructions(
            0,
            """
                return-void
            """
        )

        // 14. Disable Interstitial Load (k4.d.m())
        Fingerprint(
            definingClass = "Lk4/d;",
            name = "m",
            returnType = "V"
        ).method.replaceInstructions(
            0,
            """
                return-void
            """
        )

        // 15. Disable Ad Activity Load (k4.d.p())
        Fingerprint(
            definingClass = "Lk4/d;",
            name = "p",
            returnType = "V"
        ).method.replaceInstructions(
            0,
            """
                return-void
            """
        )

        // 16. Disable Banner Ad Load (k4.d.q())
        Fingerprint(
            definingClass = "Lk4/d;",
            name = "q",
            returnType = "V"
        ).method.replaceInstructions(
            0,
            """
                return-void
            """
        )
    }
}
