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

import com.myhome.controllers.dto.PaymentDto;
import com.myhome.controllers.dto.mapper.PaymentMapper;
import com.myhome.domain.HouseMember;
import com.myhome.domain.Payment;
import com.myhome.domain.User;
import com.myhome.repositories.HouseMemberRepository;
import com.myhome.repositories.PaymentRepository;
import com.myhome.repositories.UserRepository;
import com.myhome.services.PaymentService;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Implements {@link PaymentService} and uses Spring Data JPA Repository to do its work
 */
/**
 * TODO
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentSDJpaService implements PaymentService {
  private final PaymentRepository paymentRepository;
  private final UserRepository adminRepository;
  private final PaymentMapper paymentMapper;
  private final HouseMemberRepository houseMemberRepository;

  /**
   * 1) generates a payment ID and 2) creates a payment instance in the repository.
   * 
   * @param request PaymentDto object that contains the details of the payment to be scheduled.
   * 
   * 1/ `generatePaymentId`: The method `generatePaymentId(request)` is called to
   * generate a unique payment ID for the scheduled payment.
   * 2/ `createPaymentInRepository`: The method `createPaymentInRepository(request)`
   * creates a new payment object in the repository, which stores the payment details.
   * 
   * @returns a payment DTO containing the scheduled payment details.
   * 
   * 	- `PaymentDto request`: This is the input parameter passed to the function,
   * containing information about the payment to be scheduled.
   * 	- `generatePaymentId(request)`: This is a custom method called within the
   * `schedulePayment` function to generate a unique identifier for the payment.
   * 	- `createPaymentInRepository(request)`: This is another custom method called
   * within the `schedulePayment` function, which creates a new payment object in the
   * repository based on the input parameters provided.
   */
  @Override
  public PaymentDto schedulePayment(PaymentDto request) {
    generatePaymentId(request);
    return createPaymentInRepository(request);
  }

  /**
   * retrieves payment details from the repository and maps them to a `PaymentDto`
   * object using the provided mapping function.
   * 
   * @param paymentId ID of a payment that is being retrieved, and it is used to locate
   * the corresponding payment data in the repository.
   * 
   * 	- `paymentRepository`: A repository object that is responsible for storing and
   * retrieving payment data.
   * 	- `findByPaymentId`: A method that retrieves a single payment record based on its
   * payment ID.
   * 	- `map`: A method that applies a mapping operation to the retrieved payment record,
   * transforming it into an instance of `PaymentDto`.
   * 
   * @returns an Optional containing a PaymentDto object representing the payment details
   * for the specified payment ID.
   * 
   * 	- The function returns an Optional object, which contains a PaymentDto instance
   * if a payment with the provided payment ID exists in the repository, or an empty
   * Optional if no such payment exists.
   * 	- The `findByPaymentId` method in the repository returns a Stream of Payment
   * instances that match the provided payment ID.
   * 	- The `map` method maps each Payment instance to a corresponding PaymentDto
   * instance using the `paymentMapper` function.
   */
  @Override
  public Optional<PaymentDto> getPaymentDetails(String paymentId) {
    return paymentRepository.findByPaymentId(paymentId)
        .map(paymentMapper::paymentToPaymentDto);
  }

  /**
   * retrieves a HouseMember object from the repository based on the input memberId.
   * 
   * @param memberId ID of the House Member to be retrieved from the repository.
   * 
   * The method `getHouseMember` returns an optional instance of `HouseMember`. The
   * implementation involves calling the `findByMemberId` method on a `HouseMemberRepository`,
   * passing in the `memberId` as a parameter.
   * 
   * @returns an optional `HouseMember` object representing the member with the provided
   * `memberId`.
   * 
   * 	- The `Optional<HouseMember>` type indicates that the function may return either
   * an instance of `HouseMember` or an empty optional, depending on whether a matching
   * member exists in the repository.
   * 	- The `findByMemberId` method of the `houseMemberRepository` returns an optional
   * containing a `HouseMember` object if a member with the specified `memberId` exists
   * in the database. If no such member exists, the function returns an empty optional.
   * 	- The `Optional<HouseMember>` returned by the function can be used to safely
   * access the contents of the optional without causing null pointer exceptions or
   * other errors.
   */
  @Override
  public Optional<HouseMember> getHouseMember(String memberId) {
    return houseMemberRepository.findByMemberId(memberId);
  }

  /**
   * retrieves a set of payments associated with a given member ID from the payment repository.
   * 
   * @param memberId member ID of the payments to be retrieved.
   * 
   * 	- `memberId`: This is a String property that represents the unique identifier for
   * a member in the system.
   * 	- `paymentId`: This is an optional property that represents the payment ID
   * associated with the member.
   * 	- `charge`: This is an optional property that represents the charge amount
   * associated with the payment.
   * 	- `type`: This is an optional property that represents the type of payment (e.g.,
   * credit card, bank transfer).
   * 	- `description`: This is an optional property that provides a brief description
   * of the payment.
   * 	- `recurring`: This is an optional property that indicates whether the payment
   * is recurring (e.g., monthly subscription).
   * 	- `dueDate`: This is an optional property that represents the date when the payment
   * is due.
   * 	- `admin`: This is an optional property that represents the admin-level information
   * related to the payment.
   * 
   * @returns a set of `Payment` objects that match the given member ID.
   * 
   * 	- `Set<Payment>`: This represents a set of payments that match the specified
   * member ID.
   * 	- `paymentRepository`: This is the repository responsible for storing and retrieving
   * payment data.
   * 	- `ExampleMatcher`: This is an object used to define the matching criteria for
   * the payments, including fields such as `memberId`, `paymentId`, `charge`, `type`,
   * `description`, `recurring`, `dueDate`, and `admin`.
   * 	- `Example<Payment>`: This is an object that represents a payment, containing its
   * properties and attributes.
   * 	- `findAll(Example<Payment> example)`: This method retrieves all payments that
   * match the specified matching criteria from the repository.
   */
  @Override
  public Set<Payment> getPaymentsByMember(String memberId) {
    ExampleMatcher ignoringMatcher = ExampleMatcher.matchingAll()
        .withMatcher("memberId",
            ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
        .withIgnorePaths("paymentId", "charge", "type", "description", "recurring", "dueDate",
            "admin");

    Example<Payment> paymentExample =
        Example.of(new Payment(null, null, null, null, false, null, null,
                new HouseMember().withMemberId(memberId)),
            ignoringMatcher);

    return new HashSet<>(paymentRepository.findAll(paymentExample));
  }

  /**
   * retrieves a page of payments based on the admin ID, ignoring certain fields such
   * as payment ID, charge, type, description, recurring, due date, and member ID.
   * 
   * @param adminId ID of an administrator for whom the payments are being retrieved.
   * 
   * 	- `adminId`: This is a String input parameter that represents the ID of an
   * administrator for whom the payments will be retrieved.
   * 	- `pageable`: This is a Pageable object that specifies how the results should be
   * paged and sorted.
   * 
   * @param pageable pagination information for the query, allowing the method to
   * retrieve a specific page of results from the database.
   * 
   * 	- `pageable`: A Pageable object represents a page of data in a collection. It has
   * various attributes such as the current page number, the total number of pages, and
   * the number of items per page. In this function, the pageable is deserialized to
   * explain its properties.
   * 
   * @returns a page of Payment objects filtered based on the admin ID.
   * 
   * 	- `Page<Payment>`: This is a page of payments retrieved from the repository. The
   * pageable argument passed to the findAll method determines how the payments are
   * divided into pages.
   * 	- `Payment`: Each payment in the page is represented as an instance of the Payment
   * class, which has several attributes:
   * 	+ `adminId`: The ID of the admin who made the payment.
   * 	+ `paymentId`: The unique ID of the payment.
   * 	+ `charge`: The amount charged to the user.
   * 	+ `type`: The type of payment (e.g., invoice, subscription).
   * 	+ `description`: A brief description of the payment.
   * 	+ `recurring`: Whether the payment is recurring (true) or not (false).
   * 	+ `dueDate`: The date when the payment is due.
   * 	+ `memberId`: The ID of the member associated with the payment.
   * 	- `paymentRepository`: This is an interface that defines the methods for interacting
   * with the payment database.
   * 
   * In summary, the `getPaymentsByAdmin` function retrieves a page of payments based
   * on the admin ID and pagination criteria, and returns them in a Page object. Each
   * payment is represented as an instance of the Payment class, which has various
   * attributes related to the payment itself and its associated member.
   */
  @Override
  public Page<Payment> getPaymentsByAdmin(String adminId, Pageable pageable) {
    ExampleMatcher ignoringMatcher = ExampleMatcher.matchingAll()
        .withMatcher("adminId",
            ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
        .withIgnorePaths("paymentId", "charge", "type", "description", "recurring", "dueDate",
            "memberId");

    Example<Payment> paymentExample =
        Example.of(
            new Payment(null, null, null, null, false, null, new User().withUserId(adminId), null),
            ignoringMatcher);

    return paymentRepository.findAll(paymentExample, pageable);
  }

  /**
   * converts a `PaymentDto` object into a `Payment` entity, saves both the `Admin` and
   * `Payment` entities to their respective repositories, and returns the converted `PaymentDto`.
   * 
   * @param request PaymentDto object containing the necessary information to create a
   * new payment entity in the repository.
   * 
   * 	- PaymentDto 'request' is converted into a Payment object using the `paymentMapper`.
   * 	- The Admin entity associated with the Payment is saved in the `adminRepository`.
   * 	- The Payment entity itself is saved in the `paymentRepository`.
   * 
   * @returns a `PaymentDto` object containing the updated information of the payment
   * after saving it to the repository.
   * 
   * 	- The PaymentDto `paymentMapper.paymentToPaymentDto(payment)` represents the
   * transformed payment data from the `Payment` object to a `PaymentDto` object.
   * 	- The `adminRepository.save(payment.getAdmin())` method saves the admin information
   * associated with the payment in the `admin` repository.
   * 	- The `paymentRepository.save(payment)` method saves the payment information in
   * the `payment` repository.
   */
  private PaymentDto createPaymentInRepository(PaymentDto request) {
    Payment payment = paymentMapper.paymentDtoToPayment(request);

    adminRepository.save(payment.getAdmin());
    paymentRepository.save(payment);

    return paymentMapper.paymentToPaymentDto(payment);
  }

  /**
   * generates a unique payment ID for a given `PaymentDto` request using the
   * `UUID.randomUUID()` method and assigns it to the `paymentId` field of the `PaymentDto`
   * object.
   * 
   * @param request PaymentDto object that contains information about the payment, and
   * it is used to generate a unique payment ID using the `UUID.randomUUID().toString()`
   * method.
   * 
   * The input `request` has the following properties:
   * 
   * 	- `request`: a `PaymentDto` object containing various attributes related to the
   * payment.
   */
  private void generatePaymentId(PaymentDto request) {
    request.setPaymentId(UUID.randomUUID().toString());
  }
}
