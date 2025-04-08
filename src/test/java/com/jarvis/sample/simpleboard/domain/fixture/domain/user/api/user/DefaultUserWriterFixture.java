package com.jarvis.sample.simpleboard.domain.fixture.user.api.user;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.domain.user.specs.UserWriter;
import com.jarvis.sample.simpleboard.domain.user.impl.DefaultUserWriter;
import com.jarvis.sample.simpleboard.domain.user.repository.IUserEntityRepositoryFixture;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { User.class, DefaultUserWriter.class, UserWriter.class }
)
public class DefaultUserWriterFixture implements UserWriter {

    private final UserWriter delegate;

    public DefaultUserWriterFixture() {
        this.delegate = new DefaultUserWriter(new IUserEntityRepositoryFixture());
    }

    @Override
    public User createUser(User user) {
        return delegate.createUser(user);
    }

    @Override
    public User updateUser(User user) {
        return delegate.updateUser(user);
    }

    @Override
    public User deleteUserInfo(User user) {
        return delegate.deleteUserInfo(user);
    }
}

// - IUserEntityRepositoryFixture is assumed to be a fixture implementation of IUserEntityRepository.
// - This fixture class provides a way to test the DefaultUserWriter implementation without a real database or Spring context.