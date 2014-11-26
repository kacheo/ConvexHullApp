package com.lonkal.convexhullapp.main;

/**
 * This class holds any static variables related to the application.
 * 
 * @author Kal
 *
 */
public final class CHAppSettings {

	public static final String ABOUT_APP_MESSAGE = "Convex Hull App | v 1.1\nKalon Cheong (c) 2014\n\nServing all your Convex Hull Algorithm simulation needs :)";

	public static final String CH_BRUTE_FORCE_NAME = "Brute Force";
	public static final String CH_GRAHAM_SCAN_NAME = "Graham Scan";
	public static final String CH_JARVIS_MARCH_NAME = "Jarvis March";
	public static final String CH_MONOTONE_CHAIN_NAME = "Monotone Chain Algorithm";
	public static final String CH_RANDOMIZED_INCREMENTAL_NAME = "Randomized Incremental";
	public static final String CH_SWEEPHULL_NAME = "Sweep Hull";

	public static final String[] CH_ALGORITHMS_LIST = { CH_JARVIS_MARCH_NAME,
			CH_MONOTONE_CHAIN_NAME, CH_SWEEPHULL_NAME, CH_GRAHAM_SCAN_NAME };

}
