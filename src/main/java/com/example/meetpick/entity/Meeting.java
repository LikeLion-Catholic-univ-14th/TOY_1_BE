package com.example.meetpick.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "meetings")
public class Meeting {

    @Id
    private String meetingId;

    @Column(nullable = false)
    private String title;

    private int maxParticipants;

    @CreationTimestamp
    private LocalDateTime createdAT;
}
