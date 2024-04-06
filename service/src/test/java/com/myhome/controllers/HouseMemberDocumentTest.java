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

import com.myhome.domain.HouseMemberDocument;
import com.myhome.services.HouseMemberDocumentService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * TODO
 */
class HouseMemberDocumentTest {

  private static final String MEMBER_ID = "test-member-id";

  private static final MockMultipartFile MULTIPART_FILE =
      new MockMultipartFile("memberDocument", new byte[0]);
  private static final HouseMemberDocument MEMBER_DOCUMENT =
      new HouseMemberDocument(MULTIPART_FILE.getName(), new byte[0]);

  @Mock
  private HouseMemberDocumentService houseMemberDocumentService;

  @InjectMocks
  private HouseMemberDocumentController houseMemberDocumentController;

  /**
   * initializes Mockito annotations for the current object, enabling mocking of dependencies.
   */
  @BeforeEach
  private void init() {
    MockitoAnnotations.initMocks(this);
  }

  /**
   * tests the House Member Document Controller's ability to retrieve a house member
   * document successfully. It does this by providing an ID and verifying that the
   * correct document is returned with the correct content type and status code.
   */
  @Test
  void shouldGetDocumentSuccess() {
    // given
    given(houseMemberDocumentService.findHouseMemberDocument(MEMBER_ID))
        .willReturn(Optional.of(MEMBER_DOCUMENT));
    // when
    ResponseEntity<byte[]> responseEntity =
        houseMemberDocumentController.getHouseMemberDocument(MEMBER_ID);
    //then
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(MEMBER_DOCUMENT.getDocumentContent(), responseEntity.getBody());
    assertEquals(MediaType.IMAGE_JPEG, responseEntity.getHeaders().getContentType());
    verify(houseMemberDocumentService).findHouseMemberDocument(MEMBER_ID);
  }

  /**
   * verifies that the `getHouseMemberDocument` method returns a `HttpStatus.NOT_FOUND`
   * response when the document for a given member cannot be found in the service.
   */
  @Test
  void shouldGetDocumentFailure() {
    // given
    given(houseMemberDocumentService.findHouseMemberDocument(MEMBER_ID))
        .willReturn(Optional.empty());
    // when
    ResponseEntity<byte[]> responseEntity =
        houseMemberDocumentController.getHouseMemberDocument(MEMBER_ID);
    //then
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    verify(houseMemberDocumentService).findHouseMemberDocument(MEMBER_ID);
  }

  /**
   * tests the `uploadHouseMemberDocument` method by verifying that it returns a
   * `NO_CONTENT` status code and calls the underlying `createHouseMemberDocument`
   * method with the provided file and member ID.
   */
  @Test
  void shouldPostDocumentSuccess() {
    // given
    given(houseMemberDocumentService.createHouseMemberDocument(MULTIPART_FILE, MEMBER_ID))
        .willReturn(Optional.of(MEMBER_DOCUMENT));
    // when
    ResponseEntity<byte[]> responseEntity =
        houseMemberDocumentController.uploadHouseMemberDocument(MEMBER_ID, MULTIPART_FILE);
    //then
    assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    verify(houseMemberDocumentService).createHouseMemberDocument(MULTIPART_FILE, MEMBER_ID);
  }

  /**
   * tests the scenario where the house member document service returns an empty optional
   * when creating a new document, and the `uploadHouseMemberDocument` controller method
   * returns a `HttpStatus.NOT_FOUND` response along with the verification of the service
   * method call.
   */
  @Test
  void shouldPostDocumentFailureNotFound() {
    // given
    given(houseMemberDocumentService.createHouseMemberDocument(MULTIPART_FILE, MEMBER_ID))
        .willReturn(Optional.empty());
    // when
    ResponseEntity<byte[]> responseEntity =
        houseMemberDocumentController.uploadHouseMemberDocument(MEMBER_ID, MULTIPART_FILE);
    //then
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    verify(houseMemberDocumentService).createHouseMemberDocument(MULTIPART_FILE, MEMBER_ID);
  }

  /**
   * tests the updateHouseMemberDocument method's functionality by calling it with a
   * valid multipart file and member ID, verifying the HTTP status code and invoking
   * the underlying House Member Document Service method to update the document.
   */
  @Test
  void shouldPutDocumentSuccess() {
    // given
    given(houseMemberDocumentService.updateHouseMemberDocument(MULTIPART_FILE, MEMBER_ID))
        .willReturn(Optional.of(MEMBER_DOCUMENT));
    // when
    ResponseEntity<byte[]> responseEntity =
        houseMemberDocumentController.updateHouseMemberDocument(MEMBER_ID, MULTIPART_FILE);
    //then
    assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    verify(houseMemberDocumentService).updateHouseMemberDocument(MULTIPART_FILE, MEMBER_ID);
  }

  /**
   * verifies that when the `houseMemberDocumentController.updateHouseMemberDocument()`
   * method is called with a non-existent member ID, it returns a `HttpStatus.NOT_FOUND`
   * response and calls the `houseMemberDocumentService.updateHouseMemberDocument()`
   * method with the given file and member ID.
   */
  @Test
  void shouldPutDocumentFailureNotFound() {
    // given
    given(houseMemberDocumentService.updateHouseMemberDocument(MULTIPART_FILE, MEMBER_ID))
        .willReturn(Optional.empty());
    // when
    ResponseEntity<byte[]> responseEntity =
        houseMemberDocumentController.updateHouseMemberDocument(MEMBER_ID, MULTIPART_FILE);
    //then
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    verify(houseMemberDocumentService).updateHouseMemberDocument(MULTIPART_FILE, MEMBER_ID);
  }

  /**
   * tests whether deleting a house member document returns a successful response with
   * a HTTP status code of `NO_CONTENT`.
   */
  @Test
  void shouldDeleteDocumentSuccess() {
    // given
    given(houseMemberDocumentService.deleteHouseMemberDocument(MEMBER_ID))
        .willReturn(true);
    // when
    ResponseEntity responseEntity =
        houseMemberDocumentController.deleteHouseMemberDocument(MEMBER_ID);
    //then
    assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    verify(houseMemberDocumentService).deleteHouseMemberDocument(MEMBER_ID);
  }

  /**
   * verifies that deleting a house member document fails when the document is not found.
   */
  @Test
  void shouldDeleteDocumentFailureNotFound() {
    // given
    given(houseMemberDocumentService.deleteHouseMemberDocument(MEMBER_ID))
        .willReturn(false);
    // when
    ResponseEntity responseEntity =
        houseMemberDocumentController.deleteHouseMemberDocument(MEMBER_ID);
    //then
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    verify(houseMemberDocumentService).deleteHouseMemberDocument(MEMBER_ID);
  }
}
