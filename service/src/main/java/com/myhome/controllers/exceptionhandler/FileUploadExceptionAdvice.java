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

package com.myhome.controllers.exceptionhandler;

import java.io.IOException;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * TODO
 */
@ControllerAdvice
public class FileUploadExceptionAdvice {

  /**
   * handles the `MaxUploadSizeExceededException` by returning a response entity with
   * an error message.
   * 
   * @param exc MaxUploadSizeExceededException object that is passed to the function
   * as an exception.
   * 
   * 	- `class`: The class of the exception, which in this case is `MaxUploadSizeExceededException`.
   * 	- `message`: A string attribute of the exception that contains a message indicating
   * the file size exceeds the limit.
   * 
   * @returns a response entity with a status code of PAYLOAD_TOO_LARGE and a body
   * containing a message indicating that the file size exceeds the limit.
   * 
   * 	- The status code of the response entity is `HttpStatus.PAYLOAD_TOO_LARGE`.
   * 	- The body of the response entity contains a map with a single key-value pair,
   * where the key is `"message"` and the value is a string containing the error message
   * `"File size exceeds limit!"`.
   */
  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity handleMaxSizeException(MaxUploadSizeExceededException exc) {
    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(new HashMap<String, String>() {{
      put("message", "File size exceeds limit!");
    }});
  }

  /**
   * is an exception handler for the `MaxUploadSizeExceededException` thrown by the
   * service. It returns a response entity with a status code of `CONFLICT` and a message
   * body containing an error message.
   * 
   * @param exc `MaxUploadSizeExceededException` that occurs when the uploaded file
   * exceeds the maximum allowed size.
   * 
   * 	- `MaxUploadSizeExceededException`: The class of the exception that was handled.
   * 	- `exc`: The deserialized instance of the exception class, providing information
   * about the specific error that occurred during document saving.
   * 
   * @returns a `ResponseEntity` object with a status code of `HttpStatus.CONFLICT` and
   * a body containing a map with a single entry containing the message "Something went
   * wrong with document saving!".
   * 
   * 	- The status code of the response entity is `HttpStatus.CONFLICT`, indicating
   * that something went wrong with the document saving process.
   * 	- The body of the response entity contains a map with a single key-value pair,
   * where the key is "message" and the value is a string containing an error message
   * related to the document saving process.
   */
  @ExceptionHandler(IOException.class)
  public ResponseEntity handleIOException(MaxUploadSizeExceededException exc) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(new HashMap<String, String>() {{
      put("message", "Something go wrong with document saving!");
    }});
  }
}

