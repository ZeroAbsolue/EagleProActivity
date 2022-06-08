package com.example.EagleActivities;

import org.springframework.data.jpa.repository.JpaRepository;

interface ActivityRepository extends JpaRepository<Activity, Long> {
}