package com.example.order.usecase;

import cn.hutool.crypto.digest.BCrypt;
import com.example.order.common.BusinessException;
import com.example.order.entity.User;
import com.example.order.enums.UserRole;
import com.example.order.repository.UserRepository;
import com.example.order.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 认证用例
 * 负责用户注册、登录、登出等业务逻辑
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Service
public class AuthUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * 用户注册
     *
     * @param phone    手机号
     * @param password 密码
     * @param role     用户角色
     * @return 用户 ID
     * @throws BusinessException 手机号已存在时抛出
     */
    @Transactional(rollbackFor = Exception.class)
    public Long register(String phone, String password, UserRole role) {
        // 检查手机号是否已存在
        Optional<User> existingUser = userRepository.findByPhone(phone);
        if (existingUser.isPresent()) {
            throw new BusinessException("手机号已注册");
        }

        // 创建用户
        User user = new User();
        user.setPhone(phone);
        user.setPassword(BCrypt.hashpw(password));
        user.setRole(role.getCode());
        user.setStatus(1); // 1-正常
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        int saved = userRepository.save(user);
        if (saved <= 0) {
            throw new BusinessException("注册失败");
        }

        return user.getId();
    }

    /**
     * 手机号 + 密码登录
     *
     * @param phone    手机号
     * @param password 密码
     * @return JWT Token
     * @throws BusinessException 用户不存在或密码错误时抛出
     */
    public String loginWithPassword(String phone, String password) {
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new BusinessException("用户不存在"));

        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new BusinessException("密码错误");
        }

        return jwtTokenProvider.generateToken(user.getId(), phone);
    }

    /**
     * 手机号 + 验证码登录
     *
     * @param phone       手机号
     * @param verifyCode  验证码
     * @return JWT Token
     * @throws BusinessException 验证码错误时抛出
     */
    public String loginWithSmsCode(String phone, String verifyCode) {
        // TODO: 验证短信验证码（需要短信服务）
        // 这里先简化处理，实际应该调用短信服务验证
        
        User user = userRepository.findByPhone(phone).orElse(null);
        if (user == null) {
            user = new User();
            user.setPhone(phone);
            user.setRole(UserRole.CUSTOMER.getCode());
            user.setStatus(1); // 1-正常
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
        }

        return jwtTokenProvider.generateToken(user.getId(), user.getPhone());
    }

    /**
     * 根据用户 ID 获取用户信息
     *
     * @param userId 用户 ID
     * @return 用户信息
     */
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("用户不存在"));
    }
}
