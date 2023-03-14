package com.niit.stackroute.repository;

import com.niit.stackroute.domain.workspace.Workspace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceRepository extends MongoRepository<Workspace,String> {
    public List<Workspace> findByEmail(String email);
    Workspace findBySpaceID(String spaceId);
}
