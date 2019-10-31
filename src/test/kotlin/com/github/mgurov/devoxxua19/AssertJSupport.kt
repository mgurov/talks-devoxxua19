package com.github.mgurov.devoxxua19

import org.assertj.core.api.SoftAssertions

fun softly(assertionBlock: SoftAssertions.() -> Unit) {
    SoftAssertions.assertSoftly {
        it.assertionBlock()
    }
}