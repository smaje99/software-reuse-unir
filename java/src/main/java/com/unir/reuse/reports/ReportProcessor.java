package com.unir.reuse.reports;

/**
 * Common contract for every concrete report processor created by the category
 * factories. Returning the message instead of printing directly keeps the
 * implementation testable and lets the caller decide how to expose results.
 */
public interface ReportProcessor {
    String process();
}

