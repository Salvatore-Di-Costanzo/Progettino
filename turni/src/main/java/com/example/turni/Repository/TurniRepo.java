package com.example.turni.Repository;

import com.example.turni.Pojo.Turni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

public interface TurniRepo extends JpaRepository<Turni, Integer> {
}
