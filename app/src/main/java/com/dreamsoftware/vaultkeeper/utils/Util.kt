package com.dreamsoftware.vaultkeeper.utils

import java.util.UUID
import kotlin.random.Random

fun getRandomNumber(): Int = Random.nextInt(6, 21)

fun generateUUID(): String = UUID.randomUUID().toString()

