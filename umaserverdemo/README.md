# UMA Demo Server

An example UMA VASP server implementation using kotlin.

## Running the server

Configuration parameters (API keys, etc.) and information on how to set them can be found in `UmaConfig.kt`.
To run locally on your machine, from the kotlin-sdk directory, run:

```bash
./gradlew umaserverdemo:run
```

This will run the server on port 8080. You can change the port by setting the `PORT` environment variable.

To run the server in a docker container, from the kotlin-sdk directory, run:

```bash
docker build . -t uma-linux-amd64 -f umaserverdemo/Dockerfile
```

Then run with your desired port and environment config:

```bash
docker run -p 8080:8080 \
-e PORT=8080 \
-e LIGHTSPARK_API_TOKEN_CLIENT_ID=$LIGHTSPARK_API_TOKEN_CLIENT_ID \
-e LIGHTSPARK_API_TOKEN_CLIENT_SECRET=$LIGHTSPARK_API_TOKEN_CLIENT_SECRET \
-e LIGHTSPARK_UMA_NODE_ID=$LIGHTSPARK_UMA_NODE_ID \
-e LIGHTSPARK_UMA_RECEIVER_USER=$LIGHTSPARK_UMA_RECEIVER_USER \
-e LIGHTSPARK_UMA_ENCRYPTION_PUBKEY=$LIGHTSPARK_UMA_ENCRYPTION_PUBKEY \
-e LIGHTSPARK_UMA_ENCRYPTION_PRIVKEY=$LIGHTSPARK_UMA_ENCRYPTION_PRIVKEY \
-e LIGHTSPARK_UMA_SIGNING_PUBKEY=$LIGHTSPARK_UMA_SIGNING_PUBKEY \
-e LIGHTSPARK_UMA_SIGNING_PRIVKEY=$LIGHTSPARK_UMA_SIGNING_PRIVKEY \
uma-linux-amd64
```

## Running Test Queries

First, we'll start two instances of the server, one on port 8080 and one on port 8081 (in separate terminals):

Terminal 1:

```bash
# First set up config variables. You can also save these in a file or export them to your environment.
$ export LIGHTSPARK_API_TOKEN_CLIENT_ID=<client_id>
$ export LIGHTSPARK_API_TOKEN_CLIENT_SECRET=<client_secret>
# etc... See UmaConfig.kt for the full list of config variables.

# Now start the server on port 8080
$ PORT=8080 ./gradlew umaserverdemo:run
```

Terminal 2:

```bash
# First set up the variables as above. If you want to be able to actually send payments, use a different account.
$ export LIGHTSPARK_API_TOKEN_CLIENT_ID=<client_id_2>
$ export LIGHTSPARK_API_TOKEN_CLIENT_SECRET=<client_secret_2>
# etc... See UmaConfig.kt for the full list of config variables.

# Now start the server on port 8081
$ PORT=8081 ./gradlew umaserverdemo:run
```

Now, you can test the full uma flow like:

```bash
# First, call to vasp1 to lookup Bob at vasp2. This will return currency conversion info, etc. It will also contain a 
# callback ID that you'll need for the next call
$ curl -X GET http://localhost:8080/api/umalookup/\$bob@localhost:8081

# Now, call to vasp1 to get a payment request from vasp2. Replace the last path component here with the callbackUuid
# from the previous call. This will return an invoice and another callback ID that you'll need for the next call.
$ curl -X GET "http://localhost:8080/api/umapayreq/52ca86cd-62ed-4110-9774-4e07b9aa1f0e?amount=100&currencyCode=USD"

# Now, call to vasp1 to send the payment. Replace the last path component here with the callbackUuid from the payreq
# call. This will return a payment ID that you can use to check the status of the payment.
curl -X POST http://localhost:8080/api/sendpayment/e26cbee9-f09d-4ada-a731-965cbd043d50
```
