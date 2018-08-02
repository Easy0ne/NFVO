package org.openbaton.nfvo.api.catalogue;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import org.openbaton.catalogue.nfvo.images.NFVImage;
import org.openbaton.exceptions.*;
import org.openbaton.nfvo.core.interfaces.NFVImageManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/** Created by tbr on 30.07.18. */
@RestController
@RequestMapping("/api/v1/nfvimage")
public class RestNFVImage {
  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired Gson gson;

  @Autowired private NFVImageManagement nfvImageManagement;

  /**
   * @param nfvImage
   * @param projectId
   * @return
   * @throws NotFoundException
   * @throws BadFormatException
   * @throws NetworkServiceIntegrityException
   * @throws CyclicDependenciesException
   * @throws EntityInUseException
   * @throws BadRequestException
   * @throws IOException
   * @throws AlreadyExistingException
   * @throws PluginException
   * @throws IncompatibleVNFPackage
   * @throws VimException
   * @throws InterruptedException
   * @throws EntityUnreachableException
   */
  @RequestMapping(
    method = RequestMethod.POST,
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(
    value = "Adding an NFVImage to the NFVO's NFV image repository",
    notes = "POST request with NFVImage as JSON content of the request body"
  )
  public NFVImage create(
      @RequestBody @Valid JsonObject nfvImage,
      @RequestHeader(value = "project-id") String projectId)
      throws BadFormatException {

    NFVImage nfvImage1;
    try {
      nfvImage1 = gson.fromJson(nfvImage, NFVImage.class);
    } catch (Exception e) {
      log.error("The passed NFVImage is not valid: " + e.getMessage());
      throw new BadFormatException("The passed NFVImage is not valid: " + e.getMessage());
    }
    nfvImage1.setInImageRepo(true);
    nfvImage1.setProjectId(projectId);
    nfvImage1.setExtId(null);
    nfvImage1.setId(null);
    nfvImage1.setHbVersion(null);

    return nfvImageManagement.add(nfvImage1);
  }

  @ApiOperation(
    value = "Get all NSDs from a project",
    notes = "Returns all Network Service Descriptors onboarded in the project with the specified id"
  )
  @RequestMapping(method = RequestMethod.GET)
  public List<NFVImage> findAll(@RequestHeader(value = "project-id") String projectId) {
    return (List<NFVImage>) nfvImageManagement.queryByProjectId(projectId);
  }

  @ApiOperation(
    value = "Delete an NFVImage from the NFV image repository",
    notes = "DELETE request for removing the NFVImage with the passed ID"
  )
  @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(
      @PathVariable("id") String id, @RequestHeader(value = "project-id") String projectId)
      throws WrongStatusException, EntityInUseException, BadRequestException, NotFoundException {
    nfvImageManagement.delete(id, projectId);
  }
}
