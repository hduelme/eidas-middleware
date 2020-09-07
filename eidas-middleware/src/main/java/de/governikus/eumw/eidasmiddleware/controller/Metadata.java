/*
 * Copyright (c) 2020 Governikus KG. Licensed under the EUPL, Version 1.2 or as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence"); You may not use this work except
 * in compliance with the Licence. You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl Unless required by applicable law or agreed to in writing,
 * software distributed under the Licence is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */

package de.governikus.eumw.eidasmiddleware.controller;

import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.governikus.eumw.eidascommon.ContextPaths;
import de.governikus.eumw.eidasmiddleware.ConfigHolder;
import de.governikus.eumw.poseidas.service.MetadataService;
import lombok.extern.slf4j.Slf4j;


/**
 * Servlet implementation class Metadata
 */
@Slf4j
@Controller
@RequestMapping(ContextPaths.EIDAS_CONTEXT_PATH + ContextPaths.METADATA)
public class Metadata
{
  private final ConfigHolder configHolder;

  private final MetadataService metadataService;

  public Metadata(MetadataService metadataService, ConfigHolder configHolder)
  {
    this.metadataService = metadataService;
    this.configHolder = configHolder;
  }

  /**
   * Return the SAML Metadata for this middleware
   */
  @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<String> sendMetadata()
  {
    // at this endpoint metadata is only available when signed
    if (!configHolder.isDoSignMetadata())
    {
      return new ResponseEntity<>(HttpStatus.GONE);
    }
    byte[] out = metadataService.getMetadata();
    if (ArrayUtils.isEmpty(out))
    {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(new String(out, StandardCharsets.UTF_8), HttpStatus.OK);
  }
}
