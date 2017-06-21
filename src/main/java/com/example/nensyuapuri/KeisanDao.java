package com.example.nensyuapuri;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class KeisanDao {

    @Autowired
    private JdbcTemplate jdbc;
    
    public List<Person>findAll(){ 
        return jdbc.query("select * from person", new BeanPropertyRowMapper<>(Person.class));        
    }
    
/*    public List<Person>findByAge(int age){
        
        return jdbc.queryForList("select * from person where age=?",20);
    }
*/    
}
