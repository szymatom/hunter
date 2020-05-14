package com.threehunter.repository;

import com.threehunter.entity.ThreeEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreeHunterRepository extends JpaRepository<ThreeEntity, String> {
}
