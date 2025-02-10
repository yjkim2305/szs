package com.szs.szsyoungjunkim.user.infrastructure.entity;

import com.szs.szsyoungjunkim.common.converter.EncryptorConverter;
import com.szs.szsyoungjunkim.common.domain.entity.BaseTimeEntity;
import com.szs.szsyoungjunkim.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Convert(converter = EncryptorConverter.class)
    @Column(nullable = false)
    private String regNo;

    @Builder
    private UserEntity(String userId, String password, String name, String regNo) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.regNo = regNo;
    }

    public static UserEntity toEntity(User user) {
        return UserEntity.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .name(user.getName())
                .regNo(user.getRegNo())
                .build();
    }
}
