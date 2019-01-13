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

package com.github.debop.javatimes.ranges

import mu.KLogging
import java.sql.Timestamp

/**
 * A range of `java.sql.Timestamp`
 *
 * @autor debop
 * @since 18. 4. 16
 */
class TimestampRange(start: Timestamp, endInclusive: Timestamp) : DateRange(start, endInclusive) {

    companion object : KLogging() {
        @JvmField
        val EMPTY: TimestampRange = TimestampRange(Timestamp(1L), Timestamp(0L))

        @JvmStatic
        fun fromClosedRange(start: Timestamp, end: Timestamp): TimestampRange = when {
            start <= end -> TimestampRange(start, end)
            else         -> TimestampRange(end, start)
        }
    }
}