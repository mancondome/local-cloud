package com.mancondome.local.cloud.type;

import java.io.File;

import org.codehaus.plexus.util.FileUtils;

/**
 * ファイルコンテンツの種類を表す。
 * @author toyoshima_shingo
 */
public enum ContentType {
	/** ディレクトリ */
	DIRECTORY,
	/** 音声 */
	AUDIO("aac", /*"flac", "flv",*/"mp3", /*"mp4",*/"ogg", "wav");

	/** 対応する拡張子 */
	private final String[] extensions;

	private ContentType(String... extensions) {
		this.extensions = extensions;
	}

	/**
	 * ファイルに対応する{@link ContentType}を取得する。
	 * @param file ファイル
	 * @return {@link ContentType}
	 */
	public static ContentType get(File file) {
		if (file.isDirectory()) {
			return DIRECTORY;
		}

		final String fileExtension = FileUtils.extension(file.getName()).toLowerCase();
		for (ContentType type : values()) {
			for (String extension : type.extensions) {
				if (extension.equals(fileExtension)) {
					return type;
				}
			}
		}
		return null;
	}
}
