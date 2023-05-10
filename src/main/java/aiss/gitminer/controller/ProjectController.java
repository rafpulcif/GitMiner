package aiss.gitminer.controller;

import aiss.gitminer.exception.GitMinerException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.ProjectRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
@Tag(name="GitMiner Projects", description = "GitMiner projects management API")
@RestController
@RequestMapping("/gitminer/projects")
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

    //GET http://localhost:8080/gitminer/projects
    @Operation(summary = "Retrieve all projects"  ,description = "Get all projects" ,tags = {"projects","get"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(schema = @Schema(implementation = Project.class ),mediaType = "application/json")})
    })
    @GetMapping
    public List<Project> findAll(){
        return projectRepository.findAll();
    }

    //GET http://localhost:8080/gitminer/projects/:id

    @Operation(summary = "Retrieve project by id"  ,description = "Get project by id" ,tags = {"get"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(schema = @Schema(implementation = Project.class ),mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",description = "Not Found", content = {
                    @Content(schema = @Schema())
            })
    })
    @GetMapping("/{id}")
    public Project findOne(@Parameter(description = "id of the project to be searched") @PathVariable String id) throws GitMinerException{
        Optional<Project> project = projectRepository.findById(id);
        if(!project.isPresent()){
            throw new GitMinerException();
        }
        return project.get();
    }

    //POST http://localhost:8080/gitminer/projects ;
    @Operation(summary = "Inject project"  ,description = "Load project to GitMiner whose data is passed as body request in JSON format " ,tags = {"post"} )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created", content = {
                    @Content(schema = @Schema(implementation = Project.class ),mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",description = "Bad Request", content = {
                    @Content(schema = @Schema())
            })
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Project create(@Valid @RequestBody Project project ){
        Project newProject = projectRepository
                .save(new Project(project.getId(),project.getName(),project.getWebUrl(),project.getCommits(),project.getIssues()));
        return newProject;
    }




}
