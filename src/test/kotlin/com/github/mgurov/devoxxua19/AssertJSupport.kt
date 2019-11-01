package com.github.mgurov.devoxxua19

import org.assertj.core.api.SoftAssertions

fun softly(assertions: SoftAssertions.() -> Unit) {
    SoftAssertions.assertSoftly(assertions)
}
