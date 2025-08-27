package com.app.ecom.service;

import com.app.ecom.dto.AddressDTO;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.model.Address;
import com.app.ecom.model.User;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserResponse> fetchUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToUserResponse).toList();
    }

    public Optional<UserResponse> fetchUser(Long id) {
        return userRepository.findById(id).map(this::mapToUserResponse);
    }

    public void addUser(UserRequest userRequest) {
        User user = new User();
        updateUserFromRequest(user, userRequest);
        userRepository.save(user);
    }

    public boolean updateUser(Long id, UserRequest userRequest) {
        return userRepository.findById(id).map(user -> {
            updateUserFromRequest(user, userRequest);
            userRepository.save(user);
            return true;
        }).orElse(false);
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if(userRequest.getAddress() != null) {
            Address address = new Address();
            address.setCity(userRequest.getAddress().getCity());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setStreet(userRequest.getAddress().getStreet());
            address.setZipcode(userRequest.getAddress().getZipcode());
            address.setState(userRequest.getAddress().getState());
            user.setAddress(address);
        }
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(String.valueOf(user.getId()));
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        userResponse.setRole(user.getRole());
        if(user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            userResponse.setAddressDTO(addressDTO);
        }
        return userResponse;
    }
}
