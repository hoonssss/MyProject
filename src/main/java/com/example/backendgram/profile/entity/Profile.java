package com.example.backendgram.profile.entity;

import com.example.backendgram.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String title;

    @Column(name = "profile_image")
    private String profileImage;

    @Column
    private String introduction;

    public void setProfileImage(String imageName) {
        this.profileImage = imageName;
    }
}