{"name":"HouseSDJpaService.java","path":"house-service/src/main/java/com/prathab/homeservice/services/springdatajpa/HouseSDJpaService.java","content":{"structured":{"description":"A HouseSDJpaService class that implements HouseService interface. The class has a constructor that takes HouseRepository and HouseApiMapper objects as parameters. The addHouse method adds a new house to the database, generates a unique ID for the house, and saves it to the repository. The findAllHouses method retrieves all houses from the database and returns them in a set.","items":[{"id":"8fe80efa-6640-e58a-ad41-dfaf9b3341d9","ancestors":[],"type":"function","description":"TODO","name":"HouseSDJpaService","code":"@Service\npublic class HouseSDJpaService implements HouseService {\n  private final HouseRepository houseRepository;\n  private final HouseApiMapper houseApiMapper;\n\n  public HouseSDJpaService(HouseRepository houseRepository,\n      HouseApiMapper houseApiMapper) {\n    this.houseRepository = houseRepository;\n    this.houseApiMapper = houseApiMapper;\n  }\n\n  @Override public House addHouse(HouseDto houseDto) {\n    var house = houseApiMapper.houseDtoToHouse(houseDto);\n    house.setHouseId(generateUniqueHouseId());\n    return houseRepository.save(house);\n  }\n\n  private String generateUniqueHouseId() {\n    return UUID.randomUUID().toString();\n  }\n\n  @Override public Set<House> findAllHouses() {\n    var houseSet = new HashSet<House>();\n    houseRepository.findAll().forEach(houseSet::add);\n    return houseSet;\n  }\n}","location":{"start":29,"insert":29,"offset":" ","indent":0},"item_type":"class","length":27},{"id":"b135ad6d-358c-e88e-c843-d84b6ac1a068","ancestors":["8fe80efa-6640-e58a-ad41-dfaf9b3341d9"],"type":"function","description":"takes a `HouseDto` object and converts it into a `House` entity, assigns a unique house ID, and saves it to the repository.","params":[{"name":"houseDto","type_name":"HouseDto","description":"House object to be saved, which is converted from the `HouseDto` object through the `houseApiMapper` method before being persisted into the database by the `houseRepository`.\n\nThe `houseDto` object has several attributes:\n\n* `houseId`: A unique identifier for the house\n* `address`: The address of the house\n* `capacity`: The capacity of the house\n* `description`: A description of the house\n* `imageUrl`: An URL pointing to an image of the house\n* `latitude`: The latitude coordinate of the house\n* `longitude`: The longitude coordinate of the house\n* `price`: The price of the house\n\nThese attributes are converted into corresponding fields in the `House` object using the `houseApiMapper`. The `House` object is then persisted to the repository using the `save()` method.","complex_type":true}],"returns":{"type_name":"House","description":"a new instance of `House` object with a unique ID generated by the method.\n\n* `house`: This is a `House` object that represents a new house with a unique ID generated by the `generateUniqueHouseId()` method.\n* `houseDto`: This is the original `HouseDto` object passed into the function, which has been converted to a `House` object using the `houseApiMapper.houseDtoToHouse()` method.\n* `houseRepository`: This is an instance of `CrudRepository<House, Long>`, which is used to save the new house to the database.","complex_type":true},"usage":{"language":"java","code":"import com.prathab.homeservice.controllers.dto.HouseDto;\nimport com.prathab.homeservice.domain.House;\nimport com.prathab.homeservice.services.HouseService;\n\npublic class Main {\n  public static void main(String[] args) {\n    HouseService houseService = new HouseSDJpaService(new HouseRepository(), new HouseApiMapper());\n    HouseDto houseDto = new HouseDto(\"123 Main St.\", \"Bedroom\", 4, 500.00);\n    House house = houseService.addHouse(houseDto);\n  }\n}\n","description":""},"name":"addHouse","code":"@Override public House addHouse(HouseDto houseDto) {\n    var house = houseApiMapper.houseDtoToHouse(houseDto);\n    house.setHouseId(generateUniqueHouseId());\n    return houseRepository.save(house);\n  }","location":{"start":40,"insert":40,"offset":" ","indent":2},"item_type":"method","length":5},{"id":"1d1b20c1-7289-39b3-f044-a74bdc83cb69","ancestors":["8fe80efa-6640-e58a-ad41-dfaf9b3341d9"],"type":"function","description":"generates a unique string representing a random UUID.","params":[],"returns":{"type_name":"String","description":"a unique, randomly generated string of characters in the format of a UUID.\n\n* The output is a String value that represents a unique identifier for a house.\n* The identifier is generated using the `UUID.randomUUID()` method, which produces a universally unique identifier (UUID) that is randomly generated.\n* The resulting string is a compact representation of the UUID, typically consisting of 32 characters.","complex_type":true},"usage":{"language":"java","code":"import java.util.UUID;\n \npublic class Main {\n    public static void main(String[] args) {\n        System.out.println(generateUniqueHouseId()); // Outputs a randomly generated UUID string\n    }\n}\n","description":""},"name":"generateUniqueHouseId","code":"private String generateUniqueHouseId() {\n    return UUID.randomUUID().toString();\n  }","location":{"start":46,"insert":46,"offset":" ","indent":2},"item_type":"method","length":3},{"id":"05df55ed-c1cb-74b7-8646-506d5d289e54","ancestors":["8fe80efa-6640-e58a-ad41-dfaf9b3341d9"],"type":"function","description":"sets a new `HashSet` to hold all houses returned by the `findAll` method of a `houseRepository`. It then calls the `findAll` method and adds each house to the `HashSet`. Finally, it returns the `HashSet` containing all the houses.","params":[],"returns":{"type_name":"HashSet","description":"a set of House objects retrieved from the database.\n\n* The output is a set of House objects, denoted by <House>.\n* The set contains all houses fetched from the database using the repository's findAll() method.\n* Each element in the set represents a House object, with attributes such as name, address, and location.","complex_type":true},"usage":{"language":"java","code":"Set<House> houses = houseService.findAllHouses();\nfor(House house : houses) {\n    System.out.println(house);\n}\n","description":"\nIn this code, we first call the `findAllHouses()` method on an instance of a class that implements the `HouseService` interface. This method returns a set of all the houses in the database. We then iterate through each house and print it to the console using a for loop."},"name":"findAllHouses","code":"@Override public Set<House> findAllHouses() {\n    var houseSet = new HashSet<House>();\n    houseRepository.findAll().forEach(houseSet::add);\n    return houseSet;\n  }","location":{"start":50,"insert":50,"offset":" ","indent":2},"item_type":"method","length":5}]}}}