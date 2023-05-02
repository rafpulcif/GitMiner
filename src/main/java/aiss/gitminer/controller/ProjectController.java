package aiss.gitminer.controller;

import aiss.gitminer.model.Project;
import aiss.gitminer.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/projects")
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

    //GET http://localhost:8080/gitminer/projects
    @GetMapping
    public List<Project> findAll(){
        return projectRepository.findAll();
    }

    //GET http://localhost:8080/gitminer/projects/:id
    @GetMapping("/{id}")
    public Project findOne(@PathVariable String id){
        Optional<Project> project = projectRepository.findById(id);
        return project.get();
    }

    //POST http://localhost:8080/gitminer/projects ;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Project create(@Valid @RequestBody Project project ){
        Project newProject = projectRepository
                .save(new Project(project.getId(),project.getName(),project.getWebUrl(),project.getCommits(),project.getIssues()));
        return newProject;
    }




}
