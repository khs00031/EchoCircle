package com.luna.echocircle.member.entity;//    package com.luna.echocircle.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "member", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "nickname")
})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mid")
    private long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "nickname", unique = true, nullable = false)
    private String nickname;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
