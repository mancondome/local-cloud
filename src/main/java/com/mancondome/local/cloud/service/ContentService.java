package com.mancondome.local.cloud.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.mancondome.local.cloud.dto.FileContent;
import com.mancondome.local.cloud.dto.FileInformation;
import com.mancondome.local.cloud.type.ContentType;
import com.mancondome.local.cloud.util.FileValidator;

/**
 * ファイル内容を取得する{@link Service}。
 * @author toyoshima_shingo
 */
@Service
public class ContentService {
	/** コンテンツディレクトリのルートパス */
	@Value("${index.path}")
	private String rootPath;

	/**
	 * ファイルの種類を取得する。
	 * @param path ファイルまたはディレクトリのパス
	 * @return {@link ContentType}
	 */
	public ContentType getContentType(String path) {
		Assert.notNull(path);

		final File file = new File(this.rootPath + path);
		FileValidator.exists(file);

		return ContentType.get(file);
	}

	/**
	 * ファイルパスに相当するファイルの情報を取得する。
	 * @param path ファイルパス
	 * @return {@link FileInformation}
	 */
	public FileInformation getFileInformation(String path) {
		Assert.notNull(path);

		final File file = new File(this.rootPath + path);
		final ContentType contentType = this.getContentType(path);

		return new FileInformation(file.getName(), contentType);
	}

	/**
	 * ディレクトリ内の全ファイル情報を取得する。
	 * @param path ディレクトリパス
	 * @return {@link FileInformation}
	 */
	public List<FileInformation> getDirectory(String path) {
		Assert.notNull(path);

		final File directory = new File(this.rootPath + path);
		FileValidator.directoryExists(directory);

		final List<FileInformation> files = new ArrayList<>();
		for (final File file : directory.listFiles()) {
			final ContentType contentType = ContentType.get(file);
			if (contentType != null) {
				files.add(new FileInformation(file.getName(), contentType));
			}
		}
		return files;
	}

	/**
	 * ファイルの内容を取得する。
	 * @param path ファイルパス
	 * @return {@link FileContent}
	 */
	public FileContent getFile(String path) {
		Assert.notNull(path);

		final File file = new File(this.rootPath + path);
		FileValidator.fileExists(file);
		if (file.length() > Integer.MAX_VALUE) {
			throw new IllegalStateException("Cannot open the file. : " + path);
		}

		return new FileContent(file);
	}
}
