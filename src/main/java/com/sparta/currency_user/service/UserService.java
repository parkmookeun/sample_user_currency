package com.sparta.currency_user.service;

import com.sparta.currency_user.dto.LoginRequestDto;
import com.sparta.currency_user.dto.UserRequestDto;
import com.sparta.currency_user.dto.UserResponseDto;
import com.sparta.currency_user.entity.User;
import com.sparta.currency_user.exception.NoSuchExchangeInfoException;
import com.sparta.currency_user.exception.NoSuchUserInfoException;
import com.sparta.currency_user.exception.NotMatchPasswordException;
import com.sparta.currency_user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto findById(Long id) {
        return new UserResponseDto(findUserById(id));
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream().map(UserResponseDto::toDto).toList();
    }

    @Transactional
    public UserResponseDto save(UserRequestDto userRequestDto) {
        User savedUser = userRepository.save(userRequestDto.toEntity());
        return new UserResponseDto(savedUser);
    }

    @Transactional
    public void deleteUserById(Long id) {
        this.findUserById(id);
        userRepository.deleteById(id);
    }

    public User login(LoginRequestDto loginRequestDto) {
        //해당하는 유저 찾기
        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(
                () -> new NoSuchUserInfoException(loginRequestDto.getEmail() + "에 해당하는 유저가 없습니다!")
        );
        log.info("유저 비밀번호: {}, 요청 비밀번호: {}", user.getPassword(), loginRequestDto.getPassword());
        if(!user.getPassword().equals(loginRequestDto.getPassword())){
            throw new NotMatchPasswordException("비밀번호가 일치하지 않습니다!");
        }
        return user;
    }
}
