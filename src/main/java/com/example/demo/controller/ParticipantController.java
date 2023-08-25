package com.example.demo.controller;

import com.example.demo.model.ApiResponse;
import com.example.demo.model.Participant;
import com.example.demo.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/participants")
public class ParticipantController {
    @Autowired
    private ParticipantService participantService;

    // Menampilkan semua data peserta
    @GetMapping("")
    public ResponseEntity getAllParticipants() {
        List<Participant> participants = participantService.getAllParticipants();

        if (participants.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(new ApiResponse("Participant not found.", null));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(participants);
        }
    }

    // Menampilkan data peserta berdasarkan id
    @GetMapping("/{id}")
    public ResponseEntity getParticipantById(@PathVariable int id) {
        Participant participant = participantService.getParticipantById(id);
        if (participant != null) {
            return ResponseEntity.status(HttpStatus.OK).body(participant);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(new ApiResponse("Participant not found.", null));
        }
    }

    // Menambahkan data peserta (bisa 1 data atau banyak data)
    @PostMapping("")
    public ResponseEntity addParticipants(@RequestBody List<Participant> participants) {

        List<String> validationErrors = new ArrayList<>();
        List<String> successMessages = new ArrayList<>();

        for (Participant participant : participants) {
            if (!participant.isValid()) {
                validationErrors.add("Invalid participant information for participant: "
                        + participant.getName());
            } else {
                if (participantService.addParticipant(participant)) {
                    successMessages.add("Participant added successfully: "
                            + participant.getName());
                } else {
                    validationErrors.add("Failed to add participant: "
                            + participant.getName());
                }
            }
        }

        if (!validationErrors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(new ApiResponse(String.join("\n", validationErrors), null));
        }

        if (!successMessages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).
                    body(new ApiResponse(String.join("\n", successMessages), null));
        }
        return null;
    }

    // Menghapus data peserta berdasarkan id
    @DeleteMapping("/{id}")
    public ResponseEntity removeParticipant(@PathVariable int id) {
        if (participantService.removeParticipant(id)) {
            return ResponseEntity.status(HttpStatus.OK).
                    body(new ApiResponse("Participant removed successfully.", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(new ApiResponse("Participant not found.", null));
        }
    }

    // Mengupdate data peserta berdasarkan id
    @PutMapping("/{id}")
    public ResponseEntity updateParticipant(@PathVariable int id, @RequestBody Participant updatedParticipant) {
        if (!updatedParticipant.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body("Invalid participant information.");
        }

        if ( participantService.updateParticipant(id, updatedParticipant)) {
            return ResponseEntity.status(HttpStatus.OK).
                    body(new ApiResponse("Participant updated successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(new ApiResponse("Participant not found.", null));
        }
    }

    // Menghapus semua data peserta
    @DeleteMapping("")
    public ResponseEntity removeAllParticipants() {
        if (participantService.getAllParticipants().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(new ApiResponse("No participants found.", null));
        }

        participantService.removeAllParticipants();
        return ResponseEntity.status(HttpStatus.OK).
                body(new ApiResponse("All participant removed.", null));
    }
}