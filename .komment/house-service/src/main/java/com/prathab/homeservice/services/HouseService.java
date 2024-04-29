{"name":"HouseService.java","path":"house-service/src/main/java/com/prathab/homeservice/services/HouseService.java","content":{"structured":{"description":"An interface called HouseService that provides methods for managing houses, including adding new houses and retrieving a list of all houses. The interface includes two methods: addHouse() for adding a new house and findAllHouses() for retrieving a list of all houses.","diagram":"digraph G {\n    label=\"com.prathab.homeservice.services.HouseService\"\n    bgcolor=\"#151719\"\n    fontcolor=\"#ECEDED\"\n    splines=ortho\n    fontname=\"Courier New\"\n    edge [color=\"#26de81\"]\n    node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n    \n    subgraph cluster_0 {\n        label=\"controllers\"\n        color=\"#33363A\"\n        subgraph cluster_1 {\n            label=\"dto\"\n            color=\"#33363A\"\n            HouseDto\n        }\n    }\n    subgraph cluster_2 {\n        label=\"domain\"\n        color=\"#33363A\"\n        House\n    }\n    subgraph cluster_3 {\n        label=\"services\"\n        color=\"#33363A\"\n        subgraph cluster_main {\n            // style=filled;\n            color=\"#00000000\"; \n            HouseService [style=filled,color=\"#26de81\",shape=rectangle, fontname=\"Courier New\"];\n            label = \"\"\n        }\n    }\n    HouseService -> House \n    HouseDto -> HouseService \n}\n","items":[{"id":"9e518c6b-2f71-47bc-3641-7d1ff60b2e63","ancestors":[],"type":"function","description":"defines a set of methods for managing houses, including adding new houses and retrieving a list of all houses.","name":"HouseService","code":"public interface HouseService {\n  House addHouse(HouseDto houseDto);\n\n  Set<House> findAllHouses();\n}","location":{"start":27,"insert":23,"offset":" ","indent":0,"comment":{"start":22,"end":26}},"item_type":"interface","length":5,"docLength":4}]}}}