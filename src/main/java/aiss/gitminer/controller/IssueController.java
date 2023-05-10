package aiss.gitminer.controller;

import aiss.gitminer.exception.GitMinerException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Issue;
import aiss.gitminer.repository.CommentRepository;
import aiss.gitminer.repository.IssueRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "GitMiner Issues", description = "GitMiner issues management API")
@RestController
@RequestMapping("/gitminer/issues")
public class IssueController {

    @Autowired
    IssueRepository issueRepository;

    @Autowired
    CommentRepository commentRepository;

    @Operation(
            summary = "Retrieve all issues",
            description = "Get all issues ",
            tags = {"get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = { @Content(schema = @Schema(implementation = Issue.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema())})
    })
    //GET localhost:8080/gitminer/issues
    @GetMapping
    public List<Issue> findAll(){
        return issueRepository.findAll();
    }


    
    //GET localhost:8080/gitminer/issues/:id
    @Operation(
            summary = "Retrieve an issue",
            description = "Get one issue by specifying its Id",
            tags = {"get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = { @Content(schema = @Schema(implementation = Issue.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    public Issue findById(@Parameter(description = "id of the issue to be searched") @PathVariable String id) throws GitMinerException {
        Optional<Issue> issue = issueRepository.findById(id);
        if(!issue.isPresent()){
            throw new GitMinerException();
        }
        return issue.get();
    }

    @Operation(
            summary = "Retrieve all issue comments",
            description = "Get all comments from an issue by specifying the issue Id",
            tags = {"get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = { @Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema())})
    })
    // GET localhost:8080/gitminer/issues/:id/comments
    @GetMapping("/{id}/comments")
    public List<Comment> findCommentByIssueId(@Parameter(description = "id of issue whose comments are searched") @PathVariable String id) throws GitMinerException{
        Optional<Issue> issue = issueRepository.findById(id);
        if(!issue.isPresent()){
            throw new GitMinerException();
        }
        return issue.get().getComments();
    }

    @Operation(
            summary = "Retrieve all author issues",
            description = "Get all author issues by specifying the author Id",
            tags = {"get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = { @Content(schema = @Schema(implementation = Issue.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema())})
    })
    // GET localhost:8080/gitminer/issues?author=:authorId
    @GetMapping(params = "id")
    public List<Issue> findByAuthor(@Parameter(description = "id of the issues' author") @RequestParam (name = "id") String authorId){
        List<Issue> issues = issueRepository.findAll();
        List<Issue> issuesByAuthor = issues.stream().filter(issue -> issue.getAuthor().getId().equals(authorId)).toList();
        return issuesByAuthor;
    }

    //GET localhost:8080/gitminer/issues?state=open/close
    @Operation(
            summary = "Retrieve all issues by state",
            description = "Get all issues whose state equals the state parameter specified",
            tags = {"get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = { @Content(schema = @Schema(implementation = Issue.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema())})
    })
    @GetMapping(params = "state")
    public List<Issue> findIssueByState(@Parameter(description = "state of the issues to be searched") @RequestParam (name = "state") String state){
        List<Issue> issues = issueRepository.findAll();
        List<Issue> issuesByState = issues.stream().filter(issue -> issue.getState().equals(state)).toList();
        return issuesByState;
    }
}
