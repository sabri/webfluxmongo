package com.sabrouch.webfluxmongo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

/**
 * Created by sabrouch.
 * Date: 12/30/2020
 */
@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Transaction {

    @Id
    private String id;
    private Instant instant;
    private double  price;
    @DBRef
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Societe societe;
}
