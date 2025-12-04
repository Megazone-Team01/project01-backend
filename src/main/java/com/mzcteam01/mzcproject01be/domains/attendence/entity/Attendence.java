
package com.mzcteam01.mzcproject01be.domains.attendence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.security.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "attendence")
public class Attendence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Timestamp check_in;
    private Timestamp check_out;
    /*
        organization table, 조인
     */
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "organization_id")
//    private Organization organization;

    /*
    * User table와 조인, 외래키
    * */
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
}
