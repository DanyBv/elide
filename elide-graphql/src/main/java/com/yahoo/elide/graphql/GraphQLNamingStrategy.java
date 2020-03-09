/*
 * Copyright 2017, Yahoo Inc.
 * Licensed under the Apache License, Version 2.0
 * See LICENSE file in project root for terms.
 */

package com.yahoo.elide.graphql;

public interface GraphQLNamingStrategy {
    String getRootQueryName();
    String getRootMutationName();
    String getPageInfoName();
    String toEdgesName(Class<?> clazz);
    String toNodeName(Class<?> clazz);
    String toOutputTypeName(Class<?> clazz);
    String toInputTypeName(Class<?> clazz);
    String toMapEntryOutputName(Class<?> keyClazz, Class<?> valueClazz);
    String toMapEntryInputName(Class<?> keyClazz, Class<?> valueClazz);
}
