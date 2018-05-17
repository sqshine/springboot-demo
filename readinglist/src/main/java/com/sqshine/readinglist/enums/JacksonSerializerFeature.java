package com.sqshine.readinglist.enums;

public enum JacksonSerializerFeature {
    WriteNullListAsEmpty,
    WriteNullStringAsEmpty,
    WriteNullNumberAsZero,
    WriteNullBooleanAsFalse;

    public final int mask;

    JacksonSerializerFeature() {
        mask = (1 << ordinal());
    }
}
