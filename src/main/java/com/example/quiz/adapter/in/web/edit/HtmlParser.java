package com.example.quiz.adapter.in.web.edit;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

public class HtmlParser {

    public static String parse(String html) {
        html.trim();
        String format = String.format("<div>%s</div>", html);
        Element template = new Element(Tag.valueOf("template"), format);
        return template.html();
    }
}
