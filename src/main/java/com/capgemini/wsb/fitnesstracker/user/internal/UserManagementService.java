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
class UserManagementService implements UserService, UserProvider {

    private final UserRepository userRepository;

    @Override
    public User addNewUser(final User user) {
        log.info("Starting user creation for:\n{}", user);
        validateUserCreation(user);
        return userRepository.save(user);
    }

    private void validateUserCreation(final User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("New user cannot have an existing database ID.");
        }
    }

    @Override
    public Optional<User> getUserById(Long userId) {
            return userRepository.findById(userId);
        }

    @Override
    public List<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
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

}