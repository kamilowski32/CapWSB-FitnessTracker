package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping("/full")
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    @GetMapping("/simple")
    public List<BasicUser> getAllBasicUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toBasicUser)
                .toList();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserByID(@PathVariable long id) {
        return userService.getUser(id);
    }

    @GetMapping("/email")
    public List<EmailUser> searchByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email)
                .stream()
                .map(userMapper::toEmailUser)
                .toList();
    }

    @PostMapping()
    public ResponseEntity<User> addUser(@RequestBody UserDto userDto) throws InterruptedException {

        // Demonstracja how to use @RequestBody
        System.out.println("User with e-mail: " + userDto.email() + "passed to the request");

        User temp = userService.createUser(userMapper.toEntity(userDto));

        return ResponseEntity.status(HttpStatus.CREATED).body(temp);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long id) {
        Optional<User> user= userService.getUser(id);
        if (user.isPresent())
        {
            userService.deleteUser(id);
        }
        else throw new UserNotFoundException(id);
    }

    @GetMapping("/older/{time}")
    public List<User> getOlderThan(@PathVariable LocalDate time) {
        return userService.findAllUsers()
                .stream()
                .filter(user -> user.getBirthdate().isBefore(time))
                .toList();
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable long id, @RequestBody UserDto userDto) {
        System.out.println("User with e-mail: " + userDto.email() + "passed to the request");

        User user = userMapper.toEntity(userDto);
        return userService.updateUser(id, user);
    }


}