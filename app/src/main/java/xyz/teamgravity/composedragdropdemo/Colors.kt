package xyz.teamgravity.composedragdropdemo

import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlin.random.Random

object Colors {
    val VALUE: ImmutableList<Color> = buildList {
        repeat(5) {
            add(Color(Random.nextLong()).copy(alpha = 1F))
        }
    }.toImmutableList()
}