package com.example.quiz.adapter.in.web.edit;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class MarkdownParser {

    public String parse(String text) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(text);
        HtmlRenderer renderer = HtmlRenderer.builder().build();

        return renderer.render(document);
    }
}
