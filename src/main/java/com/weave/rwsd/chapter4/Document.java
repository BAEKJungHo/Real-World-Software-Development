package com.weave.rwsd.chapter4;

import java.util.Map;

/**
 * Document 클래스를 불변(immutable) 클래스로 만듦으로써
 * Document 로 색인을 만들거나 Document 정보를 캐시할 수 있다.
 *
 * Document 생성자를 패키지로 제한한다.
 * 그 이유는 오직 문서 관리 시스템에서만 Document 를 만들 수 있어야 하기 때문이다.
 */
public class Document {

    private final Map<String, String> attributes;

    Document(final Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public String getAttribute(final String attributeName) {
        return attributes.get(attributeName);
    }

}