package com.jarvis.sample.simpleboard.domain.fixture.user.api.user;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.domain.user.specs.UserValidator;
import com.jarvis.sample.simpleboard.domain.user.impl.DefaultUserValidator;
import com.jarvis.sample.simpleboard.domain.fixture.user.repository.IUserEntityRepositoryFixture;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { User.class, DefaultUserValidator.class, UserValidator.class }
)
public class DefaultUserValidatorFixture implements UserValidator {

    private final UserValidator delegate;

    public DefaultUserValidatorFixture() {
        this.delegate = new DefaultUserValidator(new IUserEntityRepositoryFixture());
    }

    @Override
    public Boolean canUseNickname(String nickname) {
        return delegate.canUseNickname(nickname);
    }

    @Override
    public Boolean canUpdate(Long requestUserId, User user) {
        return delegate.canUpdate(requestUserId, user);
    }

    @Override
    public Boolean canDelete(Long requestUserId, User user) {
        return delegate.canDelete(requestUserId, user);
    }
}

// Assumptions:
// 1. `IUserEntityRepositoryFixture` is a test fixture that implements `IUserEntityRepository` with mock behavior.
// 2. The fixture class is designed for unit testing without any external dependencies.