package com.sabrouch.webfluxmongo;

import com.sabrouch.webfluxmongo.Dao.SocieteRepository;
import com.sabrouch.webfluxmongo.Dao.TransactionRepository;
import com.sabrouch.webfluxmongo.model.Societe;
import com.sabrouch.webfluxmongo.model.Transaction;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.util.stream.Stream;

@SpringBootApplication
public class WebfluxmongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxmongoApplication.class, args);
    }

    @Bean
    CommandLineRunner start ( SocieteRepository societeRepository, TransactionRepository transactionRepository){

       return   args -> { societeRepository.deleteAll().subscribe(null,null,()->{
           Stream.of("AF","STEG","SOUND","TELECOM").
                   forEach(s -> societeRepository.save(new Societe(s,s,100+Math.random()*900))
                           .subscribe(societe -> System.out.println(societe.toString())));

                    });
        transactionRepository.deleteAll().subscribe(null,null,()->{
           Stream.of("AF","STEG","SOUND","TELECOM").forEach(sc ->
               societeRepository.findById(sc).subscribe( soc -> {
               for (int i = 0; i < 10; i++) {
                   Transaction transaction = new Transaction();
                   transaction.setInstant(Instant.now());
                   transaction.setPrice(soc.getPrice()*(1+(Math.random()*12)-6)/100);
                   transaction.setSociete(soc);
                   transactionRepository.save(transaction).subscribe(s -> System.out.println(s.toString()));

               }

           }));}
           );




       };
      }}
