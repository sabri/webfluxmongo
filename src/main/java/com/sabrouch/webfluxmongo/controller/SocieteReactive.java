package com.sabrouch.webfluxmongo.controller;

import com.sabrouch.webfluxmongo.Dao.SocieteRepository;
import com.sabrouch.webfluxmongo.model.Societe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by sabrouch.
 * Date: 12/31/2020
 */
@RestController
@RequestMapping("/webflux")
public class SocieteReactive {

    @Autowired
    SocieteRepository societeRepository;

    @GetMapping("/societe")
    public Flux<Societe> findall(){
        return societeRepository.findAll();
    }
    @GetMapping("/societe/add/{id}")
    public Mono<Societe> findById(@PathVariable String id){
        return societeRepository.findById(id);
    }
    @PostMapping("/societe/add")
    public Mono<Societe> save(@RequestBody Societe societe){
        return societeRepository.save(societe);
    }
    @DeleteMapping(value = "/societe/delete/{id}")
            public Mono<Void> deleteById(@PathVariable String id){return societeRepository.deleteById(id);}
    @PutMapping("/societe/put/{id}")
    public Mono<Societe> put(@RequestBody Societe societe, @PathVariable String id){
       societe.setId(id);
        return societeRepository.save(societe);
    }
}
