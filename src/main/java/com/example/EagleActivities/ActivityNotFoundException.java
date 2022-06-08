package com.example.EagleActivities;

class ActivityNotFoundException extends RuntimeException {
     ActivityNotFoundException(Long id) {
        super("Could not find activity "+id);
    }
}
