package com.mancondome.local.cloud.dto;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * ファイル内容を表す。
 * @author toyoshima_shingo
 */
public class FileContent {
	/** ファイル */
	private final File file;

	/**
	 * ファイルオブジェクトで初期化する。
	 * @param file {@link File}
	 */
	public FileContent(File file) {
		this.file = file;
	}

	/**
	 * ファイル内容を書き出す。
	 * {@code out}は実行後に必ず閉じること。
	 * @param out {@link OutputStream}
	 */
	public void writeContent(OutputStream out) {
		final ByteBuffer buffer = ByteBuffer.allocate(1024);//TODO バッファサイズ定数化

		try (final FileChannel channel = FileChannel.open(file.toPath())) {
			while (channel.read(buffer) > 0) {
				out.write(buffer.array());
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}
