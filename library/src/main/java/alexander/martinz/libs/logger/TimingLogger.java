/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package alexander.martinz.libs.logger;

import android.os.SystemClock;

import java.util.ArrayList;

/**
 * A utility class to help log timings splits throughout a method call.
 * Typical usage is:
 * <p/>
 * <pre>
 *     TimingLogger timings = new TimingLogger(TAG, "methodA");
 *     // ... do some work A ...
 *     timings.addSplit("work A");
 *     // ... do some work B ...
 *     timings.addSplit("work B");
 *     // ... do some work C ...
 *     timings.addSplit("work C");
 *     timings.dumpToLog();
 * </pre>
 * <p/>
 * <p>The dumpToLog call would add the following to the log:</p>
 * <p/>
 * <pre>
 *     D/TAG     ( 3459): methodA: begin
 *     D/TAG     ( 3459): methodA:    9 ms, work A
 *     D/TAG     ( 3459): methodA:    1 ms, work B
 *     D/TAG     ( 3459): methodA:    6 ms, work C
 *     D/TAG     ( 3459): methodA: end, 16 ms
 * </pre>
 */
public class TimingLogger {

    /**
     * The Log tag to use for logging the timings.
     */
    private String mTag;

    /**
     * A label to be included in every log.
     */
    private String mLabel;

    /**
     * Stores the time of each split.
     */
    ArrayList<Long> mSplits;

    /**
     * Stores the labels for each split.
     */
    ArrayList<String> mSplitLabels;

    /**
     * Create and initialize a TimingLogger object that will log using
     * the specific tag.
     *
     * @param tag   the log tag to use while logging the timings
     * @param label a string to be displayed with each log
     */
    public TimingLogger(String tag, String label) {
        reset(tag, label);
    }

    /**
     * Clear and initialize a TimingLogger object that will log using
     * the specific tag.
     *
     * @param tag   the log tag to use while logging the timings
     * @param label a string to be displayed with each log
     */
    public void reset(String tag, String label) {
        mTag = tag;
        mLabel = label;
        reset();
    }

    /**
     * Clear and initialize a TimingLogger object that will log using
     * the tag and label that was specified previously, either via
     * the constructor or a call to reset(tag, label).
     */
    public void reset() {
        if (mSplits == null) {
            mSplits = new ArrayList<>();
            mSplitLabels = new ArrayList<>();
        } else {
            mSplits.clear();
            mSplitLabels.clear();
        }
        addSplit(null);
    }

    /**
     * Add a split for the current time, labeled with splitLabel.
     *
     * @param splitLabel a label to associate with this split.
     */
    public void addSplit(String splitLabel) {
        long now = SystemClock.elapsedRealtime();
        mSplits.add(now);
        mSplitLabels.add(splitLabel);
    }

    /**
     * Dumps the timings to the log using {@link Logger#d(Object, String)}
     */
    public void dumpToLog() {
        Logger.d(mTag, mLabel + ": begin");
        final long first = mSplits.get(0);
        long now = first;
        for (int i = 1; i < mSplits.size(); i++) {
            now = mSplits.get(i);
            final String splitLabel = mSplitLabels.get(i);
            final long prev = mSplits.get(i - 1);

            Logger.d(mTag, mLabel + ":    " + (now - prev) + " ms, " + splitLabel);
        }
        Logger.d(mTag, mLabel + ": end, " + (now - first) + " ms");
    }

    /**
     * Dumps the timings to a String.
     */
    public String dumpToString() {
        final StringBuilder sb = new StringBuilder();
        final long first = mSplits.get(0);
        long now = first;
        sb.append(mLabel).append(": begin").append('\n');
        for (int i = 1; i < mSplits.size(); i++) {
            now = mSplits.get(i);
            final String splitLabel = mSplitLabels.get(i);
            final long prev = mSplits.get(i - 1);

            sb.append(mLabel)
                    .append(":    ")
                    .append(now - prev)
                    .append(" ms, ")
                    .append(splitLabel)
                    .append('\n');
        }
        sb.append(mLabel)
                .append(": end, ")
                .append(String.valueOf(now - first))
                .append(" ms")
                .append('\n');

        return sb.toString();
    }
}
