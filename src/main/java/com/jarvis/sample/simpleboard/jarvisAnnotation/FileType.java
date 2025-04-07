package com.jarvis.sample.simpleboard.jarvisAnnotation;

public enum FileType {
    // domain
    DOMAIN_SPEC,
    DOMAIN_API,
    DOMAIN_API_IMPL,
    DOMAIN_API_TEST,
    DOMAIN_API_FIXTURE,


    // infra
    INFRA_ENTITY,
    INFRA_REPOSITORY,
    INFRA_REPOSITORY_IMPL,
    INFRA_REPOSITORY_TEST,
    INFRA_REPOSITORY_FIXTURE,


    // web
    CONTROLLER,
    API_SERVICE_IMPL,
    API_SERVICE_TEST
}