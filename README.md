This program is an example of an AI that is trained to recognise characters from the English alphabet. It is trained on the EMNIST dataset downloaded here: https://www.kaggle.com/datasets/tomasramos21/emnist-jpeg

Build instructions
1. In the root directory: 'mvn clean package install'.
2. In the maven build directory (usually "target"), take ai-example-x.x.x.jar and the "libs/" folder for your deployment.
3. You will also need "pub/" (only for presentation implementations) and "config.json" for your deployment.

Configuration
1. "layer": Specifies what kind of operating mode for the server. There are 5 valid values, default value is "allInOne".
	a. "allInOne": The server will do everything below. This is the only layer configuration that does not require apache nginx.
	b. "presentation": The server will serve the static content (inside of pub/).
	c. "application": The server will handle API request.
	d. "database": The program will manage database schema migrations.
	e. "aiTrainer": The program will periodically train new AI models.
2. "kafkaEnabled": Tells the software whether you want to enable kafka to handle event updates. This is true by default and should only be disabled in dev environments.
3. "kafkaServer": The server address for kafka, "localhost:9092" by default.
4. "serverUrl": The url of the server, including the protocol but not the port. "http://localhost" by default.
5. "serverPort": The port of the server, 80 by default.
6. "dataSourceHostName": The hostname of the database, "localhost" by default.
7. "dataSourceDatabase": The database name, "ai_example" by default.
8. "dataSourceUser": The username for the database user "postgres" by default.
9. "dataSourcePassword": The password for the database user "postgres" by default.
10. "trainingPeriodMinutes": Amount of minutes between each attempt to train a better AI model, 60 by default.

Apache nginx
As mentioned earlier, if you deploy this application in a scalable manner using the supported n-tier architecture, you will need to use nginx as a load balancer. Here is an example of some nginx config: https://github.com/t0rrent/scalability-example/blob/master/nginx.conf

Apache kafka
A basic kafka setup is provided in "kafka/". This will require docker compose and can be run with "docker-compose up -d".
The AI trainer pipeline will publish an event when a new AI model has been trained so that application servers know when it may be time to load a better model.
