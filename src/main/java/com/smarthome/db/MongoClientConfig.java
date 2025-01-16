import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoClientConfig {

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb+srv://ducky:vbTQ078mO3QQyCCL@smarthomecluster.cgtsx.mongodb.net/smart_home?retryWrites=true&w=majority");
    }

    @Bean
    public MongoDatabase mongoDatabase(MongoClient mongoClient) {
        return mongoClient.getDatabase("smarthome-automation"); // Replace "smart_home" with your database name
    }
}
