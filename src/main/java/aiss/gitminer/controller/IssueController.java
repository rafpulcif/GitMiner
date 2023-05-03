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

    // GET localhost:8080/gitminer/issues/:id/comments
    @GetMapping("/{id}/comments")
    public List<Comment> findCommentByIssueId(@PathVariable String id){
        Issue issue = issueRepository.findById(id).get();
        return issue.getComments();
    }

    // GET localhost:8080/gitminer/issues?author=:authorId
    @GetMapping(params = "id")
    public List<Issue> findByAuthor(@RequestParam (name = "id") String authorId){
        List<Issue> issues = issueRepository.findAll();
        List<Issue> issuesByAuthor = issues.stream().filter(issue -> issue.getAuthor().getId().equals(authorId)).toList();
        return issuesByAuthor;
    }

    //GET localhost:8080/gitminer/issues?state=open/close
    @GetMapping(params = "state")
    public List<Issue> findIssueByState(@RequestParam (name = "state") String state){
        List<Issue> issues = issueRepository.findAll();
        List<Issue> issuesByState = issues.stream().filter(issue -> issue.getState().equals(state)).toList();
        return issuesByState;
    }
}
