package com.example.project_shoes.controller;

import com.example.project_shoes.dto.ConfigurationsDTO;
import com.example.project_shoes.service.ConfigurationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/configurations")
public class ConfigurationsController {
    
    @Autowired
    private ConfigurationsService configurationsService;
    
    @GetMapping
    public ResponseEntity<List<ConfigurationsDTO>> findAll() {
        List<ConfigurationsDTO> configurationsDTOs = configurationsService.findAll();
        return new ResponseEntity<>(configurationsDTOs, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ConfigurationsDTO> findById(@PathVariable Long id) {
        ConfigurationsDTO configurationsDTO = configurationsService.findById(id);
        if (configurationsDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(configurationsDTO, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<ConfigurationsDTO> save(@RequestBody ConfigurationsDTO configurationsDTO) {
        ConfigurationsDTO savedConfigurationsDTO = configurationsService.save(configurationsDTO);
        return new ResponseEntity<>(savedConfigurationsDTO, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ConfigurationsDTO> update(@PathVariable Long id, @RequestBody ConfigurationsDTO configurationsDTO) {
        configurationsDTO.setId(id);
        ConfigurationsDTO updatedConfigurationsDTO = configurationsService.update(configurationsDTO);
        if (updatedConfigurationsDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedConfigurationsDTO, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = configurationsService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<ConfigurationsDTO>> findByName(@RequestParam String name) {
        List<ConfigurationsDTO> configurationsDTOs = configurationsService.findByName(name);
        return new ResponseEntity<>(configurationsDTOs, HttpStatus.OK);
    }
} 