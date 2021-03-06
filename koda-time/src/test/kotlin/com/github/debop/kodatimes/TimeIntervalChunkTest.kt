/*
 * Copyright (c) 2016. Sunghyouk Bae <sunghyouk.bae@gmail.com>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.debop.kodatimes

import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeLessOrEqualTo
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class TimeIntervalChunkTest : AbstractKodaTimesTest() {

    @Test
    fun `chunk years`() = runBlocking<Unit> {
        val start = now().startOfYear()
        val endExclusive = start + 5.years()
        logger.debug { "start=$start, end=$endExclusive" }
        val interval = start..endExclusive

        val chunks = interval.chunkYear(4).toList()
        chunks.forEach(::println)

        chunks.size shouldEqualTo 2

        chunks.forEach { chunk ->
            chunk.size shouldBeLessOrEqualTo 4
            assertTrue { chunk.first() in interval }
            assertTrue { chunk.last() in interval }
        }

        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { interval.chunkYear(0) }
        }

        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { interval.chunkYear(-1) }
        }
    }

    @Test
    fun `chunk year and aggregate`() = runBlocking<Unit> {
        val start = now().startOfYear()
        val endExclusive = start + 5.years()
        logger.debug("start=$start, end=$endExclusive")
        val interval = start..endExclusive

        val intervals = interval.chunkYear(3).map { years -> years.first()..years.last() }.toList()

        intervals.size shouldEqualTo 2
        intervals.all { it in interval }.shouldBeTrue()
    }

    @Test
    fun `chunk months`() = runBlocking<Unit> {
        val start = now().startOfMonth()
        val endExclusive = start + 13.months()
        logger.debug("start=$start, end=$endExclusive")
        val interval = start..endExclusive

        val chunks = interval.chunkMonth(5).toList()

        chunks.forEach(::println)
        chunks.size shouldEqualTo 3

        chunks.forEach {
            it.size shouldBeLessOrEqualTo 5
            assertTrue { it.first() in interval }
            assertTrue { it.last() in interval }
        }

        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { interval.chunkMonth(0) }
        }

        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { interval.chunkMonth(-1) }
        }
    }

    @Test
    fun `chunk month and aggregate`() = runBlocking {
        val start = now().startOfMonth()
        val endExclusive = start + 13.months()
        logger.debug("start=$start, end=$endExclusive")
        val interval = start..endExclusive

        val intervals = interval.chunkMonth(5).map { months -> months.first()..months.last() }.toList()

        intervals.size shouldEqualTo 3
        intervals.all { it in interval }.shouldBeTrue()
    }

    @Test
    fun `chunk week`() = runBlocking<Unit> {
        val start = now().startOfWeek()
        val endExclusive = start + 5.weeks()
        logger.debug("start=$start, end=$endExclusive")
        val interval = start..endExclusive

        val chunks = interval.chunkWeek(2).toList()

        chunks.forEach(::println)
        chunks.size shouldEqualTo 3

        chunks.forEach {
            it.size shouldBeLessOrEqualTo 2
            assertTrue { it.first() in interval }
            assertTrue { it.last() in interval }
        }

        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { interval.chunkWeek(0) }
        }

        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { interval.chunkWeek(-1) }
        }

    }

    @Test
    fun `chunk week and aggregate`() = runBlocking<Unit> {
        val start = now().startOfWeek()
        val endExclusive = start + 5.weeks()
        logger.debug("start=$start, end=$endExclusive")
        val interval = start..endExclusive

        val intervals = interval.chunkWeek(2).map { it.first()..it.last() }.toList()

        intervals.size shouldEqual 3
        intervals.all { it in interval }.shouldBeTrue()
    }

    @Test
    fun `chunk days`() = runBlocking<Unit> {
        val start = now().startOfDay()
        val endExclusive = start + 66.days()
        logger.debug("start=$start, end=$endExclusive")
        val interval = start..endExclusive

        val chunks = interval.chunkDay(30).toList()

        chunks.forEach(::println)
        assertEquals(3, chunks.size)

        chunks.forEach {
            logger.debug("chunk=$it")
            assertTrue { it.size <= 30 }
            assertTrue { it.first() in interval }
            assertTrue { it.last() in interval }
        }

        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { interval.chunkDay(0) }
        }

        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { interval.chunkDay(-1) }
        }
    }

    @Test
    fun `chunk day and aggregate`() = runBlocking<Unit> {
        val start = now().startOfDay()
        val endExclusive = start + 66.days()
        logger.debug("start=$start, end=$endExclusive")
        val interval = start..endExclusive

        val intervals = interval.chunkDay(30).map { days -> days.first()..days.last() }.toList()

        intervals.forEach(::println)
        intervals.size shouldEqualTo 3
        intervals.all { it in interval }.shouldBeTrue()
    }

    @Test
    fun `chunk hours`() = runBlocking<Unit> {
        val start = now().trimToHour()
        val endExclusive = start + 66.hours()
        logger.debug("start=$start, end=$endExclusive")
        val interval = start..endExclusive

        val chunks = interval.chunkHour(20).toList()

        chunks.forEach(::println)
        chunks.size shouldEqualTo 4

        chunks.forEach {
            it.size shouldBeLessOrEqualTo 20
            assertTrue { it.first() in interval }
            assertTrue { it.last() in interval }
        }

        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { interval.chunkHour(0) }
        }

        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { interval.chunkHour(-1) }
        }
    }

    @Test
    fun `chunk hour and aggregate`() = runBlocking {
        val start = now().trimToHour()
        val endExclusive = start + 66.hours()
        logger.debug("start=$start, end=$endExclusive")
        val interval = start..endExclusive

        val intervals = interval.chunkHour(20).map { it.first()..it.last() }.toList()

        intervals.forEach(::println)
        intervals.size shouldEqualTo 4
        intervals.all { it in interval }.shouldBeTrue()
    }

    @Test
    fun `chunk minute`() = runBlocking<Unit> {
        val start = now().trimToMinute()
        val endExclusive = start + 33.minutes()
        logger.debug("start=$start, end=$endExclusive")
        val interval = start..endExclusive

        val chunks = interval.chunkMinute(10).toList()

        chunks.forEach(::println)
        chunks.size shouldEqualTo 4

        chunks.forEach {
            assertTrue { it.size <= 10 }
            assertTrue { it.first() in interval }
            assertTrue { it.last() in interval }
        }

        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { interval.chunkMinute(0) }
        }

        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { interval.chunkMinute(-1) }
        }
    }

    @Test
    fun `chunk minute and aggregate`() = runBlocking {
        val start = now().trimToMinute()
        val endExclusive = start + 33.minutes()
        logger.debug("start=$start, end=$endExclusive")
        val interval = start..endExclusive

        val intervals = interval.chunkMinute(10).map { it.first()..it.last() }.toList()

        intervals.forEach(::println)
        intervals.size shouldEqualTo 4
        intervals.all { it in interval }.shouldBeTrue()
    }

    @Test
    fun `chunk second`() = runBlocking<Unit> {
        val start = now().trimToSecond()
        val endExclusive = start + 33.seconds()
        logger.debug("start=$start, end=$endExclusive")
        val interval = start..endExclusive

        val chunks = interval.chunkSecond(10).toList()

        chunks.size shouldEqualTo 4

        chunks.forEach {
            it.size shouldBeLessOrEqualTo 10
            assertTrue { it.first() in interval }
            assertTrue { it.last() in interval }
        }

        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { interval.chunkSecond(0) }
        }

        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { interval.chunkSecond(-1) }
        }
    }

    @Test
    fun `chunk second and aggregate`() = runBlocking {
        val start = now().trimToSecond()
        val endExclusive = start + 33.seconds()
        logger.debug("start=$start, end=$endExclusive")
        val interval = start..endExclusive

        val intervals = interval.chunkSecond(10).map { it.first()..it.last() }.toList()

        intervals.forEach(::println)
        intervals.size shouldEqualTo 4
        intervals.all { it in interval }.shouldBeTrue()
    }
}