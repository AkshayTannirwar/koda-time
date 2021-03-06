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

package com.github.debop.kodatimes.ranges

import com.github.debop.kodatimes.AbstractKodaTimesTest
import com.github.debop.kodatimes.days
import com.github.debop.kodatimes.today
import mu.KLogging
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class DateTimeRangeTest : AbstractKodaTimesTest() {
    companion object : KLogging()


    @Test
    fun `simple creation`() {

        val start = today()
        val endInclusive = start + 5.days()
        val range = DateTimeRange(start, endInclusive)

        range.start shouldEqual start
        range.endInclusive shouldEqual endInclusive
        range.first shouldEqual start
        range.last shouldEqual endInclusive

        range.toString() shouldEqual "$start..$endInclusive"
    }

    @Test
    fun `empty range`() {

        val start = today()
        val endInclusive = start - 1.days()

        val range = DateTimeRange(start, endInclusive)

        range.isEmpty().shouldBeTrue()
        range shouldEqual DateTimeRange.EMPTY
    }
}