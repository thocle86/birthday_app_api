package fr.cefim.birthdayapp.controllers;

import fr.cefim.birthdayapp.dtos.BirthdayDTO;
import fr.cefim.birthdayapp.entities.Birthday;
import fr.cefim.birthdayapp.exceptions.AccessDeniedException;
import fr.cefim.birthdayapp.exceptions.BirthdayNotFoundException;
import fr.cefim.birthdayapp.exceptions.UserNotFoundException;
import fr.cefim.birthdayapp.services.BirthdayService;
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
    public ResponseEntity<List<Birthday>> getBirthdaysByUserId(@PathVariable Long userId) throws UserNotFoundException, AccessDeniedException {
        return new ResponseEntity<>(mBirthdayService.getBirthdaysByUserId(userId), HttpStatus.FOUND);
    }

    @GetMapping("/{userId}/birthdays/{id}")
    public ResponseEntity<Birthday> getBirthdayByUserId(@PathVariable Long userId, @PathVariable Long id) throws UserNotFoundException, BirthdayNotFoundException, AccessDeniedException {
        return new ResponseEntity<>(mBirthdayService.getBirthdayByUserId(userId, id), HttpStatus.FOUND);
    }

    @PostMapping("/{userId}/birthdays")
    public ResponseEntity<Birthday> createByDTO(@PathVariable Long userId, @RequestBody BirthdayDTO dto) throws UserNotFoundException, AccessDeniedException {
        return new ResponseEntity<>(mBirthdayService.createByDTO(userId, dto), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}/birthdays/{id}")
    public ResponseEntity<Birthday> updateById(@PathVariable Long userId, @RequestBody Birthday birthday, @PathVariable Long id) throws UserNotFoundException, BirthdayNotFoundException, AccessDeniedException {
        return new ResponseEntity<>(mBirthdayService.updateById(userId, birthday, id), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/birthdays/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long userId, @PathVariable Long id) throws UserNotFoundException, BirthdayNotFoundException, AccessDeniedException {
        mBirthdayService.deleteById(userId, id);
        return new ResponseEntity<>(HttpStatus.GONE);
    }

}
