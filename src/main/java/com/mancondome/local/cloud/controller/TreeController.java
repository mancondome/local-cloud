package com.mancondome.local.cloud.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mancondome.local.cloud.service.ContentService;

/**
 * ディレクトリ階層を表示する{@link Controller}。
 * @author toyoshima_shingo
 */
@Controller
@RequestMapping(value = TreeController.URI_PREFIX, method = RequestMethod.GET)
public class TreeController extends AbstractController {
	/** URIパスのプレフィックス */
	static final String URI_PREFIX = "/tree";

	@Autowired
	private ContentService contentService;

	@Override
	protected String uriPrefix() {
		return TreeController.URI_PREFIX;
	}

	/**
	 * ディレクトリの中身を表示する。
	 * @param request {@link HttpServletRequest}
	 * @param model {@link Model}
	 * @return テンプレート
	 */
	@RequestMapping(value = "/**", method = RequestMethod.GET)
	public String directory(HttpServletRequest request, Model model) {
		final String path = this.toFilePath(request);
		model.addAttribute("path", path);
		model.addAttribute(this.contentService.getDirectory(path));

		return "index";
	}
}
