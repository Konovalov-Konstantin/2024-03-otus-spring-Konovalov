package ru.otus.hw.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw.models.Comment;

import java.util.List;

@Component
public class CommentsConverter {
    public String commentToString(List<Comment> commentList) {
        StringBuilder sb = new StringBuilder();
        for (Comment comment : commentList) {
            sb.append("Id: %s, Comment: %s".formatted(comment.getId(), comment.getComment()));
        }
        return sb.toString();
    }
}
