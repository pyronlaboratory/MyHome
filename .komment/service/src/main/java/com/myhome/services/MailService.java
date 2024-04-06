{"name":"MailService.java","path":"service/src/main/java/com/myhome/services/MailService.java","content":{"structured":{"description":"An interface for a Mail Service that allows users to send various messages related to account management and security. The interface provides four methods: `sendPasswordRecoverCode`, `sendAccountCreated`, `sendPasswordSuccessfullyChanged`, and `sendAccountConfirmed`. These methods allow the mail service to send messages to users based on specific events, such as password recovery codes, account creation confirmations, and password changes.","image":"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\n \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<!-- Generated by graphviz version 2.43.0 (0)\n -->\n<!-- Title: com.myhome.domain.SecurityToken Pages: 1 -->\n<svg width=\"187pt\" height=\"148pt\"\n viewBox=\"0.00 0.00 187.00 148.00\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n<g id=\"graph0\" class=\"graph\" transform=\"scale(1 1) rotate(0) translate(4 144)\">\n<title>com.myhome.domain.SecurityToken</title>\n<!-- Node1 -->\n<g id=\"Node000001\" class=\"node\">\n<title>Node1</title>\n<g id=\"a_Node000001\"><a xlink:title=\" \">\n<polygon fill=\"#999999\" stroke=\"#666666\" points=\"173,-30 6,-30 6,0 173,0 173,-30\"/>\n<text text-anchor=\"start\" x=\"14\" y=\"-18\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.myhome.domain.Security</text>\n<text text-anchor=\"middle\" x=\"89.5\" y=\"-7\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">Token</text>\n</a>\n</g>\n</g>\n<!-- Node2 -->\n<g id=\"Node000002\" class=\"node\">\n<title>Node2</title>\n<g id=\"a_Node000002\"><a xlink:href=\"classcom_1_1myhome_1_1domain_1_1BaseEntity.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"179,-85 0,-85 0,-66 179,-66 179,-85\"/>\n<text text-anchor=\"middle\" x=\"89.5\" y=\"-73\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.myhome.domain.BaseEntity</text>\n</a>\n</g>\n</g>\n<!-- Node2&#45;&gt;Node1 -->\n<g id=\"edge1_Node000001_Node000002\" class=\"edge\">\n<title>Node2&#45;&gt;Node1</title>\n<g id=\"a_edge1_Node000001_Node000002\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M89.5,-55.65C89.5,-47.36 89.5,-37.78 89.5,-30.11\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"86,-55.87 89.5,-65.87 93,-55.87 86,-55.87\"/>\n</a>\n</g>\n</g>\n<!-- Node3 -->\n<g id=\"Node000003\" class=\"node\">\n<title>Node3</title>\n<g id=\"a_Node000003\"><a xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"126.5,-140 52.5,-140 52.5,-121 126.5,-121 126.5,-140\"/>\n<text text-anchor=\"middle\" x=\"89.5\" y=\"-128\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">Serializable</text>\n</a>\n</g>\n</g>\n<!-- Node3&#45;&gt;Node2 -->\n<g id=\"edge2_Node000002_Node000003\" class=\"edge\">\n<title>Node3&#45;&gt;Node2</title>\n<g id=\"a_edge2_Node000002_Node000003\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M89.5,-110.66C89.5,-101.93 89.5,-91.99 89.5,-85.09\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"86,-110.75 89.5,-120.75 93,-110.75 86,-110.75\"/>\n</a>\n</g>\n</g>\n</g>\n</svg>\n","items":[{"id":"801a93b9-b10f-a1a6-5542-9252a2e27299","ancestors":[],"type":"function","description":"allows for sending password recovery codes, account creation confirmation tokens, and successful password changes, as well as confirming account status.","name":"MailService","code":"public interface MailService {\n\n  boolean sendPasswordRecoverCode(User user, String randomCode);\n\n  boolean sendAccountCreated(User user, SecurityToken emailConfirmToken);\n\n  boolean sendPasswordSuccessfullyChanged(User user);\n\n  boolean sendAccountConfirmed(User user);\n}","location":{"start":6,"insert":6,"offset":" ","indent":0},"item_type":"interface","length":10}]}}}