package com.example.EagleActivities;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ActivityControllerTest {
    private ActivityRepository activityRepository;
    private ActivityModelAssembler activityModelAssembler;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Autowired
    private final ActivityController activityController = new ActivityController(activityRepository,activityModelAssembler);

    @Test
    void all() {
        CollectionModel<EntityModel<Activity>> activities = activityController.all();
        assertFalse(activities.getContent().isEmpty());
    }

    @Test
    void storeNewActivity() {
       Activity newActivity = instanciateAnActivity();
       ResponseEntity<?> responseActivityEntity = activityController.storeNewActivity(newActivity);
       EntityModel<Activity> activityEntityModel = (EntityModel<Activity>) responseActivityEntity.getBody();
       Activity actualActivity = activityEntityModel.getContent();
       assertEquals(201, responseActivityEntity.getStatusCodeValue());
       assertEquals(newActivity,actualActivity);
    }

    @Test
    void findById() {
        Activity expectedActivity = new Activity(
                "Operation sql",
                "Activite pour la methode find",
                LocalDateTime.parse("2022-09-05 05:06:00",formatter),
                LocalDateTime.parse("2022-10-05 05:06:00",formatter));
        expectedActivity.setId(1L);

        EntityModel<Activity> activityEntityModel =activityController.findById(1L);
        Activity actualActivity = activityEntityModel.getContent();

        assertEquals(expectedActivity.toString(),actualActivity.toString());

    }


    @Test
     void findByIdCatchExcpetion(){
        long activityId = 10L;
        Exception exception = assertThrows(ActivityNotFoundException.class,()->{
        Activity expectedActivity = new Activity();
            expectedActivity.setId(activityId);

        EntityModel<Activity> activityEntityModel =activityController.findById(expectedActivity.getId());
        Activity actualActivity = activityEntityModel.getContent();

        });

        assertEquals("Could not find activity "+activityId,exception.getMessage());

    }

    @Test
    void replaceActivity() {
        Activity expectedActivity = instanciateAnActivity();
        expectedActivity.setId(3L);
        ResponseEntity<?> responseActivityEntity = activityController.replaceActivity(expectedActivity,3L);
        EntityModel<Activity> activityEntityModel = (EntityModel<Activity>) responseActivityEntity.getBody();
        Activity actualActivity = activityEntityModel.getContent();
        assertEquals(expectedActivity.toString(),actualActivity.toString());
    }

    @Test
    void deleteActivity() {
        ResponseEntity<?> responseActivityEntity = activityController.deleteActivity(2L);
        assertEquals(204,responseActivityEntity.getStatusCodeValue());
    }

    private  Activity instanciateAnActivity(){
        return  new Activity(
                "Sondage",
                "Activite de test",
                LocalDateTime.parse("2022-05-24 05:06:00",formatter),
                LocalDateTime.parse("2022-10-24 05:06:00",formatter));
    }
}