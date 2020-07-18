package am.ik.lab.tweet;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("TWEETS")
public class Tweet {
	@Id
    private final UUID uuid;
    private final String text;
    private final Instant createdAt;

    public Tweet(UUID uuid, String text, Instant createdAt) {
        this.uuid = uuid;
        this.text = text;
        this.createdAt = createdAt;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getText() {
        return text;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
