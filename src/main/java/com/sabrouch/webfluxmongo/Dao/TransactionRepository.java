package com.sabrouch.webfluxmongo.Dao;

import com.sabrouch.webfluxmongo.model.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created by sabrouch.
 * Date: 12/30/2020
 */
public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
}
