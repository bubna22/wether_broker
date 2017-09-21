CREATE TABLE queries (
    query_id bigserial PRIMARY KEY,
    query_count INTEGER,
    query_created TIMESTAMP,
    query_lang VARCHAR(10),
    query_channel INTEGER
);

CREATE UNIQUE INDEX query_created_ui ON queries(query_created);

CREATE TABLE units (
    units_id bigserial PRIMARY KEY,
    units_distance VARCHAR(1024),
    units_pressure VARCHAR(1024),
    units_speed VARCHAR(1024),
    units_temperature VARCHAR(1024)
);

CREATE TABLE channels (
    channel_id bigserial PRIMARY KEY,
    channel_units INTEGER,
    channel_title VARCHAR(1024),
    channel_link VARCHAR(1024),
    channel_desc VARCHAR(2048),
    channel_lang VARCHAR(10),
    channel_last_build TIMESTAMP,
    channel_ttl INTEGER,
    channel_location INTEGER,
    channel_wind INTEGER,
    channel_atmosphere INTEGER,
    channel_astronomy INTEGER,
    channel_image INTEGER,
    channel_item INTEGER
);

CREATE TABLE locations (
    loc_id bigserial PRIMARY KEY,
    loc_city VARCHAR(1024),
    loc_country VARCHAR(1024),
    loc_region VARCHAR(1024)
);

CREATE TABLE winds (
    wind_id bigserial PRIMARY KEY,
    wind_chill INTEGER,
    wind_direction INTEGER,
    wind_speed INTEGER
);

CREATE TABLE atmospheres (
    atmo_id BIGSERIAL PRIMARY KEY,
    atmo_humidity INTEGER,
    atmo_pressure REAL,
    atmo_rising INTEGER,
    atmo_visibility REAL
);

CREATE TABLE astronomy (
    astro_id bigserial PRIMARY KEY,
    astro_sunrise TIMESTAMP,
    astro_sunset TIMESTAMP
);

CREATE TABLE images (
    image_id bigserial PRIMARY KEY,
    image_title VARCHAR(1024),
    image_width INTEGER,
    image_height INTEGER,
    image_link VARCHAR(1024),
    image_url VARCHAR(1024)
);

CREATE TABLE items (
    item_id bigserial PRIMARY KEY,
    item_title VARCHAR(1024),
    item_lat REAL,
    item_long REAL,
    item_link VARCHAR(1024),
    item_desc VARCHAR(2048),
    item_pub_date TIMESTAMP,
    item_condition INTEGER
);

CREATE TABLE conditions (
    condition_id bigserial PRIMARY KEY,
    condition_code INTEGER,
    condition_date TIMESTAMP,
    condition_temp INTEGER,
    condition_text VARCHAR(1024)
);

CREATE TABLE forecasts (
    forecast_id bigserial PRIMARY KEY,
    forecast_date TIMESTAMP,
    forecast_day VARCHAR(10),
    forecast_high INTEGER,
    forecast_low INTEGER,
    forecast_text VARCHAR(1024),
    forecast_item INTEGER
);