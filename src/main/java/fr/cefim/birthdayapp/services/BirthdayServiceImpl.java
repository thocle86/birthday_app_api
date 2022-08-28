package fr.cefim.birthdayapp.services;

import fr.cefim.birthdayapp.dtos.BirthdayDTO;
import fr.cefim.birthdayapp.entities.Birthday;
import fr.cefim.birthdayapp.exceptions.BusinessResourceException;
import fr.cefim.birthdayapp.repositories.BirthdayRepository;
import fr.cefim.birthdayapp.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BirthdayServiceImpl implements BirthdayService {

    private BirthdayRepository mBirthdayRepository;
    private UserServiceImpl mUserService;

    @Autowired
    public BirthdayServiceImpl(
            BirthdayRepository birthdayRepository,
            UserServiceImpl userService
    ) {
        mBirthdayRepository = birthdayRepository;
        mUserService = userService;
    }

    @Override
    public List<Birthday> getBirthdaysByUserId(Long userId) throws BusinessResourceException {
        UserDetailsImpl userAuthenticated = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userAuthenticated.getUser().getId().equals(userId)) {
            throw new BusinessResourceException("AccessDenied", String.format("Access denied with this ID: %d", userAuthenticated.getUser().getId()), HttpStatus.UNAUTHORIZED);
        }
        try {
            return mBirthdayRepository.findBirthdaysByUserId(userId);
        } catch (Exception e) {
            throw new BusinessResourceException("UserNotFound", String.format("No user with this ID: %d", userId), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Optional<Birthday> getBirthdayByUserId(Long userId, Long id) throws BusinessResourceException {
        UserDetailsImpl userAuthenticated = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userAuthenticated.getUser().getId().equals(userId)) {
            throw new BusinessResourceException("AccessDenied", String.format("Access denied with this ID: %d", userAuthenticated.getUser().getId()), HttpStatus.UNAUTHORIZED);
        }
        try {
            return mBirthdayRepository.findBirthdayByUserIdAndId(userId, id);
        } catch (Exception e) {
            throw new BusinessResourceException("UserNotFound or BirthdayNotFound", String.format("No user with this ID: %d or birthday with this ID: %d", userId, id), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Birthday saveBirthdayByDTO(Long userId, BirthdayDTO dto) throws BusinessResourceException {
        UserDetailsImpl userAuthenticated = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userAuthenticated.getUser().getId().equals(userId)) {
            throw new BusinessResourceException("AccessDenied", String.format("Access denied with this ID: %d", userAuthenticated.getUser().getId()), HttpStatus.UNAUTHORIZED);
        }
        Birthday birthday = new Birthday();
        birthday.setUser(mUserService.getUserById(userId).get());
        birthday.setDate(dto.getDate());
        birthday.setFirstname(dto.getFirstname());
        birthday.setLastname(dto.getLastname());
        mBirthdayRepository.save(birthday);
        return birthday;

    }

    @Override
    public Birthday updateBirthdayByDTO(Long userId, Long id, BirthdayDTO birthdayDTO) throws BusinessResourceException {
        UserDetailsImpl userAuthenticated = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userAuthenticated.getUser().getId().equals(userId)) {
            throw new BusinessResourceException("AccessDenied", String.format("Access denied with this ID: %d", userAuthenticated.getUser().getId()), HttpStatus.UNAUTHORIZED);
        }
        Set<Birthday> birthdaySet = mUserService.getUserById(userId).get().getBirthdays();
        Optional<Birthday> birthdayFound = mBirthdayRepository.findById(id);
        if (birthdayFound.isEmpty()) {
            throw new BusinessResourceException("BirthdayNotFound", String.format("No birthday with this ID: %d", id), HttpStatus.NOT_FOUND);
        }
        if (!birthdaySet.contains(birthdayFound.get())) {
            throw new BusinessResourceException("AccessDenied", String.format("Access denied because this birthday (%d) is not yours", id), HttpStatus.UNAUTHORIZED);
        }
        Birthday birthdayUpdated = birthdayFound.get();
        birthdayUpdated.setDate(birthdayDTO.getDate());
        birthdayUpdated.setFirstname(birthdayDTO.getFirstname());
        birthdayUpdated.setLastname(birthdayDTO.getLastname());
        return mBirthdayRepository.save(birthdayUpdated);
    }

    @Override
    public void deleteById(Long userId, Long id) throws BusinessResourceException {
        UserDetailsImpl userAuthenticated = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userAuthenticated.getUser().getId().equals(userId)) {
            throw new BusinessResourceException("AccessDenied", String.format("Access denied with this ID: %d", userAuthenticated.getUser().getId()), HttpStatus.UNAUTHORIZED);
        }
        Set<Birthday> birthdaySet = mUserService.getUserById(userId).get().getBirthdays();
        Optional<Birthday> birthdayFound = mBirthdayRepository.findById(id);
        if (birthdayFound.isEmpty()) {
            throw new BusinessResourceException("BirthdayNotFound", String.format("No birthday with this ID: %d", id), HttpStatus.NOT_FOUND);
        }
        if (!birthdaySet.contains(birthdayFound.get())) {
            throw new BusinessResourceException("AccessDenied", String.format("Access denied because this birthday (%d) is not yours", id), HttpStatus.UNAUTHORIZED);
        }
        mBirthdayRepository.deleteById(id);
    }
}
