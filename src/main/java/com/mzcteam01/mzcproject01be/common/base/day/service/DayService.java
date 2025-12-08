package com.mzcteam01.mzcproject01be.common.base.day.service;

import com.mzcteam01.mzcproject01be.common.base.day.entity.Day;
import com.mzcteam01.mzcproject01be.common.base.day.repository.DayRepository;
import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DayService {
    private final DayRepository dayRepository;

    @Transactional
    public void create( String name, int value ){
        Day temp = dayRepository.findByValue( value ).orElse(null);
        if( temp != null ) throw new CustomException("중복된 요일 value는 존재할 수 없습니다" );
        Day day = Day.builder().name(name).value(value).build();
        dayRepository.save(day);
    }

    @Transactional
    public void update( int id, String name, int value ){
        Day day = dayRepository.findById(id).orElseThrow( () -> new CustomException("잘못된 접근입니다") );
        Day temp = dayRepository.findByValue( value ).orElse(null);
        if( temp != null ) throw new CustomException("중복된 요일 value는 존재할 수 없습니다" );
        day.update( name, value );
    }

    public Map<String, Integer> findAll() {
        List<Day> days = dayRepository.findAll();
        Map<String, Integer> result = new HashMap<>();
        for( Day day : days )result.put( day.getName(), day.getValue() );
        return result;
    }

    public void delete( int id, int deletedBy ){
        Day day = dayRepository.findById(id).orElseThrow( () -> new CustomException("잘못된 접근입니다") );
        day.delete( deletedBy );
    }

    // value to name
     public String valueToName( int value ){
        Day day = dayRepository.findByValue( value ).orElseThrow(
                () -> new CustomException("해당하는 요일이 없습니다")
        );
        return day.getName();
     }
     public int nameToValue( String name ){
        Day day = dayRepository.findByName( name ).orElseThrow(
                () -> new CustomException("해당하는 요일이 없습니다")
        );
        return day.getValue();
     }
}
