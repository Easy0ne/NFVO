package org.openbaton.nfvo.repositories;

import java.util.List;
import org.openbaton.catalogue.nfvo.images.NFVImage;
import org.springframework.data.repository.CrudRepository;

public interface NFVImageRepository extends CrudRepository<NFVImage, String> {
  List<NFVImage> findAllByProjectIdAndIsInImageRepoIsTrue(String projectId);

  List<NFVImage> findAllByNameAndProjectIdAndIsInImageRepoIsTrue(String name, String projectId);

  List<NFVImage> findAllByProjectId(String projectId);

  NFVImage findOneByIdAndProjectId(String id, String projectId);
}
