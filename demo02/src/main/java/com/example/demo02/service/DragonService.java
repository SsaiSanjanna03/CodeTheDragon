package com.example.demo02.service;

import com.example.demo02.dto.DragonDTO;
import java.util.List;

public interface DragonService {
    DragonDTO createDragon(DragonDTO dragonDTO);
    List<DragonDTO> getAllDragons();
    DragonDTO getDragonById(Long id);
    DragonDTO updateDragon(Long id, DragonDTO dragonDTO);
    void deleteDragon(Long id);
    DragonDTO buyDragon(Long userId, Long dragonId);
    List<DragonDTO> getDragonsByUser(Long userId);
    List<DragonDTO> getDragonsOwnedByUser(Long userId);
}
