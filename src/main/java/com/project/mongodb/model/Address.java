package com.project.mongodb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @NotNull
    private String country;

    @NotNull
    private String city;

    @NotNull
    private String street;
}

