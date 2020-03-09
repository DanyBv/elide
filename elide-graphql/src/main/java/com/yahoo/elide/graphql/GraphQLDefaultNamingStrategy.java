/*
 * Copyright 2017, Yahoo Inc.
 * Licensed under the Apache License, Version 2.0
 * See LICENSE file in project root for terms.
 */

package com.yahoo.elide.graphql;

import com.yahoo.elide.core.EntityDictionary;

public class GraphQLDefaultNamingStrategy implements GraphQLNamingStrategy {
    private static final String MAP_SUFFIX = "Map";
    private static final String ARGUMENT_INPUT = "Input";
    private static final String ROOT_QUERY_NAME = "_root";
    private static final String ROOT_MUTATION_NAME = "_mutation_root";
    private static final String PAGE_INFO_OBJECT = "_pageInfoObject";
    private static final String NODE_PREFIX = "_node__";
    private static final String EDGES_PREFIX = "_edges__";
    private static final String INPUT_PREFIX = "_input__";

    private final EntityDictionary dictionary;

    public GraphQLDefaultNamingStrategy(EntityDictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public String getRootQueryName() {
        return ROOT_QUERY_NAME;
    }

    @Override
    public String getRootMutationName() {
        return ROOT_MUTATION_NAME;
    }

    @Override
    public String getPageInfoName() {
        return PAGE_INFO_OBJECT;
    }

    @Override
    public String toEdgesName(Class<?> clazz) {
        String entityName = dictionary.getJsonAliasFor(clazz);
        String name = EDGES_PREFIX + entityName;
        return name;
    }

    @Override
    public String toNodeName(Class<?> clazz) {
        String entityName = dictionary.getJsonAliasFor(clazz);
        String name = NODE_PREFIX + entityName;
        return name;
    }

    @Override
    public String toOutputTypeName(Class<?> clazz) {
        String name;
        if (dictionary.lookupIncludeClass(clazz) != null) {
            name = dictionary.getJsonAliasFor(clazz);
        } else {
            name = toValidName(clazz.getName());
        }
        return name;
    }

    @Override
    public String toInputTypeName(Class<?> clazz) {
        String name;
        if (dictionary.lookupIncludeClass(clazz) != null) {
            String entityName = dictionary.getJsonAliasFor(clazz);
            name = entityName + ARGUMENT_INPUT;
        } else {
            name = toValidName(INPUT_PREFIX + clazz.getName() + ARGUMENT_INPUT);
        }
        return name;
    }

    @Override
    public String toMapEntryOutputName(Class<?> keyClazz, Class<?> valueClazz) {
        return toValidName(keyClazz.getName() + valueClazz.getCanonicalName() + MAP_SUFFIX);
    }

    @Override
    public String toMapEntryInputName(Class<?> keyClazz, Class<?> valueClazz) {
        return toValidName(INPUT_PREFIX + keyClazz.getName() + valueClazz.getCanonicalName() + MAP_SUFFIX);
    }

    /**
     * Helper function converts a string into a valid name.
     *
     * @param input Input string
     * @return Sanitized form of input string
     */
    private String toValidName(String input) {
        return input
                .replace(".", "_") // Replaces package qualifier on class names
                .replace("$", "__1") // Replaces inner-class qualifier
                .replace("[", "___2"); // Replaces primitive list qualifier ([B == array of bytes)
    }
}
