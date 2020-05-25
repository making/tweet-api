package am.ik.lab.tweet;

import am.ik.lab.tweet.spec.TweetRequest;
import am.ik.lab.tweet.spec.TweetResponse;
import am.ik.lab.tweet.spec.TweetsApi;
import org.springframework.http.ResponseEntity;
import org.springframework.util.IdGenerator;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class TweetController implements TweetsApi {
    private final TweetMapper tweetMapper;
    private final IdGenerator idGenerator;
    private final Clock clock;

    public TweetController(TweetMapper tweetMapper, IdGenerator idGenerator, Clock clock) {
        this.tweetMapper = tweetMapper;
        this.idGenerator = idGenerator;
        this.clock = clock;
    }

    @Override
    public ResponseEntity<Void> deleteTweetsUuid(UUID uuid) {
        this.tweetMapper.delete(uuid);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<TweetResponse>> getTweets() {
        final List<Tweet> tweets = this.tweetMapper.findAll();
        return ResponseEntity.ok(tweets.stream()
                .map(this::toResponse)
                .collect(Collectors.toUnmodifiableList()));
    }

    @Override
    public ResponseEntity<TweetResponse> getTweetsUuid(UUID uuid) {
        final Tweet tweet = this.tweetMapper.findOne(uuid);
        return ResponseEntity.ok(this.toResponse(tweet));
    }

    @Override
    public ResponseEntity<TweetResponse> postTweets(TweetRequest tweetRequest) {
        final Tweet tweet = this.fromRequest(tweetRequest);
        this.tweetMapper.insert(tweet);
        return ResponseEntity.ok(this.toResponse(tweet));
    }

    Tweet fromRequest(TweetRequest request) {
        return new Tweet(this.idGenerator.generateId(), request.getText(), Instant.now(this.clock));
    }

    TweetResponse toResponse(Tweet tweet) {
        return new TweetResponse()
                .uuid(tweet.getUuid())
                .text(tweet.getText())
                .createdAt(OffsetDateTime.ofInstant(tweet.getCreatedAt(), ZoneId.systemDefault()));
    }
}
