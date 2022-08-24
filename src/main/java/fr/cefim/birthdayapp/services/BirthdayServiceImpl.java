package fr.cefim.birthdayapp.services;

import fr.cefim.birthdayapp.dtos.BirthdayDTO;
import fr.cefim.birthdayapp.entities.Birthday;
import fr.cefim.birthdayapp.exceptions.AccessDeniedException;
import fr.cefim.birthdayapp.exceptions.BirthdayNotFoundException;
import fr.cefim.birthdayapp.exceptions.UserNotFoundException;
import fr.cefim.birthdayapp.repositories.BirthdayRepository;
import fr.cefim.birthdayapp.security.MyPrincipalUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BirthdayServiceImpl implements BirthdayService {

    private BirthdayRepository mBirthdayRepository;
    private UserServiceImpl mUserService;

    public BirthdayServiceImpl(
            BirthdayRepository birthdayRepository,
            UserServiceImpl userService
    ) {
        mBirthdayRepository = birthdayRepository;
        mUserService = userService;
    }

    @Override
    public List<Birthday> getBirthdaysByUserId(Long userId) throws UserNotFoundException, AccessDeniedException {
        MyPrincipalUser userAuthenticated = (MyPrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userAuthenticated.getUser().getId().equals(userId)) {
            throw new AccessDeniedException(String.format("Access denied because your id(%d) is not same: %d", userAuthenticated.getUser().getId(), userId));
        }
        try {
            return mBirthdayRepository.findBirthdaysByUserId(userId);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(String.format("User not found with this id: %d", userId));
        }
    }

    @Override
    public Birthday getBirthdayByUserId(Long userId, Long id) throws AccessDeniedException, UserNotFoundException, BirthdayNotFoundException {
        MyPrincipalUser userAuthenticated = (MyPrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userAuthenticated.getUser().getId().equals(userId)) {
            throw new AccessDeniedException(String.format("Access denied because your id(%d) is not same: %d", userAuthenticated.getUser().getId(), userId));
        }
        return mBirthdayRepository.findBirthdayByUserIdAndId(userId, id);
    }

    @Override
    public Birthday createByDTO(Long userId, BirthdayDTO dto) throws AccessDeniedException, UserNotFoundException {
        MyPrincipalUser userAuthenticated = (MyPrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userAuthenticated.getUser().getId().equals(userId)) {
            throw new AccessDeniedException(String.format("Access denied because your id(%d) is not same: %d", userAuthenticated.getUser().getId(), userId));
        }
        Birthday birthday = new Birthday();
        birthday.setUser(mUserService.getUserById(userId));
        birthday.setDate(dto.getDate());
        birthday.setFirstname(dto.getFirstname());
        birthday.setLastname(dto.getLastname());
        mBirthdayRepository.save(birthday);
        return birthday;
    }

    @Override
    public Birthday updateById(Long userId, Birthday birthday, Long id) throws BirthdayNotFoundException, AccessDeniedException, UserNotFoundException {
        MyPrincipalUser userAuthenticated = (MyPrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userAuthenticated.getUser().getId().equals(userId)) {
            throw new AccessDeniedException(String.format("Access denied because your id(%d) is not same: %d", userAuthenticated.getUser().getId(), userId));
        }

        Set<Birthday> birthdaySet = mUserService.getUserById(userId).getBirthdays();
        Birthday targetBirthday = mBirthdayRepository.findById(id)
                .orElseThrow(() -> new BirthdayNotFoundException(String.format("Birthday not found with this id: %d", id)));
        if (!birthdaySet.contains(targetBirthday)) {
            throw new AccessDeniedException(String.format("Access denied because this birthday (%s) is not yours", id));
        }
        // FIXME: à simplifier car on sait déjà si le birthday existe où pas
        return mBirthdayRepository.findById(id)
                .map(birthdayUpdated -> {
                    birthdayUpdated.setDate(birthday.getDate());
                    birthdayUpdated.setFirstname(birthday.getFirstname());
                    birthdayUpdated.setLastname(birthday.getLastname());
                    return mBirthdayRepository.save(birthdayUpdated);
                }).orElseThrow(() -> new BirthdayNotFoundException(String.format("Birthday not found with this id: %d", id)));
    }

    @Override
    public void deleteById(Long userId, Long id) throws BirthdayNotFoundException, AccessDeniedException, UserNotFoundException {
//        MyPrincipalUser userAuthenticated = (MyPrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (!userAuthenticated.getUser().getId().equals(userId)) {
//            throw new AccessDeniedException(String.format("Access denied because your id(%d) is not same: %d", userAuthenticated.getUser().getId(), userId));
//        }
//
//        Set<Birthday> birthdaySet = mUserService.getUserById(userId).getBirthdays();
//        Birthday targetBirthday = mBirthdayRepository.findById(id)
//                .orElseThrow(() -> new BirthdayNotFoundException(String.format("Birthday not found with this id: %d", id)));
//        if (!birthdaySet.contains(targetBirthday)) {
//            throw new AccessDeniedException(String.format("Access denied because this birthday (%s) is not yours", id));
//        }

        mBirthdayRepository.deleteById(id);
    }
}
