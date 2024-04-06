{"name":"HouseMemberDocument.java","path":"service/src/main/java/com/myhome/domain/HouseMemberDocument.java","content":{"structured":{"description":"A class called `HouseMemberDocument` that extends the `BaseEntity` class. It has two fields: `documentFilename`, which is a unique string column, and `documentContent`, which is a binary large object (BLOB) field that contains the document's content. The class also uses Lombok annotations for constructor injection and equivalence checking.","image":"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\n \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<!-- Generated by graphviz version 2.43.0 (0)\n -->\n<!-- Title: com.myhome.domain.BaseEntity Pages: 1 -->\n<svg width=\"531pt\" height=\"371pt\"\n viewBox=\"0.00 0.00 531.00 371.00\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n<g id=\"graph0\" class=\"graph\" transform=\"scale(1 1) rotate(0) translate(4 367)\">\n<title>com.myhome.domain.BaseEntity</title>\n<!-- Node1 -->\n<g id=\"Node000001\" class=\"node\">\n<title>Node1</title>\n<g id=\"a_Node000001\"><a xlink:title=\" \">\n<polygon fill=\"#999999\" stroke=\"#666666\" points=\"289,-191 110,-191 110,-172 289,-172 289,-191\"/>\n<text text-anchor=\"middle\" x=\"199.5\" y=\"-179\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.myhome.domain.BaseEntity</text>\n</a>\n</g>\n</g>\n<!-- Node3 -->\n<g id=\"Node000003\" class=\"node\">\n<title>Node3</title>\n<g id=\"a_Node000003\"><a xlink:href=\"classcom_1_1myhome_1_1domain_1_1Amenity.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"508,-363 340,-363 340,-344 508,-344 508,-363\"/>\n<text text-anchor=\"middle\" x=\"424\" y=\"-351\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.myhome.domain.Amenity</text>\n</a>\n</g>\n</g>\n<!-- Node1&#45;&gt;Node3 -->\n<g id=\"edge2_Node000001_Node000003\" class=\"edge\">\n<title>Node1&#45;&gt;Node3</title>\n<g id=\"a_edge2_Node000001_Node000003\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M210.09,-200.41C227.9,-233.41 269.35,-301.17 325,-334.5 331.47,-338.37 338.57,-341.48 345.9,-343.97\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"213.09,-198.58 205.34,-191.36 206.89,-201.84 213.09,-198.58\"/>\n</a>\n</g>\n</g>\n<!-- Node4 -->\n<g id=\"Node000004\" class=\"node\">\n<title>Node4</title>\n<g id=\"a_Node000004\"><a xlink:href=\"classcom_1_1myhome_1_1domain_1_1AmenityBookingItem.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"508,-325.5 340,-325.5 340,-295.5 508,-295.5 508,-325.5\"/>\n<text text-anchor=\"start\" x=\"348\" y=\"-313.5\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.myhome.domain.Amenity</text>\n<text text-anchor=\"middle\" x=\"424\" y=\"-302.5\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">BookingItem</text>\n</a>\n</g>\n</g>\n<!-- Node1&#45;&gt;Node4 -->\n<g id=\"edge3_Node000001_Node000004\" class=\"edge\">\n<title>Node1&#45;&gt;Node4</title>\n<g id=\"a_edge3_Node000001_Node000004\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M215.63,-198.45C237.46,-221.83 280.13,-263.69 325,-286.5 331.79,-289.95 339.11,-292.93 346.57,-295.48\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"218.15,-196.01 208.81,-191.01 212.99,-200.74 218.15,-196.01\"/>\n</a>\n</g>\n</g>\n<!-- Node5 -->\n<g id=\"Node000005\" class=\"node\">\n<title>Node5</title>\n<g id=\"a_Node000005\"><a xlink:href=\"classcom_1_1myhome_1_1domain_1_1Community.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"516,-277 332,-277 332,-258 516,-258 516,-277\"/>\n<text text-anchor=\"middle\" x=\"424\" y=\"-265\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.myhome.domain.Community</text>\n</a>\n</g>\n</g>\n<!-- Node1&#45;&gt;Node5 -->\n<g id=\"edge4_Node000001_Node000005\" class=\"edge\">\n<title>Node1&#45;&gt;Node5</title>\n<g id=\"a_edge4_Node000001_Node000005\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M223.26,-196.49C247.51,-211.78 287.48,-235.12 325,-248.5 335.77,-252.34 347.51,-255.47 358.99,-257.99\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"225.09,-193.5 214.78,-191.06 221.31,-199.4 225.09,-193.5\"/>\n</a>\n</g>\n</g>\n<!-- Node6 -->\n<g id=\"Node000006\" class=\"node\">\n<title>Node6</title>\n<g id=\"a_Node000006\"><a xlink:href=\"classcom_1_1myhome_1_1domain_1_1CommunityHouse.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"516,-239.5 332,-239.5 332,-209.5 516,-209.5 516,-239.5\"/>\n<text text-anchor=\"start\" x=\"340\" y=\"-227.5\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.myhome.domain.Community</text>\n<text text-anchor=\"middle\" x=\"424\" y=\"-216.5\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">House</text>\n</a>\n</g>\n</g>\n<!-- Node1&#45;&gt;Node6 -->\n<g id=\"edge5_Node000001_Node000006\" class=\"edge\">\n<title>Node1&#45;&gt;Node6</title>\n<g id=\"a_edge5_Node000001_Node000006\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M260.03,-193.01C286.24,-198.07 317.36,-204.08 345.35,-209.49\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"260.36,-189.5 249.88,-191.04 259.03,-196.38 260.36,-189.5\"/>\n</a>\n</g>\n</g>\n<!-- Node7 -->\n<g id=\"Node000007\" class=\"node\">\n<title>Node7</title>\n<g id=\"a_Node000007\"><a xlink:href=\"classcom_1_1myhome_1_1domain_1_1HouseMember.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"523,-191 325,-191 325,-172 523,-172 523,-191\"/>\n<text text-anchor=\"middle\" x=\"424\" y=\"-179\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.myhome.domain.HouseMember</text>\n</a>\n</g>\n</g>\n<!-- Node1&#45;&gt;Node7 -->\n<g id=\"edge6_Node000001_Node000007\" class=\"edge\">\n<title>Node1&#45;&gt;Node7</title>\n<g id=\"a_edge6_Node000001_Node000007\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M299.35,-181.5C307.82,-181.5 316.38,-181.5 324.85,-181.5\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"299.08,-178 289.08,-181.5 299.08,-185 299.08,-178\"/>\n</a>\n</g>\n</g>\n<!-- Node8 -->\n<g id=\"Node000008\" class=\"node\">\n<title>Node8</title>\n<g id=\"a_Node000008\"><a xlink:href=\"classcom_1_1myhome_1_1domain_1_1HouseMemberDocument.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"523,-153.5 325,-153.5 325,-123.5 523,-123.5 523,-153.5\"/>\n<text text-anchor=\"start\" x=\"333\" y=\"-141.5\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.myhome.domain.HouseMember</text>\n<text text-anchor=\"middle\" x=\"424\" y=\"-130.5\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">Document</text>\n</a>\n</g>\n</g>\n<!-- Node1&#45;&gt;Node8 -->\n<g id=\"edge7_Node000001_Node000008\" class=\"edge\">\n<title>Node1&#45;&gt;Node8</title>\n<g id=\"a_edge7_Node000001_Node000008\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M260.03,-169.99C286.24,-164.93 317.36,-158.92 345.35,-153.51\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"259.03,-166.62 249.88,-171.96 260.36,-173.5 259.03,-166.62\"/>\n</a>\n</g>\n</g>\n<!-- Node9 -->\n<g id=\"Node000009\" class=\"node\">\n<title>Node9</title>\n<g id=\"a_Node000009\"><a xlink:href=\"classcom_1_1myhome_1_1domain_1_1Payment.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"509,-105 339,-105 339,-86 509,-86 509,-105\"/>\n<text text-anchor=\"middle\" x=\"424\" y=\"-93\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.myhome.domain.Payment</text>\n</a>\n</g>\n</g>\n<!-- Node1&#45;&gt;Node9 -->\n<g id=\"edge8_Node000001_Node000009\" class=\"edge\">\n<title>Node1&#45;&gt;Node9</title>\n<g id=\"a_edge8_Node000001_Node000009\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M223.26,-166.51C247.51,-151.22 287.48,-127.88 325,-114.5 335.77,-110.66 347.51,-107.53 358.99,-105.01\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"221.31,-163.6 214.78,-171.94 225.09,-169.5 221.31,-163.6\"/>\n</a>\n</g>\n</g>\n<!-- Node10 -->\n<g id=\"Node000010\" class=\"node\">\n<title>Node10</title>\n<g id=\"a_Node000010\"><a xlink:href=\"classcom_1_1myhome_1_1domain_1_1SecurityToken.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"507.5,-67.5 340.5,-67.5 340.5,-37.5 507.5,-37.5 507.5,-67.5\"/>\n<text text-anchor=\"start\" x=\"348.5\" y=\"-55.5\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.myhome.domain.Security</text>\n<text text-anchor=\"middle\" x=\"424\" y=\"-44.5\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">Token</text>\n</a>\n</g>\n</g>\n<!-- Node1&#45;&gt;Node10 -->\n<g id=\"edge9_Node000001_Node000010\" class=\"edge\">\n<title>Node1&#45;&gt;Node10</title>\n<g id=\"a_edge9_Node000001_Node000010\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M215.63,-164.55C237.46,-141.17 280.13,-99.31 325,-76.5 331.79,-73.05 339.11,-70.07 346.57,-67.52\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"212.99,-162.26 208.81,-171.99 218.15,-166.99 212.99,-162.26\"/>\n</a>\n</g>\n</g>\n<!-- Node11 -->\n<g id=\"Node000011\" class=\"node\">\n<title>Node11</title>\n<g id=\"a_Node000011\"><a xlink:href=\"classcom_1_1myhome_1_1domain_1_1User.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"498.5,-19 349.5,-19 349.5,0 498.5,0 498.5,-19\"/>\n<text text-anchor=\"middle\" x=\"424\" y=\"-7\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.myhome.domain.User</text>\n</a>\n</g>\n</g>\n<!-- Node1&#45;&gt;Node11 -->\n<g id=\"edge10_Node000001_Node000011\" class=\"edge\">\n<title>Node1&#45;&gt;Node11</title>\n<g id=\"a_edge10_Node000001_Node000011\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M210.09,-162.59C227.9,-129.59 269.35,-61.83 325,-28.5 332.51,-24 340.87,-20.54 349.45,-17.88\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"206.89,-161.16 205.34,-171.64 213.09,-164.42 206.89,-161.16\"/>\n</a>\n</g>\n</g>\n<!-- Node2 -->\n<g id=\"Node000002\" class=\"node\">\n<title>Node2</title>\n<g id=\"a_Node000002\"><a xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"74,-191 0,-191 0,-172 74,-172 74,-191\"/>\n<text text-anchor=\"middle\" x=\"37\" y=\"-179\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">Serializable</text>\n</a>\n</g>\n</g>\n<!-- Node2&#45;&gt;Node1 -->\n<g id=\"edge1_Node000001_Node000002\" class=\"edge\">\n<title>Node2&#45;&gt;Node1</title>\n<g id=\"a_edge1_Node000001_Node000002\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M84.03,-181.5C92.2,-181.5 100.96,-181.5 109.86,-181.5\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"84.02,-178 74.02,-181.5 84.02,-185 84.02,-178\"/>\n</a>\n</g>\n</g>\n</g>\n</svg>\n","items":[{"id":"0592f44f-3443-2b91-6548-f512930b91dc","ancestors":[],"type":"function","description":"in the given Java file defines a new entity with a unique document filename and binary data content.\nFields:\n\t- documentFilename (String): in the HouseMemberDocument class represents the filename of a document. \n\t- documentContent (byte[]): in the HouseMemberDocument class contains a byte array of unknown length. \n\n","name":"HouseMemberDocument","code":"@Entity\n@AllArgsConstructor\n@NoArgsConstructor\n@Data\n@EqualsAndHashCode(of = {\"documentFilename\"}, callSuper = false)\npublic class HouseMemberDocument extends BaseEntity {\n\n  @Column(unique = true)\n  private String documentFilename;\n\n  @Lob\n  @Column\n  private byte[] documentContent = new byte[0];\n}","location":{"start":27,"insert":27,"offset":" ","indent":0},"item_type":"class","length":14}]}}}