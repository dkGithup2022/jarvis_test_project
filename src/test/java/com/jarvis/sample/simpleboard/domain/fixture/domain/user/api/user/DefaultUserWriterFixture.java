package com.jarvis.sample.simpleboard.domain.fixture.domain.user.api.user;

import com.jarvis.sample.simpleboard.domain.user.api.user.DefaultUserWriter;
import com.jarvis.sample.simpleboard.domain.user.api.user.PasswordEncoder;
import com.jarvis.sample.simpleboard.domain.user.api.user.UserWriter;
import com.jarvis.sample.simpleboard.fixture.infra.user.user.IUserEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.user.specs.User;

@JarvisMeta(
        fileType = FileType.DOMAIN_API_FIXTURE,
        references = {User.class, DefaultUserWriter.class, UserWriter.class}
)
public class DefaultUserWriterFixture implements UserWriter {

    private final UserWriter delegate;

    public DefaultUserWriterFixture() {

        IUserEntityRepository repo = new IUserEntityRepositoryFixture();
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        this.delegate = new DefaultUserWriter(repo, passwordEncoder);
    }

    @Override
    public User createUser(String nickname, String password) {
        return delegate.createUser(nickname, password);
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