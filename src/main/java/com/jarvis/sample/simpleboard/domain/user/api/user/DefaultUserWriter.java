package com.jarvis.sample.simpleboard.domain.user.api.user;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.user.specs.User;

 @JarvisMeta(
     fileType = FileType.DOMAIN_API_IMPL,
     references = { User.class,  UserWriter.class }
 )
public class DefaultUserWriter implements UserWriter{
}
