package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.entities.Playlist;
import com.example.demo.entities.Song;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongService;

@Controller
public class PlaylistController {
	
	@Autowired
	SongService songService;
	@Autowired
	PlaylistService playlistService;

	@GetMapping("/createPlaylist")
	public String createPlaylist(Model model) {
		List<Song> songList =songService.fetchSongs();
		model.addAttribute("songs",songList);
		return "createPlaylist";
	}
	
	@GetMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist) {
		List<Song> songList =songService.fetchSongs();
		playlistService.addPlaylist(playlist);	
		for(Song s: songList) {
			s.getPlaylists().add(playlist);
			songService.updateSong(s);
		}
		
		return "adminHome";
	}
	
	@GetMapping("/viewPlaylist")
	public String viewPlaylist(Model model) {
		List<Playlist> allPlaylists=playlistService.fetchAllPlaylists();
		model.addAttribute("allPlaylists", allPlaylists);
	    return "displayPlaylists";
	}
}
