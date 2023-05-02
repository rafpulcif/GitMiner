package aiss.gitminer.controller;

import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Issue;
import aiss.gitminer.repository.CommentRepository;
import aiss.gitminer.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/issues")
public class IssueController {

    @Autowired
    IssueRepository issueRepository;

    @Autowired
    CommentRepository commentRepository;

    //GET localhost:8080/gitminer/issues
    @GetMapping
    public List<Issue> findAll(){
        return issueRepository.findAll();
    }


    //GET localhost:8080/gitminer/issues/:id
    @GetMapping("/{id}")
    public Issue findById(@PathVariable String id){
        Optional<Issue> issue = issueRepository.findById(id);
        return issue.get();
    }

    @GetMapping("/{id}/comments")
    public List<Comment> findCommentByIssueId(@PathVariable String id){

        Issue issue = issueRepository.findById(id).get();

        return issue.getComments();

    }


}
