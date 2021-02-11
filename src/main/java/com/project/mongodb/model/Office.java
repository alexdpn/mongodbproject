package com.project.mongodb.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Office {

    @NotNull
    private int numberOfWorkers;
    @NotNull
    private Address address;
}
