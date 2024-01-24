CREATE TABLE IF NOT EXISTS users (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(512) NOT NULL,
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
 );

CREATE TABLE IF NOT EXISTS requests (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    description varchar(512),
    created timestamp NOT NULL,
    CONSTRAINT requests_users FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS items (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    owner_id BIGINT NOT NULL,
    name varchar(255) NOT NULL,
    description varchar(512),
    is_available Boolean DEFAULT true,
    request_id BIGINT,
    CONSTRAINT item_users FOREIGN KEY (owner_id) REFERENCES users(id),
    CONSTRAINT item_requests FOREIGN KEY (request_id) REFERENCES requests(id)
);

CREATE TABLE IF NOT EXISTS bookings (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    start_date timestamp NOT NULL,
    end_date timestamp NOT NULL,
    item_id BIGINT NOT NULL,
    booker_id BIGINT NOT NULL,
    status VARCHAR(255) NOT NULL,
    CONSTRAINT booking_users FOREIGN KEY (booker_id) REFERENCES users(id),
    CONSTRAINT booking_item FOREIGN KEY (item_id) REFERENCES items(id)
);


CREATE TABLE IF NOT EXISTS comments (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    description varchar(512) NOT NULL,
    item_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    created timestamp NOT NULL,
    CONSTRAINT comments_item FOREIGN KEY (item_id) REFERENCES items(id),
    CONSTRAINT comments_users FOREIGN KEY (author_id) REFERENCES users(id)
);