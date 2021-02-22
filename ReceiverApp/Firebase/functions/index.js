const functions = require("firebase-functions");
const admin = require("firebase-admin");

// Initialise the app
admin.initializeApp();

// Function for notifying the admins that an order has been placed
exports.orderPlaced = functions.database.ref("Orders/{orderID}/")
    .onCreate((snapshot, context) => {
        const id = "ID123";
        console.log("There is a new order with ID: " + id);

        const payload = {
            notification: {
                title: id,
            },
        };

        return admin.messaging().sendToTopic("NewOrder", payload);
    });

// Function for order updated

// Leave a blank line below or it will not work!!
