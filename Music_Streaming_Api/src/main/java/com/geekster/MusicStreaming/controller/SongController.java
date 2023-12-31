package com.geekster.MusicStreaming.controller;

import com.geekster.MusicStreaming.model.Song;
import com.geekster.MusicStreaming.service.SongService;
import com.geekster.MusicStreaming.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Repository
@RequestMapping("songs")
public class SongController {

    @Autowired
    TokenService authService;

    @Autowired
    SongService songService;

    @PostMapping()
    public ResponseEntity<String> addSongs(@RequestBody List<Song> songList , @RequestParam String email , @RequestParam String token){
        HttpStatus status;
        String msg=null;

        if(authService.authenticate(email,token))
        {
            msg = songService.addSong(token , songList);
            status = HttpStatus.CREATED;
        }
        else
        {
            msg = "Invalid User";
            status = HttpStatus.FORBIDDEN;
        }

        return new ResponseEntity<String>(msg , status);
    }

    @GetMapping()
    public ResponseEntity<List<Song>> getAllSongs(@Nullable String songName , @Nullable String genre ,@RequestParam String email , @RequestParam String token){
        HttpStatus status;
        List<Song> songList = null ;

        if(authService.authenticate(email,token))
        {
            songList =  songService.getSong(token , email , songName , genre);
            status = HttpStatus.OK;
        }
        else
        {

            status = HttpStatus.FORBIDDEN;
        }

        return new ResponseEntity<List<Song>>( songList , status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSong(@PathVariable Long id ,@RequestParam String email , @RequestParam String token){
        HttpStatus status;
        String msg = "";

        if(authService.authenticate(email,token))
        {
            msg =  songService.deleteSong(token , id);
            status = HttpStatus.NO_CONTENT;
        }
        else
        {
            msg = "Invalid user";
            status = HttpStatus.FORBIDDEN;
        }

        return new ResponseEntity<String>( msg , status);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSong(@PathVariable Long id ,@RequestParam String email , @RequestParam String token , @RequestBody Song song) {
        HttpStatus status;
        String msg = "";

        if(authService.authenticate(email,token))
        {
            msg =  songService.updateSong(token , id , song);
            status = HttpStatus.OK;
        }
        else
        {
            msg = "Invalid user";
            status = HttpStatus.FORBIDDEN;
        }

        return new ResponseEntity<String>( msg , status);
    }

}
