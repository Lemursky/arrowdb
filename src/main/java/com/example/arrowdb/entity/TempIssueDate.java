package com.example.arrowdb.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Component
public class TempIssueDate {

    private LocalDate tIssueDate;

    @Bean
    @Scope("prototype")
    public LocalDate getTIssueDate() {
        return tIssueDate;
    }

    @Bean
    @Scope("prototype")
    public void setTIssueDate(LocalDate tIssueDate) {
        this.tIssueDate = tIssueDate;
    }
}
