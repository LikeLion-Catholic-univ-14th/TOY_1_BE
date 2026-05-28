package com.example.meetpick.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ai_results")
public class AIResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meeting meeting;

    @Column(nullable = false)
    private int priority;

    @Column(nullable = false, length = 50)
    private String recommendedDate;

    @Column(nullable = false)
    private int availableCount;

    @Column(columnDefinition = "TEXT")
    private String aiReason;
}
