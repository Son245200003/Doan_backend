package com.example.project_posgre.services.Impl;

import com.example.project_posgre.dtos.requests.CreateUserRequest;
import com.example.project_posgre.exception.AccountAlreadyExist;
import com.example.project_posgre.exception.NotFoundException;
import com.example.project_posgre.models.Role;
import com.example.project_posgre.models.User;
import com.example.project_posgre.repository.RoleRepository;
import com.example.project_posgre.repository.UserRepository;
import com.example.project_posgre.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("Ko tim thay user with id :"+id));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        Role role=roleRepository.findById(request.getRoleId()).orElseThrow(() ->
                new NotFoundException("Ko tim thay role id muong dang nhap"));
        if(userRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new AccountAlreadyExist("Accout Already Exist");
        }
        String password= request.getPassword();
        String encodePassword=passwordEncoder.encode(password);
        User user=User.builder()
                .address(request.getAddress())
                .fullName(request.getFullName())
                .roleId(role)
                .password(encodePassword)
                .phoneNumber(request.getPhoneNumber())
                .active(true)
                .build();
        return userRepository.save(user);
    }
    public User updateUser(Long id, CreateUserRequest request) {
        // Lấy user cần cập nhật
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng với ID: " + id));

        // Kiểm tra role hợp lệ
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy role với ID: " + request.getRoleId()));

        // Kiểm tra nếu số điện thoại đã tồn tại ở user khác
        Optional<User> userWithPhone = userRepository.findByPhoneNumber(request.getPhoneNumber());
        if (userWithPhone.isPresent() && !userWithPhone.get().getId().equals(id)) {
            throw new AccountAlreadyExist("Số điện thoại đã được sử dụng bởi tài khoản khác");
        }

        // Cập nhật thông tin
        existingUser.setFullName(request.getFullName());
        existingUser.setAddress(request.getAddress());
        existingUser.setPhoneNumber(request.getPhoneNumber());
        existingUser.setRoleId(role);

        // Chỉ encode và set password nếu người dùng có nhập password mới
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            existingUser.setPassword(encodedPassword);
        }

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void blockOrEnable(Long userId, Boolean active) {
        User user=findById(userId);
        user.setActive(active);
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAllByOrderByIdAsc();
    }

    @Override
    public List<User> searchByCreateAt(LocalDateTime begin, LocalDateTime end) {
        return userRepository.findByCreatedAtBetween(begin,end);
    }
}
