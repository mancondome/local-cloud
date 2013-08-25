package com.mancondome.local.cloud.util;

import java.io.File;

/**
 * ファイルの種類や有効性を確認する。
 * @author toyoshima_shingo
 */
public class FileValidator {
	/** ファイルまたはディレクトリが存在しないときのエラーメッセージ */
	private static final String ABSENCE_MESSAGE = "The file or the directory does not exists. : ";
	/** ファイルが存在しないときのエラーメッセージ */
	private static final String FILE_ABSENCE_MESSAGE = "The file does not exists. : ";
	/** ディレクトリが存在しないときのエラーメッセージ */
	private static final String DIRECTORY_ABSENCE_MESSAGE = "The directory does not exists. : ";

	/** 使用しない。 */
	@Deprecated
	private FileValidator() {
	}

	/**
	 * ファイルまたはディレクトリが存在することを確認する。
	 * @param file {@link File}
	 * @throws IllegalStateException {@link File}が存在しないとき
	 */
	public static void exists(File file) {
		if (!(file.exists())) {
			throw new IllegalStateException(ABSENCE_MESSAGE + file.getPath());
		}
	}

	/**
	 * ファイルが存在することを確認する。
	 * @param file {@link File}
	 * @throws IllegalStateException {@link File}が存在しない、またはディレクトリのとき
	 */
	public static void fileExists(File file) {
		if (!(file.exists()) || file.isDirectory()) {
			throw new IllegalStateException(FILE_ABSENCE_MESSAGE + file.getPath());
		}
	}

	/**
	 * ディレクトリが存在することを確認する。
	 * @param file {@link File}
	 * @throws IllegalStateException {@link File}が存在しない、またはファイルのとき
	 */
	public static void directoryExists(File file) {
		if (!(file.exists()) || file.isFile()) {
			throw new IllegalStateException(DIRECTORY_ABSENCE_MESSAGE + file.getPath());
		}
	}
}
