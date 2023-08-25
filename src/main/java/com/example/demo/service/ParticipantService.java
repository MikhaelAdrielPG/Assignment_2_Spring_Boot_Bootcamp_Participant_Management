package com.example.demo.service;

import com.example.demo.model.Participant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParticipantService {
    private List<Participant> participants = new ArrayList<>();
    private int nextId = 0; // Mulai dari ID = 0

    // Mengambil daftar semua peserta yang aktif.
    public List<Participant> getAllParticipants() {
        List<Participant> activeParticipants = new ArrayList<>();
        for (Participant participant : participants) {
            if (participant.isActive()) {
                activeParticipants.add(participant);
            }
        }
        return activeParticipants;
    }

    // Menambahkan peserta baru.
    public boolean addParticipant(Participant participant) {
        if (!participant.isValid()) {
            return false;
        }

        if (isPhoneNumberTaken(participant.getPhoneNumber())) {
            return false;
        }

        participant.setId(nextId++);
        return participants.add(participant);
    }

    // Menghapus peserta berdasarkan ID dan ubah statusnya
    public boolean removeParticipant(int id) {
        for (Participant participant : participants) {
            if (participant.getId() == id) {
                participant.setActive(false);
                return true;
            }
        }
        return false;
    }

    // Mengupdate informasi peserta berdasarkan ID.
    public boolean updateParticipant(int id, Participant updatedParticipant) {
        if (!updatedParticipant.isValid()) {
            return false;
        }

        for (Participant participant : participants) {
            if (participant.getId() == id) {
                participant.setName(updatedParticipant.getName());
                participant.setAddress(updatedParticipant.getAddress());
                participant.setPhoneNumber(updatedParticipant.getPhoneNumber());
                return true;
            }
        }
        return false;
    }

    // Mengambil informasi peserta berdasarkan ID.
    public Participant getParticipantById(int id) {
        for (Participant participant : participants) {
            if (participant.getId() == id && participant.isActive()) {
                return participant;
            }
        }
        return null;
    }

    // Memeriksa apakah nomor telepon sudah terdaftar pada peserta lain
    private boolean isPhoneNumberTaken(String phoneNumber) {
        for (Participant participant : participants) {
            if (participant.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    // Menghapus semua peserta dengan menandai mereka sebagai tidak aktif
    public void removeAllParticipants() {
        for (Participant participant : participants) {
            participant.setActive(false);
        }
    }
}