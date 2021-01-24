package com.kgisl.am.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kgisl.am.models.RssFeed;

@Repository
public interface RssFeedRepository extends JpaRepository<RssFeed, Integer> {

}
