package com.example.demo02.controller;

import com.example.demo02.dto.DragonDTO;
import com.example.demo02.service.DragonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dragons")
public class DragonController {

   @Autowired
   private DragonService dragonService;

   @PostMapping
   public ResponseEntity<DragonDTO> createDragon(@RequestBody DragonDTO dragonDTO) {
      DragonDTO createdDragon = dragonService.createDragon(dragonDTO);
      return ResponseEntity.ok(createdDragon);
   }

   @GetMapping
   public ResponseEntity<List<DragonDTO>> getAllDragons() {
      List<DragonDTO> dragons = dragonService.getAllDragons();
      return ResponseEntity.ok(dragons);
   }

   @GetMapping("/{id}")
   public ResponseEntity<DragonDTO> getDragonById(@PathVariable Long id) {
      DragonDTO dragon = dragonService.getDragonById(id);
      return ResponseEntity.ok(dragon);
   }

   @PutMapping("/{id}")
   public ResponseEntity<DragonDTO> updateDragon(@PathVariable Long id, @RequestBody DragonDTO dragonDTO) {
      DragonDTO updatedDragon = dragonService.updateDragon(id, dragonDTO);
      return ResponseEntity.ok(updatedDragon);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<String> deleteDragon(@PathVariable Long id) {
      dragonService.deleteDragon(id);
      return ResponseEntity.ok("Dragon deleted successfully");
   }

   @PostMapping("/buy/{userId}/{dragonId}")
   public ResponseEntity<DragonDTO> buyDragon(@PathVariable Long userId, @PathVariable Long dragonId) {
      DragonDTO purchasedDragon = dragonService.buyDragon(userId, dragonId);
      return ResponseEntity.ok(purchasedDragon);
   }

   @GetMapping("/user/{userId}/dragons")
   public ResponseEntity<List<DragonDTO>> getDragonsOwnedByUser(@PathVariable Long userId) {
      List<DragonDTO> dragons = dragonService.getDragonsOwnedByUser(userId);
      return ResponseEntity.ok(dragons);
   }
}
