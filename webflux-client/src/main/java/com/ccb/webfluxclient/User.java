package com.ccb.webfluxclient;

import com.google.common.collect.ImmutableSet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 类名, 随意, 但字段需要保持一致
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    private String id;

    private String name;

    private int age;

}
