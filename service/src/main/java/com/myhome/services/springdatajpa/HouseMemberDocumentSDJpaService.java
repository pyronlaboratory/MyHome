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

package com.myhome.services.springdatajpa;

import com.myhome.domain.HouseMember;
import com.myhome.domain.HouseMemberDocument;
import com.myhome.repositories.HouseMemberDocumentRepository;
import com.myhome.repositories.HouseMemberRepository;
import com.myhome.services.HouseMemberDocumentService;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

/**
 * TODO
 */
@Service
public class HouseMemberDocumentSDJpaService implements HouseMemberDocumentService {

  private final HouseMemberRepository houseMemberRepository;
  private final HouseMemberDocumentRepository houseMemberDocumentRepository;
  @Value("${files.compressionBorderSizeKBytes}")
  private int compressionBorderSizeKBytes;
  @Value("${files.maxSizeKBytes}")
  private int maxFileSizeKBytes;
  @Value("${files.compressedImageQuality}")
  private float compressedImageQuality;

  public HouseMemberDocumentSDJpaService(HouseMemberRepository houseMemberRepository,
      HouseMemberDocumentRepository houseMemberDocumentRepository) {
    this.houseMemberRepository = houseMemberRepository;
    this.houseMemberDocumentRepository = houseMemberDocumentRepository;
  }

  /**
   * returns an Optional containing the House Member Document for a given member ID,
   * retrieved from the house Member Repository.
   * 
   * @param memberId identity of a member in a house, and it is used to retrieve the
   * corresponding House Member Document from the repository.
   * 
   * 	- `houseMemberRepository`: This is an instance of the `HouseMemberRepository`
   * class, which is likely to be a data access layer component.
   * 	- `findByMemberId()`: This is a method of the `HouseMemberRepository` class that
   * returns a stream of `HouseMember` objects filtered by their `memberId`.
   * 	- `map()`: This is a method of the `Optional` class that applies a function to
   * the contained value, in this case, the `getHouse MemberDocument` method of the
   * deserialized `HouseMember` object.
   * 
   * @returns an Optional object containing the HouseMemberDocument for the specified
   * member ID.
   * 
   * 	- `Optional<HouseMemberDocument>`: This is an optional object that contains either
   * a `HouseMemberDocument` or `null`, depending on whether a `HouseMember` with the
   * provided `memberId` exists in the database.
   * 	- `houseMemberRepository`: This is a repository interface that provides methods
   * for interacting with the `HouseMember` database table.
   * 	- `findByMemberId(String memberId)`: This method is used to retrieve a `HouseMember`
   * object from the database based on the provided `memberId`. It returns an optional
   * `House Member` object, which is then passed to the `map()` method to extract the
   * `HouseMemberDocument`.
   * 	- `getHouseMemberDocument()`: This is a method that returns the `HouseMemberDocument`
   * associated with the `HouseMember` object.
   */
  @Override
  public Optional<HouseMemberDocument> findHouseMemberDocument(String memberId) {
    return houseMemberRepository.findByMemberId(memberId)
        .map(HouseMember::getHouseMemberDocument);
  }

  /**
   * deletes a member's document from the House Member Document repository by finding
   * the corresponding member record, setting its document field to null, and saving
   * it to the repository. It returns a boolean indicating whether the operation was successful.
   * 
   * @param memberId ID of the house member whose House Member Document is to be deleted.
   * 
   * 	- `memberId`: A string representing the unique identifier for a member in a house.
   * 
   * The function first queries the `houseMemberRepository` to find the member object
   * associated with the provided `memberId`. If the `House Member Document` field of
   * the member is not null, it is set to null and then saved to the repository. Finally,
   * the function returns a boolean value indicating whether the operation was successful.
   * 
   * @returns a boolean value indicating whether the house member document was successfully
   * deleted.
   */
  @Override
  public boolean deleteHouseMemberDocument(String memberId) {
    return houseMemberRepository.findByMemberId(memberId).map(member -> {
      if (member.getHouseMemberDocument() != null) {
        member.setHouseMemberDocument(null);
        houseMemberRepository.save(member);
        return true;
      }
      return false;
    }).orElse(false);
  }

  /**
   * updates an existing House Member Document by creating a new document if one does
   * not exist, and then adding it to the member's record if successful.
   * 
   * @param multipartFile file containing the House Member Document that needs to be updated.
   * 
   * 	- `multipartFile`: A `MultipartFile` object that contains the file to be updated.
   * 	- `memberId`: A `String` representing the ID of the member whose document is being
   * updated.
   * 
   * @param memberId unique identifier of the member whose House Member Document is
   * being updated.
   * 
   * 	- `memberId`: A string representing the unique identifier for a member in the house.
   * 
   * @returns an Optional<HouseMemberDocument> containing the updated document for the
   * specified member.
   * 
   * 	- `Optional<HouseMemberDocument>` represents an optional house member document
   * that can be obtained by calling the `map` method on the `Optional` instance. If a
   * house member document is found and updated successfully, this Optional will contain
   * a non-empty `HouseMemberDocument`. Otherwise, it will be empty.
   * 	- The `findByMemberId` method of the `houseMemberRepository` returns an `Optional`
   * containing the house member with the specified `memberId`, or `None` if no such
   * member is found.
   * 	- The `tryCreateDocument` method creates a new `HouseMemberDocument` instance
   * based on the provided `multipartFile` and `member` parameters, or returns
   * `Optional.empty()` if an exception occurs.
   * 	- The `addDocumentToHouseMember` method adds the created `HouseMemberDocument`
   * to the specified `house Member`, or does nothing if the document already exists.
   */
  @Override
  public Optional<HouseMemberDocument> updateHouseMemberDocument(MultipartFile multipartFile,
      String memberId) {
    return houseMemberRepository.findByMemberId(memberId).map(member -> {
      Optional<HouseMemberDocument> houseMemberDocument = tryCreateDocument(multipartFile, member);
      houseMemberDocument.ifPresent(document -> addDocumentToHouseMember(document, member));
      return houseMemberDocument;
    }).orElse(Optional.empty());
  }

  /**
   * creates a new `HouseMemberDocument` instance based on a provided `MultipartFile`
   * and `memberId`. It first retrieves the member from the repository using the
   * `memberId`, then tries to create a new document for the member using the
   * `multipartFile`. If successful, it adds the document to the member in the repository.
   * 
   * @param multipartFile file containing the House Member's document to be created or
   * updated.
   * 
   * 	- `multipartFile`: A `MultipartFile` object representing a file uploaded by the
   * user. Its attributes may include:
   * 	+ `filename`: The name of the file being uploaded.
   * 	+ `originalFilename`: The original filename of the file before it was processed
   * by Spring.
   * 	+ `contentType`: The MIME type of the file, which can be used to determine its
   * format and potential content.
   * 	+ `size`: The size of the file in bytes.
   * 	+ `inputStream`: An `InputStream` object representing the contents of the file.
   * 
   * @param memberId unique identifier of the member whose House Member Document is
   * being created or updated.
   * 
   * 	- `memberId`: This is a string representing the unique identifier for a member
   * in the House. It is used to locate the corresponding member object in the `houseMemberRepository`.
   * 
   * @returns an Optional<HouseMemberDocument> containing a newly created or updated
   * House Member document.
   * 
   * 	- The first part of the return statement involves calling the `findByMemberId`
   * method on the `houseMemberRepository`, which returns an `Optional` containing a
   * `HouseMember` object if found, and `None` otherwise.
   * 	- The second part of the return statement maps the `member` parameter to an
   * `Optional` of type `HouseMemberDocument`. This is done using the `tryCreateDocument`
   * method, which creates a new `HouseMemberDocument` instance if the `multipartFile`
   * parameter is not `None`, and returns it in an `Optional` format.
   * 	- The third part of the return statement checks if the `houseMemberDocument`
   * Optional is non-empty. If it is, the `addDocumentToHouseMember` method is called
   * with the `document` parameter, which adds the document to the corresponding `House
   * Member`.
   * 	- The final part of the return statement returns the original `Optional` containing
   * the `HouseMemberDocument`, or an empty `Optional` if no document was created.
   */
  @Override
  public Optional<HouseMemberDocument> createHouseMemberDocument(MultipartFile multipartFile,
      String memberId) {
    return houseMemberRepository.findByMemberId(memberId).map(member -> {
      Optional<HouseMemberDocument> houseMemberDocument = tryCreateDocument(multipartFile, member);
      houseMemberDocument.ifPresent(document -> addDocumentToHouseMember(document, member));
      return houseMemberDocument;
    }).orElse(Optional.empty());
  }

  /**
   * creates a HouseMemberDocument by reading an image from a multipart file, compressing
   * it if necessary, and saving it to disk. It returns an Optional containing the
   * created document or an empty one if an error occurs.
   * 
   * @param multipartFile file that contains the image to be processed and compressed,
   * which is passed through the `getImageFromMultipartFile()` method for processing.
   * 
   * 	- `multipartFile`: A `MultipartFile` object representing a file uploaded through
   * a web form. It contains information such as the file's name, size, and content type.
   * 	- `member`: An instance of the `HouseMember` class, which represents an individual
   * member of a house. This parameter is used to identify the member whose document
   * is being created.
   * 
   * @param member HouseMember object that is associated with the document being created.
   * 
   * 	- `member`: The HouseMember object that contains information about a member of a
   * house.
   * 	- `multipartFile`: A MultipartFile object representing an image file submitted
   * by the user.
   * 	- `getImageFromMultipartFile()`: A method that retrieves the image data from the
   * MultipartFile object.
   * 	- `writeImageToByteStream()` and `compressImageToByteStream()`: Methods that write
   * or compress the retrieved image data to a ByteArrayOutputStream object.
   * 	- `saveHouseMemberDocument()`: A method that saves the compressed or uncompressed
   * image data to a file with a unique name.
   * 
   * The input parameters for the `tryCreateDocument` function are explained as follows:
   * 
   * 	- `multipartFile`: The MultipartFile object representing an image file submitted
   * by the user.
   * 	- `member`: The HouseMember object that contains information about a member of a
   * house.
   * 
   * @returns an `Optional` containing a `HouseMemberDocument`, or an empty `Optional`
   * if the image could not be processed.
   * 
   * 	- The `Optional<HouseMemberDocument>` return type indicates that the function may
   * return an empty optional if there is an error during processing or if the file
   * size exceeds the maximum allowed size.
   * 	- The `HouseMemberDocument` object is a result of saving the image to a file with
   * a name formatted using the `memberId` of the `HouseMember` parameter.
   * 	- The `saveHouseMemberDocument` method saves the image to a file in a location
   * determined by the function implementation.
   * 	- The `getImageFromMultipartFile` and `compressImageToByteStream` methods are
   * used to process the multipart file and compress the image, respectively.
   * 	- The `DataSize` class is used to represent the size of data in bytes, which is
   * used in the comparison of the file size with the maximum allowed size.
   */
  private Optional<HouseMemberDocument> tryCreateDocument(MultipartFile multipartFile,
      HouseMember member) {

    try (ByteArrayOutputStream imageByteStream = new ByteArrayOutputStream()) {
      BufferedImage documentImage = getImageFromMultipartFile(multipartFile);
      if (multipartFile.getSize() < DataSize.ofKilobytes(compressionBorderSizeKBytes).toBytes()) {
        writeImageToByteStream(documentImage, imageByteStream);
      } else {
        compressImageToByteStream(documentImage, imageByteStream);
      }
      if (imageByteStream.size() < DataSize.ofKilobytes(maxFileSizeKBytes).toBytes()) {
        HouseMemberDocument houseMemberDocument = saveHouseMemberDocument(imageByteStream,
            String.format("member_%s_document.jpg", member.getMemberId()));
        return Optional.of(houseMemberDocument);
      } else {
        return Optional.empty();
      }
    } catch (IOException e) {
      return Optional.empty();
    }
  }

  /**
   * updates a HouseMember object's `HouseMemberDocument` field with the provided
   * document, then saves the modified object to the repository.
   * 
   * @param houseMemberDocument HouseMember document to be added to the member object.
   * 
   * 	- `HouseMemberDocument`: This represents an object that contains information about
   * a member and their documents.
   * 	- `houseMemberDocument`: The instance of the `HouseMemberDocument` class that is
   * being passed to the function.
   * 	- `member`: A reference to an instance of the `HouseMember` class, which represents
   * a member of a house.
   * 	- `houseMemberRepository`: This is a repository interface for storing and retrieving
   * instances of the `House Member` class.
   * 
   * @param member House Member that the `houseMemberDocument` is associated with, and
   * it is updated to reference the newly saved document.
   * 
   * 	- `setHouseMemberDocument`: sets the `HouseMemberDocument` field of the `member`
   * object to the provided `houseMemberDocument`.
   * 
   * @returns a saved House Member document and member entity.
   * 
   * 	- `HouseMemberDocument`: This is a reference to a `HouseMemberDocument` object
   * that represents the document added to the member.
   * 	- `HouseMember`: This is a reference to a `HouseMember` object that represents
   * the member to whom the document is being added.
   * 	- `houseMemberRepository`: This is a reference to a `House Member Repository`
   * interface that provides methods for persisting and retrieving `HouseMember` objects.
   * The `save()` method is called on this repository to persist the updated `HouseMember`
   * object in the database.
   */
  private HouseMember addDocumentToHouseMember(HouseMemberDocument houseMemberDocument,
      HouseMember member) {
    member.setHouseMemberDocument(houseMemberDocument);
    return houseMemberRepository.save(member);
  }

  /**
   * saves a `HouseMemberDocument` object to the repository, creating it first from an
   * image byte stream and filename.
   * 
   * @param imageByteStream 2D image of the member that is saved as a byte array to the
   * document.
   * 
   * 	- ` ByteArrayOutputStream imageByteStream`: This is an instance of
   * `java.io.ByteArrayOutputStream`, which is used to hold the serialized House Member
   * Document data.
   * 	- `filename`: A string parameter that represents the file name of the House Member
   * Document to be saved.
   * 
   * @param filename name of the output file for the saved HouseMemberDocument.
   * 
   * 	- `filename`: A string attribute representing the name of the document being saved.
   * 	- `imageByteStream`: An `ByteArrayOutputStream` object that contains the image
   * data to be saved in the document.
   * 
   * @returns a `HouseMemberDocument` object representing the saved document.
   * 
   * 	- The HouseMemberDocument object represents a new document that has been saved
   * to the repository.
   * 	- The filename property is set to the input filename provided in the function.
   * 	- The imageByteStream property is set to the output of the `toByteArray()` method,
   * which contains the binary data of the image.
   */
  private HouseMemberDocument saveHouseMemberDocument(ByteArrayOutputStream imageByteStream,
      String filename) {
    HouseMemberDocument newDocument =
        new HouseMemberDocument(filename, imageByteStream.toByteArray());
    return houseMemberDocumentRepository.save(newDocument);
  }

  /**
   * writes a `BufferedImage` object to a byte stream using the `ImageIO.write()` method
   * with the specified image format ("jpg").
   * 
   * @param documentImage 2D image that is to be written to a byte stream as a JPEG
   * image file.
   * 
   * 	- Type: `BufferedImage`, indicating that the input is an image object that can
   * be serialized and sent over a network.
   * 	- Method: `ImageIO.write()`, which is used to write the image data to a `ByteArrayOutputStream`.
   * 	- Parameters:
   * 	+ `"jpg"`: The format of the output image file.
   * 	+ `documentImage`: The input image object that will be written to the output stream.
   * 	- Throws: `IOException`, indicating that any errors encountered during the writing
   * process will be thrown and handled by the calling code.
   * 
   * @param imageByteStream output stream where the image data will be written as a
   * JPEG file.
   * 
   * 	- `imageByteStream`: A `ByteArrayOutputStream` object that stores the byte array
   * representation of the image.
   * 	- Type: ` ByteArrayOutputStream`
   * 	- Description: A stream for converting an image into a byte array.
   */
  private void writeImageToByteStream(BufferedImage documentImage,
      ByteArrayOutputStream imageByteStream)
      throws IOException {
    ImageIO.write(documentImage, "jpg", imageByteStream);
  }

  /**
   * compresses an image using JPEG compression and writes it to a byte stream.
   * 
   * @param bufferedImage 2D image to be compressed and is used by the `ImageWriter`
   * to write the compressed image to a byte stream.
   * 
   * 	- `BufferedImage` is a class that represents an image in a buffered form, allowing
   * for efficient display and manipulation of images.
   * 	- The `ImageIO` class provides methods for reading and writing images in various
   * formats, including JPEG.
   * 	- The `ImageWriter` interface defines the methods for writing images to a file
   * or other output stream.
   * 	- The `ImageWriteParam` class defines the parameters for image writing, including
   * compression mode and quality.
   * 	- The `IIOImage` class represents an image as a stream of bytes, allowing for
   * efficient reading and writing of images.
   * 
   * @param imageByteStream byte stream where the compressed image will be written.
   * 
   * 	- `BufferedImage bufferedImage`: The original image data to be compressed.
   * 	- `ByteArrayOutputStream imageByteStream`: A byte array output stream where the
   * compressed image will be written.
   * 	- `IOException throws IOException`: An exception that may occur during compression,
   * which will be caught and handled by the method.
   * 	- `ImageIO.createImageOutputStream(imageByteStream)`: Creates an image output
   * stream from the given `imageByteStream`.
   * 	- `ImageWriter imageWriter = ImageIO.getImageWritersByFormatName("jpg").next();`:
   * Gets an instance of an image writer class that can write JPEG images.
   * 	- `ImageWriteParam param = imageWriter.getDefaultWriteParam();`: Gets the default
   * write parameters for the image writer.
   * 	- `param.canWriteCompressed()`: Checks if the image writer can write compressed
   * images.
   * 	- `param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);`: Sets the compression
   * mode to explicit.
   * 	- `param.setCompressionQuality(compressedImageQuality);`: Sets the compression
   * quality of the image.
   * 	- `imageWriter.write(null, new IIOImage(bufferedImage, null, null), param);`:
   * Writes the compressed image data to the output stream using the image writer.
   * 	- `imageWriter.dispose();`: Disposes of the image writer instance.
   */
  private void compressImageToByteStream(BufferedImage bufferedImage,
      ByteArrayOutputStream imageByteStream) throws IOException {

    try (ImageOutputStream imageOutStream = ImageIO.createImageOutputStream(imageByteStream)) {

      ImageWriter imageWriter = ImageIO.getImageWritersByFormatName("jpg").next();
      imageWriter.setOutput(imageOutStream);
      ImageWriteParam param = imageWriter.getDefaultWriteParam();

      if (param.canWriteCompressed()) {
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(compressedImageQuality);
      }
      imageWriter.write(null, new IIOImage(bufferedImage, null, null), param);
      imageWriter.dispose();
    }
  }

  /**
   * reads an image from an InputStream provided by a MultipartFile object and returns
   * a BufferedImage object.
   * 
   * @param multipartFile uploaded image file to be processed and read using `ImageIO.read()`
   * method.
   * 
   * 	- `InputStream multipartFileStream`: The input stream for the file contained in
   * the `multipartFile`.
   * 	- `ImageIO read()`: A method for reading an image from the input stream.
   * 
   * @returns a `BufferedImage` object.
   * 
   * 	- The output is a `BufferedImage`, which represents a 2D image in Java.
   * 	- The image is read from an input stream using the `ImageIO` class.
   * 	- The input stream is obtained from the `MultipartFile` object through its
   * `getInputStream()` method.
   */
  private BufferedImage getImageFromMultipartFile(MultipartFile multipartFile) throws IOException {
    try (InputStream multipartFileStream = multipartFile.getInputStream()) {
      return ImageIO.read(multipartFileStream);
    }
  }
}
