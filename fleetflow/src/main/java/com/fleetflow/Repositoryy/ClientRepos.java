package com.fleetflow.Repositoryy;


import com.fleetflow.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepos extends JpaRepository<Client, Long> {
}
