package com.mancondome.local.cloud.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mancondome.local.cloud.dto.FileInformation;
import com.mancondome.local.cloud.service.ContentService;
import com.mancondome.local.cloud.type.ContentType;

/**
 * ファイルアクセスのための{@link Controller}。
 * @author toyoshima_shingo
 */
@Controller
@RequestMapping(value = FileController.URI_PREFIX, method = RequestMethod.GET)
public class FileController extends AbstractController {
	/** URIパスのプレフィックス */
	static final String URI_PREFIX = "/file";
	/** ファイルアクセス用のContent-Type */
	private static final String FILE_CONTENT_TYPE = "application/octet-stream";
	/** ファイル内容を表すヘッダー */
	private static final String DISPOSITION_HEADER = "Content-Disposition";
	/** ヘッダー内、ファイル名のためのフォーマット */
	private static final String FILE_NAME_FORMAT = "filename=\"%s\"";
	/** ファイルを利用できないときのエラーメッセージ */
	private static final String FILE_UNAVAILABLE_MESSAGE = "The file is unavailable. : ";

	@Autowired
	private ContentService contentService;

	@Override
	protected String uriPrefix() {
		return FileController.URI_PREFIX;
	}

	/**
	 * 指定したパスのファイルを取得する。
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 */
	@RequestMapping("/**")
	public void getFile(HttpServletRequest request, HttpServletResponse response) {
		final String path = this.toFilePath(request);
		final FileInformation file = this.contentService.getFileInformation(path);
		if (file.getContentType() == null || file.getContentType() == ContentType.DIRECTORY) {
			throw new UnsupportedOperationException(FILE_UNAVAILABLE_MESSAGE + file.getName());
		}

		response.setContentType(FILE_CONTENT_TYPE);
		response.setHeader(DISPOSITION_HEADER, String.format(FILE_NAME_FORMAT, file.getName()));

		try (final OutputStream out = new BufferedOutputStream(response.getOutputStream())) {
			out.write(this.contentService.getFile(path));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	@RequestMapping("/test")
	public void test(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType(FILE_CONTENT_TYPE);
		response.setHeader(DISPOSITION_HEADER, String.format(FILE_NAME_FORMAT, "test.txt"));
		try (OutputStream out = response.getOutputStream()) {
			try (InputStream in = new BufferedInputStream(new FileInputStream(new File(
					"//UBUNTU/share/音楽/KITUNE.mp3")))) {
				final byte[] buffer = new byte[1024];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
