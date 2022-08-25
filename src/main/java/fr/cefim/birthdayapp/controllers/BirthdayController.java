package fr.cefim.birthdayapp.controllers;

import fr.cefim.birthdayapp.dtos.BirthdayDTO;
import fr.cefim.birthdayapp.entities.Birthday;
import fr.cefim.birthdayapp.services.BirthdayServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class BirthdayController {

    private final BirthdayServiceImpl mBirthdayService;

    public BirthdayController(BirthdayServiceImpl birthdayService) {
        mBirthdayService = birthdayService;
    }

    @GetMapping("/{userId}/birthdays")
    public ResponseEntity<List<Birthday>> getBirthdaysByUserId(@PathVariable Long userId) {
        List<Birthday> birthdayList = mBirthdayService.getBirthdaysByUserId(userId);
        return new ResponseEntity<>(birthdayList, HttpStatus.FOUND);
    }

    @GetMapping("/{userId}/birthdays/{id}")
    public ResponseEntity<Birthday> getBirthdayByUserId(@PathVariable Long userId, @PathVariable Long id) {
        Birthday birthdayFound = mBirthdayService.getBirthdayByUserId(userId, id).get();
        return new ResponseEntity<>(birthdayFound, HttpStatus.FOUND);
    }

    @PostMapping("/{userId}/birthdays")
    public ResponseEntity<Birthday> saveBirthday(@PathVariable Long userId, @RequestBody BirthdayDTO dto) {
        Birthday birthdaySaved = mBirthdayService.saveBirthdayByDTO(userId, dto);
        return new ResponseEntity<>(birthdaySaved, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}/birthdays/{id}")
    public ResponseEntity<Birthday> updateBirthday(@PathVariable Long userId, @PathVariable Long id, @RequestBody BirthdayDTO birthdayDTO) {
        Birthday birthdayUpdated = mBirthdayService.updateBirthdayByDTO(userId, id, birthdayDTO);
        return new ResponseEntity<>(birthdayUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/birthdays/{id}")
    public ResponseEntity<Void> deleteBirthday(@PathVariable Long userId, @PathVariable Long id) {
        mBirthdayService.deleteById(userId, id);
        return new ResponseEntity<>(HttpStatus.GONE);
    }

}
