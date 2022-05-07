package com.example.quiz.adapter.in.web.edit;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MarkdownParserTest {

    @Test
    void givenMarkdownTextParsesToHtml() {
        MarkdownParser markdownParser = new MarkdownParser();
        String text = "# Header";


        String result = markdownParser.parse(text);

        assertThat(result)
                .isEqualTo("<h1>Header</h1>\n");
    }
}