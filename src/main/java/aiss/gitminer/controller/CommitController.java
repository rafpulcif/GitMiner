package aiss.gitminer.controller;

import aiss.gitminer.exception.GitMinerException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Commit;
import aiss.gitminer.repository.CommitRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name="GitMiner Commits", description = "GitMiner commits management API")
@RestController
@RequestMapping("/gitminer/commits")
public class CommitController {

    @Autowired
    CommitRepository commitRepository;

    //GET localhost:8080/gitminer/commits

    @Operation(summary = "Retrieve all comments"  ,description = "Get all comments" ,tags = {"get"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(schema = @Schema(implementation = Commit.class ),mediaType = "application/json")})
    })
    @GetMapping
    public List<Commit> findAll(){
        return commitRepository.findAll();
    }

    //GET localhost:8080/gitminer/commits/:id
    @Operation(summary = "Retrieve commit by id"  ,description = "Get commit by id" ,tags = {"get"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(schema = @Schema(implementation = Commit.class ),mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",description = "Not Found", content = {
                    @Content(schema = @Schema())
            })
    })
    @GetMapping("/{id}")
    public Commit findOne(@Parameter(description = "id of the commit to be searched")@PathVariable String id) throws GitMinerException {
        Optional<Commit> commit = commitRepository.findById(id);
        if(!commit.isPresent()){
            throw new GitMinerException();
        }
        return commit.get();
    }

    // GET localhost:8080/gitminer/commits?email=customer1@customer.com

    @Operation(summary = "Retrieve commit by author email"  ,description = "Get commit by author email" ,tags = {"get"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(schema = @Schema(implementation = Commit.class ),mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",description = "Not Found", content = {
                    @Content(schema = @Schema())
            })
    })
    @GetMapping(params = "author_email")
    public List<Commit> findCommitByEmail(@Parameter(description = "email of the author of the commits to be searched") @RequestParam (name = "author_email") String email){

        List<Commit> commits = commitRepository.findAll();
        List<Commit> commitsByEmail = commits.stream().filter(commit -> commit.getAuthorEmail().equals(email)).toList();
        return commitsByEmail;
    }
}
