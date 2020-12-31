package com.sabrouch.webfluxmongo.controller;

import com.sabrouch.webfluxmongo.Dao.SocieteRepository;
import com.sabrouch.webfluxmongo.Dao.TransactionRepository;

import com.sabrouch.webfluxmongo.model.Societe;
import com.sabrouch.webfluxmongo.model.Transaction;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

/**
 * Created by sabrouch.
 * Date: 12/31/2020
 */
@RestController
@RequestMapping("transactions")
public class TransactionReactive {

    final TransactionRepository transactionRepository;
    final SocieteRepository societeRepository;

    public TransactionReactive(TransactionRepository transactionRepository, SocieteRepository societeRepository) {
        this.transactionRepository = transactionRepository;
        this.societeRepository = societeRepository;
    }

    @GetMapping("/transaction")
    public Flux<Transaction> findall(){
        return transactionRepository.findAll();
    }
    @GetMapping("/transaction/add/{id}")
    public Mono<Transaction> findById(@PathVariable String id){
        return transactionRepository.findById(id);
    }
    @PostMapping("/transaction/add")
    public Mono<Transaction> save(@RequestBody Transaction transaction){
        return transactionRepository.save(transaction);
    }
    @DeleteMapping(value = "/transaction/delete/{id}")
            public Mono<Void> deleteById(@PathVariable String id){return transactionRepository.deleteById(id);}
    @PutMapping("/transaction/put/{id}")
    public Mono<Transaction> put(@RequestBody Transaction transaction, @PathVariable String id){
       transaction.setId(id);
        return transactionRepository.save(transaction);
    }

    @GetMapping(value = "/streamTransactions",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Transaction> streamTransactions(){
        return transactionRepository.findAll();
    }
    @GetMapping(value = "/transactionsBySociete/{id}")
    public Flux<Transaction> transactionsBySoc(@PathVariable String id){
        Societe societe=new Societe();societe.setId(id);
        return transactionRepository.findBySociete(societe);
    }
    @GetMapping(value = "/streamTransactionsBySociete/{id}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Transaction> stream(@PathVariable String id){
        return societeRepository.findById(id)
                .flatMapMany(soc->{
                    Flux<Long> interval=Flux.interval(Duration.ofMillis(1000));
                    Flux<Transaction> transactionFlux= Flux.fromStream(Stream.generate(()->{
                        Transaction transaction=new Transaction();
                        transaction.setInstant(Instant.now());
                        transaction.setSociete(soc);
                        transaction.setPrice(soc.getPrice()*(1+(Math.random()*12-6)/100));
                        return transaction;
                    }));
                    return Flux.zip(interval,transactionFlux)
                            .map(data->{
                                return data.getT2();
                            }).share();
                });
    }

    @GetMapping(value = "/events/{id}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public  Flux<Double>   events(@PathVariable String id){
        WebClient webClient=WebClient.create("http://localhost:8082");
        Flux<Double> eventFlux=webClient.get()
                .uri("/streamEvents/"+id)
                .retrieve().bodyToFlux(Event.class)
                .map(data->data.getValue());
        return eventFlux;

    }
    @GetMapping("/test")
    public String test(){
        return Thread.currentThread().getName();
    }
}
@Data
class Event{
    private Instant instant;
    private double value;
    private String societeID;
}