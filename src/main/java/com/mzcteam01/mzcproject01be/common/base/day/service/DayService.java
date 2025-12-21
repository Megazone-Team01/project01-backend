package com.mzcteam01.mzcproject01be.common.base.day.service;

import com.mzcteam01.mzcproject01be.common.base.day.entity.Day;
import com.mzcteam01.mzcproject01be.common.base.day.repository.DayRepository;
import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.common.exception.DayErrorCode;
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
    public void create( String name, int value, int createdBy ){
        Day temp = dayRepository.findByValue( value ).orElse(null);
        if( temp != null ) throw new CustomException(DayErrorCode.DAY_VALUE_ALREADY_EXIST.getMessage());
        Day day = Day.builder().name(name).value(value).createdBy(createdBy).build();
        dayRepository.save(day);
    }

    @Transactional
    public void update( int id, String name, int value ){
        Day day = dayRepository.findById(id).orElseThrow( () -> new CustomException(DayErrorCode.INVALID_DAY_ACCESS.getMessage()) );
        Day temp = dayRepository.findByValue( value ).orElse(null);
        if( temp != null ) throw new CustomException( DayErrorCode.DAY_VALUE_ALREADY_EXIST.getMessage() );
        day.update( name, value );
    }

    public Map<String, Integer> findAll() {
        List<Day> days = dayRepository.findAll();
        Map<String, Integer> result = new HashMap<>();
        for( Day day : days )result.put( day.getName(), day.getValue() );
        return result;
    }

    public void delete( int id, int deletedBy ){
        Day day = dayRepository.findById(id).orElseThrow( () -> new CustomException(DayErrorCode.INVALID_DAY_ACCESS.getMessage()) );
        day.delete( deletedBy );
    }

    // value to name
     public String valueToName( int value ){
        Day day = dayRepository.findByValue( value ).orElseThrow(
                () -> new CustomException(DayErrorCode.DAY_NOT_FOUND.getMessage())
        );
        return day.getName();
     }
     public int nameToValue( String name ){
        Day day = dayRepository.findByName( name ).orElseThrow(
                () -> new CustomException(DayErrorCode.DAY_NOT_FOUND.getMessage())
        );
        return day.getValue();
     }
}
