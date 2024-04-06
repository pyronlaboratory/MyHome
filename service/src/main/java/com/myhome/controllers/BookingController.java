package com.myhome.controllers;

import com.myhome.api.BookingsApi;
import com.myhome.services.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class BookingController implements BookingsApi {

  private final BookingService bookingSDJpaService;

  /**
   * deletes a booking based on its amenity ID and ID, returning a HTTP response code
   * indicating the outcome of the operation.
   * 
   * @param amenityId ID of an amenity for which a booking is to be deleted.
   * 
   * 	- `amenityId`: A String representing the ID of an amenity.
   * 
   * The function uses the `bookingSDJpaService.deleteBooking(amenityId, bookingId)`
   * method to delete a booking. The method takes two path variables, `amenityId` and
   * `bookingId`, as input. If the booking is successfully deleted, a `HttpStatus.NO_CONTENT`
   * response is returned. Otherwise, a `HttpStatus.NOT_FOUND` response is returned.
   * 
   * @param bookingId ID of the booking to be deleted.
   * 
   * 	- `amenityId`: The ID of the amenity associated with the booking to be deleted.
   * 	- `bookingId`: The unique identifier of the booking to be deleted.
   * 
   * @returns a `ResponseEntity` with a status code of either `NO_CONTENT` or `NOT_FOUND`,
   * depending on whether the booking was successfully deleted.
   * 
   * 	- `isBookingDeleted`: A boolean value indicating whether the booking was successfully
   * deleted or not.
   * 	- `HttpStatus`: The HTTP status code of the response entity, which is either
   * `NO_CONTENT` or `NOT_FOUND`.
   */
  @Override
  public ResponseEntity<Void> deleteBooking(@PathVariable String amenityId,
      @PathVariable String bookingId) {
    boolean isBookingDeleted = bookingSDJpaService.deleteBooking(amenityId, bookingId);
    if (isBookingDeleted) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }
}
