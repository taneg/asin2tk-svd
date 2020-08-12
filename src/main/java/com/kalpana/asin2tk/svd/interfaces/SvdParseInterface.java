package com.kalpana.asin2tk.svd.interfaces;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version 1.0
 * @date 2020/8/11 16:55
 **/
public interface SvdParseInterface {

    String getType();

    String parseUrl(String shareUrl) throws Exception;

}
