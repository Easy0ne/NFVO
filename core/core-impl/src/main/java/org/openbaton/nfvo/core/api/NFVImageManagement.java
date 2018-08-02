/*
 * Copyright (c) 2016 Open Baton (http://www.openbaton.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.openbaton.nfvo.core.api;

import java.util.Date;
import org.openbaton.catalogue.nfvo.images.NFVImage;
import org.openbaton.exceptions.NotFoundException;
import org.openbaton.nfvo.repositories.NFVImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope
public class NFVImageManagement implements org.openbaton.nfvo.core.interfaces.NFVImageManagement {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired private NFVImageRepository nfvImageRepository;

  @Override
  public NFVImage add(NFVImage NFVImage) {
    log.trace("Adding image " + NFVImage);
    log.debug("Adding image with name " + NFVImage.getName());
    return nfvImageRepository.save(NFVImage);
  }

  @Override
  public void delete(String id) {
    log.debug("Removing image with ID " + id);
    nfvImageRepository.delete(id);
  }

  @Override
  public void delete(String id, String projectId) throws NotFoundException {
    log.debug("Removing image with ID " + id + " from project with ID " + projectId);
    if (nfvImageRepository.findOneByIdAndProjectId(id, projectId) != null)
      nfvImageRepository.delete(id);
    else
      throw new NotFoundException(
          "NFVImage with ID " + id + " was not found in project with ID " + projectId);
  }

  @Override
  public NFVImage update(NFVImage nfvImage, String id) {
    nfvImage = nfvImageRepository.save(nfvImage);
    nfvImage.setUpdated(new Date());
    return nfvImage;
  }

  @Override
  public Iterable<NFVImage> query() {
    return nfvImageRepository.findAll();
  }

  @Override
  public NFVImage query(String id) {
    return nfvImageRepository.findOne(id);
  }

  @Override
  public Iterable<NFVImage> queryByProjectId(String projectId) {
    return nfvImageRepository.findAllByProjectId(projectId);
  }

  @Override
  public void copy() {
    throw new UnsupportedOperationException();
  }
}
