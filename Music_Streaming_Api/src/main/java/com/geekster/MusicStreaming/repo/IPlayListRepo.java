package com.geekster.MusicStreaming.repo;

import com.geekster.MusicStreaming.model.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlayListRepo extends JpaRepository<PlayList , Long> {
}
