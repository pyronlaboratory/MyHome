{"name":"PageInfo.java","path":"api/src/main/java/com/myhome/utils/PageInfo.java","content":{"structured":{"description":"A `PageInfo` class that encapsulates information about a page of results from a data source. The class has fields for the current page number, page size, total pages, and total elements. It also provides a constructor for creating instances of the class based on a `Pageable` object and its corresponding page.","items":[{"id":"1da2bb95-6509-7db2-e744-e90244d96583","ancestors":[],"type":"function","description":"TODO","name":"PageInfo","code":"@EqualsAndHashCode\n@ToString\n@Getter\n@RequiredArgsConstructor(access = AccessLevel.PRIVATE)\npublic class PageInfo {\n  private final int currentPage;\n  private final int pageLimit;\n  private final int totalPages;\n  private final long totalElements;\n\n  public static PageInfo of(Pageable pageable, Page<?> page) {\n    return new PageInfo(\n        pageable.getPageNumber(),\n        pageable.getPageSize(),\n        page.getTotalPages(),\n        page.getTotalElements()\n    );\n  }\n}","location":{"start":11,"insert":11,"offset":" ","indent":0},"item_type":"class","length":19},{"id":"19ba518a-dd84-e695-f748-42377c019ab4","ancestors":["1da2bb95-6509-7db2-e744-e90244d96583"],"type":"function","description":"generates a `PageInfo` object containing information about the number of pages, page size, total pages, and total elements for a given `Pageable` and `Page`.","params":[{"name":"pageable","type_name":"Pageable","description":"pageable object containing information about the pagination of the data, which is used to calculate the page number, page size, total pages, and total elements returned by the function.\n\n* `getPageNumber()`: A page number that represents where the current page falls in the overall list of pages.\n* `getPageSize()`: The number of items that can be displayed on a single page.\n* `getTotalPages()`: The total number of pages available in the list, including any empty pages at the beginning or end.\n* `getTotalElements()`: The total number of items in the entire list, including any duplicates or missing values.","complex_type":true},{"name":"page","type_name":"Page<?>","description":"current page being processed, providing the total number of elements on that page.\n\n* `pageNumber`: The number of the current page being displayed.\n* `pageSize`: The number of elements per page.\n* `totalPages`: The total number of pages in the result set.\n* `totalElements`: The total number of elements in the result set.","complex_type":true}],"returns":{"type_name":"PageInfo","description":"a `PageInfo` object containing page number, size, total pages, and total elements.\n\n* `pageNumber`: The number of the current page being displayed.\n* `pageSize`: The number of elements on each page.\n* `totalPages`: The total number of pages in the result set.\n* `totalElements`: The total number of elements in the result set.","complex_type":true},"usage":{"language":"java","code":"// Assume pageable is the pageable object and page is the Page<?> object returned from a Spring Data query\nPageInfo pageInfo = PageInfo.of(pageable, page);\nSystem.out.println(\"Current Page: \" + pageInfo.getCurrentPage());\nSystem.out.println(\"Number of elements per page: \" + pageInfo.getPageLimit());\nSystem.out.println(\"Total number of pages: \" + pageInfo.getTotalPages());\nSystem.out.println(\"Total number of elements: \" + pageInfo.getTotalElements());\n","description":""},"name":"of","code":"public static PageInfo of(Pageable pageable, Page<?> page) {\n    return new PageInfo(\n        pageable.getPageNumber(),\n        pageable.getPageSize(),\n        page.getTotalPages(),\n        page.getTotalElements()\n    );\n  }","location":{"start":21,"insert":21,"offset":" ","indent":2},"item_type":"method","length":8}]}}}