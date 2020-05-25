package am.ik.lab.tweet;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public class TweetMapper {
    private final JdbcTemplate jdbcTemplate;
    final RowMapper<Tweet> tweetRowMapper = (rs, i) -> new Tweet(UUID.fromString(rs.getString("uuid")),
            rs.getString("text"),
            rs.getTimestamp("created_at").toInstant());

    public TweetMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public int insert(Tweet tweet) {
        return this.jdbcTemplate.update(
                "INSERT INTO tweets(uuid, text, created_at) VALUES(?,?,?)",
                tweet.getUuid(), tweet.getText(),
                tweet.getCreatedAt());
    }

    public long count() {
        return this.jdbcTemplate.queryForObject("SELECT count(*) FROM tweets",
                Long.class);
    }


    public Tweet findOne(UUID uuid) {
        return this.jdbcTemplate.queryForObject(
                "SELECT uuid, text, created_at FROM tweets WHERE uuid = ?",
                tweetRowMapper, uuid);
    }

    public List<Tweet> findAll() {
        return this.jdbcTemplate.query(
                "SELECT uuid, text, created_at FROM tweets",
                tweetRowMapper);
    }

    @Transactional
    public int delete(UUID uuid) {
        return this.jdbcTemplate.update("DELETE FROM tweets WHERE uuid = ?", uuid);
    }
}
