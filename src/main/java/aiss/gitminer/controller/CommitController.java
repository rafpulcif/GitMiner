package aiss.gitminer.controller;

import aiss.gitminer.model.Commit;
import aiss.gitminer.repository.CommitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/commits")
public class CommitController {

    @Autowired
    CommitRepository commitRepository;

    //GET localhost:8080/gitminer/commits
    @GetMapping
    public List<Commit> findAll(){
        return commitRepository.findAll();
    }

    //GET localhost:8080/gitminer/commits/:id
    @GetMapping("/{id}")
    public Commit findOne(@PathVariable String id){
        Optional<Commit> commit = commitRepository.findById(id);
        return commit.get();
    }
}
