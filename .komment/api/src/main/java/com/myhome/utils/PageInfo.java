{"name":"PageInfo.java","path":"api/src/main/java/com/myhome/utils/PageInfo.java","content":{"structured":{"description":"A `PageInfo` class that contains information about the number of pages, page size, total pages, and total elements for a given `Pageable` and `Page`. The `of()` method creates a new `PageInfo` object based on the provided `Pageable` and `Page`.","items":[{"id":"81fba887-6e9e-42a6-4c4f-a13bd3af6a2a","ancestors":[],"type":"function","description":"TODO","name":"PageInfo","code":"@EqualsAndHashCode\n@ToString\n@Getter\n@RequiredArgsConstructor(access = AccessLevel.PRIVATE)\npublic class PageInfo {\n  private final int currentPage;\n  private final int pageLimit;\n  private final int totalPages;\n  private final long totalElements;\n\n  /**\n   * generates a `PageInfo` object containing information about the number of pages,\n   * page size, total pages, and total elements for a given `Pageable` and `Page`.\n   * \n   * @param pageable pageable object containing information about the pagination of the\n   * data, which is used to calculate the page number, page size, total pages, and total\n   * elements returned by the function.\n   * \n   * \t- `getPageNumber()`: A page number that represents where the current page falls\n   * in the overall list of pages.\n   * \t- `getPageSize()`: The number of items that can be displayed on a single page.\n   * \t- `getTotalPages()`: The total number of pages available in the list, including\n   * any empty pages at the beginning or end.\n   * \t- `getTotalElements()`: The total number of items in the entire list, including\n   * any duplicates or missing values.\n   * \n   * @param page current page being processed, providing the total number of elements\n   * on that page.\n   * \n   * \t- `pageNumber`: The number of the current page being displayed.\n   * \t- `pageSize`: The number of elements per page.\n   * \t- `totalPages`: The total number of pages in the result set.\n   * \t- `totalElements`: The total number of elements in the result set.\n   * \n   * @returns a `PageInfo` object containing page number, size, total pages, and total\n   * elements.\n   * \n   * \t- `pageNumber`: The number of the current page being displayed.\n   * \t- `pageSize`: The number of elements on each page.\n   * \t- `totalPages`: The total number of pages in the result set.\n   * \t- `totalElements`: The total number of elements in the result set.\n   */\n  public static PageInfo of(Pageable pageable, Page<?> page) {\n    return new PageInfo(\n        pageable.getPageNumber(),\n        pageable.getPageSize(),\n        page.getTotalPages(),\n        page.getTotalElements()\n    );\n  }\n}","location":{"start":14,"insert":14,"offset":" ","indent":0},"item_type":"class","length":51},{"id":"60860d73-25e1-8faa-0b42-fa3ffca4fc94","ancestors":["81fba887-6e9e-42a6-4c4f-a13bd3af6a2a"],"type":"function","description":"generates a `PageInfo` object containing information about the number of pages, size of each page, total number of pages and total elements for a given `Pageable` and `Page`.","params":[{"name":"pageable","type_name":"Pageable","description":"pagination information for the current page of data, which includes the page number, page size, total pages, and total elements.\n\n* `getPageNumber()`: The current page number being displayed.\n* `getPageSize()`: The number of elements in a single page.\n* `getTotalPages()`: The total number of pages available for display.\n* `getTotalElements()`: The total number of elements in the dataset.","complex_type":true},{"name":"page","type_name":"Page<?>","description":"current page being processed, providing information on its number, size, total pages, and total elements.\n\n* `getPageNumber()`: The page number, which is an integer representing the position of the page in the result set.\n* `getPageSize()`: The page size, which is an integer representing the number of elements that can be displayed on a single page.\n* `getTotalPages()`: The total number of pages in the result set, which is an integer value obtained by dividing the total number of elements by the page size.\n* `getTotalElements()`: The total number of elements in the result set, which is also an integer value obtained by adding up all the elements across all pages.","complex_type":true}],"returns":{"type_name":"PageInfo","description":"a `PageInfo` object containing page-related metadata.\n\n* pageable.getPageNumber(): The number of the current page being displayed.\n* pageable.getPageSize(): The number of elements on each page.\n* page.getTotalPages(): The total number of pages in the result set.\n* page.getTotalElements(): The total number of elements in the result set.","complex_type":true},"usage":{"language":"java","code":"public static void main(String[] args) {\n    // Create a Pageable object with page number 0 and page size 25\n    Pageable pageable = new ExamplePageable(0, 25);\n\n    // Create a Page object with 100 elements and 4 pages\n    Page<?> page = new ExamplePage(100, 4);\n\n    // Use the of method to create a PageInfo object\n    PageInfo info = PageInfo.of(pageable, page);\n\n    System.out.println(\"Current page: \" + info.getCurrentPage());\n    System.out.println(\"Page limit: \" + info.getPageLimit());\n    System.out.println(\"Total pages: \" + info.getTotalPages());\n    System.out.println(\"Total elements: \" + info.getTotalElements());\n}\n","description":""},"name":"of","code":"public static PageInfo of(Pageable pageable, Page<?> page) {\n    return new PageInfo(\n        pageable.getPageNumber(),\n        pageable.getPageSize(),\n        page.getTotalPages(),\n        page.getTotalElements()\n    );\n  }","location":{"start":56,"insert":56,"offset":" ","indent":2},"item_type":"method","length":8}]}}}