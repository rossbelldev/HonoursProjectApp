const functions = require("firebase-functions");

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//   functions.logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });

exports.orderPlaced = functions.database.ref("Orders/{OrderID}/")
    .onCreate((snapshot, context) => {
        console.log("There is a new order");
    });

// Leave a blank line
