package com.example.EagleActivities;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
class ActivityController {
private final ActivityRepository repository;
private final  ActivityModelAssembler assembler;

    public ActivityController(ActivityRepository repository, ActivityModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @Operation(summary = "Get all activities")
    @GetMapping("/activities")
    CollectionModel<EntityModel<Activity>> all(){
    List<EntityModel<Activity>> activities = repository.findAll().stream().map(assembler::toModel)
            .collect(Collectors.toList());
        return  CollectionModel.of(activities,linkTo(methodOn(ActivityController.class).all()).withSelfRel());
    }

    @Operation(summary = "save a new activity")
    @PostMapping("/activities")
    ResponseEntity<?> storeNewActivity(@RequestBody Activity newActivity){
        EntityModel<Activity> activityEntityModel  = assembler.toModel(repository.save(newActivity));

        return ResponseEntity
            .created(activityEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(activityEntityModel);
    }

    @Operation(summary = "get an activity")
    @GetMapping("/activities/{id}")
    EntityModel<Activity> findById(@PathVariable Long id){
        Activity activity = repository.findById(id).orElseThrow(()->new ActivityNotFoundException(id));
        return  assembler.toModel(activity);
    }

    @Operation(summary = "replace the information of the old activity with the details of the new one")
    @PutMapping("/activities/{id}")
    ResponseEntity<?> replaceActivity(@RequestBody Activity newActivity, @PathVariable Long id) {

        Activity replaceActivity = repository.findById(id)
                .map(activity -> {
                    activity.setType(newActivity.getType());
                    activity.setDescription(newActivity.getDescription());
                    activity.setStartDate(newActivity.getStartDate());
                    activity.setEndDate(newActivity.getEndDate());
                    return repository.save(activity);
                })
                .orElseGet(() -> {
                    newActivity.setId(id);
                    return repository.save(newActivity);
                });

        EntityModel<Activity> activityEntityModel = assembler.toModel(replaceActivity);
        return ResponseEntity
                .created(activityEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(activityEntityModel);
    }

    @Operation(summary = "delete an activity")
    @DeleteMapping("/activities/{id}")
    ResponseEntity<?> deleteActivity(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
