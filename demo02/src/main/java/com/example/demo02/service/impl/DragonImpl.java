package com.example.demo02.service.impl;

import com.example.demo02.dto.DragonDTO;
import com.example.demo02.entity.Dragon;
import com.example.demo02.entity.User;
import com.example.demo02.repository.DragonRepo;
import com.example.demo02.repository.UserRepo;
import com.example.demo02.service.DragonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DragonImpl implements DragonService {
    private static final Logger logger = LoggerFactory.getLogger(DragonImpl.class);

    @Autowired
    private DragonRepo dragonRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public DragonDTO createDragon(DragonDTO dragonDTO) {
        Dragon dragon = new Dragon(dragonDTO);
        dragonRepo.save(dragon);
        return new DragonDTO(dragon);
    }

    @Override
    public List<DragonDTO> getAllDragons() {
        List<Dragon> dragons = dragonRepo.findAll();
        return dragons.stream().map(DragonDTO::new).collect(Collectors.toList());
    }

    @Override
    public DragonDTO getDragonById(Long id) {
        Optional<Dragon> dragon = dragonRepo.findById(id);
        return dragon.map(DragonDTO::new).orElseThrow(() -> new RuntimeException("Dragon not found"));
    }

    @Override
    public DragonDTO updateDragon(Long id, DragonDTO dragonDTO) {
        Dragon dragon = dragonRepo.findById(id).orElseThrow(() -> new RuntimeException("Dragon not found"));
        dragon.setDragon_name(dragonDTO.getDragon_name());
        dragon.setAge(dragonDTO.getAge());
        dragon.setDragon_image(dragonDTO.getDragon_image());
        dragon.setPrice(dragonDTO.getPrice());
        dragon.setRarity(dragonDTO.getRarity());
        dragonRepo.save(dragon);
        return new DragonDTO(dragon);
    }

    @Override
    public void deleteDragon(Long id) {
        Dragon dragon = dragonRepo.findById(id).orElseThrow(() -> new RuntimeException("Dragon not found"));
        dragonRepo.delete(dragon);
    }
    @Override
    public DragonDTO buyDragon(Long userId, Long dragonId) {
        try {
            logger.info("Starting purchase process for userId: {} and dragonId: {}", userId, dragonId);


            User user = userRepo.findById((int) userId.intValue()).orElseThrow(() -> {
                logger.error("User with ID {} not found", userId);
                return new RuntimeException("User not found");
            });
            Dragon dragon = dragonRepo.findById(dragonId).orElseThrow(() -> {
                logger.error("Dragon with ID {} not found", dragonId);
                return new RuntimeException("Dragon not found");
            });


            if (user.getDragons().contains(dragon)) {
                logger.warn("User already owns this dragon. Returning existing dragon without adding again.");
                return new DragonDTO(dragon);
            }


            if (user.getTotPoints() < dragon.getPrice()) {
                logger.warn("Not enough points. User points: {}, Dragon price: {}", user.getTotPoints(), dragon.getPrice());
                throw new RuntimeException("Not enough points to buy this dragon.");
            }


            user.setTotPoints(user.getTotPoints() - (int) dragon.getPrice());
            user.getDragons().add(dragon);

            logger.info("Saving user and dragon to persist the transaction.");
            userRepo.save(user);


            logger.info("Purchase completed successfully for userId: {} and dragonId: {}", userId, dragonId);
            return new DragonDTO(dragon);
        } catch (Exception e) {
            logger.error("Error in buyDragon: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to purchase dragon. Details: " + e.getMessage());
        }
    }



    @Override
    public List<DragonDTO> getDragonsOwnedByUser(Long userId) {
        User user = userRepo.findById((int) Math.toIntExact(userId)).orElseThrow(() -> new RuntimeException("User not found"));
        List<Dragon> dragons = user.getDragons();
        return dragons.stream().map(DragonDTO::new).collect(Collectors.toList());
    }


    @Override
    public List<DragonDTO> getDragonsByUser(Long userId) {
        List<Dragon> dragons = dragonRepo.findByUser_UserId(userId);
        return dragons.stream().map(DragonDTO::new).collect(Collectors.toList());
    }
}
