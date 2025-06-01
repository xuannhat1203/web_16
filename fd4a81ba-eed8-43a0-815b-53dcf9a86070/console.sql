CREATE DATABASE session16;
USE session16;

CREATE TABLE User
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50)  NOT NULL,
    password VARCHAR(50)  NOT NULL,
    email    VARCHAR(100) NOT NULL UNIQUE,
    role     ENUM ('admin', 'user') DEFAULT 'user',
    status   BIT          NOT NULL  DEFAULT 1
);

CREATE TABLE trip
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    departure_point VARCHAR(100) NOT NULL,
    destination     VARCHAR(100) NOT NULL,
    departure_time  DATETIME     NOT NULL,
    arrival_time    DATETIME     NOT NULL,
    bus_id          INT          NOT NULL,
    seats_available INT          NOT NULL,
    image           VARCHAR(255) NOT NULL,
    FOREIGN KEY (bus_id) REFERENCES Bus(id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO trip (departure_point, destination, departure_time, arrival_time, bus_id, seats_available, image)
VALUES
    ('Hà Nội', 'Đà Nẵng', '2025-06-05 08:00:00', '2025-06-05 20:00:00', 1, 20, 'image/2.jpg'),
    ('Hà Nội', 'Huế', '2025-06-05 10:00:00', '2025-06-05 18:00:00', 1, 15, 'image/3.jpg'),
    ('Hà Nội', 'Hồ Chí Minh', '2025-06-06 09:30:00', '2025-06-07 10:30:00', 2, 24, 'image/4.jpg'),
    ('Đà Nẵng', 'Hồ Chí Minh', '2025-06-07 07:45:00', '2025-06-07 18:45:00', 3, 28, 'image/5.jpg'),
    ('Hà Nội', 'Hải Phòng', '2025-06-08 13:15:00', '2025-06-08 15:15:00', 4, 25, 'image/6.jpg'),
    ('Huế', 'Hồ Chí Minh', '2025-06-09 06:00:00', '2025-06-09 22:00:00', 5, 30, 'image/7.jpg');

DELIMITER //
CREATE PROCEDURE get_all_trips(IN p_offset INT, IN p_limit INT)
BEGIN
    SELECT id, departure_point, destination, departure_time, arrival_time, bus_id, seats_available, image
    FROM trip
    LIMIT p_limit OFFSET p_offset;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE get_all_trips_no_paging()
BEGIN
    SELECT id, departure_point, destination, departure_time, arrival_time, bus_id, seats_available, image
    FROM trip;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE search_trips_with_paging (
    IN dep VARCHAR(255),
    IN dest VARCHAR(255),
    IN offset_val INT,
    IN limit_val INT
)
BEGIN
    SELECT *
    FROM trip
    WHERE
        (dep = '' OR departure_point LIKE CONCAT('%', dep, '%'))
       OR
        (dest = '' OR destination LIKE CONCAT('%', dest, '%'))
    LIMIT limit_val OFFSET offset_val;
END;

DELIMITER ;

DELIMITER //
CREATE PROCEDURE count_all_trips()
BEGIN
    SELECT COUNT(*) AS total
    FROM trip;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE count_trips_by_point (
    IN dep VARCHAR(255),
    IN dest VARCHAR(255)
)
BEGIN
    SELECT COUNT(*) AS total
    FROM trip
    WHERE
        (dep = '' OR departure_point LIKE CONCAT('%', dep, '%'))
       OR
        (dest = '' OR destination LIKE CONCAT('%', dest, '%'));
END;
DELIMITER ;
DELIMITER //
CREATE PROCEDURE sp_add_trip(
    IN p_departure_point VARCHAR(100),
    IN p_destination VARCHAR(100),
    IN p_departure_time DATETIME,
    IN p_arrival_time DATETIME,
    IN p_bus_id INT,
    IN p_seats_available INT,
    IN p_image VARCHAR(255)
)
BEGIN
    INSERT INTO trip (departure_point, destination, departure_time, arrival_time, bus_id, seats_available, image)
    VALUES (p_departure_point, p_destination, p_departure_time, p_arrival_time, p_bus_id, p_seats_available, p_image);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE sp_update_trip(
    IN p_id INT,
    IN p_departure_point VARCHAR(100),
    IN p_destination VARCHAR(100),
    IN p_departure_time DATETIME,
    IN p_arrival_time DATETIME,
    IN p_bus_id INT,
    IN p_seats_available INT,
    IN p_image VARCHAR(255)
)
BEGIN
    UPDATE trip
    SET departure_point = p_departure_point,
        destination = p_destination,
        departure_time = p_departure_time,
        arrival_time = p_arrival_time,
        bus_id = p_bus_id,
        seats_available = p_seats_available,
        image = p_image
    WHERE id = p_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE sp_delete_trip(
    IN p_id INT
)
BEGIN
    DELETE FROM trip WHERE id = p_id;
END //
DELIMITER ;
drop procedure count_trips_by_point;
drop procedure count_all_trips;
drop procedure search_trips_with_paging;
drop procedure get_all_trips_no_paging;
drop procedure get_all_trips;
drop table trip;
DELIMITER //
CREATE PROCEDURE get_all_users()
BEGIN
    SELECT * FROM User;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE add_new_user(
    IN p_username VARCHAR(50),
    IN p_password VARCHAR(50),
    IN p_email VARCHAR(100)
)
BEGIN
    INSERT INTO User (username, password, email)
    VALUES (p_username, p_password, p_email);
END //
DELIMITER ;

create table Bus
(
    id            int auto_increment primary key,
    license_plate varchar(20)                    not null unique,
    bus_type      enum ('vip','luxury','normal') not null,
    rowSeats      int                            not null,
    columnSeats   int                            not null,
    totalSeats    int as (rowSeats * columnSeats) stored,
    image         varchar(255)                   not null
);
CREATE TABLE Seat
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    nameSeat VARCHAR(10)                    NOT NULL,
    bus_type ENUM ('vip','luxury','normal') NOT NULL,
    price    DOUBLE GENERATED ALWAYS AS (
        CASE
            WHEN bus_type = 'vip' THEN 200000
            WHEN bus_type = 'luxury' THEN 150000
            WHEN bus_type = 'normal' THEN 100000
            ELSE 0
            END
        ) STORED,
    bus_id   INT                            NOT NULL,
    status   BIT                            NOT NULL DEFAULT 1,
    FOREIGN KEY (bus_id) REFERENCES Bus (id) ON DELETE CASCADE ON UPDATE CASCADE
);
INSERT INTO Bus (license_plate, bus_type, rowSeats, columnSeats, image)
VALUES ('51A-12345', 'vip', 5, 4, 'image/1.jpg'),
       ('51B-23456', 'luxury', 6, 4, 'image/2.jpg'),
       ('51C-34567', 'normal', 7, 4, 'image/3.jpg'),
       ('51D-45678', 'vip', 5, 5, 'image/4.jpg'),
       ('51E-56789', 'luxury', 6, 5, 'image/5.jpg'),
       ('51F-67890', 'normal', 8, 4, 'image/6.jpg'),
       ('51G-78901', 'vip', 5, 6, 'image/7.jpg'),
       ('51H-89012', 'luxury', 7, 4, 'image/8.jpg'),
       ('51K-90123', 'normal', 8, 5, 'image/9.jpg'),
       ('51L-01234', 'vip', 6, 4, 'image/10.jpg');

delimiter \\
create procedure get_all_buses()
begin
    select * from Bus;
end \\
delimiter \\;
delimiter \\

create procedure sp_add_bus(
    in p_license_plate varchar(20),
    in p_bus_type enum ('vip','luxury','normal'),
    in p_rowSeats int,
    in p_columnSeats int,
    in p_image varchar(255)
)
begin
    declare v_bus_id int;
    declare r int default 1;
    declare c int default 1;
    declare v_nameSeat varchar(10);

    insert into bus(license_plate, bus_type, rowSeats, columnSeats, image)
    values (p_license_plate, p_bus_type, p_rowSeats, p_columnSeats, p_image);

    set v_bus_id = last_insert_id();

    set r = 1;
    while r <= p_rowSeats
        do
            set c = 1;
            while c <= p_columnSeats
                do
                    set v_nameSeat = concat(char(64 + r), c);
                    insert into seat(nameSeat, bus_type, bus_id)
                    values (v_nameSeat, p_bus_type, v_bus_id);
                    set c = c + 1;
                end while;
            set r = r + 1;
        end while;
end\\

delimiter ;
delimiter \\

create procedure sp_update_bus(
    in p_id int,
    in p_license_plate varchar(20),
    in p_bus_type enum ('vip','luxury','normal'),
    in p_rowSeats int,
    in p_columnSeats int,
    in p_image varchar(255)
)
begin
    update bus
    set license_plate = p_license_plate,
        bus_type      = p_bus_type,
        rowSeats      = p_rowSeats,
        columnSeats   = p_columnSeats,
        image         = p_image
    where id = p_id;
end\\

delimiter ;
delimiter \\

create procedure sp_delete_bus(
    in p_id int
)
begin
    delete from bus where id = p_id;
end\\

delimiter ;


DELIMITER //
CREATE PROCEDURE search_trips_by_departure (
    IN dep VARCHAR(255),
    IN offset_val INT,
    IN limit_val INT
)
BEGIN
    SELECT *
    FROM trip
    WHERE departure_point LIKE CONCAT('%', dep, '%')
    LIMIT limit_val OFFSET offset_val;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE count_trips_by_departure (
    IN dep VARCHAR(255)
)
BEGIN
    SELECT COUNT(*) AS total
    FROM trip
    WHERE departure_point LIKE CONCAT('%', dep, '%');
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE search_trips_by_destination (
    IN dest VARCHAR(255),
    IN offset_val INT,
    IN limit_val INT
)
BEGIN
    SELECT *
    FROM trip
    WHERE destination LIKE CONCAT('%', dest, '%')
    LIMIT limit_val OFFSET offset_val;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE count_trips_by_destination (
    IN dest VARCHAR(255)
)
BEGIN
    SELECT COUNT(*) AS total
    FROM trip
    WHERE destination LIKE CONCAT('%', dest, '%');
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE search_trips_by_departure_or_destination (
    IN dep VARCHAR(255),
    IN dest VARCHAR(255),
    IN offset_val INT,
    IN limit_val INT
)
BEGIN
    SELECT *
    FROM trip
    WHERE departure_point LIKE CONCAT('%', dep, '%')
       OR destination LIKE CONCAT('%', dest, '%')
    LIMIT limit_val OFFSET offset_val;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE count_trips_by_departure_or_destination (
    IN dep VARCHAR(255),
    IN dest VARCHAR(255)
)
BEGIN
    SELECT COUNT(*) AS total
    FROM trip
    WHERE departure_point LIKE CONCAT('%', dep, '%')
       OR destination LIKE CONCAT('%', dest, '%');
END //
DELIMITER ;
