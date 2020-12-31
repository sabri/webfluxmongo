package com.sabrouch.webfluxmongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by sabrouch.
 * Date: 12/30/2020
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Societe
{
    @Id
    private String id;
    private String name;
    private double price;

}
