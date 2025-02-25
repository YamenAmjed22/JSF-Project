package org.example.jsfdemo.services;

import jakarta.ejb.Local;
import org.example.jsfdemo.entities.Article;
import org.example.jsfdemo.entities.Attachment;

import java.util.List;

@Local
public interface AttachmentService {
     void addAttachment(Attachment newAttachment);
     List<Attachment> getAttachments();

}
