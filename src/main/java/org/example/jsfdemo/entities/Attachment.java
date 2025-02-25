package org.example.jsfdemo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "ATTACHMENTS")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATTACH_ID_SEQ")
    @SequenceGenerator(sequenceName = "ATTACH_ID_SEQ", allocationSize = 1, name = "ATTACH_ID_SEQ")
    @Column(name = "ATTACH_ID", nullable = false)
    private Long id;

    @Size(max = 100)
    @Column(name = "FILE_NAME", length = 100)
    private String fileName;

    @Size(max = 240)
    @Column(name = "FULL_PATH", length = 240)
    private String fullPath;

    @OneToOne(mappedBy = "attach") // Bidirectional mapping
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

}