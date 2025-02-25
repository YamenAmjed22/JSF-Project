package org.example.jsfdemo.services;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.jsfdemo.entities.Attachment;

import java.util.List;
@Stateless
public class AttachmentServiceImpl implements AttachmentService {
    @PersistenceContext(unitName = "TOBY_PU")
    private EntityManager entityManager;

    @Override
    public void addAttachment(Attachment newAttachment) {
        entityManager.persist(newAttachment);
    }

    @Override
    public List<Attachment> getAttachments() {
        return entityManager.createQuery("SELECT a FROM Attachment a", Attachment.class).getResultList();
    }
}
