package com.example.nensyuapuri;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class KeisanDao {

    @Autowired
    private JdbcTemplate jdbc;
    
    public List<Map<String,Object>>findAll(){
        
        return jdbc.queryForList("select * from person");
        
    }
    
    public List<Map<String,Object>>findByAge(int age){
        
        return jdbc.queryForList("select * from person where age=?",age);
    }
}
