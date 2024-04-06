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
 * TODO
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
   * generates a `PageInfo` object containing information about the number of pages,
   * page size, total pages, and total elements for a given `Pageable` and `Page`.
   * 
   * @param pageable pageable object containing information about the pagination of the
   * data, which is used to calculate the page number, page size, total pages, and total
   * elements returned by the function.
   * 
   * 	- `getPageNumber()`: A page number that represents where the current page falls
   * in the overall list of pages.
   * 	- `getPageSize()`: The number of items that can be displayed on a single page.
   * 	- `getTotalPages()`: The total number of pages available in the list, including
   * any empty pages at the beginning or end.
   * 	- `getTotalElements()`: The total number of items in the entire list, including
   * any duplicates or missing values.
   * 
   * @param page current page being processed, providing the total number of elements
   * on that page.
   * 
   * 	- `pageNumber`: The number of the current page being displayed.
   * 	- `pageSize`: The number of elements per page.
   * 	- `totalPages`: The total number of pages in the result set.
   * 	- `totalElements`: The total number of elements in the result set.
   * 
   * @returns a `PageInfo` object containing page number, size, total pages, and total
   * elements.
   * 
   * 	- `pageNumber`: The number of the current page being displayed.
   * 	- `pageSize`: The number of elements on each page.
   * 	- `totalPages`: The total number of pages in the result set.
   * 	- `totalElements`: The total number of elements in the result set.
   */
  /**
   * generates a `PageInfo` object containing information about the number of pages,
   * size of each page, total number of pages and total elements for a given `Pageable`
   * and `Page`.
   * 
   * @param pageable pagination information for the current page of data, which includes
   * the page number, page size, total pages, and total elements.
   * 
   * 	- `getPageNumber()`: The current page number being displayed.
   * 	- `getPageSize()`: The number of elements in a single page.
   * 	- `getTotalPages()`: The total number of pages available for display.
   * 	- `getTotalElements()`: The total number of elements in the dataset.
   * 
   * @param page current page being processed, providing information on its number,
   * size, total pages, and total elements.
   * 
   * 	- `getPageNumber()`: The page number, which is an integer representing the position
   * of the page in the result set.
   * 	- `getPageSize()`: The page size, which is an integer representing the number of
   * elements that can be displayed on a single page.
   * 	- `getTotalPages()`: The total number of pages in the result set, which is an
   * integer value obtained by dividing the total number of elements by the page size.
   * 	- `getTotalElements()`: The total number of elements in the result set, which is
   * also an integer value obtained by adding up all the elements across all pages.
   * 
   * @returns a `PageInfo` object containing page-related metadata.
   * 
   * 	- pageable.getPageNumber(): The number of the current page being displayed.
   * 	- pageable.getPageSize(): The number of elements on each page.
   * 	- page.getTotalPages(): The total number of pages in the result set.
   * 	- page.getTotalElements(): The total number of elements in the result set.
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
