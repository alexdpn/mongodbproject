package com.project.mongodb.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.AllArgsConstructor;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Office {
    private int numberOfWorkers;
    private Address address;
}
