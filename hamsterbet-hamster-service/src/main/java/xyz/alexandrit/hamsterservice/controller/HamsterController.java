package xyz.alexandrit.hamsterservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import xyz.alexandrit.common.dto.request.*;
import xyz.alexandrit.common.dto.response.*;

import xyz.alexandrit.hamsterservice.exception.HamsterNotFoundException;
import xyz.alexandrit.hamsterservice.service.HamsterService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hamsters")
@RequiredArgsConstructor
public class HamsterController {

    private final HamsterService hamsterService;

    @GetMapping
    public ResponseEntity<List<HamsterResponseDTO>> getHamsters() {
        return ResponseEntity.ok(hamsterService.findAll());
    }

    @GetMapping("/{hamster-id}")
    public ResponseEntity<HamsterResponseDTO> getHamster(@PathVariable("hamster-id") Long hamsterId) {
        try {
            return ResponseEntity.ok(hamsterService.findById(hamsterId));

        } catch (HamsterNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<HamsterRegisterResponseDTO> registerHamster(@Validated @RequestBody HamsterRegisterRequestDTO hamsterRegisterRequestDTO) {
        var response = hamsterService.save(hamsterRegisterRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{hamster-id}")
    public ResponseEntity<HamsterResponseDTO> updateHamster(@PathVariable("hamster-id") Long hamsterId, @Validated @RequestBody HamsterUpdateRequestDTO hamsterUpdateRequestDTO) {
        try {
            var response = hamsterService.updateById(hamsterId, hamsterUpdateRequestDTO);
            return ResponseEntity.ok(response);
        } catch (HamsterNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{hamster-id}")
    public ResponseEntity<HamsterDeletedResponseDTO> deleteHamster(@PathVariable("hamster-id") Long hamsterId) {
        var response = hamsterService.deleteById(hamsterId);

        return ResponseEntity.ok(response);
    }
}
