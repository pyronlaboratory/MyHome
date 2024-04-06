{"name":"CredentialsIncorrectException.java","path":"service/src/main/java/com/myhome/controllers/exceptions/CredentialsIncorrectException.java","content":{"structured":{"description":"An exception class called CredentialsIncorrectException that extends the base AuthenticationException class. The custom exception is created using the Lombok library and includes additional logging statements using the Slf4j library. The log statements provide information about the user ID for whom the credentials are incorrect.","image":"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\n \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<!-- Generated by graphviz version 2.43.0 (0)\n -->\n<!-- Title: com.myhome.controllers.exceptions.AuthenticationException Pages: 1 -->\n<svg width=\"582pt\" height=\"86pt\"\n viewBox=\"0.00 0.00 582.00 86.00\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n<g id=\"graph0\" class=\"graph\" transform=\"scale(1 1) rotate(0) translate(4 82)\">\n<title>com.myhome.controllers.exceptions.AuthenticationException</title>\n<!-- Node1 -->\n<g id=\"Node000001\" class=\"node\">\n<title>Node1</title>\n<g id=\"a_Node000001\"><a xlink:title=\" \">\n<polygon fill=\"#999999\" stroke=\"#666666\" points=\"340,-54 142,-54 142,-24 340,-24 340,-54\"/>\n<text text-anchor=\"start\" x=\"150\" y=\"-42\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.myhome.controllers.exceptions.</text>\n<text text-anchor=\"middle\" x=\"241\" y=\"-31\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">AuthenticationException</text>\n</a>\n</g>\n</g>\n<!-- Node3 -->\n<g id=\"Node000003\" class=\"node\">\n<title>Node3</title>\n<g id=\"a_Node000003\"><a xlink:href=\"classcom_1_1myhome_1_1controllers_1_1exceptions_1_1CredentialsIncorrectException.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"574,-78 376,-78 376,-48 574,-48 574,-78\"/>\n<text text-anchor=\"start\" x=\"384\" y=\"-66\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.myhome.controllers.exceptions.</text>\n<text text-anchor=\"middle\" x=\"475\" y=\"-55\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">CredentialsIncorrectException</text>\n</a>\n</g>\n</g>\n<!-- Node1&#45;&gt;Node3 -->\n<g id=\"edge2_Node000001_Node000003\" class=\"edge\">\n<title>Node1&#45;&gt;Node3</title>\n<g id=\"a_edge2_Node000001_Node000003\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M350.12,-50.18C358.73,-51.08 367.39,-51.97 375.93,-52.85\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"350.36,-46.69 340.05,-49.14 349.64,-53.65 350.36,-46.69\"/>\n</a>\n</g>\n</g>\n<!-- Node4 -->\n<g id=\"Node000004\" class=\"node\">\n<title>Node4</title>\n<g id=\"a_Node000004\"><a xlink:href=\"classcom_1_1myhome_1_1controllers_1_1exceptions_1_1UserNotFoundException.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"574,-30 376,-30 376,0 574,0 574,-30\"/>\n<text text-anchor=\"start\" x=\"384\" y=\"-18\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.myhome.controllers.exceptions.</text>\n<text text-anchor=\"middle\" x=\"475\" y=\"-7\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">UserNotFoundException</text>\n</a>\n</g>\n</g>\n<!-- Node1&#45;&gt;Node4 -->\n<g id=\"edge3_Node000001_Node000004\" class=\"edge\">\n<title>Node1&#45;&gt;Node4</title>\n<g id=\"a_edge3_Node000001_Node000004\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M350.12,-27.82C358.73,-26.92 367.39,-26.03 375.93,-25.15\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"349.64,-24.35 340.05,-28.86 350.36,-31.31 349.64,-24.35\"/>\n</a>\n</g>\n</g>\n<!-- Node2 -->\n<g id=\"Node000002\" class=\"node\">\n<title>Node2</title>\n<g id=\"a_Node000002\"><a xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"106,-48.5 0,-48.5 0,-29.5 106,-29.5 106,-48.5\"/>\n<text text-anchor=\"middle\" x=\"53\" y=\"-36.5\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">RuntimeException</text>\n</a>\n</g>\n</g>\n<!-- Node2&#45;&gt;Node1 -->\n<g id=\"edge1_Node000001_Node000002\" class=\"edge\">\n<title>Node2&#45;&gt;Node1</title>\n<g id=\"a_edge1_Node000001_Node000002\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M116.49,-39C124.71,-39 133.27,-39 141.9,-39\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"116.31,-35.5 106.31,-39 116.31,-42.5 116.31,-35.5\"/>\n</a>\n</g>\n</g>\n</g>\n</svg>\n","items":[{"id":"b77cc736-e6d4-ddb8-9741-38994a226715","ancestors":[],"type":"function","description":"extends AuthenticationException and provides a custom error message for incorrect credentials for a specific user ID.\n","name":"CredentialsIncorrectException","code":"@Slf4j\npublic class CredentialsIncorrectException extends AuthenticationException {\n  public CredentialsIncorrectException(String userId) {\n    super();\n    log.info(\"Credentials are incorrect for userId: \" + userId);\n  }\n}","location":{"start":5,"insert":5,"offset":" ","indent":0},"item_type":"class","length":7}]}}}