package com.zhna123.easylunchprep.controller;

import com.zhna123.easylunchprep.entity.Lunchbox;
import com.zhna123.easylunchprep.exception.LunchboxNotFoundException;
import com.zhna123.easylunchprep.repository.LunchboxRepository;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class LunchboxController {

    // TODO review HATEOAS & HAL Explorer

    private final LunchboxRepository lunchboxRepository;

    public LunchboxController(LunchboxRepository lunchboxRepository) {
        this.lunchboxRepository = lunchboxRepository;
    }

    @GetMapping(path = "/lunchboxes/{lunchbox_id}")
    public ResponseEntity<Lunchbox> findLunchboxById(@PathVariable Long lunchbox_id) {
        Optional<Lunchbox> lunchbox = lunchboxRepository.findById(lunchbox_id);
        if (lunchbox.isEmpty()) {
            throw new LunchboxNotFoundException(STR."lunchbox not found: id=\{lunchbox_id}");
        }
        return ResponseEntity.ok(lunchbox.get());
    }

    @DeleteMapping(path = "/lunchboxes/{lunchbox_id}")
    public ResponseEntity<Void> deleteLunchboxById(@PathVariable Long lunchbox_id) {
        Optional<Lunchbox> lunchbox = lunchboxRepository.findById(lunchbox_id);
        if (lunchbox.isEmpty()) {
            throw new LunchboxNotFoundException(STR."lunchbox not found: id=\{lunchbox_id}");
        }
        lunchboxRepository.delete(lunchbox.get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/lunchboxes/{lunchbox_id}")
    public ResponseEntity<Lunchbox> updateLunchboxById(@PathVariable Long lunchbox_id,
                                                       @RequestBody Lunchbox lunchboxDetail) {
        Optional<Lunchbox> lunchbox = lunchboxRepository.findById(lunchbox_id);
        if (lunchbox.isEmpty()) {
            throw new LunchboxNotFoundException(STR."lunchbox not found: id=\{lunchbox_id}");
        }

        Lunchbox existed = lunchbox.get();
        existed.setFoods(lunchboxDetail.getFoods());
        existed.setFavorite(lunchboxDetail.isFavorite());
        existed.setName(lunchboxDetail.getName());

        Lunchbox updated = lunchboxRepository.save(existed);
        return ResponseEntity.ok(updated);
    }
}
