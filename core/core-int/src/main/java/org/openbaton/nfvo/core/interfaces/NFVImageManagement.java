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

package org.openbaton.nfvo.core.interfaces;

import org.openbaton.catalogue.nfvo.images.NFVImage;
import org.openbaton.exceptions.NotFoundException;

/** This interface is only responsible for managing NFVImages. */
public interface NFVImageManagement {

  /**
   * This method adds an NFVImage to the NfvImageRepository.
   *
   * @param NFVImage
   * @return
   */
  NFVImage add(NFVImage NFVImage);

  /**
   * This method removes an NFVImage from the NfvImageRepository.
   *
   * @param id
   */
  void delete(String id);

  /**
   * This method removes an NFVImage from the NfvImageRepository and checks if the image has the
   * given project ID.
   *
   * @param id
   */
  void delete(String id, String projectId) throws NotFoundException;

  /**
   * This method updates an existing NFVImage.
   *
   * @param new_NFV_image
   * @param id
   * @return
   */
  NFVImage update(NFVImage new_NFV_image, String id);

  /**
   * This method returns all the NFVImages.
   *
   * @return
   */
  Iterable<NFVImage> query();

  /**
   * This method returns an NFVImage specified by the ID.
   *
   * @param id
   * @return
   */
  NFVImage query(String id);

  /**
   * This method returns all the NFVImages with a specific project ID.
   *
   * @param projectId
   * @return
   */
  Iterable<NFVImage> queryByProjectId(String projectId);

  /** This method allows copying images from a VIM to another. */
  void copy();
}
