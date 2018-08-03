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

import org.openbaton.catalogue.nfvo.Configuration;
import org.openbaton.exceptions.NotFoundException;

/** Created by lto on 13/05/15. */
public interface ConfigurationManagement {

  /**
   * This operation allows adding a configuration into the configuration repository.
   *
   * @param configuration the configuration to add
   * @return the added configuration
   */
  Configuration add(Configuration configuration);

  /**
   * This operation allows deleting the configuration from the configuration repository.
   *
   * @param id the ID of the configuration to remove
   */
  void delete(String id);

  /**
   * This operation allows updating a configuration in the configuration repository.
   *
   * @param newConfiguration the updated configuration
   * @param id the ID of the configuration to update
   * @param projectId the ID of the project
   * @return the updated configuration
   * @throws NotFoundException if the configuration to update does not exist
   */
  Configuration update(Configuration newConfiguration, String id, String projectId)
      throws NotFoundException;

  /**
   * This operation allows retrieving all configurations from the configuration repository.
   *
   * @return all the configurations
   */
  Iterable<Configuration> query();

  /**
   * This operation allows retrieving all configurations belonging to a specific project from the
   * configuration repository.
   *
   * @param projectId the ID of the project
   * @return all the configurations belonging to the specified project
   */
  Iterable<Configuration> queryByProject(String projectId);

  /**
   * This operation allows retrieving a specific configuration from the configuration repository.
   *
   * @param id the ID of the requested configuration
   * @param projectId the ID of the project
   * @return the configuration
   */
  Configuration query(String id, String projectId);

  /**
   * This operation allows retrieving a specific configuration by name.
   *
   * @param name the name of the requested configuration
   * @return the configuration
   * @throws NotFoundException if no configuration with this name exists
   */
  Configuration queryByName(String name) throws NotFoundException;
}
