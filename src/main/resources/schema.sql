DROP TABLE IF EXISTS "public"."cf_mapping_ffm_lm";
CREATE TABLE "public"."cf_mapping_ffm_lm"
(
    "ffm_id"      int4 NOT NULL,
    "lastmile_id" int4 NOT NULL,

    PRIMARY KEY ("ffm_id", "lastmile_id")
);