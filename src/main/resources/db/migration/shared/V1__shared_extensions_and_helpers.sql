CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE EXTENSION IF NOT EXISTS citext;

CREATE OR REPLACE FUNCTION public.fn_common_touch_updated_at()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$;

COMMENT ON FUNCTION public.fn_common_touch_updated_at()
IS 'Attach as a BEFORE UPDATE trigger to Hibernate-managed tables that expose updated_at.';

CREATE OR REPLACE FUNCTION public.fn_tenant_schema_health(schema_name text)
RETURNS jsonb
LANGUAGE plpgsql
AS $$
DECLARE
    schema_exists boolean;
    table_count bigint;
BEGIN
    SELECT EXISTS (
        SELECT 1
        FROM pg_namespace
        WHERE nspname = schema_name
    ) INTO schema_exists;

    IF NOT schema_exists THEN
        RETURN jsonb_build_object(
            'schemaName', schema_name,
            'exists', false,
            'tableCount', 0
        );
    END IF;

    SELECT COUNT(*)
    FROM pg_class c
    INNER JOIN pg_namespace n ON n.oid = c.relnamespace
    WHERE n.nspname = schema_name
      AND c.relkind IN ('r', 'p', 'v', 'm')
    INTO table_count;

    RETURN jsonb_build_object(
        'schemaName', schema_name,
        'exists', true,
        'tableCount', table_count
    );
END;
$$;

COMMENT ON FUNCTION public.fn_tenant_schema_health(text)
IS 'Returns a compact JSON health snapshot for a tenant schema and is safe before business tables exist.';
