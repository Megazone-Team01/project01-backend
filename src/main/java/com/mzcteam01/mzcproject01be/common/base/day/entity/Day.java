package com.mzcteam01.mzcproject01be.common.base.day.entity;

import com.mzcteam01.mzcproject01be.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
@Table(name = "day")
public class Day extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 10)
    private String name;

    // 0(월)부터 6(일)까지
    @Column(name = "value", nullable = false)
    private int value;

    public void update( String name, int value ) {
        if( name != null ) this.name = name;
        if( value != -1 ) this.value = value;
    }
}
