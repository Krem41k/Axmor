package com.testAxmor.issue.repo;

import com.testAxmor.issue.models.Issue;
import org.springframework.data.repository.CrudRepository;

public interface IssueRepository extends CrudRepository<Issue, Long>{

}
