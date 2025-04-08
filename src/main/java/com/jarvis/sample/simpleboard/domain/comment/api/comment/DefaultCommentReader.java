package com.jarvis.sample.simpleboard.domain.comment.api.comment;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.comment.specs.Comment;

 @JarvisMeta(
     fileType = FileType.DOMAIN_API_IMPL,
     references = { Comment.class,  CommentReader.class }
 )
public class DefaultCommentReader implements CommentReader{
}
