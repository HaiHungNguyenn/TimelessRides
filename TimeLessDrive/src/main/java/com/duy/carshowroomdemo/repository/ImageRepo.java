package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<Test, String> {
}
