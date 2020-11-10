package com.example.dependent.Repository;

import com.example.dependent.Pojo.Dependent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DependentRepo extends JpaRepository<Dependent, Integer> {

}
