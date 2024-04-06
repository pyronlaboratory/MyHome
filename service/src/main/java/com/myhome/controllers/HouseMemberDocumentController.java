/*
 * Copyright 2020 Prathab Murugan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.myhome.controllers;

import com.myhome.api.DocumentsApi;
import com.myhome.domain.HouseMemberDocument;
import com.myhome.services.HouseMemberDocumentService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST Controller which provides endpoints for managing house member documents
 */
/**
 * TODO
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class HouseMemberDocumentController implements DocumentsApi {

  private final HouseMemberDocumentService houseMemberDocumentService;

  /**
   * retrieves a House Member Document based on the provided member ID and returns it
   * as a byte array in the response entity, along with appropriate headers to set the
   * content type and filename.
   * 
   * @param memberId ID of the member whose house member document is being requested.
   * 
   * 	- `log`: This is an instance of `org.slf4j.Logger`, which is used for logging purposes.
   * 	- `houseMemberDocumentService`: This is a service that provides access to the
   * house member document data.
   * 	- `findHouseMemberDocument`: This is a method of the `houseMemberDocumentService`
   * that retrieves a specific house member document based on its ID.
   * 	- `memberId`: This is the input parameter passed to the `findHouseMemberDocument`
   * method, which represents the ID of the house member for whom the document is being
   * retrieved.
   * 	- `document`: This is an instance of the `HouseMemberDocument` class, which
   * contains information about the house member document, including its content and filename.
   * 	- `content`: This is a field of the `document` object that represents the actual
   * content of the document, which is returned in the response entity.
   * 	- `headers`: This is an instance of `HttpHeaders`, which contains metadata about
   * the response entity, such as its cache control and content type.
   * 	- `ContentDisposition`: This is an instance of `ContentDisposition`, which contains
   * information about how the response entity should be handled by the client, including
   * its filename and whether it should be displayed inline or not.
   * 
   * @returns a response entity containing the requested house member document as a
   * byte array.
   * 
   * 	- The `HttpHeaders` object represents the HTTP headers for the response, which
   * include cache control and content type information.
   * 	- The `byte[]` variable represents the document content, which is a JPEG image
   * in this case.
   * 	- The `ContentDisposition` object represents the content disposition of the
   * response, which includes the filename of the document.
   * 	- The `ResponseEntity` object is a class that represents the HTTP response, which
   * contains the status code and headers of the response. In this case, the status
   * code is `OK`, indicating that the request was successful.
   */
  @Override
  public ResponseEntity<byte[]> getHouseMemberDocument(@PathVariable String memberId) {
    log.trace("Received request to get house member documents");
    Optional<HouseMemberDocument> houseMemberDocumentOptional =
        houseMemberDocumentService.findHouseMemberDocument(memberId);

    return houseMemberDocumentOptional.map(document -> {

      HttpHeaders headers = new HttpHeaders();
      byte[] content = document.getDocumentContent();

      headers.setCacheControl(CacheControl.noCache().getHeaderValue());
      headers.setContentType(MediaType.IMAGE_JPEG);

      ContentDisposition contentDisposition = ContentDisposition
          .builder("inline")
          .filename(document.getDocumentFilename())
          .build();

      headers.setContentDisposition(contentDisposition);

      return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  /**
   * receives a request to add a member document, creates a new house member document
   * using the received file, and returns a response entity indicating whether the
   * operation was successful or not.
   * 
   * @param memberId unique identifier of the member whose document is being uploaded.
   * 
   * 	- `memberId`: A string parameter representing the unique identifier of a member
   * in the house.
   * 
   * @param memberDocument file that contains the document of a house member to be uploaded.
   * 
   * 	- `@RequestParam("memberDocument") MultipartFile memberDocument`: This parameter
   * represents a file upload sent by the client as part of the HTTP request. The type
   * `MultipartFile` indicates that it is a file uploaded through the multipart/form-data
   * content type.
   * 	- `log.trace("Received request to add house member documents")` - This line logs
   * a trace message indicating that the function has received a request to upload a
   * house member document.
   * 
   * @returns a response entity with a status code of NO_CONTENT or NOT_FOUND, depending
   * on whether the document was successfully uploaded or not.
   * 
   * 	- `ResponseEntity`: This is an entity representing a response message, which can
   * have a status code and a body. The status code indicates the result of the operation,
   * while the body contains additional information, such as the uploaded document.
   * 	- `HttpStatus`: This is an enum representing the HTTP status code of the response.
   * In this case, it can be either `NO_CONTENT` or `NOT_FOUND`.
   * 	- `build()`: This is a method that creates a new `ResponseEntity` object based
   * on the input parameters. It returns a new entity with the specified status code
   * and body.
   */
  @Override
  public ResponseEntity uploadHouseMemberDocument(
      @PathVariable String memberId, @RequestParam("memberDocument") MultipartFile memberDocument) {
    log.trace("Received request to add house member documents");

    Optional<HouseMemberDocument> houseMemberDocumentOptional =
        houseMemberDocumentService.createHouseMemberDocument(memberDocument, memberId);
    return houseMemberDocumentOptional
        .map(document -> ResponseEntity.status(HttpStatus.NO_CONTENT).build())
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  /**
   * updates an existing house member document with the provided `memberId` and
   * `memberDocument`. If successful, it returns a `ResponseEntity` with status code
   * `NO_CONTENT`. Otherwise, it returns a `ResponseEntity` with status code `NOT_FOUND`.
   * 
   * @param memberId unique identifier of the house member whose document is being updated.
   * 
   * 	- `memberId`: This is a string parameter that represents the unique identifier
   * for a house member.
   * 	- `@PathVariable`: This annotation indicates that the value of `memberId` is
   * passed from the URL path.
   * 
   * @param memberDocument MultipartFile containing the updated house member document
   * to be processed by the `houseMemberDocumentService`.
   * 
   * 	- `memberId`: The unique identifier for a house member.
   * 	- `memberDocument`: A MultipartFile containing the updated house member document.
   * 
   * @returns a response entity with a status of NO_CONTENT or NOT_FOUND, depending on
   * whether the update was successful or not.
   * 
   * 	- `map`: This method maps the updated house member document to a `ResponseEntity`
   * object with a status code of `HttpStatus.NO_CONTENT`.
   * 	- `orElseGet`: This method provides an alternative way to return a `ResponseEntity`
   * object with a status code of `HttpStatus.NOT_FOUND` if the `map` method returns `Optional.empty()`.
   */
  @Override
  public ResponseEntity updateHouseMemberDocument(
      @PathVariable String memberId, @RequestParam("memberDocument") MultipartFile memberDocument) {
    log.trace("Received request to update house member documents");
    Optional<HouseMemberDocument> houseMemberDocumentOptional =
        houseMemberDocumentService.updateHouseMemberDocument(memberDocument, memberId);
    return houseMemberDocumentOptional
        .map(document -> ResponseEntity.status(HttpStatus.NO_CONTENT).build())
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  /**
   * deletes a house member document based on the provided `memberId`. If successful,
   * it returns a `ResponseEntity` with a `HttpStatus.NO_CONTENT`. If unsuccessful, it
   * returns a `ResponseEntity` with a `HttpStatus.NOT_FOUND`.
   * 
   * @param memberId ID of the house member whose document is to be deleted.
   * 
   * 	- `memberId`: This is the unique identifier for a member in the house.
   * 	- `houseMemberDocumentService`: This is an instance of a service class that
   * provides methods for managing house member documents.
   * 
   * @returns a `ResponseEntity` object with a status code of either `HttpStatus.NO_CONTENT`
   * or `HttpStatus.NOT_FOUND`, depending on whether the document was successfully
   * deleted or not.
   * 
   * 	- HttpStatus.NO_CONTENT: This status code indicates that the requested resource
   * has been successfully deleted and no content was returned in the response.
   * 	- HttpStatus.NOT_FOUND: This status code indicates that the specified member ID
   * could not be found, and the document deletion operation failed.
   */
  @Override
  public ResponseEntity<Void> deleteHouseMemberDocument(@PathVariable String memberId) {
    log.trace("Received request to delete house member documents");
    boolean isDocumentDeleted = houseMemberDocumentService.deleteHouseMemberDocument(memberId);
    if (isDocumentDeleted) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }
}
