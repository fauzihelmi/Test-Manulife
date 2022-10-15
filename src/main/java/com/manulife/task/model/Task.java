package com.manulife.task.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Task")
public class Task {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", columnDefinition = "varchar DEFAULT uuid_generate_v4()")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Boolean status;
}
