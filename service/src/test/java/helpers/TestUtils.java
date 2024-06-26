package helpers;

import com.myhome.configuration.properties.mail.EmailTemplateLocalizationProperties;
import com.myhome.configuration.properties.mail.EmailTemplateProperties;
import com.myhome.configuration.properties.mail.MailProperties;
import com.myhome.controllers.dto.PaymentDto;
import com.myhome.controllers.dto.UserDto;
import com.myhome.domain.Amenity;
import com.myhome.domain.Community;
import com.myhome.domain.CommunityHouse;
import com.myhome.domain.HouseMember;
import com.myhome.domain.Payment;
import com.myhome.domain.User;
import com.myhome.model.HouseMemberDto;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.imageio.ImageIO;

import static helpers.TestUtils.CommunityHouseHelpers.getTestHouses;
import static helpers.TestUtils.General.generateUniqueId;
import static helpers.TestUtils.UserHelpers.getTestUsers;

/**
 * TODO
 */
public class TestUtils {

  /**
   * TODO
   */
  public static class General {

    /**
     * converts an image represented by a `BufferedImage` object into a byte array, which
     * can be used for further processing or storage.
     * 
     * @param height height of the resulting image in pixels.
     * 
     * @param width width of the resulting image in pixels.
     * 
     * @returns a byte array containing an image represented as a JPEG file.
     */
    public static byte[] getImageAsByteArray(int height, int width) throws IOException {
      BufferedImage documentImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      try (ByteArrayOutputStream imageBytesStream = new ByteArrayOutputStream()) {
        ImageIO.write(documentImage, "jpg", imageBytesStream);
        return imageBytesStream.toByteArray();
      }
    }

    /**
     * generates a unique identifier using the `UUID.randomUUID()` method, and returns
     * it as a string.
     * 
     * @returns a unique, 16-character string representing a universally unique identifier
     * (UUID) generated randomly.
     * 
     * 	- The method returns a `String` object representing a unique identifier generated
     * using the UUID random generator.
     * 	- The resulting string is always 36 characters long, consisting of a series of
     * letters and digits.
     * 	- Each character in the string is randomly selected from a set of letters (a-z)
     * and digits (0-9), ensuring that no two identifiers are the same.
     */
    public static String generateUniqueId() {
      return UUID.randomUUID().toString();
    }
  }

  /**
   * TODO
   */
  public static class CommunityHouseHelpers {

    /**
     * generats a set of `CommunityHouse` objects using a stream of generated objects
     * with unique IDs and default names, limiting the number of objects to the specified
     * count.
     * 
     * @param count number of CommunityHouse instances to generate and return in the set.
     * 
     * @returns a set of `CommunityHouse` objects generated randomly with unique IDs and
     * default names.
     * 
     * 	- The output is a `Set` of `CommunityHouse` objects.
     * 	- Each element in the set is generated using a `Stream` of anonymous inner classes,
     * with each instance having a unique `houseId` and a default `name`.
     * 	- The number of elements in the stream is limited to `count`, resulting in exactly
     * `count` `CommunityHouse` objects being added to the set.
     * 
     * The output of the function can be further analyzed by examining the properties of
     * each individual `CommunityHouse` object, such as its `houseId` and `name`.
     */
    public static Set<CommunityHouse> getTestHouses(int count) {
      return Stream
          .generate(() -> new CommunityHouse()
              .withHouseId(generateUniqueId())
              .withName("default-house-name")
          )
          .limit(count)
          .collect(Collectors.toSet());
    }

    /**
     * creates a new `CommunityHouse` instance with a unique ID and a default name.
     * 
     * @returns a new `CommunityHouse` instance with a unique ID and a default community
     * name.
     * 
     * 	- The community house is created with a unique ID generated by the function.
     * 	- The community house has a name set to "default-community-name".
     * 
     * These attributes define the basic characteristics of the community house returned
     * by the function.
     */
    public static CommunityHouse getTestCommunityHouse() {
      return new CommunityHouse()
          .withHouseId(generateUniqueId())
          .withName("default-community-name");
    }

    /**
     * creates a new `CommunityHouse` object with the specified `houseId` and sets its
     * `name` to a default value.
     * 
     * @param houseId unique identifier for the community house being created, which is
     * used to set the `houseId` field of the resulting `CommunityHouse` object.
     * 
     * 	- `houseId`: This is the identifier for the community house being created. Its
     * value can be any string.
     * 
     * @returns a new `CommunityHouse` object with the provided house ID and a default
     * community name.
     * 
     * 	- The function returns an instance of `CommunityHouse`.
     * 	- The `withHouseId` method is called on the new instance, passing in the `houseId`
     * parameter. This sets the `houseId` property of the instance to the given value.
     * 	- The `withName` method is called on the new instance, passing in the default
     * community name. This sets the `name` property of the instance to the default value.
     */
    public static CommunityHouse getTestCommunityHouse(String houseId) {
      return new CommunityHouse()
          .withHouseId(houseId)
          .withName("default-community-name");
    }
  }

  /**
   * TODO
   */
  public static class HouseMemberHelpers {

    /**
     * generates a set of `HouseMember` objects with unique IDs and default names, limited
     * to a specified count.
     * 
     * @param count maximum number of HouseMembers to be generated and returned by the function.
     * 
     * @returns a set of 3-5 HouseMember objects generated randomly.
     * 
     * 1/ The Set of HouseMembers returned is generated using a Stream that produces new
     * HouseMembers with unique IDs and default names.
     * 2/ The Stream is limited to the specified count of HouseMembers using `limit`.
     * 3/ The resulting Set of HouseMembers is collectied using `Collectors.toSet()`.
     */
    public static Set<HouseMember> getTestHouseMembers(int count) {
      return Stream
          .generate(() -> new HouseMember()
              .withMemberId(generateUniqueId())
              .withName("default-house-member-name")
          )
          .limit(count)
          .collect(Collectors.toSet());
    }
    /**
     * creates a new `HouseMember` object with a randomly generated unique ID and a default
     * name.
     * 
     * @returns a new instance of the `HouseMember` class with a randomly generated member
     * ID and a default name.
     * 
     * The `HouseMember` object returned has a `memberId` field that generates a unique
     * identifier.
     * The `Name` field is initialized to a predefined value, "default-house-member-name".
     */
    public static HouseMember getTestHouseMember() {
      return new HouseMember()
              .withMemberId(generateUniqueId())
              .withName("default-house-member-name");
    }
  }

  /**
   * TODO
   */
  public static class CommunityHelpers {

    /**
     * iterates over a range of numbers, maps each number to a new community object, and
     * returns a set of communities with at most `count` elements.
     * 
     * @param count maximum number of community objects to be generated and returned by
     * the `getTestCommunities()` method.
     * 
     * @returns a set of `Community` objects, each with a unique ID and name, generated
     * within a specified limit.
     * 
     * 	- The output is a `Set` containing multiple `Community` objects.
     * 	- Each `Community` object in the set has a unique `id` generated using the
     * `generateUniqueId()` method.
     * 	- The `name` attribute of each `Community` object is a concatenation of a default
     * community name and an index number, which is computed using the `n -> n + 1` iteration.
     * 	- The `district` attribute of each `Community` object is also a concatenation of
     * a default district name and an index number, similar to the `name` attribute.
     * 	- The `id`, `name`, and `district` attributes are all initialized to zero values.
     */
    public static Set<Community> getTestCommunities(int count) {
      return Stream.iterate(0, n -> n + 1)
          .map(index -> getTestCommunity(
              generateUniqueId(),
              "default-community-name" + index,
              "default-community-district" + index,
              0, 0)
          )
          .limit(count)
          .collect(Collectors.toSet());
    }

    /**
     * generates a new community with a unique ID, name, district, and location (coordinates).
     * 
     * @returns a `Community` object representing a fictional community with a unique ID,
     * name, district, and location.
     * 
     * 	- The returned value is of type `Community`.
     * 	- The `generateUniqueId()` method call is used to generate a unique ID for the community.
     * 	- The `default-community-name`, `default-community-district`, and `0`, `0` arguments
     * are used to set the name, district, and other properties of the community, respectively.
     */
    public static Community getTestCommunity() {
      return getTestCommunity(
          generateUniqueId(),
          "default-community-name",
          "default-community-district",
          0, 0);
    }

    /**
     * retrieves a pre-defined community object, adds it to the user's community list,
     * and sets the user as the only admin for that community.
     * 
     * @param admin user who adds the community to their list of communities.
     * 
     * 	- `User`: Represents an user account in the system.
     * 	- `admin`: A reference to an instance of the `User` class with information about
     * the user's identity and permissions.
     * 
     * @returns a new `Community` object that represents a test community.
     * 
     * 	- The `testCommunity` object is created with its default values.
     * 	- The `admin` parameter's `getCommunities()` method is used to add the `testCommunity`
     * object to the admin's community list.
     * 	- The `testCommunity.setAdmins()` method sets the `admin` object as the sole
     * administrator of the `testCommunity`.
     */
    public static Community getTestCommunity(User admin) {
      Community testCommunity = getTestCommunity();
      admin.getCommunities().add(testCommunity);
      testCommunity.setAdmins(Collections.singleton(admin));
      return testCommunity;
    }

    /**
     * creates a new community object and populates it with houses and admins, and returns
     * the community object.
     * 
     * @param communityId unique identifier for the community being created, which is
     * used to differentiate it from other communities in the system.
     * 
     * 	- `communityId`: A unique identifier for the community, which can be used to
     * distinguish it from other communities in the system.
     * 	- `communityName`: The human-readable name of the community, which can be used
     * to provide a label for the community in user interfaces or other contexts.
     * 	- `communityDistrict`: An optional attribute that specifies the district where
     * the community is located. This information can be useful for organizing communities
     * into different regions or districts within the system.
     * 
     * @param communityName name of the community being created or retrieved, which is
     * used to set the name of the new community object created by the function.
     * 
     * 	- `communityName`: This is the name of the community being created, which can be
     * any string value.
     * 	- `communityId`: This is a unique identifier for the community, represented as a
     * string.
     * 	- `communityDistrict`: This is the district where the community is located,
     * represented as a string.
     * 	- `adminsCount`: This is the number of administrators in the community, represented
     * as an integer.
     * 	- `housesCount`: This is the number of houses in the community, represented as
     * an integer.
     * 
     * @param communityDistrict district where the community is located, which is used
     * to create a unique identifier for the community in the function's return value.
     * 
     * 	- `communityName`: The name of the community.
     * 	- `communityId`: A unique identifier for the community.
     * 	- `communityDistrict`: A district within which the community is located. This
     * property can be used to filter or sort communities based on their location.
     * 	- `adminsCount`: The number of administrators for the community.
     * 	- `housesCount`: The number of houses in the community.
     * 
     * These properties provide a basic understanding of the community and its structure,
     * which can be further expanded upon depending on the context of the application.
     * 
     * @param adminsCount number of users who will be assigned as administrators to the
     * community created by the `getTestCommunity()` method.
     * 
     * @param housesCount number of houses to be associated with the community created
     * by the function.
     * 
     * @returns a new `Community` object representing a fictional community with houses
     * and admins.
     * 
     * 	- `Community testCommunity`: This is a new instance of the `Community` class,
     * created with empty sets for `members` and `objects`.
     * 	- `communityName`: The name of the community.
     * 	- `communityId`: The ID of the community.
     * 	- `communityDistrict`: The district of the community.
     * 	- `adminsCount`: The number of administrators in the community.
     * 	- `housesCount`: The number of houses in the community.
     * 	- `houses`: A set of `CommunityHouse` instances, each with a reference to the
     * `Community` instance.
     * 	- `admins`: A set of `User` instances, each with a reference to the `Community`
     * instance.
     * 
     * The function creates new instances of `CommunityHouse` and `User`, and sets their
     * `Community` references to the returned `Community` instance. It also adds the
     * `Community` instance to the `admins` set of each `User` instance, and adds the
     * `Community` instance to the `houses` set of each `CommunityHouse` instance.
     */
    public static Community getTestCommunity(String communityId, String communityName, String communityDistrict, int adminsCount, int housesCount) {
      Community testCommunity = new Community(
          new HashSet<>(),
          new HashSet<>(),
          communityName,
          communityId,
          communityDistrict,
          new HashSet<>()
      );
      Set<CommunityHouse> communityHouses = getTestHouses(housesCount);
      communityHouses.forEach(house -> house.setCommunity(testCommunity));
      Set<User> communityAdmins = getTestUsers(adminsCount);
      communityAdmins.forEach(user -> user.getCommunities().add(testCommunity));

      testCommunity.setHouses(communityHouses);
      testCommunity.setAdmins(communityAdmins);
      return testCommunity;
    }
  }

  /**
   * TODO
   */
  public static class AmenityHelpers {

    /**
     * creates a new `Amenity` object with a specified `amenityId` and `description`, and
     * also links it to a test `Community`.
     * 
     * @param amenityId identifier for the amenity being created.
     * 
     * 	- `amenityId`: This is a String property that represents the unique identifier
     * for the amenity.
     * 	- `amenityDescription`: This is a String property that provides a brief description
     * of the amenity.
     * 
     * @param amenityDescription description of the amenity being created.
     * 
     * 	- `amenityId`: A unique identifier for the amenity, which is passed as an argument
     * in the function call.
     * 	- `amenityDescription`: A string representing a brief description of the amenity,
     * which can be used to display information about the amenity to users.
     * 	- `community`: An instance of `CommunityHelpers.getTestCommunity()`, which
     * represents the community that the amenity belongs to.
     * 
     * @returns a new `Amenity` object with specified `amenityId`, `amenityDescription`,
     * and `community`.
     * 
     * 	- `amenityId`: A string representing the unique identifier for the amenity.
     * 	- `amenityDescription`: A string describing the amenity's purpose or usage.
     * 	- `community`: An instance of the `CommunityHelpers` class, which represents a
     * test community.
     */
    public static Amenity getTestAmenity(String amenityId, String amenityDescription) {
      return new Amenity()
          .withAmenityId(amenityId)
          .withDescription(amenityDescription)
          .withCommunity(CommunityHelpers.getTestCommunity());
    }

    /**
     * generates a set of `Amenity` objects with unique identifiers and default names and
     * descriptions, limited to a specified count using `Stream` API and `collect` method.
     * 
     * @param count maximum number of amenities to return in the set, which is generated
     * using a stream of anonymous objects.
     * 
     * @returns a set of 3-5 randomly generated amenity objects with unique IDs and default
     * names and descriptions.
     * 
     * 	- The output is a `Set` of `Amenity` objects.
     * 	- Each `Amenity` object in the set has an `amenityId` field generated using `generateUniqueId()`.
     * 	- Each `Amenity` object has a `name` field set to "default-amenity-name".
     * 	- Each `Amenity` object has a `description` field set to "default-amenity-description".
     */
    public static Set<Amenity> getTestAmenities(int count) {
      return Stream
          .generate(() -> new Amenity()
              .withAmenityId(generateUniqueId())
              .withName("default-amenity-name")
              .withDescription("default-amenity-description")
          )
          .limit(count)
          .collect(Collectors.toSet());
    }

  }

  /**
   * TODO
   */
  public static class UserHelpers {

    /**
     * creates a set of `User` objects with unique names, emails, and passwords, and
     * limits the number of elements to the input `count`.
     * 
     * @param count number of user instances to be generated and returned by the
     * `getTestUsers()` method.
     * 
     * @returns a set of 3 to 4 user objects, generated randomly and limited to the
     * specified count.
     * 
     * 1/ The Set<User> return type indicates that the function returns a collection of
     * User objects.
     * 2/ The Stream API is used to generate a sequence of User objects using an initial
     * value of 0 and incrementing it by 1 for each iteration.
     * 3/ The map() method applies a transformation to each element in the sequence,
     * creating new User objects with unique names, emails, and passwords.
     * 4/ The limit() method is used to limit the number of User objects returned to the
     * specified count.
     * 5/ Collect() method is used to collect the generated User objects into a Set<User>.
     * 
     * Overall, the function returns a set of mock user objects with unique identifiers,
     * emails, and passwords.
     */
    public static Set<User> getTestUsers(int count) {
      return Stream.iterate(0, n -> n + 1)
          .map(index -> new User(
              "default-user-name" + index,
              generateUniqueId(),
              "default-user-email" + index,
              false,
              "default-user-password" + index,
              new HashSet<>(),
              new HashSet<>())
          )
          .limit(count)
          .collect(Collectors.toSet());
    }
  }

  /**
   * TODO
   */
  public static class MailPropertiesHelper {

    /**
     * creates a new instance of the `MailProperties` class with specified values for
     * host, username, password, port, protocol, debug and dev mode, and returns the instance.
     * 
     * @returns a `MailProperties` object with customized properties for testing purposes.
     * 
     * 	- `host`: The hostname of the mail server.
     * 	- `username`: The username to use for authentication with the mail server.
     * 	- `password`: The password to use for authentication with the mail server.
     * 	- `port`: The port number used for communication with the mail server.
     * 	- `protocol`: The protocol used for communication with the mail server, such as
     * SMTP or IMAP.
     * 	- `debug`: A flag indicating whether debugging is enabled for the mail connection.
     * 	- `devMode`: A flag indicating whether the mail connection is in development mode.
     */
    public static MailProperties getTestMailProperties() {
      MailProperties testMailProperties = new MailProperties();
      testMailProperties.setHost("test host");
      testMailProperties.setUsername("test username");
      testMailProperties.setPassword("test password");
      testMailProperties.setPort(0);
      testMailProperties.setProtocol("test protocol");
      testMailProperties.setDebug(false);
      testMailProperties.setDevMode(false);
      return testMailProperties;
    }

    /**
     * creates an instance of `EmailTemplateProperties` and sets its properties to "test
     * path", "test encoding", "test mode", and "false" for caching.
     * 
     * @returns an `EmailTemplateProperties` object with customized properties for testing
     * purposes.
     * 
     * 	- Path: The setPath() method sets the path to a specific location where the email
     * template file is stored.
     * 	- Encoding: The setEncoding() method sets the encoding type used for the email
     * template file.
     * 	- Mode: The setMode() method sets the mode or type of the email template file.
     * 	- Cache: The setCache() method sets whether the email template file should be
     * cached or not.
     */
    public static EmailTemplateProperties getTestMailTemplateProperties() {
      EmailTemplateProperties testMailTemplate = new EmailTemplateProperties();
      testMailTemplate.setPath("test path");
      testMailTemplate.setEncoding("test encoding");
      testMailTemplate.setMode("test mode");
      testMailTemplate.setCache(false);
      return testMailTemplate;
    }

    /**
     * creates a new instance of `EmailTemplateLocalizationProperties` and sets its
     * properties to "test path", "test encoding", and "0" seconds for caching.
     * 
     * @returns an `EmailTemplateLocalizationProperties` object containing customized
     * localization settings for email templates.
     * 
     * 	- `setPath()`: Sets the path to the localized email templates.
     * 	- `setEncoding()`: Specifies the encoding of the localized email templates.
     * 	- `setCacheSeconds()`: Configures the caching duration for the localized email
     * templates in seconds.
     */
    public static EmailTemplateLocalizationProperties getTestLocalizationMailProperties() {
      EmailTemplateLocalizationProperties testTemplatesLocalization = new EmailTemplateLocalizationProperties();
      testTemplatesLocalization.setPath("test path");
      testTemplatesLocalization.setEncoding("test encodig");
      testTemplatesLocalization.setCacheSeconds(0);
      return testTemplatesLocalization;
    }
  }

  /**
   * TODO
   */
  public static class PaymentHelpers {

    /**
     * constructs a `PaymentDto` object with various parameters such as charge amount,
     * payment type, description, recurring status, due date, and administrator and member
     * information.
     * 
     * @param charge amount to be charged for the payment.
     * 
     * 	- `BigDecimal charge`: A large decimal value representing the amount of money
     * charged for the payment.
     * 	- `type`: A string indicating the type of payment (e.g., "invoice", "payment", etc.).
     * 	- `description`: A string providing a brief description of the payment.
     * 	- `recurring`: A boolean indicating whether the payment is recurring (true) or
     * one-time (false).
     * 	- `dueDate`: A LocalDate object representing the date the payment is due.
     * 	- `admin`: An instance of `UserDto` representing the administrator responsible
     * for the payment.
     * 	- `member`: An instance of `HouseMemberDto` representing the member who made the
     * payment.
     * 
     * @param type type of payment, which is used to determine the appropriate fields to
     * include in the resulting `PaymentDto`.
     * 
     * 	- `charge`: A `BigDecimal` object representing the amount to be charged for the
     * payment.
     * 	- `type`: An immutable `String` object specifying the type of payment, such as
     * "invoice" or "credit note".
     * 	- `description`: A `String` object providing a brief description of the payment.
     * 	- `recurring`: A `boolean` indicating whether the payment is recurring or not.
     * 	- `dueDate`: An instance of `LocalDate` representing the date when the payment
     * is due.
     * 	- `admin`: An instance of `UserDto` representing the administrator who created
     * the payment.
     * 	- `member`: An instance of `HouseMemberDto` representing the member for whom the
     * payment is made.
     * 
     * @param description description of the payment in the `PaymentDto`.
     * 
     * 	- `description`: A string attribute that represents the payment description
     * provided by the user.
     * 	- `type`: An optional string attribute that specifies the type of payment (e.g.,
     * "invoice", "credit card", etc.).
     * 	- `recurring`: A boolean attribute that indicates whether the payment is recurring
     * or not.
     * 	- `dueDate`: A local date attribute that represents the due date of the payment.
     * 
     * @param recurring payment as recurring, which means it will be charged repeatedly
     * at the specified interval.
     * 
     * @param dueDate date when the payment is due, as represented by a LocalDate object.
     * 
     * 	- `dueDate.toString()`: This property returns the string representation of the
     * `LocalDate` object, which can be used for further processing or storage purposes.
     * 
     * @param admin UserDto object containing information about the user who made the
     * payment, and it is passed to the builder to include the user's details in the
     * PaymentDto object.
     * 
     * 	- `admin`: The UserDto object represents an administrator for whom the payment
     * is made. It has various attributes such as `id`, `username`, `password`, and `email`.
     * 
     * @param member HouseMemberDto object containing information about the member whose
     * payment is being processed.
     * 
     * 	- `admin`: A `UserDto` object representing the user who made the payment.
     * 	- `dueDate`: A `LocalDate` object representing the date when the payment is due.
     * 	- `description`: A string representing a brief description of the payment.
     * 	- `member`: A `HouseMemberDto` object representing the member for whom the payment
     * was made. The member's properties include:
     * 	+ `id`: An integer representing the member's ID.
     * 	+ `name`: A string representing the member's name.
     * 
     * @returns a `PaymentDto` object built with the provided parameters.
     * 
     * 	- `charge`: A `BigDecimal` object representing the amount to be charged.
     * 	- `type`: An optional string indicating the type of payment (e.g., "invoice").
     * 	- `description`: An optional string providing additional context for the payment
     * (e.g., a project name).
     * 	- `recurring`: A boolean indicating whether the payment is recurring.
     * 	- `dueDate`: The date when the payment is due, represented as a `LocalDate` object.
     * 	- `admin`: An instance of the `UserDto` class representing the admin associated
     * with the payment.
     * 	- `member`: An instance of the `HouseMemberDto` class representing the member
     * associated with the payment.
     */
    public static PaymentDto getTestPaymentDto(BigDecimal charge, String type, String description, boolean recurring, LocalDate dueDate, UserDto admin, HouseMemberDto member) {

      return PaymentDto.builder()
          .charge(charge)
          .type(type)
          .description(description)
          .recurring(recurring)
          .dueDate(dueDate.toString())
          .admin(admin)
          .member(member)
          .build();
    }
    /**
     * creates a mock payment object with all fields except 'recurring' set to null and
     * 'recurring' set to false.
     * 
     * @returns a Payment object with all fields null or false.
     * 
     * 	- `payment`: This is null, indicating that no payment information was provided.
     * 	- `recurring`: This is false, meaning that the payment is not recurring.
     * 	- `amount`: This is null, indicating that no amount was provided for the payment.
     * 	- `currency`: This is null, indicating that no currency was provided for the payment.
     * 	- `description`: This is null, indicating that no description was provided for
     * the payment.
     * 	- `dateTime`: This is null, indicating that no date and time were provided for
     * the payment.
     * 	- `status`: This is null, indicating that the payment status is not provided.
     * 	- `errorMessage`: This is null, indicating that there is no error message associated
     * with the payment.
     */
    public static Payment getTestPaymentNullFields() {
      //Only 'recurring' field will be not null, but false
      return new Payment(
          null,
          null,
          null,
          null,
          false,
          null,
          null,
          null);
    }
  }
}
