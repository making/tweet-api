package am.ik.lab.tweet;

import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public class TweetMapper {
	private final JdbcAggregateTemplate jdbcAggregateTemplate;

    public TweetMapper(JdbcAggregateTemplate jdbcAggregateTemplate) {
		this.jdbcAggregateTemplate = jdbcAggregateTemplate;
	}

    @Transactional
    public Tweet insert(Tweet tweet) {
        return this.jdbcAggregateTemplate.insert(tweet);
    }

    public long count() {
        return this.jdbcAggregateTemplate.count(Tweet.class);
    }

    public Tweet findOne(UUID uuid) {
    	return this.jdbcAggregateTemplate.findById(uuid, Tweet.class);
    }

    public List<Tweet> findAll() {
        return (List<Tweet>) this.jdbcAggregateTemplate.findAll(Tweet.class);
    }

    @Transactional
    public void delete(UUID uuid) {
    	this.jdbcAggregateTemplate.deleteById(uuid, Tweet.class);
    }
}
