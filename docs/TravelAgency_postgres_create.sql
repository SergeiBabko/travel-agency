CREATE TABLE "customer" (
	"id" integer NOT NULL,
	"status" character varying NOT NULL,
	"middle_name" character varying,
	"birthday" character varying,
	"passport_number" character varying,
	"passport_start" character varying,
	"passport_end" character varying,
	"phone_number" character varying,
	"country" character varying,
	"city" character varying,
	"street" character varying,
	"bonus" integer,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT customer_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "favorite" (
	"id" serial NOT NULL,
	"customer_id" integer NOT NULL,
	"tour_id" integer NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT favorite_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "tour" (
	"id" serial NOT NULL,
	"version" integer NOT NULL DEFAULT '1',
	"tour_status" character varying NOT NULL,
	"name" character varying NOT NULL,
	"price" integer NOT NULL,
	"tour_type_id" integer NOT NULL,
	"nights" integer NOT NULL,
	"image" character varying,
	"description" TEXT NOT NULL,
	"city_id" integer NOT NULL,
	"address" character varying NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT tour_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "review" (
	"id" serial NOT NULL,
	"review" TEXT NOT NULL,
	"rating" integer NOT NULL,
	"customer_id" integer NOT NULL,
	"tour_date_id" integer NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT review_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "ta_description" (
	"id" serial NOT NULL,
	"description" TEXT NOT NULL,
	"contacts" TEXT NOT NULL,
	"address" character varying NOT NULL,
	"image_1" character varying,
	"image_2" character varying,
	"image_3" character varying,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT ta_description_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "booked" (
	"id" serial NOT NULL,
	"customer_id" integer NOT NULL,
	"tour_date_id" integer NOT NULL,
	"num_person" integer,
	"price" integer NOT NULL,
	"message" TEXT,
	"processed" BOOLEAN NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT booked_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "user_account" (
	"id" serial NOT NULL,
	"email" character varying NOT NULL UNIQUE,
	"password" character varying NOT NULL,
	"first_name" character varying,
	"last_name" character varying,
	"role" character varying NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT user_account_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "tour_date" (
	"id" serial NOT NULL,
	"tour_id" integer NOT NULL,
	"num_person" integer NOT NULL,
	"date_start" DATE NOT NULL,
	"date_end" DATE NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT tour_date_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "country" (
	"id" serial NOT NULL,
	"name" character varying NOT NULL UNIQUE,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT country_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "city" (
	"id" serial NOT NULL,
	"name" character varying NOT NULL UNIQUE,
	"country_id" integer NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT city_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "tour_type" (
	"id" serial NOT NULL,
	"type" character varying NOT NULL UNIQUE,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT tour_type_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "news" (
	"id" serial NOT NULL,
	"version" integer NOT NULL DEFAULT '1',
	"name" character varying(100) NOT NULL,
	"image" character varying,
	"text" TEXT NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT news_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



ALTER TABLE "customer" ADD CONSTRAINT "customer_fk0" FOREIGN KEY ("id") REFERENCES "user_account"("id");

ALTER TABLE "favorite" ADD CONSTRAINT "favorite_fk0" FOREIGN KEY ("customer_id") REFERENCES "customer"("id");
ALTER TABLE "favorite" ADD CONSTRAINT "favorite_fk1" FOREIGN KEY ("tour_id") REFERENCES "tour"("id");

ALTER TABLE "tour" ADD CONSTRAINT "tour_fk0" FOREIGN KEY ("tour_type_id") REFERENCES "tour_type"("id");
ALTER TABLE "tour" ADD CONSTRAINT "tour_fk1" FOREIGN KEY ("city_id") REFERENCES "city"("id");

ALTER TABLE "review" ADD CONSTRAINT "review_fk0" FOREIGN KEY ("customer_id") REFERENCES "customer"("id");
ALTER TABLE "review" ADD CONSTRAINT "review_fk1" FOREIGN KEY ("tour_date_id") REFERENCES "tour_date"("id");


ALTER TABLE "booked" ADD CONSTRAINT "booked_fk0" FOREIGN KEY ("customer_id") REFERENCES "customer"("id");
ALTER TABLE "booked" ADD CONSTRAINT "booked_fk1" FOREIGN KEY ("tour_date_id") REFERENCES "tour_date"("id");


ALTER TABLE "tour_date" ADD CONSTRAINT "tour_date_fk0" FOREIGN KEY ("tour_id") REFERENCES "tour"("id");


ALTER TABLE "city" ADD CONSTRAINT "city_fk0" FOREIGN KEY ("country_id") REFERENCES "country"("id");



