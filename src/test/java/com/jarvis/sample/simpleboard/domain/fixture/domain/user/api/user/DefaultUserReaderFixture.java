package com.jarvis.sample.simpleboard.domain.fixture.domain.user.api.user;

import com.jarvis.sample.simpleboard.fixture.infra.user.user.IUserEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.domain.user.api.user.UserReader;
import com.jarvis.sample.simpleboard.domain.user.api.user.DefaultUserReader;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { User.class, DefaultUserReader.class, UserReader.class }
)
public class DefaultUserReaderFixture implements UserReader {

    private final UserReader delegate;

    public DefaultUserReaderFixture() {
        this.delegate = new DefaultUserReader(new IUserEntityRepositoryFixture());
    }

    @Override
    public User findById(Long userId) {
        return delegate.findById(userId);
    }
}

// Context
// The DefaultUserReaderFixture class is a fixture that implements the UserReader interface.
// It internally uses the DefaultUserReader class, injecting it with a test fixture of IUserEntityRepositoryFixture.