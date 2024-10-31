package com.devolution.EnjoyMD.mapper;

import com.devolution.EnjoyMD.DTO.CommentDto;
import com.devolution.EnjoyMD.models.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDto toCommentDto(Comment comment);
    Comment toComment(CommentDto commentDto);
}
