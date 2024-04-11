{"name":"HouseRepository.java","path":"house-service/src/main/java/com/prathab/homeservice/repositories/HouseRepository.java","content":{"structured":{"description":"An interface for a HouseRepository, which is a Spring Data repository for handling House objects. The interface extends the CrudRepository interface and provides the standard CRUD operations for houses, including storing, retrieving, updating, and deleting them.","image":"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\n \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<!-- Generated by graphviz version 2.43.0 (0)\n -->\n<!-- Title: com.prathab.homeservice.repositories.HouseRepository Pages: 1 -->\n<svg width=\"216pt\" height=\"104pt\"\n viewBox=\"0.00 0.00 216.00 104.00\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n<g id=\"graph0\" class=\"graph\" transform=\"scale(1 1) rotate(0) translate(4 100)\">\n<title>com.prathab.homeservice.repositories.HouseRepository</title>\n<!-- Node1 -->\n<g id=\"Node000001\" class=\"node\">\n<title>Node1</title>\n<g id=\"a_Node000001\"><a xlink:title=\" \">\n<polygon fill=\"#999999\" stroke=\"#666666\" points=\"208,-30 0,-30 0,0 208,0 208,-30\"/>\n<text text-anchor=\"start\" x=\"8\" y=\"-18\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.prathab.homeservice.repositories.</text>\n<text text-anchor=\"middle\" x=\"104\" y=\"-7\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">HouseRepository</text>\n</a>\n</g>\n</g>\n<!-- Node2 -->\n<g id=\"Node000002\" class=\"node\">\n<title>Node2</title>\n<g id=\"a_Node000002\"><a xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"172.5,-96 35.5,-96 35.5,-66 172.5,-66 172.5,-96\"/>\n<text text-anchor=\"start\" x=\"43.5\" y=\"-84\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">CrudRepository&lt; House,</text>\n<text text-anchor=\"middle\" x=\"104\" y=\"-73\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\"> Long &gt;</text>\n</a>\n</g>\n</g>\n<!-- Node2&#45;&gt;Node1 -->\n<g id=\"edge1_Node000001_Node000002\" class=\"edge\">\n<title>Node2&#45;&gt;Node1</title>\n<g id=\"a_edge1_Node000001_Node000002\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M104,-55.54C104,-46.96 104,-37.61 104,-30.16\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"100.5,-55.8 104,-65.8 107.5,-55.8 100.5,-55.8\"/>\n</a>\n</g>\n</g>\n</g>\n</svg>\n","items":[{"id":"88ed9bbf-55ec-51bd-e049-e59b92ac8fe4","ancestors":[],"type":"function","description":"defines a Spring Data repository for managing House objects using the CrudRepository interface, providing basic CRUD functionality for House entities.","name":"HouseRepository","code":"@Repository\npublic interface HouseRepository extends CrudRepository<House, Long> {\n}","location":{"start":23,"insert":23,"offset":" ","indent":0,"comment":null},"item_type":"interface","length":3}]}}}