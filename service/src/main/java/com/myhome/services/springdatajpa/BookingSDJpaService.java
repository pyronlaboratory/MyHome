package com.myhome.services.springdatajpa;

import com.myhome.domain.AmenityBookingItem;
import com.myhome.repositories.AmenityBookingItemRepository;
import com.myhome.services.BookingService;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * TODO
 */
@Service
@RequiredArgsConstructor
public class BookingSDJpaService implements BookingService {

  private final AmenityBookingItemRepository bookingRepository;

  /**
   * deletes a booking from the database based on the amenity ID and booking ID parameters.
   * It first retrieves the booking item from the repository using the booking ID, then
   * checks if the amenity ID of the booking item matches the provided amenity ID. If
   * it does, the function deletes the booking item from the repository and returns
   * `true`. Otherwise, it returns `false`.
   * 
   * @param amenityId ID of the amenity for which the booking is being searched, and
   * it is used to filter the booking items in the repository to only those that
   * correspond to the specified amenity.
   * 
   * 	- `Optional<AmenityBookingItem> booking`: This represents an optional reference
   * to an `AmenityBookingItem` object in the repository. If no matching booking item
   * is found, this will be `Optional.empty()`.
   * 	- `bookingItem.getAmenity().getAmenityId()`: This retrieves the amenity ID
   * associated with the booking item.
   * 	- `amenityFound`: A boolean indicating whether the amenity ID matches the input
   * `amenityId`. If this is `true`, the function proceeds to delete the matching booking
   * item; otherwise, it returns `false`.
   * 
   * @param bookingId unique identifier of a booking item to be deleted.
   * 
   * 	- `amenityId`: The ID of the amenity related to the booking.
   * 	- `bookingId`: The unique identifier for the booking.
   * 
   * @returns a boolean value indicating whether the booking was successfully deleted.
   * 
   * 	- The `map` method is used to perform an action on each element in the `Optional`
   * object, and the result of this action is stored in the `Optional` object.
   * 	- The `findByAmenityBookingItemId` method is used to find an `AmenityBookingItem`
   * entity based on its `amenityBookingItemId`, and the resulting `Optional` object
   * contains the found entity or `None` if no entity is found.
   * 	- The `delete` method is used to delete an `AmenityBookingItem` entity from the
   * repository, which will result in the deletion of the corresponding booking item.
   * 	- The `orElse` method is used to return a default value if the `Optional` object
   * contains no elements. In this case, the default value is `false`.
   * 
   * The output of the `deleteBooking` function can be destructured as follows:
   * 
   * 	- If the `Optional` object contains an element, the resulting `boolean` value
   * will be `true` if the booking item was successfully deleted, and `false` otherwise.
   * 	- If the `Optional` object is empty (`None`), the resulting `boolean` value will
   * be `false`.
   */
  @Transactional
  @Override
  public boolean deleteBooking(String amenityId, String bookingId) {
    Optional<AmenityBookingItem> booking =
        bookingRepository.findByAmenityBookingItemId(bookingId);
    return booking.map(bookingItem -> {
      boolean amenityFound =
          bookingItem.getAmenity().getAmenityId().equals(amenityId);
      if (amenityFound) {
        bookingRepository.delete(bookingItem);
        return true;
      } else {
        return false;
      }
    }).orElse(false);
  }
}
