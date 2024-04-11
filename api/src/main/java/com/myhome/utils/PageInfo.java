package com.myhome.utils;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * TODO
 */
/**
 * from the file is a data structure that provides metadata about a pageable and its
 * corresponding page, including page number, size, total pages, and total elements.
 * The class offers a convenient way to access and manipulate pageable data by providing
 * an easy-to-use API for working with pages and their associated information.
 */
@EqualsAndHashCode
@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PageInfo {
  private final int currentPage;
  private final int pageLimit;
  private final int totalPages;
  private final long totalElements;

  /**
   * generates a `PageInfo` object containing metadata about a pageable and its
   * corresponding page. The object includes page number, size, total pages, and total
   * elements.
   * 
   * @param pageable pageable interface, which provides methods for manipulating the
   * current page of data being processed.
   * 
   * 	- The `getPageNumber()` method returns the page number associated with the current
   * page being processed.
   * 	- The `getPageSize()` method returns the size of a single page in terms of the
   * number of elements it can contain.
   * 	- The `getTotalPages()` method returns the total number of pages available for processing.
   * 	- The `getTotalElements()` method returns the total number of elements that can
   * be processed across all pages.
   * 
   * @param page current page being processed, providing the total number of elements
   * on that page.
   * 
   * 	- `pageNumber`: The page number that the input is associated with.
   * 	- `pageSize`: The number of elements in each page of the input.
   * 	- `totalPages`: The total number of pages in the input.
   * 	- `totalElements`: The total number of elements in the input.
   * 
   * @returns a `PageInfo` object containing information about the page of data.
   * 
   * 	- The page number is represented by the first element of the PageInfo object
   * (pageable.getPageNumber()). This indicates the current page being displayed to the
   * user.
   * 	- The size of a page is described by the second component of the PageInfo object
   * (pageable.getPageSize()). It represents how many items may be shown on a single page.
   * 	- The total number of pages is represented by the third element of the PageInfo
   * object (page.getTotalPages()). This indicates how many web pages are available in
   * total.
   * 	- The overall amount of items is described by the fourth element of the PageInfo
   * object (page.getTotalElements()). It represents the sum of all objects in the
   * entire collection.
   */
  /**
   * generates a `PageInfo` object containing information about the number of pages and
   * elements in a specified range.
   * 
   * @param pageable pageable object containing information about the current page of
   * data to be processed, which is used to calculate the page number, size, and total
   * pages and elements.
   * 
   * 	- The page number (0-based index) returned by the `getPageNumber()` method is
   * included in the first component of the output `PageInfo`.
   * 	- The total number of pages that can be produced by calling `getPageSize()` on
   * `pageable` is included in the second component of the output `PageInfo`.
   * 	- The total number of elements that can be processed by `pageable` is returned
   * by the `getTotalElements()` method and incorporated into the third component of
   * the output `PageInfo`.
   * 
   * @param page current page of data being processed, providing the total number of
   * elements on that page.
   * 
   * 	- `pageNumber`: The number of the page being accessed.
   * 	- `pageSize`: The number of elements in each page.
   * 	- `totalPages`: The total number of pages in the result set.
   * 	- `totalElements`: The total number of elements in the result set.
   * 
   * @returns a `PageInfo` object containing page number, size, total pages, and total
   * elements.
   * 
   * 	- `pageNumber`: The page number that the pageable object represents.
   * 	- `pageSize`: The number of elements that can be displayed on each page.
   * 	- `totalPages`: The total number of pages in the result set.
   * 	- `totalElements`: The total number of elements in the result set.
   */
  public static PageInfo of(Pageable pageable, Page<?> page) {
    return new PageInfo(
        pageable.getPageNumber(),
        pageable.getPageSize(),
        page.getTotalPages(),
        page.getTotalElements()
    );
  }
}
