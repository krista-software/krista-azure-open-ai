/*
 * Azure Open Ai Extension for Krista
 * Copyright (C) 2025 Krista Software
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package app.krista.extensions.util;

import java.time.Duration;
import dev.failsafe.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RateLimitSupport {
    private static final Logger log = LoggerFactory.getLogger(RateLimitSupport.class);
    private final Duration maxWaitTime;
    private final RateLimiter<Object> callLimiter;
    private final RateLimiter<Object> payloadLimiter;
    private final Object lock = new Object();
    private static final Duration MinusOneNano = Duration.ofNanos(-1);


    public RateLimitSupport(int callsPerDuration, Duration callsDuration, int payloadPerDuration, Duration payloadDuration, Duration maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
        callLimiter = RateLimiter.smoothBuilder(callsPerDuration, callsDuration).build();
        payloadLimiter = RateLimiter.burstyBuilder(payloadPerDuration, payloadDuration).build();
    }


    public void waitUntilReady(int payload) {

        Duration callWait, payloadWait = null;
        synchronized (lock) {

            callWait = callLimiter.tryReservePermit(maxWaitTime);
            if (!callWait.equals(MinusOneNano))
                payloadWait = payloadLimiter.tryReservePermits(payload, maxWaitTime);
        }

        long actualWait;

        if (payloadWait == null || payloadWait.equals(MinusOneNano)) {
            log.info("Rate limiter tells us wait longer than we are allowed. Will wait: " + maxWaitTime.toMillis() + " millis.");
            actualWait = maxWaitTime.toMillis();
        }
        else {
            actualWait = Math.max(callWait.toMillis(), payloadWait.toMillis());
        }
        if (actualWait > 0) {
            try {
                Thread.sleep(actualWait);
            }
            catch (InterruptedException ignored){}
        }
    }
}
