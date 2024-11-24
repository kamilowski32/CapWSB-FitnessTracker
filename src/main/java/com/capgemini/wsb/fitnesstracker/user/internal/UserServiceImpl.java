package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(Long userId) {
            return userRepository.findById(userId);
        }

    @Override
    public List<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public List<User> findUsersOlderThan(String Date) {
        return List.of();
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return Optional.empty();
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(final Long userId) {
        log.info("Deleting User {}", userId);

        userRepository.deleteById(userId);
    }

    public User updateUser(final Long id, final User user) {
        User old = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        log.info("Updating User {}", user);

        old.setFirstName(user.getFirstName());
        old.setLastName(user.getLastName());
        old.setEmail(user.getEmail());
        old.setBirthdate(user.getBirthdate());
        return userRepository.save(old);
    }

    @Override
    public List<User> searchUsersByEmail(String emailFragment) {
        return List.of();
    }

}