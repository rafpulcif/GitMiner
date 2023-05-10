package aiss.gitminer.controller;

import aiss.gitminer.exception.GitMinerException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.repository.CommentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@Tag(name="GitMiner comments",description = "Gitminer comments management API")
@RestController
@RequestMapping("gitminer/comments")
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Operation(summary = "Retrieve all comments"  ,description = "Get all comments" ,tags = {"get"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(schema = @Schema(implementation = Comment.class ),mediaType = "application/json")})
    })
    @GetMapping
    public List<Comment> findAll(){
        return commentRepository.findAll();
    }

    @Operation(summary = "Retrieve comment by id"  ,description = "Get comment by id" ,tags = {"get"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(schema = @Schema(implementation = Comment.class ),mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",description = "Not Found", content = {
                    @Content(schema = @Schema())
            })
    })
    @GetMapping("/{id}")
    public Comment findById(@Parameter(description = "id of the comment to be searched") @PathVariable String id) throws GitMinerException {
        Optional<Comment> comment = commentRepository.findById(id);
        if(!comment.isPresent()){
            throw new GitMinerException();
        }
        return comment.get();
    }
}
