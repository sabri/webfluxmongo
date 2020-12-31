package com.sabrouch.webfluxmongo.Dao;

import com.sabrouch.webfluxmongo.model.Societe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created by sabrouch.
 * Date: 12/30/2020
 */
public interface SocieteRepository extends ReactiveMongoRepository<Societe, String> {
}
