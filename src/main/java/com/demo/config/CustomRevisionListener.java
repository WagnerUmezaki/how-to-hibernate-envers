package com.demo.config;

import com.demo.model.CustomRevisionEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.RevisionListener;

@Slf4j
public class CustomRevisionListener implements RevisionListener  {

    @Override
    public void newRevision(Object revisionEntity) {
        final CustomRevisionEntity customRevision = (CustomRevisionEntity) revisionEntity;
        log.info("Revision Created: " + customRevision.getRev());
    }
}
