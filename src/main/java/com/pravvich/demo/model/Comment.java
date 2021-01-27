package com.pravvich.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jv_comment")
public class Comment {

    @Id
    @Column(name = "major_id")
    private Long id;

    @Column(name = "text")
    private String text;

}
