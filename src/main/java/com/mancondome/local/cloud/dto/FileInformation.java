package com.mancondome.local.cloud.dto;

import com.mancondome.local.cloud.type.ContentType;

/**
 * ファイル情報を保持する。
 * @author toyoshima_shingo
 */
public class FileInformation {
	/** ファイル名 */
	private final String name;
	/** ファイルの種類 */
	private final ContentType contentType;

	/**
	 * ファイルについての各情報で初期化する。
	 * @param name ファイル名
	 * @param contentType {@link ContentType}
	 */
	public FileInformation(String name, ContentType contentType) {
		this.name = name;
		this.contentType = contentType;
	}

	/**
	 * ファイル名を取得する。
	 * @return ファイル名
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * ファイルの種類を取得する。
	 * @return ファイルの種類
	 */
	public ContentType getContentType() {
		return this.contentType;
	}
}
