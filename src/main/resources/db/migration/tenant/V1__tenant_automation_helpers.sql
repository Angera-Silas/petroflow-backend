CREATE OR REPLACE FUNCTION fn_common_touch_updated_at()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$;

COMMENT ON FUNCTION fn_common_touch_updated_at()
IS 'Tenant-local helper to stamp updated_at columns on operational tables.';

CREATE OR REPLACE FUNCTION fn_reporting_mark_summary_stale()
RETURNS trigger
LANGUAGE plpgsql
AS $$
DECLARE
    payload text;
BEGIN
    payload = json_build_object(
        'schema', current_schema(),
        'table', TG_TABLE_NAME,
        'operation', TG_OP,
        'occurredAt', CURRENT_TIMESTAMP
    )::text;

    PERFORM pg_notify('petroflow_summary_refresh', payload);

    IF TG_OP = 'DELETE' THEN
        RETURN OLD;
    END IF;

    RETURN NEW;
END;
$$;

COMMENT ON FUNCTION fn_reporting_mark_summary_stale()
IS 'Use on sales, inventory, and fuel tables to signal asynchronous summary refresh workers without running heavy calculations inline.';
