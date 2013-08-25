package com.mancondome.local.cloud.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * {@link Controller}クラスの基底実装。
 * @author toyoshima_shingo
 */
public abstract class AbstractController {
	/** ログ出力コンポーネント  */
	@Autowired
	protected Logger logger;

	/**
	 * URIパスのプレフィックスを取得する。
	 * @return URIパスのプレフィックス
	 */
	protected abstract String uriPrefix();

	/**
	 * リクエストURIからファイルパスを抽出する。
	 * @param request {@link HttpServletRequest}
	 * @return ファイルパス
	 */
	protected String toFilePath(HttpServletRequest request) {
		final String rawPath = StringUtils.removeStart(request.getRequestURI(), uriPrefix());
		try {
			return URLDecoder.decode(rawPath, CharEncoding.UTF_8);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}
}
