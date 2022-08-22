package fr.cefim.birthdayapp.services;

import fr.cefim.birthdayapp.entities.Birthday;
import fr.cefim.birthdayapp.repositories.BirthdayRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Birthday> getAllBirthdays() {
        return mBirthdayRepository.findAll();
    }

    @Override
    public List<Birthday> getBirthdaysByUserId(Long userId) {
        return null;
    }

    @Override
    public Birthday save(Birthday birthday) {
        return mBirthdayRepository.save(birthday);
    }

}
