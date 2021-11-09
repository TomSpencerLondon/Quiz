package com.example.quiz.adapter.in.questionloader;

import com.example.quiz.domain.Question;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileQuestionParser {

  private QuestionParser questionLoader;

  public FileQuestionParser(QuestionParser questionLoader) {
    this.questionLoader = questionLoader;
  }

  public List<Question> read(String fileName) throws URISyntaxException, IOException {
    Path path = Paths.get(getClass().getClassLoader()
        .getResource(fileName).toURI());

    Stream<String> lines = Files.lines(path);
    String data = lines.collect(Collectors.joining("\n"));
    lines.close();

    return questionLoader.parse(data);
  }
}
